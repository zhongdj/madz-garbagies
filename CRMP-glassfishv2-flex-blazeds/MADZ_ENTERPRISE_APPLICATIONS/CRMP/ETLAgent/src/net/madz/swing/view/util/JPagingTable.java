package net.madz.swing.view.util;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.CellRendererPane;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.madz.swing.view.table.CellComparator;
import net.madz.swing.view.table.GeneralTableModel;
import net.madz.swing.view.table.IdentifierExistsException;
import net.madz.swing.view.table.TableFilter;

public class JPagingTable extends JTable implements ColumnHidingCofigureI,
		TablePreferenceI {
	/** Default height for last row */
	protected int _lastRowHeight = 16;

	/**
	 * Flag indicating that we should ignore reentrance of adjusting row sizes
	 * and position
	 */
	protected boolean _shouldIgnoreLastRowHeight = false;

	/** Used to fix so-called last row blinking bug */
	protected boolean _shouldIgnoreFirstRepainting = false;

	/**
	 * These two variables used to restore the height of last row when it
	 * becomes non-last for some reasons.
	 */
	protected int _lastSavedLastRowIndex = -1;
	protected int _lastSavedHeightOfLastRow = -1;

	/** Default width for last column */
	protected int _lastColumnWidth = 75;

	/**
	 * Flag indicating that we should ignore reentrance of adjusting column
	 * sizes and position
	 */
	protected boolean _shouldIgnoreLastColumnWidth = false;

	/**
	 * These two variables used to restore the width of last column when it
	 * becomes non-last for some reasons.
	 */
	protected TableColumn _lastSavedLastColumnIndex = null;
	protected int _lastSavedWidthOfLastColumn = -1;

	/** Holds current paging view position */
	protected PagingViewPosition _cPos = new PagingViewPosition(0, 0, 0, 0);

	/** Index of currently sorted column */
	protected int _sortedModelIndex = -1;

	/** Current sorted policy for currently sorted column */
	protected boolean _sortedModelIndexAscending = true;

	/**
	 * If true, filtering is enabled, so, filtering check-boxes are displayed on
	 * JTableHeader
	 */
	protected boolean _filteringEnabled = true;

	/** If true, selection is kept during sorting */
	protected boolean _keepSelection = true;

	/**
	 * Specially created flag. For case when all rows are selected and the user
	 * re-sort some column. In this case we should select all rows.
	 */
	protected boolean _shouldReSelectAllRows = false;

	/**
	 * If true, columns resizing is performed as usually depending on value of
	 * autoResizeMode.
	 * 
	 * If false, autoResizeMode is automatically set to AUTO_RESIZE_OFF and
	 * sizeColumnsToFit method does nothing
	 */
	private boolean _isDefaultResizeMode = true;

	/**
	 * Holds the real auto_resize_mode to be used when _isDefaultResizeMode is
	 * true
	 */
	private int _realAutoResizeMode = -1;

	/**
	 * If true, values in filtering combo-box is sorted before showing them
	 */
	private boolean _sortValuesInFilteringComboBox = true;

	/** Key listener used to handle all keyboard actions */
	protected KeyHandler _keyAdapter = new KeyHandler();

	/**
	 * This instance is used to display the tooltips on headers as well as on
	 * JPagingTable.
	 */
	protected ColumnHeaderToolTips _tips = new ColumnHeaderToolTips();

	/** Default save cell renderer to be used */
	protected DefaultSaveCellRenderer _defaultSaveCellRenderer = new DefaultSaveCellRenderer();

	/**
	 * A table of default objects that is used for "save" mechanism, indexed by
	 * class as declared in <code>getColumnClass</code> in the
	 * <code>TableModel</code> interface.
	 */
	transient protected HashMap _defaultSaveCellRenderers = new HashMap();

	transient protected Vector _customSaveCellRenderers = new Vector();

	transient protected Vector _notFilteredColumns = new Vector();

	transient protected HashMap _hiddenRows = new HashMap();

	protected boolean _isInsideJViewport = false;

	protected IdentifiersSelectionAdapter _selAdapter = new IdentifiersSelectionAdapter(
			this);

	protected boolean _isSortPaused = false;

	protected boolean _stopPagingPositionListener = false;

	protected boolean wheelScrollState = true;

	protected Vector<Icon> _headerIcons = null;
	protected boolean _columnHidingEnabled = false;
	protected Vector<ColumnSizeConfigure> _columnSizeConfigures = new Vector<ColumnSizeConfigure>();
	protected Vector<Integer> _unhidableColumnIndex = new Vector<Integer>();
	protected JDialog _columnHidingConfigureDialog = null;
	protected Frame _columnHidingConfigureFrame = null;
	protected boolean _calTableMinWidthByColumns = false;

	public JPagingTable() {
		super();
		_init();
	}

	public PagingViewPosition getPagingViewPosition() {
		return _cPos;
	}

	public int getColumnPageSize() {
		return _cPos.columnPageSize;
	}

	public int getRowPageSize() {
		return _cPos.rowPageSize;
	}

	public Point getPagingLocation() {
		return _cPos.getPagingLocation();
	}

	public void setPagingLocation(int currentRow, int currentColumn) {
		PagingViewPosition oldPos = _cPos.getPosition();
		if (_cPos.currentRow == currentRow
				&& _cPos.currentColumn == currentColumn) {
			return;
		}

		if (!checkModel()) {
			this.resizeAndRepaint();
			return;
		}

		if (currentRow >= 0 && currentRow <= getModel().getRowCount()
				&& currentColumn >= 0
				&& currentColumn <= getModel().getColumnCount()) {
			_cPos.currentRow = currentRow;
			_cPos.currentColumn = currentColumn;

			_stopPagingPositionListener = true;
			this.resizeAndRepaint();
			_stopPagingPositionListener = false;

		}
		firePropertyChange("pagingPosition", oldPos, _cPos);
	}

	public void movePagingLocation(int rowDelta, int columnDelta) {
		this.setPagingLocation(_cPos.currentRow + rowDelta, _cPos.currentColumn
				+ columnDelta);
	}

	public void repaint(long tm, int x, int y, int width, int height) {

		if (_shouldIgnoreFirstRepainting) {
			_shouldIgnoreFirstRepainting = false;
			return;
		}

		if (_shouldIgnoreLastRowHeight || _shouldIgnoreLastColumnWidth) {
			return;
		}

		adjustPageSizesAndLocation();
		super.repaint(tm, x, y, width, height);
		if (this.getTableHeader() != null) {
			this.getTableHeader().repaint();
		}

	}

	public int columnAtPoint(Point point) {
		int baseWidth = 0;
		for (int column = 0; column < _cPos.currentColumn; column++) {
			baseWidth += getColumnModel().getColumn(column).getWidth();
		}

		return getColumnModel().getColumnIndexAtX(baseWidth + point.x);
	}

	public int rowAtPoint(Point point) {
		int y = point.y;
		int result = y / getRowHeight() + _cPos.currentRow;

		if (result < 0) {
			return -1;
		} else if (result >= getRowCount()) {
			return -1;
		} else {
			return result;
		}
	}

	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle rBase = super.getCellRect(_cPos.currentRow,
				_cPos.currentColumn, includeSpacing);

		Rectangle r = super.getCellRect(row, column, includeSpacing);

		Rectangle toRet = new Rectangle(r.x - rBase.x, r.y - rBase.y, r.width,
				r.height);

		return toRet;

	}

	public void setModel(TableModel dataModel) {
		super.setModel(dataModel);

		int newWidth = getColumnPreferredWidthAtIndex(0);

		if (newWidth != -1) {
			this.setColumnWidthAtIndex(0, newWidth);
		}
	}

	public void tableChanged(TableModelEvent e) {

		if (_cPos == null) {
			return;
		}

		GeneralTableModel m = (GeneralTableModel) getModel();

		if (m._shouldIgnoreTableNValueChangeEvents) {
			_shouldIgnoreFirstRepainting = true;

			super.tableChanged(e);

			this.resizeAndRepaint();
			return;
		}

		if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
			_cPos.currentRow = 0;
			_cPos.currentColumn = 0;

			if (!checkModel()) {
			}

			firePropertyChange("columnCount", -1, getColumnCount());
		}

		boolean shouldFireRowCountChanged = false;

		if (e.getSource() instanceof TableFilter) {
			_cPos.currentRow = 0;
			if (checkModel()) {
			}

			shouldFireRowCountChanged = true;
		}

		if (e.getType() != TableModelEvent.UPDATE && checkModel()) {
			int rCount = getModel().getRowCount();
			if (_cPos.currentRow < rCount) {
				if (!(_cPos.currentRow + _cPos.rowPageSize < rCount)) {
					_cPos.currentRow = 0;
				}
			} else {
				_cPos.currentRow = 0;
			}
		}

		if (e.getType() == TableModelEvent.INSERT
				|| e.getType() == TableModelEvent.DELETE) {
			shouldFireRowCountChanged = true;
		}

		_shouldIgnoreFirstRepainting = true;

		super.tableChanged(e);

		this.resizeAndRepaint();

		if (shouldFireRowCountChanged) {

			firePropertyChange("rowCount", -1, getRowCount());
		}

		if (m._shouldInvokeValueChangeEvents) {
			_selAdapter.valueChanged(null);
		}
	}

	public void selectAll() {
		if (getSelectedRowCount() == getRowCount()) {
			return;
		}
		boolean isPausing = this.isPauseingSelectionChange();
		this.setPauseSelectionChange(true);
		super.selectAll();
		this.setPauseSelectionChange(isPausing);
		_selAdapter.valueChanged(null);
	}

	public boolean isIdentifierDisplayed() {
		return ((GeneralTableModel) getModel()).isIdentifierDisplayed();
	}

	public int getIdentifierColumn() {
		int modelIndex = ((GeneralTableModel) getModel()).getIdentifierColumn();
		return convertColumnIndexToView(modelIndex);
	}

	public int getRowByIdentifier(Object identifier) {
		return ((GeneralTableModel) getModel()).getRowByIdentifier(identifier);
	}

	public Object getIdentifier(int row) {
		return ((GeneralTableModel) getModel()).getIdentifier(row);
	}

	public void setIdentifier(int row, Object newIdentifier)
			throws IdentifierExistsException {
		((GeneralTableModel) getModel()).setIdentifier(row, newIdentifier);
	}

	public Vector getSelectedIdentifiers() {
		Vector toRet = new Vector();
		toRet.clear();

		int[] selectedIndicies = getSelectedRows();

		for (int i = 0; i < selectedIndicies.length; i++) {
			toRet.add(getIdentifier(selectedIndicies[i]));
		}
		return toRet;
	}

	private HashSet _getSelectedIdentifiers() {
		HashSet toRet = new HashSet();

		int[] selectedIndicies = getSelectedRows();

		for (int i = 0; i < selectedIndicies.length; i++) {
			Object id = getIdentifier(selectedIndicies[i]);
			if (id != null) {
				toRet.add(id);
			}
		}
		return toRet;
	}

	public void setSelectionByIdentifiers(Vector identifiers) {
		this.clearSelection();
		for (int i = 0; i < identifiers.size(); i++) {
			int rowIndex = getRowByIdentifier(identifiers.elementAt(i));
			this.addRowSelectionInterval(rowIndex, rowIndex);
		}
	}

	private Vector getNonSelectedIdentifiers() {
		Vector toRet = new Vector();
		toRet.clear();

		int rowCount = getRowCount();
		for (int i = 0; i < rowCount; i++) {
			if (!isCellSelected(i, 0)) {
				toRet.add(getIdentifier(i));
			}
		}

		return toRet;
	}

	private void setSelectionByNonSeslectedIdentifiers(Vector nonSelIds) {
		this.selectAll();
		for (int i = 0; i < nonSelIds.size(); i++) {
			int rowIndex = getRowByIdentifier(nonSelIds.elementAt(i));
			this.removeRowSelectionInterval(rowIndex, rowIndex);
		}
	}

	public void sortColumn(int col, boolean ascending) {
		if (_isSortPaused) {
			return;
		}

		GeneralTableModel m = (GeneralTableModel) getModel();

		if (!isSortable(col)) {
			return;
		}

		int modelIndex = convertColumnIndexToModel(col);
		int selCount = getSelectedRowCount();
		int rowCount = getRowCount();

		if (rowCount == 0) {
			// Nothing to sort
			_sortedModelIndex = modelIndex;
			_sortedModelIndexAscending = ascending;
			return;
		}

		Vector selectedIdentifiers = null;
		Vector nonSelectedIdentifiers = null;

		boolean isPausing = isPauseingSelectionChange();
		setPauseSelectionChange(true);

		if (_keepSelection) {
			if (selCount == rowCount) {
				_shouldReSelectAllRows = true;
			} else {
				if (selCount <= rowCount / 2) {
					selectedIdentifiers = getSelectedIdentifiers();
				} else {
					nonSelectedIdentifiers = getNonSelectedIdentifiers();
				}
			}
		}

		m.sortColumn(modelIndex, ascending);
		_sortedModelIndex = modelIndex;
		_sortedModelIndexAscending = ascending;

		if (_keepSelection) {
			if (_shouldReSelectAllRows) {
				selectAll();
				_shouldReSelectAllRows = false;
			} else {
				if (selectedIdentifiers != null) {
					setSelectionByIdentifiers(selectedIdentifiers);
				} else {
					setSelectionByNonSeslectedIdentifiers(nonSelectedIdentifiers);
				}
			}
		}

		if (this.getSelectedRow() != -1) {
			this.ensureCellIsVisible(this.getSelectedRow(), 0);
		}

		setPauseSelectionChange(isPausing);

		if (_keepSelection) {
			fireSelectionChanged();
		}
	}

	public boolean isSortable(int col) {
		GeneralTableModel m = (GeneralTableModel) getModel();
		return m.isSortable(convertColumnIndexToModel(col));
	}

	public int getSortedColumnIndex() {
		return convertColumnIndexToView(_sortedModelIndex);
	}

	public boolean isSortedColumnAscending() {
		return _sortedModelIndexAscending;
	}

	public void setKeepSelection(boolean keepSelection) {
		_keepSelection = keepSelection;
	}

	public boolean getKeepSelection() {
		return _keepSelection;
	}

	public boolean isWheelScrollingEnabled() {
		return wheelScrollState;
	}

	public void setWheelScrollingEnabled(boolean handleWheel) {
		wheelScrollState = handleWheel;
	}

	public CellComparator getCellComparator(int column) {
		GeneralTableModel m = (GeneralTableModel) getModel();
		return m.getCellComparator(convertColumnIndexToModel(column));
	}

	public void setCellComparatorForColumn(int columnIndex,
			CellComparator comparator) {
		GeneralTableModel m = (GeneralTableModel) getModel();
		m.setCellComparatorForColumn(convertColumnIndexToModel(columnIndex),
				comparator);
	}

	public void removeCellComparatorForColumn(int columnIndex) {
		setCellComparatorForColumn(columnIndex, null);
	}

	public void addFilter(TableFilter filter) {
		if (!isFilteringEnabled(filter.column)) {

			return;
		}

		filter.setColumn(convertColumnIndexToModel(filter.getColumn()));

		((GeneralTableModel) getModel()).addFilter(filter);

		this.resizeAndRepaint();
	}

	public TableFilter getFilter(int column) {
		if (!isFilteringEnabled(column)) {

			return null;
		}

		GeneralTableModel m = (GeneralTableModel) getModel();
		return m.getFilter(convertColumnIndexToModel(column));
	}

	public void removeFilter(int column) {
		if (!isFilteringEnabled(column)) {

			return;
		}

		GeneralTableModel m = (GeneralTableModel) getModel();
		m.removeFilter(convertColumnIndexToModel(column));
	}

	public int getFiltersCount() {
		return ((GeneralTableModel) getModel()).getFiltersCount();
	}

	public void removeAllFilters() {
		((GeneralTableModel) getModel()).removeAllFilters();
	}

	public boolean isFilteringEnabled() {
		return _filteringEnabled;
	}

	public void setFilteringEnabled(boolean enabled) {
		_filteringEnabled = enabled;

		if (!enabled) {
			this.removeAllFilters();
		}

		this.resizeAndRepaint();
	}

	public boolean isFilteringEnabled(int column) {
		if (column < 0 || column >= this.getColumnCount()) {

			return true;
		}

		if (!isFilteringEnabled()) {
			return false;
		}

		Integer colIndex = Integer.valueOf(convertColumnIndexToModel(column));

		for (int i = 0; i < _notFilteredColumns.size(); i++) {
			Integer aValue = (Integer) _notFilteredColumns.elementAt(i);
			if (colIndex.compareTo(aValue) == 0) {
				return false;
			}
		}

		return true;
	}

	public void setFilteringEnabled(int column, boolean enabled) {
		if (column < 0 || column >= this.getColumnCount()) {
			// Error case: return default value

			return;
		}

		Integer aCol = Integer.valueOf(convertColumnIndexToModel(column));

		if (this.isFilteringEnabled(column)) {
			if (!enabled) {
				if (this.getFilter(column) != null) {
					this.removeFilter(column);
				}
				_notFilteredColumns.add(aCol);
			} else {
				String mess = "Filtering is already enabled for " + column
						+ " column";

			}
		} else {
			if (enabled) {
				_notFilteredColumns.remove(aCol);
			} else {
				String mess = "Filtering is already disnabled for " + column
						+ " column";

			}
		}

		this.resizeAndRepaint();
	}

	public boolean getSortValuesInFilteringComboBox() {
		return _sortValuesInFilteringComboBox;
	}

	public void setSortValuesInFilteringComboBox(boolean enabled) {
		_sortValuesInFilteringComboBox = enabled;
	}

	public Vector getUniqueValuesForColumn(int column) {
		GeneralTableModel m = (GeneralTableModel) getModel();
		return m.getUniqueValuesForColumn(convertColumnIndexToModel(column));
	}

	public Color getFilteredArrowColor() {
		PagingHeaderRenderer r = (PagingHeaderRenderer) getTableHeader()
				.getDefaultRenderer();
		return r.getFilteredArrowColor();
	}

	public void setFilteredArrowColor(Color newColor) {
		PagingHeaderRenderer r = (PagingHeaderRenderer) getTableHeader()
				.getDefaultRenderer();
		r.setFilteredArrowColor(newColor);
	}

	public void doAutoSize(int column) {
		int rCount = getRowCount();

		Component c = null;
		int maxWidth = 0;

		for (int i = 0; i < rCount; i++) {
			TableCellRenderer r = getCellRenderer(i, column);
			c = r.getTableCellRendererComponent(this, this
					.getValueAt(i, column), this.isCellSelected(i, column),
					this.hasFocus(), i, column);

			if (c != null && c.getPreferredSize().width > maxWidth) {
				maxWidth = c.getPreferredSize().width;
			}
		}

		maxWidth += getColumnModel().getColumnMargin();

		getColumnModel().getColumn(column).setWidth(maxWidth);
		getColumnModel().getColumn(column).setPreferredWidth(maxWidth);
		if (column == getColumnCount() - 1) {
			this.setPreferredLastColumnWidth(maxWidth);
		}
	}

	public void addRowRespectSorting(Vector rowData)
			throws IdentifierExistsException {
		if (this.getFiltersCount() != 0) {
			String mess = "This method can be used when filtering is disabled or "
					+ "when there are no currently applied filters";
			throw new UnsupportedOperationException(mess);
		}

		GeneralTableModel m = (GeneralTableModel) this.getModel();
		if (this.getSortedColumnIndex() == -1) {
			// The same behavior as regular addRow() method
			m.addRow(rowData);
		} else {
			//
			// Here we shouls find appropriate index to
			// insert this row to
			m.addRow(rowData);
			this.sortColumn(this.getSortedColumnIndex(), this
					.isSortedColumnAscending());
		}
	}

	public void addRowRespectSorting(Object[] rowData)
			throws IdentifierExistsException {
		this.addRowRespectSorting(GeneralTableModel.convertToVector(rowData));
	}

	public void addRowRespectSorting(Vector rowData, Object identifier)
			throws IdentifierExistsException {
		if (this.getFiltersCount() != 0) {
			String mess = "This method can be used when filtering is disabled or "
					+ "when there are no currently applied filters";
			throw new UnsupportedOperationException(mess);
		}

		GeneralTableModel m = (GeneralTableModel) this.getModel();
		if (this.getSortedColumnIndex() == -1) {
			// The same behavior as regular addRow() method
			m.addRow(rowData, identifier);
		} else {
			//
			// Here we shouls find appropriate index to
			// insert this row to
			m.addRow(rowData, identifier);
			this.sortColumn(this.getSortedColumnIndex(), this
					.isSortedColumnAscending());
		}

	}

	public void addRowRespectSorting(Object[] rowData, Object identifier)
			throws IdentifierExistsException {
		this.addRowRespectSorting(GeneralTableModel.convertToVector(rowData),
				identifier);
	}

	public Object hideRow(int row) {
		// First cheking
		if (row < 0 || row >= this.getRowCount()) {
			// Error case: return default value

			return null;
		}

		// Get identifier for thw row to hide
		Object id = this.getIdentifier(row);

		// Get vector of the cells of this row
		int cCount = this.getColumnCount();
		Vector rowData = new Vector(cCount);
		for (int i = 0; i < cCount; i++) {
			rowData.add(this.getModel().getValueAt(row, i));
		}

		if (isIdentifierHidden(id)) {
			// Really strange : error case

		}

		// Store identifier and this vector to the internal hashmap object.
		_hiddenRows.put(id, rowData);

		GeneralTableModel m = (GeneralTableModel) getModel();

		m.addHiddenIdentifier(id);

		m.removeRow(row);

		return id;
	}

	public boolean isIdentifierHidden(Object identifier) {
		GeneralTableModel m = (GeneralTableModel) getModel();
		return m.isIdentifierHidden(identifier);
	}

	public void showHiddenRow(Object identifier) {
		// Fitst checking
		if (identifier == null) {

			return;
		}

		// Check that there is hidden row with this identifier
		if (!isIdentifierHidden(identifier)) {

			return;
		}

		// Get hidden row data
		Vector rowData = getVectorOfHiddenRow(identifier);
		_hiddenRows.remove(identifier);

		GeneralTableModel m = (GeneralTableModel) getModel();
		m.removeHiddenIdentifier(identifier);

		try {
			if (isIdentifierDisplayed()) {
				this.addRowRespectSorting(rowData);
			} else {
				this.addRowRespectSorting(rowData, identifier);
			}
		} catch (IdentifierExistsException ex) {

		}
	}

	public void showAllHiddenRows() {
		if (_hiddenRows.isEmpty()) {
			// Nothing to show
			return;
		}

		Set s = _hiddenRows.keySet();
		Object[] ids = s.toArray();

		boolean isPaused = setPauseSorting(true);
		for (int i = 0; i < ids.length; i++) {
			this.showHiddenRow(ids[i]);
		}
		setPauseSorting(isPaused);
		if (getSortedColumnIndex() != -1) {
			sortColumn(getSortedColumnIndex(), isSortedColumnAscending());
		}
	}

	public Set getAllHiddenIdentifiers() {
		return _hiddenRows.keySet();
	}

	public Vector getRowAsVectorByIdentifier(Object identifier) {
		// First stupid check
		if (identifier == null) {

			return null;
		}

		int cCount = this.getColumnCount();
		Vector toRet = new Vector(cCount);

		//
		// Firstly, try to get it from real model and remove it
		GeneralTableModel m = (GeneralTableModel) getModel();

		int row = m.getIndexInRealModel(identifier);
		if (row != -1) {
			// Get column index which holds identifiers
			int idColumn = m.getIdentifierColumn();

			int startIndex = (idColumn == -1 ? 1 : 0);
			cCount += startIndex;

			// Get real data model
			DefaultTableModel rM = m.getRealModel();
			for (int j = startIndex; j < cCount; j++) {
				toRet.add(rM.getValueAt(row, j)); // CR Q01104642, HeShan
													// 2005.03.25
			}
			return toRet;
		}

		//
		// If we are here, it means that row
		// isn't displayed, so, it must be hidden
		// Try to find it as hidden one
		if (isIdentifierHidden(identifier)) {
			return getVectorOfHiddenRow(identifier);
		}

		// There is no such row in the data model :-(
		return null;
	}

	private Vector getVectorOfHiddenRow(Object identifier) {
		Object toRet = null;
		toRet = _hiddenRows.get(identifier);
		if (toRet instanceof Vector) {
			return (Vector) toRet;
		}

		return null;
	}

	private void setNewDataForHiddenRow(Object identifier, Vector newRowData) {
		if (_hiddenRows.put(identifier, newRowData) == null) {
			_hiddenRows.remove(identifier);

		}
	}

	public void removeRowByIdentifier(Object identifier) {
		// First stupid checks
		if (identifier == null) {

			return;
		}

		GeneralTableModel m = (GeneralTableModel) getModel();

		int row = m.getIndexInRealModel(identifier);
		if (row != -1) {
			m._identifierToRowIndex = null;
			m.getRealModel().removeRow(row);
			return;
		}

		if (isIdentifierHidden(identifier)) {
			_hiddenRows.remove(identifier);
			m.removeHiddenIdentifier(identifier);
			return;
		}

	}

	public void updateRowData(Object identifier, Vector newData) {
		// First stupid checks
		if (identifier == null) {

			return;
		}
		if (newData == null) {

			return;
		}

		// Second one ... not so stupid :-)
		if (newData.size() != this.getColumnCount()) {

			return;
		}

		//
		// Check that everything is OK with data
		// from identifiers point of view
		GeneralTableModel m = (GeneralTableModel) getModel();

		if (this.isIdentifierDisplayed()) {
			int idColumn = this.getIdentifierColumn();
			Object o = newData.elementAt(idColumn);
			if (!identifier.equals(o)) {

				return;
			}
		}

		int row = getRowByIdentifier(identifier);
		if (row != -1) {
			// _sortedModelIndex = -1;
			int cCount = this.getColumnCount();
			int sortIndex = this.getSortedColumnIndex();
			boolean shouldSort = false;
			for (int i = 0; i < cCount; i++) {
				if (i == sortIndex
						&& !m.getValueAt(row, i).equals(newData.elementAt(i)))
					shouldSort = true;
				m.setValueAt(newData.elementAt(i), row, i);
			}
			if (shouldSort)
				this.sortColumn(sortIndex, this.isSortedColumnAscending());
			return;
		}

		if (isIdentifierHidden(identifier)) {
			setNewDataForHiddenRow(identifier, newData);
			return;
		}

		if (getFiltersCount() != 0) {
			row = m.getIndexInRealModel(identifier);
			if (row != -1) {
				// Get column index which holds identifiers
				int idColumn = m.getIdentifierColumn();

				int startIndex = (idColumn == -1 ? 1 : 0);
				int cCount = this.getColumnCount() + startIndex;

				// Get real data model
				DefaultTableModel rM = m.getRealModel();
				int vecIndex = 0;
				int sortIndex = this.getSortedColumnIndex() + startIndex;
				boolean shouldSort = false;
				for (int j = startIndex; j < cCount; j++) {
					if (j == sortIndex
							&& !rM.getValueAt(row, j).equals(
									newData.elementAt(vecIndex)))
						shouldSort = true;
					rM.setValueAt(newData.elementAt(vecIndex), row, j);
					vecIndex++;
				}
				if (shouldSort)
					this.sortColumn(sortIndex - startIndex, this
							.isSortedColumnAscending());
				return;
			}
		}

	}

	public boolean isIdentifierInRealModel(Object identifier) {
		GeneralTableModel m = (GeneralTableModel) getModel();
		return m.isIdentifierInRealModel(identifier);

	}

	public void addRowAndHideIt(Vector rowData)
			throws IdentifierExistsException {
		if (!this.isIdentifierDisplayed()) {
			// We are constructed with NON-displayed identifier
			String mess = "This object constructed with NON-displayed identifier."
					+ "Use (Vector rowData, Object identifier) method instead";
			throw new UnsupportedOperationException(mess);
		}

		GeneralTableModel m = (GeneralTableModel) getModel();
		Object identifier = rowData.elementAt(m.getIdentifierColumn());

		if (m.isIdentifierInRealModel(identifier)) {
			throw new IdentifierExistsException(identifier);
		}

		_hiddenRows.put(identifier, rowData);
		m.addHiddenIdentifier(identifier);
	}

	public void addRowAndHideIt(Object[] rowData)
			throws IdentifierExistsException {
		this.addRowAndHideIt(GeneralTableModel.convertToVector(rowData));
	}

	public void addRowAndHideIt(Vector rowData, Object identifier)
			throws IdentifierExistsException {
		if (this.isIdentifierDisplayed()) {
			// We are constructed with displayed identifier
			String mess = "This object constructed with displayed identifier."
					+ "Use (Vector rowData) method instead";
			throw new UnsupportedOperationException(mess);
		}

		GeneralTableModel m = (GeneralTableModel) getModel();

		if (m.isIdentifierInRealModel(identifier)) {
			throw new IdentifierExistsException(identifier);
		}

		_hiddenRows.put(identifier, rowData);
		m.addHiddenIdentifier(identifier);
	}

	public void addRowAndHideIt(Object[] rowData, Object identifier)
			throws IdentifierExistsException {
		this.addRowAndHideIt(GeneralTableModel.convertToVector(rowData),
				identifier);
	}

	public void setColumnWidthAtIndex(int index, int width) {
		TableColumn aCol = null;
		if (this.getColumnModel() != null) {
			if (index < this.getColumnModel().getColumnCount()) {
				aCol = this.getColumnModel().getColumn(index);
			}
		}

		if (aCol != null) {
			aCol.setWidth(width);
		}
	}

	public int getColumnWidthAtIndex(int index) {
		TableColumn aCol = null;
		if (this.getColumnModel() != null) {
			if (index < this.getColumnModel().getColumnCount()) {
				aCol = this.getColumnModel().getColumn(index);
			}
		}

		if (aCol != null) {
			return aCol.getWidth();
		}

		return -1;
	}

	public int getColumnPreferredWidthAtIndex(int index) {
		TableColumn aCol = null;
		if (this.getColumnModel() != null) {
			if (index < this.getColumnModel().getColumnCount()) {
				aCol = this.getColumnModel().getColumn(index);
			}
		}

		if (aCol != null) {
			return aCol.getPreferredWidth();
		}

		return -1;
	}

	public boolean isEmpty() {
		GeneralTableModel m = (GeneralTableModel) getModel();

		if (m == null || m.getRowCount() == 0) {
			return true;
		}

		return false;
	}

	public boolean isSelected() {
		return getSelectedRow() != -1;
	}

	public void setSelectionAllowed(boolean selectionRow,
			boolean selectionColumn) {
		this.setRowSelectionAllowed(selectionRow);
		this.setColumnSelectionAllowed(selectionColumn);
	}

	public void setCellRendererByColumn(int index,
			TableCellRenderer tableCellRenderer) {
		TableColumn aCol = null;
		if (this.getColumnModel() != null) {
			aCol = this.getColumnModel().getColumn(index);
		}

		if (aCol != null) {
			aCol.setCellRenderer(tableCellRenderer);
		}
	}

	public void ensureCellIsVisible(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex > getRowCount() || columnIndex < 0
				|| columnIndex > getColumnCount()) {

			return;
		}

		int r = rowIndex;
		int c = columnIndex;
		Point newLoc = new Point(r, c);

		if (r < _cPos.currentRow
				|| r >= _cPos.currentRow + _cPos.rowPageSize - 1
				|| c < _cPos.currentColumn
				|| c >= _cPos.currentColumn + _cPos.columnPageSize - 1) {
			if (r == _cPos.currentRow + _cPos.rowPageSize - 1) {
				newLoc.x = _cPos.currentRow + 1;
			}
			if (c == _cPos.currentColumn + _cPos.columnPageSize - 1) {
				newLoc.y = _cPos.currentColumn + 1;
			}

			if (_cPos.currentRow + _cPos.rowPageSize == getRowCount()
					&& r == _cPos.currentRow - 1) {
				newLoc.x = r - 2;
			}

			if (_cPos.currentColumn + _cPos.columnPageSize == getColumnCount()
					&& c == _cPos.currentColumn - 1) {
				newLoc.y = c - 2;
			}

			this.setPagingLocation(newLoc.x, newLoc.y);
		}
	}

	public void setTooltipHeaders(String[] tooltipHeaders) {
		if (tooltipHeaders == null) {

			return;
		}

		for (int i = 0; i < tooltipHeaders.length; i++) {
			TableColumn c = getColumnModel().getColumn(i);
			_tips.setToolTip(c, tooltipHeaders[i]);
		}
	}

	public void removeHeaderTooltips() {
		_tips.removeAllTooltips();
	}

	public void setTooltipHeader(int columnIndex, String tooltipHeader) {
		TableColumn c = getColumnModel().getColumn(columnIndex);
		_tips.setToolTip(c, tooltipHeader);
	}

	public void removeHeaderTooltip(int columnIndex) {
		TableColumn c = getColumnModel().getColumn(columnIndex);
		_tips.setToolTip(c, null);
	}

	public void addTableMouseListener(MouseListener l) {
		addMouseListener(l);
	}

	public void removeTableMouseListener(MouseListener l) {
		removeMouseListener(l);
	}

	public void addTableKeyListener(KeyListener l) {
		addKeyListener(l);
	}

	public void removeTableKeyListener(KeyListener l) {
		removeKeyListener(l);
	}

	public void setEditingAllowed(boolean allowed) {
		((GeneralTableModel) getModel()).setEditingAllowed(allowed);
	}

	public boolean isEditingAllowed() {
		return ((GeneralTableModel) getModel()).isEditingAllowed();
	}

	public void setDefaultSaveRenderer(Class columnClass,
			SaveCellRenderer renderer) {
		if (renderer != null) {
			_defaultSaveCellRenderers.put(columnClass, renderer);
		} else {
			_defaultSaveCellRenderers.remove(columnClass);
		}
	}

	public SaveCellRenderer getDefaultSaveRenderer(Class columnClass) {
		if (columnClass == null) {
			return null;
		} else {
			Object renderer = _defaultSaveCellRenderers.get(columnClass);
			if (renderer != null) {
				return (SaveCellRenderer) renderer;
			} else {
				return getDefaultSaveRenderer(columnClass.getSuperclass());
			}
		}
	}

	public SaveCellRenderer getSaveCellRenderer(int row, int column) {
		if (row > getRowCount() || column > getColumnCount()) {

			return null;
		}

		SaveCellRenderer r = null;

		try {
			r = (SaveCellRenderer) _customSaveCellRenderers.elementAt(column);
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Nothing to do.
		}

		if (r == null) {
			Class colC = getModel().getColumnClass(column);
			r = getDefaultSaveRenderer(colC);
		}

		return r;
	}

	public void setSaveCellRendererForColumn(int columnIndex,
			SaveCellRenderer renderer) {
		if (columnIndex < 0) {

		}

		setValueToVector(_customSaveCellRenderers, renderer, columnIndex);
	}

	public String getCellAsString(int rowIndex, int columnIndex) {
		if (rowIndex > getRowCount() || columnIndex > getColumnCount()) {

			return null;
		}

		SaveCellRenderer sr = getSaveCellRenderer(rowIndex, columnIndex);

		if (sr == null) {

			return null;
		}

		return sr.getObjectAsString(getModel()
				.getValueAt(rowIndex, columnIndex), rowIndex, columnIndex);
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

	public boolean isDefaultResizeMode() {
		return _isDefaultResizeMode;
	}

	public void adjustDefaultResizeMode() {
		int totalColumnWidth = getColumnModel().getTotalColumnWidth();
		int parentWidth = getParent().getSize().width;

		if (_realAutoResizeMode == -1) {
			_realAutoResizeMode = this.getAutoResizeMode();
		}

		if (_isInsideJViewport) {
			_isDefaultResizeMode = true;
			_cPos.currentColumn = 0;
			_cPos.columnPageSize = getColumnCount();
			autoResizeMode = _realAutoResizeMode;
			return;
		}

		if (totalColumnWidth > parentWidth) {
			_isDefaultResizeMode = false;
			autoResizeMode = AUTO_RESIZE_OFF;
		} else {
			_isDefaultResizeMode = true;
			_cPos.currentColumn = 0;
			_cPos.columnPageSize = getColumnCount();
			autoResizeMode = _realAutoResizeMode;
		}
	}

	public void sizeColumnsToFit(int resizingColumn) {
		if (_isDefaultResizeMode) {
			super.sizeColumnsToFit(resizingColumn);
		}
	}

	public void doLayout() {
		if (_isDefaultResizeMode) {
			super.doLayout();
		}
	}

	public void setTableHeader(JTableHeader tableHeader) {
		JTableHeader oldHeader = getTableHeader();
		if (oldHeader != null) {
			oldHeader.removeMouseMotionListener(_tips);
		}

		super.setTableHeader(tableHeader);

		if (tableHeader != null) {
			tableHeader.addMouseMotionListener(_tips);
		}
	}

	protected Rectangle getTableBounds() {
		Rectangle clip = null;
		if (this.getParent() == null) {
			return null;
		}

		clip = this.getParent().getBounds();

		if (_isInsideJViewport) {
			Container p1 = this.getParent().getParent();
			Container p2 = null;
			Container p3 = null;
			Container p4 = null;

			if (p1 != null && p1 instanceof JPanel) {
				p2 = p1.getParent();
				if (p2 != null && p2 instanceof JViewport) {
					JViewport vP = (JViewport) p2;
					Rectangle clip2 = vP.getViewRect();

					p3 = vP.getParent();
					if (p3 != null && p3 instanceof JScrollPane) {
						p4 = p3.getParent();

						if (p4 != null && p4 instanceof JPagingTablePanel) {
							JPagingTablePanel pP = (JPagingTablePanel) p4;
							int delta = 0;
							if (pP._header != null) {
								delta = pP._header.getHeight();
							}
							clip2.height -= delta;
							clip.x = clip2.x;
							clip.height = clip2.height;
						}
					}
				}
			}
		}
		return clip;
	}

	public void adjustPageSizesAndLocation() {
		if (_cPos == null) {
			return;
		}

		PagingViewPosition oldPos = _cPos.getPosition();

		Rectangle clip = getTableBounds();

		if (clip == null) {
			return;
		}

		this.adjustRowsPageSizesAndLocation(clip);

		adjustDefaultResizeMode();
		if (!isDefaultResizeMode()) {
			this.adjustColumnsPageSizesAndLocation(clip);
		}

		if (!_stopPagingPositionListener)

			firePropertyChange("pagingPosition", oldPos, _cPos);
	}

	public void setPreferredLastColumnWidth(int newWidth) {
		TableColumn col = getColumnModel().getColumn(getColumnCount() - 1);

		int old = _lastColumnWidth;
		_lastColumnWidth = Math.min(Math.max(newWidth, col.getMinWidth()), col
				.getMaxWidth());
		firePropertyChange("preferredlastcolumnwidth", old, _lastColumnWidth);
		resizeAndRepaint();
	}

	public int getPreferredLastColumnWidth() {
		return _lastColumnWidth;
	}

	public void setPreferredLastRowHeight(int newHeight) {
		if (newHeight <= 0) {
			throw new IllegalArgumentException("New row height less than 1");
		}

		int old = this._lastRowHeight;
		this._lastRowHeight = newHeight;
		firePropertyChange("preferredlastrowheight", old, this._lastRowHeight);
		this.resizeAndRepaint();
	}

	public int getPreferredLastRowHeight() {
		return _lastRowHeight;
	}

	protected class KeyHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			Point anchorCell = new Point(selectionModel
					.getAnchorSelectionIndex(), columnModel.getSelectionModel()
					.getAnchorSelectionIndex());

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (anchorCell.x == _cPos.currentRow && _cPos.currentRow != 0) {
					if (_cPos.currentRow + _cPos.rowPageSize == getRowCount()) {
						movePagingLocation(-2, 0);
					} else {
						movePagingLocation(-1, 0);
					}
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

				if ((anchorCell.x == _cPos.currentRow + _cPos.rowPageSize - 1 || anchorCell.x == _cPos.currentRow
						+ _cPos.rowPageSize - 2)
						&& _cPos.currentRow + _cPos.rowPageSize != getRowCount()) {
					int totalHeight = JPagingTable.this.getTableBounds().height;
					int rowHeight = JPagingTable.this.getRowHeight(0);
					int pageRowHeight = rowHeight * _cPos.rowPageSize;
					boolean hideLastRow = (totalHeight < pageRowHeight
							- rowHeight * 0.2);
					if (hideLastRow
							&& anchorCell.x == _cPos.currentRow
									+ _cPos.rowPageSize - 1) {
						movePagingLocation(2, 0);
					} else if (anchorCell.x == _cPos.currentRow
							+ _cPos.rowPageSize - 1
							|| hideLastRow) {
						movePagingLocation(1, 0);
					}
				}

			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (anchorCell.y == _cPos.currentColumn
						&& _cPos.currentColumn != 0) {
					if (_cPos.currentColumn + _cPos.columnPageSize == getColumnCount()) {
						movePagingLocation(0, -2);
					} else {
						movePagingLocation(0, -1);
					}
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (anchorCell.y == _cPos.currentColumn + _cPos.columnPageSize
						- 1
						&& _cPos.currentColumn != getColumnCount() - 1) {
					movePagingLocation(0, 1);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
				if (_cPos.currentRow < _cPos.rowPageSize) {
					movePagingLocation(-_cPos.currentRow, 0);
				} else {
					movePagingLocation(-_cPos.rowPageSize, 0);
				}

			} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
				movePagingLocation(getRowPageSize(), 0);
			} else if (e.getKeyCode() == KeyEvent.VK_HOME) {
				setPagingLocation(0, _cPos.currentColumn);
			} else if (e.getKeyCode() == KeyEvent.VK_END) {
				setPagingLocation(getRowCount() - 1, _cPos.currentColumn);
			}
		}
		
		
	}

	public static class ColumnHeaderToolTips extends MouseMotionAdapter {

		protected TableColumn _curCol = null;

		protected Map _tips = new HashMap();

		public void setToolTip(TableColumn col, String tooltip) {
			if (tooltip == null) {
				_tips.remove(col);
			} else {
				_tips.put(col, tooltip);
			}
		}

		public void removeAllTooltips() {
			_curCol = null;
			_tips.clear();
		}

		public void mouseMoved(MouseEvent evt) {
			TableColumn col = null;
			JTableHeader header = (JTableHeader) evt.getSource();
			JTable table = header.getTable();
			TableColumnModel colModel = table.getColumnModel();
			int vColIndex = colModel.getColumnIndexAtX(evt.getX());

			if (vColIndex >= 0) {
				col = colModel.getColumn(vColIndex);
			}

			if (col != null && !col.equals(_curCol)) {
				header.setToolTipText((String) _tips.get(col));
				_curCol = col;
			}
		}
	}

	private void _init() {
		this.addKeyListener(_keyAdapter);
		_defaultSaveCellRenderers.put(Object.class, _defaultSaveCellRenderer);
		this.getSelectionModel().addListSelectionListener(_selAdapter);
	}

	private boolean checkModel() {
		if (getModel() == null) {
			_cPos.currentRow = 0;
			_cPos.currentColumn = 0;
			_cPos.rowPageSize = 0;
			_cPos.columnPageSize = 0;
			return false;
		}
		if (_cPos.rowPageSize >= getModel().getRowCount()) {
			_cPos.rowPageSize = getModel().getRowCount();
		}

		if (_cPos.columnPageSize >= getModel().getColumnCount()) {
			_cPos.columnPageSize = getModel().getColumnCount();
		}

		return true;
	}

	private void adjustRowsPageSizesAndLocation(Rectangle clip) {
		int totalHeight = clip.height;

		int curHeight = 0;
		int rCount = getRowCount();

		_cPos.showScrollBar = false;

		if (rCount <= 0) {
			return;
		}

		for (int i = _cPos.currentRow; i < rCount; i++) {
			if (curHeight > totalHeight) {
				_cPos.rowPageSize = i - _cPos.currentRow;
				resetLastRowHeightSettings();
				return;
			}
			curHeight += getRowHeight(i);
		}

		boolean b = false;
		curHeight = 0;

		for (int i = 0; i < rCount; i++) {
			if (curHeight > totalHeight) {
				b = true;
				break;
			}

			curHeight += getRowHeight(0);
		}
		if (totalHeight > 0 && rCount > 1 && !b && curHeight > totalHeight) {
			_cPos.showScrollBar = true;

			if (_cPos.currentRow != 1) {
				_cPos.currentRow = 0;
				_cPos.rowPageSize = rCount;
				resetLastRowHeightSettings();
			} else {

				_cPos.rowPageSize = rCount - 1;

				_shouldIgnoreLastRowHeight = true;
				if (_lastSavedLastRowIndex == -1) {
					_lastSavedLastRowIndex = getRowCount() - 1;
					_lastSavedHeightOfLastRow = getRowHeight(_lastSavedLastRowIndex);
				}

				checkAndSetRowHeight(getRowCount() - 1, totalHeight
						- (curHeight - getRowHeight(0)) + _lastRowHeight);
				_shouldIgnoreLastRowHeight = false;
			}
			return;
		}

		curHeight = _lastRowHeight;
		int lHeight = 0;
		for (int i = rCount - 2; i >= 0; i--) {
			if (curHeight > totalHeight) {
				_cPos.currentRow = i + 2;
				_cPos.rowPageSize = rCount - _cPos.currentRow;

				int partHeight = 0;
				for (int jj = _cPos.currentRow; jj < rCount - 1; jj++) {
					partHeight += getRowHeight(jj);
				}
				partHeight += _lastRowHeight;

				_shouldIgnoreLastRowHeight = true;

				if (_lastSavedLastRowIndex != -1
						&& _lastSavedLastRowIndex != getRowCount() - 1) {
					int delta = getRowHeight(getRowCount() - 1)
							- _lastSavedHeightOfLastRow;

					if (delta >= 0) {
						checkAndSetRowHeight(_lastSavedLastRowIndex,
								_lastSavedHeightOfLastRow);

						checkAndSetRowHeight(getRowCount() - 1, totalHeight
								- partHeight + _lastRowHeight + delta);

						_lastSavedLastRowIndex = getRowCount() - 1;
						_lastSavedHeightOfLastRow = getRowHeight(_lastSavedLastRowIndex);
					}
				} else {
					if (_lastSavedLastRowIndex == -1) {
						_lastSavedLastRowIndex = getRowCount() - 1;
						_lastSavedHeightOfLastRow = getRowHeight(_lastSavedLastRowIndex);
					}

					checkAndSetRowHeight(getRowCount() - 1, totalHeight
							- partHeight + _lastRowHeight);
				}

				_shouldIgnoreLastRowHeight = false;
				return;
			}

			lHeight = getRowHeight(i);
			curHeight += lHeight;
		}

		_cPos.currentRow = 0;
		_cPos.rowPageSize = rCount - _cPos.currentRow;
		resetLastRowHeightSettings();
	}

	private void resetLastRowHeightSettings() {
		if (_lastSavedLastRowIndex == -1 || _lastSavedHeightOfLastRow == -1) {
			return;
		}

		_shouldIgnoreLastRowHeight = true;
		checkAndSetRowHeight(_lastSavedLastRowIndex, _lastSavedHeightOfLastRow);
		_lastSavedLastRowIndex = _lastSavedHeightOfLastRow = -1;
		_shouldIgnoreLastRowHeight = false;
	}

	private void resetLastColumnWidthSettings() {
		if (_lastSavedLastColumnIndex == null
				|| _lastSavedWidthOfLastColumn == -1) {
			return;
		}
		_shouldIgnoreLastColumnWidth = true;
		_lastSavedLastColumnIndex.setWidth(_lastSavedWidthOfLastColumn);
		_lastSavedLastColumnIndex = null;
		_lastSavedWidthOfLastColumn = -1;
		_shouldIgnoreLastColumnWidth = false;
	}

	private void adjustColumnsPageSizesAndLocation(Rectangle clip) {
		int totalWidth = clip.width;

		int curWidth = 0;
		int cCount = getColumnCount();

		if (cCount <= 0) {
			return;
		}

		for (int i = _cPos.currentColumn; i < cCount; i++) {
			if (curWidth > totalWidth) {
				_cPos.columnPageSize = i - _cPos.currentColumn;
				resetLastColumnWidthSettings();
				return;
			}
			curWidth += getColumnModel().getColumn(i).getWidth();
		}

		curWidth = _lastColumnWidth;
		int lWidth = 0;
		for (int i = cCount - 2; i >= 0; i--) {
			if (curWidth > totalWidth) {
				_cPos.currentColumn = i + 2;
				_cPos.columnPageSize = cCount - _cPos.currentColumn;

				int partWidth = 0;
				for (int jj = _cPos.currentColumn; jj < cCount - 1; jj++) {
					partWidth += getColumnModel().getColumn(jj).getWidth();
				}
				partWidth += _lastColumnWidth;

				TableColumn lastColumn = getColumnModel().getColumn(cCount - 1);

				_shouldIgnoreLastColumnWidth = true;

				if (_lastSavedLastColumnIndex != null
						&& !_lastSavedLastColumnIndex.equals(lastColumn)) {
					int delta = lastColumn.getWidth()
							- _lastSavedWidthOfLastColumn;

					if (delta >= 0) {
						_lastSavedLastColumnIndex
								.setWidth(_lastSavedWidthOfLastColumn);

						int newV = totalWidth - partWidth + _lastColumnWidth
								+ delta;
						lastColumn.setWidth(newV);

						_lastSavedLastColumnIndex = lastColumn;
						_lastSavedWidthOfLastColumn = _lastSavedLastColumnIndex
								.getWidth();
					}
				} else {
					if (_lastSavedLastColumnIndex == null) {
						_lastSavedLastColumnIndex = lastColumn;
						_lastSavedWidthOfLastColumn = _lastSavedLastColumnIndex
								.getWidth();
					}

					lastColumn.setWidth(totalWidth - partWidth
							+ _lastColumnWidth);
				}

				_shouldIgnoreLastColumnWidth = false;
				return;
			}

			lWidth = getColumnModel().getColumn(i).getWidth();
			curWidth += lWidth;
		}

		_cPos.currentColumn = 0;
		_cPos.columnPageSize = cCount - _cPos.currentColumn;
		resetLastColumnWidthSettings();
	}

	public void printAsPaint(Graphics g) {
		if (this.getRowCount() <= 0 || this.getColumnCount() <= 0) {
			return;
		}

		int rMin = 0;
		int rMax = this.getRowCount() - 1;
		int cMin = 0;
		int cMax = this.getColumnCount() - 1;

		g.setColor(this.getGridColor());

		Rectangle minCell = super.getCellRect(rMin, cMin, true);
		Rectangle maxCell = super.getCellRect(rMax, cMax, true);

		if (this.getShowHorizontalLines()) {
			int tableWidth = maxCell.x + maxCell.width;
			int y = minCell.y;
			for (int row = rMin; row <= rMax; row++) {
				y += this.getRowHeight(row);
				g.drawLine(0, y - 1, tableWidth - 1, y - 1);
			}
		}

		if (this.getShowVerticalLines()) {
			TableColumnModel cm = this.getColumnModel();
			int tableHeight = maxCell.y + maxCell.height;
			int x = minCell.x;
			for (int column = cMin; column <= cMax; column++) {
				x += cm.getColumn(column).getWidth();
				g.drawLine(x - 1, 0, x - 1, tableHeight - 1);
			}
		}

		TableColumnModel cm = this.getColumnModel();
		int columnMargin = cm.getColumnMargin();

		for (int row = rMin; row <= rMax; row++) {
			Rectangle cellRect = super.getCellRect(row, cMin, false);
			for (int column = cMin; column <= cMax; column++) {
				TableColumn aColumn = cm.getColumn(column);
				int columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth - columnMargin;
				paintCell(g, cellRect, row, column);
				cellRect.x += columnWidth;
			}
		}
	}

	public void cleanAllData() {
		GeneralTableModel m = (GeneralTableModel) getModel();
		_hiddenRows.clear();
		m.cleanAllData();
	}

	private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
		if (this.isEditing() && this.getEditingRow() == row
				&& this.getEditingColumn() == column) {
			Component component = this.getEditorComponent();
			component.setBounds(cellRect);
			component.validate();
		} else {
			CellRendererPane rendererPane = new CellRendererPane();
			TableCellRenderer renderer = this.getCellRenderer(row, column);
			Component component = this.prepareRenderer(renderer, row, column);
			rendererPane.paintComponent(g, component, this, cellRect.x,
					cellRect.y, cellRect.width, cellRect.height, true);
		}
	}

	private void checkAndSetRowHeight(int row, int rowHeight) {
		if (getRowHeight(row) != rowHeight) {
			this.setRowHeight(row, rowHeight);
		}
	}

	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}

	public void addIdentifiersSelectionListener(IdentifiersSelectionListener l) {
		_selAdapter.addIdentifiersSelectionListener(l);
	}

	public void removeIdentifiersSelectionListener(
			IdentifiersSelectionListener l) {
		_selAdapter.removeIdentifiersSelectionListener(l);
	}

	public class IdentifiersSelectionAdapter implements ListSelectionListener {

		protected JPagingTable _pTable = null;

		protected Vector idSellistenerList = new Vector();

		protected HashSet oldSelection = new HashSet();

		protected boolean _isPaused = false;

		public IdentifiersSelectionAdapter(JPagingTable pTable) {
			this._pTable = pTable;
		}

		public void pauseProcessing() {
			_isPaused = true;
		}

		public void continueProcessing() {
			_isPaused = false;
		}

		public void valueChanged(ListSelectionEvent e) {
			if (_isPaused) {

				return;
			}

			GeneralTableModel m = (GeneralTableModel) getModel();
			if (m._shouldIgnoreTableNValueChangeEvents) {
				return;
			}

			HashSet newSelection = _pTable._getSelectedIdentifiers();

			if (!compareSelections(oldSelection, newSelection)) {
				fireValueChanged(new Vector(oldSelection), new Vector(
						newSelection));
				oldSelection = newSelection;
			}
		}

		public void addIdentifiersSelectionListener(
				IdentifiersSelectionListener l) {
			idSellistenerList.add(l);
		}

		public void removeIdentifiersSelectionListener(
				IdentifiersSelectionListener l) {
			idSellistenerList.remove(l);
		}

		protected boolean compareSelections(HashSet oldSelection,
				HashSet newSelection) {
			if (oldSelection == null && newSelection == null) {
				return true;
			}

			if (oldSelection == null && newSelection != null) {
				return false;
			}

			if (oldSelection != null && newSelection == null) {
				return false;
			}

			if (oldSelection.size() != newSelection.size()) {
				return false;
			}

			for (Iterator i = newSelection.iterator(); i.hasNext();) {
				Object id = i.next();
				if (!oldSelection.contains(id)) {
					return false;
				}
			}

			return true;
		}

		protected void fireValueChanged(Vector oldSelection, Vector newSelection) {

			IdentifiersSelectionEvent e = new IdentifiersSelectionEvent(
					_pTable, oldSelection, newSelection);

			for (int i = 0; i < idSellistenerList.size(); i++) {
				IdentifiersSelectionListener l = (IdentifiersSelectionListener) idSellistenerList
						.elementAt(i);

				l.valueChanged(e);
			}
		}

	}

	public boolean isPauseingSelectionChange() {
		return _selAdapter._isPaused;
	}

	public void setPauseSelectionChange(boolean isPause) {
		if (isPause) {
			_selAdapter.pauseProcessing();
		} else {
			_selAdapter.continueProcessing();
		}
	}

	public void fireSelectionChanged() {
		_selAdapter.valueChanged(null);
	}

	public boolean setPauseSorting(boolean isSortPause) {
		boolean oldSortPause = _isSortPaused;
		_isSortPaused = isSortPause;
		return oldSortPause;
	}

	public void batchAddRowsRespectSorting(Vector rowsData, Vector identifiers) {
		if (this.getFiltersCount() != 0) {
			String mess = "This method can be used when filtering is disabled or "
					+ "when there are no currently applied filters";
			throw new UnsupportedOperationException(mess);
		}

		if (rowsData.size() != identifiers.size()) {

			return;
		}

		GeneralTableModel m = (GeneralTableModel) this.getModel();
		for (int i = 0; i < identifiers.size(); i++) {
			Object identifier = identifiers.elementAt(i);
			Vector rowData = (Vector) rowsData.elementAt(i);
			try {
				m.addRow(rowData, identifier);
			} catch (IdentifierExistsException e) {

			}
		}
		if (this.getSortedColumnIndex() != -1) {
			this.sortColumn(this.getSortedColumnIndex(), this
					.isSortedColumnAscending());
		}
	}

	public void batchAddRowsRespectSorting(Vector rowsData) {
		if (this.getFiltersCount() != 0) {
			String mess = "This method can be used when filtering is disabled or "
					+ "when there are no currently applied filters";
			throw new UnsupportedOperationException(mess);
		}

		GeneralTableModel m = (GeneralTableModel) this.getModel();
		for (int i = 0; i < rowsData.size(); i++) {
			Vector rowData = (Vector) rowsData.elementAt(i);
			try {
				m.addRow(rowData);
			} catch (IdentifierExistsException e) {

			}
		}
		if (this.getSortedColumnIndex() != -1) {
			this.sortColumn(this.getSortedColumnIndex(), this
					.isSortedColumnAscending());
		}
	}

	public void batchAddRowsRespectSorting(Object[][] rowsData) {
		if (this.getFiltersCount() != 0) {
			String mess = "This method can be used when filtering is disabled or "
					+ "when there are no currently applied filters";
			throw new UnsupportedOperationException(mess);
		}

		GeneralTableModel m = (GeneralTableModel) this.getModel();
		for (int i = 0; i < rowsData.length; i++) {
			Object[] rowData = rowsData[i];
			try {
				m.addRow(rowData);
			} catch (IdentifierExistsException e) {

			}
		}
		if (this.getSortedColumnIndex() != -1) {
			this.sortColumn(this.getSortedColumnIndex(), this
					.isSortedColumnAscending());
		}
	}

	public void batchAddRowsRespectSorting(Object[][] rowsData,
			Object[] identifiers) {
		if (this.getFiltersCount() != 0) {
			String mess = "This method can be used when filtering is disabled or "
					+ "when there are no currently applied filters";
			throw new UnsupportedOperationException(mess);
		}

		if (rowsData.length != identifiers.length) {

			return;
		}

		GeneralTableModel m = (GeneralTableModel) this.getModel();
		for (int i = 0; i < identifiers.length; i++) {
			Object identifier = identifiers[i];
			Object[] rowData = rowsData[i];
			try {
				m.addRow(rowData, identifier);
			} catch (IdentifierExistsException e) {
				e.printStackTrace();
			}
		}
		if (this.getSortedColumnIndex() != -1) {
			this.sortColumn(this.getSortedColumnIndex(), this
					.isSortedColumnAscending());
		}
	}

	public void updateRowData(int rowIndex, Vector newData) {
		// First stupid checks
		if (rowIndex < 0 || rowIndex >= this.getRowCount()) {

			return;
		}
		if (newData == null) {

			return;
		}

		// Second one ... not so stupid :-)
		if (newData.size() != this.getColumnCount()) {

			return;
		}

		Object id = this.getIdentifier(rowIndex);
		if (this.isIdentifierDisplayed()
				&& (id == null || !id.equals(newData.elementAt(this
						.getIdentifierColumn())))) {

			return;
		}

		GeneralTableModel m = (GeneralTableModel) getModel();
		// _sortedModelIndex = -1;
		int cCount = this.getColumnCount();
		int sortIndex = this.getSortedColumnIndex();
		boolean shouldSort = false;
		for (int i = 0; i < cCount; i++) {
			if (i == sortIndex
					&& !m.getValueAt(rowIndex, i).equals(newData.elementAt(i)))
				shouldSort = true;
			m.setValueAt(newData.elementAt(i), rowIndex, i);
		}
		if (shouldSort)
			this.sortColumn(sortIndex, this.isSortedColumnAscending());
	}

	public void removeRowsByIndentifier(Object[] identifiers) {
		removeRowsByIndentifier(GeneralTableModel.convertToVector(identifiers));
	}

	public void removeRowsByIndentifier(Vector identifiers) {
		// First stupid checks
		if (identifiers == null || identifiers.size() == 0) {

			return;
		}

		boolean isPausing = this.isPauseingSelectionChange();
		this.setPauseSelectionChange(true);

		GeneralTableModel m = (GeneralTableModel) getModel();
		Vector clone = (Vector) identifiers.clone();

		//
		// Firstly, try to get it from real model and remove it
		HashSet rows = new HashSet();
		for (int i = clone.size() - 1; i >= 0; i--) {
			Object identifier = clone.elementAt(i);
			int row = m.getIndexInRealModel(identifier);
			if (row != -1) {
				rows.add(Integer.valueOf(row));
				clone.remove(i);
			}
		}
		if (rows.size() > 0) {
			Vector rowsVector = new Vector(rows);
			Collections.sort(rowsVector);
			m._identifierToRowIndex = null;
			m._shouldIgnoreTableNValueChangeEvents = true;
			for (int i = rowsVector.size() - 1; i >= 0; i--) {
				if (i == 0) {
					m._shouldIgnoreTableNValueChangeEvents = false;
					m._shouldInvokeValueChangeEvents = true;
				}
				int row = ((Integer) rowsVector.elementAt(i)).intValue();
				m.getRealModel().removeRow(row);
			}
			m._shouldInvokeValueChangeEvents = false;
			m._shouldIgnoreTableNValueChangeEvents = false;
		}

		for (int i = clone.size() - 1; i >= 0; i--) {
			Object identifier = clone.elementAt(i);
			if (isIdentifierHidden(identifier)) {
				_hiddenRows.remove(identifier);
				m.removeHiddenIdentifier(identifier);
				clone.remove(i);
			}
		}

		this.setPauseSelectionChange(isPausing);
		_selAdapter.valueChanged(null);

		if (clone.size() > 0) {

		}
	}

	public void clearDisplayedRows() {
		boolean isPausing = this.isPauseingSelectionChange();
		this.setPauseSelectionChange(true);

		GeneralTableModel m = (GeneralTableModel) getModel();
		for (int i = this.getRowCount() - 1; i >= 0; i--)
			m.removeRow(i);

		this.setPauseSelectionChange(isPausing);
		_selAdapter.valueChanged(null);
	}

	public int getAllDataCount() {
		return ((GeneralTableModel) getModel())._model.getRowCount()
				+ getHiddenRowsCount();
	}

	public int getHiddenRowsCount() {
		return _hiddenRows.size();
	}

	public void setHeaderIcons(Vector<Icon> headerIcons) {
		if (headerIcons == _headerIcons)
			return;
		if (headerIcons == null || headerIcons.size() == getColumnCount()) {
			Object oldValue = _headerIcons;
			_headerIcons = headerIcons;
			firePropertyChange("headerIcons", oldValue, headerIcons);
		}
	}

	public void setColumnHidingEnabled(boolean columnHidingEnabled) {
		if (columnHidingEnabled == _columnHidingEnabled)
			return;
		_columnHidingEnabled = columnHidingEnabled;
		if (!columnHidingEnabled) {
			for (int i = 0; i < _columnSizeConfigures.size(); i++) {
				ColumnSizeConfigure columnSizeCon = _columnSizeConfigures
						.elementAt(i);
				if (columnSizeCon != null) {
					showColumn(i);
				}
			}
		}
	}

	public void setColumnHidingConfigureDialog(JDialog configureDialog) {
		if (configureDialog == null
				|| _columnHidingConfigureDialog == configureDialog)
			return;
		configureDialog.setTitle("Show/Hide Columns");
		_columnHidingConfigureDialog = configureDialog;
	}

	public void setUnhidableColumnIndex(Vector<Integer> unhidableColumnIndex) {
		_unhidableColumnIndex.clear();
		if (unhidableColumnIndex == null)
			return;
		for (int i = unhidableColumnIndex.size() - 1; i >= 0; i--) {
			Integer columnIndex = unhidableColumnIndex.elementAt(i);
			if (columnIndex != null) {
				int modelColumnIndex = convertColumnIndexToModel(columnIndex
						.intValue());
				_unhidableColumnIndex.addElement(Integer
						.valueOf(modelColumnIndex));
				showColumn(modelColumnIndex);
			}
		}
	}

	protected void hideColumn(int modelColumnIndex) {
		if (modelColumnIndex < 0
				|| modelColumnIndex >= getColumnCount()
				|| _unhidableColumnIndex.indexOf(Integer
						.valueOf(modelColumnIndex)) != -1)
			return;
		if (_columnSizeConfigures.size() <= modelColumnIndex) {
			_columnSizeConfigures.setSize(modelColumnIndex + 1);
		}
		ColumnSizeConfigure columnSizeCon = _columnSizeConfigures
				.elementAt(modelColumnIndex);
		if (columnSizeCon != null)
			return;
		TableColumn tabCol = getColumnModel().getColumn(
				convertColumnIndexToView(modelColumnIndex));
		columnSizeCon = new ColumnSizeConfigure(tabCol.getMaxWidth(), tabCol
				.getMinWidth(), tabCol.getPreferredWidth(), tabCol
				.getResizable());
		_columnSizeConfigures.setElementAt(columnSizeCon, modelColumnIndex);
		tabCol.setMinWidth(0);
		tabCol.setMaxWidth(0);
		tabCol.setPreferredWidth(0);
		tabCol.setResizable(false);
		_calculateTableMinWidthByColumns();
	}

	protected void showColumn(int modelColumnIndex) {
		if (modelColumnIndex < 0 || modelColumnIndex >= getColumnCount()
				|| _columnSizeConfigures.size() <= modelColumnIndex
				|| _columnSizeConfigures.elementAt(modelColumnIndex) == null)
			return;
		ColumnSizeConfigure columnSizeCon = _columnSizeConfigures
				.elementAt(modelColumnIndex);
		_columnSizeConfigures.setElementAt(null, modelColumnIndex);
		TableColumn tabCol = getColumnModel().getColumn(
				convertColumnIndexToView(modelColumnIndex));
		tabCol.setMinWidth(columnSizeCon._minSize);
		tabCol.setMaxWidth(columnSizeCon._maxSize);
		tabCol.setPreferredWidth(columnSizeCon._preferSize);
		tabCol.setResizable(columnSizeCon._isResizable);
		_calculateTableMinWidthByColumns();
	}

	public void setColumnHidingStatus(int column, boolean isHide) {
		int modelColumnIndex = convertColumnIndexToModel(column);
		if (isHide)
			hideColumn(modelColumnIndex);
		else
			showColumn(modelColumnIndex);
	}

	public Vector<TableHeaderInfo> getHidableColumnsInfo() {
		int columnCount = getColumnCount();
		Vector<TableHeaderInfo> hidableColumnsInfo = new Vector<TableHeaderInfo>();
		Icon icon = null;
		for (int i = 0; i < columnCount; i++) {
			int modelColumnIndex = convertColumnIndexToModel(i);
			if (_unhidableColumnIndex
					.indexOf(Integer.valueOf(modelColumnIndex)) != -1)
				continue;
			if (_headerIcons != null && _headerIcons.size() > i) {
				icon = _headerIcons.elementAt(modelColumnIndex);
			} else {
				icon = null;
			}
			TableHeaderInfo info = new TableHeaderInfo(getColumnName(i), icon,
					i, (String) _tips._tips.get(getColumnModel().getColumn(i)));
			hidableColumnsInfo.addElement(info);
		}
		return hidableColumnsInfo;
	}

	public Vector<Integer> getHidenColumnsIndex() {
		Vector<Integer> shownColumnsIndex = new Vector<Integer>();
		for (int i = 0; i < _columnSizeConfigures.size(); i++) {
			if (_columnSizeConfigures.elementAt(i) != null) {
				shownColumnsIndex.add(Integer
						.valueOf(convertColumnIndexToView(i)));
			}
		}
		return shownColumnsIndex;
	}

	public boolean canAllColumnsUnchecked() {
		return !_unhidableColumnIndex.isEmpty();
	}

	public void setFrameForColumnHidingConfigurePanel(Frame frame) {
		_columnHidingConfigureFrame = frame;
	}

	protected void showColumnHidingConfigurePanel() {
		if (_columnHidingConfigureDialog == null) {
			_columnHidingConfigureDialog = new ColumnHidingConfigureDialog(
					_columnHidingConfigureFrame, _getTablePanel(),
					new ColumnHidingConfigurePanel(this));
		}
		_columnHidingConfigureDialog.setVisible(true);
	}

	public static class ColumnSizeConfigure {
		protected int _maxSize;
		protected int _minSize;
		protected int _preferSize;
		protected boolean _isResizable;

		public ColumnSizeConfigure(int maxSize, int minSize, int preferSize,
				boolean isResizable) {
			_maxSize = maxSize;
			_minSize = minSize;
			_preferSize = preferSize;
			_isResizable = isResizable;
		}
	}

	private JPagingTablePanel _getTablePanel() {
		JPagingTablePanel panel = null;
		Component c = this.getParent();
		for (; c != null; c = c.getParent()) {
			if (c instanceof JPagingTablePanel) {
				panel = (JPagingTablePanel) c;
				break;
			}
		}
		return panel;
	}

	private Dimension _getTablePanelSize() {
		Dimension d = new Dimension(-1, -1);
		JPagingTablePanel panel = _getTablePanel();
		if (panel != null) {
			d = panel.getSize();
		}
		return d;
	}

	public TablePreferenceInfo getTablePreferenceInfo() {
		TablePreferenceInfo info = new TablePreferenceInfo();
		int columnCount = getColumnCount();
		Vector<Integer> columnIndex = new Vector<Integer>();
		Vector<Integer> columnWidth = new Vector<Integer>();
		Vector<Integer> shownColumnIndex = new Vector<Integer>();
		for (int modelColumnIndex = 0; modelColumnIndex < columnCount; modelColumnIndex++) {
			columnIndex.addElement(Integer
					.valueOf(convertColumnIndexToView(modelColumnIndex)));
			if (_columnSizeConfigures.size() <= modelColumnIndex
					|| _columnSizeConfigures.elementAt(modelColumnIndex) == null) {
				shownColumnIndex.addElement(modelColumnIndex);
				columnWidth.addElement(getColumnModel().getColumn(
						convertColumnIndexToView(modelColumnIndex)).getWidth());
			} else {
				ColumnSizeConfigure columnSizeConf = _columnSizeConfigures
						.elementAt(modelColumnIndex);
				columnWidth.addElement(columnSizeConf._preferSize);
			}
		}
		info.setColumnIndex(columnIndex);
		info.setColumnWidth(columnWidth);
		info.setTableMinimumWidth(getTableMinimumWidth());
		info.setShownColumnIndex(shownColumnIndex);
		info.setTablePanelSize(_getTablePanelSize());
		TablePreferenceInfo.SortedColumnPair pair = new TablePreferenceInfo.SortedColumnPair(
				_sortedModelIndex, isSortedColumnAscending());
		info.setSortedColumnPair(pair);
		return info;
	}

	private Vector<Integer> _translateToViewColumnIndex(
			Vector<Integer> columnIndex) {
		if (columnIndex == null)
			return null;
		int columnCount = columnIndex.size();
		Vector<Integer> viewColumnIndex = new Vector<Integer>(columnCount);
		for (int i = 0; i < columnCount; i++) {
			Integer viewIndex = columnIndex.elementAt(i);
			if (viewIndex != null) {
				if (viewIndex >= viewColumnIndex.size()) {
					viewColumnIndex.setSize(viewIndex + 1);
				}
				viewColumnIndex.set(viewIndex.intValue(), i);
			}
		}
		return viewColumnIndex;
	}

	public void setTablePreferenceInfo(TablePreferenceInfo info) {
		if (info == null)
			return;
		Vector<Integer> viewColumnIndex = _translateToViewColumnIndex(info
				.getColumnIndex());
		Vector<Integer> columnWidth = info.getColumnWidth();
		int tableMinimumWidth = info.getTableMinimumWidth();
		Vector<Integer> shownColumnIndex = info.getShownColumnIndex();
		TablePreferenceInfo.SortedColumnPair pair = info.getSortedColumnPair();
		Dimension tablePanelSize = info.getTablePanelSize();
		int columnCount = getColumnCount();
		if (tablePanelSize != null && tablePanelSize.width >= 0
				&& tablePanelSize.height >= 0) {
			JPagingTablePanel panel = _getTablePanel();
			if (panel != null) {
				panel.setPreferredSize(tablePanelSize);
			}
		}
		if (viewColumnIndex != null) {
			int count = viewColumnIndex.size();
			count = Math.min(count, columnCount);
			for (int i = 0; i < count; i++) {
				Integer modelIndex = viewColumnIndex.elementAt(i);
				if (modelIndex != null && modelIndex < columnCount) {
					moveColumn(convertColumnIndexToView(modelIndex.intValue()),
							i);
				}
			}
		}
		if (tableMinimumWidth >= 0) {
			setTableMinimumWidth(tableMinimumWidth);
		}
		if (columnWidth != null) {
			int count = columnWidth.size();
			count = Math.min(count, columnCount);
			for (int i = 0; i < count; i++) {
				Integer width = columnWidth.elementAt(i);
				if (width != null) {
					if (_columnSizeConfigures.size() <= i
							|| _columnSizeConfigures.elementAt(i) == null) {
						TableColumn column = getColumnModel().getColumn(
								convertColumnIndexToView(i));
						column.setPreferredWidth(width.intValue());
						column.setWidth(width.intValue());
					} else {
						_columnSizeConfigures.elementAt(i)._preferSize = width
								.intValue();
					}
				}
			}
		}
		if (shownColumnIndex != null) {
			for (int modelColumnIndex = 0; modelColumnIndex < columnCount; modelColumnIndex++) {
				if (shownColumnIndex.indexOf(modelColumnIndex) == -1) {
					hideColumn(modelColumnIndex);
				} else {
					showColumn(modelColumnIndex);
				}
			}
		}
		if (pair != null) {
			int sortedModelIndex = pair.getSortedColumnIndex();
			if (sortedModelIndex >= 0 && sortedModelIndex < columnCount) {
				sortColumn(convertColumnIndexToView(sortedModelIndex), pair
						.getIsSortAscending());
			} else {
				_sortedModelIndex = -1;
			}
		}
	}

	public void setTableMinimumWidth(int minimumWidth) {
		if (_isInsideJViewport) {
			if (minimumWidth < 0
					|| this.getParent() == null
					|| !(this.getParent().getParent() instanceof JPagingTablePanel.PagingTableLeftPanel))
				return;
			((JPagingTablePanel.PagingTableLeftPanel) this.getParent()
					.getParent()).setMinimumWidth(minimumWidth);
			this.revalidate();
		}
	}

	public int getTableMinimumWidth() {
		int tableMinimumWidth = -1;
		if (_isInsideJViewport) {
			if (this.getParent() != null
					&& this.getParent().getParent() != null) {
				tableMinimumWidth = this.getParent().getParent()
						.getPreferredSize().width;
			}
		}
		return tableMinimumWidth;
	}

	public void setCalculateTableMinWidthByColumns(
			boolean calTableMinWidthByColumns) {
		_calTableMinWidthByColumns = calTableMinWidthByColumns;
		_calculateTableMinWidthByColumns();
	}

	private void _calculateTableMinWidthByColumns() {
		if (!_calTableMinWidthByColumns)
			return;
		int tableMinimumWidth = 0;
		int columnCount = getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			// the column is shown
			if (_columnSizeConfigures.size() <= i
					|| _columnSizeConfigures.elementAt(i) == null) {
				int minWidth = getColumnModel().getColumn(i).getMinWidth();
				if (minWidth > 0) {
					tableMinimumWidth += minWidth;
				}
			}
		}
		setTableMinimumWidth(tableMinimumWidth);
	}

	public void setColumnMinWidth(int column, int minWidth) {
		int columModelIndex = convertColumnIndexToModel(column);
		if (columModelIndex < 0 || columModelIndex >= getColumnCount())
			return;

		ColumnSizeConfigure columnSizeCon = null;
		if (columModelIndex < _columnSizeConfigures.size()) {
			columnSizeCon = _columnSizeConfigures.elementAt(columModelIndex);
		}
		if (columnSizeCon == null) {
			getColumnModel().getColumn(column).setMinWidth(minWidth);
		} else {
			columnSizeCon._minSize = minWidth;
		}
	}

}
