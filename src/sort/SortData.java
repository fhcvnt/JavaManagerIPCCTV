package sort;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SortData {
	private Table table;
	private boolean number = true; // kiểm tra xem table có cột number (STT) hay không, number=true có,
									// number=false không có cột STT
	private int selectcolumn = -1; // cột được chọn, nếu chọn lần nữa sẽ sắp xếp ngược lại từ Z - A

	public SortData() {
	}

	public void setTable(Table ta) {
		table = ta;
	}

	public void setNumber(boolean num) {
		number = num;
	}

	// -------------------------------------------------------------------------------
	// Sắp xếp dữ liệu cho cột có nội dung là số và chữ
	public Listener sortListenerCode = new Listener() {
		public void handleEvent(Event e) {
			TableItem[] items = table.getItems();
			TableColumn column = (TableColumn) e.widget;
			int index = table.indexOf(column); // lấy vị trí cột đang chọn có tên là column gán cho index

			// có cột số thứ tự
			if (selectcolumn != index) {
				selectcolumn = index;

				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareCode(value1, value2) < 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			} else {
				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareCode(value1, value2) > 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
				selectcolumn = -1;
			}

			// sap xep lai cot number
			if (number) {
				for (int j = 0; j < items.length; j++) {
					String[] values = new String[table.getColumnCount()];
					values[0] = (j + 1) + "";
					for (int n = 1; n < table.getColumnCount(); n++) {
						values[n] = items[j].getText(n);
					}
					items[j].dispose();
					TableItem item2 = new TableItem(table, SWT.NONE, j);
					item2.setText(values);
					items = table.getItems();
				}
			}

		}

	};

	// -------------------------------------------------------------------------------
	// Sắp xếp dữ liệu cho cột có nội dung là ngày tháng
	public Listener sortListenerDate = new Listener() {
		public void handleEvent(Event e) {
			TableItem[] items = table.getItems();
			TableColumn column = (TableColumn) e.widget;
			int index = table.indexOf(column); // lấy vị trí cột đang chọn có tên là column gán cho index

			// có cột số thứ tự
			if (selectcolumn != index) {
				selectcolumn = index;

				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareDate(value1, value2) < 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			} else {
				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareDate(value1, value2) > 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
				selectcolumn = -1;
			}

			// sap xep lai cot number
			if (number) {
				for (int j = 0; j < items.length; j++) {
					String[] values = new String[table.getColumnCount()];
					values[0] = (j + 1) + "";
					for (int n = 1; n < table.getColumnCount(); n++) {
						values[n] = items[j].getText(n);
					}
					items[j].dispose();
					TableItem item2 = new TableItem(table, SWT.NONE, j);
					item2.setText(values);
					items = table.getItems();
				}
			}

		}

	};
	// -------------------------------------------------------------------------------
	// Sắp xếp dữ liệu cho cột có nội dung là ngày tháng năm thời gian, có dạng:
	// 25/06/2021 12:24:26
	public Listener sortListenerDateTime = new Listener() {
		public void handleEvent(Event e) {
			TableItem[] items = table.getItems();
			TableColumn column = (TableColumn) e.widget;
			int index = table.indexOf(column); // lấy vị trí cột đang chọn có tên là column gán cho index

			// có cột số thứ tự
			if (selectcolumn != index) {
				selectcolumn = index;

				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareDateTime(value1, value2) < 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			} else {
				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareDateTime(value1, value2) > 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
				selectcolumn = -1;
			}

			// sap xep lai cot number
			if (number) {
				for (int j = 0; j < items.length; j++) {
					String[] values = new String[table.getColumnCount()];
					values[0] = (j + 1) + "";
					for (int n = 1; n < table.getColumnCount(); n++) {
						values[n] = items[j].getText(n);
					}
					items[j].dispose();
					TableItem item2 = new TableItem(table, SWT.NONE, j);
					item2.setText(values);
					items = table.getItems();
				}
			}

		}

	};

	// -------------------------------------------------------------------------------
	// Sắp xếp dữ liệu cho cột có nội dung là ngày tháng năm thời gian, có dạng:
	// 25/06/2021 12:24:26
	public Listener sortListenerDateTime_Thu_Nhiem = new Listener() {
		public void handleEvent(Event e) {
			try {
				TableItem[] items = table.getItems();
				TableColumn column = (TableColumn) e.widget;
				int index = table.indexOf(column); // lấy vị trí cột đang chọn có tên là column gán cho index
				String[][] a = new String[table.getItemCount()][table.getColumnCount()];
				int end = table.getItemCount() - 1;
				for (int i = 0; i < table.getItemCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						a[i][j] = items[i].getText(j);
					}
				}
				if (selectcolumn != index) {
					selectcolumn = index;
					table.removeAll();
					QuickSortDateTimeTable.quickSort(a, selectcolumn, 0, end);
					int stt = 1;
					for (int i = 0; i < table.getItemCount(); i++) {
						TableItem item = new TableItem(table, SWT.NONE);
						a[i][0] = stt + "";
						item.setText(a[i]);
						stt++;
					}

				} else {
					QuickSortDateTimeTable.quickSortDecrease(a, selectcolumn, 0, end);
					int stt = 1;
					for (int i = 0; i < table.getItemCount(); i++) {
						TableItem item = new TableItem(table, SWT.NONE);
						a[i][0] = stt + "";
						item.setText(a[i]);
						stt++;
					}
					selectcolumn = -1;
				}

				for (int i = 0; i < table.getItemCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						System.out.println(a[i][j]);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	};

	// -------------------------------------------------------------------------------
	// Sắp xếp dữ liệu cho cột có nội dung là IP
	public Listener sortListenerIP = new Listener() {
		public void handleEvent(Event e) {
			TableItem[] items = table.getItems();
			TableColumn column = (TableColumn) e.widget;
			int index = table.indexOf(column); // lấy vị trí cột đang chọn có tên là column gán cho index

			// có cột số thứ tự
			if (selectcolumn != index) {
				selectcolumn = index;

				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareIP(value1, value2) < 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
			} else {
				for (int i = 1; i < items.length; i++) {
					String value1 = items[i].getText(index);
					for (int j = 0; j < i; j++) {
						String value2 = items[j].getText(index);
						if (compareIP(value1, value2) > 0) {
							String[] values = new String[table.getColumnCount()];
							for (int n = 0; n < table.getColumnCount(); n++) {
								values[n] = items[i].getText(n);
							}
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							items = table.getItems();
							break;
						}
					}
				}
				selectcolumn = -1;
			}

			// sap xep lai cot number
			if (number) {
				for (int j = 0; j < items.length; j++) {
					String[] values = new String[table.getColumnCount()];
					values[0] = (j + 1) + "";
					for (int n = 1; n < table.getColumnCount(); n++) {
						values[n] = items[j].getText(n);
					}
					items[j].dispose();
					TableItem item2 = new TableItem(table, SWT.NONE, j);
					item2.setText(values);
					items = table.getItems();
				}
			}

		}

	};

	// hàm so sánh giữa 2 IP
	// ------------------------------------------------------------------------------------------------------------------------
	public static int compareIP(String ip1, String ip2) {
		if (ip1.isEmpty()) {
			return -1;
		} else if (ip2.isEmpty()) {
			return 1;
		} else {
			String[] ip1string = ip1.split("\\.");
			String[] ip2string = ip2.split("\\.");
			int[] ip1number = new int[4];
			try {
				ip1number[0] = Integer.parseInt(ip1string[0]);
				ip1number[1] = Integer.parseInt(ip1string[1]);
				ip1number[2] = Integer.parseInt(ip1string[2]);
				ip1number[3] = Integer.parseInt(ip1string[3]);
			} catch (NumberFormatException e) {
			}
			int[] ip2number = new int[4];
			try {
				ip2number[0] = Integer.parseInt(ip2string[0]);
				ip2number[1] = Integer.parseInt(ip2string[1]);
				ip2number[2] = Integer.parseInt(ip2string[2]);
				ip2number[3] = Integer.parseInt(ip2string[3]);
			} catch (NumberFormatException e) {
			}

			if (ip1number[0] > ip2number[0]) {
				return 1;
			} else if (ip1number[0] < ip2number[0]) {
				return -1;
			} else {
				if (ip1number[1] > ip2number[1]) {
					return 1;
				} else if (ip1number[1] < ip2number[1]) {
					return -1;
				} else {
					if (ip1number[2] > ip2number[2]) {
						return 1;
					} else if (ip1number[2] < ip2number[2]) {
						return -1;
					} else {
						if (ip1number[3] > ip2number[3]) {
							return 1;
						} else if (ip1number[3] < ip2number[3]) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			}
		}
	}

	// format dd/MM/yyy, Example: "29/09/2020"
	// So sánh ngày tháng năm
	// ------------------------------------------------------------------------------------------------------------------------
	public static int compareDate(String date1, String date2) {
		if (date1.isEmpty()) {
			return -1;
		} else if (date2.isEmpty()) {
			return 1;
		} else {
			String[] date1string = date1.split("/");
			String[] date2string = date2.split("/");
			int[] date1number = new int[3];
			if (date1string.length == 3 && date2string.length == 3) {
				try {
					date1number[0] = Integer.parseInt(date1string[0]);
					date1number[1] = Integer.parseInt(date1string[1]);
					date1number[2] = Integer.parseInt(date1string[2]);
				} catch (NumberFormatException e) {
				}
				int[] date2number = new int[3];
				try {
					date2number[0] = Integer.parseInt(date2string[0]);
					date2number[1] = Integer.parseInt(date2string[1]);
					date2number[2] = Integer.parseInt(date2string[2]);
				} catch (NumberFormatException e) {
				}

				if (date1number[2] > date2number[2]) {
					return 1;
				} else if (date1number[2] < date2number[2]) {
					return -1;
				} else {
					if (date1number[1] > date2number[1]) {
						return 1;
					} else if (date1number[1] < date2number[1]) {
						return -1;
					} else {
						if (date1number[0] > date2number[0]) {
							return 1;
						} else if (date1number[0] < date2number[0]) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			} else {
				if (date2string.length == 3) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}

	// format dd/MM/yyy hh:mm:ss, Example: "29/09/2020 04:12:25"
	// So sánh ngày tháng năm thời gian
	// ------------------------------------------------------------------------------------------------------------------------
	public static int compareDateTime(String datetime1, String datetime2) {
		try {
			if (datetime1.isEmpty()) {
				return -1;
			} else if (datetime2.isEmpty()) {
				return 1;
			} else {
				String[] date1string = new String[6];
				date1string[0] = datetime1.substring(0, 2);
				date1string[1] = datetime1.substring(3, 5);
				date1string[2] = datetime1.substring(6, 10);
				date1string[3] = datetime1.substring(11, 13);
				date1string[4] = datetime1.substring(14, 16);
				date1string[5] = datetime1.substring(17, 19);

				String[] date2string = new String[6];
				date2string[0] = datetime2.substring(0, 2);
				date2string[1] = datetime2.substring(3, 5);
				date2string[2] = datetime2.substring(6, 10);
				date2string[3] = datetime2.substring(11, 13);
				date2string[4] = datetime2.substring(14, 16);
				date2string[5] = datetime2.substring(17, 19);

				int[] date1number = new int[6];
				if (date1string.length == 6 && date2string.length == 6) {
					try {
						date1number[0] = Integer.parseInt(date1string[0]);
						date1number[1] = Integer.parseInt(date1string[1]);
						date1number[2] = Integer.parseInt(date1string[2]);
						date1number[3] = Integer.parseInt(date1string[3]);
						date1number[4] = Integer.parseInt(date1string[4]);
						date1number[5] = Integer.parseInt(date1string[5]);
					} catch (NumberFormatException e) {
					}
					int[] date2number = new int[6];
					try {
						date2number[0] = Integer.parseInt(date2string[0]);
						date2number[1] = Integer.parseInt(date2string[1]);
						date2number[2] = Integer.parseInt(date2string[2]);
						date2number[3] = Integer.parseInt(date2string[3]);
						date2number[4] = Integer.parseInt(date2string[4]);
						date2number[5] = Integer.parseInt(date2string[5]);
					} catch (NumberFormatException e) {
					}

					if (date1number[2] > date2number[2]) {
						return 1;
					} else if (date1number[2] < date2number[2]) {
						return -1;
					}
					if (date1number[1] > date2number[1]) {
						return 1;
					} else if (date1number[1] < date2number[1]) {
						return -1;
					}
					if (date1number[0] > date2number[0]) {
						return 1;
					} else if (date1number[0] < date2number[0]) {
						return -1;
					}

					// so sanh thoi gan
					if (date1number[3] > date2number[3]) {
						return 1;
					} else if (date1number[3] < date2number[3]) {
						return -1;
					}
					if (date1number[4] > date2number[4]) {
						return 1;
					} else if (date1number[4] < date2number[4]) {
						return -1;
					}
					if (date1number[5] > date2number[5]) {
						return 1;
					} else if (date1number[5] < date2number[5]) {
						return -1;
					} else {
						return 0;
					}
				} else {
					if (date2string.length == 6) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		} catch (Exception ex) {
		}
		return -1;
	}

	// So sánh số và chữ, số sẽ nhỏ hơn là chữ, ví dụ: 21608 < "tri"
	// ------------------------------------------------------------------------------------------------------------------------
	public static int compareCode(String code1, String code2) {
		int sothe1, sothe2;
		try {
			sothe1 = Integer.parseInt(code1);
		} catch (NumberFormatException ne) {
			try {
				sothe2 = Integer.parseInt(code2);
				return 1;
			} catch (NumberFormatException nexc) {
				if (code1.compareToIgnoreCase(code2) > 0) {
					return 1;
				} else if (code1.compareToIgnoreCase(code2) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}

		try {
			sothe2 = Integer.parseInt(code2);
			if (sothe1 > sothe2) {
				return 1;
			} else if (sothe1 < sothe2) {
				return -1;
			} else {
				return 0;
			}
		} catch (NumberFormatException ne) {
			return -1;
		}
	}
}
