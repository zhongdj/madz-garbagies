package net.madz.swing.view.table;

import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class FiltersList extends AbstractTableModel implements TableModelListener {

	private TableModel _model = null;

	private Vector _filtersList = null;

	public FiltersList(TableModel model) {
		// Store data model
		_model = model;

		// Create and clear filter list object
		_filtersList = new Vector();
		_filtersList.clear();

		// Check data model
		if (_model == null) {

			return;
		}

		// Add itself as listener to the data model
		_model.addTableModelListener(this);

	}

	public void addFilter(TableFilter filter) {
		if (filter == null) {

			return;
		}

		// If we have no currently working filters
		if (_filtersList.size() == 0) {
			// filter will be applied directly to the data model
			_model.removeTableModelListener(this);
			_filtersList.add(filter);
			filter.setTableModel(_model);
		} else {
			// filter will be applied to the last filter in filter list
			getLastFilter().removeTableModelListener(this);
			filter.setTableModel(getLastFilter());
			_filtersList.add(filter);
		}

		// Anyway we should add itself as listener to the last filter
		filter.addTableModelListener(this);
	}

	public TableFilter getFilter(int column) {
		// Trying to find appropriate filter
		for (int i = 0; i < _filtersList.size(); i++) {
			TableFilter aFilter = (TableFilter) _filtersList.elementAt(i);

			if (aFilter.getColumn() == column) {
				// We've found it
				return aFilter;
			}
		}

		// Nothing found, just return null
		return null;
	}

	public void removeFilter(int column) {
		// Trying to find filter for specified column
		TableFilter aFilter = getFilter(column);

		if (aFilter == null) {
			// Nothing found, just write an error and return

			return;
		}

		TableFilter lFilter = getLastFilter();
		if (aFilter != null && aFilter.equals(lFilter)) {
			// Yes. Do that
			_filtersList.remove(lFilter);

			lFilter = getLastFilter();
			if (lFilter != null) {
				lFilter.addTableModelListener(this);
				lFilter.removeTableModelListener(aFilter);
			} else {
				_model.addTableModelListener(this);
				_model.removeTableModelListener(aFilter);
			}

			this.tableChanged(new TableModelEvent(this));
			return;
		}

		// Check whether we should remove first filter or not
		int index = _filtersList.indexOf(aFilter);
		if (index == 0) {
			// Yes. Do that
			TableFilter newFirstFilter = (TableFilter) _filtersList.elementAt(1);
			newFirstFilter.setTableModel(_model);
			_filtersList.remove(aFilter);
			this.tableChanged(new TableModelEvent(this));
			return;
		}

		// If we are here, we should remove non-first and non-last filter
		TableFilter nextFilter = (TableFilter) _filtersList.elementAt(index + 1);
		nextFilter.setTableModel((TableFilter) _filtersList.elementAt(index - 1));
		_filtersList.remove(aFilter);
		this.tableChanged(new TableModelEvent(this));
	}

	public int getFiltersCount() {
		return _filtersList.size();
	}

	public int getRowCount() {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.getRowCount() : _model.getRowCount();
	}

	public int getColumnCount() {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.getColumnCount() : _model.getColumnCount();
	}

	public String getColumnName(int columnIndex) {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.getColumnName(columnIndex) : _model.getColumnName(columnIndex);
	}

	public Class getColumnClass(int columnIndex) {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.getColumnClass(columnIndex) : _model.getColumnClass(columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.isCellEditable(rowIndex, columnIndex) : _model.isCellEditable(rowIndex, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.getValueAt(rowIndex, columnIndex) : _model.getValueAt(rowIndex, columnIndex);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		TableFilter lFilter = getLastFilter();
		if (lFilter != null) {
			lFilter.setValueAt(aValue, rowIndex, columnIndex);
		} else {
			_model.setValueAt(aValue, rowIndex, columnIndex);
		}
	}

	public TableFilter getLastFilter() {
		try {
			return (TableFilter) _filtersList.lastElement();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void tableChanged(TableModelEvent e) {
		fireTableChanged(e);
	}

	public int getModelIndex(int rowIndex) {
		TableFilter lFilter = getLastFilter();
		return (lFilter != null) ? lFilter.getModelIndex(rowIndex) : rowIndex;
	}

}
