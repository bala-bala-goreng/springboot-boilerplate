# Grafana Dashboards Summary

This document provides an overview of all default Grafana dashboards available for monitoring the microservices.

## Available Dashboards

### 1. Microservices Overview - All Services
**File**: `microservices-overview.json`

Comprehensive overview dashboard showing metrics across all services:
- Service Health Status (UP/DOWN indicators for all services)
- HTTP Request Rate (all services combined)
- HTTP Response Time (P95 percentiles)
- HTTP Error Rate (4xx & 5xx errors)
- HTTP Success Rate (2xx responses)
- JVM Memory Usage (Heap & Non-Heap)
- JVM Threads (Live & Peak)
- JVM GC Pause Time
- CPU Usage
- Database Connection Pool (Active connections)
- Eureka Registered Instances
- Service Uptime

**Use Case**: High-level monitoring of the entire microservices ecosystem.

---

### 2. Gateway Service Dashboard
**File**: `gateway-service.json`

Gateway-specific metrics:
- Service Status (UP/DOWN)
- HTTP Request Rate (by method and URI)
- HTTP Response Time (P50, P95, P99 percentiles)
- HTTP Status Codes distribution
- JVM Heap Memory (Used vs Max)
- JVM Threads (Live threads count)
- CPU Usage percentage

**Use Case**: Monitor API Gateway performance and health.

---

### 3. Authentication Service Dashboard
**File**: `authentication-service.json`

Authentication service metrics:
- Service Status (UP/DOWN)
- HTTP Request Rate (by method and URI)
- HTTP Response Time (P95 percentile)
- OAuth2 Token Generation rate (specific endpoints)
- Database Connection Pool (Active, Idle, Total)
- JVM Heap Memory (Used vs Max)
- JVM GC Pause Time (by action type)

**Use Case**: Monitor authentication service, OAuth2 token generation, and database connections.

---

### 4. Account Service Dashboard
**File**: `account-service.json`

Account service metrics:
- Service Status (UP/DOWN)
- HTTP Request Rate (by method and URI)
- HTTP Response Time (P95 percentile)
- Account API Endpoints (specific `/api/accounts/*` routes)
- Database Connection Pool (Active, Idle)
- JVM Heap Memory (Used vs Max)
- CPU Usage percentage

**Use Case**: Monitor account service operations and database performance.

---

### 5. Payment Service Dashboard
**File**: `payment-service.json`

Payment service metrics:
- Service Status (UP/DOWN)
- HTTP Request Rate (by method and URI)
- HTTP Response Time (P95 percentile)
- Payment API Endpoints (specific `/api/payments/*` routes)
- Database Connection Pool (Active, Idle)
- JVM Heap Memory (Used vs Max)
- CPU Usage percentage

**Use Case**: Monitor payment processing and transaction performance.

---

### 6. Eureka Service Discovery Dashboard
**File**: `eureka-service.json`

Eureka service discovery metrics:
- Service Status (UP/DOWN)
- Registered Instances count
- HTTP Request Rate (by method and URI)
- HTTP Response Time (P95 percentile)
- JVM Heap Memory (Used vs Max)
- CPU Usage percentage

**Use Case**: Monitor service discovery registry and registered microservices.

---

## Dashboard Features

All dashboards include:
- **Auto-refresh**: Every 10 seconds
- **Time Range**: Default 15 minutes (configurable)
- **Color Coding**: Status indicators (green=UP, red=DOWN)
- **Units**: Proper units for each metric (reqps, MB, percent, seconds)
- **Legends**: Descriptive labels for all metrics

## Accessing Dashboards

1. **Start Grafana**:
   ```bash
   cd docker
   docker-compose up grafana prometheus
   ```

2. **Access Grafana**:
   - URL: http://localhost:3000
   - Username: `admin`
   - Password: `admin`

3. **View Dashboards**:
   - Go to "Dashboards" → "Browse"
   - All dashboards are automatically provisioned and available

## Metrics Data Source

All dashboards use the **Prometheus** data source, which is pre-configured in Grafana.

Prometheus scrapes metrics from:
- Gateway: `http://gateway:8999/actuator/prometheus`
- Authentication: `http://authentication:8080/actuator/prometheus`
- Account: `http://account:8081/actuator/prometheus`
- Payment: `http://payment:8082/actuator/prometheus`
- Eureka: `http://eureka:8761/actuator/prometheus`

## Customization

Dashboards can be customized in Grafana UI:
1. Open any dashboard
2. Click "Edit" (pencil icon)
3. Modify panels, add new ones, or change queries
4. Click "Save" to persist changes

Changes made in the UI are stored in Grafana's database and will persist across container restarts.

## Dashboard Structure

Each dashboard follows this structure:
```json
{
  "dashboard": {
    "title": "Dashboard Name",
    "tags": ["tag1", "tag2"],
    "timezone": "browser",
    "schemaVersion": 38,
    "version": 1,
    "refresh": "10s",
    "panels": [...]
  }
}
```

## Troubleshooting

If dashboards don't appear:
1. Check Grafana logs: `docker-compose logs grafana`
2. Verify Prometheus is running: `docker-compose ps prometheus`
3. Check data source: Grafana → Configuration → Data Sources
4. Verify metrics are available: Visit `http://localhost:9090` (Prometheus UI)
