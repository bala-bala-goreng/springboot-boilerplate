# Monitoring Setup with Grafana and Prometheus

This directory contains the configuration for monitoring microservices using Grafana and Prometheus.

## Services

### Prometheus
- **Port**: `9090`
- **URL**: http://localhost:9090
- **Purpose**: Collects metrics from all microservices via Actuator endpoints

### Grafana
- **Port**: `3000`
- **URL**: http://localhost:3000
- **Default Credentials**:
  - Username: `admin`
  - Password: `admin`
- **Purpose**: Visualizes metrics collected by Prometheus

## Configuration Files

- `prometheus/prometheus.yml` - Prometheus scrape configuration
- `grafana/provisioning/datasources/prometheus.yml` - Grafana data source configuration
- `grafana/provisioning/dashboards/dashboard.yml` - Dashboard provisioning configuration

## Running the Monitoring Stack

### Start Prometheus and Grafana

```bash
cd docker
docker-compose up prometheus grafana
```

### Start All Services (including monitoring)

```bash
cd docker
docker-compose up
```

## Accessing the Services

1. **Grafana Dashboard**: http://localhost:3000
   - Login with `admin/admin`
   - Prometheus data source is pre-configured

2. **Prometheus UI**: http://localhost:9090
   - View raw metrics
   - Test PromQL queries

## Metrics Endpoints

All services expose Prometheus metrics at:
- Gateway: http://localhost:8999/actuator/prometheus
- Authentication: http://localhost:8080/actuator/prometheus
- Account: http://localhost:8081/actuator/prometheus
- Payment: http://localhost:8082/actuator/prometheus
- Eureka: http://localhost:8761/actuator/prometheus

## Available Metrics

Spring Boot Actuator provides various metrics including:
- HTTP request rates and latencies
- JVM memory usage
- Thread counts
- Database connection pool metrics
- Custom application metrics

## Creating Custom Dashboards

1. Login to Grafana at http://localhost:3000
2. Go to Dashboards → New Dashboard
3. Add panels with PromQL queries
4. Example queries:
   - Request rate: `sum(rate(http_server_requests_seconds_count[5m])) by (service)`
   - Error rate: `sum(rate(http_server_requests_seconds_count{status=~"5.."}[5m])) by (service)`
   - Memory usage: `jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}`

## Data Persistence

- Prometheus data is stored in `prometheus_data` volume
- Grafana dashboards and settings are stored in `grafana_data` volume

These volumes persist data across container restarts.
