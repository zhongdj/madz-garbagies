package net.madz.swing.wizard.displayable;

import net.madz.swing.wizard.WizardMessage;

public class DefaultSyncStatusDisplayable implements ISyncStatusDisplayable {

	private String id;
	private String name;
	private String importIndicatorText = "";
	private String syncIndicatorText = "";
	private String state = WizardMessage.PENDING_STATE;
	private int importQuantity;
	private int syncQuantity;

	public DefaultSyncStatusDisplayable(String name) {
		super();
		this.name = name;
	}

	public DefaultSyncStatusDisplayable(IMixingPlantDisplayable plant) {
		id = plant.getPlantId();
		name = plant.getPlantName();
		importIndicatorText = plant.getLocalImportIndicatorText();
		syncIndicatorText = plant.getRemoteSyncIndicatorText();
	}

	@Override
	public String getPlantId() {
		return id;
	}

	@Override
	public String getPlantName() {
		return name;
	}

	@Override
	public String getLocalImportIndicatorText() {
		return importIndicatorText;
	}

	@Override
	public String getRemoteSyncIndicatorText() {
		return syncIndicatorText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImportIndicatorText() {
		return importIndicatorText;
	}

	public void setImportIndicatorText(String importIndicatorText) {
		this.importIndicatorText = importIndicatorText;
	}

	public String getSyncIndicatorText() {
		return syncIndicatorText;
	}

	public void setSyncIndicatorText(String syncIndicatorText) {
		this.syncIndicatorText = syncIndicatorText;
	}

	public int getImportQuantity() {
		return importQuantity;
	}

	public void setImportQuantity(int importQuantity) {
		this.importQuantity = importQuantity;
	}

	public int getSyncQuantity() {
		return syncQuantity;
	}

	public void setSyncQuantity(int syncQuantity) {
		this.syncQuantity = syncQuantity;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int getImportedQuantity() {
		return importQuantity;
	}

	@Override
	public int getSyncedQuantity() {
		return syncQuantity;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setLocalImportIndicatorText(String text) {
		this.importIndicatorText = text;
	}

	@Override
	public void setRemoteSyncIndicatorText(String text) {
		this.syncIndicatorText = text;
	}

	@Override
	public void setImportedQuantity(int quantity) {
		this.importQuantity = quantity;
	}

	@Override
	public void setSyncedQuantity(int quantity) {
		this.syncQuantity = quantity;
	}

}
