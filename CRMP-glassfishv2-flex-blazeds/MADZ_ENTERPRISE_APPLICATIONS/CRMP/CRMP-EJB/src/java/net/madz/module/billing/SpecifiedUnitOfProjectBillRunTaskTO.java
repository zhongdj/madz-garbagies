package net.madz.module.billing;

import net.madz.module.billing.entity.SpecifiedUnitOfProjectBillRunTask;
import net.vicp.madz.infra.binding.annotation.Binding;

public class SpecifiedUnitOfProjectBillRunTaskTO extends BillRunTaskTO<SpecifiedUnitOfProjectBillRunTask> {

	private static final long serialVersionUID = 2215810575665225155L;

	@Binding(name = "specifiedUnitOfProject.id")
	private String specifiedUnitOfProjectId;
	@Binding(name = "specifiedUnitOfProject.name")
	private String specifiedUnitOfProjectName;

	public String getSpecifiedUnitOfProjectId() {
		return specifiedUnitOfProjectId;
	}

	public void setSpecifiedUnitOfProjectId(String specifiedUnitOfProjectId) {
		this.specifiedUnitOfProjectId = specifiedUnitOfProjectId;
	}

	public String getSpecifiedUnitOfProjectName() {
		return specifiedUnitOfProjectName;
	}

	public void setSpecifiedUnitOfProjectName(String specifiedUnitOfProjectName) {
		this.specifiedUnitOfProjectName = specifiedUnitOfProjectName;
	}

	@Override
	public String toString() {
		return "SpecifiedUnitOfProjectBillRunTaskTO [specifiedUnitOfProjectId=" + specifiedUnitOfProjectId
				+ ", specifiedUnitOfProjectName=" + specifiedUnitOfProjectName + "]";
	}

}
