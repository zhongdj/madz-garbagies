package net.madz.module.billing;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import net.madz.module.billing.entity.InvoiceBO;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class InvoiceTO extends StandardTO<InvoiceBO> {

	private static final long serialVersionUID = -210672061389009805L;

	private String invoiceNumber;
	private String referenceNumber;
	private String comment;
	private String state;
	private Date dueDate;
	private Date postDate;
	private double totalQuantity;
	private double totalAmount;
	private double unpaidAmount;
	@Binding(name = "payerAccount.id")
	private String payerAccountId;
	@Binding(name = "payerAccount.name")
	private String payerAccountName;
	@Binding(name = "billToAccount.id")
	private String billToAccountId;
	@Binding(name = "billToAccount.name")
	private String billToAccountName;
	@Binding(name = "soldToAccount.id")
	private String soldToAccountId;
	@Binding(name = "soldToAccount.name")
	private String soldToAccountName;
	@Binding(name = "shipToAccount.id")
	private String shipToAccountId;
	@Binding(name = "shipToAccount.name")
	private String shipToAccountName;

	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = ARTransactionTO.class, name = "ARtransactionSet")
	private Set<ARTransactionTO> crmpArtransactionSet;

	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = AdjustmentInvoiceTO.class, name = "AdjustmentInvoiceSet")
	private Set<AdjustmentInvoiceTO> crmpAdjustmentInvoiceSet;

	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = PaymentInvoiceTO.class, name = "PaymentInvoiceSet")
	private Set<PaymentInvoiceTO> crmpPaymentInvoiceSet;

	@Binding(bindingType = BindingTypeEnum.Entity, accessType = AccessTypeEnum.Property, embeddedType = InvoiceItemTO.class, name = "InvoiceItemSet")
	private Set<InvoiceItemTO> crmpInvoiceItemSet;

	public String getBillToAccountId() {
		return billToAccountId;
	}

	public String getBillToAccountName() {
		return billToAccountName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<AdjustmentInvoiceTO> getCrmpAdjustmentInvoiceSet() {
		return Collections.unmodifiableSet(crmpAdjustmentInvoiceSet);
	}

	public Set<ARTransactionTO> getCrmpArtransactionSet() {
		return Collections.unmodifiableSet(crmpArtransactionSet);
	}

	public Set<InvoiceItemTO> getCrmpInvoiceItemSet() {
		return Collections.unmodifiableSet(crmpInvoiceItemSet);
	}

	public Set<PaymentInvoiceTO> getCrmpPaymentInvoiceSet() {
		return Collections.unmodifiableSet(crmpPaymentInvoiceSet);
	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getPayerAccountId() {
		return payerAccountId;
	}

	public String getPayerAccountName() {
		return payerAccountName;
	}

	public Date getPostDate() {
		return postDate;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public String getShipToAccountId() {
		return shipToAccountId;
	}

	public String getShipToAccountName() {
		return shipToAccountName;
	}

	public String getSoldToAccountId() {
		return soldToAccountId;
	}

	public String getSoldToAccountName() {
		return soldToAccountName;
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

	public void setBillToAccountId(String billToAccountId) {
		this.billToAccountId = billToAccountId;
	}

	public void setBillToAccountName(String billToAccountName) {
		this.billToAccountName = billToAccountName;
	}

	public void setCrmpAdjustmentInvoiceSet(Set<AdjustmentInvoiceTO> crmpAdjustmentInvoiceSet) {
		this.crmpAdjustmentInvoiceSet = crmpAdjustmentInvoiceSet;
	}

	public void setCrmpArtransactionSet(Set<ARTransactionTO> crmpArtransactionSet) {
		this.crmpArtransactionSet = crmpArtransactionSet;
	}

	public void setCrmpInvoiceItemSet(Set<InvoiceItemTO> crmpInvoiceItemSet) {
		this.crmpInvoiceItemSet = crmpInvoiceItemSet;
	}

	public void setCrmpPaymentInvoiceSet(Set<PaymentInvoiceTO> crmpPaymentInvoiceSet) {
		this.crmpPaymentInvoiceSet = crmpPaymentInvoiceSet;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setPayerAccountId(String payerAccountId) {
		this.payerAccountId = payerAccountId;
	}

	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setShipToAccountId(String shipToAccountId) {
		this.shipToAccountId = shipToAccountId;
	}

	public void setShipToAccountName(String shipToAccountName) {
		this.shipToAccountName = shipToAccountName;
	}

	public void setSoldToAccountId(String soldToAccountId) {
		this.soldToAccountId = soldToAccountId;
	}

	public void setSoldToAccountName(String soldToAccountName) {
		this.soldToAccountName = soldToAccountName;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}
}
