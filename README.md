# Spring Boot Microservices Boilerplate

A production-ready microservices boilerplate built with Spring Boot 3.x, Spring Cloud, and modern DevOps practices. This boilerplate provides a complete foundation for building scalable, distributed applications with service discovery, API gateway, authentication, and comprehensive monitoring.

## 🎯 What This Boilerplate Achieves

This boilerplate is designed to help you quickly build and deploy a **microservices architecture** with:

- **Service Discovery**: Automatic service registration and discovery using Netflix Eureka
- **API Gateway**: Centralized routing, authentication, and request forwarding
- **Authentication & Authorization**: Multi-tenant authentication with OAuth2 and Partner management
- **Shared Core Library**: Reusable components and utilities across services
- **Observability**: Distributed tracing (end-to-end from gateway to database), metrics collection, and monitoring dashboards
- **Containerization**: Docker-based deployment with docker-compose orchestration
- **Database Support**: PostgreSQL for all data storage
- **Production-Ready**: Health checks, structured logging, error handling, and security best practices

## 📐 Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Applications                      │
│                    (Web, Mobile, Third-party APIs)               │
└────────────────────────────┬────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API Gateway (Port 8999)                     │
│  • Request Routing & Load Balancing                              │
│  • Token Authentication & Validation                             │
│  • Header Forwarding (X-INTERNAL-PARTNER-ID)                    │
│  • Request/Response Transformation                              │
└────────────────────────────┬────────────────────────────────────┘
                              │
                              ▼
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ Authentication│    │   Account    │    │   Payment    │
│   Service     │    │   Service    │    │   Service    │
│  (Port 8080)  │    │  (Port 8081) │    │  (Port 8082) │
│              │    │              │    │              │
│ • OAuth2     │    │ • User Mgmt  │    │ • Payments   │
│ • Partners   │    │ • Accounts   │    │ • Transactions│
│              │    │ • Profiles   │    │ • Processing  │
└──────┬───────┘    └──────┬───────┘    └──────┬───────┘
       │                    │                    │
       └────────────────────┼────────────────────┘
                            │
                            ▼
              ┌─────────────────────────┐
              │   Eureka Discovery       │
              │   (Port 8761)           │
              │   • Service Registry    │
              │   • Health Monitoring    │
              └─────────────────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
        ▼                   ▼                   ▼
┌──────────────┐                        ┌──────────────┐
│  PostgreSQL  │                        │  Monitoring  │
│  (Port 5432) │                        │  Stack       │
│              │                        │              │
│ • Relational │                        │ • Prometheus │
│   Data       │                        │ • Grafana    │
│ • JPA/Hibernate│                      │ • Tempo      │
│ • Partners   │                        │              │
│ • Tokens     │                        │              │
└──────────────┘                        └──────────────┘
```

## 🏗️ Project Structure

```
springboot-boilerplate/
│
├── core/                          # Shared Core Library
│   ├── src/main/java/com/boilerplate/app/base/
│   │   ├── config/                # Base configurations
│   │   ├── filter/                # Request/response filters
│   │   ├── interceptor/           # Logging interceptors
│   │   ├── logging/               # Structured logging utilities
│   │   ├── constant/              # Shared constants
│   │   └── util/                  # Common utilities
│   └── pom.xml                    # Maven dependencies
│
├── eureka/                        # Service Discovery Server
│   ├── src/main/java/.../
│   │   └── EurekaServerApplication.java
│   └── pom.xml
│
├── gateway/                       # API Gateway Service
│   ├── src/main/java/.../
│   │   ├── config/                # Gateway configuration
│   │   ├── controller/            # Routing controller
│   │   ├── service/               # Request forwarding logic
│   │   ├── filter/                # Authentication filter
│   │   └── validator/             # Token validation strategies
│   └── pom.xml
│
├── authentication/               # Authentication Service
│   ├── src/main/java/.../
│   │   ├── controller/            # OAuth2, Partner endpoints
│   │   ├── service/               # Authentication business logic
│   │   ├── model/                 # Data models (JPA entities)
│   │   └── repository/            # JPA repositories
│   └── pom.xml
│
├── account/                       # Account Management Service
│   ├── src/main/java/.../
│   │   ├── controller/            # Account REST endpoints
│   │   ├── service/               # Account business logic
│   │   ├── model/                 # JPA entities
│   │   └── repository/             # JPA repositories
│   └── pom.xml
│
├── payment/                       # Payment Processing Service
│   ├── src/main/java/.../
│   │   ├── controller/            # Payment REST endpoints
│   │   ├── service/               # Payment business logic
│   │   ├── model/                 # JPA entities
│   │   └── repository/            # JPA repositories
│   └── pom.xml
│
├── docker/                        # Docker Configuration
│   ├── docker-compose.yml        # Service orchestration
│   ├── postgresql/               # PostgreSQL setup
│   │   ├── Dockerfile
│   │   └── init/                 # Database initialization scripts
│   ├── prometheus/               # Prometheus configuration
│   │   └── prometheus.yml
│   └── grafana/                  # Grafana dashboards
│       └── provisioning/
│
└── README.md                      # This file
```

## 🔧 Core Components

### 1. **Core Library** (`core/`)
Shared library containing common utilities and base components used across all microservices:

- **Logging**: Structured JSON logging with trace ID correlation
- **Tracing**: OpenTelemetry bridge for distributed tracing
- **Filters**: Request/response body caching and processing
- **Interceptors**: Automatic request/response logging
- **Utilities**: Common utilities for JSON, date/time, object conversion

**Usage**: All microservices depend on this library via Maven dependency.

### 2. **Eureka Service Discovery** (`eureka/`)
Netflix Eureka server that acts as a service registry:

- **Service Registration**: All microservices register themselves on startup
- **Service Discovery**: Services can discover each other by name
- **Health Monitoring**: Tracks service health status
- **Load Balancing**: Enables client-side load balancing

**Port**: `8761`  
**Dashboard**: http://localhost:8761

### 3. **API Gateway** (`gateway/`)
Central entry point for all client requests:

- **Request Routing**: Routes requests to appropriate backend services based on path patterns
- **Load Balancing**: Automatic load balancing via Spring Cloud LoadBalancer
- **Authentication**: JWT token validation for OAuth2 tokens
- **Tracing**: Automatic trace context propagation to downstream services

**Port**: `8999`  
**Key Features**:
- OAuth2 token validation
- Configurable routes via `application.yml`
- Request body caching for multiple reads
- Automatic service discovery via Eureka

### 4. **Authentication Service** (`authentication/`)
Handles all authentication and authorization:

- **OAuth2 Token Generation**: Client Credentials flow with Basic Authentication for partners
- **Partner Management**: CRUD operations for partners and API keys
- **Token Validation & Revocation**: JWT token validation and revocation
- **Multi-tenant Support**: Supports multiple partners with OAuth2 authentication

**Port**: `8080`  
**Database**: PostgreSQL  
**Key Features**:
- OAuth2 for partners (Basic Auth with JSON body)
- Partner management with integrated API keys
- JWT token generation with custom claims
- Token validation and revocation
- Database tracing enabled (JDBC spans)

### 5. **Account Service** (`account/`)
Manages user accounts and profiles:

- **User Management**: User CRUD operations
- **Account Management**: Account creation, updates, and queries
- **Profile Management**: User profile operations

**Port**: `8081`  
**Database**: PostgreSQL  
**Key Features**:
- Database tracing enabled (JDBC spans)

### 6. **Payment Service** (`payment/`)
Handles payment processing and transactions:

- **Payment Processing**: Payment transaction handling
- **Transaction Management**: Transaction creation, updates, and queries
- **Payment Gateway Integration**: Integration with external payment providers

**Port**: `8082`  
**Database**: PostgreSQL  
**Key Features**:
- Database tracing enabled (JDBC spans)

## 🔄 Request Flow Example

### Example: OAuth2 Authenticated Request

```
1. Client Request
   POST /api/account/users
   Headers:
     Authorization: Bearer <jwt-token>

2. Gateway (Port 8999)
   ├─ AuthenticationFilter validates JWT token
   ├─ Validates token via Authentication Service
   ├─ Routes to account service
   └─ Forwards request with headers

3. Backend Service (Account Service)
   ├─ Receives authenticated request
   ├─ Processes user management operation
   └─ Returns response

4. Response flows back through Gateway to Client

**Tracing**: All requests are automatically traced end-to-end:
- Gateway span (HTTP request received)
- RestTemplate span (outgoing HTTP call)
- Service span (HTTP request in service)
- Database span (JDBC query execution)
- All spans linked with same trace ID
```

## 🚀 Quick Start

### Prerequisites

- **Java 21+**
- **Maven 3.6+**
- **Docker & Docker Compose** (for containerized deployment)
- **PostgreSQL** (if running services locally)

### Option 1: Docker Compose (Recommended)

The easiest way to run the entire stack:

```bash
# Navigate to docker directory
cd docker

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Check service status
docker-compose ps
```

**Service URLs**:
- Eureka Dashboard: http://localhost:8761
- Gateway: http://localhost:8999
- Authentication Service: http://localhost:8080
- Account Service: http://localhost:8081
- Payment Service: http://localhost:8082
- Grafana: http://localhost:3000 (admin/admin)
- Prometheus: http://localhost:9090
- Grafana Tempo: Distributed tracing (via Grafana Explore)

### Option 2: Local Development

1. **Build Core Library**:
   ```bash
   cd core
   mvn clean install
   ```

2. **Start Infrastructure**:
   ```bash
   # Start PostgreSQL (or use Docker)
   docker-compose -f docker/docker-compose.yml up -d postgresql
   
   # Start Eureka
   cd eureka
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

3. **Start Services** (in separate terminals):
   ```bash
   # Authentication Service
   cd authentication
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   
   # Account Service
   cd account
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   
   # Payment Service
   cd payment
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   
   # Gateway
   cd gateway
   mvn spring-boot:run -Dspring-boot.run.profiles=default
   ```

## 📊 Monitoring & Observability

### Prometheus
- **URL**: http://localhost:9090
- **Purpose**: Metrics collection and storage
- **Metrics**: Service health, request rates, response times, error rates

### Grafana
- **URL**: http://localhost:3000
- **Credentials**: `admin` / `admin`
- **Purpose**: Visualization and dashboards
- **Features**: Pre-configured dashboards for microservices overview

### Grafana Tempo
- **Access**: Via Grafana Explore (http://localhost:3000/explore)
- **Purpose**: Distributed tracing with end-to-end span visibility
- **Features**: 
  - End-to-end request tracing (Gateway → Services → Database)
  - Database query tracing with JDBC spans
  - Automatic trace context propagation
  - Integrated with Grafana for unified observability
  - All spans visible in single trace view

### Health Checks
All services expose health endpoints:
```bash
# Service health
curl http://localhost:8080/actuator/health

# Prometheus metrics
curl http://localhost:8080/actuator/prometheus
```

## 🔐 Authentication Flow

### OAuth2 Flow (Partner Authentication)

```bash
# 1. Generate Token
curl -X POST "http://localhost:8080/api/oauth/token" \
  -H "Authorization: Basic $(echo -n 'merchant-x-api-key-456:merchant-x-secret-key-123' | base64)" \
  -H "Content-Type: application/json" \
  -d '{"grant_type":"client_credentials"}'

# 2. Use Token
curl -X GET "http://localhost:8999/api/account/users" \
  -H "Authorization: Bearer <access_token>"
```

### Partner Management

```bash
# 1. Create Partner
curl -X POST "http://localhost:8080/partners" \
  -H "Content-Type: application/json" \
  -d '{
    "partnerCode": "TEST_PARTNER",
    "partnerName": "Test Partner",
    "clientSecret": "secret-key"
  }'

# 2. Get Partner
curl -X GET "http://localhost:8080/partners/TEST_PARTNER"

# 3. Validate Token
curl -X POST "http://localhost:8080/api/oauth/token/validate" \
  -H "Content-Type: application/json" \
  -d '{"token": "<jwt-token>"}'
```

## 🗄️ Database Schema

### PostgreSQL
Used by all services (Authentication, Account, and Payment):
- **Database**: `app_db`
- **Schema**: Initialized via `docker/postgresql/init/` scripts
- **Tables**: 
  - `authentication.partners`: Partner information and API keys
  - `authentication.auth_tokens`: JWT tokens for validation and revocation
  - Account and payment related tables

## 🛠️ Technology Stack

### Core Technologies
- **Java 21**: Modern Java features and performance
- **Spring Boot 3.5.10**: Application framework
- **Spring Cloud 2025.0.0**: Microservices patterns
- **Maven**: Build and dependency management

### Spring Cloud Components
- **Netflix Eureka**: Service discovery
- **Spring Cloud LoadBalancer**: Client-side load balancing
- **Spring Cloud OpenFeign**: Declarative HTTP clients
- **Spring Cloud Config**: Configuration management (optional)

### Databases
- **PostgreSQL**: Relational database for all services (Authentication, Account, Payment)

### Security
- **JWT (JJWT 0.12.3)**: Token-based authentication
- **OAuth2**: Standard authentication protocol (Client Credentials flow)
- **Basic Authentication**: For OAuth2 token generation

### Observability
- **Micrometer**: Metrics collection
- **Prometheus**: Metrics storage
- **Grafana**: Visualization
- **Grafana Tempo**: Distributed tracing backend
- **OpenTelemetry**: OTLP protocol for trace export

### Documentation
- **SpringDoc OpenAPI**: API documentation (Swagger UI)

### Utilities
- **Lombok**: Boilerplate code reduction
- **Gson**: JSON processing

## 📝 Configuration

### Service Configuration
Each service has multiple profile configurations:
- `application.yml`: Base configuration
- `application-local.yml`: Local development
- `application-development.yml`: Development environment
- `application-default.yml`: Default/production

### Gateway Routes
Configure routes in `gateway/src/main/resources/application-default.yml`:

```yaml
gateway:
  routes:
    authentication-service:
      base-path: /auth
      service-id: eleanor-service-authentication
      strip-prefix: true
      enabled: true
      required-auth: true
```

### Environment Variables
Services can be configured via environment variables:
- `SPRING_PROFILES_ACTIVE`: Active profile
- `SPRING_DATASOURCE_URL`: Database connection
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE`: Eureka server URL

## 🧪 Testing

### Health Checks
```bash
# Check all services
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8999/actuator/health
```

### Service Discovery
```bash
# Check Eureka registry
curl http://localhost:8761/eureka/apps
```

### Gateway Routing
```bash
# Test gateway routing
curl http://localhost:8999/api/auth/partners
```

## 🔄 Adding a New Microservice

1. **Create Service Module**:
   ```bash
   mkdir new-service
   cd new-service
   # Copy structure from existing service
   ```

2. **Add Core Dependency**:
   ```xml
   <dependency>
       <groupId>com.boilerplate.app</groupId>
       <artifactId>core</artifactId>
       <version>1.0.0</version>
   </dependency>
   ```

3. **Configure Eureka Client**:
   ```yaml
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka
   ```

4. **Add to Docker Compose**:
   ```yaml
   new-service:
     build:
       context: ..
       dockerfile: new-service/Dockerfile
     ports:
       - "8083:8083"
     environment:
       - SPRING_PROFILES_ACTIVE=development
       - EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka:8761/eureka
     depends_on:
       eureka:
         condition: service_healthy
   ```

5. **Add Gateway Route**:
   ```yaml
   gateway:
     routes:
       new-service:
         base-path: /new
         service-id: eleanor-service-new
         strip-prefix: true
         enabled: true
   ```

## 📚 Additional Documentation

- **API Collection**: See `docs/hoppscotch.json` for complete API collection
- **Docker Setup**: See `docker/README.md`

## 🎯 Key Design Patterns

1. **Microservices Architecture**: Loosely coupled, independently deployable services
2. **Service Discovery**: Automatic service registration and discovery
3. **API Gateway Pattern**: Single entry point for all client requests
4. **OAuth2 Authentication**: Standard OAuth2 Client Credentials flow
5. **Shared Core Library**: DRY principle for common functionality
6. **Containerization**: Docker for consistent deployment environments

## 🔒 Security Considerations

- **JWT Token Validation**: All authenticated routes validate tokens via Authentication Service
- **OAuth2 Client Credentials**: Secure token generation using Basic Authentication
- **Partner Management**: Centralized partner and API key management
- **Database Security**: Connection strings and credentials via environment variables
- **API Documentation**: Swagger UI available for development (can be secured in production)

## 🚧 Future Enhancements

Potential improvements and extensions:
- [ ] Circuit Breaker pattern (Resilience4j)
- [ ] Message Queue integration (RabbitMQ/Kafka)
- [ ] Redis for caching and session management
- [ ] Kubernetes deployment manifests
- [ ] CI/CD pipeline configuration
- [ ] Rate limiting and throttling
- [ ] API versioning strategy

## 📄 License

This is a boilerplate template. Customize as needed for your project.

## 🤝 Contributing

This is a boilerplate project. Feel free to fork and customize for your needs.

---

**Built with ❤️ using Spring Boot and Spring Cloud**
