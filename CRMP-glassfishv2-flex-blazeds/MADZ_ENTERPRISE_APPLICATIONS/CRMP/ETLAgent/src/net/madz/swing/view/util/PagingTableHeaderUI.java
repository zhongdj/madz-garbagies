package net.madz.swing.view.util;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PagingTableHeaderUI extends TableHeaderUI {

	protected JTableHeader header;

	protected JPagingTable _pTable;

	protected CellRendererPane rendererPane;

	protected MouseInputListener mouseInputListener;

	public class MouseInputHandler implements MouseInputListener {

		private int lastEffectiveMouseX;

		public void mouseClicked(MouseEvent e) {
		}

		private boolean canResize(TableColumn column) {
			return (column != null) && header.getResizingAllowed() && column.getResizable();
		}

		private TableColumn getResizingColumn(Point p) {
			return getResizingColumn(p, header.columnAtPoint(p));
		}

		private TableColumn getResizingColumn(Point p, int column) {
			if (column == -1) {
				return null;
			}
			Rectangle r = header.getHeaderRect(column);
			r.grow(-3, 0);
			if (r.contains(p)) {
				return null;
			}

			int midPoint = r.x + r.width / 2;
			int columnIndex = (p.x < midPoint) ? column - 1 : column;
			if (columnIndex == -1) {
				return null;
			}
			return header.getColumnModel().getColumn(columnIndex);
		}

		public void mousePressed(MouseEvent e) {
			header.setDraggedColumn(null);
			header.setResizingColumn(null);
			header.setDraggedDistance(0);

			Point p = e.getPoint();
			lastEffectiveMouseX = p.x;

			// First find which header cell was hit
			TableColumnModel columnModel = header.getColumnModel();
			int index = header.columnAtPoint(p);

			if (index != -1) {
				// The last 3 pixels + 3 pixels of next column are for resizing
				TableColumn resizingColumn = getResizingColumn(p, index);
				if (canResize(resizingColumn)) {
					header.setResizingColumn(resizingColumn);
				} else if (header.getReorderingAllowed()) {
					TableColumn hitColumn = columnModel.getColumn(index);
					header.setDraggedColumn(hitColumn);
				} else { // Not allowed to reorder or resize.
				}
			}
		}

		private void setCursor(Cursor c) {
			if (header.getCursor() == null || !header.getCursor().equals(c)) {
				header.setCursor(c);
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (canResize(getResizingColumn(e.getPoint()))) {
				setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

		public void mouseDragged(MouseEvent e) {
			int mouseX = e.getX();
			int deltaX = mouseX - lastEffectiveMouseX;

			if (deltaX == 0) {
				return;
			}

			TableColumn resizingColumn = header.getResizingColumn();
			TableColumn draggedColumn = header.getDraggedColumn();

			if (resizingColumn != null) {
				int oldWidth = resizingColumn.getWidth();
				int newWidth = oldWidth + deltaX;
				resizingColumn.setWidth(newWidth);

				int acheivedDeltaX = resizingColumn.getWidth() - oldWidth;
				lastEffectiveMouseX = lastEffectiveMouseX + acheivedDeltaX;
			} else if (draggedColumn != null) {
				move(e, deltaX);
				lastEffectiveMouseX = mouseX;
			} else {
				// Neither dragging nor resizing ...
				lastEffectiveMouseX = mouseX;
			}
		}

		public void mouseReleased(MouseEvent e) {
			setDraggedDistance(0, viewIndexForColumn(header.getDraggedColumn()));
			header.setResizingColumn(null);
			header.setDraggedColumn(null);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		//
		// Protected & Private Methods
		//

		private void setDraggedDistance(int draggedDistance, int column) {
			header.setDraggedDistance(draggedDistance);
			if (column != -1) {
				header.getColumnModel().moveColumn(column, column);
			}
		}

		private void move(MouseEvent e, int delta) {
			TableColumnModel columnModel = header.getColumnModel();
			int lastColumn = columnModel.getColumnCount() - 1;

			TableColumn draggedColumn = header.getDraggedColumn();
			int draggedDistance = header.getDraggedDistance() + delta;
			int hitColumnIndex = viewIndexForColumn(draggedColumn);

			// Now check if we have moved enough to do a swap
			if ((draggedDistance < 0) && (hitColumnIndex != 0)) {
				// Moving left; check prevColumn
				int width = columnModel.getColumn(hitColumnIndex - 1).getWidth();
				if (-draggedDistance > (width / 2)) {
					// Swap me
					columnModel.moveColumn(hitColumnIndex, hitColumnIndex - 1);

					draggedDistance = width + draggedDistance;
					hitColumnIndex--;
				}
			} else if ((draggedDistance > 0) && (hitColumnIndex != lastColumn)) {
				// Moving right; check nextColumn
				int width = columnModel.getColumn(hitColumnIndex + 1).getWidth();
				if (draggedDistance > (width / 2)) {
					// Swap me
					columnModel.moveColumn(hitColumnIndex, hitColumnIndex + 1);

					draggedDistance = -(width - draggedDistance);
					hitColumnIndex++;
				}
			}

			header.setDraggedColumn(columnModel.getColumn(hitColumnIndex));
			setDraggedDistance(draggedDistance, hitColumnIndex);
		}
	}

	protected MouseInputListener createMouseInputListener() {
		return new MouseInputHandler();
	}

	public static ComponentUI createUI(JComponent h) {
		return new PagingTableHeaderUI();
	}

	public void installUI(JComponent c) {
		header = (JTableHeader) c;

		rendererPane = new CellRendererPane();
		if (header.add(rendererPane) == null) {
		}

		installDefaults();
		installListeners();
		installKeyboardActions();
	}

	protected void installDefaults() {
		LookAndFeel.installColorsAndFont(header, "TableHeader.background", "TableHeader.foreground", "TableHeader.font");
	}

	protected void installListeners() {
		mouseInputListener = createMouseInputListener();

		header.addMouseListener(mouseInputListener);
		header.addMouseMotionListener(mouseInputListener);
	}

	protected void installKeyboardActions() {
	}

	// Uninstall methods

	public void uninstallUI(JComponent c) {
		uninstallDefaults();
		uninstallListeners();
		uninstallKeyboardActions();

		header.remove(rendererPane);
		rendererPane = null;
		header = null;
	}

	protected void uninstallDefaults() {
	}

	protected void uninstallListeners() {
		header.removeMouseListener(mouseInputListener);
		header.removeMouseMotionListener(mouseInputListener);

		mouseInputListener = null;
	}

	protected void uninstallKeyboardActions() {
	}

	public void paint(Graphics g, JComponent c) {
		if (header.getColumnModel().getColumnCount() <= 0) {
			return;
		}

		_pTable = (JPagingTable) header.getTable();

		TableColumnModel cm = header.getColumnModel();

		int cMin = _pTable.getPagingLocation().y;
		int cMax = cMin + _pTable.getColumnPageSize() - 1;

		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}

		if (cMax == -1) {
			cMax = cm.getColumnCount() - 1;
		}

		TableColumn draggedColumn = header.getDraggedColumn();

		Rectangle cellRect = header.getHeaderRect(cMin);
		for (int column = cMin; column <= cMax; column++) {
			TableColumn aColumn = cm.getColumn(column);
			int columnWidth = aColumn.getWidth();
			cellRect.width = columnWidth;
			if (!aColumn.equals(draggedColumn)) {
				paintCell(g, cellRect, column);
			}
			cellRect.x += columnWidth;
		}

		// Paint the dragged column if we are dragging.
		if (draggedColumn != null)

		{
			int draggedColumnIndex = viewIndexForColumn(draggedColumn);
			Rectangle draggedCellRect = header.getHeaderRect(draggedColumnIndex);

			// Draw a gray well in place of the moving column.
			g.setColor(header.getParent().getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y, draggedCellRect.width, draggedCellRect.height);

			draggedCellRect.x += header.getDraggedDistance();
			paintCell(g, draggedCellRect, draggedColumnIndex);
		}

		// Remove all components in the rendererPane.
		rendererPane.removeAll();
	}

	private Component getHeaderRenderer(int columnIndex) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();
		if (renderer == null) {
			renderer = header.getDefaultRenderer();
		}
		return renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);
	}

	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		Component component = getHeaderRenderer(columnIndex);
		rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
	}

	private int viewIndexForColumn(TableColumn aColumn) {
		TableColumnModel cm = header.getColumnModel();
		for (int column = 0; column < cm.getColumnCount(); column++) {
			TableColumn tc = cm.getColumn(column);
			if (tc != null && tc.equals(aColumn)) {
				return column;
			}
		}
		return -1;
	}

	private int getHeaderHeight() {
		int height = 0;
		boolean accomodatedDefault = false;
		TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			TableColumn aColumn = columnModel.getColumn(column);

			if (aColumn.getHeaderRenderer() != null || !accomodatedDefault) {
				Component comp = getHeaderRenderer(column);
				int rendererHeight = comp.getPreferredSize().height;
				height = Math.max(height, rendererHeight);

				if (rendererHeight > 0) {
					accomodatedDefault = true;
				}
			}
		}

		return height;
	}

	private Dimension createHeaderSize(long width) {
		// None of the callers include the intercell spacing, do it here.
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension((int) width, getHeaderHeight());
	}

	public Dimension getMinimumSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMinWidth();
		}
		return createHeaderSize(width);
	}

	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}

	public Dimension getMaximumSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMaxWidth();
		}
		return createHeaderSize(width);
	}
}
