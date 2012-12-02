/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.contract.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.account.entity.Account;
import net.madz.module.account.entity.Contact;
import net.madz.module.common.entity.Address;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_project", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "Project.findAll", query = "SELECT c FROM Project c"),
		@NamedQuery(name = "Project.findById", query = "SELECT c FROM Project c WHERE c.id = :id"),
		@NamedQuery(name = "Project.findByName", query = "SELECT c FROM Project c WHERE c.name = :name") })
public class Project extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "NAME", length = 60)
	private String name;
	@ManyToOne(optional = true)
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
	private Address address;

	@ManyToOne
	@JoinColumn(name = "OWNER_ACCOUNT_ID", referencedColumnName = "ID", updatable = false, nullable = false)
	private Account owner;
	@ManyToOne
	@JoinColumn(name = "BILL_TO_CONTACT_ID", referencedColumnName = "ID")
	private Contact billToContact;
	@ManyToOne
	@JoinColumn(name = "SHIP_TO_CONTACT_ID", referencedColumnName = "ID")
	private Contact shipToContact;
	@ManyToOne
	@JoinColumn(name = "PAYER_CONTACT_ID", referencedColumnName = "ID")
	private Contact payerContact;
	@ManyToOne
	@JoinColumn(name = "SOLD_TO_CONTACT_ID", referencedColumnName = "ID")
	private Contact soldToContact;

	public Project() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Contact getBillToContact() {
		return billToContact;
	}

	public void setBillToContact(Contact billToContact) {
		this.billToContact = billToContact;
	}

	public Contact getShipToContact() {
		return shipToContact;
	}

	public void setShipToContact(Contact shipToContact) {
		this.shipToContact = shipToContact;
	}

	public Contact getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(Contact payerContact) {
		this.payerContact = payerContact;
	}

	public Contact getSoldToContact() {
		return soldToContact;
	}

	public void setSoldToContact(Contact soldToContact) {
		this.soldToContact = soldToContact;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", address=" + address + ", billToContact=" + billToContact + ", shipToContact=" + shipToContact
				+ ", payerContact=" + payerContact + ", soldToContact=" + soldToContact + "]";
	}

}
