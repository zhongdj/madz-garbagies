package net.madz.swing.wizard.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import net.madz.etl.engine.ISyncListener.SyncContext;
import net.madz.etl.engine.ISyncListener.SyncPhaseEnum;
import net.madz.swing.wizard.WizardMessage;
import net.madz.swing.wizard.displayable.ISyncStatusDisplayable;

public class SyncStatusTableModel extends AbstractTableModel implements
		TableModel {

	private static final long serialVersionUID = 2626330498708870972L;

	private volatile List<ISyncStatusDisplayable> statusList = new ArrayList<ISyncStatusDisplayable>();
	private volatile Map<String, ISyncStatusDisplayable> statusMap = new HashMap<String, ISyncStatusDisplayable>();

	public SyncStatusTableModel(
			Collection<ISyncStatusDisplayable> statusCollection) {
		initialize(statusCollection);
	}

	private synchronized void initialize(
			Collection<ISyncStatusDisplayable> statusCollection) {
		statusList.addAll(statusCollection);
		for (ISyncStatusDisplayable iSync : statusCollection) {
			statusMap.put(iSync.getPlantId(), iSync);
		}
	}

	@Override
	public synchronized int getRowCount() {
		return statusList.size();
	}

	@Override
	public int getColumnCount() {
		return ISyncStatusDisplayable.COLUMNS;
	}

	public Object getValueAt(int row, int column) {
		if (0 > row || statusList.size() <= row) {
			throw new ArrayIndexOutOfBoundsException();
		}
		final ISyncStatusDisplayable target = statusList.get(row);
		switch (column) {
		case 0:
			return target.getState();
		case 1:
			return target.getPlantName();
		case 2:
			return target.getImportedQuantity();
		case 3:
			return target.getLocalImportIndicatorText();
		case 4:
			return target.getSyncedQuantity();
		case 5:
			return target.getRemoteSyncIndicatorText();
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return WizardMessage.STATE_NAME_COLUMN_TITLE;
		case 1:
			return WizardMessage.STRING_PLANT_NAME_COLUMN_TITLE;
		case 2:
			return WizardMessage.IMPORT_QUANTITY_COLUMN_TITLE;
		case 3:
			return WizardMessage.STRING_IMPORT_INDICATOR_COLUMN_TITLE;
		case 4:
			return WizardMessage.SYNC_QUANTITY_COLUMN_TITLE;
		case 5:
			return WizardMessage.STRING_SYNC_INDICATOR_COLUMN_TITLE;
		default:
			return super.getColumnName(column);
		}
	}

	public synchronized void reload(
			Collection<ISyncStatusDisplayable> syncStatus) {
		statusList.clear();
		statusMap.clear();
		initialize(syncStatus);
	}

	// public synchronized void onSyncStatusUpdate(SyncContext context) {
	// final ISyncStatusDisplayable status = statusMap.get(context.plantId);
	// if (context.phase == SyncPhaseEnum.Importing) {
	// if (context.subPhase == SyncPhaseEnum.ImportEnd) {
	// status.setImportedQuantity(context.completedQuantity);
	// status.setLocalImportIndicatorText(context.indicatorText);
	// }
	// } else if (context.phase == SyncPhaseEnum.Syncing) {
	// if (context.subPhase == SyncPhaseEnum.SyncEnd) {
	// status.setRemoteSyncIndicatorText(context.indicatorText);
	// status.setSyncedQuantity(context.completedQuantity);
	// }
	// }
	// }

	public synchronized void onStart(String plantId) {
		final ISyncStatusDisplayable status = statusMap.get(plantId);
		status.setState(WizardMessage.STATE_STARTED);
		fireTableDataChanged();
	}

	public synchronized void onComplete(String plantId) {
		final ISyncStatusDisplayable status = statusMap.get(plantId);
		status.setState(WizardMessage.STATE_COMPLETED);
		fireTableDataChanged();
	}

	public void onFailed(SyncContext context) {
		final ISyncStatusDisplayable status = statusMap.get(context.plantId);
		status.setState(WizardMessage.MESSAGE_STATE_FAILED);
		fireTableDataChanged();
	}

	public void onSyncStatusUpdate(SyncContext context, boolean imported) {
		final ISyncStatusDisplayable status = statusMap.get(context.plantId);
		if (imported) {
			status.setImportedQuantity(context.completedQuantity);
			status.setLocalImportIndicatorText(context.indicatorText);
		} else {
			status.setRemoteSyncIndicatorText(context.indicatorText);
			status.setSyncedQuantity(context.completedQuantity);
		}
	}
}
