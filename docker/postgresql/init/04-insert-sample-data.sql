-- Insert sample partners for authentication service
INSERT INTO authentication.partners (id, partner_code, partner_name, client_secret, api_key, active, created_by, created_at, updated_at)
VALUES 
    ('partner-002', 'merchant-x', 'Merchant X Partner', 'merchant-x-secret-key-123', 'merchant-x-api-key-456', true, 'system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (partner_code) DO NOTHING;

-- Insert sample bank accounts for account service
INSERT INTO account.bank_accounts (id, account_number, account_name, bank_code, bank_name, account_type, balance, currency, status, created_at, updated_at)
VALUES 
    ('acc-001', '1234567890', 'Main Operating Account', 'BCA', 'Bank Central Asia', 'CURRENT', 1000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-002', '9876543210', 'Savings Account', 'MANDIRI', 'Bank Mandiri', 'SAVINGS', 500000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-003', '5555555555', 'Investment Account', 'BNI', 'Bank Negara Indonesia', 'INVESTMENT', 2500000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (account_number) DO NOTHING;
