/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.contract.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.core.state.annotation.StateIndicator;
import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.account.entity.Account;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.unknown.entity.Amendment;

/**
 * 
 * @author a
 */
@MappedSuperclass
@NamedQueries({
		@NamedQuery(name = "Contract.findAll", query = "SELECT c FROM ContractBO c"),
		@NamedQuery(name = "Contract.findById", query = "SELECT c FROM ContractBO c WHERE c.id = :id"),
		@NamedQuery(name = "Contract.findByName", query = "SELECT c FROM ContractBO c WHERE c.name = :name"),
		@NamedQuery(name = "Contract.findByState", query = "SELECT c FROM ContractBO c WHERE c.state = :state"),
		@NamedQuery(name = "Contract.findByEffectiveStartDate", query = "SELECT c FROM ContractBO c WHERE c.effectiveStartDate = :effectiveStartDate"),
		@NamedQuery(name = "Contract.findByEffectiveEndDate", query = "SELECT c FROM ContractBO c WHERE c.effectiveEndDate = :effectiveEndDate"),
		@NamedQuery(name = "Contract.findByPaymentTerm", query = "SELECT c FROM ContractBO c WHERE c.paymentTerm = :paymentTerm"),
		@NamedQuery(name = "Contract.findByActive", query = "SELECT c FROM ContractBO c WHERE c.active = :active"),
		@NamedQuery(name = "Contract.findByCreatedOn", query = "SELECT c FROM ContractBO c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "Contract.findByUpdatedOn", query = "SELECT c FROM ContractBO c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "Contract.findByDeleted", query = "SELECT c FROM ContractBO c WHERE c.deleted = :deleted") })
public class Contract extends StandardObject {
	public static enum BillPeriodBaselineUnit {
		YEAR, MONTH, DAY, UNSPECIFIED
	}

	public static enum PaymentTerm {
		// Paid on receipt
		//
		ON_RECEIPT,
		// Paid in 7 days
		NET7,
		// Paid in 15 days
		//
		NET15,
		// Paid in 30 days
		//
		NET30,
		// Paid in 90 days
		//
		NET90,
		//
		//
		UNSPECIFIED
	}

	public static enum BillBaselineCombination {
		// Bill run depends on Period baseline only
		//
		PERIOD_ONLY,
		// Bill run depends on Volume baseline only
		//
		VOLUME_ONLY,
		// Bill run depends on Period or Volume baseline
		// For example:
		// Period: 30 days
		// Volume: 1000
		// Bill run requirement: Period >=30 days or Volume >= 1000
		//
		PERIOD_OR_VOLUME,
		// Bill run depends on Period and Volume baseline
		// For example:
		// Period: 30 days
		// Volume: 1000
		// Bill run requirement: Period >=30days and Volume >=1000
		//
		PERIOD_AND_VOLUME
	}

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "NAME", nullable = false, length = 50)
	protected String name;

	@Basic(optional = false)
	@Column(name = "STATE", nullable = false, length = 20)
	@StateIndicator
	protected String state;

	// The Date that customer sign the contract.
	//
	@Column(name = "CUSTOMER_ACCEPTANCE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date customerAcceptanceDate;

	// The Date that the contract takes effect.
	//
	@Column(name = "EFFECTIVE_START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date effectiveStartDate;

	@Column(name = "EFFECTIVE_END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date effectiveEndDate;

	@Basic(optional = false)
	@Column(name = "PAYMENT_TERM", nullable = false, length = 20)
	protected String paymentTerm;

	@Basic(optional = false)
	@Column(name = "BILL_VOLUME_BASELINE", length = 20)
	protected int billVolumeBaseline;

	@Basic(optional = false)
	@Column(name = "BILL_PERIOD_BASELINE", length = 20)
	protected int billPeriodBaseline;

	@Basic(optional = false)
	@Column(name = "BILL_PERIOD_BASELINE_UNIT", length = 20)
	protected String billPeriodBaselineUnit;

	@Column(name = "BILL_BASELINE_COMBINATION", length = 20)
	protected String billBaselineCombination;

	@Basic(optional = false)
	@Column(name = "ACTIVE", nullable = false)
	protected boolean active;

	@Basic(optional = false)
	@Column(name = "VERSION", nullable = false)
	protected int version;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "BILL_VOLUME_BASELINE_UNIT", referencedColumnName = "ID")
	protected UnitOfMeasure billVolumeBaselineUnit;

	// @JoinColumn(name = "original_contract_id", referencedColumnName = "ID")
	// @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	// protected ContractBO originalContract;

	@OneToMany(mappedBy = "contract", cascade = { CascadeType.REFRESH, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	protected final List<UnitOfProject> unitOfProjects = new ArrayList<UnitOfProject>();

	@JoinColumn(name = "PAYER_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	protected Account payerAccount;

	@JoinColumn(name = "BILL_TO_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	protected Account billToAccount;

	@JoinColumn(name = "SOLD_TO_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	protected Account soldToAccount;

	@JoinColumn(name = "SHIP_TO_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	protected Account shipToAccount;

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST }, mappedBy = "contract", fetch = FetchType.LAZY)
	protected final Set<Amendment> amendmentSet = new HashSet<Amendment>();

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST }, mappedBy = "contract", fetch = FetchType.EAGER)
	protected final Set<ContractRatePlanComponent> contractRatePlanComponentSet = new HashSet<ContractRatePlanComponent>();

	@Deprecated
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contract", fetch = FetchType.EAGER)
	protected final Set<PartMerchandisePair> partMerchandisePairSet = new HashSet<PartMerchandisePair>();

	public Contract() {
	}

	public Contract(String name, String state, String paymentTerm, boolean active) {
		this.name = name;
		this.state = state;
		this.paymentTerm = paymentTerm;
		this.active = active;
	}

	protected void addAmendment(Amendment amendment) {
		this.amendmentSet.add(amendment);
	}

	protected void addContractRatePlanComponent(ContractRatePlanComponent component) {
		this.contractRatePlanComponentSet.add(component);
	}

	protected void addPartMerchandisePair(PartMerchandisePair pair) {
		this.partMerchandisePairSet.add(pair);
	}

	public boolean getActive() {
		return active;
	}

	public Set<Amendment> getAmendmentSet() {
		return amendmentSet;
	}

	public int getBillPeriodBaseline() {
		return billPeriodBaseline;
	}

	public UnitOfMeasure getBillVolumeBaselineUnit() {
		return billVolumeBaselineUnit;
	}

	public void setBillVolumeBaselineUnit(UnitOfMeasure billVolumeBaselineUnit) {
		this.billVolumeBaselineUnit = billVolumeBaselineUnit;
	}

	public BillPeriodBaselineUnit getBillPeriodBaselineUnit() {
		return BillPeriodBaselineUnit.valueOf(billPeriodBaselineUnit);
	}

	public Account getBillToAccount() {
		return billToAccount;
	}

	public int getBillVolumeBaseline() {
		return billVolumeBaseline;
	}

	public Set<ContractRatePlanComponent> getContractRatePlanComponentSet() {
		return contractRatePlanComponentSet;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public String getName() {
		return name;
	}

	// public ContractBO getOriginalContract() {
	// return originalContract;
	// }

	public Set<PartMerchandisePair> getPartMerchandisePairSet() {
		return partMerchandisePairSet;
	}

	public Account getPayerAccount() {
		return payerAccount;
	}

	public PaymentTerm getPaymentTerm() {
		return PaymentTerm.valueOf(paymentTerm);
	}

	public Account getShipToAccount() {
		return shipToAccount;
	}

	public Account getSoldToAccount() {
		return soldToAccount;
	}

	public String getState() {
		return state;
	}

	public List<UnitOfProject> getUnitOfProjects() {
		return unitOfProjects;
	}

	public int getVersion() {
		return version;
	}

	protected void removeAmendment(Amendment amendment) {
		this.amendmentSet.remove(amendment);
	}

	protected void removeContractRatePlanComponent(ContractRatePlanComponent component) {
		this.contractRatePlanComponentSet.remove(component);
	}

	protected void removePartMerchandisePair(PartMerchandisePair pair) {
		this.partMerchandisePairSet.remove(pair);
	}

	protected void removeUnitOfProject(UnitOfProject unitOfProject) {
		if (null != unitOfProject) {
			this.unitOfProjects.remove(unitOfProject);
		}
	}

	public Date getCustomerAcceptanceDate() {
		return customerAcceptanceDate;
	}

	public void setCustomerAcceptanceDate(Date customerAcceptanceDate) {
		this.customerAcceptanceDate = customerAcceptanceDate;
	}

	public String getBillBaselineCombination() {
		return billBaselineCombination;
	}

	public void setBillBaselineCombination(String billBaselineCombination) {
		this.billBaselineCombination = BillBaselineCombination.valueOf(billBaselineCombination).name();
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public void setBillPeriodBaselineUnit(String billPeriodBaselineUnit) {
		this.billPeriodBaselineUnit = billPeriodBaselineUnit;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setBillPeriodBaseline(int billPeriodBaseline) {
		this.billPeriodBaseline = billPeriodBaseline;
	}

	public void setBillPeriodBaselineUnit(BillPeriodBaselineUnit billPeriodBaselineUnit) {
		this.billPeriodBaselineUnit = billPeriodBaselineUnit.name();
	}

	public void setBillToAccount(Account billToAccount) {
		this.billToAccount = billToAccount;
	}

	public void setBillVolumnBaseline(int billVolumnBaseline) {
		this.billVolumeBaseline = billVolumnBaseline;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public void setOriginalContract(ContractBO originalContract) {
	// this.originalContract = originalContract;
	// }

	public void setPayerAccount(Account payerAccount) {
		this.payerAccount = payerAccount;
	}

	public void setPaymentTerm(PaymentTerm paymentTerm) {
		this.paymentTerm = paymentTerm.name();
	}

	public void setShipToAccount(Account shipToAccount) {
		this.shipToAccount = shipToAccount;
	}

	public void setSoldToAccount(Account soldToAccount) {
		this.soldToAccount = soldToAccount;
	}

	protected void addUnitOfProject(UnitOfProject unitOfProject) {
		if (!this.unitOfProjects.contains(unitOfProject)) {
			this.unitOfProjects.add(unitOfProject);
		}
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Contract [name=" + name + ", state=" + state + ", effectiveStartDate=" + effectiveStartDate + ", effectiveEndDate="
				+ effectiveEndDate + ", paymentTerm=" + paymentTerm + ", billVolumnBaseline=" + billVolumeBaseline
				+ ", billPeriodBaseline=" + billPeriodBaseline + ", billPeriodBaselineUnit=" + billPeriodBaselineUnit + ", active="
				+ active + ", version=" + version
				+ ", billVolumnBaselineUnit="
				+ billVolumeBaselineUnit
				// + ", originalContract=" + originalContract
				+ ", unitOfProjects=" + unitOfProjects + ", payerAccount=" + payerAccount + ", billToAccount=" + billToAccount
				+ ", soldToAccount=" + soldToAccount + ", shipToAccount=" + shipToAccount + ", amendmentSet=" + amendmentSet
				+ ", contractRatePlanComponentSet=" + contractRatePlanComponentSet + ", partMerchandisePairSet=" + partMerchandisePairSet
				+ "]";
	}

}
