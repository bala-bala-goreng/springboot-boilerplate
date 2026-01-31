# PostgreSQL Setup

This directory contains PostgreSQL Docker setup and initialization scripts for the microservices.

## Structure

- `Dockerfile` - PostgreSQL Docker image configuration
- `init/` - SQL initialization scripts that run on first container start
  - `01-init-schema.sql` - Creates schemas for authentication and account services
  - `02-init-authentication-tables.sql` - Creates tables for authentication service (partners, auth_tokens)
  - `03-init-account-tables.sql` - Creates tables for account service (bank_accounts, account_balance_history)
  - `04-insert-sample-data.sql` - Inserts sample data for testing

## Usage

The PostgreSQL container will automatically run all SQL scripts in the `init/` directory in alphabetical order when the container is first created.

## Connection Details

- **Host**: localhost (or container name in docker network)
- **Port**: 5432
- **Database**: app_db
- **Username**: app_user
- **Password**: app_password

## Schemas

- `authentication` - Tables for authentication service (partners, auth_tokens)
- `account` - Tables for account service (bank_accounts, account_balance_history)
