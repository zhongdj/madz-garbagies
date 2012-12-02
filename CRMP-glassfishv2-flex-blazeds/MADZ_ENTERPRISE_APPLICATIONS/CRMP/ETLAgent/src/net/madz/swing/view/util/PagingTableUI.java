package net.madz.swing.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.ActionMap;
import javax.swing.CellEditor;
import javax.swing.CellRendererPane;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TableUI;
import javax.swing.plaf.UIResource;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PagingTableUI extends TableUI {

	protected JPagingTable table;
	protected CellRendererPane rendererPane;

	protected KeyListener keyListener;
	protected FocusListener focusListener;
	protected MouseInputListener mouseInputListener;

	public class FocusHandler implements FocusListener {
		private void repaintAnchorCell() {
			int rc = table.getRowCount();
			int cc = table.getColumnCount();
			int ar = table.getSelectionModel().getAnchorSelectionIndex();
			int ac = table.getColumnModel().getSelectionModel().getAnchorSelectionIndex();
			if (ar < 0 || ar >= rc || ac < 0 || ac >= cc) {
				return;
			}

			Rectangle dirtyRect = table.getCellRect(ar, ac, false);
			table.repaint(dirtyRect);
		}

		public void focusGained(FocusEvent e) {
			repaintAnchorCell();
		}

		public void focusLost(FocusEvent e) {
			repaintAnchorCell();
		}
	}

	public class MouseInputHandler implements MouseInputListener {

		private Component dispatchComponent;

		public void mouseClicked(MouseEvent e) {
		}

		private void setDispatchComponent(MouseEvent e) {
			Component editorComponent = table.getEditorComponent();
			Point p = e.getPoint();
			Point p2 = SwingUtilities.convertPoint(table, p, editorComponent);
			dispatchComponent = SwingUtilities.getDeepestComponentAt(editorComponent, p2.x, p2.y);
		}

		private boolean repostEvent(MouseEvent e) {
			if (dispatchComponent == null) {
				return false;
			}
			MouseEvent e2 = SwingUtilities.convertMouseEvent(table, e, dispatchComponent);
			dispatchComponent.dispatchEvent(e2);
			return true;
		}

		private void setValueIsAdjusting(boolean flag) {
			table.getSelectionModel().setValueIsAdjusting(flag);
			table.getColumnModel().getSelectionModel().setValueIsAdjusting(flag);
		}

		private boolean shouldIgnore(MouseEvent e) {
			return !(SwingUtilities.isLeftMouseButton(e) && table.isEnabled());
		}

		public void mousePressed(MouseEvent e) {
			if (shouldIgnore(e)) {
				return;
			}

			Point p = e.getPoint();
			int row = table.rowAtPoint(p);
			int column = table.columnAtPoint(p);
			// The autoscroller can generate drag events outside the Table's
			// range.
			if ((column == -1) || (row == -1)) {
				return;
			}

			if (table.editCellAt(row, column, e)) {
				setDispatchComponent(e);
				if (!repostEvent(e)) {
				}
			} else {
				table.requestFocus();
			}

			CellEditor editor = table.getCellEditor();
			if (editor == null || editor.shouldSelectCell(e)) {
				setValueIsAdjusting(true);
				table.changeSelection(row, column, e.isControlDown(), e.isShiftDown());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (shouldIgnore(e)) {
				return;
			}

			if (!repostEvent(e)) {
			}
			dispatchComponent = null;
			setValueIsAdjusting(false);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		// The Table's mouse motion listener methods.

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
			if (shouldIgnore(e)) {
				return;
			}

			if (repostEvent(e)) {
			}

			CellEditor editor = table.getCellEditor();
			if (editor == null || editor.shouldSelectCell(e)) {
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				int column = table.columnAtPoint(p);
				// The autoscroller can generate drag
				// events outside the Table's range.
				if ((column == -1) || (row == -1)) {
					return;
				}
				// CR Q01475919, He Shan, 2006/10/19 begin
				if (e.isControlDown()) {
					ListSelectionModel cm = table.getColumnModel().getSelectionModel();
					ListSelectionModel rm = table.getSelectionModel();
					int colAnchor = cm.getAnchorSelectionIndex();
					int rowAnchor = rm.getAnchorSelectionIndex();

					boolean selected = true;

					if (rowAnchor == -1 || rowAnchor >= table.getRowCount()) {
						rowAnchor = 0;
						selected = false;
					}

					if (colAnchor == -1 || colAnchor >= table.getColumnCount()) {
						colAnchor = 0;
						selected = false;
					}

					selected = selected && table.isCellSelected(rowAnchor, colAnchor);

					changeSelectionModel(cm, colAnchor, selected, column);
					changeSelectionModel(rm, rowAnchor, selected, row);

					if (table.getAutoscrolls()) {
						Rectangle cellRect = table.getCellRect(row, column, false);
						if (cellRect != null) {
							table.scrollRectToVisible(cellRect);
						}
					}
				} else {
					table.changeSelection(row, column, false, true);
				}
			}
		}

		private void changeSelectionModel(ListSelectionModel sm, int anchorIndex, boolean anchorSelected, int index) {

			if (anchorSelected) {
				sm.addSelectionInterval(anchorIndex, index);
			} else {
				sm.removeSelectionInterval(anchorIndex, index);
			}
		}

	}

	protected KeyListener createKeyListener() {
		return null;
	}

	protected FocusListener createFocusListener() {
		return new FocusHandler();
	}

	protected MouseInputListener createMouseInputListener() {
		return new MouseInputHandler();
	}

	public static ComponentUI createUI(JComponent c) {
		return new PagingTableUI();
	}

	public void installUI(JComponent c) {
		table = (JPagingTable) c;

		rendererPane = new CellRendererPane();
		if (table.add(rendererPane) == null) {
		}

		installDefaults();
		installListeners();
		installKeyboardActions();
	}

	protected void installDefaults() {
		LookAndFeel.installColorsAndFont(table, "Table.background", "Table.foreground", "Table.font");

		Color sbg = table.getSelectionBackground();
		if (sbg == null || sbg instanceof UIResource) {
			Color c = UIManager.getColor("Table.selectionBackground");
			table.setSelectionBackground(c);
		}

		Color sfg = table.getSelectionForeground();
		if (sfg == null || sfg instanceof UIResource) {
			Color c = UIManager.getColor("Table.selectionForeground");
			table.setSelectionForeground(c);
		}

		Color gridColor = table.getGridColor();
		if (gridColor == null || gridColor instanceof UIResource) {
			table.setGridColor(UIManager.getColor("Table.gridColor"));
		}

		Container parent = table.getParent(); // should be viewport
		if (parent != null) {
			parent = parent.getParent(); // should be the scrollpane
			if (parent != null && parent instanceof JScrollPane) {
				LookAndFeel.installBorder((JScrollPane) parent, "Table.scrollPaneBorder");
			}
		}
	}

	protected void installListeners() {
		focusListener = createFocusListener();
		keyListener = createKeyListener();
		mouseInputListener = createMouseInputListener();

		table.addFocusListener(focusListener);
		table.addKeyListener(keyListener);
		table.addMouseListener(mouseInputListener);
		table.addMouseMotionListener(mouseInputListener);
	}

	protected void installKeyboardActions() {
		ActionMap map = getActionMap();

		SwingUtilities.replaceUIActionMap(table, map);
		InputMap inputMap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		SwingUtilities.replaceUIInputMap(table, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, inputMap);
	}

	InputMap getInputMap(int condition) {
		if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) {
			return (InputMap) UIManager.get("Table.ancestorInputMap");
		}
		return null;
	}

	ActionMap getActionMap() {
		ActionMap map = (ActionMap) UIManager.get("Table.actionMap");

		if (map == null) {
			if (UIManager.put("Table.actionMap", map) == null) {
			}
		}
		return map;
	}

	public void uninstallUI(JComponent c) {
		uninstallDefaults();
		uninstallListeners();
		uninstallKeyboardActions();

		table.remove(rendererPane);
		rendererPane = null;
		table = null;
	}

	protected void uninstallDefaults() {
	}

	protected void uninstallListeners() {
		table.removeFocusListener(focusListener);
		table.removeKeyListener(keyListener);
		table.removeMouseListener(mouseInputListener);
		table.removeMouseMotionListener(mouseInputListener);

		focusListener = null;
		keyListener = null;
		mouseInputListener = null;
	}

	protected void uninstallKeyboardActions() {
		SwingUtilities.replaceUIInputMap(table, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, null);
		SwingUtilities.replaceUIActionMap(table, null);
	}

	private Dimension createTableSize(long width) {
		int height = 0;
		int rowCount = table.getRowCount();
		int rowI = table.getPagingLocation().x + table.getRowPageSize() - 1;
		if (rowCount > 0 && table.getColumnCount() > 0) {
			Rectangle r = table.getCellRect(rowI, 0, true);
			height = r.y + r.height;
		}

		long tmp = Math.abs(width);
		if (tmp > Integer.MAX_VALUE) {
			tmp = Integer.MAX_VALUE;
		}

		return new Dimension((int) tmp, height);
	}

	public Dimension getMinimumSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = table.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMinWidth();
		}
		return createTableSize(width);
	}

	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = table.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		return createTableSize(width);
	}

	public Dimension getMaximumSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = table.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMaxWidth();
		}
		return createTableSize(width);
	}

	public void paint(Graphics g, JComponent c) {
		if (table.getRowCount() <= 0 || table.getColumnCount() <= 0) {
			return;
		}

		int rMin = table.getPagingLocation().x;
		int rMax = rMin + table.getRowPageSize() - 1;

		// This should never happen.
		if (rMin == -1) {
			rMin = 0;
		}

		if (rMax == -1) {
			rMax = table.getRowCount() - 1;
		}
		int cMin = table.getPagingLocation().y;
		int cMax = cMin + table.getColumnPageSize() - 1;
		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}

		if (cMax == -1) {
			cMax = table.getColumnCount() - 1;
		}

		// Paint the grid.
		paintGrid(g, rMin, rMax, cMin, cMax);

		// Paint the cells.
		paintCells(g, rMin, rMax, cMin, cMax);
	}

	private void paintGrid(Graphics g, int rMin, int rMax, int cMin, int cMax) {
		g.setColor(table.getGridColor());

		Rectangle minCell = table.getCellRect(rMin, cMin, true);
		Rectangle maxCell = table.getCellRect(rMax, cMax, true);

		if (table.getShowHorizontalLines()) {
			int tableWidth = maxCell.x + maxCell.width;
			int y = minCell.y;
			for (int row = rMin; row <= rMax; row++) {
				y += table.getRowHeight(row);
				g.drawLine(0, y - 1, tableWidth - 1, y - 1);
			}
		}

		if (table.getShowVerticalLines()) {
			TableColumnModel cm = table.getColumnModel();
			int tableHeight = maxCell.y + maxCell.height;
			int x = minCell.x;
			for (int column = cMin; column <= cMax; column++) {
				x += cm.getColumn(column).getWidth();
				g.drawLine(x - 1, 0, x - 1, tableHeight - 1);
			}
		}
	}

	private int viewIndexForColumn(TableColumn aColumn) {
		TableColumnModel cm = table.getColumnModel();
		for (int column = 0; column < cm.getColumnCount(); column++) {
			TableColumn tc = cm.getColumn(column);
			if (tc != null && tc.equals(aColumn)) {
				return column;
			}
		}
		return -1;
	}

	private void paintCells(Graphics g, int rMin, int rMax, int cMin, int cMax) {
		JTableHeader header = table.getTableHeader();
		TableColumn draggedColumn = (header == null) ? null : header.getDraggedColumn();

		TableColumnModel cm = table.getColumnModel();
		int columnMargin = cm.getColumnMargin();

		for (int row = rMin; row <= rMax; row++) {
			Rectangle cellRect = table.getCellRect(row, cMin, false);
			for (int column = cMin; column <= cMax; column++) {
				// There may raise a exception, because it is not effect, we
				// should hide it to user.
				TableColumn aColumn;
				try {
					aColumn = cm.getColumn(column);
				} catch (ArrayIndexOutOfBoundsException e) {

					continue;
				}
				int columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth - columnMargin;

				if (!aColumn.equals(draggedColumn)) {
					paintCell(g, cellRect, row, column);
				}

				cellRect.x += columnWidth;
			}
		}
		// Paint the dragged column if we are dragging.
		if (draggedColumn != null) {
			paintDraggedArea(g, rMin, rMax, draggedColumn, header.getDraggedDistance());
		}

		// Remove any renderers that may be left in the rendererPane.
		rendererPane.removeAll();
	}

	private void paintDraggedArea(Graphics g, int rMin, int rMax, TableColumn draggedColumn, int distance) {
		int draggedColumnIndex = viewIndexForColumn(draggedColumn);

		Rectangle minCell = table.getCellRect(rMin, draggedColumnIndex, true);
		Rectangle maxCell = table.getCellRect(rMax, draggedColumnIndex, true);

		Rectangle vacatedColumnRect = minCell.union(maxCell);

		// Paint a gray well in place of the moving column.
		g.setColor(table.getParent().getBackground());
		g.fillRect(vacatedColumnRect.x, vacatedColumnRect.y, vacatedColumnRect.width, vacatedColumnRect.height);

		// Move to the where the cell has been dragged.
		vacatedColumnRect.x += distance;

		// Fill the background.
		g.setColor(table.getBackground());
		g.fillRect(vacatedColumnRect.x, vacatedColumnRect.y, vacatedColumnRect.width, vacatedColumnRect.height);

		// Paint the vertical grid lines if necessary.
		if (table.getShowVerticalLines()) {
			g.setColor(table.getGridColor());
			int x1 = vacatedColumnRect.x;
			int y1 = vacatedColumnRect.y;
			int x2 = x1 + vacatedColumnRect.width - 1;
			int y2 = y1 + vacatedColumnRect.height - 1;
			// Left
			g.drawLine(x1 - 1, y1, x1 - 1, y2);
			// Right
			g.drawLine(x2, y1, x2, y2);
		}

		for (int row = rMin; row <= rMax; row++) {
			// Render the cell value
			Rectangle r = table.getCellRect(row, draggedColumnIndex, false);
			r.x += distance;
			paintCell(g, r, row, draggedColumnIndex);

			// Paint the (lower) horizontal grid line if necessary.
			if (table.getShowHorizontalLines()) {
				g.setColor(table.getGridColor());
				Rectangle rcr = table.getCellRect(row, draggedColumnIndex, true);
				rcr.x += distance;
				int x1 = rcr.x;
				int y1 = rcr.y;
				int x2 = x1 + rcr.width - 1;
				int y2 = y1 + rcr.height - 1;
				g.drawLine(x1, y2, x2, y2);
			}
		}
	}

	private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
		if (table.isEditing() && table.getEditingRow() == row && table.getEditingColumn() == column) {
			Component component = table.getEditorComponent();
			component.setBounds(cellRect);
			component.validate();
		} else {
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component component = table.prepareRenderer(renderer, row, column);
			rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
		}
	}
} // End of Class PagingTableUI

