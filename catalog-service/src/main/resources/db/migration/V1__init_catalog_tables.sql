-- V1__init_catalog_tables.sql
-- Initial schema for Catalog Service

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Artist' AND xtype='U')
BEGIN
    CREATE TABLE Artist (
        ArtistId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(120) NULL
    );
END;

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Genre' AND xtype='U')
BEGIN
    CREATE TABLE Genre (
        GenreId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(120) NULL
    );
END;

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='MediaType' AND xtype='U')
BEGIN
    CREATE TABLE MediaType (
        MediaTypeId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(120) NULL
    );
END;

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Album' AND xtype='U')
BEGIN
    CREATE TABLE Album (
        AlbumId INT IDENTITY(1,1) PRIMARY KEY,
        Title NVARCHAR(160) NOT NULL,
        ArtistId INT NOT NULL,
        CONSTRAINT FK_Album_Artist FOREIGN KEY (ArtistId) REFERENCES Artist(ArtistId)
    );
    CREATE INDEX IX_Album_ArtistId ON Album(ArtistId);
END;

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Track' AND xtype='U')
BEGIN
    CREATE TABLE Track (
        TrackId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(200) NOT NULL,
        AlbumId INT NULL,
        MediaTypeId INT NOT NULL,
        GenreId INT NULL,
        Composer NVARCHAR(220) NULL,
        Milliseconds INT NOT NULL,
        Bytes INT NULL,
        UnitPrice DECIMAL(10,2) NOT NULL,
        IsDeleted BIT NOT NULL DEFAULT 0,
        CONSTRAINT FK_Track_Album FOREIGN KEY (AlbumId) REFERENCES Album(AlbumId),
        CONSTRAINT FK_Track_MediaType FOREIGN KEY (MediaTypeId) REFERENCES MediaType(MediaTypeId),
        CONSTRAINT FK_Track_Genre FOREIGN KEY (GenreId) REFERENCES Genre(GenreId)
    );
    CREATE INDEX IX_Track_AlbumId ON Track(AlbumId);
    CREATE INDEX IX_Track_GenreId ON Track(GenreId);
END;
