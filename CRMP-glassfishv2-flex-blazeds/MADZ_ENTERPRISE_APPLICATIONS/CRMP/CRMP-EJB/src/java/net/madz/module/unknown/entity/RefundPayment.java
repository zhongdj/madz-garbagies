/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.unknown.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_refund_payment", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "RefundPayment.findAll", query = "SELECT c FROM RefundPayment c"),
		@NamedQuery(name = "RefundPayment.findById", query = "SELECT c FROM RefundPayment c WHERE c.id = :id"),
		@NamedQuery(name = "RefundPayment.findByApplyAmount", query = "SELECT c FROM RefundPayment c WHERE c.applyAmount = :applyAmount"),
		@NamedQuery(name = "RefundPayment.findByCreatedOn", query = "SELECT c FROM RefundPayment c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "RefundPayment.findByUpdatedOn", query = "SELECT c FROM RefundPayment c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "RefundPayment.findByDeleted", query = "SELECT c FROM RefundPayment c WHERE c.deleted = :deleted") })
public class RefundPayment extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Column(name = "apply_amount", precision = 18, scale = 0)
	private Double applyAmount;

	public RefundPayment() {
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.RefundPayment[id=" + getId() + "]";
	}

}
