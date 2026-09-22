-- ==========================================================
-- Chinook Music Store Microservices - Database Initialization
-- Creates the 5 decoupled databases for the microservices
-- ==========================================================

USE master;
GO

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_catalog')
BEGIN
    CREATE DATABASE chinook_catalog;
    PRINT 'Database chinook_catalog created.';
END
ELSE
    PRINT 'Database chinook_catalog already exists.';
GO

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_customer')
BEGIN
    CREATE DATABASE chinook_customer;
    PRINT 'Database chinook_customer created.';
END
ELSE
    PRINT 'Database chinook_customer already exists.';
GO

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_invoice')
BEGIN
    CREATE DATABASE chinook_invoice;
    PRINT 'Database chinook_invoice created.';
END
ELSE
    PRINT 'Database chinook_invoice already exists.';
GO

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_payment')
BEGIN
    CREATE DATABASE chinook_payment;
    PRINT 'Database chinook_payment created.';
END
ELSE
    PRINT 'Database chinook_payment already exists.';
GO

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_library')
BEGIN
    CREATE DATABASE chinook_library;
    PRINT 'Database chinook_library created.';
END
ELSE
    PRINT 'Database chinook_library already exists.';
GO

PRINT 'All 5 Chinook microservice databases are ready for Flyway migrations.';
GO
