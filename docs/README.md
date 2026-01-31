# API Documentation - Hoppscotch Collections

This directory contains Hoppscotch collection JSON files for testing all API endpoints in the microservices architecture.

## 🎯 Quick Start - Unified Collection

**Import the all-in-one collection:** `docs/hoppscotch.json`

This single file contains **ALL endpoints** from all services:
- ✅ OAuth2 & Partner Management (via Gateway)
- ✅ Account Service (via Gateway)
- ✅ Payment Service (via Gateway)
- ✅ Direct Service Access (Authentication, Account, Payment)
- ✅ Eureka Service Discovery
- ✅ Health & Monitoring endpoints

**Recommended:** Use the unified collection for complete API testing!

## Directory Structure

```
docs/
├── hoppscotch.json        # ⭐ UNIFIED COLLECTION - All endpoints in one file
├── authentication/
│   └── hoppscotch.json    # Authentication service endpoints (direct)
├── account/
│   └── hoppscotch.json    # Account service endpoints (direct)
├── payment/
│   └── hoppscotch.json    # Payment service endpoints (direct)
├── gateway/
│   └── hoppscotch.json    # All endpoints via API Gateway
├── eureka/
│   └── hoppscotch.json    # Eureka service discovery endpoints
└── README.md              # This file
```

## How to Use

### Import into Hoppscotch

1. **Open Hoppscotch**: Go to https://hoppscotch.io (or your local instance)

2. **Import Collection**:
   - Click on "Collections" in the sidebar
   - Click "Import" or the "+" button
   - Select "Import from File"
   - Choose the JSON file you want to import (e.g., `docs/gateway/hoppscotch.json`)

3. **Set Environment Variables** (Optional):
   - Create an environment in Hoppscotch
   - Add variables:
     - `access_token`: Your JWT token (will be set after OAuth2 token generation)
     - `partnerCode`: Partner code for testing (e.g., "TEST_PARTNER")

### Service Endpoints

#### Authentication Service (Port 8080)
- **Direct Access**: Use `docs/authentication/hoppscotch.json`
- **Base URL**: `http://localhost:8080`
- **Endpoints**:
  - OAuth2 Token Generation
  - Token Validation
  - Partner Management (CRUD)
  - Partner Activation/Deactivation

#### Account Service (Port 8081)
- **Direct Access**: Use `docs/account/hoppscotch.json`
- **Base URL**: `http://localhost:8081`
- **Endpoints**:
  - Get All Bank Accounts
  - Get Balance Summary

#### Payment Service (Port 8082)
- **Direct Access**: Use `docs/payment/hoppscotch.json`
- **Base URL**: `http://localhost:8082`
- **Endpoints**:
  - Get All Billers
  - Payment Inquiry
  - Process Payment

#### API Gateway (Port 8999)
- **Via Gateway**: Use `docs/gateway/hoppscotch.json`
- **Base URL**: `http://localhost:8999`
- **All Registered Routes**:
  - `/api/oauth/token` → Authentication Service
  - `/api/partners` → Authentication Service (requires auth)
  - `/api/accounts` → Account Service
  - `/api/payments` → Payment Service

#### Eureka Service Discovery (Port 8761)
- **Service Registry**: Use `docs/eureka/hoppscotch.json`
- **Base URL**: `http://localhost:8761`
- **Endpoints**:
  - Get All Registered Services
  - Get Specific Service Information
  - Eureka Dashboard

## Testing Workflow

### 1. Start All Services
```bash
cd docker
docker-compose up -d
```

### 2. Generate OAuth2 Token
1. Import `docs/hoppscotch.json` (unified collection) or `docs/gateway/hoppscotch.json`
2. Run "Generate OAuth2 Token" request (in "🔐 OAuth2 (via Gateway)" folder)
3. Copy the `access_token` from response
4. Set it as environment variable `access_token` in Hoppscotch

### 3. Test Authenticated Endpoints
- Use the `{{access_token}}` variable in Authorization headers
- Test partner management endpoints
- Test account and payment endpoints

### 4. Test via Gateway
- Import `docs/hoppscotch.json` (unified collection) or `docs/gateway/hoppscotch.json`
- All endpoints are routed through the gateway
- Gateway handles authentication and routing automatically
- The unified collection includes both gateway and direct access endpoints

## Environment Variables

Create these variables in Hoppscotch for easier testing:

```json
{
  "access_token": "your-jwt-token-here",
  "partnerCode": "TEST_PARTNER",
  "billerCode": "PLN",
  "customerNumber": "1234567890"
}
```

## Notes

- **OAuth2 Token**: The Basic Auth credentials in the collection are for Rintis partner (from application config)
- **Authentication**: Partner management endpoints require Bearer token authentication
- **Gateway Routes**: All `/api/**` requests go through the gateway
- **Service Discovery**: Check Eureka dashboard at http://localhost:8761 to see registered services

## Collection Details

### Authentication Service
- **OAuth2**: Token generation and validation
- **Partners**: Full CRUD operations for partner management
- **Health**: Health checks and metrics

### Account Service
- **Accounts**: List all bank accounts
- **Balance**: Get balance summary from all accounts

### Payment Service
- **Billers**: List available biller partners
- **Inquiry**: Check payment information before processing
- **Payment**: Process payment transactions

### Gateway
- **All Routes**: Complete collection of all registered endpoints
- **Organized by Service**: Endpoints grouped by backend service
- **Includes Health Checks**: Gateway health and metrics

### Eureka
- **Service Registry**: Query registered services
- **Service Discovery**: Get service instances and metadata
