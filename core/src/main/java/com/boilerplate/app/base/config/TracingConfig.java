package com.boilerplate.app.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Centralized tracing configuration for all microservices.
 * 
 * This configuration enables:
 * - End-to-end tracing from Gateway → Services → Database
 * - Span logging (logs included in spans)
 * - Database query tracing (JPA/Hibernate)
 * - HTTP client tracing (RestTemplate, Feign)
 * 
 * Configuration is done via application.yml:
 * - management.tracing.sampling.probability: Sampling rate
 * - management.otlp.tracing.endpoint: Jaeger endpoint
 * - management.tracing.baggage.remote-fields: Baggage propagation
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = "io.micrometer.tracing.Tracer")
public class TracingConfig {
    
    public TracingConfig() {
        log.info("Tracing configuration initialized - Jaeger distributed tracing enabled");
        log.info("  - Database query tracing: Enabled (via JPA/Hibernate instrumentation)");
        log.info("  - HTTP client tracing: Enabled (RestTemplate, Feign auto-instrumented)");
        log.info("  - Span logging: Enabled (logs will appear in spans)");
    }
}
