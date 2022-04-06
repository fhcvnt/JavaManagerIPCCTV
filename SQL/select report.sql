SELECT DISTINCT Location.Location,(SELECT COUNT(ds.MaLocation) FROM DanhSachIP AS ds WHERE ds.MaLocation=DanhSachIP.MaLocation) FROM DanhSachIP,Location WHERE DanhSachIP.MaLocation=Location.MaLocation ORDER BY Location.Location ASC

SELECT COUNT(*) AS TOTAL FROM DanhSachIP
SELECT Location.Location FROM DanhSachIP,Location WHERE DanhSachIP.MaLocation=Location.MaLocation ORDER BY Location.Location ASC

SELECT DISTINCT DanhSachIP.IP,(SELECT COUNT(ds.IP) FROM DanhSachIP AS ds WHERE ds.MaLocation=DanhSachIP.MaLocation) FROM DanhSachIP ORDER BY DanhSachIP.IP ASC



