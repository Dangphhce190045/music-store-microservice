-- =============================================================================
-- Chinook Music Store Microservices (MSS) - Full Enterprise Seed Data
-- Tự động tạo và nạp đầy đủ dữ liệu mẫu phong phú cho cả 5 Microservices:
-- 1. chinook_catalog (Artist, Genre, MediaType, Album, Track)
-- 2. chinook_customer (Employee, Customer)
-- 3. chinook_invoice (Invoice, InvoiceLine)
-- 4. chinook_payment (PaymentTransaction)
-- 5. chinook_library (Entitlement, UserPlaylist, UserPlaylistTrack)
-- =============================================================================

USE master;
GO

-- =============================================================================
-- 1. DATABASE: chinook_catalog
-- =============================================================================
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_catalog')
    CREATE DATABASE chinook_catalog;
GO

USE chinook_catalog;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Artist' AND xtype='U')
    CREATE TABLE Artist (
        ArtistId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(120) NULL
    );
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Genre' AND xtype='U')
    CREATE TABLE Genre (
        GenreId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(120) NULL
    );
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='MediaType' AND xtype='U')
    CREATE TABLE MediaType (
        MediaTypeId INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(120) NULL
    );
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Album' AND xtype='U')
    CREATE TABLE Album (
        AlbumId INT IDENTITY(1,1) PRIMARY KEY,
        Title NVARCHAR(160) NOT NULL,
        ArtistId INT NOT NULL,
        CONSTRAINT FK_Album_Artist FOREIGN KEY (ArtistId) REFERENCES Artist(ArtistId)
    );
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Track' AND xtype='U')
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
        PreviewUrl NVARCHAR(500) NULL,
        StreamUrl NVARCHAR(500) NULL,
        TrackNumber INT NULL,
        DiscNumber INT NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        UpdatedAt DATETIME2 NULL,
        Version BIGINT NOT NULL DEFAULT 0,
        CONSTRAINT FK_Track_Album FOREIGN KEY (AlbumId) REFERENCES Album(AlbumId),
        CONSTRAINT FK_Track_MediaType FOREIGN KEY (MediaTypeId) REFERENCES MediaType(MediaTypeId),
        CONSTRAINT FK_Track_Genre FOREIGN KEY (GenreId) REFERENCES Genre(GenreId)
    );
GO

-- Seed: Artist (25 Popular Artists)
IF NOT EXISTS (SELECT 1 FROM Artist)
BEGIN
    SET IDENTITY_INSERT Artist ON;
    INSERT INTO Artist (ArtistId, Name) VALUES
    (1, N'AC/DC'),
    (2, N'Accept'),
    (3, N'Aerosmith'),
    (4, N'Alanis Morissette'),
    (5, N'Alice In Chains'),
    (6, N'Antônio Carlos Jobim'),
    (7, N'Apocalyptica'),
    (8, N'Audioslave'),
    (9, N'BackBeat'),
    (10, N'Billy Cobham'),
    (11, N'Black Sabbath'),
    (12, N'Deep Purple'),
    (13, N'Eric Clapton'),
    (14, N'Guns N'' Roses'),
    (15, N'Iron Maiden'),
    (16, N'Jimi Hendrix'),
    (17, N'Judas Priest'),
    (18, N'Led Zeppelin'),
    (19, N'Metallica'),
    (20, N'Nirvana'),
    (21, N'Pink Floyd'),
    (22, N'Queen'),
    (23, N'Red Hot Chili Peppers'),
    (24, N'The Beatles'),
    (25, N'The Rolling Stones');
    SET IDENTITY_INSERT Artist OFF;
END;
GO

-- Seed: Genre (15 Popular Genres)
IF NOT EXISTS (SELECT 1 FROM Genre)
BEGIN
    SET IDENTITY_INSERT Genre ON;
    INSERT INTO Genre (GenreId, Name) VALUES
    (1, N'Rock'),
    (2, N'Jazz'),
    (3, N'Metal'),
    (4, N'Alternative & Punk'),
    (5, N'Rock And Roll'),
    (6, N'Blues'),
    (7, N'Latin'),
    (8, N'Reggae'),
    (9, N'Pop'),
    (10, N'Soundtrack'),
    (11, N'Bossa Nova'),
    (12, N'Easy Listening'),
    (13, N'Heavy Metal'),
    (14, N'R&B/Soul'),
    (15, N'Classical');
    SET IDENTITY_INSERT Genre OFF;
END;
GO

-- Seed: MediaType (5 Standard Formats)
IF NOT EXISTS (SELECT 1 FROM MediaType)
BEGIN
    SET IDENTITY_INSERT MediaType ON;
    INSERT INTO MediaType (MediaTypeId, Name) VALUES
    (1, N'MPEG audio file'),
    (2, N'Protected AAC audio file'),
    (3, N'Protected MPEG-4 video file'),
    (4, N'Purchased AAC audio file'),
    (5, N'AAC audio file');
    SET IDENTITY_INSERT MediaType OFF;
END;
GO

-- Seed: Album (20 Classic Albums)
IF NOT EXISTS (SELECT 1 FROM Album)
BEGIN
    SET IDENTITY_INSERT Album ON;
    INSERT INTO Album (AlbumId, Title, ArtistId) VALUES
    (1, N'For Those About To Rock We Salute You', 1),
    (2, N'Balls to the Wall', 2),
    (3, N'Restless and Wild', 2),
    (4, N'Let There Be Rock', 1),
    (5, N'Big Ones', 3),
    (6, N'Jagged Little Pill', 4),
    (7, N'Facelift', 5),
    (8, N'Warner 25 Anos', 6),
    (9, N'Plays Metallica By Four Cellos', 7),
    (10, N'Audioslave', 8),
    (11, N'Paranoid', 11),
    (12, N'Machine Head', 12),
    (13, N'Unplugged', 13),
    (14, N'Appetite for Destruction', 14),
    (15, N'The Number of the Beast', 15),
    (16, N'Are You Experienced?', 16),
    (17, N'Led Zeppelin IV', 18),
    (18, N'Master of Puppets', 19),
    (19, N'Nevermind', 20),
    (20, N'A Night at the Opera', 22);
    SET IDENTITY_INSERT Album OFF;
END;
GO

-- Seed: Track (50 Classic Tracks)
IF NOT EXISTS (SELECT 1 FROM Track)
BEGIN
    SET IDENTITY_INSERT Track ON;
    INSERT INTO Track (TrackId, Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice, IsDeleted) VALUES
    (1, N'For Those About To Rock (We Salute You)', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 343719, 11170334, 0.99, 0),
    (2, N'Balls to the Wall', 2, 2, 1, N'U. Dirkschneider, W. Hoffmann, H. Frank, P. Baltes', 342562, 5510424, 0.99, 0),
    (3, N'Fast As a Shark', 3, 2, 1, N'F. Baltes, S. Kaufman, U. Dirkschneider & W. Hoffmann', 230619, 3990994, 0.99, 0),
    (4, N'Restless and Wild', 3, 2, 1, N'F. Baltes, R.A. Smith-Diesel, S. Kaufman, U. Dirkschneider & W. Hoffmann', 252051, 4331779, 0.99, 0),
    (5, N'Princess of the Dawn', 3, 2, 1, N'Deaffy & R.A. Smith-Diesel', 375418, 6290521, 0.99, 0),
    (6, N'Put The Finger On You', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 205662, 6713451, 0.99, 0),
    (7, N'Let''s Get It Up', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 233926, 7636561, 0.99, 0),
    (8, N'Inject The Venom', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 210834, 6852060, 0.99, 0),
    (9, N'Snowballed', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 203102, 6599424, 0.99, 0),
    (10, N'Evil Walks', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 263497, 8611245, 0.99, 0),
    (11, N'C.O.D.', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 199836, 6566314, 0.99, 0),
    (12, N'Breaking The Rules', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 263288, 8596840, 0.99, 0),
    (13, N'Night Of The Long Knives', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 205688, 6706347, 0.99, 0),
    (14, N'Spellbound', 1, 1, 1, N'Angus Young, Malcolm Young, Brian Johnson', 270863, 8817038, 0.99, 0),
    (15, N'Go Down', 4, 1, 1, N'AC/DC', 331180, 10847611, 0.99, 0),
    (16, N'Dog Eat Dog', 4, 1, 1, N'AC/DC', 215196, 7032162, 0.99, 0),
    (17, N'Let There Be Rock', 4, 1, 1, N'AC/DC', 366654, 12021261, 0.99, 0),
    (18, N'Bad Boy Boogie', 4, 1, 1, N'AC/DC', 267720, 8776140, 0.99, 0),
    (19, N'Problem Child', 4, 1, 1, N'AC/DC', 325041, 10617116, 0.99, 0),
    (20, N'Overdose', 4, 1, 1, N'AC/DC', 369319, 12066294, 0.99, 0),
    (21, N'Hell Ain''t A Bad Place To Be', 4, 1, 1, N'AC/DC', 254380, 8331286, 0.99, 0),
    (22, N'Whole Lotta Rosie', 4, 1, 1, N'AC/DC', 323735, 10576974, 0.99, 0),
    (23, N'Walk On Water', 5, 1, 1, N'Steven Tyler, Joe Perry, Jack Blades, Tommy Shaw', 295680, 9719579, 0.99, 0),
    (24, N'Love In An Elevator', 5, 1, 1, N'Steven Tyler, Joe Perry', 338181, 11055734, 0.99, 0),
    (25, N'Rag Doll', 5, 1, 1, N'Steven Tyler, Joe Perry, Jim Vallance, Holly Knight', 264698, 8675345, 0.99, 0),
    (26, N'What It Takes', 5, 1, 1, N'Steven Tyler, Joe Perry, Desmond Child', 311066, 10144730, 0.99, 0),
    (27, N'Dude (Looks Like A Lady)', 5, 1, 1, N'Steven Tyler, Joe Perry, Desmond Child', 265534, 8679945, 0.99, 0),
    (28, N'Janie''s Got A Gun', 5, 1, 1, N'Steven Tyler, Tom Hamilton', 330736, 10869391, 0.99, 0),
    (29, N'Cryin''', 5, 1, 1, N'Steven Tyler, Joe Perry, Taylor Rhodes', 309263, 10056995, 0.99, 0),
    (30, N'Crazy', 5, 1, 1, N'Steven Tyler, Joe Perry, Desmond Child', 316626, 10402392, 0.99, 0),
    (31, N'You Oughta Know', 6, 1, 4, N'Alanis Morissette & Glenn Ballard', 249234, 8196916, 0.99, 0),
    (32, N'Hand In My Pocket', 6, 1, 4, N'Alanis Morissette & Glenn Ballard', 221570, 7224285, 0.99, 0),
    (33, N'Ironic', 6, 1, 4, N'Alanis Morissette & Glenn Ballard', 229799, 7597087, 0.99, 0),
    (34, N'You Learn', 6, 1, 4, N'Alanis Morissette & Glenn Ballard', 239660, 7824837, 0.99, 0),
    (35, N'Head Over Feet', 6, 1, 4, N'Alanis Morissette & Glenn Ballard', 267493, 8758042, 0.99, 0),
    (36, N'Man In The Box', 7, 1, 3, N'Jerry Cantrell, Layne Staley', 286641, 9310272, 0.99, 0),
    (37, N'We Die Young', 7, 1, 3, N'Jerry Cantrell', 152084, 4970834, 0.99, 0),
    (38, N'Bleed The Freak', 7, 1, 3, N'Jerry Cantrell', 241502, 7847777, 0.99, 0),
    (39, N'Sea Of Sorrow', 7, 1, 3, N'Jerry Cantrell', 349857, 11316510, 0.99, 0),
    (40, N'Enter Sandman', 9, 1, 3, N'Kirk Hammett, James Hetfield, Lars Ulrich', 221701, 7255148, 0.99, 0),
    (41, N'Master of Puppets', 18, 1, 3, N'Cliff Burton, Kirk Hammett, James Hetfield, Lars Ulrich', 515239, 16824050, 0.99, 0),
    (42, N'Battery', 18, 1, 3, N'James Hetfield, Lars Ulrich', 312424, 10189033, 0.99, 0),
    (43, N'Welcome to the Jungle', 14, 1, 1, N'Axl Rose, Slash, Izzy Stradlin, Duff McKagan, Steven Adler', 273397, 8931120, 0.99, 0),
    (44, N'Sweet Child O'' Mine', 14, 1, 1, N'Axl Rose, Slash, Izzy Stradlin, Duff McKagan, Steven Adler', 356075, 11624789, 0.99, 0),
    (45, N'Paradise City', 14, 1, 1, N'Axl Rose, Slash, Izzy Stradlin, Duff McKagan, Steven Adler', 406334, 13247012, 0.99, 0),
    (46, N'Smells Like Teen Spirit', 19, 1, 4, N'Kurt Cobain, Krist Novoselic, Dave Grohl', 301244, 9845211, 0.99, 0),
    (47, N'Come As You Are', 19, 1, 4, N'Kurt Cobain', 219219, 7156900, 0.99, 0),
    (48, N'Stairway to Heaven', 17, 1, 1, N'Jimmy Page, Robert Plant', 482873, 15729110, 0.99, 0),
    (49, N'Smoke on the Water', 12, 1, 1, N'Ritchie Blackmore, Ian Gillan, Roger Glover, Jon Lord, Ian Paice', 340793, 11119280, 0.99, 0),
    (50, N'Bohemian Rhapsody', 20, 1, 1, N'Freddie Mercury', 354320, 11578321, 0.99, 0);
    SET IDENTITY_INSERT Track OFF;
END;
GO

-- =============================================================================
-- 2. DATABASE: chinook_customer
-- =============================================================================
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_customer')
    CREATE DATABASE chinook_customer;
GO

USE chinook_customer;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Employee' AND xtype='U')
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
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Customer' AND xtype='U')
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
        IdentityId VARCHAR(100) NULL,
        Status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        Tier VARCHAR(20) NOT NULL DEFAULT 'FREE',
        SubscriptionExpiresAt DATETIME2 NULL,
        AvatarUrl NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        UpdatedAt DATETIME2 NULL,
        Version BIGINT NOT NULL DEFAULT 0,
        CONSTRAINT FK_Customer_SupportRep FOREIGN KEY (SupportRepId) REFERENCES Employee(EmployeeId)
    );
GO

-- Seed: Employee (Standard 8 Chinook Employees)
IF NOT EXISTS (SELECT 1 FROM Employee)
BEGIN
    SET IDENTITY_INSERT Employee ON;
    INSERT INTO Employee (EmployeeId, LastName, FirstName, Title, ReportsTo, BirthDate, HireDate, Address, City, State, Country, PostalCode, Phone, Email) VALUES
    (1, N'Adams', N'Andrew', N'General Manager', NULL, '1962-02-18', '2002-08-14', N'11120 Jasper Ave NW', N'Edmonton', N'AB', N'Canada', N'T5K 2N1', N'+1 (780) 428-9482', N'andrew@chinookcorp.com'),
    (2, N'Edwards', N'Nancy', N'Sales Manager', 1, '1958-12-08', '2002-05-01', N'825 8 Ave SW', N'Calgary', N'AB', N'Canada', N'T2P 2T3', N'+1 (403) 262-3443', N'nancy@chinookcorp.com'),
    (3, N'Peacock', N'Jane', N'Sales Support Agent', 2, '1973-08-29', '2002-04-01', N'1111 6 Ave SW', N'Calgary', N'AB', N'Canada', N'T2P 5M5', N'+1 (403) 262-3443', N'jane@chinookcorp.com'),
    (4, N'Park', N'Margaret', N'Sales Support Agent', 2, '1947-09-19', '2003-05-03', N'683 10 Street SW', N'Calgary', N'AB', N'Canada', N'T2P 5G3', N'+1 (403) 263-4423', N'margaret@chinookcorp.com'),
    (5, N'Johnson', N'Steve', N'Sales Support Agent', 2, '1965-03-03', '2003-10-17', N'7727B 41 Ave', N'Calgary', N'AB', N'Canada', N'T3B 1Y7', N'+1 (403) 246-9887', N'steve@chinookcorp.com'),
    (6, N'Mitchell', N'Michael', N'IT Manager', 1, '1973-07-01', '2003-10-17', N'5827 Bowness Road NW', N'Calgary', N'AB', N'Canada', N'T3B 0C5', N'+1 (403) 246-9887', N'michael@chinookcorp.com'),
    (7, N'King', N'Robert', N'IT Staff', 6, '1970-05-29', '2004-01-02', N'590 Columbia Boulevard West', N'Lethbridge', N'AB', N'Canada', N'T1K 5N8', N'+1 (403) 456-9986', N'robert@chinookcorp.com'),
    (8, N'Callahan', N'Laura', N'IT Staff', 6, '1968-01-09', '2004-03-04', N'923 13 Ave SW', N'Calgary', N'AB', N'Canada', N'T2R 0V5', N'+1 (403) 467-3351', N'laura@chinookcorp.com');
    SET IDENTITY_INSERT Employee OFF;
END;
GO

-- Seed: Customer (15 Global Customers)
IF NOT EXISTS (SELECT 1 FROM Customer)
BEGIN
    SET IDENTITY_INSERT Customer ON;
    INSERT INTO Customer (CustomerId, FirstName, LastName, Company, Address, City, State, Country, PostalCode, Phone, Email, SupportRepId, Status, Tier) VALUES
    (1, N'Luís', N'Gonçalves', N'Embraer - Empresa Brasileira de Aeronáutica S.A.', N'Av. Brigadeiro Faria Lima, 2170', N'São José dos Campos', N'SP', N'Brazil', N'12227-000', N'+55 (12) 3923-5555', N'luisg@embraer.com.br', 3, 'ACTIVE', 'GOLD'),
    (2, N'Leonie', N'Köhler', NULL, N'Theodor-Heuss-Straße 34', N'Stuttgart', NULL, N'Germany', N'70174', N'+49 0711 2842222', N'leonekohler@surfeu.de', 5, 'ACTIVE', 'FREE'),
    (3, N'François', N'Tremblay', NULL, N'1498 rue Bélanger', N'Montréal', N'QC', N'Canada', N'H2G 1A7', N'+1 (514) 721-4711', N'ftremblay@gmail.com', 3, 'ACTIVE', 'FREE'),
    (4, N'Bjørn', N'Hansen', NULL, N'Ullevålsveien 14', N'Oslo', NULL, N'Norway', N'0171', N'+47 22 12 13 14', N'bjorn.hansen@yahoo.no', 4, 'ACTIVE', 'PREMIUM'),
    (5, N'František', N'Wichterlová', N'JetBrains s.r.o.', N'Klanova 9/506', N'Prague', NULL, N'Czech Republic', N'14700', N'+420 2 4172 5555', N'frantisekw@seznam.cz', 4, 'ACTIVE', 'FREE'),
    (6, N'Helena', N'Holý', NULL, N'Rilská 3174/6', N'Prague', NULL, N'Czech Republic', N'14300', N'+420 2 4177 0449', N'hholy@gmail.com', 5, 'ACTIVE', 'FREE'),
    (7, N'Astrid', N'Gruber', NULL, N'Rotenturmstraße 4, 1010 Innere Stadt', N'Vienne', NULL, N'Austria', N'1010', N'+43 01 5134559', N'astrid.gruber@apple.at', 5, 'ACTIVE', 'GOLD'),
    (8, N'Daan', N'Peeters', NULL, N'Grétrystraat 63', N'Brussels', NULL, N'Belgium', N'1000', N'+32 02 219 03 62', N'daan_peeters@apple.be', 4, 'ACTIVE', 'FREE'),
    (9, N'Kara', N'Nielsen', NULL, N'Sønder Boulevard 51', N'Copenhagen', NULL, N'Denmark', N'1720', N'+45 3336 6360', N'kara.nielsen@jubii.dk', 4, 'ACTIVE', 'FREE'),
    (10, N'Eduardo', N'Martins', N'Woodstock Discos', N'Rua Dr. Falcão Filho, 155', N'São Paulo', N'SP', N'Brazil', N'01007-010', N'+55 (11) 3033-5446', N'eduardo@woodstock.com.br', 4, 'ACTIVE', 'PREMIUM'),
    (11, N'Alexandre', N'Rocha', N'Banco do Brasil S.A.', N'Av. Paulista, 2022', N'São Paulo', N'SP', N'Brazil', N'01310-200', N'+55 (11) 3055-3278', N'alero@uol.com.br', 5, 'ACTIVE', 'FREE'),
    (12, N'Roberto', N'Almeida', N'Riotur', N'Praça Pio X, 119', N'Rio de Janeiro', N'RJ', N'Brazil', N'20040-020', N'+55 (21) 2271-7000', N'roberto.almeida@riotur.gov.br', 3, 'ACTIVE', 'FREE'),
    (13, N'Fernanda', N'Ramos', NULL, N'QMS 03, Lote 52', N'Brasília', N'DF', N'Brazil', N'71578-000', N'+55 (61) 3363-5547', N'fernr@terra.com.br', 4, 'ACTIVE', 'FREE'),
    (14, N'Mark', N'Philips', N'Telus', N'8210 111 ST NW', N'Edmonton', N'AB', N'Canada', N'T6G 2C7', N'+1 (780) 434-4554', N'mphilips12@shaw.ca', 5, 'ACTIVE', 'FREE'),
    (15, N'Jennifer', N'Peterson', NULL, N'700 W Pender Street', N'Vancouver', N'BC', N'Canada', N'V6C 1G8', N'+1 (604) 682-0609', N'jenniferp@rogers.ca', 3, 'ACTIVE', 'GOLD');
    SET IDENTITY_INSERT Customer OFF;
END;
GO

-- =============================================================================
-- 3. DATABASE: chinook_invoice
-- =============================================================================
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_invoice')
    CREATE DATABASE chinook_invoice;
GO

USE chinook_invoice;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Invoice' AND xtype='U')
    CREATE TABLE Invoice (
        InvoiceId INT IDENTITY(1,1) PRIMARY KEY,
        InvoiceCode VARCHAR(50) NULL,
        CustomerId INT NOT NULL,
        CustomerFirstName NVARCHAR(40) NULL,
        CustomerLastName NVARCHAR(20) NULL,
        CustomerEmail NVARCHAR(60) NULL,
        InvoiceDate DATETIME2 NOT NULL,
        BillingAddress NVARCHAR(70) NULL,
        BillingCity NVARCHAR(40) NULL,
        BillingState NVARCHAR(40) NULL,
        BillingCountry NVARCHAR(40) NULL,
        BillingPostalCode NVARCHAR(10) NULL,
        SubTotal DECIMAL(10,2) NULL,
        DiscountAmount DECIMAL(10,2) NOT NULL DEFAULT 0,
        TaxAmount DECIMAL(10,2) NOT NULL DEFAULT 0,
        Total DECIMAL(10,2) NOT NULL,
        PaymentStatus VARCHAR(30) NOT NULL DEFAULT 'PAID',
        PaymentMethod VARCHAR(50) NULL DEFAULT 'CREDIT_CARD',
        PaymentTransactionId VARCHAR(100) NULL,
        PaidAt DATETIME2 NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        UpdatedAt DATETIME2 NULL,
        Version BIGINT NOT NULL DEFAULT 0,
        IsDeleted BIT NOT NULL DEFAULT 0
    );
GO

-- Đảm bảo các cột mới tồn tại nếu bảng đã được tạo từ trước
IF COL_LENGTH('Invoice', 'CustomerEmail') IS NULL
    ALTER TABLE Invoice ADD CustomerEmail NVARCHAR(60) NULL;
IF COL_LENGTH('Invoice', 'CustomerFirstName') IS NULL
    ALTER TABLE Invoice ADD CustomerFirstName NVARCHAR(40) NULL;
IF COL_LENGTH('Invoice', 'CustomerLastName') IS NULL
    ALTER TABLE Invoice ADD CustomerLastName NVARCHAR(20) NULL;
IF COL_LENGTH('Invoice', 'InvoiceCode') IS NULL
    ALTER TABLE Invoice ADD InvoiceCode VARCHAR(50) NULL;
IF COL_LENGTH('Invoice', 'PaymentTransactionId') IS NULL
    ALTER TABLE Invoice ADD PaymentTransactionId VARCHAR(100) NULL;
IF COL_LENGTH('Invoice', 'PaidAt') IS NULL
    ALTER TABLE Invoice ADD PaidAt DATETIME2 NULL;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='InvoiceLine' AND xtype='U')
    CREATE TABLE InvoiceLine (
        InvoiceLineId INT IDENTITY(1,1) PRIMARY KEY,
        InvoiceId INT NOT NULL,
        TrackId INT NOT NULL,
        TrackName NVARCHAR(200) NULL,
        UnitPrice DECIMAL(10,2) NOT NULL,
        Quantity INT NOT NULL,
        CONSTRAINT FK_InvoiceLine_Invoice FOREIGN KEY (InvoiceId) REFERENCES Invoice(InvoiceId)
    );
GO

-- Seed: Historical Invoices & Lines
IF NOT EXISTS (SELECT 1 FROM Invoice)
BEGIN
    SET IDENTITY_INSERT Invoice ON;
    INSERT INTO Invoice (InvoiceId, InvoiceCode, CustomerId, CustomerFirstName, CustomerLastName, CustomerEmail, InvoiceDate, BillingAddress, BillingCity, BillingCountry, SubTotal, Total, PaymentStatus, PaymentMethod, PaidAt) VALUES
    (1, 'INV-2026-0001', 2, N'Leonie', N'Köhler', N'leonekohler@surfeu.de', '2026-01-10 10:00:00', N'Theodor-Heuss-Straße 34', N'Stuttgart', N'Germany', 1.98, 1.98, 'PAID', 'CREDIT_CARD', '2026-01-10 10:01:00'),
    (2, 'INV-2026-0002', 4, N'Bjørn', N'Hansen', N'bjorn.hansen@yahoo.no', '2026-01-15 14:30:00', N'Ullevålsveien 14', N'Oslo', N'Norway', 2.97, 2.97, 'PAID', 'CREDIT_CARD', '2026-01-15 14:31:30'),
    (3, 'INV-2026-0003', 1, N'Luís', N'Gonçalves', N'luisg@embraer.com.br', '2026-02-01 09:15:00', N'Av. Brigadeiro Faria Lima, 2170', N'São José dos Campos', N'Brazil', 3.96, 3.96, 'PAID', 'CREDIT_CARD', '2026-02-01 09:16:00'),
    (4, 'INV-2026-0004', 3, N'François', N'Tremblay', N'ftremblay@gmail.com', '2026-02-14 18:00:00', N'1498 rue Bélanger', N'Montréal', N'Canada', 1.98, 1.98, 'PAID', 'PAYPAL', '2026-02-14 18:02:00'),
    (5, 'INV-2026-0005', 7, N'Astrid', N'Gruber', N'astrid.gruber@apple.at', '2026-03-01 11:20:00', N'Rotenturmstraße 4, 1010 Innere Stadt', N'Vienne', N'Austria', 2.97, 2.97, 'PAID', 'BANK_TRANSFER', '2026-03-01 11:25:00');
    SET IDENTITY_INSERT Invoice OFF;

    SET IDENTITY_INSERT InvoiceLine ON;
    INSERT INTO InvoiceLine (InvoiceLineId, InvoiceId, TrackId, TrackName, UnitPrice, Quantity) VALUES
    (1, 1, 1, N'For Those About To Rock (We Salute You)', 0.99, 1),
    (2, 1, 2, N'Balls to the Wall', 0.99, 1),
    (3, 2, 40, N'Enter Sandman', 0.99, 1),
    (4, 2, 41, N'Master of Puppets', 0.99, 1),
    (5, 2, 42, N'Battery', 0.99, 1),
    (6, 3, 43, N'Welcome to the Jungle', 0.99, 1),
    (7, 3, 44, N'Sweet Child O'' Mine', 0.99, 1),
    (8, 3, 45, N'Paradise City', 0.99, 1),
    (9, 3, 50, N'Bohemian Rhapsody', 0.99, 1),
    (10, 4, 31, N'You Oughta Know', 0.99, 1),
    (11, 4, 33, N'Ironic', 0.99, 1),
    (12, 5, 46, N'Smells Like Teen Spirit', 0.99, 1),
    (13, 5, 47, N'Come As You Are', 0.99, 1),
    (14, 5, 48, N'Stairway to Heaven', 0.99, 1);
    SET IDENTITY_INSERT InvoiceLine OFF;
END;
GO

-- =============================================================================
-- 4. DATABASE: chinook_payment
-- =============================================================================
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_payment')
    CREATE DATABASE chinook_payment;
GO

USE chinook_payment;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='PaymentTransaction' AND xtype='U')
    CREATE TABLE PaymentTransaction (
        TransactionId INT IDENTITY(1,1) PRIMARY KEY,
        InvoiceId INT NOT NULL,
        CustomerId INT NOT NULL,
        Amount DECIMAL(10,2) NOT NULL,
        PaymentMethod VARCHAR(50) NOT NULL,
        Status VARCHAR(20) NOT NULL,
        ReferenceCode VARCHAR(100) NULL,
        Note VARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        UpdatedAt DATETIME2 NULL
    );
GO

-- Seed: Historical Payments
IF NOT EXISTS (SELECT 1 FROM PaymentTransaction)
BEGIN
    SET IDENTITY_INSERT PaymentTransaction ON;
    INSERT INTO PaymentTransaction (TransactionId, InvoiceId, CustomerId, Amount, PaymentMethod, Status, ReferenceCode, Note) VALUES
    (1, 1, 2, 1.98, 'CREDIT_CARD', 'SUCCESS', 'TXN-2026-0001', 'Invoice #1 payment settled'),
    (2, 2, 4, 2.97, 'CREDIT_CARD', 'SUCCESS', 'TXN-2026-0002', 'Invoice #2 payment settled'),
    (3, 3, 1, 3.96, 'CREDIT_CARD', 'SUCCESS', 'TXN-2026-0003', 'Invoice #3 payment settled'),
    (4, 4, 3, 1.98, 'PAYPAL', 'SUCCESS', 'TXN-2026-0004', 'Invoice #4 payment settled'),
    (5, 5, 7, 2.97, 'BANK_TRANSFER', 'SUCCESS', 'TXN-2026-0005', 'Invoice #5 payment settled');
    SET IDENTITY_INSERT PaymentTransaction OFF;
END;
GO

-- =============================================================================
-- 5. DATABASE: chinook_library
-- =============================================================================
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'chinook_library')
    CREATE DATABASE chinook_library;
GO

USE chinook_library;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Entitlement' AND xtype='U')
    CREATE TABLE Entitlement (
        EntitlementId INT IDENTITY(1,1) PRIMARY KEY,
        CustomerId INT NOT NULL,
        TrackId INT NOT NULL,
        InvoiceId INT NOT NULL,
        GrantedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserPlaylist' AND xtype='U')
    CREATE TABLE UserPlaylist (
        PlaylistId INT IDENTITY(1,1) PRIMARY KEY,
        CustomerId INT NOT NULL,
        Name NVARCHAR(120) NOT NULL,
        Description NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserPlaylistTrack' AND xtype='U')
    CREATE TABLE UserPlaylistTrack (
        PlaylistId INT NOT NULL,
        TrackId INT NOT NULL,
        AddedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        PRIMARY KEY (PlaylistId, TrackId),
        CONSTRAINT FK_UserPlaylistTrack_Playlist FOREIGN KEY (PlaylistId) REFERENCES UserPlaylist(PlaylistId) ON DELETE CASCADE
    );
GO

-- Seed: Historical Entitlements & Playlists
IF NOT EXISTS (SELECT 1 FROM Entitlement)
BEGIN
    SET IDENTITY_INSERT Entitlement ON;
    INSERT INTO Entitlement (EntitlementId, CustomerId, TrackId, InvoiceId) VALUES
    (1, 2, 1, 1),
    (2, 2, 2, 1),
    (3, 4, 40, 2),
    (4, 4, 41, 2),
    (5, 4, 42, 2),
    (6, 1, 43, 3),
    (7, 1, 44, 3),
    (8, 1, 45, 3),
    (9, 1, 50, 3),
    (10, 3, 31, 4),
    (11, 3, 33, 4),
    (12, 7, 46, 5),
    (13, 7, 47, 5),
    (14, 7, 48, 5);
    SET IDENTITY_INSERT Entitlement OFF;
END;
GO

IF NOT EXISTS (SELECT 1 FROM UserPlaylist)
BEGIN
    SET IDENTITY_INSERT UserPlaylist ON;
    INSERT INTO UserPlaylist (PlaylistId, CustomerId, Name, Description) VALUES
    (1, 2, N'Classic Rock Legends', N'Best rock songs from AC/DC and Accept'),
    (2, 4, N'Heavy Metal Riffs', N'Fast-paced metal tracks'),
    (3, 1, N'Ultimate Favorites', N'Guns N Roses and Queen hits');
    SET IDENTITY_INSERT UserPlaylist OFF;

    INSERT INTO UserPlaylistTrack (PlaylistId, TrackId) VALUES
    (1, 1),
    (1, 2),
    (2, 40),
    (2, 41),
    (2, 42),
    (3, 43),
    (3, 44),
    (3, 45),
    (3, 50);
END;
GO

PRINT '=============================================================';
PRINT '  ALL 5 DATABASES & SEED DATA INITIALIZED SUCCESSFULLY!     ';
PRINT '  1. chinook_catalog (25 Artists, 15 Genres, 20 Albums, 50 Tracks)';
PRINT '  2. chinook_customer (8 Employees, 15 Customers)           ';
PRINT '  3. chinook_invoice (5 Invoices, 14 Invoice Lines)          ';
PRINT '  4. chinook_payment (5 Payment Transactions)               ';
PRINT '  5. chinook_library (14 Entitlements, 3 Playlists)          ';
PRINT '=============================================================';
GO
