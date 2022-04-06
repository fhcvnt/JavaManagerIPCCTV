package sort;

public class QuickSortDateTimeTable {
	public static void main(String[] args) {
		int i;
		String[][] arr = {
				{ "24/06/2021 16:23:49", "15/02/2021 18:20:19", "02/02/2019 20:05:06", "22/03/2021 05:25:29",
						"08/03/2021 05:10:19", "21/10/2020 18:20:19", "22/12/2010 18:20:19", "22/12/2020 11:20:19",
						"22/12/2020 18:30:29", "22/12/2020 18:20:19","24/06/2021 16:23:49", "15/02/2021 18:20:19", "02/02/2019 20:05:06", "22/03/2021 05:25:29",
						"08/03/2021 05:10:19", "21/10/2020 18:20:19", "22/12/2010 18:20:19", "22/12/2020 11:20:19",
						"22/12/2020 18:30:29", "22/12/2020 18:20:19" },
				{ "25/06/2022 16:20:49", "15/02/2021 18:20:19", "02/02/2019 20:05:06", "22/03/2021 05:25:29",
						"08/03/2021 05:10:19", "21/10/2020 18:20:19", "22/12/2010 18:20:19", "22/12/2020 11:20:19",
						"22/12/2020 18:30:29", "22/12/2020 18:20:19","24/06/2021 16:23:49", "15/02/2021 18:20:19", "02/02/2019 20:05:06", "22/03/2021 05:25:29",
						"08/03/2021 05:10:19", "21/10/2020 18:20:19", "22/12/2010 18:20:19", "22/12/2020 11:20:19",
						"22/12/2020 18:30:29", "22/12/2020 18:20:19" },
				{ "24/06/2021 18:23:49", "15/02/2021 18:20:19", "02/02/2019 20:05:06", "22/03/2021 05:25:29",
						"08/03/2021 05:10:19", "21/10/2020 18:20:19", "22/12/2010 18:20:19", "22/12/2020 11:20:19",
						"22/12/2020 18:30:29", "22/12/2020 18:20:19","24/06/2021 16:23:49", "15/02/2021 18:20:19", "02/02/2019 20:05:06", "22/03/2021 05:25:29",
						"08/03/2021 05:10:19", "21/10/2020 18:20:19", "22/12/2010 18:20:19", "22/12/2020 11:20:19",
						"22/12/2020 18:30:29", "22/12/2020 18:20:19" } };
		String chuoi = "";
		for (int j = 0; j < arr.length; j++) {
			for (int k = 0; k < arr[0].length; k++) {
				chuoi = chuoi + arr[j][k] + " ";
			}
			chuoi = chuoi + "\n";
		}
		System.out.println(chuoi);
		System.out.println("arr: "+arr.length);
		quickSort(arr,0, 0, 2);
		quickSortDecrease(arr,0, 0, 2);
		System.out.println("\n The sorted array is: \n");
		for (i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static int partition(String a[][], int positionColumn, int beg, int end) {
		// positionColumn: là vị trí cột cần sắp xếp trong mảng 2 chiều
		int left, right, loc, flag;
		String temp[];
		loc = left = beg;
		right = end;
		flag = 0;
		while (flag != 1) {
			while (compareDateTime(a[loc][positionColumn], a[right][positionColumn]) <= 0 && (loc != right))
				right--;
			if (loc == right)
				flag = 1;
			else if (compareDateTime(a[loc][positionColumn], a[right][positionColumn]) > 0) {
				temp = a[loc];
				a[loc] = a[right];
				a[right] = temp;
				loc = right;
			}
			if (flag != 1) {
				while ((compareDateTime(a[loc][positionColumn], a[left][positionColumn]) >= 0) && (loc != left))
					left++;
				if (loc == left)
					flag = 1;
				else if (compareDateTime(a[loc][positionColumn], a[left][positionColumn]) < 0) {
					temp = a[loc];
					a[loc] = a[left];
					a[left] = temp;
					loc = left;
				}
			}
		}
		return loc;
	}

	public static void quickSort(String a[][],int positionColumn, int beg, int end) {
		int loc;
		if (beg < end) {
			loc = partition(a, positionColumn, beg, end);
			quickSort(a,positionColumn, beg, loc - 1);
			quickSort(a,positionColumn, loc + 1, end);
		}
	}
	
	public static int partitionDecrease(String a[][], int positionColumn, int beg, int end) {
		// positionColumn: là vị trí cột cần sắp xếp trong mảng 2 chiều
		int left, right, loc, flag;
		String temp[];
		loc = left = beg;
		right = end;
		flag = 0;
		while (flag != 1) {
			while (compareDateTime(a[loc][positionColumn], a[right][positionColumn])<= 0 && (loc != right))
				right--;
			if (loc == right)
				flag = 1;
			else if (compareDateTime(a[loc][positionColumn], a[right][positionColumn]) < 0) {
				temp = a[loc];
				a[loc] = a[right];
				a[right] = temp;
				loc = right;
			}
			if (flag != 1) {
				while ((compareDateTime(a[loc][positionColumn], a[left][positionColumn]) >= 0) && (loc != left))
					left++;
				if (loc == left)
					flag = 1;
				else if (compareDateTime(a[loc][positionColumn], a[left][positionColumn]) > 0) {
					temp = a[loc];
					a[loc] = a[left];
					a[left] = temp;
					loc = left;
				}
			}
		}
		return loc;
	}

	public static void quickSortDecrease(String a[][],int positionColumn, int beg, int end) {
		int loc;
		if (beg < end) {
			loc = partitionDecrease(a, positionColumn, beg, end);
			quickSort(a,positionColumn, beg, loc - 1);
			quickSort(a,positionColumn, loc + 1, end);
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
}