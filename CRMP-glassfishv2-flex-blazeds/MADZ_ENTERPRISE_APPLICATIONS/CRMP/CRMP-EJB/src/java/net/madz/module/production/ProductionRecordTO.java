package net.madz.module.production;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.production.entity.ProductionRecord;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class ProductionRecordTO extends StandardTO<ProductionRecord> {

	private static final long serialVersionUID = -188770064828461879L;

	private String state;
	private String description;
	private String referenceNumber;
	@Binding(name = "uom.id")
	private String uomId;
	@Binding(name = "uom.name")
	private String uomName;
	private double quantity;
	private Date productionTime;

	private String truckNumber;
	private String projectOwnerName;
	private String unitOfProjectOwnerName;

	@Binding(name = "unitOfProject.id")
	private String unitOfProjectId;
	@Binding(name = "unitOfProject.name")
	private String unitOfProjectName;
	@Binding(name = "mixingPlant.id")
	private String mixingPlantId;
	@Binding(name = "mixingPlant.name")
	private String mixingPlantName;
	@Binding(name = "part.id")
	private String partId;
	@Binding(name = "part.name")
	private String partName;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = MerchandiseQTO.class, name = "merchandises")
	private List<MerchandiseQTO> merchandises;

	public String getDescription() {
		return description;
	}

	public String getMixingPlantId() {
		return mixingPlantId;
	}

	public String getMixingPlantName() {
		return mixingPlantName;
	}

	public String getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}

	public Date getProductionTime() {
		return productionTime;
	}

	public double getQuantity() {
		return quantity;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public String getUnitOfProjectId() {
		return unitOfProjectId;
	}

	public String getUnitOfProjectName() {
		return unitOfProjectName;
	}

	public String getUomId() {
		return uomId;
	}

	public String getUomName() {
		return uomName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMixingPlantId(String mixingPlantId) {
		this.mixingPlantId = mixingPlantId;
	}

	public void setMixingPlantName(String mixingPlantName) {
		this.mixingPlantName = mixingPlantName;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public void setProductionTime(Date productionTime) {
		this.productionTime = productionTime;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setUnitOfProjectId(String unitOfProjectId) {
		this.unitOfProjectId = unitOfProjectId;
	}

	public void setUnitOfProjectName(String unitOfProjectName) {
		this.unitOfProjectName = unitOfProjectName;
	}

	public void setUomId(String uomId) {
		this.uomId = uomId;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public List<MerchandiseQTO> getMerchandise() {
		return merchandises;
	}

	public void addMerchandise(MerchandiseQTO merchandise) {
		if (null == this.merchandises) {
			this.merchandises = new ArrayList<MerchandiseQTO>();
		}
		this.merchandises.add(merchandise);
	}

	public String getTruckNumber() {
		return truckNumber;
	}

	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}

	public String getProjectOwnerName() {
		return projectOwnerName;
	}

	public void setProjectOwnerName(String projectOwnerName) {
		this.projectOwnerName = projectOwnerName;
	}

	public String getUnitOfProjectOwnerName() {
		return unitOfProjectOwnerName;
	}

	public void setUnitOfProjectOwnerName(String unitOfProjectOwnerName) {
		this.unitOfProjectOwnerName = unitOfProjectOwnerName;
	}

	@Override
	public String toString() {
		return "ProductionRecordTO [billed=" + state + ", referenceNumber=" + referenceNumber + ", uomId=" + uomId + ", uomName=" + uomName
				+ ", quantity=" + quantity + ", productionTime=" + productionTime + ", description=" + description + ", unitOfProjectId="
				+ unitOfProjectId + ", unitOfProjectName=" + unitOfProjectName + ", mixingPlantId=" + mixingPlantId + ", mixingPlantName="
				+ mixingPlantName + ", partId=" + partId + ", partName=" + partName + "]";
	}

}
