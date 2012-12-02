package net.madz.module.contract.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.account.entity.Contact;

@Entity
@Table(name = "crmp_unit_of_project", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "UnitOfProject.findAll", query = "SELECT c FROM UnitOfProject c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfProject.findById", query = "SELECT c FROM UnitOfProject c WHERE c.id = :id"),
		@NamedQuery(name = "UnitOfProject.findByName", query = "SELECT c FROM UnitOfProject c WHERE c.name = :name AND c.tenant.id = :tenantId") })
public class UnitOfProject extends StandardObject {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "NAME", length = 60)
	private String name;

	// The Date that start to supply service.
	//
	@Column(name = "START_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date startDate;
	@Column(name = "END_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date endDate;

	// Next Bill date
	@Column(name = "NEXT_BILL_DATE", nullable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date nextBillDate;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Project project;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "BILL_TO_CONTACT_ID", referencedColumnName = "ID")
	private Contact billToContact;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "SHIP_TO_CONTACT_ID", referencedColumnName = "ID")
	private Contact shipToContact;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "PAYER_CONTACT_ID", referencedColumnName = "ID")
	private Contact payerContact;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "SOLD_TO_CONTACT_ID", referencedColumnName = "ID")
	private Contact soldToContact;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ContractBO contract;

	@Deprecated
	@ManyToMany
	@JoinTable(name = "crmp_unitofproject_constructionpart", joinColumns = @JoinColumn(name = "UNIT_OF_PROJECT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CONSTRUCTION_ID", referencedColumnName = "ID"))
	private Set<ConstructionPart> parts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public Set<ConstructionPart> getParts() {
		return parts;
	}

	public void setParts(Set<ConstructionPart> parts) {
		this.parts = parts;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getNextBillDate() {
		return nextBillDate;
	}

	public void setNextBillDate(Date nextBillDate) {
		this.nextBillDate = nextBillDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ContractBO getContract() {
		return contract;
	}

	public void setContract(ContractBO contract) {
		if (null == contract) {
			if (null != this.contract) {
				this.contract.removeUnitOfProject(this);
			}
			return;
		}
		this.contract = contract;
		this.contract.addUnitOfProject(this);
	}

	@Override
	public String toString() {
		return "UnitOfProject [name=" + name + ", billToContact=" + billToContact + ", shipToContact=" + shipToContact + ", payerContact="
				+ payerContact + ", soldToContact=" + soldToContact + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
