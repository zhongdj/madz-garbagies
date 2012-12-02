package net.madz.swing.view.util;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class SplitPaneUIWithHiddenDivider extends BasicSplitPaneUI {

	private int _dividerSize = 7; // by default

	public SplitPaneUIWithHiddenDivider(int dividerSize) {
		super();
		if (dividerSize >= 1) {
			_dividerSize = dividerSize;
		}
	}

	public BasicSplitPaneDivider createDefaultDivider() {
		return new HiddenSplitPaneDivider(this, _dividerSize);
	}

	private class HiddenSplitPaneDivider extends BasicSplitPaneDivider {

		private Border _border = BorderFactory.createEmptyBorder();
		private int _size;

		public HiddenSplitPaneDivider(BasicSplitPaneUI sp, int size) {
			super(sp);
			_size = size;
		}

		public Border getBorder() {
			return _border;
		}

		public int getDividerSize() {
			return _size;
		}
	}
}
