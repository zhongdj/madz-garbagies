package net.madz.module.contract;

import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.common.to.query.UnitOfMeasureQTO;
import net.madz.module.contract.entity.ContractRatePlanComponent;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class ContractRatePlanComponentTO extends StandardTO<ContractRatePlanComponent> {

	private static final long serialVersionUID = 1L;
	private String chargeType;
	private String chargeModel;
	private Double chargeRate;
	private boolean active;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContractTO.class)
	private ContractTO contract;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = MerchandiseQTO.class)
	private MerchandiseQTO merchandise;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = UnitOfMeasureQTO.class)
	private UnitOfMeasureQTO unitOfMeasure;

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ContractTO getContract() {
		return contract;
	}

	public void setContract(ContractTO contract) {
		this.contract = contract;
	}

	public MerchandiseQTO getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(MerchandiseQTO merchandise) {
		this.merchandise = merchandise;
	}

	public UnitOfMeasureQTO getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(UnitOfMeasureQTO unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

}
