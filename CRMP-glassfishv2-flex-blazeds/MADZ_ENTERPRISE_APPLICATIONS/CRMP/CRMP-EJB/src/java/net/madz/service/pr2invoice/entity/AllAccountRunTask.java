package net.madz.service.pr2invoice.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import net.madz.core.state.annotation.ReactiveObject;

@Entity
@DiscriminatorValue("ALL")
@ReactiveObject
public class AllAccountRunTask extends AccountRunTask {

	private static final long serialVersionUID = -3460692657195736485L;

	private int scannedUnitOfProjectQuantity;

	public int getScannedAccountQuantity() {
		return scannedUnitOfProjectQuantity;
	}

	public void setScannedAccountQuantity(int scannedAccountQuantity) {
		this.scannedUnitOfProjectQuantity = scannedAccountQuantity;
	}

	public int getScannedUnitOfProjectQuantity() {
		return scannedUnitOfProjectQuantity;
	}

	public void setScannedUnitOfProjectQuantity(int scannedUnitOfProjectQuantity) {
		this.scannedUnitOfProjectQuantity += scannedUnitOfProjectQuantity;
	}

	@Override
	public String toString() {
		return "AllAccountRunTask [scannedAccountQuantity=" + scannedUnitOfProjectQuantity + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", deleted=" + deleted + ", tenant=" + tenant + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", id=" + id + "]";
	}

}
