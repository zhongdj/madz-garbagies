package net.madz.service.pr2invoice;

import net.madz.service.pr2invoice.entity.AllAccountRunTask;

public class AllAccountRunTaskTO extends AccountRunTaskTO<AllAccountRunTask> {

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
		return "AllAccountRunTaskTO [scannedUnitOfProjectQuantity=" + scannedUnitOfProjectQuantity + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", executeDate=" + executeDate + ", executeStateTime=" + executeStateTime + ", executeEndTime="
				+ executeEndTime + ", state=" + state + ", generatedInvoiceQuantity=" + generatedInvoiceQuantity
				+ ", generatedInvoiceAmount=" + generatedInvoiceAmount + ", scannedProductionRecordQuantity="
				+ scannedProductionRecordQuantity + ", scannedProductivity=" + scannedProductivity + ", id=" + id + ", createdByName="
				+ createdByName + ", createdOn=" + createdOn + ", updatedByName=" + updatedByName + ", updatedOn=" + updatedOn + "]";
	}

}
