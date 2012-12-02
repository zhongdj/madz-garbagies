/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.billing.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.unknown.entity.ARPayment;
import net.madz.module.unknown.entity.RefundPaymentInvoice;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_payment_invoice", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "PaymentInvoice.findAll", query = "SELECT c FROM PaymentInvoice c"),
		@NamedQuery(name = "PaymentInvoice.findById", query = "SELECT c FROM PaymentInvoice c WHERE c.id = :id"),
		@NamedQuery(name = "PaymentInvoice.findByApplyAmount", query = "SELECT c FROM PaymentInvoice c WHERE c.applyAmount = :applyAmount"),
		@NamedQuery(name = "PaymentInvoice.findByRefundAmount", query = "SELECT c FROM PaymentInvoice c WHERE c.refundAmount = :refundAmount"),
		@NamedQuery(name = "PaymentInvoice.findByCreatedOn", query = "SELECT c FROM PaymentInvoice c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "PaymentInvoice.findByUpdatedOn", query = "SELECT c FROM PaymentInvoice c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "PaymentInvoice.findByDeleted", query = "SELECT c FROM PaymentInvoice c WHERE c.deleted = :deleted") })
public class PaymentInvoice extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Column(name = "apply_amount", precision = 18, scale = 0)
	private Double applyAmount;
	@Column(name = "refund_amount", precision = 18, scale = 0)
	private Double refundAmount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentInvoiceId", fetch = FetchType.LAZY)
	private Set<RefundPaymentInvoice> crmpRefundPaymentInvoiceSet;

	@JoinColumn(name = "payment_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ARPayment paymentId;
	@JoinColumn(name = "invoice_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private InvoiceBO invoice;

	public PaymentInvoice() {
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Set<RefundPaymentInvoice> getRefundPaymentInvoiceSet() {
		return crmpRefundPaymentInvoiceSet;
	}

	public void setRefundPaymentInvoiceSet(Set<RefundPaymentInvoice> crmpRefundPaymentInvoiceSet) {
		this.crmpRefundPaymentInvoiceSet = crmpRefundPaymentInvoiceSet;
	}

	public ARPayment getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(ARPayment paymentId) {
		this.paymentId = paymentId;
	}

	public InvoiceBO getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceBO invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.PaymentInvoice[id=" + getId() + "]";
	}

}
