package net.madz.module.production;

import java.io.Serializable;

public class ProductionRecordStatixTO implements Serializable {

	private static final long serialVersionUID = 6151011601994629505L;

	public String unitOfProjectName;
	public Double quantity;
	public Long count;
	public String productCode;

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

	@Override
	public String toString() {
		return "ProductionRecordStatixTO [unitOfProjectName=" + unitOfProjectName + ", quantity=" + quantity + ", count=" + count
				+ ", productCode=" + productCode + "]";
	}

}
