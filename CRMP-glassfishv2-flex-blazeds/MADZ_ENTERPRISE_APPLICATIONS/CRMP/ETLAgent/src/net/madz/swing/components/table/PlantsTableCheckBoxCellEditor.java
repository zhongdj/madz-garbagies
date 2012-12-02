package net.madz.swing.components.table;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import net.madz.swing.wizard.model.PlantsTableModel;

public class PlantsTableCheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor, ItemListener {

	private static final long serialVersionUID = 7123535962589354904L;
	private final JCheckBox box = new JCheckBox();
	private PlantsTableModel plantsModel;

	public PlantsTableCheckBoxCellEditor() {
		super();
		box.setEnabled(true);
		box.setFocusable(true);
	}

	public PlantsTableCheckBoxCellEditor(PlantsTableModel plantsModel) {
		this();
		this.plantsModel = plantsModel;
	}

	@Override
	public Object getCellEditorValue() {
		return box.isSelected();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, final int row, final int column) {
		if (0 == column) {
			if ("true".equalsIgnoreCase(String.valueOf(value))) {
				box.setSelected(true);
			} else {
				box.setSelected(false);
			}

			box.addItemListener(this);
			return box;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}

}
