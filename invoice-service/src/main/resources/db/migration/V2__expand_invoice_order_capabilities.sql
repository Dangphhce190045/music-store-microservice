-- V2__expand_invoice_order_capabilities.sql
-- Expand invoice entity with enterprise order & payment tracking fields

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'InvoiceCode')
BEGIN
    ALTER TABLE Invoice ADD InvoiceCode VARCHAR(50) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'SubTotal')
BEGIN
    ALTER TABLE Invoice ADD SubTotal DECIMAL(10,2) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'DiscountAmount')
BEGIN
    ALTER TABLE Invoice ADD DiscountAmount DECIMAL(10,2) NOT NULL DEFAULT 0;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'TaxAmount')
BEGIN
    ALTER TABLE Invoice ADD TaxAmount DECIMAL(10,2) NOT NULL DEFAULT 0;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'PaymentStatus')
BEGIN
    ALTER TABLE Invoice ADD PaymentStatus VARCHAR(30) NOT NULL DEFAULT 'PAID';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'PaymentMethod')
BEGIN
    ALTER TABLE Invoice ADD PaymentMethod VARCHAR(50) NULL DEFAULT 'CREDIT_CARD';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'PaymentTransactionId')
BEGIN
    ALTER TABLE Invoice ADD PaymentTransactionId VARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'PaidAt')
BEGIN
    ALTER TABLE Invoice ADD PaidAt DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'CreatedAt')
BEGIN
    ALTER TABLE Invoice ADD CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME();
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'UpdatedAt')
BEGIN
    ALTER TABLE Invoice ADD UpdatedAt DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Invoice') AND name = 'Version')
BEGIN
    ALTER TABLE Invoice ADD Version BIGINT NOT NULL DEFAULT 0;
END;
