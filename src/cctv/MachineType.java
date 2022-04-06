package cctv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class MachineType {
	private Connection conn = null;
	private Statement statement = null;
	private boolean them = false, sua = false;
	// Sử dụng cho nút Sửa
	private String maloaimay_sua, loaimay_sua;
	// vị trí dòng sửa
	private int index_sua = -1;
	private int selectcolumn = -1; // cột được chọn, nếu chọn lần nữa sẽ sắp xếp ngược lại từ Z - A

	// Hiện trong CTabFolder
	// ==================================================================================
	public void showTabFolder(CTabFolder tabfolder, Shell shell, int ngonngu, String db_url) {

		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Machine type");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);
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

		Label lbMaloaimay = new Label(composite_1, SWT.CENTER);
		lbMaloaimay.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMaloaimay.setText("Mã Loại Máy");

		Text textMaloaimay = new Text(composite_1, SWT.BORDER | SWT.CENTER);
		textMaloaimay.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textMaloaimay.setLayoutData(new RowData(169, SWT.DEFAULT));
		textMaloaimay.setBackground(SWTResourceManager.getColor(224, 255, 255));

		Label lbLoaimay = new Label(composite_1, SWT.CENTER);
		lbLoaimay.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbLoaimay.setText("Loại Máy");

		Text textLoaimay = new Text(composite_1, SWT.BORDER | SWT.CENTER);
		textLoaimay.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textLoaimay.setLayoutData(new RowData(224, SWT.DEFAULT));
		textLoaimay.setBackground(SWTResourceManager.getColor(224, 255, 255));

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new RowLayout(SWT.VERTICAL));

		Table table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));

		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setLayoutData(new RowData(Display.getDefault().getBounds().width - 40,
				Display.getDefault().getBounds().height - 180));

		table.setHeaderVisible(true);

		TableColumn tbcNumber = new TableColumn(table, SWT.NONE);
		tbcNumber.setWidth(110);
		tbcNumber.setText("STT");

		TableColumn tbcMaloaimay = new TableColumn(table, SWT.NONE);
		tbcMaloaimay.setWidth(201);
		tbcMaloaimay.setText("Mã Loại Máy");

		TableColumn tbcLoaimay = new TableColumn(table, SWT.NONE);
		tbcLoaimay.setWidth(484);
		tbcLoaimay.setText("Loại Máy");

		Button btnThem = new Button(composite_1, SWT.CENTER);
		btnThem.setLayoutData(new RowData(62, SWT.DEFAULT));
		btnThem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));

		btnThem.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnThem.setText("Thêm");

		Button btnSua = new Button(composite_1, SWT.CENTER);
		btnSua.setLayoutData(new RowData(61, SWT.DEFAULT));
		btnSua.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));

		btnSua.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnSua.setText("Sửa");

		Button btnXoa = new Button(composite_1, SWT.CENTER);
		btnXoa.setLayoutData(new RowData(61, SWT.DEFAULT));
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnXoa.setText("Xóa");

		Button btnTimKiem = new Button(composite_1, SWT.CENTER);
		btnTimKiem.setLayoutData(new RowData(91, SWT.DEFAULT));
		btnTimKiem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));

		btnTimKiem.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnTimKiem.setText("Tìm kiếm");

		Button btnLuu = new Button(composite_1, SWT.NONE);
		btnLuu.setLayoutData(new RowData(58, SWT.DEFAULT));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnLuu.setText("Lưu");
		btnLuu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));

		Button btnHuy = new Button(composite_1, SWT.NONE);
		btnHuy.setLayoutData(new RowData(80, SWT.DEFAULT));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnHuy.setText("Hủy");
		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		// Set Language
		// --------------------------------------------------------------------------------
		if (ngonngu == 0) {
			itemtab.setText("Loại máy");
			lbMaloaimay.setText("Mã Loại Máy");
			lbLoaimay.setText("Loại Máy");
			tbcNumber.setText("STT");
			tbcMaloaimay.setText("Mã Loại Máy");
			tbcLoaimay.setText("Loại Máy");
			btnThem.setText("Thêm");
			btnSua.setText("Sửa");
			btnXoa.setText("Xóa");
			btnTimKiem.setText("Tìm kiếm");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
		} else {
			// English
			itemtab.setText("Machine type");
			lbMaloaimay.setText("Machine Type Code");
			lbLoaimay.setText("Machine Type");
			tbcNumber.setText("Number");
			tbcMaloaimay.setText("Machine Type Code");
			tbcLoaimay.setText("Machine Type");
			btnThem.setText("Add");
			btnSua.setText("Edit");
			btnXoa.setText("Delete");
			btnTimKiem.setText("Search");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
		}

		// Ẩn button Luu, button Huy
		// ****************************************************************************
		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// Xử lý sự kiện Thêm
		// *****************************************************************************
		btnThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				them = true;
				sua = false;
				textMaloaimay.setText("");
				textLoaimay.setText("");

				btnLuu.setVisible(true);
				btnHuy.setVisible(true);

				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
				btnTimKiem.setEnabled(false);
			}
		});

		// Xử lý sự kiện Sửa
		// *******************************************************************************
		btnSua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sua = true;
				them = false;
				textMaloaimay.setText("");
				textLoaimay.setText("");
				try {
					TableItem[] item = table.getSelection();
					maloaimay_sua = item[0].getText();
					textMaloaimay.setText(maloaimay_sua);
					loaimay_sua = item[0].getText(1);
					textLoaimay.setText(loaimay_sua);

					// vị trí của item cần sửa trong bảng
					index_sua = table.indexOf(item[0]);

					btnLuu.setVisible(true);
					btnHuy.setVisible(true);

					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);
					btnTimKiem.setEnabled(false);
				} catch (Exception ex) {
					btnLuu.setVisible(false);
					btnHuy.setVisible(false);

					btnThem.setEnabled(true);
					btnXoa.setEnabled(true);
					btnTimKiem.setEnabled(true);
					sua = false;
				}
			}
		});

		// Xử lý sự kiện Xóa
		// ******************************************************************************************
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					try {
						// Lấy cột MaLoaimay
						TableItem[] item = table.getSelection();
						String maloaimay = item[0].getText();
						String deleteMabuilding = "DELETE LoaiMay WHERE MaLoaiMay='" + maloaimay + "'";
						try {
							statement.executeUpdate(deleteMabuilding);
							table.remove(table.getSelectionIndices());
						} catch (Exception ee) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo");
							thongbao.setMessage(ee.toString());
							thongbao.open();
						}
					} catch (Exception ex) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						thongbao.setText("Thông báo");
						thongbao.setMessage("Không có dòng nào được chọn!");
						thongbao.open();
					}

				} catch (SQLException se) {
					// se.printStackTrace();
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					thongbao.setText("Thông báo lỗi!");
					thongbao.setMessage("Lỗi kết nối!");
					thongbao.setMessage(se.toString());
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

		// Xử lý sự kiện Tìm Kiếm
		// ***********************************************************************************************
		btnTimKiem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Xu ly SQL
				try {
					conn = DriverManager.getConnection(db_url);

					statement = conn.createStatement();
					String selectLoaimay = "SELECT MaLoaiMay,LoaiMay FROM LoaiMay WHERE MaLoaiMay LIKE '%"
							+ textMaloaimay.getText() + "%' AND (LoaiMay LIKE N'%" + textLoaimay.getText()
							+ "%'  OR [dbo].[convertUnicodetoASCII](LoaiMay) LIKE '%" + textLoaimay.getText() + "%')";
					ResultSet result = statement.executeQuery(selectLoaimay);
					table.removeAll();
					int stt = 1;
					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { stt + "", result.getString(1), result.getString(2) });
						stt++;
					}

					result.close();

				} catch (SQLException se) {
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					thongbao.setText("Thông báo lỗi!");
					thongbao.setMessage("Lỗi kết nối!");
					thongbao.setMessage(se.toString());
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

		// Xu ly su kien Huy - Cancel
		// ********************************************************************************************************
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				them = false;
				sua = false;
				textMaloaimay.setText("");
				textLoaimay.setText("");

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
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						try {
							String insertLoaimay = "INSERT INTO LoaiMay (MaLoaiMay,LoaiMay) VALUES ('"
									+ textMaloaimay.getText() + "',N'" + textLoaimay.getText() + "')";
							int result = statement.executeUpdate(insertLoaimay);
							if (result > 0) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { textMaloaimay.getText(), textLoaimay.getText() });
								textMaloaimay.setText("");
								textLoaimay.setText("");
							}
						} catch (Exception ex) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo!");
							thongbao.setMessage("Thêm không thành công\n" + ex.toString());
							thongbao.open();
						}

					} catch (SQLException se) {
						// se.printStackTrace();
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						thongbao.setText("Thông báo lỗi!");
						thongbao.setMessage("Lỗi kết nối!");
						thongbao.setMessage(se.toString());
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
				} else if (sua) {
					// Nut Sua duoc chon
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						try {
							String updateLoaimay = "UPDATE LoaiMay SET MaLoaiMay='" + textMaloaimay.getText()
									+ "' , LoaiMay=N'" + textLoaimay.getText() + "' WHERE MaLoaiMay='" + maloaimay_sua
									+ "'";
							int result = statement.executeUpdate(updateLoaimay);
							if (result > 0) {
								table.remove(index_sua);
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { textMaloaimay.getText(), textLoaimay.getText() });
								textMaloaimay.setText("");
								textLoaimay.setText("");

								them = false;
								sua = false;

								// Ẩn button Luu, button Huy
								btnLuu.setVisible(false);
								btnHuy.setVisible(false);

								btnSua.setEnabled(true);
								btnXoa.setEnabled(true);
								btnTimKiem.setEnabled(true);
							}
						} catch (Exception ex) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo!");
							thongbao.setMessage("Sửa không thành công\n" + ex.toString());
							thongbao.open();
						}

					} catch (SQLException se) {
						// se.printStackTrace();
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						thongbao.setText("Thông báo lỗi!");
						thongbao.setMessage("Lỗi kết nối!");
						thongbao.setMessage(se.toString());
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
			}
		});

		// Sort data
		// *********************************************************************************************************************************************************************
		// Sắp xếp table theo một cột đã chọn
		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableItem[] items = table.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				TableColumn column = (TableColumn) e.widget;
				int index = column == tbcNumber ? 0 : column == tbcMaloaimay ? 1 : 2;
				if (!(selectcolumn == index)) {
					selectcolumn = index;
					for (int i = 1; i < items.length; i++) {
						String value1 = items[i].getText(index);
						for (int j = 0; j < i; j++) {
							String value2 = items[j].getText(index);
							if (collator.compare(value1, value2) < 0) {

								String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2),
										items[i].getText(3), items[i].getText(4), items[i].getText(5),
										items[i].getText(6) };
								items[i].dispose();
								TableItem item = new TableItem(table, SWT.NONE, j);
								item.setText(values);
								items = table.getItems();
								break;
							}
						}
					}
				} else {
					selectcolumn = -1;
					for (int i = 1; i < items.length; i++) {
						String value1 = items[i].getText(index);
						for (int j = 0; j < i; j++) {
							String value2 = items[j].getText(index);
							if (collator.compare(value1, value2) > 0) {

								String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2),
										items[i].getText(3), items[i].getText(4), items[i].getText(5),
										items[i].getText(6) };
								items[i].dispose();
								TableItem item = new TableItem(table, SWT.NONE, j);
								item.setText(values);

								items = table.getItems();
								break;
							}
						}
					}
				}
				table.setSortColumn(column);

				// sap xep lai cot Number (STT)
				for (int i = 0; i < items.length; i++) {
					String[] values = { (i + 1) + "", items[i].getText(1), items[i].getText(2) };
					items[i].dispose();
					TableItem item2 = new TableItem(table, SWT.NONE, i);
					item2.setText(values);
					items = table.getItems();
				}
			}
		};

		tbcMaloaimay.addListener(SWT.Selection, sortListener);
		tbcLoaimay.addListener(SWT.Selection, sortListener);
	}
}
