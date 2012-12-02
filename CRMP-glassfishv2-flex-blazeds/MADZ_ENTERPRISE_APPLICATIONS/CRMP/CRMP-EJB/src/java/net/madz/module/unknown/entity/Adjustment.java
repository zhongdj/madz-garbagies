/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.unknown.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.billing.entity.AdjustmentInvoice;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_adjustment", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "Adjustment.findAll", query = "SELECT c FROM Adjustment c"),
		@NamedQuery(name = "Adjustment.findById", query = "SELECT c FROM Adjustment c WHERE c.id = :id"),
		@NamedQuery(name = "Adjustment.findByState", query = "SELECT c FROM Adjustment c WHERE c.state = :state"),
		@NamedQuery(name = "Adjustment.findByCreatedOn", query = "SELECT c FROM Adjustment c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "Adjustment.findByUpdatedOn", query = "SELECT c FROM Adjustment c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "Adjustment.findByDeleted", query = "SELECT c FROM Adjustment c WHERE c.deleted = :deleted") })
public class Adjustment extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Column(name = "state", length = 20)
	private String state;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adjustmentId", fetch = FetchType.LAZY)
	private Set<AdjustmentInvoice> adjustmentInvoiceSet;

	public Adjustment() {
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<AdjustmentInvoice> getAdjustmentInvoiceSet() {
		return adjustmentInvoiceSet;
	}

	public void setAdjustmentInvoiceSet(Set<AdjustmentInvoice> crmpAdjustmentInvoiceSet) {
		this.adjustmentInvoiceSet = crmpAdjustmentInvoiceSet;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.Adjustment[id=" + getId() + "]";
	}
}
