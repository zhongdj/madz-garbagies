/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.to.create;

import java.io.Serializable;

import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;

/**
 * 
 * @author Administrator
 */
public class CompanyCTO implements Serializable, IServiceArgument {
	private static final long serialVersionUID = 5183106115094315308L;

	private String name;
	private String address;
	private String artificialPersonName;
	private String adminName;
	private String adminPass;

	// private boolean locked;
	// private boolean freezen;
	// private Timestamp paymentDate;
	// private double payment;
	// private int historyServiceDays;
	// private int serviceDaysPaid;
	// private int serviceDaysLeft;
	// private Timestamp maturityDate;
	// private boolean arrearage;
	// private boolean evaluated;
	// private Company parentCompany;
	// private AccountCTO accountCTO;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * public boolean isArrearage() { return arrearage; }
	 * 
	 * public void setArrearage(boolean arrearage) { this.arrearage = arrearage;
	 * }
	 */
	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	/*
	 * public boolean isEvaluated() { return evaluated; }
	 * 
	 * public void setEvaluated(boolean evaluated) { this.evaluated = evaluated;
	 * }
	 * 
	 * public boolean isFreezen() { return freezen; }
	 * 
	 * public void setFreezen(boolean freezen) { this.freezen = freezen; }
	 * 
	 * public int getHistoryServiceDays() { return historyServiceDays; }
	 * 
	 * public void setHistoryServiceDays(int historyServiceDays) {
	 * this.historyServiceDays = historyServiceDays; }
	 * 
	 * public boolean isLocked() { return locked; }
	 * 
	 * public void setLocked(boolean locked) { this.locked = locked; }
	 * 
	 * public Timestamp getMaturityDate() { return maturityDate; }
	 * 
	 * public void setMaturityDate(Timestamp maturityDate) { this.maturityDate =
	 * maturityDate; }
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public void validate() throws ValidationException {
		if (null == name || name.trim().length() <= 0) {
			throw new ValidationException("The Company Name must not be empty!");
		}
		// if (null == artificialPersonName ||
		// artificialPersonName.trim().length() <= 0) {
		// throw new
		// ValidationException("The Artificial PersonName must not be empty!");
		// }
		if (null == adminName || adminName.trim().length() <= 0) {
			throw new ValidationException("The Admin Name  of Company must not be empty!");
		}
		if (null == adminPass || adminPass.trim().length() <= 0) {
			throw new ValidationException("The Admin Password of Company must not be empty!");
		}
	}

	public String getArgumentName() {
		return name;
	}

	/*
	 * public Company getParentCompany() { return parentCompany; }
	 * 
	 * public void setParentCompany(Company parentCompany) { this.parentCompany
	 * = parentCompany; }
	 * 
	 * public double getPayment() { return payment; }
	 * 
	 * public void setPayment(double payment) { this.payment = payment; }
	 * 
	 * public Timestamp getPaymentDate() { return paymentDate; }
	 * 
	 * public void setPaymentDate(Timestamp paymentDate) { this.paymentDate =
	 * paymentDate; }
	 * 
	 * public int getServiceDaysLeft() { return serviceDaysLeft; }
	 * 
	 * public void setServiceDaysLeft(int serviceDaysLeft) {
	 * this.serviceDaysLeft = serviceDaysLeft; }
	 * 
	 * public int getServiceDaysPaid() { return serviceDaysPaid; }
	 * 
	 * public void setServiceDaysPaid(int serviceDaysPaid) {
	 * this.serviceDaysPaid = serviceDaysPaid; }
	 */

}
