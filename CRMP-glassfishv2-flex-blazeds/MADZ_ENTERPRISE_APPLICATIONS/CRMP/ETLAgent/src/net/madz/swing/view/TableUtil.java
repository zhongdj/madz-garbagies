package net.madz.swing.view;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import net.madz.swing.view.util.JPagingTable;


public class TableUtil {

	public static final String idPrefix = "RecId-";

	public static synchronized void addRowsToTable(final Object[][] newRows,
			final JPagingTable _pagingTable, final boolean isForAlarm) {
		
		Runnable doWorkRunnable = new Runnable() {

			public void run() {
				int length = newRows.length;
				String[] newIds = new String[length];
				
				for (int j = 0; j < length; j++) {
					Object[] row = newRows[j];
					String newId = makeID(row);
					if (isForAlarm) {
						newId = makeAlarmID(row);
					}
					newIds[j] = newId;
				}
				
				try {
					_pagingTable.batchAddRowsRespectSorting(newRows, newIds);
				} catch (UnsupportedOperationException ex) {
					ex.printStackTrace();
				}
			}
		};
		runInSwingThread(doWorkRunnable);

	}

	public static synchronized void addFragementPathToTable(
			final Object[][] newRows, final JPagingTable _pagingTable) {
		Runnable doWorkRunnable = new Runnable() {

			public void run() {
				int length = newRows.length;
				String[] newIds = new String[length];
				for (int j = 0; j < length; j++) {
					Object[] row = newRows[j];
					String newId = makeFragmentPathID(row);

					newIds[j] = newId;
				}
				try {
					_pagingTable.batchAddRowsRespectSorting(newRows, newIds);
				} catch (UnsupportedOperationException ex) {
					ex.printStackTrace();
				}
			}
		};
		runInSwingThread(doWorkRunnable);

	}

	public static synchronized void addResultRowsToTable(
			final Object[][] newRows, final JPagingTable _pagingTable) {
		Runnable doWorkRunnable = new Runnable() {

			public void run() {
				int length = newRows.length;
				String[] newIds = new String[length];
				for (int j = 0; j < length; j++) {
					Object[] row = newRows[j];
					String newId = makeAlarmID(row);
					newIds[j] = newId;
				}

				try {
					_pagingTable.batchAddRowsRespectSorting(newRows, newIds);
				} catch (UnsupportedOperationException ex) {
				}
			}
		};
		runInSwingThread(doWorkRunnable);

	}
	
	public static synchronized void addOperatorLogRowsToTable(
			final Object[][] newRows, final JPagingTable _pagingTable) {
		Runnable doWorkRunnable = new Runnable() {

			public void run() {
				int length = newRows.length;
				String[] newIds = new String[length];
				for (int j = 0; j < length; j++) {
					Object[] row = newRows[j];
					String newId = makeOperatorlogID(row);
					newIds[j] = newId;
				}

				try {
					_pagingTable.batchAddRowsRespectSorting(newRows, newIds);
				} catch (UnsupportedOperationException ex) {
				}
			}
		};
		runInSwingThread(doWorkRunnable);

	}

	public static void runInSwingThread(Runnable job) {
		if (SwingUtilities.isEventDispatchThread()) {
			job.run();
		} else {
			try {
				SwingUtilities.invokeLater(job);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String makeID(final Object[] row) {
		StringBuilder newId = new StringBuilder(100);
		newId.append(idPrefix);
		newId.append(row[0]);
		return newId.toString();
	}

	public static String makeFragmentPathID(final Object[] row) {
		StringBuilder newId = new StringBuilder(100);
		newId.append(idPrefix);
		newId.append(row[row.length - 1]);
		return newId.toString();
	}

	public static String makeAlarmID(final Object[] row) {
		StringBuilder newId = new StringBuilder(200);
		newId.append(idPrefix);
		int length = row.length;
		newId.append(row[length - 1]);
		return newId.toString();
	}
	
	public static String makeOperatorlogID(final Object[] row) {
		StringBuilder newId = new StringBuilder(200);
		newId.append(idPrefix);
		int length = row.length;
		newId.append(row[length - 1]);
		return newId.toString();
	}

	public synchronized static void updateRowsToTable(final Object[] newRows,
			final JPagingTable _pagingTable, final boolean isForAlarm) {

		Runnable doWorkRunnable = new Runnable() {

			public void run() {

				String newId = makeID(newRows);
				if (isForAlarm) {
					newId = makeAlarmID(newRows);
				}

				Vector v = new Vector();
				if (isForAlarm) {
					for (int i = 0; i < newRows.length - 1; i++) {
						Object obj = newRows[i];
						v.add(obj);
					}
				} else {
					for (int i = 0; i < newRows.length; i++) {
						Object obj = newRows[i];
						v.add(obj);
					}
				}

				try {
					_pagingTable.updateRowData(newId, v);

				} catch (UnsupportedOperationException ex) {
					ex.printStackTrace();
				}
			}
		};
		runInSwingThread(doWorkRunnable);
	}

	public synchronized static void updateFragmentPathRowsToTable(
			final Object[] newRows, final JPagingTable _pagingTable) {

		Runnable doWorkRunnable = new Runnable() {

			public void run() {

				String newId = makeFragmentPathID(newRows);

				Vector v = new Vector();

				for (int i = 0; i < newRows.length - 1; i++) {
					Object obj = newRows[i];
					v.add(obj);
				}

				try {
					_pagingTable.updateRowData(newId, v);

				} catch (UnsupportedOperationException ex) {
					ex.printStackTrace();
				}
			}
		};
		runInSwingThread(doWorkRunnable);
	}

	public synchronized static void deleteRowsToTable(final Object[] newRows,
			final JPagingTable _pagingTable, final boolean isForAlarm) {
		Runnable doWorkRunnable = new Runnable() {

			public void run() {
				int length = newRows.length;
				String[] newIds = new String[length];
				String newId = makeID(newRows);
				if (isForAlarm) {
					newId = makeAlarmID(newRows);
				}
				try {

					_pagingTable.removeRowByIdentifier(newId);

				} catch (UnsupportedOperationException ex) {
				}
			}
		};
		runInSwingThread(doWorkRunnable);
	}
	
	public synchronized static void deleteFragmentPathRowsToTable(final Object[] newRows,
			final JPagingTable _pagingTable) {
		Runnable doWorkRunnable = new Runnable() {

			public void run() {
				int length = newRows.length;
				
				String newId = makeFragmentPathID(newRows);
				
				try {

					_pagingTable.removeRowByIdentifier(newId);

				} catch (UnsupportedOperationException ex) {
				}
			}
		};
		runInSwingThread(doWorkRunnable);
	}

	public static JButton createTextButton(String fTitle) {
		JButton lRes = new JButton(fTitle);

		Dimension lPref = lRes.getPreferredSize();
		int width = lPref.width;
		int height = lPref.height;
		height -= 5;
		if (width <= 90)
			width = 90;

		lRes.setPreferredSize(new Dimension(width, height));
		lRes.setMaximumSize(new Dimension(3000, height));
		return lRes;
	}

}
