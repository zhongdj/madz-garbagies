/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.production.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.infra.security.persistence.User;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_concrete_mixing_plant", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "ConcreteMixingPlant.findAll", query = "SELECT c FROM ConcreteMixingPlant c"),
		@NamedQuery(name = "ConcreteMixingPlant.findById", query = "SELECT c FROM ConcreteMixingPlant c WHERE c.id = :id"),
		@NamedQuery(name = "ConcreteMixingPlant.findByName", query = "SELECT c FROM ConcreteMixingPlant c WHERE c.name = :name"),
		@NamedQuery(name = "ConcreteMixingPlant.findByCreatedOn", query = "SELECT c FROM ConcreteMixingPlant c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "ConcreteMixingPlant.findByUpdatedOn", query = "SELECT c FROM ConcreteMixingPlant c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "ConcreteMixingPlant.findByDeleted", query = "SELECT c FROM ConcreteMixingPlant c WHERE c.deleted = :deleted") })
public class ConcreteMixingPlant extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 40)
	private String name;

	@OneToMany(mappedBy = "mixingPlant", fetch = FetchType.LAZY)
	private Set<ProductionRecordBO> crmpProductionRecordSet;
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private User operator;

	public ConcreteMixingPlant() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProductionRecordBO> getProductionRecordSet() {
		return crmpProductionRecordSet;
	}

	public void setProductionRecordSet(Set<ProductionRecordBO> crmpProductionRecordSet) {
		this.crmpProductionRecordSet = crmpProductionRecordSet;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "ConcreteMixingPlant [name=" + name + ", crmpProductionRecordSet=" + crmpProductionRecordSet + ", operator=" + operator
				+ "]";
	}

}
