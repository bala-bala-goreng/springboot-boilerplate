-- Create schemas for different services
CREATE SCHEMA IF NOT EXISTS authentication;
CREATE SCHEMA IF NOT EXISTS account;
CREATE SCHEMA IF NOT EXISTS payment;

-- Grant privileges
GRANT ALL PRIVILEGES ON SCHEMA authentication TO app_user;
GRANT ALL PRIVILEGES ON SCHEMA account TO app_user;
GRANT ALL PRIVILEGES ON SCHEMA payment TO app_user;