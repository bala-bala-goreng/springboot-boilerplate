-- Insert merchant-x partner (can be run manually if database already exists)
INSERT INTO authentication.partners (id, partner_code, partner_name, client_secret, api_key, active, created_by, created_at, updated_at)
VALUES 
    ('partner-002', 'merchant-x', 'Merchant X Partner', 'merchant-x-secret-key-123', 'merchant-x-api-key-456', true, 'system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (partner_code) DO UPDATE SET
    partner_name = EXCLUDED.partner_name,
    client_secret = EXCLUDED.client_secret,
    api_key = EXCLUDED.api_key,
    active = EXCLUDED.active,
    updated_at = CURRENT_TIMESTAMP;
