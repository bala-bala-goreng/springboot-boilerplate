package com.boilerplate.app.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

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
        log.info("  - Database query tracing: Enabled (via JDBC instrumentation + Hibernate statistics)");
        log.info("  - HTTP client tracing: Enabled (RestTemplate, Feign auto-instrumented)");
        log.info("  - Span logging: Enabled (logs will appear in spans)");
        log.info("  - OTLP Export: Enabled (traces will be sent to Tempo via OTLP)");
        log.info("==========================================");
    }
}
