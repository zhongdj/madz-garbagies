package net.madz.swing.view.table;

import java.util.ArrayList;

import javax.swing.table.TableModel;

public class TableMatchFilter extends TableFilter {

	protected ArrayList tempIndexes = new ArrayList();

	protected CellComparator _comparator = null;

	public TableMatchFilter(int column, CellComparator comparator) {
		super(column);

		if (comparator == null) {

			return;
		}
		_comparator = comparator;
	}

	public TableMatchFilter(TableModel model, int column, CellComparator comparator) {
		super(model, column);

		// Firstly, check if we have comparator to use
		if (comparator == null) {

			return;
		}
		_comparator = comparator;
	}

	public synchronized void filter() {
		// Firstly, check if we have comparator to use
		if (_comparator == null) {

			return;
		}

		int fColumn = getColumn();
		if (getShouldIncrementColumnIndex()) {
			fColumn++;
		}

		tempIndexes.clear();
		if (values == null) {
			return;
		}

		int rows = model.getRowCount();
		for (int row = 0; row < rows; row++) {
			if (fColumn < 0) {
				tempIndexes.add(Integer.valueOf(row));
			} else {
				Object value = model.getValueAt(row, fColumn);
				for (int i = 0; i < values.length; i++) {
					if (_comparator.compareObjects(values[i], value) == 0) {
						tempIndexes.add(Integer.valueOf(row));
					}
				}
			}
		}

		indexes = new int[tempIndexes.size()];
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = ((Integer) tempIndexes.get(i)).intValue();
		}
	}

}
