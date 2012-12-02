package net.madz.swing.wizard.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.madz.etl.engine.ISyncListener.SyncContext;
import net.madz.remote.ETLClientProxy;
import net.madz.swing.wizard.displayable.DefaultSyncStatusDisplayable;
import net.madz.swing.wizard.displayable.IMixingPlantDisplayable;
import net.madz.swing.wizard.displayable.ISyncStatusDisplayable;
import net.madz.swing.wizard.model.PlantsTableModel;
import net.madz.swing.wizard.model.SyncStatusTableModel;

public class ProductionRecordSyncWizardContext {

	private static final ProductionRecordSyncWizardContext INSTANCE = new ProductionRecordSyncWizardContext();

	public static ProductionRecordSyncWizardContext getInstance() {
		return INSTANCE;
	}

	private volatile PlantsTableModel plantTableModel;
	private volatile SyncStatusTableModel statusTableModel;

	synchronized void createPlantsTableModel(
			Collection<IMixingPlantDisplayable> plantsCollection) {
		if (null == plantsCollection) {
			throw new NullPointerException("");
		}

		if (null != plantTableModel) {
			throw new IllegalStateException(
					"Plant Table Model can be initialized only once.");
		}

		plantTableModel = new PlantsTableModel(plantsCollection);
	}

	public void createStatusTableModel() {
		if (null != statusTableModel) {
			throw new IllegalStateException();
		}
		createStatusTableModel(new ArrayList<ISyncStatusDisplayable>());
	}

	private synchronized void createStatusTableModel(
			Collection<ISyncStatusDisplayable> syncStatusCollection) {
		if (null == syncStatusCollection) {
			throw new NullPointerException("");
		}

		if (null != statusTableModel) {
			throw new IllegalStateException(
					"Sync Status Table Model can be initialized only once.");
		}

		statusTableModel = new SyncStatusTableModel(syncStatusCollection);
	}

	private Collection<ISyncStatusDisplayable> createSyncStatus() {

		final Collection<ISyncStatusDisplayable> plantsCollection = new ArrayList<ISyncStatusDisplayable>();
		final List<IMixingPlantDisplayable> plants = plantTableModel
				.getPlants();
		for (IMixingPlantDisplayable plant : plants) {
			if (plant.isSelected()) {
				plantsCollection.add(new DefaultSyncStatusDisplayable(plant));
			}
		}

		return plantsCollection;
	}

	public synchronized PlantsTableModel getPlantTableModel() {
		if (null == plantTableModel) {
			final Collection<IMixingPlantDisplayable> plantsCollection = ETLClientProxy
					.getInstance().findPlants();
			createPlantsTableModel(plantsCollection);
		}
		return plantTableModel;
	}

	public synchronized SyncStatusTableModel getSyncStatusTableModel() {
		if (null == statusTableModel) {
			createStatusTableModel();
		}
		return statusTableModel;
	}

	public void reloadStatusTableModel() {
		Collection<ISyncStatusDisplayable> syncStatus = createSyncStatus();
		statusTableModel.reload(syncStatus);
		statusTableModel.fireTableDataChanged();
	}

	public void onStart(String plantId) {
		statusTableModel.onStart(plantId);
	}

	public void onComplete(String plantId) {
		statusTableModel.onComplete(plantId);
	}

	public void onFailed(SyncContext context) {
		statusTableModel.onFailed(context);
	}

	public void onStatusUpdate(SyncContext context, boolean imported) {
		statusTableModel.onSyncStatusUpdate(context, imported);
	}

}
