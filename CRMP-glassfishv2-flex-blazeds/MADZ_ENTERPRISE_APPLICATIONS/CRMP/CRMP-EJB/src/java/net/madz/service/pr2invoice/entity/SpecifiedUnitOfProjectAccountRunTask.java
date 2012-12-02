package net.madz.service.pr2invoice.entity;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.module.contract.entity.UnitOfProject;

@Entity
@DiscriminatorValue("SINGLE")
@ReactiveObject
public class SpecifiedUnitOfProjectAccountRunTask extends AccountRunTask {

	private static final long serialVersionUID = 3845671217046217935L;
	// @@JoinColumn(name = "SPECIFIED_UNIT_OF_PROJECT_ID")
	@JoinColumn(name = "SPECIFIEDUNITOFPROJECT_ID")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	private UnitOfProject specifiedUnitOfProject;

	public UnitOfProject getSpecifiedUnitOfProject() {
		return specifiedUnitOfProject;
	}

	public void setSpecifiedUnitOfProject(UnitOfProject specifiedUnitOfProject) {
		this.specifiedUnitOfProject = specifiedUnitOfProject;
	}

	@Override
	public String toString() {
		return "SingleAccountRunTask [specifiedUnitOfProject=" + specifiedUnitOfProject + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", deleted=" + deleted + ", tenant=" + tenant + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", id=" + id + "]";
	}

}
