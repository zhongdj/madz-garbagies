/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.billing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.unknown.entity.ARPayment;
import net.madz.module.unknown.entity.PaymentMethod;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_artransaction", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "ARTransaction.findAll", query = "SELECT c FROM ARTransaction c"),
		@NamedQuery(name = "ARTransaction.findById", query = "SELECT c FROM ARTransaction c WHERE c.id = :id"),
		@NamedQuery(name = "ARTransaction.findByAmount", query = "SELECT c FROM ARTransaction c WHERE c.amount = :amount"),
		@NamedQuery(name = "ARTransaction.findByAge", query = "SELECT c FROM ARTransaction c WHERE c.age = :age"),
		@NamedQuery(name = "ARTransaction.findByCreatedOn", query = "SELECT c FROM ARTransaction c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "ARTransaction.findByUpdatedOn", query = "SELECT c FROM ARTransaction c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "ARTransaction.findByDeleted", query = "SELECT c FROM ARTransaction c WHERE c.deleted = :deleted") })
public class ARTransaction extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Column(name = "amount", precision = 18, scale = 0)
	private Double amount;
	@Column(name = "age")
	private Integer age;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ARPayment paymentId;
	@JoinColumn(name = "invoice_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private InvoiceBO invoice;
	@JoinColumn(name = "payment_method_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private PaymentMethod paymentMethodId;

	public ARTransaction() {
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public PaymentMethod getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(PaymentMethod paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.ARTransaction[id=" + getId() + "]";
	}

}
