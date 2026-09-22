-- V1__init_customer_tables.sql
-- Initial schema for Customer Service (Employee, Customer)

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Employee' AND xtype='U')
BEGIN
    CREATE TABLE Employee (
        EmployeeId INT IDENTITY(1,1) PRIMARY KEY,
        LastName NVARCHAR(20) NOT NULL,
        FirstName NVARCHAR(20) NOT NULL,
        Title NVARCHAR(30) NULL,
        ReportsTo INT NULL,
        BirthDate DATETIME NULL,
        HireDate DATETIME NULL,
        Address NVARCHAR(70) NULL,
        City NVARCHAR(40) NULL,
        State NVARCHAR(40) NULL,
        Country NVARCHAR(40) NULL,
        PostalCode NVARCHAR(10) NULL,
        Phone NVARCHAR(24) NULL,
        Fax NVARCHAR(24) NULL,
        Email NVARCHAR(60) NULL,
        CONSTRAINT FK_Employee_ReportsTo FOREIGN KEY (ReportsTo) REFERENCES Employee(EmployeeId)
    );
END;

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Customer' AND xtype='U')
BEGIN
    CREATE TABLE Customer (
        CustomerId INT IDENTITY(1,1) PRIMARY KEY,
        FirstName NVARCHAR(40) NOT NULL,
        LastName NVARCHAR(20) NOT NULL,
        Company NVARCHAR(80) NULL,
        Address NVARCHAR(70) NULL,
        City NVARCHAR(40) NULL,
        State NVARCHAR(40) NULL,
        Country NVARCHAR(40) NULL,
        PostalCode NVARCHAR(10) NULL,
        Phone NVARCHAR(24) NULL,
        Fax NVARCHAR(24) NULL,
        Email NVARCHAR(60) NOT NULL,
        SupportRepId INT NULL,
        IsDeleted BIT NOT NULL DEFAULT 0,
        CONSTRAINT FK_Customer_SupportRep FOREIGN KEY (SupportRepId) REFERENCES Employee(EmployeeId)
    );
    CREATE INDEX IX_Customer_SupportRepId ON Customer(SupportRepId);
END;
