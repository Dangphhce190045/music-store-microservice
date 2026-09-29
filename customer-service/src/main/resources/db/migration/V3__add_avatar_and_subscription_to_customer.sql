-- V3__add_avatar_and_subscription_to_customer.sql
-- Add missing columns AvatarUrl and SubscriptionExpiresAt to Customer table

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'AvatarUrl')
BEGIN
    ALTER TABLE Customer ADD AvatarUrl NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Customer') AND name = 'SubscriptionExpiresAt')
BEGIN
    ALTER TABLE Customer ADD SubscriptionExpiresAt DATETIME2 NULL;
END;
