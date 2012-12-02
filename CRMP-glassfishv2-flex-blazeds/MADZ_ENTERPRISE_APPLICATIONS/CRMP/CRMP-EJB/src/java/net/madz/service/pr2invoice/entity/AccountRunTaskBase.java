package net.madz.service.pr2invoice.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.core.state.annotation.StateIndicator;
import net.madz.infra.security.persistence.StandardObject;

@MappedSuperclass
public class AccountRunTaskBase extends StandardObject {

	private static final long serialVersionUID = 1198571720869337678L;

	//Date for searching
	@Temporal(TemporalType.DATE)
	protected Date startDate;
	//Date for searching
	@Temporal(TemporalType.DATE)
	protected Date endDate;
	@Temporal(TemporalType.DATE)
	protected Date executeDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date executeStartTime;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date executeEndTime;

	@StateIndicator
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

	public int getGeneratedInvoiceQuantity() {
		return generatedInvoiceQuantity;
	}

	public void setGeneratedInvoiceQuantity(int generatedInvoiceQuantity) {
		this.generatedInvoiceQuantity += generatedInvoiceQuantity;
	}

	public double getGeneratedInvoiceAmount() {
		return generatedInvoiceAmount;
	}

	public void setGeneratedInvoiceAmount(double generatedInvoiceAmount) {
		this.generatedInvoiceAmount += generatedInvoiceAmount;
	}

	public int getScannedProductionRecordQuantity() {
		return scannedProductionRecordQuantity;
	}

	public void setScannedProductionRecordQuantity(int scannedProductionRecordQuantity) {
		this.scannedProductionRecordQuantity += scannedProductionRecordQuantity;
	}

	public double getScannedProductivity() {
		return scannedProductivity;
	}

	public void setScannedProductivity(double scannedProductivity) {
		this.scannedProductivity += scannedProductivity;
	}

	@Override
	public String toString() {
		return "AccountRunTaskBase [startDate=" + startDate + ", endDate=" + endDate + ", executeDate=" + executeDate
				+ ", executeStateTime=" + executeStartTime + ", executeEndTime=" + executeEndTime + ", state=" + state
				+ ", generatedInvoiceQuantity=" + generatedInvoiceQuantity + ", generatedInvoiceAmount=" + generatedInvoiceAmount
				+ ", scannedProductionRecordQuantity=" + scannedProductionRecordQuantity + ", scannedProductivity=" + scannedProductivity
				+ "]";
	}

}
