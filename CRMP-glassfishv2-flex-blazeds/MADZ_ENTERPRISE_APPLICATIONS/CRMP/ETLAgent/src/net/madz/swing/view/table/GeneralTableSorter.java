package net.madz.swing.view.table;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class GeneralTableSorter {

	protected int sortingColumns;

	protected boolean ascending = true;

	private int lastColumnIndexSort = -1;

	private String[] columnNames = {};
	private Object[][] data = {};

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] tmpData) {
		data = tmpData;
	}

	private void sort() {
		Arrays.sort(data, new Comparator() {
			public int compare(Object o1, Object o2) {
				int result = compareRowsByColumn((Object[]) o1, (Object[]) o2, sortingColumns);
				if (result != 0) {
					return ascending ? result : -result;
				}
				return 0;
			}
		});
	}

	protected int compareRowsByColumn(Object[] row1, Object[] row2, int column) {
		Object tmpObject1 = row1[column];
		Object tmpObject2 = row2[column];

		// If both values are null, return 0.
		if (tmpObject1 == null && tmpObject2 == null) {
			return 0;
		} else if (tmpObject1 == null) { // Define null less than everything.
			return -1;
		} else if (tmpObject2 == null) {
			return 1;
		}
		Class type = tmpObject1.getClass();
		Object tmpNewObject1, tmpNewObject2;

		// Change objects to be compared to objects that implement the
		// Comparable interface
		if (type.getSuperclass() == java.lang.Number.class) {
			tmpNewObject1 = new Double(((Number) tmpObject1).doubleValue());
			tmpNewObject2 = new Double(((Number) tmpObject2).doubleValue());
		} else {
			if (type == java.util.Date.class || type == String.class) {
				tmpNewObject1 = tmpObject1;
				tmpNewObject2 = tmpObject2;
			} else {
				tmpNewObject1 = tmpObject1.toString();
				tmpNewObject2 = tmpObject2.toString();
			}
		}
		return ((Comparable) tmpNewObject1).compareTo(tmpNewObject2);
	}

	private void sortByColumn(int column, boolean ascending) {

		this.ascending = ascending;
		// System.out.println("public void sortByColumn : ascending = "+ascending
		// );
		sortingColumns = column;
		sort();
	}

	private Object[][] _copySelectedDataRows(ListSelectionModel fSelModel) {
		Vector lRows = new Vector();
		Object[][] lRes = null;

		for (int lRowIdx = fSelModel.getMinSelectionIndex(); lRowIdx >= 0 && lRowIdx <= fSelModel.getMaxSelectionIndex(); lRowIdx++) {
			if (fSelModel.isSelectedIndex(lRowIdx)) {
				Object[] lRow = new Object[data[0].length];
				for (int lColIdx = 0; lColIdx < data[0].length; lColIdx++) {
					lRow[lColIdx] = data[lRowIdx][lColIdx];
				}
				lRows.add(lRow);
			}
		}

		if (lRows.size() > 0) {
			lRes = new Object[lRows.size()][];
			lRows.copyInto(lRes);
		}

		return lRes;
	}

	private void _reselectDataRows(ListSelectionModel listSelectionModel, Object[][] dataRows) {
		listSelectionModel.clearSelection();
		if (dataRows != null) {
			for (int i = 0; i < dataRows.length; i++) {
				for (int dataRowIndexe = 0; dataRowIndexe < data.length; dataRowIndexe++) {
					boolean isSelected = false;
					for (int numberColumn = 0; numberColumn < dataRows[0].length; numberColumn++) {
						isSelected = (compareRowsByColumn(data[dataRowIndexe], dataRows[i], numberColumn) == 0);
					}
					if (isSelected) {
						listSelectionModel.addSelectionInterval(dataRowIndexe, dataRowIndexe);
					}
				}
			}
		}
	}

	public void addMouseListenerToHeaderInTable(JTable table) {

		final JTable tableView = table;
		tableView.setColumnSelectionAllowed(false);
		MouseAdapter listMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				TableColumnModel columnModel = tableView.getColumnModel();
				int viewColumn = columnModel.getColumnIndexAtX(e.getX());
				int column = tableView.convertColumnIndexToModel(viewColumn);
				Object[][] lSelectedCopies = _copySelectedDataRows(tableView.getSelectionModel());
				if (column != -1) {
					if (lastColumnIndexSort == column) {
						lastColumnIndexSort = -1;
						sortByColumn(column, false);
					} else {
						lastColumnIndexSort = column;
						sortByColumn(column, true);
					}
				}
				_reselectDataRows(tableView.getSelectionModel(), lSelectedCopies);
				if (tableView.getSelectionModel().getMinSelectionIndex() >= 0) {
					int lSelRowIdx = tableView.getSelectionModel().getMinSelectionIndex();
					Rectangle lRect = tableView.getCellRect(lSelRowIdx, 0, true);
					tableView.scrollRectToVisible(lRect);
				}
			}
		};

		JTableHeader th = tableView.getTableHeader();
		th.addMouseListener(listMouseListener);
	}
}
