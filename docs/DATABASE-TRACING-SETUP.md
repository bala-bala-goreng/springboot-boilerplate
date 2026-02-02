# Database Tracing Setup

## Overview

Database tracing has been enabled to capture spans for all database queries (JDBC operations) in addition to HTTP requests. This provides end-to-end visibility from API calls through to database operations.

## Changes Made

### 1. Hibernate Configuration

Updated all service `application-docker.yml` files:
- `authentication/src/main/resources/application-docker.yml`
- `account/src/main/resources/application-docker.yml`
- `payment/src/main/resources/application-docker.yml`

**Added Hibernate properties:**
```yaml
jpa:
  properties:
    hibernate:
      # Enable Hibernate statistics for tracing
      generate_statistics: true
      # Format SQL for better readability in traces
      format_sql: true
```

### 2. Automatic JDBC Instrumentation

Spring Boot 3.5 with `micrometer-tracing-bridge-otel` **automatically instruments JDBC operations** when:
- `spring-boot-starter-data-jpa` is present (already in all services)
- `micrometer-tracing-bridge-otel` is present (already in core library)
- Tracing is enabled (`management.tracing.export.otlp.enabled: true`)

**No additional dependency needed!** Spring Boot automatically wraps JDBC operations with tracing spans.

## How It Works

1. **Automatic JDBC Instrumentation**: Spring Boot 3.5 with micrometer-tracing automatically wraps JDBC operations with tracing spans.

2. **Hibernate Statistics**: Enabling `generate_statistics: true` allows Hibernate to provide additional metadata for tracing.

3. **Automatic Span Creation**: Every database query automatically creates a span with:
   - Operation name: `jdbc.query`, `jdbc.execute`, `jdbc.prepare`
   - Database name: PostgreSQL
   - SQL statement (if enabled)
   - Query duration
   - Connection details

## Span Information

Database spans will include:
- **Operation**: `jdbc.query`, `jdbc.execute`, `jdbc.prepare`, `jdbc.commit`, `jdbc.rollback`
- **Database**: PostgreSQL
- **SQL**: The actual SQL query (if `show-sql: true` or via Hibernate statistics)
- **Duration**: Query execution time
- **Connection**: Database connection details

## Verification

### 1. Check Traces in Grafana

1. Open Grafana: http://localhost:3000
2. Go to **Explore** → Select **Tempo** datasource
3. Select a service (e.g., `service-authentication`)
4. Look for spans with operation names:
   - `jdbc.query`
   - `jdbc.execute`
   - `jdbc.prepare`
   - `jdbc.commit`
   - `jdbc.rollback`

### 2. Check Service Logs

After making a request, check service logs for database operations:
```bash
docker-compose logs authentication --tail=20 | grep -i "jdbc\|sql\|database"
```

### 3. Trace Structure

A complete trace should show:
```
HTTP Request (gateway)
  └── HTTP Request (service)
      └── JDBC Query (SELECT ...)
      └── JDBC Query (INSERT ...)
      └── JDBC Commit
```

## Configuration Options

### Enable SQL in Spans

To see actual SQL queries in spans, you can enable SQL logging:
```yaml
jpa:
  show-sql: true
  properties:
    hibernate:
      format_sql: true
```

**Note**: This will also log SQL to console, which may be verbose. For production, keep `show-sql: false` and rely on tracing spans.

### Disable Hibernate Statistics

If you want to disable Hibernate statistics (minimal performance impact):
```yaml
jpa:
  properties:
    hibernate:
      generate_statistics: false
```

**Note**: This won't disable JDBC tracing (it's automatic), but will reduce metadata in spans.

## Performance Considerations

- **Hibernate Statistics**: Enabling `generate_statistics: true` has minimal performance impact but provides better tracing metadata.
- **SQL Formatting**: `format_sql: true` only affects logging/tracing output, not query performance.
- **Tracing Overhead**: JDBC instrumentation adds minimal overhead (~1-2ms per query).

## Troubleshooting

### No Database Spans Appearing

1. **Verify configuration**: Check `application-docker.yml` has:
   - `management.tracing.export.otlp.enabled: true`
   - `management.otlp.tracing.endpoint: http://otel-collector:4318/v1/traces`

2. **Check service logs**: Look for tracing initialization messages
   ```bash
   docker-compose logs authentication | grep -i "tracing\|jdbc\|database"
   ```

3. **Verify dependencies**: Ensure `spring-boot-starter-data-jpa` is present
   ```bash
   docker-compose exec authentication ls -la /app/app.jar
   ```

4. **Rebuild services**: If configuration was just changed, rebuild:
   ```bash
   docker-compose build authentication account payment
   docker-compose restart authentication account payment
   ```

### SQL Not Visible in Spans

- SQL statements are included in spans by default
- If not visible, check Grafana span details (expand span to see attributes)
- Ensure Hibernate statistics are enabled: `hibernate.generate_statistics: true`
- Optionally enable SQL logging: `jpa.show-sql: true` (for debugging only)

## How Spring Boot Enables JDBC Tracing

Spring Boot 3.5 automatically enables JDBC tracing when:
1. `spring-boot-starter-data-jpa` is on classpath
2. `micrometer-tracing` is on classpath (via `micrometer-tracing-bridge-otel`)
3. Tracing is enabled (`management.tracing.export.otlp.enabled: true`)

The auto-configuration wraps:
- `DataSource` with tracing proxy
- JDBC `Connection` with tracing wrapper
- `PreparedStatement` and `Statement` with tracing

This happens automatically - no manual configuration needed!

## References

- [Spring Boot Tracing](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.tracing)
- [Micrometer Tracing](https://micrometer.io/docs/tracing)
- [Hibernate Statistics](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#statistics)
