package cctv;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindowCCTV {

	protected Shell shell;
	// language=0: Tieng Viet, language=1: Tieng Anh
	private int language = 1;
	private String db_url = "";
	private String version = "V4.30.7.20201016";

	public static void main(String[] args) {
		try {
			MainWindowCCTV window = new MainWindowCCTV();
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
	protected void createContents() {
		shell = new Shell();
		shell.setSize(809, 431);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setMaximized(true);
		shell.setText("IP Manager - " + version);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/IP.ico"));

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("&File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmClose = new MenuItem(menu_1, SWT.NONE);
		mntmClose.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/exit.png"));

		mntmClose.setText("Exit\tCtrl + Q");
		mntmClose.setAccelerator(SWT.MOD1 + 'Q');

		MenuItem mntmIp = new MenuItem(menu, SWT.CASCADE);
		mntmIp.setText("&IP");

		Menu menu_2 = new Menu(mntmIp);
		mntmIp.setMenu(menu_2);

		MenuItem mntmIpList = new MenuItem(menu_2, SWT.NONE);
		mntmIpList.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/iplist.png"));
		mntmIpList.setText("IP List\tCtrl + L");
		mntmIpList.setAccelerator(SWT.MOD1 + 'L');

		MenuItem mntmLocation = new MenuItem(menu, SWT.CASCADE);
		mntmLocation.setText("&Location");

		Menu menu_5 = new Menu(mntmLocation);
		mntmLocation.setMenu(menu_5);

		MenuItem mntmListLocation = new MenuItem(menu_5, SWT.NONE);
		mntmListLocation.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/Poison.png"));
		mntmListLocation.setText("Location\tCtrl + B");
		mntmListLocation.setAccelerator(SWT.MOD1 + 'B');

		MenuItem mntmMachineType = new MenuItem(menu, SWT.CASCADE);
		mntmMachineType.setText("&Machine Type");

		Menu menu_9 = new Menu(mntmMachineType);
		mntmMachineType.setMenu(menu_9);

		MenuItem mntmMachineTypeList = new MenuItem(menu_9, SWT.NONE);
		mntmMachineTypeList.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/machine25.png"));

		mntmMachineTypeList.setText("Machine Type List\tCtrl + M");
		mntmMachineTypeList.setAccelerator(SWT.MOD1 + 'M');

		MenuItem mntmNote = new MenuItem(menu, SWT.CASCADE);
		mntmNote.setText("Note");

		Menu menu_10 = new Menu(mntmNote);
		mntmNote.setMenu(menu_10);

		MenuItem mntmNotedetail = new MenuItem(menu_10, SWT.NONE);
		mntmNotedetail.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/notes.png"));
		mntmNotedetail.setText("Note");

		MenuItem mntmReport = new MenuItem(menu, SWT.CASCADE);
		mntmReport.setText("Report");

		Menu menu_3 = new Menu(mntmReport);
		mntmReport.setMenu(menu_3);

		MenuItem mntmReportdetail = new MenuItem(menu_3, SWT.NONE);
		mntmReportdetail.setText("Report");
		mntmReportdetail.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/char25.png"));
		mntmReportdetail.setAccelerator(131151);

		MenuItem mntmLanguage = new MenuItem(menu, SWT.CASCADE);
		mntmLanguage.setText("&Language");

		Menu menu_8 = new Menu(mntmLanguage);
		mntmLanguage.setMenu(menu_8);

		MenuItem mntmEnglish = new MenuItem(menu_8, SWT.NONE);
		mntmEnglish.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/englisht.png"));
		mntmEnglish.setText("English\tAlt + E");
		mntmEnglish.setAccelerator(SWT.MOD3 + 'E');

		MenuItem mntmVietnamese = new MenuItem(menu_8, SWT.NONE);
		mntmVietnamese.setImage(SWTResourceManager.getImage(MainWindowCCTV.class, "/cctv/Icon/menu/Vietnam.png"));
		mntmVietnamese.setText("Việt Nam\tAlt + V");
		mntmVietnamese.setAccelerator(SWT.MOD3 + 'V');

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder tabFolder = new CTabFolder(composite, SWT.TOP);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		db_url = "jdbc:sqlserver://192.168.30.7;databaseName=IPManagerCCTV;user=camera;password=ManagerIPcctv!20201016";

		// Đóng ứng dụng Exit Window
		// -----------------------------------------------------------------------------
		mntmClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
				// shell.close();
				// Display.getDefault().dispose();
			}
		});

		// Mở cửa sổ IP List
		// ===================================================================================
		mntmIpList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("IP")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("IP")) {
							return;
						}
					}
				}
				try {
					IPList iplist = new IPList();
					iplist.showTabFolder(tabFolder, shell, language);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);
				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Khu vực
		// ----------------------------------------------------------------------------------
		mntmListLocation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Khu vực")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("Location")) {
							return;
						}
					}
				}
				try {
					Location location = new Location();
					location.showTabFolder(tabFolder,shell, language);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Loại Máy - Machine Type
		// ------------------------------------------------------------------
		mntmMachineTypeList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Loại máy")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("Machine type")) {
							return;
						}
					}
				}
				try {
					MachineType machine = new MachineType();
					machine.showTabFolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Ghi chú
		// ---------------------------------------------------------------------
		mntmNotedetail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Ghi chú")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("Note")) {
							return;
						}
					}
				}
				try {
					Note ghichu = new Note();
					ghichu.showTabFolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở báo cáo
		// *******************************************************************************************************************************************
		mntmReportdetail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Báo cáo")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("Report")) {
							return;
						}
					}
				}
				try {
					Report report = new Report();
					report.showTabFolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Thay đổi ngôn ngữ thành Tiếng Anh
		// -------------------------------------------------------------------------------------------------
		mntmEnglish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				shell.setText("IP Manager - " + version);
				mntmFile.setText("&File");
				mntmClose.setText("Exit\tCtrl + Q");
				mntmIp.setText("&IP");
				mntmIpList.setText("IP List\tCtrl + L");
				mntmLocation.setText("&Location");
				mntmListLocation.setText("Location\tCtrl + B");
				mntmMachineType.setText("&Machine Type");
				mntmMachineTypeList.setText("Machine Type List\tCtrl + M");
				mntmNote.setText("Note");
				mntmNotedetail.setText("Note");
				mntmReport.setText("Report");
				mntmReportdetail.setText("Report");
				mntmLanguage.setText("&Language");

				if (language == 0) {
					for (CTabItem itemrun : tabFolder.getItems()) {
						itemrun.dispose();
					}
				}

				language = 1;
				Display.getDefault().update();
			}
		});

		// Thay đổi ngôn ngữ thành Tiếng Việt
		// -------------------------------------------------------------------------------------------------
		mntmVietnamese.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				shell.setText("Quản lý IP - " + version);
				mntmFile.setText("Tệp");
				mntmClose.setText("Thoát\tCtrl + Q");
				mntmIp.setText("IP");
				mntmIpList.setText("Danh sách IP\tCtrl + L");
				mntmLocation.setText("Khu Vực");
				mntmListLocation.setText("Khu Vực\tCtrl + B");
				mntmMachineType.setText("Loại Máy");
				mntmMachineTypeList.setText("Danh sách loại máy\tCtrl + M");
				mntmNote.setText("Ghi Chú");
				mntmNotedetail.setText("Ghi Chú");
				mntmReport.setText("Báo Cáo");
				mntmReportdetail.setText("Báo Cáo");
				mntmLanguage.setText("Ngôn Ngữ");

				if (language == 1) {
					for (CTabItem itemrun : tabFolder.getItems()) {
						itemrun.dispose();
					}
				}

				language = 0;
				Display.getDefault().update();
			}
		});
	}

	// ***********************************************************************************************************
	// Resize hinh anh
	public Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}
}
