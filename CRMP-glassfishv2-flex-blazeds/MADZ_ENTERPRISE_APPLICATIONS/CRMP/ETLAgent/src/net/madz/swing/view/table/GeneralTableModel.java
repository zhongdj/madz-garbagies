package net.madz.swing.view.table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GeneralTableModel extends AbstractTableModel implements SortTableModel, TableModelListener {

	public DefaultTableModel _model = null;

	private Vector _columnClasses = null;

	private FiltersList _filterList = null;

	private int _identifierColumn = -1;

	private boolean _isEditingAllowed = true;

	private HashSet _hiddenIdentifiers = new HashSet();

	transient protected Vector _customCellComparators = new Vector();

	transient protected DefaultCellComparator _defaultCellComparator = new DefaultCellComparator();

	public boolean _shouldIgnoreTableNValueChangeEvents = false;
	public boolean _shouldInvokeValueChangeEvents = false;

	public Hashtable _identifierToRowIndex = null;

	public GeneralTableModel(Object[][] data, Object[] names, Class[] columnClasses, int identifierColumn) {
		this(convertToVector(data), convertToVector(names), convertToVector(columnClasses), identifierColumn);
	}

	public GeneralTableModel(Vector data, Vector names, Vector columnClasses, int identifierColumn) {
		_identifierColumn = identifierColumn;
		_columnClasses = columnClasses;
		_identifierToRowIndex = new Hashtable();

		Vector targetDataVector = new Vector();
		for (int i = 0; i < data.size(); i++) {
			Vector rowVector = convertToVector((Object[]) data.elementAt(i));
			Object identifier = rowVector.elementAt(_identifierColumn);
			if (!_identifierToRowIndex.containsKey(identifier)) {
				targetDataVector.add(rowVector);
				_identifierToRowIndex.put(identifier, Integer.valueOf(targetDataVector.size() - 1));
			} else {

			}

		}

		_model = new DefaultTableModel(targetDataVector, names);
		_init();
	}

	public GeneralTableModel(Object[][] data, Object[] names, Class[] columnClasses, Object[] identifiers) {
		this(convertToVector(data), convertToVector(names), convertToVector(columnClasses), convertToVector(identifiers));
	}

	public GeneralTableModel(Vector data, Vector names, Vector columnClasses, Vector identifiers) {
		_identifierToRowIndex = new Hashtable();

		Vector targetDataVector = new Vector();
		names.insertElementAt("Identifier_Column_Name", 0);
		_columnClasses = columnClasses;
		_columnClasses.insertElementAt(Object.class, 0);

		for (int i = 0; i < data.size(); i++) {
			Vector rowVector = convertToVector((Object[]) data.elementAt(i));
			Object identifier = identifiers.elementAt(i);
			rowVector.insertElementAt(identifier, 0);
			if (!_identifierToRowIndex.containsKey(identifier)) {
				targetDataVector.add(rowVector);
				_identifierToRowIndex.put(identifier, Integer.valueOf(targetDataVector.size() - 1));
			} else {

			}
		}

		_model = new DefaultTableModel(targetDataVector, names);
		_init();
	}

	public boolean isIdentifierDisplayed() {
		return (_identifierColumn != -1) ? true : false;
	}

	public int getIdentifierColumn() {
		return _identifierColumn;
	}

	public int getRowByIdentifier(Object identifier) {

		if (identifier == null) {
			return -1;
		}

		buildRealModelHash();
		if (getFiltersCount() == 0) {
			return getIndexInRealModel(identifier);
		} else {

			for (int i = 0; i < getRowCount(); i++) {
				Object aIdentifier = getIdentifier(i);
				if (aIdentifier == null) {
					continue;
				}
				if (identifier.equals(aIdentifier)) {
					return i;
				}
			}
		}

		return -1;
	}

	private void buildRealModelHash() {
		if (_identifierToRowIndex == null) {
			_identifierToRowIndex = new Hashtable();
			for (int i = 0; i < _model.getRowCount(); i++) {
				Object aIdentifier = null;
				try {
					int cIndex = (_identifierColumn != -1) ? _identifierColumn : 0;
					aIdentifier = _model.getValueAt(i, cIndex);
				} catch (ArrayIndexOutOfBoundsException ex) {

				}
				if (aIdentifier == null) {
					continue;
				}
				_identifierToRowIndex.put(aIdentifier, Integer.valueOf(i));
			}
		}
	}

	public int getIndexInRealModel(Object identifier) {
		buildRealModelHash();
		Object rowIndex = _identifierToRowIndex.get(identifier);
		if (rowIndex instanceof Integer) {
			return ((Integer) rowIndex).intValue();
		}
		return -1;
	}

	public boolean isIdentifierInRealModel(Object identifier) {

		if (identifier == null) {
			return false;
		}

		if (isIdentifierHidden(identifier)) {
			return true;
		}

		return getIndexInRealModel(identifier) == -1 ? false : true;
	}

	public Object getIdentifier(int row) {
		if (row < 0 || row >= getRowCount()) {
			return null;
		}

		try {
			int cIndex = (_identifierColumn != -1) ? _identifierColumn : 0;
			return _filterList.getValueAt(row, cIndex);
		} catch (ArrayIndexOutOfBoundsException ex) {
			return null;
		}
	}

	public void setIdentifier(int row, Object newIdentifier) throws IdentifierExistsException {
		if (row < 0 || row >= getRowCount() || newIdentifier == null) {
			return;
		}

		if (newIdentifier.equals(getIdentifier(row))) {
			// Trying to set the same identifier
			// Nothing to do here
			return;
		}

		if (isIdentifierInRealModel(newIdentifier)) {
			// Already exists
			throw new IdentifierExistsException(newIdentifier);
		}

		int cIndex = (_identifierColumn != -1) ? _identifierColumn : 0;
		_filterList.setValueAt(newIdentifier, row, cIndex);
	}

	public void addColumn(Object columnName, Class columnClass, Vector columnData) {
		_model.addColumn(columnName, columnData);
		_columnClasses.add(columnClass);
	}

	public void addColumn(Object columnName, Class columnClass, Object[] columnData) {
		_model.addColumn(columnName, columnData);
		_columnClasses.add(columnClass);
	}

	public void addRow(Vector rowData) throws IdentifierExistsException {
		if (_identifierColumn == -1) {
			// We are constructed with NON-displayed identifier
			String mess = "This object constructed with NON-displayed identifier."
					+ "Use (Vector rowData, Object identifier) method instead";
			throw new UnsupportedOperationException(mess);
		}

		Object identifier = rowData.elementAt(_identifierColumn);
		if (isIdentifierInRealModel(identifier)) {
			throw new IdentifierExistsException(identifier);
		}
		_model.addRow(rowData);
		if (_identifierToRowIndex != null) {
			_identifierToRowIndex.put(identifier, Integer.valueOf(_model.getRowCount() - 1));
		}
	}

	public void addRow(Object[] rowData) throws IdentifierExistsException {
		this.addRow(convertToVector(rowData));
	}

	public void addRow(Vector rowData, Object identifier) throws IdentifierExistsException {
		if (_identifierColumn != -1) {
			// We are constructed with displayed identifier
			String mess = "This object constructed with displayed identifier." + "Use (Vector rowData) method instead";
			throw new UnsupportedOperationException(mess);
		}

		if (isIdentifierInRealModel(identifier)) {
			throw new IdentifierExistsException(identifier);
		}

		rowData.insertElementAt(identifier, 0);
		_model.addRow(rowData);
		if (_identifierToRowIndex != null) {
			_identifierToRowIndex.put(identifier, Integer.valueOf(_model.getRowCount() - 1));
		}
	}

	public void addRow(Object[] rowData, Object identifier) throws IdentifierExistsException {
		this.addRow(convertToVector(rowData), identifier);
	}

	public void insertRow(int row, Vector rowData) throws IdentifierExistsException {
		if (_identifierColumn == -1) {
			// We are constructed with NON-displayed identifier
			String mess = "This object constructed with NON-displayed identifier."
					+ "Use (Vector rowData, Object identifier) method instead";
			throw new UnsupportedOperationException(mess);
		}

		Object identifier = rowData.elementAt(_identifierColumn);
		if (isIdentifierInRealModel(identifier)) {
			throw new IdentifierExistsException(identifier);
		}
		_identifierToRowIndex = null;
		_model.insertRow(getModelIndex(row), rowData);
	}

	public void insertRow(int row, Object[] rowData) throws IdentifierExistsException {
		this.insertRow(row, convertToVector(rowData));
	}

	public void insertRow(int row, Vector rowData, Object identifier) throws IdentifierExistsException {
		if (_identifierColumn != -1) {
			// We are constructed with displayed identifier
			String mess = "This object constructed with displayed identifier." + "Use (Vector rowData) method instead";
			throw new UnsupportedOperationException(mess);
		}

		if (isIdentifierInRealModel(identifier)) {
			throw new IdentifierExistsException(identifier);
		}

		rowData.insertElementAt(identifier, 0);
		_identifierToRowIndex = null;
		_model.insertRow(getModelIndex(row), rowData);
	}

	public void insertRow(int row, Object[] rowData, Object identifier) throws IdentifierExistsException {
		this.insertRow(row, convertToVector(rowData), identifier);
	}

	public void removeRow(int row) {
		if (row < 0 || row > this.getRowCount() - 1) {

			return;
		}

		_identifierToRowIndex = null;
		_model.removeRow(getModelIndex(row));
	}

	public void removeRows(int[] rowIndices) {
		if (rowIndices == null || rowIndices.length <= 0) {

			return;
		}

		Vector v = new Vector();
		for (int i = rowIndices.length - 1; i >= 0; i--) {
			v.addElement(Integer.valueOf(rowIndices[i]));
		}
		removeRows(v);
	}

	public void removeRows(Vector rowIndices) {
		if (rowIndices == null || rowIndices.size() <= 0) {

			return;
		}

		_shouldIgnoreTableNValueChangeEvents = true;
		Vector clone = (Vector) rowIndices.clone();
		Collections.sort(clone);

		int prevRowIndex = -1;
		int cloneSize = clone.size();
		for (int i = cloneSize - 1; i >= 0; i--) {
			Object o = clone.elementAt(i);
			if (o == null || !(o instanceof Integer)) {

				clone.removeElementAt(i);
				continue;
			}
			Integer intrep = (Integer) o;
			if (intrep.intValue() != prevRowIndex && intrep.intValue() >= 0)
				prevRowIndex = intrep.intValue();
			else
				clone.removeElementAt(i);
		}
		for (int i = clone.size() - 1; i >= 0; i--) {
			Object o = clone.elementAt(i);

			Integer intrep = (Integer) o;

			if (i == 0) {
				_shouldIgnoreTableNValueChangeEvents = false;
				_shouldInvokeValueChangeEvents = true;
			}

			removeRow(getModelIndex(intrep.intValue()));
		}

		_shouldInvokeValueChangeEvents = false;
		_shouldIgnoreTableNValueChangeEvents = false;
	}

	public void removeRows(int startIndex, int endIndex) {
		if (startIndex < 0 || endIndex < 0 || endIndex < startIndex || endIndex >= getRowCount()) {

			return;
		}

		_identifierToRowIndex = null;
		_shouldIgnoreTableNValueChangeEvents = true;
		for (int i = endIndex; i >= startIndex; i--) {
			if (i == startIndex) {
				_shouldIgnoreTableNValueChangeEvents = false;
				_shouldInvokeValueChangeEvents = true;
			}

			_model.removeRow(getModelIndex(i));
		}
		_shouldInvokeValueChangeEvents = false;
	}

	public void moveRow(int fromIndex, int toIndex) {
		_identifierToRowIndex = null;
		_model.moveRow(getModelIndex(fromIndex), getModelIndex(fromIndex), getModelIndex(toIndex));
	}

	public void addHiddenIdentifier(Object identifier) {
		// Fitst checking
		if (identifier == null) {

			return;
		}

		// Check that there is hidden row with this identifier
		if (this.isIdentifierHidden(identifier)) {

			return;
		}

		_hiddenIdentifiers.add(identifier);
	}

	public boolean isIdentifierHidden(Object identifier) {
		if (identifier == null) {
			return false;
		}

		if (_hiddenIdentifiers.contains(identifier)) {
			return true;
		}

		return false;
	}

	public void removeHiddenIdentifier(Object identifier) {
		// Fitst checking
		if (identifier == null) {

			return;
		}

		if (!_hiddenIdentifiers.remove(identifier)) {

		}
	}

	public boolean isSortable(int col) {

		return true;
	}

	public void sortColumn(int col, boolean ascending) {
		ColumnComparator c = new ColumnComparator(translateColumnIndex(col), ascending, getCellComparator(col));

		Collections.sort(_model.getDataVector(), c);
		_identifierToRowIndex = null;
		_model.fireTableDataChanged();
	}

	public CellComparator getCellComparator(int column) {
		if (column > getColumnCount()) {

			return null;
		}

		CellComparator cc = null;

		try {
			cc = (CellComparator) _customCellComparators.elementAt(column);
		} catch (ArrayIndexOutOfBoundsException ex) {

		}

		if (cc == null) {
			cc = _defaultCellComparator;
		}

		return cc;
	}

	public void setCellComparatorForColumn(int columnIndex, CellComparator comparator) {
		if (columnIndex < 0) {
			String m = "Incorrect parameter specified. columnIndex < 0";

		}

		setValueToVector(_customCellComparators, comparator, columnIndex);
	}

	public void removeCellComparatorForColumn(int columnIndex) {
		setCellComparatorForColumn(columnIndex, null);
	}

	public void addFilter(TableFilter filter) {

		if (!isIdentifierDisplayed()) {
			filter.setShouldIncrementColumnIndex(true);
		}

		_filterList.addFilter(filter);
	}

	public TableFilter getFilter(int column) {
		return _filterList.getFilter(column);
	}

	public void removeFilter(int column) {
		_filterList.removeFilter(column);
	}

	public int getFiltersCount() {
		return _filterList.getFiltersCount();
	}

	public void removeAllFilters() {
		if (getFiltersCount() == 0) {
			return;
		}

		TableFilter f = null;
		while (true) {
			f = _filterList.getLastFilter();

			if (f == null) {
				break;
			}

			// We should remove this filter
			this.removeFilter(f.column);
		}
	}

	public Vector getUniqueValuesForColumn(int column) {
		Vector toRet = new Vector();
		int rCount = getRowCount();
		CellComparator usedComparator = getCellComparator(column);

		for (int i = 0; i < rCount; i++) {
			Object value = getValueAt(i, column);
			if (value == null) {
				continue;
			}

			if (!isValueinVector(toRet, value, usedComparator)) {
				toRet.add(new Pair(i, value));
			}
		}

		return toRet;
	}

	private boolean isValueinVector(Vector vector, Object value, CellComparator comparator) {
		for (int i = 0; i < vector.size(); i++) {
			Pair elem = (Pair) vector.elementAt(i);

			if (0 == comparator.compareObjects(value, elem.value)) {
				return true;
			}
		}
		return false;
	}

	public int getRowCount() {
		return _filterList.getRowCount();
	}

	public int getColumnCount() {
		return _filterList.getColumnCount() + translateColumnIndexBack(0);
	}

	public String getColumnName(int columnIndex) {
		return _filterList.getColumnName(translateColumnIndex(columnIndex));
	}

	public Class getColumnClass(int columnIndex) {
		return (Class) _columnClasses.elementAt(translateColumnIndex(columnIndex));
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (!_isEditingAllowed) {
			return false;
		}

		return _filterList.isCellEditable(rowIndex, translateColumnIndex(columnIndex));
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			return _filterList.getValueAt(rowIndex, translateColumnIndex(columnIndex));
		} catch (ArrayIndexOutOfBoundsException ex) {
			return null;
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		if (isIdentifierDisplayed() && columnIndex == getIdentifierColumn()) {
			try {
				this.setIdentifier(rowIndex, aValue);
			} catch (IdentifierExistsException ex) {

			}

			return;
		}

		try {
			_filterList.setValueAt(aValue, rowIndex, translateColumnIndex(columnIndex));
		} catch (ArrayIndexOutOfBoundsException ex) {

		}
	}

	public void setEditingAllowed(boolean allowed) {
		_isEditingAllowed = allowed;
	}

	public boolean isEditingAllowed() {
		return _isEditingAllowed;
	}

	public void tableChanged(TableModelEvent e) {

		if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
			fireTableChanged(e);
			return;
		}

		TableModelEvent newEvent = new TableModelEvent((TableModel) e.getSource(), e.getFirstRow(), e.getLastRow(),
				translateColumnIndexBack(e.getColumn()), e.getType());

		fireTableChanged(newEvent);
	}

	public static class Pair {
		/** index of row */
		public int rowIndex;

		/** value for the cell in this row */
		public Object value;

		public Pair(int rowIndex, Object value) {
			// Just store these tow references for future using
			this.rowIndex = rowIndex;
			this.value = value;
		}
	}

	protected int translateColumnIndex(int col) {
		if (_identifierColumn == -1) {
			return (col + 1);
		}
		return col;
	}

	protected int translateColumnIndexBack(int col) {
		if (_identifierColumn == -1) {
			return col - 1;
		}
		return col;
	}

	public static Vector convertToVector(Object[] anArray) {
		if (anArray == null)
			return null;

		Vector v = new Vector(anArray.length);
		for (int i = 0; i < anArray.length; i++) {
			v.addElement(anArray[i]);
		}
		return v;
	}

	private void _init() {
		_filterList = new FiltersList(_model);
		_filterList.addTableModelListener(this);
	}

	private void setValueToVector(Vector vec, Object value, int index) {
		if (vec == null) {

			return;
		}

		if (vec.size() <= index) {
			vec.setSize(index + 1);
		}

		vec.setElementAt(value, index);
	}

	public int getModelIndex(int rowIndex) {
		return _filterList.getModelIndex(rowIndex);
	}

	public DefaultTableModel getRealModel() {
		return _model;
	}

	public void cleanAllData() {
		if (_identifierToRowIndex != null) {
			_identifierToRowIndex.clear();
			_identifierToRowIndex = null;
		}
		_model.setRowCount(0);
		_hiddenIdentifiers.clear();
	}

}
