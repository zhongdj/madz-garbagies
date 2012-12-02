/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_contact", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
		@NamedQuery(name = "Contact.findById", query = "SELECT c FROM Contact c WHERE c.id = :id"),
		@NamedQuery(name = "Contact.findByName", query = "SELECT c FROM Contact c WHERE c.name = :name"),
		@NamedQuery(name = "Contact.findByEmail", query = "SELECT c FROM Contact c WHERE c.email = :email"),
		@NamedQuery(name = "Contact.findByMobile", query = "SELECT c FROM Contact c WHERE c.mobile = :mobile"),
		@NamedQuery(name = "Contact.findByFax", query = "SELECT c FROM Contact c WHERE c.fax = :fax"),
		@NamedQuery(name = "Contact.findBySex", query = "SELECT c FROM Contact c WHERE c.sex = :sex"),
		@NamedQuery(name = "Contact.findByCreatedOn", query = "SELECT c FROM Contact c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "Contact.findByUpdatedOn", query = "SELECT c FROM Contact c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "Contact.findByDeleted", query = "SELECT c FROM Contact c WHERE c.deleted = :deleted") })
public class Contact extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Column(name = "name", length = 50)
	private String name;
	@Column(name = "email", length = 60)
	private String email;
	@Column(name = "mobile", length = 20)
	private String mobile;
	@Column(name = "telephone", length = 20)
	private String telephone;
	@Column(name = "fax", length = 20)
	private String fax;
	@Column(name = "sex", length = 10)
	private Boolean sex;
	@Column(name = "contact_type", length = 10)
	private String contactType;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Account account;

	public Contact() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ContactType getContactType() {
		return ContactType.valueOf(contactType);
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType.name();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.Contact[id=" + getId() + "]";
	}

	public static enum ContactType {
		BillTo, SoldTo, Payer, ShipTo, Unknown
	}
}
