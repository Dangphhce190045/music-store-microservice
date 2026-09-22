-- V1__init_library_tables.sql
-- Initial schema for Library & Entitlement Service

-- Entitlement Table (Proof of ownership for purchased music)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Entitlement' AND xtype='U')
BEGIN
    CREATE TABLE Entitlement (
        EntitlementId INT IDENTITY(1,1) PRIMARY KEY,
        CustomerId INT NOT NULL,
        TrackId INT NOT NULL,
        InvoiceId INT NOT NULL,
        GrantedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
    CREATE INDEX IX_Entitlement_CustomerId ON Entitlement(CustomerId);
    CREATE INDEX IX_Entitlement_TrackId ON Entitlement(TrackId);
    CREATE INDEX IX_Entitlement_InvoiceId ON Entitlement(InvoiceId);
END;

-- UserPlaylist Table
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserPlaylist' AND xtype='U')
BEGIN
    CREATE TABLE UserPlaylist (
        PlaylistId INT IDENTITY(1,1) PRIMARY KEY,
        CustomerId INT NOT NULL,
        Name NVARCHAR(120) NOT NULL,
        Description NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
    CREATE INDEX IX_UserPlaylist_CustomerId ON UserPlaylist(CustomerId);
END;

-- UserPlaylistTrack Table (Many-to-many relationship)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserPlaylistTrack' AND xtype='U')
BEGIN
    CREATE TABLE UserPlaylistTrack (
        PlaylistId INT NOT NULL,
        TrackId INT NOT NULL,
        AddedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        CONSTRAINT PK_UserPlaylistTrack PRIMARY KEY (PlaylistId, TrackId),
        CONSTRAINT FK_UserPlaylistTrack_UserPlaylist FOREIGN KEY (PlaylistId) REFERENCES UserPlaylist(PlaylistId) ON DELETE CASCADE
    );
    CREATE INDEX IX_UserPlaylistTrack_TrackId ON UserPlaylistTrack(TrackId);
END;
