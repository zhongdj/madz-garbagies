package net.madz.swing.view.util;

import java.util.EventObject;

public class ItemsMotionEvent extends EventObject {
	public static final int SINGLE_UPLOAD = MovingItemsPanel.SINGLE_UPLOAD;
	public static final int SINGLE_DOWNLOAD = MovingItemsPanel.SINGLE_DOWNLOAD;
	public static final int ALL_UPLOAD = MovingItemsPanel.ALL_UPLOAD;
	public static final int ALL_DOWNLOAD = MovingItemsPanel.ALL_DOWNLOAD;

	int _movement;

	public ItemsMotionEvent(Object fSource, int fMovementType) {
		super(fSource);
		_movement = fMovementType;
	}

	public int getMovement() {
		return _movement;
	}

	public String toString() {
		String lRes = super.toString() + "movement = ";

		if (_movement == SINGLE_DOWNLOAD)
			lRes += "selection download";
		else if (_movement == SINGLE_UPLOAD)
			lRes += "selection upload";
		else if (_movement == ALL_DOWNLOAD)
			lRes += "all download";
		else if (_movement == ALL_UPLOAD)
			lRes += "all upload";
		else
			lRes += " forbidden";

		return lRes;
	}
}