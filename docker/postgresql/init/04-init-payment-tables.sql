-- Payment Service Tables

-- Billers table
CREATE TABLE IF NOT EXISTS payment.billers (
    id VARCHAR(255) PRIMARY KEY,
    biller_code VARCHAR(50) UNIQUE NOT NULL,
    biller_name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    description TEXT,
    icon_url VARCHAR(500),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Payments table
CREATE TABLE IF NOT EXISTS payment.payments (
    id VARCHAR(255) PRIMARY KEY,
    transaction_id VARCHAR(100) UNIQUE NOT NULL,
    biller_code VARCHAR(50) NOT NULL,
    customer_number VARCHAR(100) NOT NULL,
    amount DECIMAL(18, 2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'IDR',
    status VARCHAR(20) DEFAULT 'PENDING',
    payment_date TIMESTAMP,
    inquiry_data TEXT,
    payment_data TEXT,
    error_message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_billers_status ON payment.billers(status);
CREATE INDEX IF NOT EXISTS idx_billers_category ON payment.billers(category);
CREATE INDEX IF NOT EXISTS idx_payments_transaction_id ON payment.payments(transaction_id);
CREATE INDEX IF NOT EXISTS idx_payments_biller_code ON payment.payments(biller_code);
CREATE INDEX IF NOT EXISTS idx_payments_status ON payment.payments(status);

-- Grant privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA payment TO app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA payment TO app_user;
