package cctv;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import sql.ConnectSQL;

public class EditIP {

	protected Shell shell;
	private ConnectSQL connect;
	private Text textTen;
	private Text textIP;
	private Text textghichu;

	public static void main(String[] args) {
		try {
			EditIP window = new EditIP();
			window.createContents("", "", "", "", "", 0);
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
	protected void createContents(String ten, String khuvuc, String ip, String loaimay, String ghichu, int ngonngu) {

		shell = new Shell(SWT.CLOSE);
		shell.setImage(SWTResourceManager.getImage(EditIP.class, "/cctv/Icon/IP.ico"));
		shell.setSize(1086, 560);
		shell.setText("Edit IP");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);

		CLabel lbhoten = new CLabel(composite, SWT.NONE);
		lbhoten.setAlignment(SWT.RIGHT);
		lbhoten.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lbhoten.setBounds(20, 29, 103, 35);
		lbhoten.setText("Tên");

		textTen = new Text(composite, SWT.BORDER);
		textTen.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textTen.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		textTen.setBounds(136, 29, 319, 35);

		CLabel lbKhuvuc = new CLabel(composite, SWT.NONE);
		lbKhuvuc.setAlignment(SWT.RIGHT);
		lbKhuvuc.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lbKhuvuc.setBounds(461, 29, 219, 35);
		lbKhuvuc.setText("Khu Vực");

		Combo cbKhuvuc = new Combo(composite, SWT.NONE);
		cbKhuvuc.setBackground(SWTResourceManager.getColor(224, 255, 255));
		cbKhuvuc.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		cbKhuvuc.setBounds(686, 29, 290, 35);

		CLabel lbIP = new CLabel(composite, SWT.NONE);
		lbIP.setAlignment(SWT.RIGHT);
		lbIP.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lbIP.setBounds(64, 83, 59, 35);
		lbIP.setText("IP");

		textIP = new Text(composite, SWT.BORDER);
		textIP.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textIP.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		textIP.setBounds(136, 83, 319, 35);

		CLabel lbGhichu = new CLabel(composite, SWT.NONE);
		lbGhichu.setAlignment(SWT.RIGHT);
		lbGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lbGhichu.setBounds(10, 256, 113, 80);
		lbGhichu.setText("Ghi Chú");

		textghichu = new Text(composite, SWT.BORDER | SWT.MULTI);
		textghichu.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		textghichu.setBounds(136, 148, 566, 262);

		CLabel lbLoaimay = new CLabel(composite, SWT.NONE);
		lbLoaimay.setLeftMargin(0);
		lbLoaimay.setText("Loại Máy");
		lbLoaimay.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lbLoaimay.setAlignment(SWT.RIGHT);
		lbLoaimay.setBounds(461, 83, 219, 35);

		Combo cbLoaimay = new Combo(composite, SWT.NONE);
		cbLoaimay.setBackground(SWTResourceManager.getColor(224, 255, 255));
		cbLoaimay.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		cbLoaimay.setBounds(686, 83, 290, 35);

		Button btnLuu = new Button(composite, SWT.NONE);
		btnLuu.setImage(SWTResourceManager.getImage(EditIP.class, "/cctv/Icon/bieutuong/save.png"));
		btnLuu.setForeground(SWTResourceManager.getColor(0, 0, 255));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 25, SWT.BOLD));
		btnLuu.setBounds(303, 430, 188, 58);
		btnLuu.setText("Lưu");

		// Gán ngôn ngữ
		// -------------------------------------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Sửa IP");
			lbhoten.setText("Tên");
			lbKhuvuc.setText("Khu Vực");
			lbIP.setText("IP");
			lbGhichu.setText("Ghi Chú");
			lbLoaimay.setText("Loại Máy");
			btnLuu.setText("Lưu");
		} else {
			shell.setText("Edit IP");
			lbhoten.setText("Name");
			lbKhuvuc.setText("Location");
			lbIP.setText("IP");
			lbGhichu.setText("Note");
			lbLoaimay.setText("Machine Type");
			btnLuu.setText("Save");
		}

		// ======================================================================================================
		// Thêm dữ liệu cho Combox
		try {
			if (connect == null) {
				connect = new ConnectSQL();
				connect.setConnection();
			}
			connect.setStatement();

			// Khu vực
			String selectlocation = "SELECT Location FROM Location ORDER BY Location ASC";
			ResultSet result = connect.getStatement().executeQuery(selectlocation);
			while (result.next()) {
				cbKhuvuc.add(result.getString(1));
			}
			cbKhuvuc.select(0);

			// Loai may
			String selectloaimay = "SELECT LoaiMay FROM LoaiMay ORDER BY LoaiMay ASC";
			ResultSet resultLoaimay = connect.getStatement().executeQuery(selectloaimay);
			while (resultLoaimay.next()) {
				cbLoaimay.add(resultLoaimay.getString(1));
			}
			cbLoaimay.select(0);

			CLabel lbImage = new CLabel(composite, SWT.NONE);
			lbImage.setBackground(SWTResourceManager.getImage(NewIP.class, "/cctv/Icon/bieutuong/search ip.png"));
			lbImage.setBounds(729, 148, 292, 250);

			result.close();
			resultLoaimay.close();

		} catch (Exception se) {
			MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			if (ngonngu == 0) {
				thongbao.setText("Thông báo lỗi!");
				thongbao.setMessage("Lỗi kết nối!");
			} else {
				thongbao.setText("Alert!");
				thongbao.setMessage("Connect error!");
			}
			thongbao.open();
		} finally {
			try {
				connect.closeStatement();
			} catch (Exception se2) {
			}
		}

		// Khoi tao gia tri ban dau
		// *****************************************************************************************************************************
		textTen.setText(ten);
		cbKhuvuc.setText(khuvuc);
		textIP.setText(ip);
		cbLoaimay.setText(loaimay);
		textghichu.setText(ghichu);

		// ***************************************************************************************************************************
		// Lưu
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// ***************************************************************
				if (!textTen.getText().isEmpty() && !textIP.getText().isEmpty()) {
					if (validate(textIP.getText())) {
						try {
							if (connect == null) {
								connect = new ConnectSQL();
								connect.setConnection();
							}
							connect.setStatement();

							String makhuvuc = "", maloaimay = "";

							try {
								// Khu vưc
								String select = "SELECT MaLocation FROM Location WHERE Location=N'" + cbKhuvuc.getText()
										+ "'";
								ResultSet resultselect = connect.getStatement().executeQuery(select);
								while (resultselect.next()) {
									makhuvuc = resultselect.getString(1);
									break;
								}

								// Loai may
								select = "SELECT MaLoaiMay FROM LoaiMay WHERE LoaiMay=N'" + cbLoaimay.getText() + "'";
								resultselect = connect.getStatement().executeQuery(select);
								while (resultselect.next()) {
									maloaimay = resultselect.getString(1);
									break;
								}

								String updateIP = "UPDATE DanhSachIP SET Ten=N'" + textTen.getText() + "',MaLocation='"
										+ makhuvuc + "',IP='" + textIP.getText() + "',MaLoaiMay='" + maloaimay
										+ "',GhiChu=N'" + textghichu.getText() + "',NgayCapNhat=GETDATE() WHERE IP='"
										+ ip + "'";

								int result = connect.getStatement().executeUpdate(updateIP);
								if (result > 0) {
									try {
										cbKhuvuc.select(0);
										cbLoaimay.select(0);
										textTen.setText("");
										textIP.setText("");
										textghichu.setText("");

										MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
										if (ngonngu == 0) {
											thongbao.setText("Thông báo");
											thongbao.setMessage("Cập nhật thành công!");
										} else {
											thongbao.setText("Aler");
											thongbao.setMessage("Update success!");
										}
										thongbao.open();
										shell.dispose();
									} catch (Exception ex) {

									}
								}
							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Cập nhật không thành công!");
								} else {
									thongbao.setText("Alert");
									thongbao.setMessage("Update failed!");
								}
								thongbao.open();
							}

						} catch (Exception se) {
							// se.printStackTrace();
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo lỗi");
								thongbao.setMessage("Lỗi kết nối!");
							} else {
								thongbao.setText("Alert");
								thongbao.setMessage("Connect error!");
							}
							thongbao.open();
						} finally {
							try {
								connect.closeStatement();
							} catch (SQLException se2) {
								// nothing we can do
							}
						}
					} else {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Định dạng IP sai!");
						} else {
							thongbao.setText("Notification");
							thongbao.setMessage("IP format error!");
						}
						thongbao.open();
					}
				}
			}
		});
	}

	// Kiểm tra định dạng IP
	public static boolean validate(final String ip) {
		String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

		return ip.matches(PATTERN);
	}

}
