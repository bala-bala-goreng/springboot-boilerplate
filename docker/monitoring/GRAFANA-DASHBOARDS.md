# Grafana Dashboards

This directory contains Grafana dashboard configurations for monitoring all microservices.

## Available Dashboards

### 1. Microservices Overview - All Services
**File**: `microservices-overview.json`

Comprehensive overview dashboard showing metrics for all services:
- Service Health Status (UP/DOWN indicators)
- HTTP Request Rate (all services)
- HTTP Response Time (P95)
- HTTP Error Rate (4xx & 5xx)
- HTTP Success Rate (2xx)
- JVM Memory Usage (Heap & Non-Heap)
- JVM Threads (Live & Peak)
- JVM GC Pause Time
- CPU Usage
- Database Connection Pool (Active connections)
- Eureka Registered Instances
- Service Uptime

### 2. Gateway Service
**File**: `gateway-service.json`

Gateway-specific metrics:
- HTTP Request Rate by endpoint
- HTTP Response Time (P50, P95, P99)
- HTTP Status Codes
- JVM Memory
- JVM Threads
- CPU Usage

### 3. Authentication Service
**File**: `authentication-service.json`

Authentication service metrics:
- HTTP Request Rate
- HTTP Response Time (P95)
- OAuth2 Token Generation Rate
- Database Connection Pool
- JVM Memory
- JVM GC Pause Time

### 4. Account Service
**File**: `account-service.json`

Account service metrics:
- HTTP Request Rate
- HTTP Response Time (P95)
- Account API Endpoints
- Database Connection Pool
- JVM Memory
- CPU Usage

### 5. Payment Service
**File**: `payment-service.json`

Payment service metrics:
- HTTP Request Rate
- HTTP Response Time (P95)
- Payment API Endpoints
- Database Connection Pool
- JVM Memory
- CPU Usage

### 6. Eureka Service Discovery
**File**: `eureka-service.json`

Eureka service discovery metrics:
- Registered Instances count
- Client Status (UP/DOWN)
- HTTP Request Rate
- HTTP Response Time
- JVM Memory
- CPU Usage

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

## Metrics Collection

All services expose Prometheus metrics at:
- Gateway: http://localhost:8999/actuator/prometheus
- Authentication: http://localhost:8080/actuator/prometheus
- Account: http://localhost:8081/actuator/prometheus
- Payment: http://localhost:8082/actuator/prometheus
- Eureka: http://localhost:8761/actuator/prometheus

Prometheus scrapes these endpoints every 15 seconds and stores the metrics.

## Dashboard Refresh

All dashboards are configured to:
- Auto-refresh every 10 seconds
- Show data from the last 15 minutes by default
- Use browser timezone

## Customization

Dashboards can be customized in Grafana UI:
1. Open any dashboard
2. Click "Edit" (pencil icon)
3. Modify panels, add new ones, or change queries
4. Click "Save" to persist changes

Changes made in the UI are stored in Grafana's database and will persist across container restarts.
