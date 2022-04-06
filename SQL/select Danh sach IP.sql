/****** Script for SelectTopNRows command from SSMS  ******/
SELECT SoThe
      ,HoTen  
	  ,DonVi.DonVi
      ,IP
      ,LoaiMay.LoaiMay
      ,Email
      ,HeDieuHanh.HeDieuHanh
      ,Office.Office
      ,Building.Building
      ,CASE Internet WHEN 1 THEN 'Có' ELSE 'Không' END AS Internet
      ,CASE MailNgoai WHEN 1 THEN 'Có' ELSE 'Không' END AS MailNgoai
      ,CASE Skype WHEN 1 THEN 'Có' ELSE 'Không' END AS Skype
      ,CASE USB WHEN 1 THEN 'Có' ELSE 'Không' END AS USB
      ,GhiChu
      ,NgayCapNhat
  FROM DanhSachIP,DonVi,LoaiMay,HeDieuHanh,Office,Building
  WHERE DanhSachIP.MaDonVi=DonVi.MaDonVi AND DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay AND DanhSachIP.MaHeDieuHanh=HeDieuHanh.MaHeDieuHanh AND DanhSachIP.MaOffice=Office.MaOffice AND DanhSachIP.MaBuilding=Building.MaBuilding AND DonVi.DonVi=N