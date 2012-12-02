/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.infra.security.persistence.Tenant;

@Entity
@Table(name = "crmp_product")
@NamedQueries({
		@NamedQuery(name = "Merchandise.findAll", query = "SELECT c FROM Merchandise c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "Merchandise.findById", query = "SELECT c FROM Merchandise c WHERE c.id = :id AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "Merchandise.findByName", query = "SELECT c FROM Merchandise c WHERE c.name = :name AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "Merchandise.findByDescription", query = "SELECT c FROM Merchandise c WHERE c.description = :description AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "Merchandise.findByCreatedOn", query = "SELECT c FROM Merchandise c WHERE c.createdOn = :createdOn AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "Merchandise.findByUpdatedOn", query = "SELECT c FROM Merchandise c WHERE c.updatedOn = :updatedOn AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "Merchandise.findByDeleted", query = "SELECT c FROM Merchandise c WHERE c.deleted = :deleted AND  c.tenant.id = :tenantId") })
public class Merchandise extends StandardObject {

	private static final long serialVersionUID = 1L;

	// We tend to use enum. Add discard original Category.
	public enum CategoryEnum {
		CONCRETE, ADDITIVE, UNSPECIFIED
	}

	@Column(insertable = true, updatable = true, nullable = false)
	private String name;

	@Column(insertable = true, updatable = true, nullable = false)
	private String code;
	private Double suggestPrice;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private UnitOfMeasure uom;

	@Basic(optional = false)
	@Column(name = "CATEGORY", length = 20)
	@Enumerated(EnumType.ORDINAL)
	private CategoryEnum category;

	public Merchandise() {
	}

	public Merchandise(String name, String code, CategoryEnum category, Tenant company) {
		setName(name);
		setCode(code);
		setCategory(category);
	}

	public CategoryEnum getCategory() {
		return category;
	}

	public void setCategory(CategoryEnum category) {
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Double suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Merchandise)) {
			return false;
		}
		Merchandise other = (Merchandise) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + ":[id = " + id + ", name = " + name + ", description = " + description + ", category = " + category
				+ ", code = " + code + "]";
	}
}
