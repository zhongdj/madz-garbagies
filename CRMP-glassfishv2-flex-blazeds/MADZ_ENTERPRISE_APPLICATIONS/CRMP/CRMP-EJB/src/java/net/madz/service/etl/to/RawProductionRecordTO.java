package net.madz.service.etl.to;

import net.madz.module.production.entity.ProductionRecordBO;
import net.madz.service.etl.annotation.EntityMappingDescriptor;
import net.madz.service.etl.annotation.EntityMappingDescriptors;
import net.madz.service.etl.annotation.FieldMappingDescriptor;
import net.madz.service.etl.annotation.FieldMappingDescriptor.Type;
import net.madz.service.etl.annotation.FieldMappingDescriptors;
import net.madz.service.etl.strategy.MechandiseSetMappingStrategy;
import net.madz.standard.StandardTO;

@SuppressWarnings("rawtypes")
@EntityMappingDescriptors({ @EntityMappingDescriptor(mapToEntity = ProductionRecordBO.class, rawDescriptionField = "description") })
public class RawProductionRecordTO extends StandardTO {

	private static final long serialVersionUID = 1L;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "description") })
	private String description;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "productionTime") })
	private String productionTime;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "quantity") })
	private String outputVolumn;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "referenceNumber") })
	private String referenceNumber;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "productCode") })
	private String productCode;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "truckNumber", unsupport = false) })
	private String truckNumber;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "projectOwnerName", unsupport = false) })
	private String projectOwnerName;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Field, mapToField = "unitOfProjectOwnerName", unsupport = false) })
	private String unitOfProjectOwnerName;
	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Relationship, mappingKey = "Production.ProductionRecord.unitOfProject.id", mapToField = "unitOfProject", unspecifiedProviderMethod = "contractFacade.getUnspecifiedUnitOfProject") })
	private String unitOfProjectName;

	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Relationship, mappingKey = "Production.ProductionRecord.part.id", mapToField = "part", unspecifiedProviderMethod = "contractFacade.getUnspecifiedConstructionPart") })
	private String constructionPartName;

	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Relationship, mappingKey = "Production.ProductionRecord.mixingPlant.id", mapToField = "mixingPlant", unspecifiedProviderMethod = "productionFacade.getUnspecifiedConcreteMixingPlant") })
	private String mixingPlantName;

	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Relationship, mappingKey = "Production.ProductionRecord.unitOfMeasure.id", mapToField = "uom", unspecifiedProviderMethod = "commonFacade.getUnspecifiedUnitOfMeasure") })
	private String unitOfMeasureName;

	@FieldMappingDescriptors({ @FieldMappingDescriptor(entityClass = ProductionRecordBO.class, type = Type.Relationship, mappingKey = "Production.ProductionRecord.merchandiseSet", mapToField = "merchandises", mappingStrategyClass = MechandiseSetMappingStrategy.class, unspecifiedProviderMethod = "commonFacade.getUnspecifiedMerchandise") })
	private String recipeName;

	public String getConstructionPartName() {
		return constructionPartName;
	}

	public String getDescription() {
		return description;
	}

	public String getMixingPlantName() {
		return mixingPlantName;
	}

	public String getOutputVolumn() {
		return outputVolumn;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductionTime() {
		return productionTime;
	}

	public String getProjectOwnerName() {
		return projectOwnerName;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public String getTruckNumber() {
		return truckNumber;
	}

	public String getUnitOfMeasureName() {
		return unitOfMeasureName;
	}

	public String getUnitOfProjectName() {
		return unitOfProjectName;
	}

	public String getUnitOfProjectOwnerName() {
		return unitOfProjectOwnerName;
	}

	public void setConstructionPartName(String constructionPartName) {
		this.constructionPartName = constructionPartName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMixingPlantName(String mixingPlantName) {
		this.mixingPlantName = mixingPlantName;
	}

	public void setOutputVolumn(String outputVolumn) {
		this.outputVolumn = outputVolumn;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductionTime(String productionTime) {
		this.productionTime = productionTime;
	}

	public void setProjectOwnerName(String projectOwnerName) {
		this.projectOwnerName = projectOwnerName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}

	public void setUnitOfMeasureName(String unitOfMeasureName) {
		this.unitOfMeasureName = unitOfMeasureName;
	}

	public void setUnitOfProjectName(String unitOfProjectName) {
		this.unitOfProjectName = unitOfProjectName;
	}

	public void setUnitOfProjectOwnerName(String unitOfProjectOwnerName) {
		this.unitOfProjectOwnerName = unitOfProjectOwnerName;
	}

	@Override
	public String toString() {
		return "ProductionRecordImportTO [productionTime=" + productionTime + ", recipeName=" + recipeName + ", outputVolumn="
				+ outputVolumn + ", truckNumber=" + truckNumber + ", projectOwnerName=" + projectOwnerName + ", unitOfProjectName="
				+ unitOfProjectName + ", unitOfProjectOwnerName=" + unitOfProjectOwnerName + ", constructionPartName="
				+ constructionPartName + ", mixingPlantName=" + mixingPlantName + ", referenceNumber=" + referenceNumber + "]";
	}

}
