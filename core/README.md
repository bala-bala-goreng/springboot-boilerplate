# Core Library

Shared base utilities and components library for microservices.

## Overview

This library contains common base packages that are shared across all microservices:
- Logging utilities with structured JSON logging
- Request/response interceptors and filters
- Common utility classes
- Configuration classes

## Package Structure

```
com.boilerplate.app.base/
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
├── constant/
│   ├── DateTimeConstants.java         # Date/time constants
│   ├── LoggingConstants.java          # Logging constants
│   └── TraceConstants.java            # Trace ID constants
└── util/
    ├── CommonUtil.java                # Object conversion utilities
    ├── DateTimeUtil.java              # Date/time formatting
    ├── JsonFormatter.java             # JSON parsing and formatting
    └── TraceIdUtil.java               # Trace ID management (MDC)
```

## Usage

### Adding to a Service

Add the dependency to your service's `pom.xml`:

```xml
<dependency>
    <groupId>com.boilerplate.app</groupId>
    <artifactId>core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Building the Library

```bash
cd core
mvn clean install
```

This will install the library to your local Maven repository, making it available to other services.

### Using in Services

1. **Logging**: The base package automatically enables structured logging when included
2. **Filters**: Extend `CachedBodyFilter` in your service:
   ```java
   @Component
   @Order(1)
   public class CachedBodyFilter extends com.boilerplate.app.base.filter.CachedBodyFilter {
   }
   ```
3. **Interceptors**: The `BaseWebMvcConfig` automatically registers the logging interceptor
4. **Utilities**: Import and use utility classes as needed:
   ```java
   import com.boilerplate.app.base.logging.LoggingUtil;
   import com.boilerplate.app.base.util.CommonUtil;
   ```

## Features

- **Automatic Logging**: All HTTP requests/responses are automatically logged
- **Structured Logging**: JSON-formatted logs with trace IDs
- **Request Correlation**: Trace IDs propagate through the request lifecycle
- **Feign Client Logging**: Automatic logging for Feign client calls

## Dependencies

- Spring Boot Web
- Spring Cloud OpenFeign
- Gson (for JSON formatting)
- Lombok

## Version

Current version: 1.0.0
