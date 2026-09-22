-- V2__expand_catalog_streaming.sql
-- Expand Catalog with Streaming, Media, Auditing, ISRC, and Lyrics

-- Expand Track with streaming, status, and auditing
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'PreviewUrl')
BEGIN
    ALTER TABLE Track ADD PreviewUrl NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'StreamUrl')
BEGIN
    ALTER TABLE Track ADD StreamUrl NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'TrackNumber')
BEGIN
    ALTER TABLE Track ADD TrackNumber INT NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'DiscNumber')
BEGIN
    ALTER TABLE Track ADD DiscNumber INT NULL DEFAULT 1;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'IsrcCode')
BEGIN
    ALTER TABLE Track ADD IsrcCode VARCHAR(15) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'IsExplicit')
BEGIN
    ALTER TABLE Track ADD IsExplicit BIT NOT NULL DEFAULT 0;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'Lyrics')
BEGIN
    ALTER TABLE Track ADD Lyrics NVARCHAR(MAX) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'PlayCount')
BEGIN
    ALTER TABLE Track ADD PlayCount BIGINT NOT NULL DEFAULT 0;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'Status')
BEGIN
    ALTER TABLE Track ADD Status VARCHAR(20) NOT NULL DEFAULT 'PUBLISHED';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'CreatedAt')
BEGIN
    ALTER TABLE Track ADD CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME();
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'UpdatedAt')
BEGIN
    ALTER TABLE Track ADD UpdatedAt DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Track') AND name = 'Version')
BEGIN
    ALTER TABLE Track ADD Version BIGINT NOT NULL DEFAULT 0;
END;

-- Expand Album with cover art, release date, combo price, and auditing
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'CoverArtUrl')
BEGIN
    ALTER TABLE Album ADD CoverArtUrl NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'AlbumType')
BEGIN
    ALTER TABLE Album ADD AlbumType VARCHAR(20) NOT NULL DEFAULT 'ALBUM';
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'ReleaseDate')
BEGIN
    ALTER TABLE Album ADD ReleaseDate DATE NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'RecordLabel')
BEGIN
    ALTER TABLE Album ADD RecordLabel NVARCHAR(150) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'Price')
BEGIN
    ALTER TABLE Album ADD Price DECIMAL(10,2) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'CreatedAt')
BEGIN
    ALTER TABLE Album ADD CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME();
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'UpdatedAt')
BEGIN
    ALTER TABLE Album ADD UpdatedAt DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Album') AND name = 'Version')
BEGIN
    ALTER TABLE Album ADD Version BIGINT NOT NULL DEFAULT 0;
END;

-- Expand Artist with Image, Banner, Bio, Verification, and Country
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'ImageUrl')
BEGIN
    ALTER TABLE Artist ADD ImageUrl NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'BannerUrl')
BEGIN
    ALTER TABLE Artist ADD BannerUrl NVARCHAR(500) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'Bio')
BEGIN
    ALTER TABLE Artist ADD Bio NVARCHAR(MAX) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'IsVerified')
BEGIN
    ALTER TABLE Artist ADD IsVerified BIT NOT NULL DEFAULT 0;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'Country')
BEGIN
    ALTER TABLE Artist ADD Country NVARCHAR(50) NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'CreatedAt')
BEGIN
    ALTER TABLE Artist ADD CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME();
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'UpdatedAt')
BEGIN
    ALTER TABLE Artist ADD UpdatedAt DATETIME2 NULL;
END;

IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Artist') AND name = 'Version')
BEGIN
    ALTER TABLE Artist ADD Version BIGINT NOT NULL DEFAULT 0;
END;
