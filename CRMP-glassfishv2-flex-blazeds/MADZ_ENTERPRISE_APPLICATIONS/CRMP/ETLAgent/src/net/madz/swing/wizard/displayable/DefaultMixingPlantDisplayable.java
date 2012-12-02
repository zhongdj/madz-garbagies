package net.madz.swing.wizard.displayable;

import net.madz.module.production.ConcreteMixingPlantTO;

public class DefaultMixingPlantDisplayable implements IMixingPlantDisplayable {

	private boolean selected;
	private String name;
	private String id;

	public DefaultMixingPlantDisplayable(String name) {
		super();
		this.name = name;
		selected = false;
	}

	public DefaultMixingPlantDisplayable(ConcreteMixingPlantTO plant) {
		this.id = plant.getId();
		this.name = plant.getName();
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
		return "2010-06-30 10:34:08";
	}

	@Override
	public String getRemoteSyncIndicatorText() {
		return "2010-06-29 05:34:08";
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
