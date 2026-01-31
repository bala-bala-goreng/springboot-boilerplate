# Eleanor Artajasa Service

Artajasa service for Eleanor platform providing QRIS payment credit, status check, and refund operations.

## Overview

This service implements payment processing through the Artajasa payment gateway. It includes a **base package** (`com.gpay.eleanor.base`) that contains reusable, shared components that can be copied to other services for consistent logging, filtering, and utility functions.

## Base Package Structure

The `base` package is designed to be **copyable directly to new projects**. It provides:

- **Automatic logging** for all HTTP requests/responses
- **Structured logging** with trace IDs for request correlation
- **Feign client logging** for external service calls
- **Request/response body caching** for multiple reads
- **Common utilities** for JSON, dates, and object conversion

### Package Organization

```
base/
├── config/
│   └── BaseWebMvcConfig.java          # Auto-registers logging interceptor
├── filter/
│   └── CachedBodyFilter.java          # Caches request/response bodies
├── interceptor/
│   ├── ControllerLoggingInterceptor.java      # Base interceptor class
│   └── BaseControllerLoggingInterceptor.java  # Ready-to-use @Component
├── logging/
│   ├── FeignLoggingLogger.java        # Feign client structured logging
│   ├── LoggingUtil.java               # Structured logging utilities
│   └── RequestResponseLogger.java     # HTTP request/response logging
└── util/
    ├── CommonUtil.java                # Object conversion utilities
    ├── DateTimeUtil.java              # Date/time formatting
    ├── JsonFormatter.java             # JSON parsing and formatting
    └── TraceIdUtil.java                # Trace ID management (MDC)
```

### Package Justification

#### `base.config` - Configuration Classes
- **Purpose**: Auto-configuration classes that are automatically applied
- **Why separate**: Configuration classes need to be in a package scanned by Spring
- **Components**: `BaseWebMvcConfig` - automatically registers the logging interceptor

#### `base.filter` - Servlet Filters
- **Purpose**: HTTP request/response filtering at the servlet level
- **Why separate**: Filters run before controllers and interceptors (different lifecycle)
- **Components**: `CachedBodyFilter` - allows multiple reads of request body

#### `base.interceptor` - Spring MVC Interceptors
- **Purpose**: Intercept HTTP requests at the Spring MVC level
- **Why separate**: Different execution point than filters, access to HandlerMethod
- **Components**: 
  - `ControllerLoggingInterceptor` - base class for logging
  - `BaseControllerLoggingInterceptor` - ready-to-use component

#### `base.logging` - Logging Utilities
- **Purpose**: Structured logging utilities and helpers
- **Why separate**: Centralized logging format, reusable across services
- **Components**: Logging utilities for structured JSON logging with trace IDs

#### `base.util` - Utility Classes
- **Purpose**: General-purpose utility classes
- **Why separate**: Pure utility functions, no Spring dependencies
- **Components**: JSON, date/time, object conversion utilities

## Features

### Automatic Logging

When the base package is present, logging is **automatically enabled**:

1. **Controller Logging**: All HTTP requests/responses are automatically logged with:
   - Trace IDs for request correlation
   - Request/response bodies (formatted as JSON)
   - Headers
   - Response times
   - Error details

2. **Feign Client Logging**: All Feign client calls are logged with:
   - Trace IDs (correlated with controller requests)
   - Request/response bodies
   - HTTP status codes

### Structured Logging Format

All logs follow a consistent JSON structure:

```json
{
  "trace_id": "uuid-here",
  "timestamp": "2025-01-01T12:00:00.000Z",
  "service.name": "eleanor-service-artajasa",
  "service.namespace": "ControllerName",
  "operation.name": "Request",
  "message": {
    "method": "POST",
    "url": "/v1/qris/payment/credit",
    "body": { ... }
  },
  "response.code": "200"
}
```

## Usage

### For Existing Services (Artajasa)

The base package is already integrated. No additional configuration needed!

### For New Services

To use the base package in a new service:

1. **Copy the entire `base/` directory** to your new service:
   ```
   src/main/java/com/gpay/eleanor/base/
   ```

2. **Initialize service name** in your main application class:
   ```java
   @SpringBootApplication
   public class MyServiceApplication {
       public static void main(String[] args) {
           LoggingUtil.setServiceName("my-service-name");
           SpringApplication.run(MyServiceApplication.class, args);
       }
   }
   ```

3. **Create CachedBodyFilter** (if needed):
   ```java
   package com.gpay.eleanor.filter;
   
   import com.gpay.eleanor.base.filter.CachedBodyFilter;
   import org.springframework.core.annotation.Order;
   import org.springframework.stereotype.Component;
   
   @Component
   @Order(1)
   public class CachedBodyFilter extends com.gpay.eleanor.base.filter.CachedBodyFilter {
       // Service-specific implementation if needed
   }
   ```

4. **Configure Feign Logging** (if using Feign clients):
   ```java
   public class MyFeignConfig {
       @Bean
       public Logger feignLogger() {
           return new com.gpay.eleanor.base.logging.FeignLoggingLogger();
       }
       
       @Bean
       public Logger.Level feignLoggerLevel() {
           return Logger.Level.FULL;
       }
   }
   ```

That's it! Logging is automatically enabled via `BaseWebMvcConfig`.

## Customization

### Custom Interceptor

If you need service-specific logging behavior:

```java
@Component
public class MyServiceLoggingInterceptor extends BaseControllerLoggingInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Custom logic before request processing
        return super.preHandle(request, response, handler);
    }
}
```

Then create a custom `WebMvcConfig`:

```java
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final MyServiceLoggingInterceptor interceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
```

**Note**: If you create a custom `WebMvcConfig`, you may want to exclude `BaseWebMvcConfig` from auto-scanning, or ensure your interceptor extends `BaseControllerLoggingInterceptor` to maintain base functionality.

### Custom Filter

If you need service-specific filtering:

```java
@Component
@Order(1)
public class MyCachedBodyFilter extends com.gpay.eleanor.base.filter.CachedBodyFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        // Custom logic
        super.doFilterInternal(request, response, filterChain);
    }
}
```

## Design Principles

1. **No @Component in Base Classes**: Base classes are designed to be extended, not auto-scanned
2. **Auto-Configuration**: `BaseWebMvcConfig` automatically registers the interceptor
3. **Copyable**: Entire `base/` package can be copied to new projects
4. **Extensible**: Services can extend base classes for customization
5. **Consistent**: Same logging format across all services

## API Endpoints

### QRIS Payment Credit
- **POST** `/v1/qris/payment/credit` - Process QRIS payment

### QRIS Check Status
- **POST** `/v1/qris/checkstatus` - Check payment status

### QRIS Refund
- **POST** `/v1/qris/refund` - Process refund

### Signature Generation
- **POST** `/v1/signature/generate` - Generate HMAC signature

## Configuration

### Application Properties

Key configuration files:
- `application.yml` - Base configuration
- `application-default.yml` - Default profile
- `application-development.yml` - Development profile
- `application-local.yml` - Local development profile

### Feign Clients

- **ArtajasaClient**: External Artajasa payment gateway
- **B2BTransactionClient**: Internal B2B transaction service

Both use structured logging via `FeignLoggingLogger`.

## Development

### Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL (for data storage)
- Eureka Server (for service discovery)

### Running Locally

```bash
mvn spring-boot:run
```

### Building

```bash
mvn clean package
```

## Dependencies

- Spring Boot 3.x
- Spring Cloud (Eureka, Feign)
- PostgreSQL
- Lombok
- Swagger/OpenAPI

## Logging

All logs use structured JSON format with:
- Trace IDs for request correlation
- Service name identification
- Request/response details
- Error information

Logs can be easily parsed and analyzed by log aggregation tools like ELK, Splunk, etc.

## Contributing

When adding new features:
1. Follow the base package structure
2. Use structured logging for all operations
3. Ensure trace IDs are propagated through Feign calls
4. Update this README if adding new base components

## License

[Add your license here]
