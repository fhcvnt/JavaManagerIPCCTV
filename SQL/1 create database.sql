CREATE DATABASE IPManagerCCTV
GO 
USE IPManagerCCTV
GO

CREATE TABLE LoaiMay(
	-- Loại Máy: PC, Laptop, Server, Camera
	MaLoaiMay VARCHAR(15) PRIMARY KEY NOT NULL,
	LoaiMay NVARCHAR(30) UNIQUE NOT NULL
)
GO

INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('pc',N'Máy vi tính')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('daughikca',N'Đầu Ghi KCA')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('server',N'Server')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('daughigkb',N'Đầu Ghi GKB')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('cctvgkb',N'Camera GKB')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('cctvkb',N'Camera KBVISION')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('switch',N'Switch')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('router',N'Router')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('gateway',N'Gateway')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('cctvkca',N'Camera KCA')
INSERT INTO dbo.LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ('eac',N'EAC')
GO 


CREATE TABLE Location(
	MaLocation VARCHAR(10) PRIMARY KEY NOT NULL,
	Location NVARCHAR(50) UNIQUE NOT NULL)
GO

INSERT INTO dbo.Location ( MaLocation, Location ) VALUES  ('a',N'Xưởng A')
INSERT INTO dbo.Location ( MaLocation, Location ) VALUES  ('b',N'Xưởng B')
INSERT INTO dbo.Location ( MaLocation, Location ) VALUES  ('hr',N'Hàng Rào')
go

CREATE TABLE DanhSachIP(
	Ten NVARCHAR(40) NOT NULL,
	MaLocation VARCHAR(10) FOREIGN KEY (MaLocation) REFERENCES dbo.Location(MaLocation) NOT NULL,
	IP VARCHAR(15) UNIQUE NOT NULL,
	-- Loại Máy: PC, Laptop, Server, Camera
	MaLoaiMay VARCHAR(15) FOREIGN KEY (MaLoaiMay) REFERENCES dbo.LoaiMay(MaLoaiMay) NOT NULL,
	GhiChu NVARCHAR(255),
	NgayCapNhat DATE DEFAULT GETDATE()
)
GO 

CREATE TABLE Note(
	Note NVARCHAR(450) UNIQUE NOT NULL,
	NgayTao DATE DEFAULT GETDATE())
GO


--***********************************************************************

CREATE FUNCTION convertUnicodetoASCII (@text nvarchar(max))
RETURNS nvarchar(max)
AS
BEGIN
	SET @text = LOWER(@text)
	DECLARE @textLen int = LEN(@text)
	IF @textLen > 0
	BEGIN
		DECLARE @index int = 1
		DECLARE @lPos int
		DECLARE @SIGN_CHARS nvarchar(100) = N'ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵýđð'
		DECLARE @UNSIGN_CHARS varchar(100) = 'aadeoouaaaaaaaaaaaaaaaeeeeeeeeeeiiiiiooooooooooooooouuuuuuuuuuyyyyydd'

		WHILE @index <= @textLen
		BEGIN
			SET @lPos = CHARINDEX(SUBSTRING(@text,@index,1),@SIGN_CHARS)
			IF @lPos > 0
			BEGIN
				SET @text = STUFF(@text,@index,1,SUBSTRING(@UNSIGN_CHARS,@lPos,1))
			END
			SET @index = @index + 1
		END
	END
	RETURN @text
END