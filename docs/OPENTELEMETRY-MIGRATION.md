# OpenTelemetry Bridge Migration

This document describes the migration from Brave bridge to OpenTelemetry bridge for distributed tracing, following the [Spring Boot OpenTelemetry blog post](https://spring.io/blog/2025/11/18/opentelemetry-with-spring-boot).

## Changes Made

### 1. Dependency Updates

**Replaced in `core/pom.xml` and `authentication/pom.xml`:**
- ❌ `micrometer-tracing-bridge-brave` 
- ✅ `micrometer-tracing-bridge-otel`

The OpenTelemetry bridge provides native OTLP support and better integration with OpenTelemetry-compatible backends like Jaeger.

### 2. Configuration Updates

**Updated `TracingConfig.java`:**
- Added reference to OpenTelemetry bridge
- Updated log messages to reflect OpenTelemetry bridge usage

**Updated logging levels:**
- Removed `brave: DEBUG` logging
- Added comprehensive OpenTelemetry debug logging:
  - `io.opentelemetry: DEBUG`
  - `org.springframework.boot.actuate.autoconfigure.tracing: DEBUG`
  - `org.springframework.boot.actuate.autoconfigure.tracing.otlp: DEBUG`
  - `io.opentelemetry.exporter.otlp: DEBUG`
  - `io.opentelemetry.sdk: DEBUG`

### 3. Why OpenTelemetry Bridge?

According to the Spring Boot blog post:

1. **Native OTLP Support**: The OpenTelemetry bridge (`micrometer-tracing-bridge-otel`) provides native support for the OTLP protocol, which is the standard for OpenTelemetry-compatible backends.

2. **Better Integration**: Spring Boot 3.5+ auto-configures `OtlpHttpSpanExporter` (or `OtlpGrpcSpanExporter`) when using the OpenTelemetry bridge, providing seamless integration.

3. **Protocol-First Approach**: As stated in the blog: "It's the protocol that matters, not the library used." The OTLP protocol allows Micrometer signals to be exported to any OpenTelemetry-capable backend.

4. **Future-Proof**: The OpenTelemetry bridge aligns with the direction Spring Boot is heading (Spring Boot 4.0 introduces `spring-boot-starter-opentelemetry`).

## Configuration

The existing OTLP configuration remains the same:

```yaml
management:
  tracing:
    enabled: true
    sampling:
      probability: 1.0
    export:
      otlp:
        enabled: true
  otlp:
    tracing:
      endpoint: http://localhost:4318/v1/traces
      protocol: http/protobuf
```

## Benefits

1. **Better OTLP Export**: The OpenTelemetry bridge automatically exports traces to OTLP endpoints without additional configuration.

2. **Improved Trace Quality**: Native OpenTelemetry integration provides better span attributes and trace context propagation.

3. **Compatibility**: Works seamlessly with Jaeger, Grafana, and other OpenTelemetry-compatible backends.

## Testing

After rebuilding and restarting services:

1. **Check logs** for OpenTelemetry initialization:
   ```
   === Tracing Configuration Initialized ===
   Tracing Bridge: OpenTelemetry (micrometer-tracing-bridge-otel)
   OTLP Export: Enabled (traces will be sent to Jaeger via OTLP)
   ```

2. **Verify traces in Jaeger**:
   - Open http://localhost:16686
   - Select your service from the dropdown
   - Click "Find Traces"
   - You should see traces with proper service names

3. **Check for OTLP export logs**:
   - Look for `io.opentelemetry.exporter.otlp` DEBUG logs
   - These indicate spans are being exported to Jaeger

## References

- [Spring Boot OpenTelemetry Blog Post](https://spring.io/blog/2025/11/18/opentelemetry-with-spring-boot)
- [Micrometer Tracing Documentation](https://micrometer.io/docs/tracing)
- [OpenTelemetry Documentation](https://opentelemetry.io/docs/)
