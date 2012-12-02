package net.madz.swing.view.util;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar {
	public ProgressBar(BoundedRangeModel fModel) {
		super(fModel);
	}

	public ProgressBar() {
		super();
	}

	public ProgressBar(int orient) {
		super(orient);
	}

	public ProgressBar(int min, int max) {
		super(min, max);
	}

	public ProgressBar(int orient, int min, int max) {
		super(orient, min, max);
	}
}
