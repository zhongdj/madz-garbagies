package net.madz.swing.view.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.CellRendererPane;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.madz.swing.view.table.CellComparator;
import net.madz.swing.view.table.TableFilter;
import net.madz.swing.view.table.TableMatchFilter;

public class JPagingTableHeader extends JTableHeader {

	public final static int HORIZONTAL_CELL_MARGIN = 3;

	/** Downward shift size of filtering combo-box */
	public final static int VERTICAL_BORDER_SIZE = 2;

	/** Used JPagingTable */
	private JPagingTable _pTable = null;

	/** Mouse listener to handle sorting, filtering and auto-size events */
	private MouseAdapter _mouseListener = null;

	/** Current column index */
	private int _currentColumnIndex = -1;

	/** combo popup object used to show current filtering combo-box */
	private FilterComboPopup _filterCombopopup = new FilterComboPopup(new JComboBox());

	private JPopupMenu _popup = new JPopupMenu();

	public JPagingTableHeader(TableColumnModel cm) {
		super(cm);
		_mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				processMouseClickedEvent(e);
			}
		};
		this.addMouseListener(_mouseListener);

		_filterCombopopup.getComboBox().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				processFilterApplied(e);
			}
		});

		JMenuItem menuItem = new JMenuItem("Show/Hide Columns...");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_pTable.showColumnHidingConfigurePanel();
			}
		});
		_popup.add(menuItem);

	}

	public int columnAtPoint(Point point) {
		_pTable = (JPagingTable) this.getTable();

		return _pTable.columnAtPoint(point);
	}

	public Rectangle getHeaderRect(int column) {
		_pTable = (JPagingTable) this.getTable();

		Rectangle rBase = super.getHeaderRect(_pTable.getPagingLocation().y);
		Rectangle r = super.getHeaderRect(column);

		Rectangle toRet = new Rectangle(r.x - rBase.x, r.y - rBase.y, r.width, r.height);

		return toRet;
	}

	public void processMouseClickedEvent(MouseEvent e) {
		if (isSortingMouseEvent(e)) {
			processSortingAction(e);
		} else if (_pTable.isFilteringEnabled() && isFilteringMouseEvent(e)) {
			processFilteringAction(e);
		} else if (isAutoSizeMouseEvent(e)) {
			processAutoSizeAction(e);
		}

		else if (isColumnHidingConfigureMouseEvent(e)) {
			processColumnHidingConfigureAction(e);
		}

	}

	public void processSortingAction(MouseEvent e) {
		int column = columnAtPoint(e.getPoint());

		if (!_pTable.isSortable(column)) {
			// Sorting is not allowed
			return;
		}

		if (column != -1) {
			if (column != _pTable.getSortedColumnIndex()) {
				_pTable.sortColumn(column, true);
			} else {
				_pTable.sortColumn(column, !_pTable.isSortedColumnAscending());
			}
		}
	}

	public void processFilteringAction(MouseEvent e) {

		Vector currentControlItems = new Vector();
		currentControlItems.add("---");
		currentControlItems.add("All");
		currentControlItems.add("---");

		_currentColumnIndex = columnAtPoint(e.getPoint());

		Vector items = new Vector(0);
		items.clear();

		if (_pTable.getFilter(_currentColumnIndex) == null) {
			items = _pTable.getUniqueValuesForColumn(_currentColumnIndex);
		}

		_filterCombopopup.configure(currentControlItems, items, _pTable, _currentColumnIndex);

		Rectangle rect = getHeaderRect(columnAtPoint(e.getPoint()));

		_filterCombopopup.show(this, rect.x + HORIZONTAL_CELL_MARGIN, rect.y + VERTICAL_BORDER_SIZE);
	}

	public void processFilterApplied(ItemEvent e) {

		JComboBox fcc = _filterCombopopup.getComboBox();

		if (e.getSource() != null && e.getSource().equals(fcc) && e.getStateChange() == ItemEvent.SELECTED && fcc.getSelectedIndex() != 0
				&& fcc.getSelectedIndex() != 2) {
			if (fcc.getSelectedIndex() != 1) {
				TableFilter aFilter = _pTable.getFilter(_currentColumnIndex);

				if (aFilter == null) {
					CellComparator c = _pTable.getCellComparator(_currentColumnIndex);

					TableMatchFilter filter = new TableMatchFilter(_currentColumnIndex, c);

					_pTable.addFilter(filter);

					filter.filter(fcc.getSelectedItem());
				}
			} else {
				if (_pTable.getFilter(_currentColumnIndex) != null) {
					_pTable.removeFilter(_currentColumnIndex);
				}
			}
			_filterCombopopup.hide();
		}
	}

	public void processAutoSizeAction(MouseEvent e) {
		int column = columnAtPoint(e.getPoint());

		Rectangle hRect = getHeaderRect(column);
		Point mPoint = e.getPoint();

		if (mPoint.x <= hRect.x + HORIZONTAL_CELL_MARGIN && mPoint.x >= hRect.x && column != 0) {
			column--;
		}

		_pTable.doAutoSize(column);

	}

	public void processColumnHidingConfigureAction(MouseEvent e) {
		_popup.show(e.getComponent(), e.getX(), e.getY());
	}

	public boolean isColumnHidingConfigureMouseEvent(MouseEvent e) {
		return SwingUtilities.isRightMouseButton(e) && _pTable._columnHidingEnabled;
	}

	public boolean isSortingMouseEvent(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return false;
		}

		if (e.getClickCount() != 1) {
			return false;
		}

		int column = columnAtPoint(e.getPoint());
		if (column == -1) {
			return false;
		}

		Rectangle hRect = getHeaderRect(column);
		Point mPoint = e.getPoint();

		int maxPoint = hRect.x + HORIZONTAL_CELL_MARGIN;

		if (_pTable.isFilteringEnabled(column)) {
			maxPoint += PagingHeaderRenderer.FILTER_COMBOBOX_WIDTH;
		}

		if (mPoint.x <= maxPoint || mPoint.x >= hRect.x + hRect.width - HORIZONTAL_CELL_MARGIN) {
			return false;
		}

		return true;
	}

	public boolean isFilteringMouseEvent(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return false;
		}

		if (e.getClickCount() != 1) {
			return false;
		}

		int column = columnAtPoint(e.getPoint());
		if (column == -1) {
			return false;
		}

		Rectangle hRect = getHeaderRect(column);
		Point mPoint = e.getPoint();

		if (mPoint.x >= hRect.x + PagingHeaderRenderer.FILTER_COMBOBOX_WIDTH || mPoint.x <= hRect.x + HORIZONTAL_CELL_MARGIN) {
			return false;
		}

		return true;
	}

	public boolean isAutoSizeMouseEvent(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return false;
		}

		if (e.getClickCount() != 2) {
			return false;
		}

		int column = columnAtPoint(e.getPoint());
		if (column == -1) {
			return false;
		}

		Rectangle hRect = getHeaderRect(column);
		Point mPoint = e.getPoint();

		if (mPoint.x > hRect.x + HORIZONTAL_CELL_MARGIN &&

		mPoint.x < hRect.x + hRect.width - HORIZONTAL_CELL_MARGIN)

		{
			return false;
		}

		return true;
	}

	public void printAsPaint(Graphics g) {
		if (this.getColumnModel().getColumnCount() <= 0) {
			return;
		}

		TableColumnModel cm = this.getColumnModel();
		int cMin = 0;
		int cMax = cm.getColumnCount() - 1;
		Rectangle cellRect = super.getHeaderRect(cMin);

		for (int column = cMin; column <= cMax; column++) {
			TableColumn aColumn = cm.getColumn(column);
			int columnWidth = aColumn.getWidth();
			cellRect.width = columnWidth;
			paintCell(g, cellRect, column);
			cellRect.x += columnWidth;
		}
	}

	private Component getHeaderRenderer(int columnIndex) {
		TableColumn aColumn = this.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();
		if (renderer == null) {
			renderer = this.getDefaultRenderer();
		}
		return renderer.getTableCellRendererComponent(this.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);
	}

	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		CellRendererPane rendererPane = new CellRendererPane();
		Component component = getHeaderRenderer(columnIndex);
		rendererPane.paintComponent(g, component, this, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
	}
}
