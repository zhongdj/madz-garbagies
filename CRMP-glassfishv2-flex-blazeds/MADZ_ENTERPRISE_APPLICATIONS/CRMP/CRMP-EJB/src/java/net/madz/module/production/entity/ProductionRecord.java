/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.production.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.core.state.annotation.StateIndicator;
import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.contract.entity.ConstructionPart;
import net.madz.module.contract.entity.UnitOfProject;

/**
 * 
 * @author a
 */
@MappedSuperclass
@NamedQueries({
		@NamedQuery(name = "ProductionRecord.findAll", query = "SELECT c FROM ProductionRecordBO c"),
		@NamedQuery(name = "ProductionRecord.findById", query = "SELECT c FROM ProductionRecordBO c WHERE c.id = :id"),
		@NamedQuery(name = "ProductionRecord.findByState", query = "SELECT c FROM ProductionRecordBO c WHERE c.state = :state"),
		@NamedQuery(name = "ProductionRecord.findByReferenceNumber", query = "SELECT c FROM ProductionRecordBO c WHERE c.referenceNumber = :referenceNumber"),
		@NamedQuery(name = "ProductionRecord.findByCreatedOn", query = "SELECT c FROM ProductionRecordBO c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "ProductionRecord.findByUpdatedOn", query = "SELECT c FROM ProductionRecordBO c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "ProductionRecord.findByDeleted", query = "SELECT c FROM ProductionRecordBO c WHERE c.deleted = :deleted") })
public class ProductionRecord extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "STATE", nullable = false)
	@StateIndicator
	protected String state;
	@Column(name = "REFERENCE_NUMBER", length = 32)
	protected String referenceNumber;
	@Column(name = "QUANTITY", nullable = false)
	protected Double quantity;
	@Column(name = "PRODUCTION_TIME", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date productionTime;

	@Column(name = "DESCRIPTION", length = 2000)
	protected String description;

	@Column(name = "TRUCK_NUMBER", length = 20)
	protected String truckNumber;

	@Column(name = "PROJECT_OWNER_NAME", length = 40)
	protected String projectOwnerName;

	@Column(name = "UNIT_OF_PROJECT_OWNER_NAME", length = 40)
	protected String unitOfProjectOwnerName;

	// @Column(name = "GROUP_INDICATOR", length = 40)
	// protected String groupIndicator;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected UnitOfProject unitOfProject;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected ConstructionPart part;

	@JoinColumn(name = "MIXING_PLANT_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	protected ConcreteMixingPlant mixingPlant;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<Merchandise> merchandises;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected UnitOfMeasure uom;

	@Column(name = "PRODUCT_CODE")
	protected String productCode;

	public ProductionRecord() {
	}

	public void addMerchandise(Merchandise merchandise) {
		if (null == this.merchandises) {
			this.merchandises = new ArrayList<Merchandise>();
		}
		this.merchandises.add(merchandise);
	}

	public String getDescription() {
		return description;
	}

	public List<Merchandise> getMerchandiseSet() {
		return merchandises;
	}

	public ConcreteMixingPlant getMixingPlant() {
		return mixingPlant;
	}

	public ConstructionPart getPart() {
		return part;
	}

	public String getProductCode() {
		return productCode;
	}

	public Date getProductionTime() {
		return productionTime;
	}

	public String getProjectOwnerName() {
		return projectOwnerName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public String getState() {
		return state;
	}

	public String getTruckNumber() {
		return truckNumber;
	}

	public UnitOfProject getUnitOfProject() {
		return unitOfProject;
	}

	// public String getGroupIndicator() {
	// return groupIndicator;
	// }
	//
	// public void setGroupIndicator(String groupIndicator) {
	// this.groupIndicator = groupIndicator;
	// }

	public String getUnitOfProjectOwnerName() {
		return unitOfProjectOwnerName;
	}

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMixingPlant(ConcreteMixingPlant mixingPlant) {
		this.mixingPlant = mixingPlant;
	}

	public void setPart(ConstructionPart part) {
		this.part = part;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductionTime(Date productionTime) {
		this.productionTime = productionTime;
	}

	public void setProjectOwnerName(String projectOwnerName) {
		this.projectOwnerName = projectOwnerName;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setTruckNumber(String truckNumber) {
		this.truckNumber = truckNumber;
	}

	public void setUnitOfProject(UnitOfProject unitOfProject) {
		this.unitOfProject = unitOfProject;
	}

	public void setUnitOfProjectOwnerName(String unitOfProjectOwnerName) {
		this.unitOfProjectOwnerName = unitOfProjectOwnerName;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}

	@Override
	public String toString() {
		return "ProductionRecord [state=" + state + ", referenceNumber=" + referenceNumber + ", quantity=" + quantity + ", productionTime="
				+ productionTime + ", description=" + description + ", truckNumber=" + truckNumber + ", projectOwnerName="
				+ projectOwnerName + ", unitOfProjectOwnerName=" + unitOfProjectOwnerName + ", unitOfProject=" + unitOfProject + ", part="
				+ part + ", mixingPlant=" + mixingPlant + ", merchandiseSet=" + merchandises + ", uom=" + uom + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + ", deleted=" + deleted + ", tenant=" + tenant + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", id=" + id + "]";
	}

}
