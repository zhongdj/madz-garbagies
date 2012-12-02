/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.unknown.entity;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_refund", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "Refund.findAll", query = "SELECT c FROM Refund c"),
		@NamedQuery(name = "Refund.findById", query = "SELECT c FROM Refund c WHERE c.id = :id"),
		@NamedQuery(name = "Refund.findByState", query = "SELECT c FROM Refund c WHERE c.state = :state"),
		@NamedQuery(name = "Refund.findByTotalAmount", query = "SELECT c FROM Refund c WHERE c.totalAmount = :totalAmount"),
		@NamedQuery(name = "Refund.findByCreatedOn", query = "SELECT c FROM Refund c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "Refund.findByUpdatedOn", query = "SELECT c FROM Refund c WHERE c.updatedOn = :updatedOn") })
public class Refund extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "state", nullable = false, length = 20)
	private String state;
	@Column(name = "total_amount", precision = 18, scale = 0)
	private Double totalAmount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "refundId", fetch = FetchType.LAZY)
	private Set<RefundPaymentInvoice> crmpRefundPaymentInvoiceSet;

	public Refund() {
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<RefundPaymentInvoice> getRefundPaymentInvoiceSet() {
		return crmpRefundPaymentInvoiceSet;
	}

	public void setRefundPaymentInvoiceSet(Set<RefundPaymentInvoice> crmpRefundPaymentInvoiceSet) {
		this.crmpRefundPaymentInvoiceSet = crmpRefundPaymentInvoiceSet;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.Refund[id=" + getId() + "]";
	}

}
