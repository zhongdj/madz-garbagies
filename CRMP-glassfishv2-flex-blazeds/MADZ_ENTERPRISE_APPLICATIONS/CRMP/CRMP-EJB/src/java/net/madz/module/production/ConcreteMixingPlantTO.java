package net.madz.module.production;

import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class ConcreteMixingPlantTO extends StandardTO<ConcreteMixingPlant> {
	private static final long serialVersionUID = 1L;
	private String name;
	@Binding(name = "operator.id")
	private String operatorId;
	@Binding(name = "operator.fullName")
	private String operatorName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Override
	public String toString() {
		return "ConcreteMixingPlantTO [name=" + name + ", operatorId=" + operatorId + ", operatorName=" + operatorName + "]";
	}

}
