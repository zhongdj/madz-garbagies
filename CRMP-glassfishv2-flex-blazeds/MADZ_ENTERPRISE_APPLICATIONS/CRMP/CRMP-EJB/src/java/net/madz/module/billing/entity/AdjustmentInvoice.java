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
import net.madz.module.unknown.entity.Adjustment;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_adjustment_invoice", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "AdjustmentInvoice.findAll", query = "SELECT c FROM AdjustmentInvoice c"),
		@NamedQuery(name = "AdjustmentInvoice.findById", query = "SELECT c FROM AdjustmentInvoice c WHERE c.id = :id"),
		@NamedQuery(name = "AdjustmentInvoice.findByApplyAmount", query = "SELECT c FROM AdjustmentInvoice c WHERE c.applyAmount = :applyAmount"),
		@NamedQuery(name = "AdjustmentInvoice.findByAccountingCode", query = "SELECT c FROM AdjustmentInvoice c WHERE c.accountingCode = :accountingCode"),
		@NamedQuery(name = "AdjustmentInvoice.findByCreatedOn", query = "SELECT c FROM AdjustmentInvoice c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "AdjustmentInvoice.findByUpdatedOn", query = "SELECT c FROM AdjustmentInvoice c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "AdjustmentInvoice.findByDeleted", query = "SELECT c FROM AdjustmentInvoice c WHERE c.deleted = :deleted") })
public class AdjustmentInvoice extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Column(name = "apply_Amount", precision = 18, scale = 0)
	private Double applyAmount;
	@Column(name = "accounting_code", length = 100)
	private String accountingCode;
	@JoinColumn(name = "adjustment_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Adjustment adjustmentId;
	@JoinColumn(name = "invoice_id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private InvoiceBO invoice;

	public AdjustmentInvoice() {
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public Adjustment getAdjustmentId() {
		return adjustmentId;
	}

	public void setAdjustmentId(Adjustment adjustmentId) {
		this.adjustmentId = adjustmentId;
	}

	public InvoiceBO getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceBO invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.AdjustmentInvoice[id=" + getId() + "]";
	}
}
