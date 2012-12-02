/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.billing.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.core.state.annotation.StateIndicator;
import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.account.entity.Account;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.contract.entity.UnitOfProject;

/**
 * 
 * @author a
 */
@MappedSuperclass
@NamedQueries({
		@NamedQuery(name = "Invoice.findAll", query = "SELECT c FROM InvoiceBO c"),
		@NamedQuery(name = "Invoice.findById", query = "SELECT c FROM InvoiceBO c WHERE c.id = :id"),
		@NamedQuery(name = "Invoice.findByInvoiceNumber", query = "SELECT c FROM InvoiceBO c WHERE c.invoiceNumber = :invoiceNumber"),
		@NamedQuery(name = "Invoice.findByReferenceNumber", query = "SELECT c FROM InvoiceBO c WHERE c.referenceNumber = :referenceNumber"),
		@NamedQuery(name = "Invoice.findByState", query = "SELECT c FROM InvoiceBO c WHERE c.state = :state"),
		@NamedQuery(name = "Invoice.findByDueDate", query = "SELECT c FROM InvoiceBO c WHERE c.dueDate = :dueDate"),
		@NamedQuery(name = "Invoice.findByCreatedOn", query = "SELECT c FROM InvoiceBO c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "Invoice.findByUpdatedOn", query = "SELECT c FROM InvoiceBO c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "Invoice.findByDeleted", query = "SELECT c FROM InvoiceBO c WHERE c.deleted = :deleted") })
public class Invoice extends StandardObject {

	protected static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "INVOICE_NUMBER", nullable = false, length = 12)
	protected String invoiceNumber;
	@Column(name = "REFERENCE_NUMBER", length = 60)
	protected String referenceNumber;
	@Basic(optional = false)
	@Column(name = "STATE", nullable = false, length = 20)
	@StateIndicator
	protected String state;
	@Column(name = "DUEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dueDate;
	// Date for sending out invoice to customer
	@Column(name = "POSTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date postDate;
	@Column(name = "TOTAL_QUANTITY")
	protected double totalQuantity;
	@Column(name = "TOTAL_AMOUNT")
	protected double totalAmount;
	@Column(name = "UNPAID_AMOUNT")
	protected double unpaidAmount;
	@Column(name = "PRODUCT_CODE")
	protected String productCode;
	@Column(name = "PRODUCTION_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date productionTime;

	// New added column for recording the Lost Prodcution Record
	@Column(name = "COMMENT")
	protected String commment;

	@JoinColumn(name = "PAYER_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	protected Account payerAccount;
	@JoinColumn(name = "BILL_TO_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	protected Account billToAccount;
	@JoinColumn(name = "SOLD_TO_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	protected Account soldToAccount;
	@JoinColumn(name = "SHIP_TO_ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	protected Account shipToAccount;
	@JoinColumn(name = "UNIT_OF_PROJECT_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	protected UnitOfProject unitOfProject;

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "invoice", fetch = FetchType.LAZY)
	protected Set<ARTransaction> crmpARtransactionSet = new HashSet<ARTransaction>();
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "invoice", fetch = FetchType.LAZY)
	protected Set<AdjustmentInvoice> crmpAdjustmentInvoiceSet = new HashSet<AdjustmentInvoice>();
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "invoice", fetch = FetchType.LAZY)
	protected Set<PaymentInvoice> crmpPaymentInvoiceSet = new HashSet<PaymentInvoice>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", fetch = FetchType.EAGER)
	protected Set<InvoiceItem> crmpInvoiceItemSet = new HashSet<InvoiceItem>();

	public Invoice() {
	}

	public Invoice(String id, String invoiceNumber, String state) {
		this.invoiceNumber = invoiceNumber;
		this.state = state;
	}

	public Set<AdjustmentInvoice> getAdjustmentInvoiceSet() {
		return crmpAdjustmentInvoiceSet;
	}

	public Set<ARTransaction> getARtransactionSet() {
		return crmpARtransactionSet;
	}

	public Account getBillToAccount() {
		return billToAccount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Set<InvoiceItem> getInvoiceItemSet() {
		return Collections.unmodifiableSet(crmpInvoiceItemSet);
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public Account getPayerAccount() {
		return payerAccount;
	}

	public Set<PaymentInvoice> getPaymentInvoiceSet() {
		return Collections.unmodifiableSet(crmpPaymentInvoiceSet);
	}

	public Date getPostDate() {
		return postDate;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public Account getShipToAccount() {
		return shipToAccount;
	}

	public Account getSoldToAccount() {
		return soldToAccount;
	}

	public String getState() {
		return state;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public String getCommment() {
		return commment;
	}

	public void setCommment(String commment) {
		this.commment = commment;
	}

	public void setAdjustmentInvoiceSet(Set<AdjustmentInvoice> crmpAdjustmentInvoiceSet) {
		this.crmpAdjustmentInvoiceSet = crmpAdjustmentInvoiceSet;
	}

	public void setARtransactionSet(Set<ARTransaction> crmpARtransactionSet) {
		this.crmpARtransactionSet = crmpARtransactionSet;
	}

	public void setBillToAccount(Account billToAccount) {
		this.billToAccount = billToAccount;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	protected void addInvoiceItem(InvoiceItem item) {
		this.crmpInvoiceItemSet.add(item);
		this.totalAmount += item.getAmount();
		this.unpaidAmount += item.getAmount();
		if (item.getMerchandise().getCategory().equals(CategoryEnum.CONCRETE)) {
			this.totalQuantity += item.getQuantity();
		}
	}

	protected void removeInvoiceItem(InvoiceItem item) {
		this.crmpInvoiceItemSet.remove(item);
		this.totalAmount -= item.getAmount();
		this.unpaidAmount -= item.getAmount();
		if (item.getMerchandise().getCategory().equals(CategoryEnum.CONCRETE)) {
			this.totalQuantity -= item.getQuantity();
		}
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setPayerAccount(Account payerAccount) {
		this.payerAccount = payerAccount;
	}

	public void setPaymentInvoiceSet(Set<PaymentInvoice> crmpPaymentInvoiceSet) {
		this.crmpPaymentInvoiceSet = crmpPaymentInvoiceSet;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setShipToAccount(Account shipToAccount) {
		this.shipToAccount = shipToAccount;
	}

	public void setSoldToAccount(Account soldToAccount) {
		this.soldToAccount = soldToAccount;
	}

	protected void setState(String state) {
		this.state = state;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void subtractFromTotalAmount(double amount) {
		this.totalAmount -= amount;
	}

	public void addToTotalAmount(double amount) {
		this.totalAmount += amount;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void subtractFromTotalQuantity(double quantity) {
		this.totalQuantity -= quantity;
	}

	public void addToTotalQuantity(double quantity) {
		this.totalQuantity += quantity;
	}

	public double getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getProductionTime() {
		return productionTime;
	}

	public void setProductionTime(Date productionTime) {
		this.productionTime = productionTime;
	}

	public UnitOfProject getUnitOfProject() {
		return unitOfProject;
	}

	public void setUnitOfProject(UnitOfProject unitOfProject) {
		this.unitOfProject = unitOfProject;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.Invoice[id=" + getId() + "]";
	}
}
