package net.madz.swing.view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class GuiJScrolledList extends JScrollPane {
	private int _minWidth = -1;
	private int _minHeight = -1;
	private int _maxWidth = -1;
	private int _maxHeight = -1;
	private JList _jList = null;

	public GuiJScrolledList() {
		_jList = new JList();
		getViewport().setView(_jList);
	}

	public GuiJScrolledList(ListModel dataModel) {
		_jList = new JList(dataModel);
		getViewport().setView(_jList);
	}

	public GuiJScrolledList(Object[] listData) {
		_jList = new JList(listData);
		getViewport().setView(_jList);
	}

	public GuiJScrolledList(Vector listData) {
		_jList = new JList(listData);
		getViewport().setView(_jList);
	}

	public JList getJList() {
		return _jList;
	}

	public Dimension getPreferredScrollableViewportSize() {
		Dimension result = _jList.getPreferredScrollableViewportSize();
		return result;
	}

	public Dimension getSize(Dimension rv) {
		rv = _jList.getSize(rv);
		return rv;
	}

	public void setVisibleRowCount(int visibleRowCount) {
		_jList.setVisibleRowCount(visibleRowCount);
	}

	public void setMinimumWidth(int value) {
		_minWidth = value;
		updateAllSizes();
	};

	public void setMinimumHeight(int value) {
		_minHeight = value;
		updateAllSizes();
	};

	public void setMaximumWidth(int value) {
		_maxWidth = value;
		updateAllSizes();
	};

	public void setMaximumHeight(int value) {
		_maxHeight = value;
		updateAllSizes();
	};

	public void setMinimumSize(Dimension minimumSize) {
		int w = minimumSize.width;
		int h = minimumSize.height;

		if ((_minWidth != -1) && (_minWidth > w))
			w = _minWidth;
		if ((_minHeight != -1) && (_minHeight > h))
			h = _minHeight;
		super.setMinimumSize(new Dimension(w, h));
	};

	public Dimension getMinimumSize() {
		Dimension dim = _jList.getMinimumSize();
		int nbLine = _jList.getVisibleRowCount();
		int heightLine = _jList.getFixedCellHeight();
		if (heightLine <= -1)
			heightLine = 16;
		int newHeight = heightLine * nbLine;
		dim.setSize(dim.width, newHeight);

		if (dim != null) {
			int w = dim.width;
			int h = dim.height;

			if ((_minWidth != -1) && (_minWidth > w))
				w = _minWidth;
			if ((_minHeight != -1) && (_minHeight > h))
				h = _minHeight;

			dim.setSize(w, h);
		}
		return dim;
	};

	public void setPreferredSize(Dimension preferredSize) {
		int w = preferredSize.width;
		int h = preferredSize.height;

		if ((_minWidth != -1) && (_minWidth > w))
			w = _minWidth;
		if ((_minHeight != -1) && (_minHeight > h))
			h = _minHeight;
		if ((_maxWidth != -1) && (_maxWidth < w))
			w = _maxWidth;
		if ((_maxHeight != -1) && (_maxHeight < h))
			h = _maxHeight;

		super.setPreferredSize(new Dimension(w, h));
	};

	public Dimension getPreferredSize(Dimension preferredSize) {
		Dimension dim = _jList.getPreferredSize();
		int nbLine = _jList.getVisibleRowCount();
		int heightLine = _jList.getFixedCellHeight();
		if (heightLine <= -1)
			heightLine = 16;
		int newHeight = heightLine * nbLine;
		dim.setSize(dim.width, newHeight);

		if (dim != null) {
			int w = dim.width;
			int h = dim.height;

			if ((_minWidth != -1) && (_minWidth > w))
				w = _minWidth;
			if ((_minHeight != -1) && (_minHeight > h))
				h = _minHeight;
			if ((_maxWidth != -1) && (_maxWidth < w))
				w = _maxWidth;
			if ((_maxHeight != -1) && (_maxHeight < h))
				h = _maxHeight;

			dim.setSize(w, h);
		}
		return dim;
	};

	public void setMaximumSize(Dimension maximumSize) {
		int w = maximumSize.width;
		int h = maximumSize.height;

		if ((_maxWidth != -1) && (_maxWidth < w))
			w = _maxWidth;
		if ((_maxHeight != -1) && (_maxHeight < h))
			h = _maxHeight;
		super.setMaximumSize(new Dimension(w, h));
	};

	public Dimension getMaximumSize() {
		Dimension dim = _jList.getMaximumSize();
		if (dim != null) {
			int w = dim.width;
			int h = dim.height;

			if ((_maxWidth != -1) && (_maxWidth < w))
				w = _maxWidth;
			if ((_maxHeight != -1) && (_maxHeight < h))
				h = _maxHeight;

			dim.setSize(w, h);
		}
		return dim;
	};

	private void updateAllSizes() {
		setMinimumSize(getMinimumSize());
		setMaximumSize(getMaximumSize());
		setPreferredSize(getPreferredSize());
	};

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (_jList != null)
			_jList.setEnabled(enabled);
	}

	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (_jList != null)
			_jList.setBackground(bg);
	}

	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (_jList != null)
			_jList.setForeground(fg);
	}

}