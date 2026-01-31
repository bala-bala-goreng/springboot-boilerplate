package com.boilerplate.app.base.interceptor;

import org.springframework.stereotype.Component;

/**
 * Base component for controller logging interceptor.
 * This is a ready-to-use component that services can use directly or extend for customization.
 * 
 * Usage:
 * 1. Direct use: Register in WebMvcConfig:
 *    <pre>
 *    @Configuration
 *    public class WebMvcConfig implements WebMvcConfigurer {
 *        private final BaseControllerLoggingInterceptor interceptor;
 *        
 *        @Override
 *        public void addInterceptors(InterceptorRegistry registry) {
 *            registry.addInterceptor(interceptor).addPathPatterns("/**");
 *        }
 *    }
 *    </pre>
 * 
 * 2. Extend for customization:
 *    <pre>
 *    @Component
 *    public class MyServiceLoggingInterceptor extends BaseControllerLoggingInterceptor {
 *        // Override methods if needed
 *    }
 *    </pre>
 */
@Component
public class BaseControllerLoggingInterceptor extends ControllerLoggingInterceptor {
    // Ready-to-use component - no additional implementation needed
    // Services can extend this class if they need custom behavior
}

