/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.unknown.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.contract.entity.ContractRatePlanComponent;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_amendment_item", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "AmendmentItem.findAll", query = "SELECT c FROM AmendmentItem c"),
		@NamedQuery(name = "AmendmentItem.findById", query = "SELECT c FROM AmendmentItem c WHERE c.id = :id"),
		@NamedQuery(name = "AmendmentItem.findByState", query = "SELECT c FROM AmendmentItem c WHERE c.state = :state"),
		@NamedQuery(name = "AmendmentItem.findByOldChargeType", query = "SELECT c FROM AmendmentItem c WHERE c.oldChargeType = :oldChargeType"),
		@NamedQuery(name = "AmendmentItem.findByOldChargeModel", query = "SELECT c FROM AmendmentItem c WHERE c.oldChargeModel = :oldChargeModel"),
		@NamedQuery(name = "AmendmentItem.findByOldChargeRate", query = "SELECT c FROM AmendmentItem c WHERE c.oldChargeRate = :oldChargeRate"),
		@NamedQuery(name = "AmendmentItem.findByNewChargeType", query = "SELECT c FROM AmendmentItem c WHERE c.newChargeType = :newChargeType"),
		@NamedQuery(name = "AmendmentItem.findByNewChargeModel", query = "SELECT c FROM AmendmentItem c WHERE c.newChargeModel = :newChargeModel"),
		@NamedQuery(name = "AmendmentItem.findByNewChargeRate", query = "SELECT c FROM AmendmentItem c WHERE c.newChargeRate = :newChargeRate"),
		@NamedQuery(name = "AmendmentItem.findByCreatedOn", query = "SELECT c FROM AmendmentItem c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "AmendmentItem.findByUpdatedOn", query = "SELECT c FROM AmendmentItem c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "AmendmentItem.findByDeleted", query = "SELECT c FROM AmendmentItem c WHERE c.deleted = :deleted") })
public class AmendmentItem extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Column(name = "state", length = 20)
	private String state;
	@Column(name = "old_charge_type", length = 20)
	private String oldChargeType;
	@Column(name = "old_charge_model", length = 20)
	private String oldChargeModel;
	@Column(name = "old_charge_rate", precision = 18, scale = 0)
	private Double oldChargeRate;
	@Column(name = "new_charge_type", length = 20)
	private String newChargeType;
	@Column(name = "new_charge_model", length = 20)
	private String newChargeModel;
	@Column(name = "new_charge_rate", precision = 18, scale = 0)
	private Double newChargeRate;
	@JoinColumn(name = "amended_rate_plan_component_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ContractRatePlanComponent amendedRatePlanComponentId;

	public AmendmentItem() {
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOldChargeType() {
		return oldChargeType;
	}

	public void setOldChargeType(String oldChargeType) {
		this.oldChargeType = oldChargeType;
	}

	public String getOldChargeModel() {
		return oldChargeModel;
	}

	public void setOldChargeModel(String oldChargeModel) {
		this.oldChargeModel = oldChargeModel;
	}

	public Double getOldChargeRate() {
		return oldChargeRate;
	}

	public void setOldChargeRate(Double oldChargeRate) {
		this.oldChargeRate = oldChargeRate;
	}

	public String getNewChargeType() {
		return newChargeType;
	}

	public void setNewChargeType(String newChargeType) {
		this.newChargeType = newChargeType;
	}

	public String getNewChargeModel() {
		return newChargeModel;
	}

	public void setNewChargeModel(String newChargeModel) {
		this.newChargeModel = newChargeModel;
	}

	public Double getNewChargeRate() {
		return newChargeRate;
	}

	public void setNewChargeRate(Double newChargeRate) {
		this.newChargeRate = newChargeRate;
	}

	public ContractRatePlanComponent getAmendedRatePlanComponentId() {
		return amendedRatePlanComponentId;
	}

	public void setAmendedRatePlanComponentId(ContractRatePlanComponent amendedRatePlanComponentId) {
		this.amendedRatePlanComponentId = amendedRatePlanComponentId;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.AmendmentItem[id=" + getId() + "]";
	}
}
