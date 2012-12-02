/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.billing.entity;

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
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_invoice_item", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "InvoiceItem.findAll", query = "SELECT c FROM InvoiceItem c"),
		@NamedQuery(name = "InvoiceItem.findById", query = "SELECT c FROM InvoiceItem c WHERE c.id = :id"),
		@NamedQuery(name = "InvoiceItem.findByState", query = "SELECT c FROM InvoiceItem c WHERE c.state = :state"),
		@NamedQuery(name = "InvoiceItem.findByQuantity", query = "SELECT c FROM InvoiceItem c WHERE c.quantity = :quantity"),
		@NamedQuery(name = "InvoiceItem.findByAmount", query = "SELECT c FROM InvoiceItem c WHERE c.amount = :amount"),
		@NamedQuery(name = "InvoiceItem.findByCreatedOn", query = "SELECT c FROM InvoiceItem c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "InvoiceItem.findByUpdatedOn", query = "SELECT c FROM InvoiceItem c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "InvoiceItem.findByDeleted", query = "SELECT c FROM InvoiceItem c WHERE c.deleted = :deleted") })
public class InvoiceItem extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "STATE", nullable = false, length = 20)
	private boolean state;
	@Basic(optional = false)
	@Column(name = "QUANTITY", nullable = false)
	private double quantity;
	// For money
	@Basic(optional = false)
	@Column(name = "AMOUNT", nullable = false)
	private double amount;
	@JoinColumn(name = "MERCHANDISE_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private Merchandise merchandise;
	@JoinColumn(name = "INVOICE_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private InvoiceBO invoice;
	@JoinColumn(name = "UNIT_OF_MEASURE_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private UnitOfMeasure unitOfMeasure;

	public InvoiceItem() {
		this.state = false;
	}

	public InvoiceItem(double quantity, double amount) {
		this.state = false;
		this.quantity = quantity;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public InvoiceBO getInvoice() {
		return invoice;
	}

	public Merchandise getMerchandise() {
		return merchandise;
	}

	public double getQuantity() {
		return quantity;
	}

	public boolean getState() {
		return state;
	}

	public UnitOfMeasure getUnitOfMeasureId() {
		return unitOfMeasure;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setInvoice(InvoiceBO invoice) {
		if (null != this.invoice) {
			this.invoice.removeInvoiceItem(this);
		}
		this.invoice = invoice;
		this.invoice.addInvoiceItem(this);
	}

	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public void setUnitOfMeasureId(UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@Override
	public String toString() {
		return "InvoiceItem [state=" + state + ", quantity=" + quantity + ", amount=" + amount + ", merchandise=" + merchandise
				+ ", invoice=" + invoice + ", unitOfMeasure=" + unitOfMeasure + "]";
	}
}
