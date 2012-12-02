package net.madz.module.common.to.query;

import java.io.Serializable;

import net.madz.module.common.to.update.MerchandiseUTO;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class MerchandiseQTO extends StandardTO implements Serializable {

	private static final long serialVersionUID = -1060321953130709657L;
	private String code;
	private String name;
	private String description;
	private Double suggestPrice;
	private String categoryName;
	@Binding(name = "uom.id")
	private String unitOfMeasureId;
	@Binding(name = "uom.name")
	private String unitOfMeasureName;

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSuggestPrice(Double suggestValue) {
		this.suggestPrice = suggestValue;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getSuggestPrice() {
		return suggestPrice;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getCode() {
		return code;
	}

	public String getUnitOfMeasureId() {
		return unitOfMeasureId;
	}

	public void setUnitOfMeasureId(String unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}

	public String getUnitOfMeasureName() {
		return unitOfMeasureName;
	}

	public void setUnitOfMeasureName(String unitOfMeasureName) {
		this.unitOfMeasureName = unitOfMeasureName;
	}

	public MerchandiseUTO valueofMerchandiseUTO() {
		MerchandiseUTO uto = new MerchandiseUTO();
		uto.setId(getId());
		uto.setName(name);
		uto.setCode(code);
		uto.setDescription(description);
		uto.setSuggestPrice(suggestPrice);
		return uto;
	}

	@Override
	public String toString() {
		return getClass().getName() + ":[id = " + getId() + ", name = " + name + ", description = " + description + ", categoryName = "
				+ categoryName + ", code = " + code + "]";
	}
}
