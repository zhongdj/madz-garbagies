package net.madz.swing.wizard.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import net.madz.swing.wizard.WizardMessage;
import net.madz.swing.wizard.displayable.IMixingPlantDisplayable;

public class PlantsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 3412573751115527929L;

	private final List<IMixingPlantDisplayable> plants = new ArrayList<IMixingPlantDisplayable>();

	public PlantsTableModel(Collection<IMixingPlantDisplayable> plantsCollection) {
		this.plants.addAll(plantsCollection);
	}

	public List<IMixingPlantDisplayable> getPlants() {
		return plants;
	}

	@Override
	public int getColumnCount() {
		return IMixingPlantDisplayable.COLUMNS;
	}

	@Override
	public int getRowCount() {
		return plants.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (0 > row || plants.size() <= row) {
			throw new ArrayIndexOutOfBoundsException();
		}
		final IMixingPlantDisplayable target = plants.get(row);
		switch (column) {
		case 0:
			return target.isSelected();
		case 1:
			return target.getPlantName();
		case 2:
			return target.getLocalImportIndicatorText();
		case 3:
			return target.getRemoteSyncIndicatorText();
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (0 > rowIndex || plants.size() <= rowIndex) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (0 == columnIndex) {
			plants.get(rowIndex).setSelected((Boolean)aValue);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					fireTableDataChanged();
				}
			});
			return;
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (0 == columnIndex) {
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return WizardMessage.STRING_SELECT_COLUMN_TITLE;
		case 1:
			return WizardMessage.STRING_PLANT_NAME_COLUMN_TITLE;
		case 2:
			return WizardMessage.STRING_IMPORT_INDICATOR_COLUMN_TITLE;
		case 3:
			return WizardMessage.STRING_SYNC_INDICATOR_COLUMN_TITLE;
		default:
			return super.getColumnName(column);
		}
	}

	public boolean hasSelectedPlants() {
		for (IMixingPlantDisplayable plant : plants) {
			if (plant.isSelected()) {
				return true;
			}
		}
		return false;
	}

}
