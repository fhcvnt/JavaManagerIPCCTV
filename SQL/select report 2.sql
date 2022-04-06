SELECT DISTINCT Location.Location,(SELECT COUNT(ds.MaLocation) FROM DanhSachIP AS ds WHERE ds.MaLocation=DanhSachIP.MaLocation) FROM DanhSachIP,Location WHERE DanhSachIP.MaLocation=Location.MaLocation ORDER BY Location.Location ASC

SELECT COUNT(*) AS TOTAL FROM DanhSachIP
SELECT Location.Location FROM DanhSachIP,Location WHERE DanhSachIP.MaLocation=Location.MaLocation ORDER BY Location.Location ASC



SELECT DISTINCT (SELECT CHARINDEX('.',DanhSachIP.IP,8)),DanhSachIP.IP,(SELECT COUNT(ds.IP) FROM DanhSachIP AS ds WHERE ds.IP=DanhSachIP.IP) FROM DanhSachIP ORDER BY DanhSachIP.IP ASC

SELECT CHARINDEX('.',DanhSachIP.IP,9) FROM DanhSachIP


SELECT DISTINCT CAST(SUBSTRING(DanhSachIP.IP,9,(SELECT CHARINDEX('.',DanhSachIP.IP,9)-9)) AS INT) AS LopIP FROM DanhSachIP ORDER BY LopIP ASC

SELECT CAST(SUBSTRING(DanhSachIP.IP,9,(SELECT CHARINDEX('.',DanhSachIP.IP,9)-9)) AS INT) AS LopIP FROM DanhSachIP ORDER BY LopIP ASC

-- luu vao bang tam
SELECT CAST(SUBSTRING(DanhSachIP.IP,9,(SELECT CHARINDEX('.',DanhSachIP.IP,9)-9)) AS INT) AS LopIP INTO #LopIP FROM DanhSachIP

SELECT DISTINCT #LopIP.LopIP,(SELECT COUNT(*) FROM #LopIP AS LopIP2 WHERE LopIP2.LopIP=#LopIP.LopIP) FROM #LopIP ORDER BY #LopIP.LopIP

-- global
SELECT CAST(SUBSTRING(DanhSachIP.IP,9,(SELECT CHARINDEX('.',DanhSachIP.IP,9)-9)) AS INT) AS LopIP INTO ##LopIP FROM DanhSachIP 
GO 
SELECT DISTINCT ##LopIP.LopIP,(SELECT COUNT(*) FROM ##LopIP AS LopIP2 WHERE LopIP2.LopIP=##LopIP.LopIP) FROM ##LopIP ORDER BY ##LopIP.LopIP ASC

-- LOAI MAY
SELECT DISTINCT LoaiMay.LoaiMay,(SELECT COUNT(*) FROM DanhSachIP AS DS WHERE DS.MaLoaiMay=DanhSachIP.MaLoaiMay) FROM DanhSachIP,LoaiMay WHERE DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay