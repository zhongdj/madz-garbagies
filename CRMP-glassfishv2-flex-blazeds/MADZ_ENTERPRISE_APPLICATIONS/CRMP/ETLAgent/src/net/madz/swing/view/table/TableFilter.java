package net.madz.swing.view.table;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

public abstract class TableFilter extends TableMap {

	protected int[] indexes = null;

	public int column = -1;

	protected Object[] values = null;

	protected boolean _shouldIncrementColumnIndex = false;

	public void setShouldIncrementColumnIndex(boolean newValue) {
		_shouldIncrementColumnIndex = newValue;
	}

	protected boolean getShouldIncrementColumnIndex() {
		return _shouldIncrementColumnIndex;
	}

	public TableFilter(int column) {
		this.column = column;
		indexes = new int[0];
	}

	public TableFilter(TableModel model, int column) {
		this.column = column;
		setTableModel(model);
	}

	public void setTableModel(TableModel model) {
		super.setModel(model);
		filter();
	}

	public abstract void filter();

	public void tableChanged(TableModelEvent evt) {
		filter();
		super.tableChanged(evt);
	}

	public int getRowCount() {
		return indexes.length;
	}

	public Object getValueAt(int row, int column) {
		try {
			return model.getValueAt(indexes[row], column);
		} catch (Exception e) {

		}
		return null;
	}

	public void setValueAt(Object newValue, int row, int column) {
		model.setValueAt(newValue, indexes[row], column);
		fireTableChanged(new TableModelEvent(this, row, row, column));
	}

	public void filter(Object value) {
		Object[] values = new Object[1];
		values[0] = value;
		filter(values);
	}

	public void filter(Object[] valuesArray) {
		this.values = valuesArray;
		filter();
		super.tableChanged(new TableModelEvent(this));
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Object[] getValues() {
		return this.values;
	}

	public void setValues(Object[] valuesArray) {
		this.values = valuesArray;
	}

	public int getModelIndex(int rowIndex) {
		if (model instanceof TableFilter) {
			return ((TableFilter) model).getModelIndex(indexes[rowIndex]);
		}

		return indexes[rowIndex];
	}
}