-- V2__expand_customer_identity_tier.sql
-- Expand Customer with IdentityId (OAuth2 Keycloak), Status, Tier, and Auditing

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'IdentityId')
BEGIN
    ALTER TABLE Customer ADD IdentityId VARCHAR(100) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'Status')
BEGIN
    ALTER TABLE Customer ADD Status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'Tier')
BEGIN
    ALTER TABLE Customer ADD Tier VARCHAR(20) NOT NULL DEFAULT 'FREE';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'CreatedAt')
BEGIN
    ALTER TABLE Customer ADD CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME();
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'UpdatedAt')
BEGIN
    ALTER TABLE Customer ADD UpdatedAt DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'Version')
BEGIN
    ALTER TABLE Customer ADD Version BIGINT NOT NULL DEFAULT 0;
END;
