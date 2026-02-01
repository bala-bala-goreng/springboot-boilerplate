# Troubleshooting Jaeger and Prometheus Connections

## Quick Verification

### 1. Check Service Health
```bash
# Check if services are running
docker-compose ps

# Check service logs for errors
docker-compose logs authentication | grep -i "jaeger\|otlp\|tracing"
docker-compose logs gateway | grep -i "jaeger\|otlp\|tracing"
```

### 2. Verify Jaeger is Running
```bash
# Check Jaeger container
docker-compose logs jaeger

# Access Jaeger UI
# http://localhost:16686
```

### 3. Verify Prometheus is Scraping
```bash
# Check Prometheus targets
# http://localhost:9090/targets

# All targets should show "UP" status
```

### 4. Verify Grafana Data Source
```bash
# Access Grafana
# http://localhost:3000
# Login: admin/admin
# Go to Configuration > Data Sources > Prometheus
# Should show: http://prometheus:9090
```

## Common Issues

### Issue 1: Services Not Sending Traces to Jaeger

**Symptoms:**
- Jaeger UI shows no services
- No traces in Jaeger

**Solutions:**

1. **Verify Environment Variable:**
   ```bash
   docker-compose exec authentication env | grep JAEGER_HOST
   # Should output: JAEGER_HOST=jaeger
   ```

2. **Check OTLP Endpoint Configuration:**
   - Verify `management.otlp.tracing.endpoint` in application-default.yml
   - Should be: `http://${JAEGER_HOST:localhost}:4318/v1/traces`
   - In Docker: `http://jaeger:4318/v1/traces`

3. **Verify Dependencies:**
   - Check `core/pom.xml` has `opentelemetry-exporter-otlp`
   - Check services include `core` dependency

4. **Check Network Connectivity:**
   ```bash
   # From service container, test connection to Jaeger
   docker-compose exec authentication wget -O- http://jaeger:4318/v1/traces
   # Should connect (may return error, but connection should work)
   ```

5. **Verify Tracing Configuration:**
   ```yaml
   management:
     tracing:
       sampling:
         probability: 1.0  # 100% sampling
       export:
         otlp:
           enabled: true
     otlp:
       tracing:
         endpoint: http://jaeger:4318/v1/traces
         protocol: http/protobuf
   ```

### Issue 2: Prometheus Not Scraping Metrics

**Symptoms:**
- Prometheus targets show "DOWN"
- No metrics in Grafana

**Solutions:**

1. **Verify Service Endpoints:**
   ```bash
   # Test from Prometheus container
   docker-compose exec prometheus wget -O- http://gateway:8999/actuator/prometheus
   docker-compose exec prometheus wget -O- http://authentication:8080/actuator/prometheus
   docker-compose exec prometheus wget -O- http://account:8081/actuator/prometheus
   docker-compose exec prometheus wget -O- http://payment:8082/actuator/prometheus
   ```

2. **Check Prometheus Configuration:**
   - Verify `docker/prometheus/prometheus.yml` has correct service names
   - Service names must match Docker service names (not hostnames)

3. **Verify Metrics Endpoint:**
   ```yaml
   management:
     endpoints:
       web:
         exposure:
           include: prometheus,health,metrics
     prometheus:
       metrics:
         export:
           enabled: true
   ```

4. **Check Service Ports:**
   - Gateway: 8999
   - Authentication: 8080
   - Account: 8081
   - Payment: 8082
   - Eureka: 8761

### Issue 3: Grafana Not Showing Data

**Symptoms:**
- Grafana dashboards show "No data"
- Prometheus data source shows "Connection refused"

**Solutions:**

1. **Verify Prometheus Data Source:**
   - URL should be: `http://prometheus:9090` (Docker network)
   - Not `http://localhost:9090`

2. **Check Grafana Logs:**
   ```bash
   docker-compose logs grafana | grep -i "prometheus\|error"
   ```

3. **Test Prometheus Query in Grafana:**
   - Go to Explore
   - Select Prometheus data source
   - Try query: `up{job="gateway"}`
   - Should return 1 if service is up

## Testing Connections

### Test Jaeger Connection
```bash
# Generate a trace by making a request
curl -X POST http://localhost:8999/api/oauth/token \
  -H "Content-Type: application/json" \
  -u "merchant-x:merchant-x-secret" \
  -d '{"grant_type":"client_credentials"}'

# Check Jaeger UI for traces
# http://localhost:16686
# Select service: service-gateway or service-authentication
```

### Test Prometheus Scraping
```bash
# Check Prometheus targets
curl http://localhost:9090/api/v1/targets | jq '.data.activeTargets[] | {job: .labels.job, health: .health}'

# Query metrics
curl http://localhost:9090/api/v1/query?query=up
```

### Test Grafana
```bash
# Access Grafana
# http://localhost:3000
# Login: admin/admin
# Go to Dashboards > Browse
# Should see pre-configured dashboards
```

## Network Debugging

### Check Docker Network
```bash
# List networks
docker network ls

# Inspect app-network
docker network inspect app_app-network

# Should show all services connected
```

### Test Service-to-Service Communication
```bash
# From authentication container
docker-compose exec authentication ping jaeger
docker-compose exec authentication ping prometheus

# From gateway container
docker-compose exec gateway ping jaeger
docker-compose exec gateway ping prometheus
```

## Configuration Checklist

- [ ] All services have `core` dependency (provides tracing)
- [ ] All services have `micrometer-registry-prometheus` dependency
- [ ] `JAEGER_HOST=jaeger` set in docker-compose.yml for all services
- [ ] `management.otlp.tracing.endpoint` configured correctly
- [ ] `management.tracing.export.otlp.enabled: true` set
- [ ] Prometheus `prometheus.yml` has correct service names and ports
- [ ] Grafana data source points to `http://prometheus:9090`
- [ ] All services are on `app-network` Docker network
- [ ] Jaeger has `COLLECTOR_OTLP_ENABLED=true`

## Restart Services

If issues persist, restart the monitoring stack:

```bash
# Restart monitoring services
docker-compose restart jaeger prometheus grafana

# Restart application services
docker-compose restart gateway authentication account payment

# Or restart everything
docker-compose down
docker-compose up -d
```

## Logs to Check

```bash
# Jaeger logs
docker-compose logs jaeger

# Prometheus logs
docker-compose logs prometheus

# Service logs (look for tracing/metrics errors)
docker-compose logs authentication | grep -i "tracing\|otlp\|metrics"
docker-compose logs gateway | grep -i "tracing\|otlp\|metrics"
```
