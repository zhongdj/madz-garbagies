package net.madz.module.contract;

import java.util.Date;
import java.util.List;

import net.madz.module.account.AccountTO;
import net.madz.module.contract.entity.ContractBO;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class ContractTO extends StandardTO<ContractBO> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String state;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	private String paymentTerm;
	private int billVolumnBaseline;
	private int billPeriodBaseline;
	private String billPeriodBaselineUnit;
	private boolean active;
	protected String billBaselineCombination;

	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = AccountTO.class, name = "payerAccount")
	private AccountTO payerAccount;
	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = AccountTO.class, name = "billToAccount")
	private AccountTO billToAccount;
	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = AccountTO.class, name = "soldToAccount")
	private AccountTO soldToAccount;
	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = AccountTO.class, name = "shipToAccount")
	private AccountTO shipToAccount;
	@Binding(name = "billVolumeBaselineUnit.id")
	private String billVolumeBaselineUnitId;
	@Binding(name = "billVolumeBaselineUnit.name")
	private String billVolumeBaselineUnitName;
	// @Binding(name = "originalContract.id")
	// private String originalContractId;
	// @Binding(name = "originalContract.name")
	// private String originalContractName;

	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = UnitOfProjectTO.class)
	private List<UnitOfProjectTO> unitOfProjects;

	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContractRatePlanComponentTO.class, name = "contractRatePlanComponentSet")
	private List<ContractRatePlanComponentTO> ratePlanComponents;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getBillMethod() {
		return paymentTerm;
	}

	public void setBillMethod(String billMethod) {
		this.paymentTerm = billMethod;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getBillBaselineCombination() {
		return billBaselineCombination;
	}
	
	public void setBillBaselineCombination(String billBaselineCombination) {
		this.billBaselineCombination = billBaselineCombination;
	}

	public AccountTO getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(AccountTO payerAccount) {
		this.payerAccount = payerAccount;
	}

	public AccountTO getBillToAccount() {
		return billToAccount;
	}

	public void setBillToAccount(AccountTO billToAccount) {
		this.billToAccount = billToAccount;
	}

	public AccountTO getSoldToAccount() {
		return soldToAccount;
	}

	public void setSoldToAccount(AccountTO soldToAccount) {
		this.soldToAccount = soldToAccount;
	}

	public AccountTO getShipToAccount() {
		return shipToAccount;
	}

	public void setShipToAccount(AccountTO shipToAccount) {
		this.shipToAccount = shipToAccount;
	}

	// public String getOriginalContractId() {
	// return originalContractId;
	// }
	//
	// public void setOriginalContractId(String originalContractId) {
	// this.originalContractId = originalContractId;
	// }
	//
	// public String getOriginalContractName() {
	// return originalContractName;
	// }
	//
	// public void setOriginalContractName(String originalContractName) {
	// this.originalContractName = originalContractName;
	// }

	public List<UnitOfProjectTO> getUnitOfProjects() {
		return unitOfProjects;
	}

	public void setUnitOfProjects(List<UnitOfProjectTO> unitOfProjects) {
		this.unitOfProjects = unitOfProjects;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public int getBillVolumnBaseline() {
		return billVolumnBaseline;
	}

	public void setBillVolumnBaseline(int billVolumnBaseline) {
		this.billVolumnBaseline = billVolumnBaseline;
	}

	public int getBillPeriodBaseline() {
		return billPeriodBaseline;
	}

	public void setBillPeriodBaseline(int billPeriodBaseline) {
		this.billPeriodBaseline = billPeriodBaseline;
	}

	public String getBillPeriodBaselineUnit() {
		return billPeriodBaselineUnit;
	}

	public void setBillPeriodBaselineUnit(String billPeriodBaselineUnit) {
		this.billPeriodBaselineUnit = billPeriodBaselineUnit;
	}

	public String getBillVolumnBaselineUnitId() {
		return billVolumeBaselineUnitId;
	}

	public void setBillVolumeBaselineUnitId(String billVolumeBaselineUnitId) {
		this.billVolumeBaselineUnitId = billVolumeBaselineUnitId;
	}

	public String getBillVolumeBaselineUnitName() {
		return billVolumeBaselineUnitName;
	}

	public void setBillVolumeBaselineUnitName(String billVolumeBaselineUnitName) {
		this.billVolumeBaselineUnitName = billVolumeBaselineUnitName;
	}

	public List<ContractRatePlanComponentTO> getRatePlanComponents() {
		return ratePlanComponents;
	}

	public void setRatePlanComponents(List<ContractRatePlanComponentTO> ratePlanComponents) {
		this.ratePlanComponents = ratePlanComponents;
	}

	@Override
	public String toString() {
		return "ContractTO [name=" + name + ", state=" + state + ", effectiveStartDate=" + effectiveStartDate + ", effectiveEndDate="
				+ effectiveEndDate + ", paymentTerm=" + paymentTerm + ", billVolumnBaseline=" + billVolumnBaseline
				+ ", billPeriodBaseline=" + billPeriodBaseline + ", billPeriodBaselineUnit=" + billPeriodBaselineUnit + ", active="
				+ active + ", payerAccount=" + payerAccount + ", billToAccount=" + billToAccount + ", soldToAccount=" + soldToAccount
				+ ", shipToAccount=" + shipToAccount + ", billVolumnBaselineUnitId=" + billVolumeBaselineUnitId
				+ ", billVolumnBaselineUnitName=" + billVolumeBaselineUnitName
				// + ", originalContractId=" + originalContractId
				// + ", originalContractName=" + originalContractName
				+ ", unitOfProjects=" + unitOfProjects + "]";
	}

}
