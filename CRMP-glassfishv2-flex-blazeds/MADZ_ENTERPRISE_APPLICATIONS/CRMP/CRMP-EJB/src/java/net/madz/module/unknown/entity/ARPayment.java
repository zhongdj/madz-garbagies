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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.account.entity.Account;
import net.madz.module.billing.entity.ARTransaction;
import net.madz.module.billing.entity.PaymentInvoice;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_ar_payment", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "ARPayment.findAll", query = "SELECT c FROM ARPayment c"),
		@NamedQuery(name = "ARPayment.findById", query = "SELECT c FROM ARPayment c WHERE c.id = :id"),
		@NamedQuery(name = "ARPayment.findByPaymentNumber", query = "SELECT c FROM ARPayment c WHERE c.paymentNumber = :paymentNumber"),
		@NamedQuery(name = "ARPayment.findByStatus", query = "SELECT c FROM ARPayment c WHERE c.status = :status"),
		@NamedQuery(name = "ARPayment.findByAmount", query = "SELECT c FROM ARPayment c WHERE c.amount = :amount"),
		@NamedQuery(name = "ARPayment.findByRefundAmount", query = "SELECT c FROM ARPayment c WHERE c.refundAmount = :refundAmount"),
		@NamedQuery(name = "ARPayment.findByCreatedOn", query = "SELECT c FROM ARPayment c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "ARPayment.findByUpdatedOn", query = "SELECT c FROM ARPayment c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "ARPayment.findByDeleted", query = "SELECT c FROM ARPayment c WHERE c.deleted = :deleted") })
public class ARPayment extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "payment_Number", nullable = false, length = 32)
	private String paymentNumber;
	@Column(name = "status", length = 20)
	private String status;
	@Basic(optional = false)
	@Column(name = "amount", nullable = false)
	private double amount;
	@Column(name = "refundAmount", precision = 18, scale = 0)
	private Double refundAmount;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentId", fetch = FetchType.LAZY)
	private Set<ARTransaction> crmpArtransactionSet;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentId", fetch = FetchType.LAZY)
	private Set<PaymentInvoice> crmpPaymentInvoiceSet;
	@JoinColumn(name = "payer_account_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Account payerAccountId;

	public ARPayment() {
	}

	public ARPayment(String paymentNumber, double amount) {
		this.paymentNumber = paymentNumber;
		this.amount = amount;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Set<ARTransaction> getArtransactionSet() {
		return crmpArtransactionSet;
	}

	public void setArtransactionSet(Set<ARTransaction> crmpArtransactionSet) {
		this.crmpArtransactionSet = crmpArtransactionSet;
	}

	public Set<PaymentInvoice> getPaymentInvoiceSet() {
		return crmpPaymentInvoiceSet;
	}

	public void setPaymentInvoiceSet(Set<PaymentInvoice> crmpPaymentInvoiceSet) {
		this.crmpPaymentInvoiceSet = crmpPaymentInvoiceSet;
	}

	public Account getPayerAccountId() {
		return payerAccountId;
	}

	public void setPayerAccountId(Account payerAccountId) {
		this.payerAccountId = payerAccountId;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.ArPayment[id=" + getId() + "]";
	}

}
