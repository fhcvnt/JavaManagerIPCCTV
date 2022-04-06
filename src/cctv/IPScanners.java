package cctv;

import java.net.InetAddress;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class IPScanners {

	protected Shell shlIpScanner;
	private static Text textIPstart;
	private static Text textIPend;
	private static Table table;
	private static Text textIP;
	private static Text textIPPing;
	private String textping = "";
	private InetAddress inet;
	private String ipAddress;
	private Runnable timer;

	public static void main(String[] args) {
		try {
			IPScanners window = new IPScanners();
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
		createContents();
		shlIpScanner.open();
		shlIpScanner.layout();
		while (!shlIpScanner.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlIpScanner = new Shell();
		shlIpScanner.setSize(1360, 730);
		shlIpScanner.setText("IP Scanner");

		CLabel lbIPStart = new CLabel(shlIpScanner, SWT.NONE);
		lbIPStart.setAlignment(SWT.RIGHT);
		lbIPStart.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbIPStart.setBounds(20, 22, 87, 25);
		lbIPStart.setText("IP Start");

		textIPstart = new Text(shlIpScanner, SWT.BORDER);
		textIPstart.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textIPstart.setBounds(113, 22, 190, 25);
		textIPstart.setTextLimit(15);

		CLabel lbIPEnd = new CLabel(shlIpScanner, SWT.NONE);
		lbIPEnd.setAlignment(SWT.RIGHT);
		lbIPEnd.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbIPEnd.setText("IP End");
		lbIPEnd.setBounds(319, 25, 68, 21);

		textIPend = new Text(shlIpScanner, SWT.BORDER);
		textIPend.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textIPend.setBounds(393, 22, 190, 25);
		textIPend.setTextLimit(15);

		Button btnScan = new Button(shlIpScanner, SWT.NONE);
		btnScan.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnScan.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.BOLD));
		btnScan.setBounds(609, 16, 87, 37);
		btnScan.setText("Scan");

		table = new Table(shlIpScanner, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(20, 65, 717, 575);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnStatus = new TableColumn(table, SWT.NONE);
		tblclmnStatus.setWidth(100);
		tblclmnStatus.setText("Status");

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(271);
		tblclmnName.setText("Name");

		TableColumn tblclmnIp = new TableColumn(table, SWT.NONE);
		tblclmnIp.setWidth(262);
		tblclmnIp.setText("IP");

		// Xử lý Scan
		// ===============================================================================================================
		btnScan.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					table.removeAll();
					String ipbatdau=textIPstart.getText();
					String ipketthuc=textIPend.getText();
					String status = "";
					if(validateIP(ipbatdau) && validateIP(ipketthuc) && ipbatdau.compareTo(ipketthuc)<=0) {
						
						//while(ipbatdau.equals(ipketthuc)) {
						for(int i=0;i<20;i++) {
						//while(a<10) {
							
							InetAddress address = InetAddress.getByName(ipbatdau);
							
							if (address.isReachable(300)) {
								status = "Online";
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { status, address.getHostName(), address.getHostAddress() });
								table.update();
								ipbatdau=getNextIPV4Address(ipbatdau);
						
							}else {
								continue;
							}
							
							
							//a++;
						//}
						}
					}
				} catch (Exception e1) {
				}
			}
		});

		Label label = new Label(shlIpScanner, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(757, 65, 2, 575);

		CLabel lblIp = new CLabel(shlIpScanner, SWT.NONE);
		lblIp.setText("IP");
		lblIp.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lblIp.setAlignment(SWT.RIGHT);
		lblIp.setBounds(794, 22, 51, 25);

		textIP = new Text(shlIpScanner, SWT.BORDER);
		textIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textIP.setBounds(851, 22, 226, 25);
		textIP.setTextLimit(15);

		Button btnPing = new Button(shlIpScanner, SWT.NONE);
		btnPing.setText("Ping");
		btnPing.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnPing.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.BOLD));
		btnPing.setBounds(1100, 16, 87, 37);

		textIPPing = new Text(shlIpScanner, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textIPPing.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		textIPPing.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textIPPing.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textIPPing.setBounds(781, 65, 519, 575);

		Button btnCancel = new Button(shlIpScanner, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.BOLD));
		btnCancel.setBounds(1196, 16, 87, 37);

		// Xử lý Ping
		// =========================================================================================================================
		btnPing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				textping = "";
				String ipping = textIP.getText();
				if (validateIP(ipping)) {
					ipAddress = ipping;
					textping = textping + "Sending Ping Request to " + ipAddress + "";

					timer = new Runnable() {
						public void run() {
							try {
								inet = InetAddress.getByName(ipAddress);
								textIPPing.setText("");
								// inet = InetAddress.getByName(ipAddress);

								textIPPing.setText(textping);

								long finish = 0;
								long start = new GregorianCalendar().getTimeInMillis();

								if (inet.isReachable(5000)) {
									finish = new GregorianCalendar().getTimeInMillis();
									textping = textping + "\n" + "Reply from " + ipping + ": time < "
											+ (finish - start + 1) + "ms";
								} else {
									textping = textping + "\n" + "Request time out.";
								}
							} catch (Exception ex) {
							}
							textIPPing.setText(textping);
							textIPPing.setTopIndex(textIPPing.getLineCount() - 1);

							Display.getDefault().timerExec(1000, this);
						}
					};
					Display.getDefault().timerExec(1000, timer);
				}
			}
		});

		// Xử lý Cancel
		// =========================================================================================================================
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Display.getDefault().timerExec(-1, timer);
				} catch (Exception ex) {

				}

				textping = "";
				textIPPing.setText(textping);
			}
		});

	}

	// Kiểm tra định dạng IP
	//==============================================================================================================
	public static boolean validateIP(final String ip) {
		String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

		return ip.matches(PATTERN);
	}
	
	// Next IP
	//=======================================================================================================
	public String getNextIPV4Address(String ip) {
	    String[] nums = ip.split("\\.");
	    int i = (Integer.parseInt(nums[0]) << 24 | Integer.parseInt(nums[2]) << 8
	          |  Integer.parseInt(nums[1]) << 16 | Integer.parseInt(nums[3])) + 1;

	    // If you wish to skip over .255 addresses.
	    if ((byte) i == -1) i++;

	    return String.format("%d.%d.%d.%d", i >>> 24 & 0xFF, i >> 16 & 0xFF,
	                                        i >>   8 & 0xFF, i >>  0 & 0xFF);
	}

}
