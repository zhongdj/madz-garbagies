package net.madz.swing.view.util;

import java.awt.Dimension;
import java.util.Vector;

public class TablePreferenceInfo {
	private Vector<Integer> _columnIndex = null;
	private Vector<Integer> _columnWidth = null;
	private int _tableMinimumWidth = -1;
	private Vector<Integer> _shownColumnIndex = null;
	private SortedColumnPair _sortedColumnPair = null;
	private Dimension _tablePanelSize = null;

	public TablePreferenceInfo() {
	}

	public void setColumnIndex(Vector<Integer> columnIndex) {
		_columnIndex = columnIndex;
	}

	public Vector<Integer> getColumnIndex() {
		return _columnIndex;
	}

	public void setColumnWidth(Vector<Integer> columnWidth) {
		_columnWidth = columnWidth;
	}

	public Vector<Integer> getColumnWidth() {
		return _columnWidth;
	}

	public void setTableMinimumWidth(int tableMinimumWidth) {
		_tableMinimumWidth = tableMinimumWidth;
	}

	public int getTableMinimumWidth() {
		return _tableMinimumWidth;
	}

	public void setShownColumnIndex(Vector<Integer> shownColumnIndex) {
		_shownColumnIndex = shownColumnIndex;
	}

	public Vector<Integer> getShownColumnIndex() {
		return _shownColumnIndex;
	}

	public void setSortedColumnPair(SortedColumnPair sortedColumnPair) {
		_sortedColumnPair = sortedColumnPair;
	}

	public SortedColumnPair getSortedColumnPair() {
		return _sortedColumnPair;
	}

	public static class SortedColumnPair {
		private int _sortedColumnIndex = -1;
		private boolean _isSortAscending = true;

		public SortedColumnPair(int sortedColumnIndex, boolean isSortAscending) {
			_sortedColumnIndex = sortedColumnIndex;
			_isSortAscending = isSortAscending;
		}

		public int getSortedColumnIndex() {
			return _sortedColumnIndex;
		}

		public boolean getIsSortAscending() {
			return _isSortAscending;
		}
	}

	public void setTablePanelSize(Dimension tablePanelSize) {
		_tablePanelSize = tablePanelSize;
	}

	public Dimension getTablePanelSize() {
		return _tablePanelSize;
	}
}
