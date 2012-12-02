package net.madz.swing.view.util;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class GuiJComboBox extends JComboBox {
	private int _minWidth = -1;
	private int _minHeight = -1;
	private int _maxWidth = -1;
	private int _maxHeight = -1;

	public GuiJComboBox() {
		super();
	}

	public GuiJComboBox(ComboBoxModel aModel) {
		super(aModel);
	}

	public GuiJComboBox(Object[] items) {
		super(items);
	}

	public GuiJComboBox(Vector items) {
		super(items);
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
		Dimension dim = super.getMinimumSize();
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
		Dimension dim = super.getPreferredSize();
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
		Dimension dim = super.getMaximumSize();
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
}
