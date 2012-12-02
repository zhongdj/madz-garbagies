/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.unknown.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.billing.entity.PaymentInvoice;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_refund_payment_invoice", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "RefundPaymentInvoice.findAll", query = "SELECT c FROM RefundPaymentInvoice c"),
		@NamedQuery(name = "RefundPaymentInvoice.findById", query = "SELECT c FROM RefundPaymentInvoice c WHERE c.id = :id"),
		@NamedQuery(name = "RefundPaymentInvoice.findByRefundAmount", query = "SELECT c FROM RefundPaymentInvoice c WHERE c.refundAmount = :refundAmount"),
		@NamedQuery(name = "RefundPaymentInvoice.findByCreatedOn", query = "SELECT c FROM RefundPaymentInvoice c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "RefundPaymentInvoice.findByUpdatedOn", query = "SELECT c FROM RefundPaymentInvoice c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "RefundPaymentInvoice.findByDeleted", query = "SELECT c FROM RefundPaymentInvoice c WHERE c.deleted = :deleted") })
public class RefundPaymentInvoice extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "refund_amount", nullable = false)
	private double refundAmount;
	@JoinColumn(name = "payment_invoice_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private PaymentInvoice paymentInvoiceId;
	@JoinColumn(name = "refund_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Refund refundId;

	public RefundPaymentInvoice() {
	}

	public RefundPaymentInvoice(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public PaymentInvoice getPaymentInvoiceId() {
		return paymentInvoiceId;
	}

	public void setPaymentInvoiceId(PaymentInvoice paymentInvoiceId) {
		this.paymentInvoiceId = paymentInvoiceId;
	}

	public Refund getRefundId() {
		return refundId;
	}

	public void setRefundId(Refund refundId) {
		this.refundId = refundId;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.RefundPaymentInvoice[id=" + getId() + "]";
	}

}
