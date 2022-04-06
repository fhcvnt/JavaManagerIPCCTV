package cctv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import sort.SortData;
import sql.ConnectSQL;

public class IPList {
	private ConnectSQL connect;
	private Text textTen;
	private Text textKhuvuc;
	private Text textIP;
	private Table table;
	private TableItem[] itemtable;
	private String ipxoa;
	private int vitrixoa = -1;
	private ArrayList<String> excelname;
	private ArrayList<String> excellocationcode, excellocation, excelip, excelmachinecode, excelmachinetype, excelnote;

	protected Shell shell;
	private Composite compositeshell;
	private Text textLoaimay;
	private int ngonngu = 1;

	public static void main(String[] args) {
		try {
			IPList window = new IPList();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContentsShell();
		createContents(compositeshell, shell, ngonngu);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	protected void createContentsShell() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(IPList.class, "/cctv/Icon/IP.ico"));
		shell.setSize(1919, 1050);
		shell.setMaximized(true);
		shell.setText("IP");

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		shell.setLayout(fillLayout);
		compositeshell = new Composite(shell, SWT.NONE);
		compositeshell.setLayout(null);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Composite composite, Shell shell,int ngonngu) {
		CLabel lbTen = new CLabel(composite, SWT.RIGHT);
		lbTen.setBounds(10, 10, 63, 30);
		lbTen.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTen.setText("Tên");

		textTen = new Text(composite, SWT.BORDER);
		textTen.setBounds(79, 10, 200, 30);
		textTen.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textTen.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));

		CLabel lbKhuvuc = new CLabel(composite, SWT.RIGHT);
		lbKhuvuc.setBounds(285, 10, 159, 30);
		lbKhuvuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbKhuvuc.setText("Khu Vực");

		textKhuvuc = new Text(composite, SWT.BORDER);
		textKhuvuc.setBounds(456, 10, 218, 30);
		textKhuvuc.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textKhuvuc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));

		Button btnthem = new Button(composite, SWT.NONE);
		btnthem.setBounds(680, 10, 63, 30);
		btnthem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnthem.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnthem.setText("Thêm");

		Button btnsua = new Button(composite, SWT.NONE);
		btnsua.setBounds(749, 10, 56, 30);
		btnsua.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnsua.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnsua.setText("Sửa");

		Button btnxoa = new Button(composite, SWT.NONE);
		btnxoa.setBounds(811, 10, 81, 30);
		btnxoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnxoa.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnxoa.setText("Xóa");

		Button btnLuu = new Button(composite, SWT.NONE);
		btnLuu.setBounds(898, 10, 87, 30);
		btnLuu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnLuu.setText("Lưu");
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));

		Button btnHuy = new Button(composite, SWT.NONE);
		btnHuy.setBounds(991, 10, 93, 30);
		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnHuy.setText("Hủy");
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));

		CLabel lbIP = new CLabel(composite, SWT.RIGHT);
		lbIP.setBounds(9, 46, 64, 30);
		lbIP.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbIP.setText("IP");

		textIP = new Text(composite, SWT.BORDER);
		textIP.setBounds(79, 46, 200, 30);
		textIP.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));

		CLabel lbLoaimay = new CLabel(composite, SWT.RIGHT);
		lbLoaimay.setBounds(285, 46, 159, 30);
		lbLoaimay.setText("Machine Type");
		lbLoaimay.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		textLoaimay = new Text(composite, SWT.BORDER);
		textLoaimay.setBounds(456, 46, 218, 30);
		textLoaimay.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textLoaimay.setBackground(SWTResourceManager.getColor(224, 255, 255));

		Button btntimkiem = new Button(composite, SWT.NONE);
		btntimkiem.setBounds(680, 46, 107, 30);
		btntimkiem.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		btntimkiem.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.BOLD));
		btntimkiem.setText("Tìm Kiếm");

		Button btnGetIP = new Button(composite, SWT.NONE);
		btnGetIP.setBounds(793, 46, 81, 30);
		btnGetIP.setText("Get IP");
		btnGetIP.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnGetIP.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.BOLD));

		Button btnImport = new Button(composite, SWT.NONE);
		btnImport.setText("Import");
		btnImport.setImage(SWTResourceManager.getImage(IPList.class, "/cctv/Icon/bieutuong/import.png"));
		btnImport.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		btnImport.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.BOLD));
		btnImport.setBounds(880, 46, 112, 30);

		Button btnExport = new Button(composite, SWT.NONE);
		btnExport.setImage(SWTResourceManager.getImage(IPList.class, "/cctv/Icon/bieutuong/excel25.png"));
		btnExport.setText("Export");
		btnExport.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnExport.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.BOLD));
		btnExport.setBounds(998, 46, 117, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		table.setBounds(10, 82, 1888, 888);
		table.setHeaderForeground(SWTResourceManager.getColor(0, 0, 0));
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		TableColumn tbcSTT = new TableColumn(table, SWT.NONE);
		tbcSTT.setWidth(77);
		tbcSTT.setText("STT");

		TableColumn tbcTen = new TableColumn(table, SWT.NONE);
		tbcTen.setWidth(243);
		tbcTen.setText("Tên");

		TableColumn tbcKhuvuc = new TableColumn(table, SWT.NONE);
		tbcKhuvuc.setWidth(245);
		tbcKhuvuc.setText("Khu Vực");

		TableColumn tbcIP = new TableColumn(table, SWT.NONE);
		tbcIP.setWidth(193);
		tbcIP.setText("IP");

		TableColumn tbcLoaimay = new TableColumn(table, SWT.NONE);
		tbcLoaimay.setWidth(187);
		tbcLoaimay.setText("Loại Máy");

		TableColumn tbcGhichu = new TableColumn(table, SWT.NONE);
		tbcGhichu.setWidth(187);
		tbcGhichu.setText("Ghi Chú");

		TableColumn tbcNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbcNgaycapnhat.setWidth(155);
		tbcNgaycapnhat.setText("Ngày Cập Nhật");

		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// ngôn ngữ
		// ********************************************************************************************************************************************************************
		if (ngonngu == 0) {
			lbTen.setText("Tên");
			lbKhuvuc.setText("Khu Vực");
			btnthem.setText("Thêm");
			btnsua.setText("Sửa");
			btnxoa.setText("Xóa");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
			lbIP.setText("IP");
			lbLoaimay.setText("Loại Máy");
			btntimkiem.setText("Tìm Kiếm");
			btnGetIP.setText("Lấy IP");
			btnImport.setText("Nhập");
			btnExport.setText("Xuất");
			tbcSTT.setText("STT");
			tbcTen.setText("Tên");
			tbcKhuvuc.setText("Khu Vực");
			tbcIP.setText("IP");
			tbcLoaimay.setText("Loại Máy");
			tbcGhichu.setText("Ghi Chú");
			tbcNgaycapnhat.setText("Ngày Cập Nhật");
		} else {
			lbTen.setText("Name");
			lbKhuvuc.setText("Location");
			btnthem.setText("Add");
			btnsua.setText("Edit");
			btnxoa.setText("Delete");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
			lbIP.setText("IP");
			lbLoaimay.setText("Machine Type");
			btntimkiem.setText("Search");
			btnImport.setText("Import");
			btnExport.setText("Export");
			btnGetIP.setText("Get IP");
			tbcSTT.setText("Number");
			tbcTen.setText("Name");
			tbcKhuvuc.setText("Location");
			tbcIP.setText("IP");
			tbcLoaimay.setText("Machine Type");
			tbcGhichu.setText("Note");
			tbcNgaycapnhat.setText("Date Update");
		}

		// Tìm kiếm sau khi enter ở text name
		// ******************************************************************************
		textTen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					search(shell, ngonngu);
				}
			}
		});

		// Tìm kiếm sau khi enter ở text name
		// ******************************************************************************
		textTen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					search(shell, ngonngu);
				}
			}
		});

		// Tìm kiếm sau khi enter ở text IP
		// ******************************************************************************
		textIP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					search(shell, ngonngu);
				}
			}
		});

		// Tìm kiếm sau khi enter ở text MachineType
		// ******************************************************************************
		textLoaimay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					search(shell, ngonngu);
				}
			}
		});

		// Tìm kiếm sau khi enter ở text Location
		// ******************************************************************************
		textKhuvuc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					search(shell, ngonngu);
				}
			}
		});

		// Tìm kiếm sau khi enter ở btn search
		// ******************************************************************************
		btntimkiem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					search(shell, ngonngu);
				}
			}
		});

		// Sort data
		// ************************************************************************************************
		// Sắp xếp table theo một cột đã chọn
		SortData sort = new SortData();
		sort.setTable(table);
		sort.setNumber(true);

		tbcTen.addListener(SWT.Selection, sort.sortListenerCode);
		tbcKhuvuc.addListener(SWT.Selection, sort.sortListenerCode);
		tbcIP.addListener(SWT.Selection, sort.sortListenerIP);
		tbcLoaimay.addListener(SWT.Selection, sort.sortListenerCode);
		tbcGhichu.addListener(SWT.Selection, sort.sortListenerCode);
		tbcNgaycapnhat.addListener(SWT.Selection, sort.sortListenerDate);

		// Import Excel
		// ********************************************************************************************************************************************************************
		btnImport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (connect == null) {
					connect = new ConnectSQL();
					connect.setConnection();
				}
				connect.setStatement();
				try {
					String[] FILTER_NAMES = { "Excel(*.xls)" };
					// đuôi file có thể mở
					String[] FILTER_EXTS = { "*.xls" };

					FileDialog dlg = new FileDialog(shell, SWT.OPEN);
					dlg.setFilterNames(FILTER_NAMES);
					dlg.setFilterExtensions(FILTER_EXTS);
					String filename = dlg.open();

					// open file excel save to ArrayList
					readXLSFile(filename);

					for (int i = 1; i < excellocationcode.size(); i++) {
						// location
						try {
							String insert = "INSERT INTO Location (MaLocation,Location) VALUES ('"
									+ excellocationcode.get(i).toString() + "',N'" + excellocation.get(i).toString()
									+ "')";
							connect.execUpdateQuery(insert);
						} catch (Exception se) {
						}
						connect.closeStatement();

						// machine

						try {
							connect.setStatement();
							String insert = "INSERT INTO LoaiMay (MaLoaiMay,LoaiMay) VALUES ('"
									+ excelmachinecode.get(i).toString() + "',N'" + excelmachinetype.get(i).toString()
									+ "')";
							connect.execUpdateQuery(insert);
							connect.closeStatement();
						} catch (Exception se) {
						}

						// insert data
						try {
							connect.setStatement();
							String insert = "INSERT INTO DanhSachIP (Ten,MaLocation,IP,MaLoaiMay,GhiChu,NgayCapNhat) VALUES ( N'"
									+ excelname.get(i).toString() + "','" + excellocationcode.get(i).toString() + "','"
									+ excelip.get(i).toString() + "','" + excelmachinecode.get(i).toString() + "',N'"
									+ excelnote.get(i).toString() + "',GETDATE())";
							connect.execUpdateQuery(insert);
							connect.closeStatement();
						} catch (Exception se) {
						}
					}
				} catch (Exception exce) {
				}
			}
		});

		// Excel
		// ********************************************************************************************************************************************************************
		btnExport.addSelectionListener(new SelectionAdapter() {

			private HSSFWorkbook wb;

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (connect == null) {
						connect = new ConnectSQL();
						connect.setConnection();
					}
					connect.setStatement();

					String selectDanhSachIP = "";
					ResultSet result = null;

					// text find
					String ten = textTen.getText().isEmpty() ? "" : " AND Ten LIKE N'%" + textTen.getText() + "%'";
					String khuvuc = textKhuvuc.getText().isEmpty() ? ""
							: " AND Location LIKE N'%" + textKhuvuc.getText() + "%'";
					String ip = textIP.getText().isEmpty() ? "" : " AND IP LIKE '%" + textIP.getText() + "'";
					String loaimay = textLoaimay.getText().isEmpty() ? ""
							: " AND LoaiMay.LoaiMay LIKE N'%" + textLoaimay.getText() + "%'";

					selectDanhSachIP = "SELECT Ten,DanhSachIP.MaLocation,Location.Location,IP,DanhSachIP.MaLoaiMay,LoaiMay.LoaiMay,GhiChu,NgayCapNhat FROM DanhSachIP,Location,LoaiMay WHERE DanhSachIP.MaLocation=Location.MaLocation AND DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay"
							+ ten + khuvuc + ip + loaimay + " ORDER BY Location.Location ASC";
					try {
						String[] FILTER_NAMES = { "Excel (*.xls)" };
						// đuôi file có thể mở
						String[] FILTER_EXTS = { "*.xls" };

						FileDialog dlg = new FileDialog(shell, SWT.SAVE);
						dlg.setFilterNames(FILTER_NAMES);
						dlg.setFilterExtensions(FILTER_EXTS);

						Calendar now = Calendar.getInstance();
						int year = now.get(Calendar.YEAR);
						int month = now.get(Calendar.MONTH) + 1;
						int day = now.get(Calendar.DAY_OF_MONTH);
						dlg.setFileName("List IP Camera " + day + "." + month + "." + year + ".xls");

						String filename = dlg.open(); // ten file luu
						if (filename != null) {

							String excelFileName = filename;// name of excel file

							String sheetName = "Sheet1";// name of sheet

							wb = new HSSFWorkbook();
							HSSFSheet sheet = wb.createSheet(sheetName);

							// Tao style cho tieu de
							HSSFCellStyle styletitle = wb.createCellStyle();
							styletitle.setAlignment(HorizontalAlignment.CENTER);
							styletitle.setVerticalAlignment(VerticalAlignment.CENTER);
							styletitle.setBorderTop(BorderStyle.THIN);
							styletitle.setBorderBottom(BorderStyle.THIN);
							styletitle.setBorderRight(BorderStyle.THIN);
							styletitle.setBorderLeft(BorderStyle.THIN);
							styletitle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
							styletitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

							// Font cho tieu de
							HSSFFont fonttitle = wb.createFont();
							fonttitle.setBold(true);
							fonttitle.setFontHeightInPoints((short) 13);
							fonttitle.setFontName("Times New Roman");
							fonttitle.setColor(IndexedColors.RED.getIndex());
							styletitle.setFont(fonttitle);

							HSSFRow rowtieude = sheet.createRow(1);
							HSSFCell cell1 = rowtieude.createCell(1);
							cell1.setCellValue("Name");
							cell1.setCellStyle(styletitle);

							HSSFCell cell2 = rowtieude.createCell(2);
							cell2.setCellValue("Location Code");
							cell2.setCellStyle(styletitle);

							HSSFCell cell3 = rowtieude.createCell(3);
							cell3.setCellValue("Location");
							cell3.setCellStyle(styletitle);

							HSSFCell cell4 = rowtieude.createCell(4);
							cell4.setCellValue("IP");
							cell4.setCellStyle(styletitle);

							HSSFCell cell5 = rowtieude.createCell(5);
							cell5.setCellValue("Machine Code");
							cell5.setCellStyle(styletitle);

							HSSFCell cell6 = rowtieude.createCell(6);
							cell6.setCellValue("Machine Type");
							cell6.setCellStyle(styletitle);

							HSSFCell cell7 = rowtieude.createCell(7);
							cell7.setCellValue("Note");
							cell7.setCellStyle(styletitle);

							HSSFCell cell8 = rowtieude.createCell(8);
							cell8.setCellValue("Date Update");
							cell8.setCellStyle(styletitle);

							// style cho cac o du lieu, tao border
							HSSFCellStyle style = wb.createCellStyle();
							style.setVerticalAlignment(VerticalAlignment.CENTER);
							style.setBorderTop(BorderStyle.THIN);
							style.setBorderBottom(BorderStyle.THIN);
							style.setBorderRight(BorderStyle.THIN);
							style.setBorderLeft(BorderStyle.THIN);

							// Font cho cac o du lieu
							HSSFFont fontcell = wb.createFont();
							fontcell.setFontHeightInPoints((short) 13);
							fontcell.setFontName("Times New Roman");
							fontcell.setColor(IndexedColors.BLACK.getIndex());
							style.setFont(fontcell);

							// iterating r number of rows
							int r = 2;
							result = connect.getStatement().executeQuery(selectDanhSachIP);
							while (result.next()) {
								HSSFRow row = sheet.createRow(r);
								String ngaycapnhat = result.getString(8);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}

								// iterating c number of columns
								cell1 = row.createCell(1);
								cell1.setCellValue(result.getString(1));
								cell1.setCellStyle(style);

								cell2 = row.createCell(2);
								cell2.setCellValue(result.getString(2));
								cell2.setCellStyle(style);

								cell3 = row.createCell(3);
								cell3.setCellValue(result.getString(3));
								cell3.setCellStyle(style);

								cell4 = row.createCell(4);
								cell4.setCellValue(result.getString(4));
								cell4.setCellStyle(style);

								cell5 = row.createCell(5);
								cell5.setCellValue(result.getString(5));
								cell5.setCellStyle(style);

								cell6 = row.createCell(6);
								cell6.setCellValue(result.getString(6));
								cell6.setCellStyle(style);

								cell7 = row.createCell(7);
								cell7.setCellValue(result.getString(7));
								cell7.setCellStyle(style);

								cell8 = row.createCell(8);
								cell8.setCellValue(ngaycapnhat);
								cell8.setCellStyle(style);
								r++;
							}
							result.close();

							sheet.autoSizeColumn(1);
							sheet.autoSizeColumn(2);
							sheet.autoSizeColumn(3);
							sheet.autoSizeColumn(4);
							sheet.autoSizeColumn(5);
							sheet.autoSizeColumn(6);
							sheet.autoSizeColumn(7);
							sheet.autoSizeColumn(8);

							FileOutputStream fileOut = new FileOutputStream(excelFileName);

							// write this workbook to an Outputstream.
							wb.write(fileOut);
							fileOut.flush();
							fileOut.close();
						}
						wb.close();
						MessageBox thongbao = new MessageBox(shell, SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Lưu thành công!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Save successful!");
						}
						thongbao.open();
					} catch (Exception ee) {
						MessageBox thongbao = new MessageBox(shell, SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Lưu thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Save failed!");
						}
						thongbao.open();
					}

				} catch (Exception se) {
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					if (ngonngu == 0) {
						thongbao.setText("Thông báo");
						thongbao.setMessage("Lỗi kết nối!");
					} else {
						thongbao.setText("Notification");
						thongbao.setMessage("Connect error!");
					}
					thongbao.open();
				} finally {
					try {
						connect.closeStatement();
					} catch (Exception se2) {
					}
				}
			}

		});

		// Get IP
		btnGetIP.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GetIP getip = new GetIP();
				composite.setEnabled(false);
				getip.createContents(ngonngu);
				getip.open();
				composite.setEnabled(true);
			}
		});

		// Tìm kiếm
		// --------------------------------------------------------------------------------------------------------------------
		btntimkiem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				search(shell, ngonngu);
			}
		});

		// Hủy--------------------------------------------------------------------------------------------------------------
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnthem.setEnabled(true);
				btnsua.setEnabled(true);
				btntimkiem.setEnabled(true);
				btnLuu.setVisible(false);
				btnHuy.setVisible(false);
				btnGetIP.setEnabled(true);
				btnExport.setEnabled(true);
				btnImport.setEnabled(true);
			}
		});

		// Lưu--------------------------------------------------------------------------------------------------------------
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (connect == null) {
						connect = new ConnectSQL();
						connect.setConnection();
					}
					connect.setStatement();

					try {
						String deleteDanhSachIP = "DELETE DanhSachIP WHERE IP='" + ipxoa + "'";
						int result = connect.getStatement().executeUpdate(deleteDanhSachIP);
						if (result > 0) {
							table.remove(vitrixoa);
						}
						btnthem.setEnabled(true);
						btnsua.setEnabled(true);
						btntimkiem.setEnabled(true);
						btnLuu.setVisible(false);
						btnHuy.setVisible(false);
						btnGetIP.setEnabled(true);
						btnExport.setEnabled(true);
						btnImport.setEnabled(true);
					} catch (Exception ex) {
					}

				} catch (Exception se) {
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					if (ngonngu == 0) {
						thongbao.setText("Thông báo");
						thongbao.setMessage("Lỗi kết nối!");
					} else {
						thongbao.setText("Notification");
						thongbao.setMessage("Connect error!");
					}
					thongbao.setMessage(se.toString());
					thongbao.open();
				} finally {
					try {
						connect.closeStatement();
					} catch (Exception se2) {
					}
				}
			}
		});

		// Xóa-------------------------------------------------------------------------------------------------------------------------------
		btnxoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					btnthem.setEnabled(false);
					btnsua.setEnabled(false);
					btntimkiem.setEnabled(false);
					btnLuu.setVisible(true);
					btnHuy.setVisible(true);
					btnGetIP.setEnabled(false);
					btnExport.setEnabled(false);
					btnImport.setEnabled(false);
					// Lấy cột IP
					itemtable = table.getSelection();
					ipxoa = itemtable[0].getText(3);
					vitrixoa = table.getSelectionIndex();
				} catch (Exception ex) {
					btnthem.setEnabled(true);
					btnsua.setEnabled(true);
					btntimkiem.setEnabled(true);
					btnLuu.setVisible(false);
					btnHuy.setVisible(false);
					btnGetIP.setEnabled(true);
					btnExport.setEnabled(true);
					btnImport.setEnabled(true);
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					if (ngonngu == 0) {
						thongbao.setText("Thông báo");
						thongbao.setMessage("Không có dòng nào được chọn!");
					} else {
						thongbao.setText("Notification");
						thongbao.setMessage("No lines are selected!");
					}
					thongbao.open();
				}
			}
		});

		// Sửa-----------------------------------------------------------------------------------------------------------------------------
		btnsua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (connect == null) {
						connect = new ConnectSQL();
						connect.setConnection();
					}

					try {
						TableItem[] itemsua = table.getSelection();
						String ipsua = "";
						ipsua = itemsua[0].getText(3);
						if (!ipsua.isEmpty()) {
							EditIP editip = new EditIP();
							// mở cửa sổ mới thì cửa sổ cũ không cho thao tác
							composite.setEnabled(false);
							editip.createContents(itemsua[0].getText(1), itemsua[0].getText(2), itemsua[0].getText(3),
									itemsua[0].getText(4), itemsua[0].getText(5), ngonngu);
							editip.open();
							composite.setEnabled(true);

							// cập nhật dữ liệu sau khi sửa
							// IP-----------------------------------------------------------------------------------------------------
							// gọi lại hàm tìm kiếm

							try {
								connect.setStatement();
								String selectDanhSachIP = "";
								ResultSet result;
								table.removeAll();

								// text find
								String ten = textTen.getText().isEmpty() ? ""
										: " AND (Ten LIKE N'%" + textTen.getText()
												+ "%' OR [dbo].[convertUnicodetoASCII](Ten) LIKE '%" + textTen.getText()
												+ "%')";
								String khuvuc = textKhuvuc.getText().isEmpty() ? ""
										: " AND (Location LIKE N'%" + textKhuvuc.getText()
												+ "%' OR [dbo].[convertUnicodetoASCII](Location) LIKE '%"
												+ textKhuvuc.getText() + "%')";
								String ip = textIP.getText().isEmpty() ? ""
										: " AND IP LIKE '%" + textIP.getText() + "'";
								String loaimay = textLoaimay.getText().isEmpty() ? ""
										: " AND (LoaiMay.LoaiMay LIKE N'%" + textLoaimay.getText()
												+ "%' OR [dbo].[convertUnicodetoASCII](LoaiMay.LoaiMay) LIKE '%"
												+ textLoaimay.getText() + "%')";

								// xử lý tìm theo lớp IP, nếu textIP nhận vào là lớp IP ví dụ: 30 thay vì nhận
								// được là 30.1 thì ta tìm theo lớp IP chứ không tìm theo IP
								int lopip = -1;
								try {
									lopip = Integer.parseInt(textIP.getText());
								} catch (NumberFormatException ne) {

								}
								if (lopip >= 0) {
									ip = textIP.getText().isEmpty() ? ""
											: " AND IP LIKE '%" + "%.%." + lopip + "." + "%'";
								}

								selectDanhSachIP = "SELECT Ten,Location.Location,IP,LoaiMay.LoaiMay,GhiChu,NgayCapNhat FROM DanhSachIP,Location,LoaiMay WHERE DanhSachIP.MaLocation=Location.MaLocation AND DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay"
										+ ten + khuvuc + ip + loaimay + " ORDER BY NgayCapNhat DESC";

								// Xử lý dữ liệu tìm kiếm, hiển thị lên Table
								result = connect.getStatement().executeQuery(selectDanhSachIP);
								int stt = 1;
								while (result.next()) {
									String ngaycapnhat = result.getString(6);
									try {
										ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7)
												+ "/" + ngaycapnhat.substring(0, 4);
									} catch (IndexOutOfBoundsException ie) {
										ngaycapnhat = "";
									}
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { stt + "", result.getString(1), result.getString(2),
											result.getString(3), result.getString(4), result.getString(5),
											ngaycapnhat });
									stt++;
								}

								result.close();

							} catch (Exception se) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Lỗi kết nối!");
								} else {
									thongbao.setText("Notification");
									thongbao.setMessage("Connect error!");
								}
								thongbao.setMessage(se.toString());
								thongbao.open();
							}
						}

					} catch (Exception ex) {
					}

				} catch (Exception se) {
					// se.printStackTrace();
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					if (ngonngu == 0) {
						thongbao.setText("Thông báo");
						thongbao.setMessage("Lỗi kết nối!");
					} else {
						thongbao.setText("Notification");
						thongbao.setMessage("Connect error!");
					}
					thongbao.open();
				} finally {
					try {
						connect.closeStatement();
					} catch (Exception se2) {
					}
				}
			}
		});

		// Thêm--------------------------------------------------------------------------------------------------------------------------
		btnthem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NewIP newip = new NewIP();
				// mở cửa sổ mới thì cửa sổ cũ không cho thao tác
				composite.setEnabled(false);
				newip.createContents(ngonngu);
				newip.open();
				composite.setEnabled(true);
				// cap nhat lai du lieu

				try {
					if (connect == null) {
						connect = new ConnectSQL();
						connect.setConnection();
					}
					connect.setStatement();
					String selectDanhSachIP = "";
					ResultSet result;
					table.removeAll();

					// text find
					String ten = textTen.getText().isEmpty() ? ""
							: " AND (Ten LIKE N'%" + textTen.getText()
									+ "%' OR [dbo].[convertUnicodetoASCII](Ten) LIKE '%" + textTen.getText() + "%')";
					String khuvuc = textKhuvuc.getText().isEmpty() ? ""
							: " AND (Location LIKE N'%" + textKhuvuc.getText()
									+ "%' OR [dbo].[convertUnicodetoASCII](Location) LIKE '%" + textKhuvuc.getText()
									+ "%')";
					String ip = textIP.getText().isEmpty() ? "" : " AND IP LIKE '%" + textIP.getText() + "'";
					String loaimay = textLoaimay.getText().isEmpty() ? ""
							: " AND (LoaiMay.LoaiMay LIKE N'%" + textLoaimay.getText()
									+ "%' OR [dbo].[convertUnicodetoASCII](LoaiMay.LoaiMay) LIKE '%"
									+ textLoaimay.getText() + "%')";

					// xử lý tìm theo lớp IP, nếu textIP nhận vào là lớp IP ví dụ: 30 thay vì nhận
					// được là 30.1 thì ta tìm theo lớp IP chứ không tìm theo IP
					int lopip = -1;
					try {
						lopip = Integer.parseInt(textIP.getText());
					} catch (NumberFormatException ne) {

					}
					if (lopip >= 0) {
						ip = textIP.getText().isEmpty() ? "" : " AND IP LIKE '%" + "%.%." + lopip + "." + "%'";
					}

					selectDanhSachIP = "SELECT Ten,Location.Location,IP,LoaiMay.LoaiMay,GhiChu,NgayCapNhat FROM DanhSachIP,Location,LoaiMay WHERE DanhSachIP.MaLocation=Location.MaLocation AND DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay"
							+ ten + khuvuc + ip + loaimay + " ORDER BY NgayCapNhat DESC";

					// Xử lý dữ liệu tìm kiếm, hiển thị lên Table
					result = connect.getStatement().executeQuery(selectDanhSachIP);
					int stt = 1;
					while (result.next()) {
						String ngaycapnhat = result.getString(6);
						try {
							ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
									+ ngaycapnhat.substring(0, 4);
						} catch (IndexOutOfBoundsException ie) {
							ngaycapnhat = "";
						}
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { stt + "", result.getString(1), result.getString(2),
								result.getString(3), result.getString(4), result.getString(5), ngaycapnhat });
						stt++;
					}

					result.close();

				} catch (Exception se) {
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					if (ngonngu == 0) {
						thongbao.setText("Thông báo");
						thongbao.setMessage("Lỗi kết nối!");
					} else {
						thongbao.setText("Notification");
						thongbao.setMessage("Connect error!");
					}
					thongbao.setMessage(se.toString());
					thongbao.open();
				} finally {
					try {
						connect.closeStatement();
					} catch (Exception se2) {
					}
				}
			}
		});

	}

	// Hiện trong CTabFolder
	// *****************************************************************************************************************************************************
	public void showTabFolder(CTabFolder tabfolder, Shell shell, int ngonngu) {
		this.ngonngu = ngonngu;
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("IP");

		Composite composite = new Composite(tabfolder, SWT.EMBEDDED);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		composite.setLayout(null);
		itemtab.setControl(composite);

		createContents(composite, shell, ngonngu);
		table.setSize(Display.getDefault().getBounds().width - 20, Display.getDefault().getBounds().height - 200);
	}

	// Đọc file excel ghi vào mảng
	// ==========================================================================================================================================================================================
	private void readXLSFile(String filename) {
		try {
			InputStream ExcelFileToRead = new FileInputStream(filename);
			HSSFWorkbook worbook = new HSSFWorkbook(ExcelFileToRead);

			// khai bao
			excelname = new ArrayList<String>();
			excellocationcode = new ArrayList<String>();
			excellocation = new ArrayList<String>();
			excelip = new ArrayList<String>();
			excelmachinecode = new ArrayList<String>();
			excelmachinetype = new ArrayList<String>();
			excelnote = new ArrayList<String>();

			HSSFSheet sheet = worbook.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int vitricell = 1;
				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();
					try {
						if (vitricell == 1) {
							excelname.add(cell.getStringCellValue());
						}
						if (vitricell == 2) {
							excellocationcode.add(cell.getStringCellValue());
						}
						if (vitricell == 3) {
							excellocation.add(cell.getStringCellValue());
						}
						if (vitricell == 4) {
							excelip.add(cell.getStringCellValue());
						}
						if (vitricell == 5) {
							excelmachinecode.add(cell.getStringCellValue());
						}
						if (vitricell == 6) {
							excelmachinetype.add(cell.getStringCellValue());
						}
						if (vitricell == 7) {
							excelnote.add(cell.getStringCellValue());
						}
					} catch (Exception exc) {

					}

					vitricell++;
				}
			}
			worbook.close();
		} catch (Exception eee) {

		}
	}

	// tìm kiếm
	// -----------------------------------------------------------------------------------------------------------------------------------------------
	public void search(Shell shell, int ngonngu) {
		try {
			if (connect == null) {
				connect = new ConnectSQL();
				connect.setConnection();
			}
			connect.setStatement();

			String selectDanhSachIP = "";
			ResultSet result;
			table.removeAll();

			// text find
			String ten = textTen.getText().isEmpty() ? ""
					: " AND (Ten LIKE N'%" + textTen.getText() + "%' OR [dbo].[convertUnicodetoASCII](Ten) LIKE '%"
							+ textTen.getText() + "%')";
			String khuvuc = textKhuvuc.getText().isEmpty() ? ""
					: " AND (Location LIKE N'%" + textKhuvuc.getText()
							+ "%' OR [dbo].[convertUnicodetoASCII](Location) LIKE '%" + textKhuvuc.getText() + "%')";
			String ip = textIP.getText().isEmpty() ? "" : " AND IP LIKE '%" + textIP.getText() + "'";
			String loaimay = textLoaimay.getText().isEmpty() ? ""
					: " AND (LoaiMay.LoaiMay LIKE N'%" + textLoaimay.getText()
							+ "%' OR [dbo].[convertUnicodetoASCII](LoaiMay.LoaiMay) LIKE '%" + textLoaimay.getText()
							+ "%')";

			// xử lý tìm theo lớp IP, nếu textIP nhận vào là lớp IP ví dụ: 30 thay vì nhận
			// được là 30.1 thì ta tìm theo lớp IP chứ không tìm theo IP
			int lopip = -1;
			try {
				lopip = Integer.parseInt(textIP.getText());
			} catch (NumberFormatException ne) {

			}
			if (lopip >= 0) {
				ip = textIP.getText().isEmpty() ? "" : " AND IP LIKE '%" + "%.%." + lopip + "." + "%'";
			}

			selectDanhSachIP = "SELECT Ten,Location.Location,IP,LoaiMay.LoaiMay,GhiChu,NgayCapNhat FROM DanhSachIP,Location,LoaiMay WHERE DanhSachIP.MaLocation=Location.MaLocation AND DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay"
					+ ten + khuvuc + ip + loaimay + " ORDER BY NgayCapNhat DESC";

			// Xử lý dữ liệu tìm kiếm, hiển thị lên Table
			result = connect.getStatement().executeQuery(selectDanhSachIP);
			int stt = 1;
			while (result.next()) {
				String ngaycapnhat = result.getString(6);
				try {
					ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
							+ ngaycapnhat.substring(0, 4);
				} catch (IndexOutOfBoundsException ie) {
					ngaycapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { stt + "", result.getString(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), ngaycapnhat });
				stt++;
			}

			result.close();

		} catch (Exception se) {
			MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			if (ngonngu == 0) {
				thongbao.setText("Thông báo");
				thongbao.setMessage("Lỗi kết nối!");
			} else {
				thongbao.setText("Notification");
				thongbao.setMessage("Connect error!");
			}
			thongbao.setMessage(se.toString());
			thongbao.open();
		} finally {
			try {
				connect.closeStatement();
			} catch (Exception se2) {
				// nothing we can do
			}
		}
	}
}
