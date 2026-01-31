-- Account Service Tables
-- Bank accounts table
CREATE TABLE IF NOT EXISTS account.bank_accounts (
    id VARCHAR(255) PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_name VARCHAR(255) NOT NULL,
    bank_code VARCHAR(10) NOT NULL,
    bank_name VARCHAR(255) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(18, 2) DEFAULT 0.00,
    currency VARCHAR(3) DEFAULT 'IDR',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Account balance history (optional, for audit trail)
CREATE TABLE IF NOT EXISTS account.account_balance_history (
    id VARCHAR(255) PRIMARY KEY,
    account_id VARCHAR(255) NOT NULL,
    balance DECIMAL(18, 2) NOT NULL,
    transaction_type VARCHAR(50),
    reference_number VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_balance_history_account FOREIGN KEY (account_id) REFERENCES account.bank_accounts(id) ON DELETE CASCADE
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_bank_accounts_account_number ON account.bank_accounts(account_number);
CREATE INDEX IF NOT EXISTS idx_bank_accounts_bank_code ON account.bank_accounts(bank_code);
CREATE INDEX IF NOT EXISTS idx_bank_accounts_status ON account.bank_accounts(status);
CREATE INDEX IF NOT EXISTS idx_balance_history_account_id ON account.account_balance_history(account_id);
CREATE INDEX IF NOT EXISTS idx_balance_history_created_at ON account.account_balance_history(created_at);

-- Grant privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA account TO app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA account TO app_user;
