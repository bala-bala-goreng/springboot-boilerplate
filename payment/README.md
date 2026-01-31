# Payment Service

Payment service for processing biller payments, inquiries, and managing biller partners.

## Overview

This service provides payment processing functionality including:

- **Biller Management**: List of biller partners available for payment
- **Payment Inquiry**: Check payment information before processing
- **Payment Processing**: Execute payment transactions

## Endpoints

### 1. Get Billers

- **GET** `/api/payments/billers`
- **Description**: Retrieves a list of all active biller partners
- **Response**: List of `BillerResponse`

### 2. Payment Inquiry

- **POST** `/api/payments/inquiry`
- **Description**: Inquires payment information for a specific biller and customer
- **Request Body**:
  
  ```json
  {
    "billerCode": "PLN",
    "customerNumber": "1234567890"
  }
  ```
  
- **Response**: `InquiryResponse` with payment details

### 3. Process Payment

- **POST** `/api/payments/payment`
- **Description**: Processes a payment transaction
- **Request Body**:

  ```json
  {
    "billerCode": "PLN",
    "customerNumber": "1234567890",
    "amount": 100000,
    "currency": "IDR"
  }
  ```

- **Response**: `PaymentResponse` with transaction details

## Database Schema

### Billers Table

- Stores biller partner information
- Schema: `payment.billers`

### Payments Table

- Stores payment transaction records
- Schema: `payment.payments`

## Configuration

- **Port**: 8082
- **Service Name**: `service-payment`
- **Eureka Registration**: Yes
- **Database**: PostgreSQL (schema: `payment`)

## Running Locally

### Using Maven

```bash
cd payment
./mvnw spring-boot:run
```

### Using Docker

```bash
cd docker
docker-compose up payment
```

## Swagger Documentation

Once running, access Swagger UI at:
- [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

## Dependencies

- Core Library (shared base utilities)
- Spring Boot Web
- Spring Data JPA
- PostgreSQL Driver
- Eureka Client
- Swagger/OpenAPI
