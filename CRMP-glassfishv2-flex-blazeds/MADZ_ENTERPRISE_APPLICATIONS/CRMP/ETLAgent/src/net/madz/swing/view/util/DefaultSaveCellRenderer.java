package net.madz.swing.view.util;

public class DefaultSaveCellRenderer implements SaveCellRenderer {

	public String getObjectAsString(Object object, int rowIndex, int columnIndex) {
		if (object == null) {
			return "null";
		} else {
			return object.toString();
		}
	}
}
