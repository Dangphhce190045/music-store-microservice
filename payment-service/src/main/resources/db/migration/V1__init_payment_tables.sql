-- V1__init_payment_tables.sql
-- Initial schema for Payment Service

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='PaymentTransaction' AND xtype='U')
BEGIN
    CREATE TABLE PaymentTransaction (
        TransactionId INT IDENTITY(1,1) PRIMARY KEY,
        InvoiceId INT NOT NULL,
        CustomerId INT NOT NULL,
        Amount DECIMAL(10,2) NOT NULL,
        PaymentMethod VARCHAR(50) NOT NULL,
        Status VARCHAR(20) NOT NULL,
        ReferenceCode VARCHAR(100) NULL,
        Note NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        UpdatedAt DATETIME2 NULL
    );
    CREATE INDEX IX_PaymentTransaction_InvoiceId ON PaymentTransaction(InvoiceId);
    CREATE INDEX IX_PaymentTransaction_CustomerId ON PaymentTransaction(CustomerId);
    CREATE INDEX IX_PaymentTransaction_Status ON PaymentTransaction(Status);
END;
