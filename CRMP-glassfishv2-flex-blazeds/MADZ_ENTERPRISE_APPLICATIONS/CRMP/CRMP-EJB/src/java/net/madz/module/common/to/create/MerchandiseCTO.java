package net.madz.module.common.to.create;

import java.io.Serializable;

import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;

public class MerchandiseCTO implements Serializable, IServiceArgument {

	private static final long serialVersionUID = -7371768138134790226L;
	protected String code;
	protected String name;
	protected String description;
	protected Double suggestPrice;
	protected String category;
	protected String unitOfMeasureId;

	public Double getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Double suggestValue) {
		this.suggestPrice = suggestValue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCode() {
		return code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUnitOfMeasureId() {
		return unitOfMeasureId;
	}

	public void setUnitOfMeasureId(String unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}

	public void validate() throws ValidationException {
		if ((getName() == null) || (getName().trim().length() <= 0)) {
			throw new ValidationException("Merchandise name Cannot be empty!");
		}
		if ((getCode() == null) || (getCode().trim().length() <= 0)) {
			throw new ValidationException("Merchandise description Cannot be empty!");
		}
//		if (getCategory() == null) {
//			throw new ValidationException("Merchandise category Cannot be empty");
//		}
	}

	@Override
	public String getArgumentName() {
		return name;
	}

	@Override
	public String toString() {
		return getClass().getName() + ":[name = " + name + ", description = " + description + ", categoryID = " + category + ", code = "
				+ code + "]";
	}

}
