package cctv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class Report {

	protected Shell shell;
	private Table table;
	private Connection conn = null;
	private Statement statement = null;

	public static void main(String[] args) {
		try {
			Report window = new Report();
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
		createContents(0, "jdbc:sqlserver://192.168.30.123;databaseName=IPManagerCCTV;user=sa;password=Killua21608");
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(int ngonngu, String db_url) {
		shell = new Shell();
		shell.setSize(1166, 667);
		shell.setText("Report");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);

		Button btncheckLocation = new Button(composite, SWT.CHECK);
		btncheckLocation.setSelection(true);
		btncheckLocation.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btncheckLocation.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btncheckLocation.setBounds(81, 20, 122, 30);
		btncheckLocation.setText("Location");

		Button btncheckIP = new Button(composite, SWT.CHECK);
		btncheckIP.setSelection(true);
		btncheckIP.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btncheckIP.setText("IP");
		btncheckIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btncheckIP.setBounds(234, 20, 56, 30);

		Button btncheckMachinetype = new Button(composite, SWT.CHECK);
		btncheckMachinetype.setSelection(true);
		btncheckMachinetype.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		btncheckMachinetype.setText("Machine Type");
		btncheckMachinetype.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btncheckMachinetype.setBounds(329, 20, 149, 30);

		Button btnStatistic = new Button(composite, SWT.NONE);
		btnStatistic.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnStatistic.setImage(SWTResourceManager.getImage(Report.class, "/cctv/Icon/bieutuong/report.png"));
		btnStatistic.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnStatistic.setBounds(484, 20, 132, 35);
		btnStatistic.setText("Statistic");

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		table.setBounds(10, 65, 1130, 553);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(Display.getDefault().getBounds().width - 20, Display.getDefault().getBounds().height - 150);

		TableColumn tbclLocation = new TableColumn(table, SWT.NONE);
		tbclLocation.setWidth(284);
		tbclLocation.setText("Location");

		TableColumn tbclCountLocation = new TableColumn(table, SWT.NONE);
		tbclCountLocation.setWidth(111);
		tbclCountLocation.setText("Count");

		TableColumn tbclIP = new TableColumn(table, SWT.NONE);
		tbclIP.setWidth(118);
		tbclIP.setText("IP");

		TableColumn tbclCountIP = new TableColumn(table, SWT.NONE);
		tbclCountIP.setWidth(118);
		tbclCountIP.setText("Count");

		TableColumn tbclMachinetype = new TableColumn(table, SWT.NONE);
		tbclMachinetype.setWidth(294);
		tbclMachinetype.setText("Machine Type");

		TableColumn tbclCountMachine = new TableColumn(table, SWT.NONE);
		tbclCountMachine.setWidth(114);
		tbclCountMachine.setText("Count");

		// language
		// **************************************************************************************************************************************************
		if (ngonngu == 0) {
			shell.setText("Báo cáo");
			btncheckLocation.setText("Khu Vực");
			btncheckIP.setText("IP");
			btncheckMachinetype.setText("Loại Máy");
			btnStatistic.setText("Thống Kê");
			tbclLocation.setText("Khu Vực");
			tbclCountLocation.setText("Số Lượng");
			tbclIP.setText("IP");
			tbclCountIP.setText("Số Lượng");
			tbclMachinetype.setText("Loại Máy");
			tbclCountMachine.setText("Số Lượng");
		} else {
			shell.setText("Report");
			btncheckLocation.setText("Location");
			btncheckIP.setText("IP");
			btncheckMachinetype.setText("Machine Type");
			btnStatistic.setText("Statistic");
			tbclLocation.setText("Location");
			tbclCountLocation.setText("Count");
			tbclIP.setText("IP");
			tbclCountIP.setText("Count");
			tbclMachinetype.setText("Machine Type");
			tbclCountMachine.setText("Count");
		}

		// button Statistic
		// **************************************************************************************************************************************************
		btnStatistic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);

					table.removeAll();
					ArrayList<ArrayList<String>> dslocation = new ArrayList<ArrayList<String>>();
					int countdong = 0;
					int dem = 0;
					int countlocation = 0;
					int countip = 0;
					int countmachine = 0;

					// tinh tong so luong IP da dung
					String total = "";
					String selecttotal = "SELECT COUNT(*) AS TOTAL FROM DanhSachIP";
					Statement state = conn.createStatement();
					ResultSet ketqua = state.executeQuery(selecttotal);
					while (ketqua.next()) {
//						TableItem item = new TableItem(table, SWT.NONE);
//						item.setText(new String[] { "Total", ketqua.getString(1) });
//						item.setBackground(0, Display.getDefault().getSystemColor(SWT.COLOR_RED));
//						item.setBackground(1, Display.getDefault().getSystemColor(SWT.COLOR_RED));
						total = ketqua.getString(1);
					}
					ketqua.close();
					state.close();

					if (btncheckLocation.getSelection()) {
						tbclLocation.setWidth(284);
						tbclCountLocation.setWidth(111);
						tbclLocation.setResizable(true);
						tbclCountLocation.setResizable(true);

						statement = conn.createStatement();
						String select = "SELECT DISTINCT Location.Location,(SELECT COUNT(ds.MaLocation) FROM DanhSachIP AS ds WHERE ds.MaLocation=DanhSachIP.MaLocation) FROM DanhSachIP,Location WHERE DanhSachIP.MaLocation=Location.MaLocation ORDER BY Location.Location ASC";
						ResultSet result = statement.executeQuery(select);

						while (result.next()) {
							ArrayList<String> currentlist = new ArrayList<String>();
							currentlist.add(result.getString(1));
							currentlist.add(result.getString(2));
							dslocation.add(currentlist);
							dem++;
						}
						countdong = dem + 1;
						countlocation = countdong;
						dem = 0;
						result.close();

						ArrayList<String> currentlist = new ArrayList<String>();
						currentlist.add("Total");
						currentlist.add(total);
						dslocation.add(currentlist);
					} else {
						tbclLocation.setWidth(0);
						tbclLocation.setResizable(false);
						tbclCountLocation.setWidth(0);
						tbclCountLocation.setResizable(false);
					}

					// Thong ke IP
					ArrayList<ArrayList<String>> dsip = new ArrayList<ArrayList<String>>();
					if (btncheckIP.getSelection()) {
						tbclIP.setWidth(118);
						tbclCountIP.setWidth(118);
						tbclIP.setResizable(true);
						tbclCountIP.setResizable(true);
						try {
							String bangtam = "SELECT CAST(SUBSTRING(DanhSachIP.IP,9,(SELECT CHARINDEX('.',DanhSachIP.IP,9)-9)) AS INT) AS LopIP INTO ##LopIP FROM DanhSachIP";
							Statement statebangtam = conn.createStatement();
							statebangtam.executeUpdate(bangtam);
							String selectbangtam = "SELECT DISTINCT ##LopIP.LopIP,(SELECT COUNT(*) FROM ##LopIP AS LopIP2 WHERE LopIP2.LopIP=##LopIP.LopIP) FROM ##LopIP ORDER BY ##LopIP.LopIP DESC";
							Statement stateip = conn.createStatement();
							ResultSet resultbangtam = stateip.executeQuery(selectbangtam);

							while (resultbangtam.next()) {
								ArrayList<String> currentlist = new ArrayList<String>();
								currentlist.add(resultbangtam.getString(1));
								currentlist.add(resultbangtam.getString(2));
								dsip.add(currentlist);
								dem++;
							}
							countip = dem + 1;
							countdong = countdong < countip ? countip : countdong;
							dem = 0;
							resultbangtam.close();
							stateip.close();
							statebangtam.close();

							ArrayList<String> currentlistip = new ArrayList<String>();
							currentlistip.add("Total");
							currentlistip.add(total);
							dsip.add(currentlistip);
						} catch (SQLException sqlex) {
							// System.out.println(sqlex.toString());
						}
					} else {
						tbclIP.setWidth(0);
						tbclIP.setResizable(false);
						tbclCountIP.setWidth(0);
						tbclCountIP.setResizable(false);
					}

					// thong ke Machine Type
					ArrayList<ArrayList<String>> dsmachine = new ArrayList<ArrayList<String>>();
					if (btncheckMachinetype.getSelection()) {
						tbclMachinetype.setWidth(294);
						tbclCountMachine.setWidth(114);
						tbclMachinetype.setResizable(true);
						tbclCountMachine.setResizable(true);

						try {
							String selectloaimay = "SELECT DISTINCT LoaiMay.LoaiMay,(SELECT COUNT(*) FROM DanhSachIP AS DS WHERE DS.MaLoaiMay=DanhSachIP.MaLoaiMay) FROM DanhSachIP,LoaiMay WHERE DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay";
							Statement stateloaimay = conn.createStatement();
							ResultSet kqloaimay = stateloaimay.executeQuery(selectloaimay);

							while (kqloaimay.next()) {
								ArrayList<String> currentlist = new ArrayList<String>();
								currentlist.add(kqloaimay.getString(1));
								currentlist.add(kqloaimay.getString(2));
								dsmachine.add(currentlist);
								dem++;
							}
							countmachine = dem + 1;
							countdong = countdong < countmachine ? countmachine : countdong;
							dem = 0;
							kqloaimay.close();
							stateloaimay.close();

							ArrayList<String> currentlist = new ArrayList<String>();
							currentlist.add("Total");
							currentlist.add(total);
							dsmachine.add(currentlist);

						} catch (SQLException sqlee) {

						}
					} else {
						tbclMachinetype.setWidth(0);
						tbclMachinetype.setResizable(false);
						tbclCountMachine.setWidth(0);
						tbclCountMachine.setResizable(false);
					}

					String[][] thongke = new String[countdong][6];
					for (int i = 0; i < countdong; i++) {
						for (int j = 0; j < 6; j++) {
							thongke[i][j] = "";
						}
					}
					int dong = 0;
					try {
						if (btncheckLocation.getSelection()) {
							for (ArrayList<String> itemrow : dslocation) {
								thongke[dong][0] = itemrow.get(0);
								thongke[dong][1] = itemrow.get(1);
								dong++;
							}
						}
					} catch (Exception arr) {

					}
					dong = 0;
					try {
						if (btncheckIP.getSelection()) {
							for (ArrayList<String> itemrow : dsip) {
								thongke[dong][2] = itemrow.get(0);
								thongke[dong][3] = itemrow.get(1);
								dong++;
							}
						}
					} catch (Exception arr) {

					}
					dong = 0;
					try {
						if (btncheckMachinetype.getSelection()) {
							for (ArrayList<String> itemrow : dsmachine) {
								thongke[dong][4] = itemrow.get(0);
								thongke[dong][5] = itemrow.get(1);
								dong++;
							}
						}
					} catch (Exception arr) {

					}

					// hien thi len table

					try {
						for (int i = 0; i < countdong; i++) {
							TableItem item = new TableItem(table, SWT.NONE);
//							item.setText(new String[] { thongke[i][0], thongke[i][1], thongke[i][2], thongke[i][3],
//									thongke[i][4], thongke[i][5] });

							try {
								if (btncheckLocation.getSelection()) {
									item.setText(0, thongke[i][0]);
									item.setText(1, thongke[i][1]);
									if (i < countlocation - 1) {
										item.setBackground(0, Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
									}
									if (i == countlocation - 1) {
										item.setBackground(0,
												Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
										item.setBackground(1, Display.getDefault().getSystemColor(SWT.COLOR_RED));
									}
								}
							} catch (Exception ae) {

							}
							try {
								if (btncheckIP.getSelection()) {
									item.setText(2, thongke[i][2]);
									item.setText(3, thongke[i][3]);
									if (i < countip - 1) {
										item.setBackground(2, Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
									}
									if (i == countip - 1) {
										item.setBackground(2,
												Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
										item.setBackground(3, Display.getDefault().getSystemColor(SWT.COLOR_RED));
									}
								}
							} catch (Exception ae) {

							}
							try {
								if (btncheckMachinetype.getSelection()) {
									item.setText(4, thongke[i][4]);
									item.setText(5, thongke[i][5]);
									if (i < countmachine - 1) {
										item.setBackground(4, Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
									}
									if (i == countmachine - 1) {
										item.setBackground(4,
												Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
										item.setBackground(5, Display.getDefault().getSystemColor(SWT.COLOR_RED));
									}
								}
							} catch (Exception ae) {

							}
						}
					} catch (Exception ae) {

					}

//					// hien thi len table
//					for(ArrayList<String> itemrow:dslocation) {
//						TableItem itemlocation = new TableItem(table, SWT.NONE);
//						itemlocation.setText(0, itemrow.get(0));
//						itemlocation.setText(1, itemrow.get(1));
//					}

				} catch (SQLException se) {
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
						if (statement != null) {
							statement.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException se2) {
						// nothing we can do
					}
				}
			}
		});
	}

	// Hien trong Ctabfolder
	// ************************************************************************************************************************************************************
	protected void showTabFolder(CTabFolder tabfolder, Shell shell, int ngonngu, String db_url) {
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("IP list");

		Composite composite = new Composite(tabfolder, SWT.EMBEDDED);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 10, SWT.NORMAL));
		itemtab.setControl(composite);

		Button btncheckLocation = new Button(composite, SWT.CHECK);
		btncheckLocation.setSelection(true);
		btncheckLocation.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btncheckLocation.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btncheckLocation.setBounds(81, 20, 122, 30);
		btncheckLocation.setText("Location");

		Button btncheckIP = new Button(composite, SWT.CHECK);
		btncheckIP.setSelection(true);
		btncheckIP.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btncheckIP.setText("IP");
		btncheckIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btncheckIP.setBounds(234, 20, 56, 30);

		Button btncheckMachinetype = new Button(composite, SWT.CHECK);
		btncheckMachinetype.setSelection(true);
		btncheckMachinetype.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		btncheckMachinetype.setText("Machine Type");
		btncheckMachinetype.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btncheckMachinetype.setBounds(329, 20, 149, 30);

		Button btnStatistic = new Button(composite, SWT.NONE);
		btnStatistic.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnStatistic.setImage(SWTResourceManager.getImage(Report.class, "/cctv/Icon/bieutuong/report.png"));
		btnStatistic.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnStatistic.setBounds(484, 20, 132, 35);
		btnStatistic.setText("Statistic");

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		table.setBounds(10, 65, 1130, 553);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		// table.setSize(Display.getDefault().getBounds().width - 20,
		// Display.getDefault().getBounds().height - 150);
		table.setSize(Display.getDefault().getBounds().width - 25, Display.getDefault().getBounds().height - 180);

		TableColumn tbclLocation = new TableColumn(table, SWT.NONE);
		tbclLocation.setWidth(284);
		tbclLocation.setText("Location");

		TableColumn tbclCountLocation = new TableColumn(table, SWT.NONE);
		tbclCountLocation.setWidth(111);
		tbclCountLocation.setText("Count");

		TableColumn tbclIP = new TableColumn(table, SWT.NONE);
		tbclIP.setWidth(118);
		tbclIP.setText("IP");

		TableColumn tbclCountIP = new TableColumn(table, SWT.NONE);
		tbclCountIP.setWidth(118);
		tbclCountIP.setText("Count");

		TableColumn tbclMachinetype = new TableColumn(table, SWT.NONE);
		tbclMachinetype.setWidth(294);
		tbclMachinetype.setText("Machine Type");

		TableColumn tbclCountMachine = new TableColumn(table, SWT.NONE);
		tbclCountMachine.setWidth(114);
		tbclCountMachine.setText("Count");

		// language
		// **************************************************************************************************************************************************
		if (ngonngu == 0) {
			itemtab.setText("Báo cáo");
			btncheckLocation.setText("Khu Vực");
			btncheckIP.setText("IP");
			btncheckMachinetype.setText("Loại Máy");
			btnStatistic.setText("Thống Kê");
			tbclLocation.setText("Khu Vực");
			tbclCountLocation.setText("Số Lượng");
			tbclIP.setText("IP");
			tbclCountIP.setText("Số Lượng");
			tbclMachinetype.setText("Loại Máy");
			tbclCountMachine.setText("Số Lượng");
		} else {
			itemtab.setText("Report");
			btncheckLocation.setText("Location");
			btncheckIP.setText("IP");
			btncheckMachinetype.setText("Machine Type");
			btnStatistic.setText("Statistic");
			tbclLocation.setText("Location");
			tbclCountLocation.setText("Count");
			tbclIP.setText("IP");
			tbclCountIP.setText("Count");
			tbclMachinetype.setText("Machine Type");
			tbclCountMachine.setText("Count");
		}

		// button Statistic
		// **************************************************************************************************************************************************
		btnStatistic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);

					table.removeAll();
					ArrayList<ArrayList<String>> dslocation = new ArrayList<ArrayList<String>>();
					int countdong = 0;
					int dem = 0;
					int countlocation = 0;
					int countip = 0;
					int countmachine = 0;

					// tinh tong so luong IP da dung
					String total = "";
					String selecttotal = "SELECT COUNT(*) AS TOTAL FROM DanhSachIP";
					Statement state = conn.createStatement();
					ResultSet ketqua = state.executeQuery(selecttotal);
					while (ketqua.next()) {
//						TableItem item = new TableItem(table, SWT.NONE);
//						item.setText(new String[] { "Total", ketqua.getString(1) });
//						item.setBackground(0, Display.getDefault().getSystemColor(SWT.COLOR_RED));
//						item.setBackground(1, Display.getDefault().getSystemColor(SWT.COLOR_RED));
						total = ketqua.getString(1);
					}
					ketqua.close();
					state.close();

					if (btncheckLocation.getSelection()) {
						tbclLocation.setWidth(284);
						tbclCountLocation.setWidth(111);
						tbclLocation.setResizable(true);
						tbclCountLocation.setResizable(true);

						statement = conn.createStatement();
						String select = "SELECT DISTINCT Location.Location,(SELECT COUNT(ds.MaLocation) FROM DanhSachIP AS ds WHERE ds.MaLocation=DanhSachIP.MaLocation) FROM DanhSachIP,Location WHERE DanhSachIP.MaLocation=Location.MaLocation ORDER BY Location.Location ASC";
						ResultSet result = statement.executeQuery(select);

						while (result.next()) {
							ArrayList<String> currentlist = new ArrayList<String>();
							currentlist.add(result.getString(1));
							currentlist.add(result.getString(2));
							dslocation.add(currentlist);
							dem++;
						}
						countdong = dem + 1;
						countlocation = countdong;
						dem = 0;
						result.close();

						ArrayList<String> currentlist = new ArrayList<String>();
						currentlist.add("Total");
						currentlist.add(total);
						dslocation.add(currentlist);
					} else {
						tbclLocation.setWidth(0);
						tbclLocation.setResizable(false);
						tbclCountLocation.setWidth(0);
						tbclCountLocation.setResizable(false);
					}

					// Thong ke IP
					ArrayList<ArrayList<String>> dsip = new ArrayList<ArrayList<String>>();
					if (btncheckIP.getSelection()) {
						tbclIP.setWidth(118);
						tbclCountIP.setWidth(118);
						tbclIP.setResizable(true);
						tbclCountIP.setResizable(true);
						try {
							String bangtam = "SELECT CAST(SUBSTRING(DanhSachIP.IP,9,(SELECT CHARINDEX('.',DanhSachIP.IP,9)-9)) AS INT) AS LopIP INTO ##LopIP FROM DanhSachIP";
							Statement statebangtam = conn.createStatement();
							statebangtam.executeUpdate(bangtam);
							String selectbangtam = "SELECT DISTINCT ##LopIP.LopIP,(SELECT COUNT(*) FROM ##LopIP AS LopIP2 WHERE LopIP2.LopIP=##LopIP.LopIP) FROM ##LopIP ORDER BY ##LopIP.LopIP DESC";
							Statement stateip = conn.createStatement();
							ResultSet resultbangtam = stateip.executeQuery(selectbangtam);

							while (resultbangtam.next()) {
								ArrayList<String> currentlist = new ArrayList<String>();
								currentlist.add(resultbangtam.getString(1));
								currentlist.add(resultbangtam.getString(2));
								dsip.add(currentlist);
								dem++;
							}
							countip = dem + 1;
							countdong = countdong < countip ? countip : countdong;
							dem = 0;
							resultbangtam.close();
							stateip.close();
							statebangtam.close();

							ArrayList<String> currentlistip = new ArrayList<String>();
							currentlistip.add("Total");
							currentlistip.add(total);
							dsip.add(currentlistip);
						} catch (SQLException sqlex) {
							// System.out.println(sqlex.toString());
						}
					} else {
						tbclIP.setWidth(0);
						tbclIP.setResizable(false);
						tbclCountIP.setWidth(0);
						tbclCountIP.setResizable(false);
					}

					// thong ke Machine Type
					ArrayList<ArrayList<String>> dsmachine = new ArrayList<ArrayList<String>>();
					if (btncheckMachinetype.getSelection()) {
						tbclMachinetype.setWidth(294);
						tbclCountMachine.setWidth(114);
						tbclMachinetype.setResizable(true);
						tbclCountMachine.setResizable(true);

						try {
							String selectloaimay = "SELECT DISTINCT LoaiMay.LoaiMay,(SELECT COUNT(*) FROM DanhSachIP AS DS WHERE DS.MaLoaiMay=DanhSachIP.MaLoaiMay) FROM DanhSachIP,LoaiMay WHERE DanhSachIP.MaLoaiMay=LoaiMay.MaLoaiMay";
							Statement stateloaimay = conn.createStatement();
							ResultSet kqloaimay = stateloaimay.executeQuery(selectloaimay);

							while (kqloaimay.next()) {
								ArrayList<String> currentlist = new ArrayList<String>();
								currentlist.add(kqloaimay.getString(1));
								currentlist.add(kqloaimay.getString(2));
								dsmachine.add(currentlist);
								dem++;
							}
							countmachine = dem + 1;
							countdong = countdong < countmachine ? countmachine : countdong;
							dem = 0;
							kqloaimay.close();
							stateloaimay.close();

							ArrayList<String> currentlist = new ArrayList<String>();
							currentlist.add("Total");
							currentlist.add(total);
							dsmachine.add(currentlist);

						} catch (SQLException sqlee) {

						}
					} else {
						tbclMachinetype.setWidth(0);
						tbclMachinetype.setResizable(false);
						tbclCountMachine.setWidth(0);
						tbclCountMachine.setResizable(false);
					}

					String[][] thongke = new String[countdong][6];
					for (int i = 0; i < countdong; i++) {
						for (int j = 0; j < 6; j++) {
							thongke[i][j] = "";
						}
					}
					int dong = 0;
					try {
						if (btncheckLocation.getSelection()) {
							for (ArrayList<String> itemrow : dslocation) {
								thongke[dong][0] = itemrow.get(0);
								thongke[dong][1] = itemrow.get(1);
								dong++;
							}
						}
					} catch (Exception arr) {

					}
					dong = 0;
					try {
						if (btncheckIP.getSelection()) {
							for (ArrayList<String> itemrow : dsip) {
								thongke[dong][2] = itemrow.get(0);
								thongke[dong][3] = itemrow.get(1);
								dong++;
							}
						}
					} catch (Exception arr) {

					}
					dong = 0;
					try {
						if (btncheckMachinetype.getSelection()) {
							for (ArrayList<String> itemrow : dsmachine) {
								thongke[dong][4] = itemrow.get(0);
								thongke[dong][5] = itemrow.get(1);
								dong++;
							}
						}
					} catch (Exception arr) {

					}

					// hien thi len table

					try {
						for (int i = 0; i < countdong; i++) {
							TableItem item = new TableItem(table, SWT.NONE);
//							item.setText(new String[] { thongke[i][0], thongke[i][1], thongke[i][2], thongke[i][3],
//									thongke[i][4], thongke[i][5] });

							try {
								if (btncheckLocation.getSelection()) {
									item.setText(0, thongke[i][0]);
									item.setText(1, thongke[i][1]);
									if (i < countlocation - 1) {
										item.setBackground(0, Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
									}
									if (i == countlocation - 1) {
										item.setBackground(0,
												Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
										item.setBackground(1, Display.getDefault().getSystemColor(SWT.COLOR_RED));
									}
								}
							} catch (Exception ae) {

							}
							try {
								if (btncheckIP.getSelection()) {
									item.setText(2, thongke[i][2]);
									item.setText(3, thongke[i][3]);
									if (i < countip - 1) {
										item.setBackground(2, Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
									}
									if (i == countip - 1) {
										item.setBackground(2,
												Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
										item.setBackground(3, Display.getDefault().getSystemColor(SWT.COLOR_RED));
									}
								}
							} catch (Exception ae) {

							}
							try {
								if (btncheckMachinetype.getSelection()) {
									item.setText(4, thongke[i][4]);
									item.setText(5, thongke[i][5]);
									if (i < countmachine - 1) {
										item.setBackground(4, Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
									}
									if (i == countmachine - 1) {
										item.setBackground(4,
												Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
										item.setBackground(5, Display.getDefault().getSystemColor(SWT.COLOR_RED));
									}
								}
							} catch (Exception ae) {

							}
						}
					} catch (Exception ae) {

					}

//					// hien thi len table
//					for(ArrayList<String> itemrow:dslocation) {
//						TableItem itemlocation = new TableItem(table, SWT.NONE);
//						itemlocation.setText(0, itemrow.get(0));
//						itemlocation.setText(1, itemrow.get(1));
//					}

				} catch (SQLException se) {
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
						if (statement != null) {
							statement.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException se2) {
						// nothing we can do
					}
				}
			}
		});
	}
}
