package net.madz.swing.wizard.displayable;

public interface ISyncStatusDisplayable {

	public static final int COLUMNS = 6;

	String getPlantId();

	String getPlantName();

	String getLocalImportIndicatorText();

	String getRemoteSyncIndicatorText();

	String getState();

	int getImportedQuantity();

	int getSyncedQuantity();

	void setLocalImportIndicatorText(String text);

	void setRemoteSyncIndicatorText(String text);

	void setState(String state);

	void setImportedQuantity(int quantity);

	void setSyncedQuantity(int quantity);
}
