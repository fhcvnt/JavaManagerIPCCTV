package cctv;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import sql.ConnectSQL;

public class GetIP {
	private ConnectSQL connect;

	protected Shell shell;
	private Text textLayIP;
	private Table table;
	private int language = 1;

	public void setLanguage(int ngonngu) {
		language = ngonngu;
	}

	public int getLanguage() {
		return language;
	}

	public static void main(String[] args) {
		try {
			GetIP window = new GetIP();
			window.createContents(0);
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
	protected void createContents(int ngonngu) {
		shell = new Shell(SWT.MIN);
		shell.setSize(473, 612);
		shell.setText("Get IP");
		shell.setImage(SWTResourceManager.getImage(GetIP.class, "/cctv/Icon/IP.ico"));

		textLayIP = new Text(shell, SWT.BORDER);
		textLayIP.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textLayIP.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		textLayIP.setBounds(104, 21, 82, 35);
		textLayIP.setTextLimit(3);

		CLabel lbIP = new CLabel(shell, SWT.NONE);
		lbIP.setAlignment(SWT.RIGHT);
		lbIP.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lbIP.setBounds(47, 21, 51, 35);
		lbIP.setText("IP");

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		table.setBounds(10, 62, 437, 496);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnStt = new TableColumn(table, SWT.NONE);
		tblclmnStt.setWidth(100);
		tblclmnStt.setText("STT");

		TableColumn tblclmnIp = new TableColumn(table, SWT.NONE);
		tblclmnIp.setWidth(303);
		tblclmnIp.setText("IP");

		Button btnGetIP = new Button(shell, SWT.NONE);
		btnGetIP.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnGetIP.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.BOLD));
		btnGetIP.setBounds(209, 20, 104, 35);
		btnGetIP.setText("Get IP");

		// xu ly ngon ngu
		// *******************************************************************************
		if (ngonngu == 0) {
			// Tieng Viet
			btnGetIP.setText("Lấy IP");
			shell.setText("Lấy IP");
		} else {
			// Tieng Anh
			btnGetIP.setText("Get IP");
			shell.setText("Get IP");
		}

		// Xy ly Get IP
		// =========================================================================================
		btnGetIP.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getIP();
			}
		});

		// Get IP sau khi enter ở text Get IP
		// ******************************************************************************
		textLayIP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					getIP();
				}
			}
		});

		// Get IP sau khi enter ở btn Get IP
		// ******************************************************************************
		btnGetIP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// phím tắt Enter thì tìm kiếm
				if (e.character == SWT.CR) {
					getIP();
				}
			}
		});

	}

	public void getIP() {
		try {
			if (connect == null) {
				connect = new ConnectSQL();
				connect.setConnection();
			}
			connect.setStatement();
			String select = "SELECT IP FROM DanhSachIP WHERE IP LIKE '%." + textLayIP.getText() + ".%' ORDER BY IP ASC";
			ResultSet result = connect.getStatement().executeQuery(select);
			try {

				if (!textLayIP.getText().isEmpty() && Integer.parseInt(textLayIP.getText()) >= 0
						&& Integer.parseInt(textLayIP.getText()) <= 255) {
					table.removeAll();
					int stt = 0;
					ArrayList<String> danhsachip = new ArrayList<String>();
					String chuoiip = "192.168.";
					String lopip = textLayIP.getText();
					for (int i = 1; i <= 254; i++) {
						danhsachip.add(chuoiip + lopip + "." + i);
					}

					ArrayList<String> danhsachipcsdl = new ArrayList<String>();

					while (result.next()) {
						danhsachipcsdl.add(result.getString(1));
					}

					danhsachip.removeAll(danhsachipcsdl);
					for (String list : danhsachip) {
						stt++;
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { stt + "", list });
					}
				}
			} catch (Exception ee) {

			}

			result.close();

		} catch (Exception se) {
			MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			thongbao.setText("Thông báo lỗi!");
			thongbao.setMessage("Lỗi kết nối!");
			thongbao.setMessage(se.toString());
			thongbao.open();
		} finally {
			try {
				connect.closeStatement();
			} catch (Exception se2) {
			}
		}
	}
}
