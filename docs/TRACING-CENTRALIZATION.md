# Tracing Dependencies Centralization

This document describes how tracing dependencies are centralized in the `core` library and used across all microservices.

## Architecture

All tracing dependencies are centralized in the `core` library (`core/pom.xml`). Individual services (gateway, authentication, account, payment) depend on the `core` library and automatically inherit all tracing capabilities.

## Centralized Dependencies

**Location:** `core/pom.xml`

```xml
<!-- Micrometer Tracing - Centralized in core library -->
<!-- Using OpenTelemetry bridge for native OTLP support -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing</artifactId>
</dependency>
```

## Service Dependencies

All services depend on the `core` library:

### Gateway (`gateway/pom.xml`)
```xml
<dependency>
    <groupId>com.boilerplate.app</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Authentication (`authentication/pom.xml`)
```xml
<dependency>
    <groupId>com.boilerplate.app</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0</version>
</dependency>
<!-- Tracing dependencies are centralized in core library -->
<!-- No need for explicit tracing dependencies here - they come from core -->
```

### Account (`account/pom.xml`)
```xml
<dependency>
    <groupId>com.boilerplate.app</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Payment (`payment/pom.xml`)
```xml
<dependency>
    <groupId>com.boilerplate.app</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

Each service has its own `application-default.yml` and `application-docker.yml` files with tracing configuration. The configuration is consistent across all services:

```yaml
management:
  tracing:
    enabled: true  # Explicitly enable tracing
    sampling:
      probability: 1.0
    baggage:
      remote-fields: traceId,spanId
    span:
      exportable: true
    service-name: ${spring.application.name}
    export:
      otlp:
        enabled: true
      zipkin:
        enabled: false  # Disable Zipkin, use OTLP for Jaeger
  otlp:
    tracing:
      endpoint: http://${JAEGER_HOST:localhost}:4318/v1/traces
      protocol: http/protobuf
      headers: {}
      timeout: 10s
      compression: none
logging:
  level:
    root: INFO
    io.micrometer.tracing: DEBUG
    io.opentelemetry: DEBUG
    org.springframework.boot.actuate.autoconfigure.tracing: DEBUG
    org.springframework.boot.actuate.autoconfigure.tracing.otlp: DEBUG
    io.opentelemetry.exporter.otlp: DEBUG
    io.opentelemetry.sdk: DEBUG
```

## Benefits of Centralization

1. **Single Source of Truth**: All tracing dependencies are managed in one place (`core/pom.xml`)
2. **Consistency**: All services use the same tracing implementation (OpenTelemetry bridge)
3. **Easy Updates**: Update tracing dependencies once in `core`, and all services automatically get the update
4. **Reduced Duplication**: No need to declare tracing dependencies in each service's `pom.xml`
5. **Automatic Inheritance**: Services automatically inherit tracing capabilities by depending on `core`

## Centralized Configuration Class

The `TracingConfig` class in `core/src/main/java/com/boilerplate/app/base/config/TracingConfig.java` provides centralized tracing configuration initialization and logging.

## Service-Specific Configuration

While dependencies are centralized, each service can still customize tracing behavior via:
- `application-default.yml` - Local development configuration
- `application-docker.yml` - Docker environment configuration
- Environment variables (e.g., `JAEGER_HOST`)

## Updating Tracing Dependencies

To update tracing dependencies:

1. **Update `core/pom.xml`** with new dependency versions
2. **Rebuild core library**: `cd core && ./mvnw.cmd clean install`
3. **Rebuild all services**: They will automatically pick up the new versions from `core`

## Verification

To verify that services are using centralized tracing:

1. **Check service logs** for:
   ```
   === Tracing Configuration Initialized ===
   Tracing Bridge: OpenTelemetry (micrometer-tracing-bridge-otel)
   OTLP Export: Enabled (traces will be sent to Jaeger via OTLP)
   ```

2. **Verify in Jaeger UI**:
   - All services should appear in the service dropdown
   - Traces should show proper service names
   - Distributed traces should span across services

3. **Check dependency tree**:
   ```bash
   cd <service> && ./mvnw.cmd dependency:tree | grep -i "micrometer-tracing"
   ```
   Should show dependencies coming from `core` library.
