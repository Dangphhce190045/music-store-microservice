-- V1__init_invoice_tables.sql
-- Initial schema for Invoice Service (Invoice, InvoiceLine)

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Invoice' AND xtype='U')
BEGIN
    CREATE TABLE Invoice (
        InvoiceId INT IDENTITY(1,1) PRIMARY KEY,
        CustomerId INT NOT NULL,
        CustomerFirstName NVARCHAR(40) NULL,
        CustomerLastName NVARCHAR(20) NULL,
        InvoiceDate DATETIME2 NOT NULL,
        BillingAddress NVARCHAR(70) NULL,
        BillingCity NVARCHAR(40) NULL,
        BillingState NVARCHAR(40) NULL,
        BillingCountry NVARCHAR(40) NULL,
        BillingPostalCode NVARCHAR(10) NULL,
        Total DECIMAL(10,2) NOT NULL
    );
    CREATE INDEX IX_Invoice_CustomerId ON Invoice(CustomerId);
END;

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='InvoiceLine' AND xtype='U')
BEGIN
    CREATE TABLE InvoiceLine (
        InvoiceLineId INT IDENTITY(1,1) PRIMARY KEY,
        InvoiceId INT NOT NULL,
        TrackId INT NOT NULL,
        TrackName NVARCHAR(200) NULL,
        UnitPrice DECIMAL(10,2) NOT NULL,
        Quantity INT NOT NULL,
        CONSTRAINT FK_InvoiceLine_Invoice FOREIGN KEY (InvoiceId) REFERENCES Invoice(InvoiceId) ON DELETE CASCADE
    );
    CREATE INDEX IX_InvoiceLine_InvoiceId ON InvoiceLine(InvoiceId);
END;
