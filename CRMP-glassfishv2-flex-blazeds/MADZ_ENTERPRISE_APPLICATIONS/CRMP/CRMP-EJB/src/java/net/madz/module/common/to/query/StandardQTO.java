package net.madz.module.common.to.query;

import java.io.Serializable;
import java.util.Date;

import net.vicp.madz.infra.binding.annotation.Binding;

public class StandardQTO implements Serializable {

	private static final long serialVersionUID = -2122623137928582107L;
	protected String id;
	@Binding(name = "updatedBy.username")
	protected String updatedBy;
	protected Date updatedOn;
	@Binding(name = "createdBy.username")
	protected String createdBy;
	protected Date createdOn;
	protected Boolean deleted = false;

	public StandardQTO() {

	}

	public StandardQTO(String updatedBy, Date updatedOn, String createdBy, Date createdOn, Boolean deleted) {
		super();
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.deleted = deleted;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
