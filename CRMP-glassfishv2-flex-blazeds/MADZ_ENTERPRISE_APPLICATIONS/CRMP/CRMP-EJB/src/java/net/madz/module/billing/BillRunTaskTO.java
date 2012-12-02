package net.madz.module.billing;

import java.sql.Timestamp;
import java.util.Date;

import net.madz.module.billing.entity.BillRunTask;
import net.madz.module.billing.entity.IBillRun;
import net.madz.standard.StandardTO;

public abstract class BillRunTaskTO<T extends IBillRun> extends StandardTO<BillRunTask> {

	private static final long serialVersionUID = -970528588841926110L;
	protected Date executeDate;

	protected Date executeStateTime;
	protected Date executeEndTime;

	protected String state;
	protected double generatedARQuantity;
	protected double generatedARAmount;

	protected int scannedInvoiceCount;
	protected double scannedProductivity;
	
	protected String billBaselineCombination;
	protected Date targetBillDate;
	
	protected boolean isNextBillDateUpdated;

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

	public double getGeneratedARQuantity() {
		return generatedARQuantity;
	}

	public void setGeneratedARQuantity(double generatedARQuantity) {
		this.generatedARQuantity = generatedARQuantity;
	}

	public double getGeneratedARAmount() {
		return generatedARAmount;
	}

	public void setGeneratedARAmount(double generatedARAmount) {
		this.generatedARAmount = generatedARAmount;
	}

	public int getScannedInvoiceCount() {
		return scannedInvoiceCount;
	}

	public void setScannedInvoiceCount(int scannedInvoiceCount) {
		this.scannedInvoiceCount = scannedInvoiceCount;
	}

	public void setExecuteStateTime(Date executeStateTime) {
		this.executeStateTime = executeStateTime;
	}

	public void setExecuteEndTime(Date executeEndTime) {
		this.executeEndTime = executeEndTime;
	}

	public double getScannedProductivity() {
		return scannedProductivity;
	}

	public void setScannedProductivity(double scannedProductivity) {
		this.scannedProductivity = scannedProductivity;
	}

	public String getBillBaselineCombination() {
		return billBaselineCombination;
	}

	public void setBillBaselineCombination(String billBaselineCombination) {
		this.billBaselineCombination = billBaselineCombination;
	}

	public Date getTargetBillDate() {
		return targetBillDate;
	}

	public void setTargetBillDate(Date targetBillDate) {
		this.targetBillDate = targetBillDate;
	}

	public boolean isNextBillDateUpdated() {
		return isNextBillDateUpdated;
	}

	public void setNextBillDateUpdated(boolean isNextBillDateUpdated) {
		this.isNextBillDateUpdated = isNextBillDateUpdated;
	}

}
