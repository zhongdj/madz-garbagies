package net.madz.swing.view.util;

import java.util.EventObject;
import java.util.Vector;

public class IdentifiersSelectionEvent extends EventObject {

	private Vector _oldSelection = null;

	private Vector _newSelection = null;

	public IdentifiersSelectionEvent(Object source, Vector oldSelection,
			Vector newSelection) {
		super(source);
		_oldSelection = oldSelection;
		_newSelection = newSelection;
	}

	public Vector getNewSelection() {
		return _newSelection;
	}

	public Vector getOldSelection() {
		return _oldSelection;
	}

	public String toString() {
		String properties = " source=" + getSource() + " oldSelection= "
				+ _oldSelection.toString() + " newSelection= "
				+ _newSelection.toString() + " ";
		return getClass().getName() + "[" + properties + "]";
	}
}