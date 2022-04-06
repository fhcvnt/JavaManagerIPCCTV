package cctv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class Note {

	protected Shell shell;
	private Text textNote;
	private Table table;
	private Connection conn = null;
	private Statement statement = null;
	private String ghichunote = "";
	private int trangthai=-1; // 1: Add, 2: Edit, 3: Delete

	public static void main(String[] args) {
		try {
			Note window = new Note();
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
		shell.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/IP.ico"));
		shell.setSize(1197, 648);
		shell.setText("Note");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);

		textNote = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textNote.setBackground(SWTResourceManager.getColor(192, 192, 192));
		textNote.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		textNote.setBounds(10, 22, 847, 284);
		textNote.setTextLimit(450);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnAdd.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/add35.png"));
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnAdd.setBounds(885, 22, 141, 40);
		btnAdd.setText("Add");

		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setText("Edit");
		btnEdit.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/edit35.png"));
		btnEdit.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		btnEdit.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnEdit.setBounds(885, 83, 141, 40);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 140, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		table.setBounds(10, 312, 1161, 279);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		// table.setSize(Display.getDefault().getBounds().width-20,Display.getDefault().getBounds().height-400);
		table.setSize(Display.getDefault().getBounds().width - 20, Display.getDefault().getBounds().height - 400);

		TableColumn tblclmnNote = new TableColumn(table, SWT.NONE);
		tblclmnNote.setWidth(843);
		tblclmnNote.setText("Note");

		TableColumn tblclmnDateUpdate = new TableColumn(table, SWT.NONE);
		tblclmnDateUpdate.setWidth(251);
		tblclmnDateUpdate.setText("Date Update");

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setText("Delete");
		btnDelete.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/delete.png"));
		btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnDelete.setBounds(885, 141, 141, 40);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/save35.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnSave.setBounds(885, 199, 141, 40);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/huy.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnCancel.setBounds(885, 258, 141, 40);

		// Hide button Save, Cancel
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
		textNote.setEnabled(false);

		if (ngonngu == 0) {
			shell.setText("Ghi chú");
			btnAdd.setText("Thêm");
			btnEdit.setText("Sửa");
			tblclmnNote.setText("Ghi Chú");
			tblclmnDateUpdate.setText("Ngày Cập Nhật");
			btnDelete.setText("Xóa");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else {
			shell.setText("Note");
			btnAdd.setText("Add");
			btnEdit.setText("Edit");
			tblclmnNote.setText("Note");
			tblclmnDateUpdate.setText("Date Update");
			btnDelete.setText("Delete");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		}

		// Lấy dữ liệu cho table
		// **********************************************************************************************************************************************************************
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();
			while (result.next()) {
				String ngaycapnhat = result.getString(2);
				try {
					ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
							+ ngaycapnhat.substring(0, 4);
				} catch (IndexOutOfBoundsException ie) {
					ngaycapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), ngaycapnhat });
			}
			result.close();
		} catch (SQLException se) {
			// se.printStackTrace();
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

		// button Add
		// **********************************************************************************************************************************************************************
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnSave.getVisible()) {
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					textNote.setEnabled(true);
					textNote.setText("");
					trangthai=1;
				}
			}
		});

		// button Edit
		// **********************************************************************************************************************************************************************
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnSave.getVisible()) {
					btnAdd.setEnabled(false);
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(false);
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					textNote.setEnabled(true);
					trangthai=2;
					try {
						TableItem[] item = table.getSelection();
						ghichunote = item[0].getText();
						textNote.setText(ghichunote);
					} catch (Exception ne) {
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnDelete.setEnabled(true);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						textNote.setText("");
						textNote.setEnabled(false);
						trangthai=-1;
					}
				}
			}
		});

		// button Delete
		// **********************************************************************************************************************************************************************
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnSave.getVisible()) {
					btnAdd.setEnabled(false);
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(true);
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					textNote.setEnabled(false);
					trangthai=3;
					try {
						TableItem[] item = table.getSelection();
						ghichunote = item[0].getText();
						textNote.setText(ghichunote);
					} catch (Exception ne) {
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnDelete.setEnabled(true);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						textNote.setText("");
						textNote.setEnabled(false);
						trangthai=-1;
					}
				}
			}
		});

		// button Save
		// **********************************************************************************************************************************************************************
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai==1) {
					// Add
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String insert = "INSERT INTO Note(Note,NgayTao) VALUES (N'" + textNote.getText()
								+ "',GETDATE())";
						Statement stateinsert = conn.createStatement();
						if (stateinsert.executeUpdate(insert) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Thêm thành công!");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Add success!");
							}
							thongbao.open();
							textNote.setText("");

							String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();
							while (result.next()) {
								String ngaycapnhat = result.getString(2);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), ngaycapnhat });
							}
							result.close();
						}
					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Thêm thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Add failed!");
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
						}
					}
				} else if (trangthai==2) {
					// Edit
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String update = "UPDATE Note SET Note=N'"+textNote.getText()+"',NgayTao=GETDATE() WHERE Note=N'"+ghichunote+"'";
						Statement stateupdate = conn.createStatement();
						if (stateupdate.executeUpdate(update) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Sửa thành công!");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Edit success!");
							}
							thongbao.open();
							btnAdd.setEnabled(true);
							btnEdit.setEnabled(true);
							btnDelete.setEnabled(true);
							btnSave.setVisible(false);
							btnCancel.setVisible(false);
							textNote.setText("");
							textNote.setEnabled(false);
							trangthai=-1;

							String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();
							while (result.next()) {
								String ngaycapnhat = result.getString(2);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), ngaycapnhat });
							}
							result.close();
						}
					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Sửa thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Edit failed!");
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
						}
					}
				} else if (trangthai==3) {
					// Delete
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String delete = "DELETE FROM Note WHERE Note=N'"+ghichunote+"'";
						Statement statedelete = conn.createStatement();
						if (statedelete.executeUpdate(delete) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Xóa thành công!");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Delete success!");
							}
							thongbao.open();
							btnAdd.setEnabled(true);
							btnEdit.setEnabled(true);
							btnDelete.setEnabled(true);
							btnSave.setVisible(false);
							btnCancel.setVisible(false);
							textNote.setText("");
							textNote.setEnabled(false);
							trangthai=-1;

							String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();
							while (result.next()) {
								String ngaycapnhat = result.getString(2);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), ngaycapnhat });
							}
							result.close();
						}
					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Xóa thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Delete failed!");
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
						}
					}
				}
			}
		});

		// button Cancel
		// **********************************************************************************************************************************************************************
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnAdd.setEnabled(true);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				textNote.setText("");
				textNote.setEnabled(false);
				trangthai=-1;
			}
		});

		// table
		// **********************************************************************************************************************************************************************
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

	}

	// hien trong Ctabfolder ****************************************************************************************************************************************************
	protected void showTabFolder(CTabFolder tabfolder, Shell shell,int ngonngu, String db_url) {
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Note");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);

		textNote = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textNote.setBackground(SWTResourceManager.getColor(192, 192, 192));
		textNote.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		textNote.setBounds(10, 22, 847, 284);
		textNote.setTextLimit(450);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnAdd.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/add35.png"));
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnAdd.setBounds(885, 22, 141, 40);
		btnAdd.setText("Add");

		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setText("Edit");
		btnEdit.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/edit35.png"));
		btnEdit.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		btnEdit.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnEdit.setBounds(885, 83, 141, 40);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 140, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		table.setBounds(10, 312, 1161, 279);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(Display.getDefault().getBounds().width - 20, Display.getDefault().getBounds().height - 430);

		TableColumn tblclmnNote = new TableColumn(table, SWT.NONE);
		tblclmnNote.setWidth(843);
		tblclmnNote.setText("Note");

		TableColumn tblclmnDateUpdate = new TableColumn(table, SWT.NONE);
		tblclmnDateUpdate.setWidth(251);
		tblclmnDateUpdate.setText("Date Update");

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setText("Delete");
		btnDelete.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/delete.png"));
		btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnDelete.setBounds(885, 141, 141, 40);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/save35.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnSave.setBounds(885, 199, 141, 40);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(Note.class, "/cctv/Icon/bieutuong/huy.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		btnCancel.setBounds(885, 258, 141, 40);

		// Hide button Save, Cancel
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
		textNote.setEnabled(false);

		if (ngonngu == 0) {
			itemtab.setText("Ghi chú");
			btnAdd.setText("Thêm");
			btnEdit.setText("Sửa");
			tblclmnNote.setText("Ghi Chú");
			tblclmnDateUpdate.setText("Ngày Cập Nhật");
			btnDelete.setText("Xóa");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else {
			itemtab.setText("Note");
			btnAdd.setText("Add");
			btnEdit.setText("Edit");
			tblclmnNote.setText("Note");
			tblclmnDateUpdate.setText("Date Update");
			btnDelete.setText("Delete");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		}

		// Lấy dữ liệu cho table
		// **********************************************************************************************************************************************************************
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();
			while (result.next()) {
				String ngaycapnhat = result.getString(2);
				try {
					ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
							+ ngaycapnhat.substring(0, 4);
				} catch (IndexOutOfBoundsException ie) {
					ngaycapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), ngaycapnhat });
			}
			result.close();
		} catch (SQLException se) {
			// se.printStackTrace();
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

		// button Add
		// **********************************************************************************************************************************************************************
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnSave.getVisible()) {
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					textNote.setEnabled(true);
					textNote.setText("");
					trangthai=1;
				}
			}
		});

		// button Edit
		// **********************************************************************************************************************************************************************
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnSave.getVisible()) {
					btnAdd.setEnabled(false);
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(false);
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					textNote.setEnabled(true);
					trangthai=2;
					try {
						TableItem[] item = table.getSelection();
						ghichunote = item[0].getText();
						textNote.setText(ghichunote);
					} catch (Exception ne) {
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnDelete.setEnabled(true);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						textNote.setText("");
						textNote.setEnabled(false);
						trangthai=-1;
					}
				}
			}
		});

		// button Delete
		// **********************************************************************************************************************************************************************
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnSave.getVisible()) {
					btnAdd.setEnabled(false);
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(true);
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					textNote.setEnabled(false);
					trangthai=3;
					try {
						TableItem[] item = table.getSelection();
						ghichunote = item[0].getText();
						textNote.setText(ghichunote);
					} catch (Exception ne) {
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnDelete.setEnabled(true);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						textNote.setText("");
						textNote.setEnabled(false);
						trangthai=-1;
					}
				}
			}
		});

		// button Save
		// **********************************************************************************************************************************************************************
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai==1) {
					// Add
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String insert = "INSERT INTO Note(Note,NgayTao) VALUES (N'" + textNote.getText()
								+ "',GETDATE())";
						Statement stateinsert = conn.createStatement();
						if (stateinsert.executeUpdate(insert) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Thêm thành công!");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Add success!");
							}
							thongbao.open();
							textNote.setText("");

							String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();
							while (result.next()) {
								String ngaycapnhat = result.getString(2);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), ngaycapnhat });
							}
							result.close();
						}
					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Thêm thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Add failed!");
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
						}
					}
				} else if (trangthai==2) {
					// Edit
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String update = "UPDATE Note SET Note=N'"+textNote.getText()+"',NgayTao=GETDATE() WHERE Note=N'"+ghichunote+"'";
						Statement stateupdate = conn.createStatement();
						if (stateupdate.executeUpdate(update) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Sửa thành công!");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Edit success!");
							}
							thongbao.open();
							btnAdd.setEnabled(true);
							btnEdit.setEnabled(true);
							btnDelete.setEnabled(true);
							btnSave.setVisible(false);
							btnCancel.setVisible(false);
							textNote.setText("");
							textNote.setEnabled(false);
							trangthai=-1;

							String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();
							while (result.next()) {
								String ngaycapnhat = result.getString(2);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), ngaycapnhat });
							}
							result.close();
						}
					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Sửa thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Edit failed!");
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
						}
					}
				} else if (trangthai==3) {
					// Delete
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String delete = "DELETE FROM Note WHERE Note=N'"+ghichunote+"'";
						Statement statedelete = conn.createStatement();
						if (statedelete.executeUpdate(delete) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Xóa thành công!");
							} else {
								thongbao.setText("Notification");
								thongbao.setMessage("Delete success!");
							}
							thongbao.open();
							btnAdd.setEnabled(true);
							btnEdit.setEnabled(true);
							btnDelete.setEnabled(true);
							btnSave.setVisible(false);
							btnCancel.setVisible(false);
							textNote.setText("");
							textNote.setEnabled(false);
							trangthai=-1;

							String select = "SELECT Note,NgayTao FROM Note ORDER BY NgayTao DESC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();
							while (result.next()) {
								String ngaycapnhat = result.getString(2);
								try {
									ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
											+ ngaycapnhat.substring(0, 4);
								} catch (IndexOutOfBoundsException ie) {
									ngaycapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), ngaycapnhat });
							}
							result.close();
						}
					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Xóa thất bại!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("Delete failed!");
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
						}
					}
				}
			}
		});

		// button Cancel
		// **********************************************************************************************************************************************************************
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnAdd.setEnabled(true);
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				textNote.setText("");
				textNote.setEnabled(false);
				trangthai=-1;
			}
		});

		// table
		// **********************************************************************************************************************************************************************
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

	}
}
