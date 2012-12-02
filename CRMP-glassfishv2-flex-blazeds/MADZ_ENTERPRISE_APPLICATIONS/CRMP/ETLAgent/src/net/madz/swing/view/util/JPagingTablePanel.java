package net.madz.swing.view.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.RepaintManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import net.madz.swing.view.table.GeneralTableModel;

public class JPagingTablePanel extends JPanel implements AdjustmentListener, PropertyChangeListener, MouseWheelListener {

	protected BorderLayout _layout = new BorderLayout();

	protected BorderLayout _pLeftLayout = new BorderLayout();

	protected BorderLayout _pRightLayout = new BorderLayout();

	protected BorderLayout _pTablePanelLayout = new BorderLayout();

	protected JPagingTable _pTable = null;

	protected JPagingTableHeader _header = null;

	protected JScrollBar _columnScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);

	protected JScrollBar _rowScrollBar = new JScrollBar(JScrollBar.VERTICAL);

	protected JPanel _pPaginTablePanel = new JPanel(_pTablePanelLayout);

	protected JPanel _pLeftPanel = new PagingTableLeftPanel(_pLeftLayout);

	protected JPanel _pRightPanel = new JPanel(_pRightLayout);

	protected JPanel _pTopRightPanel = new JPanel();
	protected JPanel _pBottomRightPanel = new JPanel();

	protected PagingTableUI _tableUI = new PagingTableUI();

	protected PagingTableHeaderUI _tableHeaderUI = new PagingTableHeaderUI();

	private int _lastRowCount = -1;
	private int _lastColCount = -1;

	private boolean _stopAdjustmentListener = false;

	public JPagingTablePanel(boolean creatingForSmallAmountOfColumns) {
		this(new GeneralTableModel(new Vector(), new Vector(), new Vector(), new Vector()), creatingForSmallAmountOfColumns);

	}

	public JPagingTablePanel(GeneralTableModel realModel, boolean creatingForSmallAmountOfColumns) {
		_pTable = new JPagingTable();
		_pTable.setUI(_tableUI);
		_pTable.setModel(realModel);
		_pTable._isInsideJViewport = creatingForSmallAmountOfColumns;

		_pTable.addPropertyChangeListener(this);

		_header = new JPagingTableHeader(_pTable.getColumnModel());
		_header.setUI(_tableHeaderUI);
		_header.setDefaultRenderer(new PagingHeaderRenderer());
		_pTable.setTableHeader(_header);
		this.setLayout(_layout);

		_pPaginTablePanel.add(_pTable, BorderLayout.CENTER);

		_pTable.setAutoscrolls(false);

		_pLeftPanel.add(_columnScrollBar, BorderLayout.SOUTH);
		_pLeftPanel.add(_header, BorderLayout.NORTH);
		_pLeftPanel.add(_pPaginTablePanel, BorderLayout.CENTER);
		_pRightPanel.add(_rowScrollBar, BorderLayout.CENTER);

		int headerHeight = _header.getPreferredSize().height;
		int bottomPanelHeight = 0;
		ComponentAdapter tableResizeListener = null;

		if (creatingForSmallAmountOfColumns) {
			JScrollPane toRet = new JScrollPane(_pLeftPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			toRet.setWheelScrollingEnabled(false);
			toRet.addMouseWheelListener(this);
			toRet.setBorder(null);
			this.add(toRet, BorderLayout.CENTER);
			final JScrollBar columnScrollBar = toRet.getHorizontalScrollBar();
			bottomPanelHeight = columnScrollBar.getPreferredSize().height;
			tableResizeListener = new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					_pBottomRightPanel.setVisible(columnScrollBar.isVisible());
				}
			};
			toRet.addComponentListener(tableResizeListener);
		} else {
			this.addMouseWheelListener(this);

			this.add(_pLeftPanel, BorderLayout.CENTER);
			bottomPanelHeight = _columnScrollBar.getPreferredSize().height;
			tableResizeListener = new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					_pBottomRightPanel.setVisible(_columnScrollBar.isVisible());
				}
			};
			this.addComponentListener(tableResizeListener);
		}
		_pTable.addComponentListener(tableResizeListener);

		Dimension topRightDimension = new Dimension(_rowScrollBar.getPreferredSize().width, headerHeight);
		_pTopRightPanel.setPreferredSize(topRightDimension);
		_pRightPanel.add(_pTopRightPanel, BorderLayout.NORTH);
		_pBottomRightPanel.setPreferredSize(new Dimension(topRightDimension.width, bottomPanelHeight));
		_pRightPanel.add(_pBottomRightPanel, BorderLayout.SOUTH);
		Color highlight = UIManager.getColor("Table.highlight");
		Color shadow = UIManager.getColor("Table.shadow");
		if (highlight != null && shadow != null) {
			super.setBorder(new BevelBorder(BevelBorder.LOWERED, highlight.darker(), shadow.darker()));
		} else {
			super.setBorder(BorderFactory.createLoweredBevelBorder());
		}

		this.add(_pRightPanel, BorderLayout.EAST);

		_columnScrollBar.addAdjustmentListener(this);
		_rowScrollBar.addAdjustmentListener(this);

		adjustScrollBars(_pTable.getPagingViewPosition());
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {

		if (_stopAdjustmentListener)
			return;
		if (e.getSource() != null && e.getSource().equals(_rowScrollBar)) {
			int newValue = e.getValue();
			if (newValue == _pTable.getRowCount() - _pTable.getPagingViewPosition().rowPageSize - 1 && newValue < _pTable._cPos.currentRow
					&& !_pTable._cPos.showScrollBar)

			{
				newValue--;
			}

			_pTable.setPagingLocation(newValue, _pTable.getPagingLocation().y);
		} else if (e.getSource() != null && e.getSource().equals(_columnScrollBar)) {
			int newValue = e.getValue();
			if (newValue == _pTable.getColumnCount() - _pTable.getPagingViewPosition().columnPageSize - 1) {
				newValue--;
			}

			_pTable.setPagingLocation(_pTable.getPagingLocation().x, newValue);
		} else {

		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == null || !evt.getSource().equals(_pTable)) {

			return;
		}

		if ("pagingPosition".equals(evt.getPropertyName())) {
			adjustScrollBars((PagingViewPosition) evt.getNewValue());
		} else if ("columnCount".equals(evt.getPropertyName())) {
			Integer newV = (Integer) evt.getNewValue();
			if (newV.intValue() != _lastColCount) {
				_lastColCount = newV.intValue();
				adjustScrollBars(_pTable.getPagingViewPosition());
			}
		} else if ("rowCount".equals(evt.getPropertyName())) {
			Integer newV = (Integer) evt.getNewValue();
			if (newV.intValue() != _lastRowCount) {
				_lastRowCount = newV.intValue();
				adjustScrollBars(_pTable.getPagingViewPosition());
			}
		}

		else if ("headerIcons".equals(evt.getPropertyName())) {
			int maxHeight = (new JLabel("test_text")).getPreferredSize().height;
			if (evt.getNewValue() != null) {
				Iterator it = ((Vector) evt.getNewValue()).iterator();
				while (it.hasNext()) {
					Icon icon = (Icon) it.next();
					if (icon != null && icon.getIconHeight() > maxHeight) {
						maxHeight = icon.getIconHeight();
					}
				}
			}
			Insets insets = UIManager.getBorder("TableHeader.cellBorder").getBorderInsets(_header);
			int borderHeight = insets.top + insets.bottom;
			Dimension d = _header.getPreferredSize();
			d.height = maxHeight + borderHeight;
			_header.setPreferredSize(d);
			d = _pTopRightPanel.getPreferredSize();
			d.height = maxHeight + borderHeight;
			_pTopRightPanel.setPreferredSize(d);
			_pTable.revalidate();
			_pRightPanel.revalidate();
			this.repaint();
		}

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (_pTable.isWheelScrollingEnabled() && e.getScrollAmount() != 0) {
			JScrollBar toScroll = _rowScrollBar;
			int direction = 0;

			if (toScroll == null || !toScroll.isVisible()) {
				return;
			}
			direction = e.getWheelRotation() < 0 ? -1 : 1;
			if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

				int delta;
				int units = e.getScrollAmount();
				for (int i = 0; i < units; i++) {
					if (direction > 0) {
						delta = toScroll.getUnitIncrement(direction);
					} else {
						delta = -toScroll.getUnitIncrement(direction);
					}

					int oldValue = toScroll.getValue();
					int newValue = oldValue + delta;

					if (delta > 0 && newValue < oldValue) {
						newValue = toScroll.getMaximum();
					} else if (delta < 0 && newValue > oldValue) {
						newValue = toScroll.getMinimum();
					}
					if (oldValue == newValue) {
						break;
					}
					toScroll.setValue(newValue);
				}
			}
		}
	}

	public JPagingTable getPagingTable() {
		return _pTable;
	}

	protected void adjustScrollBars(PagingViewPosition newPos) {

		if (newPos.rowPageSize < _pTable.getRowCount() || (_pTable._cPos.showScrollBar && _pTable.getRowCount() > 0)) {
			int newEx = (newPos.rowPageSize * _pTable.getRowCount()) / _pTable.getRowCount();
			_rowScrollBar.setVisible(true);

			_pRightPanel.setVisible(true);
			if (_pTable._cPos.showScrollBar)
				checkAndSetScrollbarValues(_rowScrollBar, newPos.currentRow, _pTable.getRowCount() - 1, 0, _pTable.getRowCount());
			else

				checkAndSetScrollbarValues(_rowScrollBar, newPos.currentRow, newEx, 0, _pTable.getRowCount());
		} else {
			_rowScrollBar.setVisible(false);

			_pRightPanel.setVisible(false);
			checkAndSetScrollbarValues(_rowScrollBar, 0, 0, 0, _pTable.getRowCount());
		}

		if (newPos.columnPageSize < _pTable.getColumnCount()) {
			int newEx = (newPos.columnPageSize * _pTable.getColumnCount()) / _pTable.getColumnCount();
			_columnScrollBar.setVisible(true);
			checkAndSetScrollbarValues(_columnScrollBar, newPos.currentColumn, newEx, 0, _pTable.getColumnCount());
		} else {
			_columnScrollBar.setVisible(false);
			checkAndSetScrollbarValues(_columnScrollBar, 0, 0, 0, _pTable.getColumnCount());
		}

		this.getLayout().layoutContainer(this);
		RepaintManager.currentManager(this).validateInvalidComponents();
	}

	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}

	public void setBounds(int x, int y, int width, int height) {
		int oldHeight = getBounds().height;
		super.setBounds(x, y, width, height);
		if (_pTable._isInsideJViewport) {
			if (height < oldHeight) {
				Rectangle r = _pTable.getBounds();
				r.height -= oldHeight - height;
				_pTable.setBounds(r);
				_pTable.revalidate();
			}
		}
	}

	private void checkAndSetScrollbarValues(JScrollBar sc, int newValue, int newExtent, int newMin, int newMax) {

		_stopAdjustmentListener = true;
		if (sc.getValue() != newValue || sc.getVisibleAmount() != newExtent || sc.getMinimum() != newMin || sc.getMaximum() != newMax) {
			sc.setValues(newValue, newExtent, newMin, newMax);

			_pTable.repaint();
		}
		_stopAdjustmentListener = false;

	}

	public static class PagingTableLeftPanel extends JPanel {
		private int _minimumWidth = -1;

		public PagingTableLeftPanel(LayoutManager layout) {
			super(layout);
		}

		public void setMinimumWidth(int minimumWidth) {
			_minimumWidth = minimumWidth;
		}

		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			if (_minimumWidth >= 0) {
				d.width = _minimumWidth;
			}
			return d;
		}
	}

	public void setBorder(Border border) {

	}

}
