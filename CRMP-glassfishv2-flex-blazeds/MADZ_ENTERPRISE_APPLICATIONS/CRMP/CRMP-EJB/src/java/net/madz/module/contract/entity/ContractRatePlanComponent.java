/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.contract.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.unknown.entity.AmendmentItem;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_contract_rate_plan_component", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "ContractRatePlanComponent.findAll", query = "SELECT c FROM ContractRatePlanComponent c"),
		@NamedQuery(name = "ContractRatePlanComponent.findById", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.id = :id"),
		@NamedQuery(name = "ContractRatePlanComponent.findByChargeType", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.chargeType = :chargeType"),
		@NamedQuery(name = "ContractRatePlanComponent.findByChargeModel", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.chargeModel = :chargeModel"),
		@NamedQuery(name = "ContractRatePlanComponent.findByChargeRate", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.chargeRate = :chargeRate"),
		@NamedQuery(name = "ContractRatePlanComponent.findByActive", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.active = :active"),
		@NamedQuery(name = "ContractRatePlanComponent.findByCreatedOn", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "ContractRatePlanComponent.findByUpdatedOn", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "ContractRatePlanComponent.findByDeleted", query = "SELECT c FROM ContractRatePlanComponent c WHERE c.deleted = :deleted") })
public class ContractRatePlanComponent extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "CHARGE_TYPE", nullable = false, length = 20)
	private String chargeType = "按使用量计费"; // per usage

	@Basic(optional = false)
	@Column(name = "CHARGE_MODEL", nullable = false, length = 20)
	private String chargeModel = "按计量单位计费"; // per unit

	@Column(name = "CHARGE_RATE", precision = 18, scale = 0)
	private Double chargeRate;

	@Basic(optional = false)
	@Column(name = "ACTIVE", nullable = false)
	private boolean active;

	@JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ContractBO contract;

	@JoinColumn(name = "MERCHANDISE_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Merchandise merchandise;

	@JoinColumn(name = "UNIT_OF_MEASURE_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private UnitOfMeasure unitOfMeasure;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "amendedRatePlanComponentId", fetch = FetchType.LAZY)
	private Set<AmendmentItem> crmpAmendmentItemSet;

	public ContractRatePlanComponent() {
	}

	public ContractRatePlanComponent(String chargeType, String chargeModel, boolean active) {
		this.chargeType = chargeType;
		this.chargeModel = chargeModel;
		this.active = active;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getChargeModel() {
		return chargeModel;
	}

	public void setChargeModel(String chargeModel) {
		this.chargeModel = chargeModel;
	}

	public Double getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(Double chargeRate) {
		this.chargeRate = chargeRate;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<AmendmentItem> getAmendmentItemSet() {
		return crmpAmendmentItemSet;
	}

	public void setAmendmentItemSet(Set<AmendmentItem> crmpAmendmentItemSet) {
		this.crmpAmendmentItemSet = crmpAmendmentItemSet;
	}

	public ContractBO getContract() {
		return contract;
	}

	public void setContract(ContractBO contract) {
		if (null == contract) {
			if (null != this.contract) {
				this.contract.removeContractRatePlanComponent(this);
			}
			//this.contract = null;
			return;
		}
		this.contract = contract;
		this.contract.addContractRatePlanComponent(this);
	}

	public Merchandise getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}

	public UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Set<AmendmentItem> getCrmpAmendmentItemSet() {
		return crmpAmendmentItemSet;
	}

	public void setCrmpAmendmentItemSet(Set<AmendmentItem> crmpAmendmentItemSet) {
		this.crmpAmendmentItemSet = crmpAmendmentItemSet;
	}

	@Override
	public String toString() {
		return "ContractRatePlanComponent [chargeType=" + chargeType + ", chargeModel=" + chargeModel + ", chargeRate=" + chargeRate
				+ ", active=" + active + ", merchandise=" + merchandise + ", unitOfMeasure=" + unitOfMeasure + ", crmpAmendmentItemSet="
				+ crmpAmendmentItemSet + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", deleted=" + deleted + ", tenant="
				+ tenant + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", id=" + id + "]";
	}

}
