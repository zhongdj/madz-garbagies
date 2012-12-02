package net.madz.module.billing;

import net.madz.module.billing.entity.AllBillRunTask;

public class AllBillRunTaskTO extends BillRunTaskTO<AllBillRunTask> {

	private static final long serialVersionUID = 146334595164533546L;
	private int scannedUnitOfProjectQuantity;

	public int getScannedUnitOfProjectQuantity() {
		return scannedUnitOfProjectQuantity;
	}

	public void setScannedUnitOfProjectQuantity(int scannedUnitOfProjectQuantity) {
		this.scannedUnitOfProjectQuantity = scannedUnitOfProjectQuantity;
	}

	@Override
	public String toString() {
		return "AllBillRunTaskTO [scannedUnitOfProjectQuantity=" + scannedUnitOfProjectQuantity + ", executeDate=" + executeDate
				+ ", executeStateTime=" + executeStateTime + ", executeEndTime=" + executeEndTime + ", state=" + state
				+ ", generatedARQuantity=" + generatedARQuantity + ", generatedARAmount=" + generatedARAmount + ", scannedInvoiceCount="
				+ scannedInvoiceCount + ", scannedProductivity=" + scannedProductivity + ", id=" + id + ", createdByName=" + createdByName
				+ ", createdOn=" + createdOn + ", updatedByName=" + updatedByName + ", updatedOn=" + updatedOn + "]";
	}

}
