# Jaeger Distributed Tracing Guide

## 📊 Accessing Jaeger UI

### 1. Open Jaeger UI
- **URL**: http://localhost:16686
- Jaeger UI is available when services are running in Docker

### 2. Verify Jaeger is Running
```bash
# Check if Jaeger container is running
docker ps | grep jaeger

# Or check via docker-compose
cd docker
docker-compose ps jaeger
```

---

## 🔍 Viewing Traces

### Step 1: Generate Traces
Make requests through your services to generate traces. Here are example requests:

#### Example 1: OAuth Token Generation (via Gateway)
```bash
curl -X POST http://localhost:8999/api/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -H "Authorization: Basic bWVyY2hhbnQteDptZXJjaGFudC14LXNlY3JldC1rZXktMTIz" \
  -d "grant_type=client_credentials"
```

**Trace Flow**: Gateway → Authentication Service

#### Example 2: Get All Partners (via Gateway)
```bash
# First, get OAuth token
TOKEN=$(curl -X POST http://localhost:8999/api/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -H "Authorization: Basic bWVyY2hhbnQteDptZXJjaGFudC14LXNlY3JldC1rZXktMTIz" \
  -d "grant_type=client_credentials" | jq -r '.access_token')

# Then use token to get partners
curl -X GET http://localhost:8999/api/partners \
  -H "Authorization: Bearer $TOKEN"
```

**Trace Flow**: Gateway → Authentication Service

#### Example 3: Account Service (via Gateway)
```bash
curl -X GET http://localhost:8999/api/accounts
```

**Trace Flow**: Gateway → Account Service

#### Example 4: Payment Service (via Gateway)
```bash
curl -X GET http://localhost:8999/api/payments
```

**Trace Flow**: Gateway → Payment Service

---

### Step 2: Search Traces in Jaeger UI

1. **Open Jaeger UI**: http://localhost:16686

2. **Search Options**:
   - **Service**: Select a service name:
     - `service-gateway`
     - `service-authentication`
     - `service-account`
     - `service-payment`
   - **Operation**: Leave empty or select specific operation
   - **Tags**: Add tags to filter (e.g., `http.method=GET`, `http.status_code=200`)
   - **Lookback**: Select time range (Last 15 minutes, Last 1 hour, etc.)
   - **Max Results**: Limit number of traces (default: 20)

3. **Click "Find Traces"**

---

### Step 3: View Trace Details

Once you find a trace:

1. **Trace Timeline View**:
   - Shows all spans in a timeline
   - Each span represents a service operation
   - Spans are color-coded by service
   - Duration is shown for each span

2. **Trace Details**:
   - Click on a trace to see details
   - **Trace ID**: Unique identifier for the entire request flow
   - **Duration**: Total time for the request
   - **Services**: All services involved in the trace
   - **Spans**: Individual operations within the trace

3. **Span Details**:
   - Click on a span to see:
     - **Operation Name**: e.g., `GET /api/oauth/token`
     - **Service**: Service that handled the operation
     - **Duration**: How long the operation took
     - **Tags**: HTTP method, status code, URL, etc.
     - **Logs**: Any log entries associated with the span

---

## 📈 Understanding Trace Visualization

### Trace Structure
```
┌─────────────────────────────────────────────────────────┐
│ Trace: Request through Gateway                          │
│ Duration: 150ms                                          │
├─────────────────────────────────────────────────────────┤
│ [Gateway] GET /api/oauth/token (50ms)                   │
│   └─ [Authentication] POST /api/oauth/token (100ms)      │
│       └─ [Database] Query partners table (30ms)         │
└─────────────────────────────────────────────────────────┘
```

### What You'll See:
- **Root Span**: Usually the Gateway entry point
- **Child Spans**: Service-to-service calls
- **Nested Spans**: Database queries, external API calls, etc.

---

## 🔎 Common Search Patterns

### Find All Traces for a Service
1. Select **Service**: `service-gateway`
2. Click **Find Traces**

### Find Failed Requests
1. Select **Service**: `service-gateway`
2. Add **Tag**: `http.status_code>=400`
3. Click **Find Traces**

### Find Slow Requests
1. Select **Service**: `service-gateway`
2. Add **Tag**: `duration>1s` (or adjust threshold)
3. Click **Find Traces**

### Find Traces by HTTP Method
1. Select **Service**: `service-gateway`
2. Add **Tag**: `http.method=POST`
3. Click **Find Traces**

---

## 🎯 Trace Tags and Metadata

Common tags you'll see in traces:

- **HTTP Tags**:
  - `http.method`: GET, POST, PUT, DELETE, etc.
  - `http.status_code`: 200, 404, 500, etc.
  - `http.url`: Full request URL
  - `http.route`: Route pattern matched

- **Service Tags**:
  - `service.name`: Service identifier
  - `spring.application.name`: Spring application name

- **Error Tags**:
  - `error`: true (if error occurred)
  - `error.message`: Error message

---

## 🐛 Troubleshooting

### No Traces Appearing?

1. **Check Services are Running**:
   ```bash
   docker-compose ps
   ```

2. **Check Jaeger is Running**:
   ```bash
   docker-compose ps jaeger
   curl http://localhost:16686
   ```

3. **Check Tracing Configuration**:
   - Verify `management.tracing.sampling.probability: 1.0` in application.yml
   - Verify `management.otlp.tracing.endpoint` is correct:
     - Local: `http://localhost:4318/v1/traces`
     - Docker: `http://jaeger:4318/v1/traces`

4. **Check Service Logs**:
   ```bash
   docker-compose logs gateway | grep -i trace
   docker-compose logs authentication | grep -i trace
   ```

5. **Verify OTLP Endpoint**:
   ```bash
   # Test if Jaeger OTLP endpoint is accessible
   curl http://localhost:4318/v1/traces
   ```

### Traces Not Complete?

- **Missing Spans**: Check if all services have tracing dependencies
- **No Child Spans**: Verify trace context propagation is working
- **Incomplete Traces**: Check network connectivity between services

---

## 📝 Example: Complete Trace Flow

### Request Flow:
```
Client Request
    ↓
Gateway (service-gateway)
    ↓
Authentication Service (service-authentication)
    ↓
PostgreSQL Database
```

### What You'll See in Jaeger:

1. **Trace**: Single trace ID for entire request
2. **Spans**:
   - `GET /api/oauth/token` (Gateway)
   - `POST /api/oauth/token` (Authentication)
   - `SELECT * FROM partners` (Database)

3. **Timeline**: Shows sequential execution
4. **Duration**: Total time and per-span time

---

## 🚀 Quick Start Commands

### Generate a Trace
```bash
# Simple GET request through gateway
curl http://localhost:8999/api/accounts

# OAuth token request
curl -X POST http://localhost:8999/api/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -H "Authorization: Basic bWVyY2hhbnQteDptZXJjaGFudC14LXNlY3JldC1rZXktMTIz" \
  -d "grant_type=client_credentials"
```

### View in Jaeger
1. Open: http://localhost:16686
2. Select Service: `service-gateway`
3. Click: **Find Traces**
4. Click on a trace to see details

---

## 💡 Tips

1. **Use Service Names**: Search by service name to see all traces for that service
2. **Check Duration**: Look for slow spans (red/orange in timeline)
3. **Error Tags**: Filter by `error=true` to find failed requests
4. **Time Range**: Adjust lookback period to find recent traces
5. **Compare Traces**: Compare multiple traces to identify performance issues

---

## 📚 Additional Resources

- **Jaeger Documentation**: https://www.jaegertracing.io/docs/
- **Micrometer Tracing**: https://micrometer.io/docs/tracing
- **OpenTelemetry**: https://opentelemetry.io/
