package net.madz.swing.components.table;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxCellRender implements TableCellRenderer {

	final JCheckBox box = new JCheckBox();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if ("true".equalsIgnoreCase(String.valueOf(value))) {
			box.setSelected(true);
		} else {
			box.setSelected(false);
		}
		return box;
	}

}
