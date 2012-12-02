package net.madz.module.billing;

import java.io.Serializable;

public class InvoiceStatixTO implements Serializable {

	private static final long serialVersionUID = 6890017480431373339L;
	public String unitOfProjectName;
	public Double quantity;
	public Long count;
	public String productCode;
	public Double amount;

	public String getUnitOfProjectName() {
		return unitOfProjectName;
	}

	public void setUnitOfProjectName(String unitOfProjectName) {
		this.unitOfProjectName = unitOfProjectName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "InvoiceStatixTO [unitOfProjectName=" + unitOfProjectName + ", quantity=" + quantity + ", count=" + count + ", productCode="
				+ productCode + ", amount=" + amount + "]";
	}

}
