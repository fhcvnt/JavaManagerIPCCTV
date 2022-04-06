package cctv;

import java.sql.ResultSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import sort.SortData;
import sql.ConnectSQL;

public class Location {
	protected Shell shell;
	private Composite compositeshell;
	private ConnectSQL connect;
	private boolean them = false, sua = false;
	// Sử dụng cho nút Sửa
	private String maLocation_sua, Location_sua;
	// vị trí dòng sửa
	private int index_sua = -1;
	private Table table;

	public static void main(String[] args) {
		try {
			Location window = new Location();
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
		createContents(compositeshell,shell, 0);
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
		shell.setImage(SWTResourceManager.getImage(Location.class, "/cctv/Icon/IP.ico"));
		shell.setSize(1404, 678);
		shell.setMaximized(true);
		shell.setText("Location");

		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		shell.setLayout(fillLayout);
		compositeshell = new Composite(shell, SWT.NONE);
		compositeshell.setLayout(fillLayout);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Composite composite2, Shell shell, int ngonngu) {
		Composite composite = new Composite(composite2, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.VERTICAL);
		rl_composite.wrap = false;
		rl_composite.marginTop = 0;
		rl_composite.spacing = 0;
		rl_composite.marginLeft = 5;
		composite.setLayout(rl_composite);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.marginLeft = 10;
		rl_composite_1.marginTop = 10;
		rl_composite_1.spacing = 10;
		composite_1.setLayout(rl_composite_1);

		Label lbMaLocation = new Label(composite_1, SWT.CENTER);
		lbMaLocation.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbMaLocation.setText("Mã Khu Vực");

		Text textMaLocation = new Text(composite_1, SWT.BORDER | SWT.CENTER);
		textMaLocation.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textMaLocation.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textMaLocation.setLayoutData(new RowData(169, SWT.DEFAULT));

		Label lbLocation = new Label(composite_1, SWT.CENTER);
		lbLocation.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbLocation.setText("Khu Vực");

		Text textLocation = new Text(composite_1, SWT.BORDER | SWT.CENTER);
		textLocation.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textLocation.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textLocation.setLayoutData(new RowData(224, SWT.DEFAULT));

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new RowLayout(SWT.VERTICAL));

		table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);

		table.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		//table.setLayoutData(new RowData(1330, 580));
		table.setLayoutData(new RowData(Display.getDefault().getBounds().width - 40, Display.getDefault().getBounds().height-190));
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setSize(1080,1920);

		table.setHeaderVisible(true);

		TableColumn tbcNumber = new TableColumn(table, SWT.NONE);
		tbcNumber.setWidth(128);
		tbcNumber.setText("STT");

		TableColumn tbcMaLocation = new TableColumn(table, SWT.NONE);
		tbcMaLocation.setWidth(258);
		tbcMaLocation.setText("Mã khu vực");

		TableColumn tbcLocation = new TableColumn(table, SWT.NONE);
		tbcLocation.setWidth(400);
		tbcLocation.setText("Khu vực");

		Button btnThem = new Button(composite_1, SWT.CENTER);
		btnThem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnThem.setLayoutData(new RowData(62, SWT.DEFAULT));

		btnThem.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnThem.setText("Thêm");

		Button btnSua = new Button(composite_1, SWT.CENTER);
		btnSua.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnSua.setLayoutData(new RowData(61, SWT.DEFAULT));

		btnSua.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnSua.setText("Sửa");

		Button btnXoa = new Button(composite_1, SWT.CENTER);
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnXoa.setLayoutData(new RowData(72, SWT.DEFAULT));

		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnXoa.setText("Xóa");

		Button btnTimKiem = new Button(composite_1, SWT.CENTER);
		btnTimKiem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTimKiem.setLayoutData(new RowData(91, SWT.DEFAULT));

		btnTimKiem.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnTimKiem.setText("Tìm kiếm");

		Button btnLuu = new Button(composite_1, SWT.NONE);
		btnLuu.setLayoutData(new RowData(58, SWT.DEFAULT));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnLuu.setText("Lưu");
		btnLuu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));

		Button btnHuy = new Button(composite_1, SWT.NONE);
		btnHuy.setLayoutData(new RowData(80, SWT.DEFAULT));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnHuy.setText("Hủy");
		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		// Set Language
		// --------------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Khu vực");
			tbcNumber.setText("STT");
			lbMaLocation.setText("Mã Khu Vực");
			lbLocation.setText("Khu Vực");
			tbcMaLocation.setText("Mã Khu Vực");
			tbcLocation.setText("Khu Vực");
			btnThem.setText("Thêm");
			btnSua.setText("Sửa");
			btnXoa.setText("Xóa");
			btnTimKiem.setText("Tìm kiếm");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
			
			tbcNumber.setText("STT");
			tbcMaLocation.setText("Mã khu vực");
			tbcLocation.setText("Khu vực");
			
		} else {
			// English
			shell.setText("Location");
			tbcNumber.setText("Number");
			lbMaLocation.setText("Location Code");
			lbLocation.setText("Location");
			tbcMaLocation.setText("Location Code");
			tbcLocation.setText("Location");
			btnThem.setText("Add");
			btnSua.setText("Edit");
			btnXoa.setText("Delete");
			btnTimKiem.setText("Search");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
			
			tbcNumber.setText("Number");
			tbcMaLocation.setText("Location id");
			tbcLocation.setText("Location");
		}

		// Ẩn button Luu, button Huy
		// *******************************************************
		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// Xử lý sự kiện Thêm
		// **************************************************************
		btnThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				them = true;
				sua = false;
				textMaLocation.setText("");
				textLocation.setText("");

				btnLuu.setVisible(true);
				btnHuy.setVisible(true);

				btnThem.setEnabled(true);
				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
				btnTimKiem.setEnabled(false);
			}
		});

		// Xử lý sự kiện Sửa
		// *****************************************************************
		btnSua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sua = true;
				them = false;
				textMaLocation.setText("");
				textLocation.setText("");
				try {
					TableItem[] item = table.getSelection();
					maLocation_sua = item[0].getText();
					textMaLocation.setText(maLocation_sua);
					Location_sua = item[0].getText(1);
					textLocation.setText(Location_sua);
					// vị trí của item cần sửa trong bảng
					index_sua = table.indexOf(item[0]);

					btnLuu.setVisible(true);
					btnHuy.setVisible(true);

					btnSua.setEnabled(true);
					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);
					btnTimKiem.setEnabled(false);
				} catch (Exception ex) {
					btnLuu.setVisible(false);
					btnHuy.setVisible(false);

					btnThem.setEnabled(true);
					btnSua.setEnabled(true);
					btnXoa.setEnabled(true);
					btnTimKiem.setEnabled(true);
					sua = false;
				}
			}
		});

		// Xử lý sự kiện Xóa
		// *********************************************************************
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (connect == null) {
						connect = new ConnectSQL();
						connect.setConnection();
					}
					connect.setStatement();
					try {
						// Lấy cột MaLocation
						TableItem[] item = table.getSelection();
						String maLocation = item[0].getText();
						String deleteMaLocation = "DELETE Location WHERE MaLocation='" + maLocation + "'";
						try {
							connect.execUpdateQuery(deleteMaLocation);
							table.remove(table.getSelectionIndices());
						} catch (Exception ee) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Notification");
							thongbao.setMessage(ee.toString());
							thongbao.open();
						}
					} catch (Exception ex) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Không có dòng nào được chọn!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("No lines are selected!");
						}
						thongbao.open();
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

		// Xử lý sự kiện Tìm Kiếm
		// ***********************************************************************
		btnTimKiem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Xu ly SQL
				try {
					if (connect == null) {
						connect = new ConnectSQL();
						connect.setConnection();
					}
					connect.setStatement();

					String makhuvuc = textMaLocation.getText().isEmpty() ? ""
							: " WHERE MaLocation LIKE '%" + textMaLocation.getText() + "%'";
					String khuvuc = textLocation.getText().isEmpty() ? ""
							: makhuvuc.isEmpty()
									? " WHERE (Location LIKE N'%" + textLocation.getText()
											+ "%'  OR [dbo].[convertUnicodetoASCII](Location) LIKE '%"
											+ textLocation.getText() + "%')"
									: " AND (Location LIKE N'%" + textLocation.getText()
											+ "%' OR [dbo].[convertUnicodetoASCII](Location) LIKE '%"
											+ textLocation.getText() + "%')";
					String selectLocation = "SELECT MaLocation,Location FROM Location" + makhuvuc + khuvuc
							+ " ORDER BY Location ASC";
					ResultSet result = connect.getStatement().executeQuery(selectLocation);
					table.removeAll();
					int stt = 1;
					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { stt + "", result.getString(1), result.getString(2) });
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

		// Xu ly su kien Huy - Cancel
		// ********************************************************************************************************
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				them = false;
				sua = false;
				textMaLocation.setText("");
				textLocation.setText("");

				// Ẩn button Luu, button Huy
				btnLuu.setVisible(false);
				btnHuy.setVisible(false);

				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);
				btnTimKiem.setEnabled(true);
			}
		});

		// xu ly su kien Luu - Save
		// *********************************************************************************************************
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Nút Thêm được chọn
				if (them) {
					try {
						if (connect == null) {
							connect = new ConnectSQL();
							connect.setConnection();
						}
						connect.setStatement();

						try {
							String insertLocation = "INSERT INTO Location (MaLocation,Location) VALUES ('"
									+ textMaLocation.getText() + "',N'" + textLocation.getText() + "')";
							int result = connect.execUpdateQuery(insertLocation);
							if (result > 0) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { textMaLocation.getText(), textLocation.getText() });
								textMaLocation.setText("");
								textLocation.setText("");
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Thêm thành công");
								} else {
									thongbao.setText("Notification");
									thongbao.setMessage("Add success!");
								}
								thongbao.open();
							}
						} catch (Exception ex) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Thêm không thành công");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Add failed!");
							}
							thongbao.open();
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
						thongbao.open();
					} finally {
						try {
							connect.closeStatement();
						} catch (Exception se2) {
							// nothing we can do
						}
					}
				} else if (sua) {
					// Nut Sua duoc chon
					try {
						if (connect == null) {
							connect = new ConnectSQL();
							connect.setConnection();
						}
						connect.setStatement();
						try {
							String updateLocation = "UPDATE Location SET MaLocation='" + textMaLocation.getText()
									+ "' , Location=N'" + textLocation.getText() + "' WHERE MaLocation='"
									+ maLocation_sua + "'";

							int result = connect.execUpdateQuery(updateLocation);
							if (result > 0) {
								table.remove(index_sua);
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { textMaLocation.getText(), textLocation.getText(), });
								textMaLocation.setText("");
								textLocation.setText("");

								them = false;
								sua = false;

								// Ẩn button Luu, button Huy
								btnLuu.setVisible(false);
								btnHuy.setVisible(false);
								btnThem.setEnabled(true);
								btnSua.setEnabled(true);
								btnXoa.setEnabled(true);
								btnTimKiem.setEnabled(true);
							}
						} catch (Exception ex) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo!");
								thongbao.setMessage("Sửa không thành công");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Edit failed!");
							}
							thongbao.open();
						}

					} catch (Exception se) {
						// se.printStackTrace();
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
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
		});

		// Sort data
		// ************************************************************************************************
		// Sắp xếp table theo một cột đã chọn
		SortData sort = new SortData();
		sort.setTable(table);
		sort.setNumber(true);

		tbcMaLocation.addListener(SWT.Selection, sort.sortListenerCode);
		tbcLocation.addListener(SWT.Selection, sort.sortListenerCode);

	}

	// hien trong Ctabfolder
	// ================================================================================================================================================
	public void showTabFolder(CTabFolder tabfolder, Shell shell, int ngonngu) {
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		if (ngonngu == 0) {
			itemtab.setText("Khu vực");
		} else {
			itemtab.setText("Location");
		}

		Composite composite = new Composite(tabfolder, SWT.EMBEDDED);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		itemtab.setControl(composite);

		createContents(composite,shell, ngonngu);
		//table.setSize(Display.getDefault().getBounds().width - 20, Display.getDefault().getBounds().height);

	}

}
