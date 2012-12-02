package net.madz.module.billing;

import net.madz.module.billing.entity.InvoiceItem;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class InvoiceItemTO extends StandardTO<InvoiceItem> {
	private static final long serialVersionUID = 1L;
	private boolean state;
	private double quantity;
	private double amount;
	@Binding(name = "merchandise.id")
	private String merchandiseId;
	@Binding(name = "merchandise.name")
	private String merchandiseName;
	@Binding(name = "invoice.id")
	private String invoiceId;
	@Binding(name = "unitOfMeasure.id")
	private String unitOfMeasureId;
	@Binding(name = "unitOfMeasure.name")
	private String unitOfMeasureName;

	public double getAmount() {
		return amount;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public String getMerchandiseId() {
		return merchandiseId;
	}

	public String getMerchandiseName() {
		return merchandiseName;
	}

	public double getQuantity() {
		return quantity;
	}

	public boolean getState() {
		return state;
	}

	public String getUnitOfMeasureId() {
		return unitOfMeasureId;
	}

	public String getUnitOfMeasureName() {
		return unitOfMeasureName;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setMerchandiseId(String merchandiseId) {
		this.merchandiseId = merchandiseId;
	}

	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setUnitOfMeasureId(String unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}

	public void setUnitOfMeasureName(String unitOfMeasureName) {
		this.unitOfMeasureName = unitOfMeasureName;
	}
}