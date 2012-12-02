/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.unknown.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.infra.biz.entity.AbstractBaseEntity;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.persistence.User;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_state_change_log", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "StateChangeLog.findAll", query = "SELECT c FROM StateChangeLog c"),
		@NamedQuery(name = "StateChangeLog.findById", query = "SELECT c FROM StateChangeLog c WHERE c.id = :id"),
		@NamedQuery(name = "StateChangeLog.findByDataId", query = "SELECT c FROM StateChangeLog c WHERE c.dataId = :dataId"),
		@NamedQuery(name = "StateChangeLog.findByDataType", query = "SELECT c FROM StateChangeLog c WHERE c.dataType = :dataType"),
		@NamedQuery(name = "StateChangeLog.findBySourceState", query = "SELECT c FROM StateChangeLog c WHERE c.sourceState = :sourceState"),
		@NamedQuery(name = "StateChangeLog.findByTargetStateName", query = "SELECT c FROM StateChangeLog c WHERE c.targetStateName = :targetStateName"),
		@NamedQuery(name = "StateChangeLog.findByFinalState", query = "SELECT c FROM StateChangeLog c WHERE c.finalState = :finalState"),
		@NamedQuery(name = "StateChangeLog.findByCreateOn", query = "SELECT c FROM StateChangeLog c WHERE c.createOn = :createOn") })
public class StateChangeLog extends AbstractBaseEntity {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "data_id", nullable = false, length = 32)
	private String dataId;
	@Basic(optional = false)
	@Column(name = "data_type", nullable = false, length = 40)
	private String dataType;
	@Basic(optional = false)
	@Column(name = "source_state", nullable = false, length = 20)
	private String sourceState;
	@Basic(optional = false)
	@Column(name = "target_state_name", nullable = false, length = 20)
	private String targetStateName;
	@Column(name = "final_State")
	private Boolean finalState;
	@Column(name = "create_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createOn;
	@JoinColumn(name = "tenant_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Tenant tenantId;
	@JoinColumn(name = "created_by", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User createdBy;

	public StateChangeLog() {
	}

	public StateChangeLog(String dataId, String dataType, String sourceState, String targetStateName) {
		this.dataId = dataId;
		this.dataType = dataType;
		this.sourceState = sourceState;
		this.targetStateName = targetStateName;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSourceState() {
		return sourceState;
	}

	public void setSourceState(String sourceState) {
		this.sourceState = sourceState;
	}

	public String getTargetStateName() {
		return targetStateName;
	}

	public void setTargetStateName(String targetStateName) {
		this.targetStateName = targetStateName;
	}

	public Boolean getFinalState() {
		return finalState;
	}

	public void setFinalState(Boolean finalState) {
		this.finalState = finalState;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Tenant getTenantId() {
		return tenantId;
	}

	public void setTenantId(Tenant tenantId) {
		this.tenantId = tenantId;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "StateChangeLog [dataId=" + dataId + ", dataType=" + dataType + ", sourceState=" + sourceState + ", targetStateName="
				+ targetStateName + ", finalState=" + finalState + ", createOn=" + createOn + ", tenantId=" + tenantId + ", createdBy="
				+ createdBy + "]";
	}

}
