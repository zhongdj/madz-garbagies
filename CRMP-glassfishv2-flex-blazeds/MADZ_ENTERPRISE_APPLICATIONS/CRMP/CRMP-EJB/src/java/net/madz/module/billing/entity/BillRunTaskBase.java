package net.madz.module.billing.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.core.state.annotation.StateIndicator;
import net.madz.infra.security.persistence.StandardObject;

@MappedSuperclass
public class BillRunTaskBase extends StandardObject {

	private static final long serialVersionUID = 1198571720869337678L;

	@Temporal(TemporalType.DATE)
	protected Date executeDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date executeStartTime;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date executeEndTime;

	@StateIndicator
	protected String state;
	protected double generatedARQuantity;
	protected double generatedARAmount;

	protected int scannedInvoiceCount;
	protected double scannedProductivity;

	protected String billBaselineCombination;
	@Temporal(TemporalType.DATE)
	protected Date targetBillDate;

	protected boolean isNextBillDateUpdated;
	
	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Date getExecuteStateTime() {
		return executeStartTime;
	}

	public void setExecuteStartTime(Timestamp executeStartTime) {
		this.executeStartTime = executeStartTime;
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

	public Date getExecuteStartTime() {
		return executeStartTime;
	}

	public void setExecuteStartTime(Date executeStartTime) {
		this.executeStartTime = executeStartTime;
	}

	public double getGeneratedARQuantity() {
		return generatedARQuantity;
	}

	public void setGeneratedARQuantity(double d) {
		this.generatedARQuantity += d;
	}

	public double getGeneratedARAmount() {
		return generatedARAmount;
	}

	public void setGeneratedARAmount(double generatedARAmount) {
		this.generatedARAmount += generatedARAmount;
	}

	public int getScannedInvoiceQuantity() {
		return scannedInvoiceCount;
	}

	public void setScannedInvoiceCount(int scannedInvoiceCount) {
		this.scannedInvoiceCount += scannedInvoiceCount;
	}

	public void setExecuteEndTime(Date executeEndTime) {
		this.executeEndTime = executeEndTime;
	}

	public double getScannedProductivity() {
		return scannedProductivity;
	}

	public void setScannedProductivity(double scannedProductivity) {
		this.scannedProductivity += scannedProductivity;
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

	public int getScannedInvoiceCount() {
		return scannedInvoiceCount;
	}

	public boolean isNextBillDateUpdated() {
		return isNextBillDateUpdated;
	}

	public void setNextBillDateUpdated(boolean isNextBillDateUpdated) {
		this.isNextBillDateUpdated = isNextBillDateUpdated;
	}

	@Override
	public String toString() {
		return "BillRunTaskBase [executeDate=" + executeDate + ", executeStartTime=" + executeStartTime + ", executeEndTime="
				+ executeEndTime + ", state=" + state + ", generatedARQuantity=" + generatedARQuantity + ", generatedARAmount="
				+ generatedARAmount + ", scannedInvoiceCount=" + scannedInvoiceCount + ", scannedProductivity=" + scannedProductivity
				+ ", billBaselineCombination=" + billBaselineCombination + ", targetBillDate=" + targetBillDate
				+ ", isNextBillDateUpdated=" + isNextBillDateUpdated + "]";
	}

}
