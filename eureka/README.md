# Eureka Service Discovery Server

Eureka service discovery server for microservices architecture. All services register with Eureka to enable service discovery and load balancing.

## Overview

Eureka is a REST-based service registry for locating services for load balancing and failover of middle-tier servers. This Eureka server provides:

- **Service Registry**: Central registry where all microservices register themselves
- **Service Discovery**: Allows services to discover and communicate with each other
- **Health Monitoring**: Tracks the health status of registered services
- **Load Balancing**: Enables client-side load balancing via service discovery

## Features

- Standalone Eureka server (doesn't register with itself)
- Self-preservation mode enabled for production
- Health check endpoints via Spring Boot Actuator
- Configurable eviction and cache settings

## Configuration

### Application Properties

- **Port**: 8761 (default Eureka port)
- **Self-preservation**: Enabled by default (recommended for production)
- **Eviction Interval**: 60 seconds (removes unhealthy instances)

### Service Registration

All microservices should configure:
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

## Running Locally

### Using Maven
```bash
cd eureka
./mvnw spring-boot:run
```

### Using Docker
```bash
cd docker
docker-compose up eureka
```

## Accessing Eureka Dashboard

Once running, access the Eureka dashboard at:
- **URL**: http://localhost:8761
- **Dashboard**: http://localhost:8761

The dashboard shows all registered services and their instances.

## Health Check

- **Health Endpoint**: http://localhost:8761/actuator/health
- **Info Endpoint**: http://localhost:8761/actuator/info

## Service Registration

Services will automatically register with Eureka when they start if configured with:
- `spring-cloud-starter-netflix-eureka-client` dependency
- Proper Eureka client configuration

## Development

### Prerequisites
- Java 21+
- Maven 3.8+

### Building
```bash
./mvnw clean package
```

### Running Tests
```bash
./mvnw test
```

## Notes

- Eureka server should start **before** all other services
- In Docker Compose, Eureka has health check dependencies
- Self-preservation mode prevents removal of all instances if network issues occur
