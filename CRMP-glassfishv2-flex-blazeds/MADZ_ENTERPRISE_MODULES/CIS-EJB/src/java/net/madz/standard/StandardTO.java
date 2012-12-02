package net.madz.standard;

import java.io.Serializable;
import java.util.Date;

import net.madz.infra.security.persistence.StandardObject;
import net.vicp.madz.infra.binding.annotation.Binding;

public class StandardTO<E extends StandardObject> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;
	@Binding(name = "createdBy.username")
	protected String createdByName;
	protected Date createdOn;
	@Binding(name = "updatedBy.username")
	protected String updatedByName;
	protected Date updatedOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
