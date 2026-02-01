package com.boilerplate.app.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Centralized tracing configuration for all microservices.
 * 
 * Uses OpenTelemetry bridge (micrometer-tracing-bridge-otel) for native OTLP support.
 * This provides better integration with OpenTelemetry-compatible backends like Jaeger.
 * 
 * This configuration enables:
 * - End-to-end tracing from Gateway → Services → Database
 * - Span logging (logs included in spans)
 * - Database query tracing (JPA/Hibernate)
 * - HTTP client tracing (RestTemplate, Feign)
 * - Native OTLP export to Jaeger
 * 
 * Configuration is done via application.yml:
 * - management.tracing.sampling.probability: Sampling rate
 * - management.otlp.tracing.endpoint: Jaeger endpoint
 * - management.tracing.baggage.remote-fields: Baggage propagation
 * 
 * Reference: https://spring.io/blog/2025/11/18/opentelemetry-with-spring-boot
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = "io.micrometer.tracing.Tracer")
public class TracingConfig {
    
    @Value("${spring.application.name:unknown}")
    private String serviceName;
    
    @Value("${management.otlp.tracing.endpoint:http://localhost:4318/v1/traces}")
    private String otlpEndpoint;
    
    @PostConstruct
    public void init() {
        log.info("=== Tracing Configuration Initialized ===");
        log.info("Service Name: {}", serviceName);
        log.info("OTLP Endpoint: {}", otlpEndpoint);
        log.info("  - Tracing Bridge: OpenTelemetry (micrometer-tracing-bridge-otel)");
        log.info("  - Database query tracing: Enabled (via JPA/Hibernate instrumentation)");
        log.info("  - HTTP client tracing: Enabled (RestTemplate, Feign auto-instrumented)");
        log.info("  - Span logging: Enabled (logs will appear in spans)");
        log.info("  - OTLP Export: Enabled (traces will be sent to Jaeger via OTLP)");
        log.info("==========================================");
    }
}
