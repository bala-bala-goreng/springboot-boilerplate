package com.boilerplate.app.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass(name = "io.micrometer.tracing.Tracer")
public class TracingConfig {
    
    public TracingConfig() {
        log.info("Tracing configuration initialized - Jaeger distributed tracing enabled");
    }
}
