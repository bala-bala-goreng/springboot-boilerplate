-- Insert All Sample Data
-- This file contains comprehensive sample data for all services

-- ============================================
-- AUTHENTICATION SERVICE - Partners
-- ============================================
INSERT INTO authentication.partners (
    id, partner_code, partner_name, client_secret, api_key, 
    partner_public_key, payment_notify_url, active, created_by, created_at, updated_at
)
VALUES 
    ('partner-001', 'merchant-x', 'Merchant X Partner', 'merchant-x-secret-key-123', 'merchant-x-api-key-456', 
     NULL, 'https://merchant-x.example.com/notify', true, 'system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('partner-002', 'test-partner', 'Test Partner', 'test-secret-key-789', 'test-api-key-012', 
     NULL, 'https://test-partner.example.com/notify', true, 'system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('partner-003', 'demo-partner', 'Demo Partner', 'demo-secret-key-345', 'demo-api-key-678', 
     NULL, 'https://demo-partner.example.com/notify', true, 'system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('partner-004', 'inactive-partner', 'Inactive Partner', 'inactive-secret-key-901', 'inactive-api-key-234', 
     NULL, NULL, false, 'system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (partner_code) DO UPDATE SET
    partner_name = EXCLUDED.partner_name,
    client_secret = EXCLUDED.client_secret,
    api_key = EXCLUDED.api_key,
    active = EXCLUDED.active,
    updated_at = CURRENT_TIMESTAMP;

-- ============================================
-- ACCOUNT SERVICE - Bank Accounts
-- ============================================
INSERT INTO account.bank_accounts (
    id, account_number, account_name, bank_code, bank_name, 
    account_type, balance, currency, status, created_at, updated_at
)
VALUES 
    ('acc-001', '1234567890', 'Main Operating Account', 'BCA', 'Bank Central Asia', 
     'CURRENT', 10000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-002', '9876543210', 'Savings Account', 'MANDIRI', 'Bank Mandiri', 
     'SAVINGS', 5000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-003', '5555555555', 'Investment Account', 'BNI', 'Bank Negara Indonesia', 
     'INVESTMENT', 25000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-004', '1111111111', 'Payroll Account', 'BRI', 'Bank Rakyat Indonesia', 
     'CURRENT', 15000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-005', '2222222222', 'Emergency Fund', 'CIMB', 'CIMB Niaga', 
     'SAVINGS', 30000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-006', '3333333333', 'Project Account', 'DANAMON', 'Bank Danamon', 
     'CURRENT', 7500000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-007', '4444444444', 'Reserve Account', 'PERMATA', 'Bank Permata', 
     'SAVINGS', 20000000.00, 'IDR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('acc-008', '6666666666', 'Inactive Account', 'BCA', 'Bank Central Asia', 
     'CURRENT', 0.00, 'IDR', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (account_number) DO UPDATE SET
    account_name = EXCLUDED.account_name,
    balance = EXCLUDED.balance,
    status = EXCLUDED.status,
    updated_at = CURRENT_TIMESTAMP;

-- ============================================
-- ACCOUNT SERVICE - Account Balance History
-- ============================================
INSERT INTO account.account_balance_history (
    id, account_id, balance, transaction_type, reference_number, created_at
)
VALUES 
    ('hist-001', 'acc-001', 10000000.00, 'INITIAL', 'INIT-001', CURRENT_TIMESTAMP),
    ('hist-002', 'acc-002', 5000000.00, 'INITIAL', 'INIT-002', CURRENT_TIMESTAMP),
    ('hist-003', 'acc-003', 25000000.00, 'INITIAL', 'INIT-003', CURRENT_TIMESTAMP),
    ('hist-004', 'acc-004', 15000000.00, 'INITIAL', 'INIT-004', CURRENT_TIMESTAMP),
    ('hist-005', 'acc-005', 30000000.00, 'INITIAL', 'INIT-005', CURRENT_TIMESTAMP),
    ('hist-006', 'acc-006', 7500000.00, 'INITIAL', 'INIT-006', CURRENT_TIMESTAMP),
    ('hist-007', 'acc-007', 20000000.00, 'INITIAL', 'INIT-007', CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- ============================================
-- PAYMENT SERVICE - Billers
-- ============================================
INSERT INTO payment.billers (
    id, biller_code, biller_name, category, description, icon_url, status, created_at, updated_at
)
VALUES 
    ('biller-001', 'PLN', 'PLN (Listrik)', 'UTILITY', 'Pembayaran tagihan listrik PLN', 
     'https://example.com/icons/pln.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-002', 'PDAM', 'PDAM (Air)', 'UTILITY', 'Pembayaran tagihan air PDAM', 
     'https://example.com/icons/pdam.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-003', 'TELKOM', 'Telkom (Telepon)', 'TELECOMMUNICATION', 'Pembayaran tagihan telepon Telkom', 
     'https://example.com/icons/telkom.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-004', 'INDIHOME', 'IndiHome (Internet)', 'TELECOMMUNICATION', 'Pembayaran tagihan internet IndiHome', 
     'https://example.com/icons/indihome.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-005', 'BPJS-KES', 'BPJS Kesehatan', 'INSURANCE', 'Pembayaran iuran BPJS Kesehatan', 
     'https://example.com/icons/bpjs-kes.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-006', 'BPJS-TK', 'BPJS Ketenagakerjaan', 'INSURANCE', 'Pembayaran iuran BPJS Ketenagakerjaan', 
     'https://example.com/icons/bpjs-tk.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-007', 'PULSA', 'Pulsa Telepon', 'TELECOMMUNICATION', 'Isi ulang pulsa telepon', 
     'https://example.com/icons/pulsa.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-008', 'PAKET-DATA', 'Paket Data', 'TELECOMMUNICATION', 'Pembelian paket data internet', 
     'https://example.com/icons/paket-data.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-009', 'TV-CABLE', 'TV Kabel', 'ENTERTAINMENT', 'Pembayaran tagihan TV kabel', 
     'https://example.com/icons/tv-cable.png', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('biller-010', 'INACTIVE-BILLER', 'Inactive Biller', 'OTHER', 'Biller tidak aktif untuk testing', 
     NULL, 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (biller_code) DO UPDATE SET
    biller_name = EXCLUDED.biller_name,
    category = EXCLUDED.category,
    description = EXCLUDED.description,
    status = EXCLUDED.status,
    updated_at = CURRENT_TIMESTAMP;

-- ============================================
-- PAYMENT SERVICE - Payments
-- ============================================
INSERT INTO payment.payments (
    id, transaction_id, biller_code, customer_number, amount, currency, 
    status, payment_date, inquiry_data, payment_data, error_message, created_at, updated_at
)
VALUES 
    ('pay-001', 'TXN-20240201-001', 'PLN', '1234567890', 150000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"customerName":"John Doe","period":"2024-02"}', 
     '{"receiptNumber":"RCP-001","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-002', 'TXN-20240201-002', 'PDAM', '9876543210', 75000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"customerName":"Jane Smith","period":"2024-02"}', 
     '{"receiptNumber":"RCP-002","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-003', 'TXN-20240201-003', 'TELKOM', '5555555555', 200000.00, 'IDR', 
     'PENDING', NULL, '{"customerName":"Bob Johnson","period":"2024-02"}', 
     NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-004', 'TXN-20240201-004', 'INDIHOME', '1111111111', 300000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"customerName":"Alice Brown","period":"2024-02"}', 
     '{"receiptNumber":"RCP-004","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-005', 'TXN-20240201-005', 'BPJS-KES', '2222222222', 50000.00, 'IDR', 
     'FAILED', NULL, '{"customerName":"Charlie Wilson","period":"2024-02"}', 
     NULL, 'Insufficient balance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-006', 'TXN-20240201-006', 'PULSA', '081234567890', 100000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"phoneNumber":"081234567890","provider":"TELKOMSEL"}', 
     '{"receiptNumber":"RCP-006","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-007', 'TXN-20240201-007', 'PAKET-DATA', '081234567890', 50000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"phoneNumber":"081234567890","package":"5GB"}', 
     '{"receiptNumber":"RCP-007","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-008', 'TXN-20240201-008', 'TV-CABLE', '3333333333', 250000.00, 'IDR', 
     'PENDING', NULL, '{"customerName":"David Lee","period":"2024-02"}', 
     NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-009', 'TXN-20240201-009', 'PLN', '4444444444', 180000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"customerName":"Emma Davis","period":"2024-02"}', 
     '{"receiptNumber":"RCP-009","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('pay-010', 'TXN-20240201-010', 'BPJS-TK', '6666666666', 100000.00, 'IDR', 
     'COMPLETED', CURRENT_TIMESTAMP, '{"customerName":"Frank Miller","period":"2024-02"}', 
     '{"receiptNumber":"RCP-010","paymentMethod":"BANK_TRANSFER"}', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (transaction_id) DO UPDATE SET
    status = EXCLUDED.status,
    payment_date = EXCLUDED.payment_date,
    payment_data = EXCLUDED.payment_data,
    error_message = EXCLUDED.error_message,
    updated_at = CURRENT_TIMESTAMP;
