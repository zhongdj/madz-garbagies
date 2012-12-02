package net.madz.swing.view.util;

import java.awt.Component;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.table.TableCellRenderer;

import net.madz.swing.view.table.GeneralTableModel;

public class FilterComboPopup extends BasicComboPopup implements
		ListCellRenderer {
	private JComboBox _combo = null;

	
	private DefaultListCellRenderer _defRenderer = new DefaultListCellRenderer();

	
	private Vector _currentValuesVector = null;

	
	private int _currentControlItemsSize = 0;

	
	private JPagingTable _pTable = null;

	
	private int _currentColumnIndex = -1;


	private PairComparator _pairComparator = new PairComparator();

	
	public FilterComboPopup(JComboBox combo) {
		super(combo);

		_combo = combo;
		
		_combo.setRenderer(this);
	}


	public JComboBox getComboBox() {
		return _combo;
	}

	
	public void configure(Vector controlItems, Vector items,
			JPagingTable table, int column) {
		
		_combo.removeAllItems();

		
		if (controlItems != null) {
			_currentControlItemsSize = controlItems.size();
		} else {
			
			_currentControlItemsSize = 0;
		}

		
		_pTable = table;

		
		if (_pTable == null) {

			return;
		}

	
		_currentColumnIndex = column;

		
		if (_currentColumnIndex < 0) {

			return;
		}

		
		for (int i = 0; i < _currentControlItemsSize; i++) {
			_combo.addItem(controlItems.elementAt(i));
		}

		
		_currentValuesVector = items;

		if (_currentValuesVector == null) {
			
			return;
		}

		
		if (_pTable.getSortValuesInFilteringComboBox()) {
			Collections.sort(_currentValuesVector, _pairComparator);
		}

		
		for (int i = 0; i < _currentValuesVector.size(); i++) {
			GeneralTableModel.Pair elem = (GeneralTableModel.Pair) _currentValuesVector
					.elementAt(i);
			_combo.addItem(elem.value);
		}
	}


	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		if (index < _currentControlItemsSize) {
			
			return _defRenderer.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
		}

		
		if (_currentValuesVector == null) {
			
			return _defRenderer.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
		}

		
		int rowIndex = 0;
		for (int i = 0; i < _currentValuesVector.size(); i++) {
			GeneralTableModel.Pair elem = (GeneralTableModel.Pair) _currentValuesVector
					.elementAt(i);
			Comparable v = (Comparable) elem.value;
			if (0 == v.compareTo(value)) {
				// We've found it
				rowIndex = elem.rowIndex;
				break;
			}
		}

		
		TableCellRenderer cellR = _pTable.getCellRenderer(rowIndex,
				_currentColumnIndex);

		
		return cellR.getTableCellRendererComponent(_pTable, value, isSelected,
				cellHasFocus, rowIndex, _currentColumnIndex);
	}


	public static class PairComparator implements java.util.Comparator {
		
		public int compare(Object o1, Object o2) {
			
			if (!(o1 instanceof GeneralTableModel.Pair && o2 instanceof GeneralTableModel.Pair)) {

				return 0;
			}

			if (!(((GeneralTableModel.Pair) o1).value instanceof Comparable && ((GeneralTableModel.Pair) o2).value instanceof Comparable)) {

				if (o1.equals(o2))
					return 0;
				else
					return 1;
			}

			Comparable v1 = (Comparable) ((GeneralTableModel.Pair) o1).value;
			Comparable v2 = (Comparable) ((GeneralTableModel.Pair) o2).value;

			return v1.compareTo(v2);
		}
	}
}