package net.madz.service.pr2invoice;

import java.sql.Timestamp;
import java.util.Date;

import net.madz.service.pr2invoice.entity.AccountRunTask;
import net.madz.service.pr2invoice.entity.IAccountRun;
import net.madz.standard.StandardTO;

public abstract class AccountRunTaskTO<T extends IAccountRun> extends StandardTO<AccountRunTask> {

	private static final long serialVersionUID = -970528588841926110L;
	protected Date startDate;
	protected Date endDate;
	protected Date executeDate;

	protected Date executeStateTime;
	protected Date executeEndTime;

	protected String state;
	protected int generatedInvoiceQuantity;
	protected double generatedInvoiceAmount;

	protected int scannedProductionRecordQuantity;
	protected double scannedProductivity;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Date getExecuteStateTime() {
		return executeStateTime;
	}

	public void setExecuteStateTime(Timestamp executeStateTime) {
		this.executeStateTime = executeStateTime;
	}

	public Date getExecuteEndTime() {
		return executeEndTime;
	}

	public void setExecuteEndTime(Timestamp executeEndTime) {
		this.executeEndTime = executeEndTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getGeneratedInvoiceQuantity() {
		return generatedInvoiceQuantity;
	}

	public void setGeneratedInvoiceQuantity(int generatedInvoiceQuantity) {
		this.generatedInvoiceQuantity = generatedInvoiceQuantity;
	}

	public double getGeneratedInvoiceAmount() {
		return generatedInvoiceAmount;
	}

	public void setGeneratedInvoiceAmount(double generatedInvoiceAmount) {
		this.generatedInvoiceAmount = generatedInvoiceAmount;
	}

	public int getScannedProductionRecordQuantity() {
		return scannedProductionRecordQuantity;
	}

	public void setScannedProductionRecordQuantity(int scannedProductionRecordQuantity) {
		this.scannedProductionRecordQuantity = scannedProductionRecordQuantity;
	}

	public double getScannedProductivity() {
		return scannedProductivity;
	}

	public void setScannedProductivity(double scannedProductivity) {
		this.scannedProductivity = scannedProductivity;
	}

}
