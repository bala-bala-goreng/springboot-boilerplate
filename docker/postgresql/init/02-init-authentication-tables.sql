-- Authentication Service Tables
-- Partners table
CREATE TABLE IF NOT EXISTS authentication.partners (
    id VARCHAR(255) PRIMARY KEY,
    partner_code VARCHAR(255) UNIQUE NOT NULL,
    partner_name VARCHAR(255),
    client_secret VARCHAR(255),
    partner_public_key TEXT,
    payment_notify_url VARCHAR(500),
    api_key VARCHAR(255) UNIQUE,
    public_key TEXT,
    private_key TEXT,
    api_key_expires_at TIMESTAMP,
    active BOOLEAN DEFAULT true,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Auth tokens table
CREATE TABLE IF NOT EXISTS authentication.auth_tokens (
    id VARCHAR(255) PRIMARY KEY,
    partner_id VARCHAR(255),
    token VARCHAR(500) UNIQUE NOT NULL,
    expires_at TIMESTAMP,
    revoked BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revoked_at TIMESTAMP,
    CONSTRAINT fk_auth_tokens_partner FOREIGN KEY (partner_id) REFERENCES authentication.partners(id) ON DELETE CASCADE
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_partners_partner_code ON authentication.partners(partner_code);
CREATE INDEX IF NOT EXISTS idx_partners_api_key ON authentication.partners(api_key);
CREATE INDEX IF NOT EXISTS idx_auth_tokens_token ON authentication.auth_tokens(token);
CREATE INDEX IF NOT EXISTS idx_auth_tokens_partner_id ON authentication.auth_tokens(partner_id);

-- Grant privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA authentication TO app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA authentication TO app_user;
