package net.madz.swing.wizard.displayable;

public interface IMixingPlantDisplayable {

	public static final int COLUMNS = 4;

	String getPlantId();

	String getPlantName();

	String getLocalImportIndicatorText();

	String getRemoteSyncIndicatorText();

	boolean isSelected();

	void setSelected(boolean selected);
}
