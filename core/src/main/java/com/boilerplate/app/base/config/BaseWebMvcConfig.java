package com.boilerplate.app.base.config;

import com.boilerplate.app.base.interceptor.BaseControllerLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Base WebMVC configuration that automatically registers BaseControllerLoggingInterceptor.
 * This ensures all controller requests are logged when base package is present.
 */
@Configuration
@RequiredArgsConstructor
public class BaseWebMvcConfig implements WebMvcConfigurer {

    private final BaseControllerLoggingInterceptor controllerLoggingInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(controllerLoggingInterceptor)
            .addPathPatterns("/**");
    }
}

