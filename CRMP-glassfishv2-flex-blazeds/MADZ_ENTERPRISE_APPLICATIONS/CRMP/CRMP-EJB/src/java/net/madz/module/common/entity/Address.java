package net.madz.module.common.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_address", catalog = "crmp", schema = "")
public class Address extends StandardObject {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "zip_code_id", referencedColumnName = "ID")
	private ZipCode zipCode;
	private Street street;
	private String lineOne;
	private String lineTwo;
	private String lineThree;

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public ZipCode getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	public String getLineOne() {
		return lineOne;
	}

	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}

	public String getLineTwo() {
		return lineTwo;
	}

	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}

	public String getLineThree() {
		return lineThree;
	}

	public void setLineThree(String lineThree) {
		this.lineThree = lineThree;
	}

	@Override
	public String toString() {
		return "Address [zipCode=" + zipCode + ", street=" + street + ", lineOne=" + lineOne + ", lineTwo=" + lineTwo + ", lineThree="
				+ lineThree + "]";
	}

}
