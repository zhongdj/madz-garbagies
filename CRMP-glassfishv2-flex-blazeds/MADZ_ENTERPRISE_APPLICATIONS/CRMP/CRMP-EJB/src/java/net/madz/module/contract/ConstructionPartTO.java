package net.madz.module.contract;

import net.madz.module.contract.entity.ConstructionPart;
import net.madz.standard.StandardTO;

public class ConstructionPartTO extends StandardTO<ConstructionPart> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ConstructionPartTO [name=" + name + ", description=" + description + "]";
	}

}
