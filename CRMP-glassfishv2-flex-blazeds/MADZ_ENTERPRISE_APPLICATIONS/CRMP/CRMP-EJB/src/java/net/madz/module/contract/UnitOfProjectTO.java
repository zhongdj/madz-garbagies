package net.madz.module.contract;

import java.util.Date;
import java.util.Set;

import net.madz.module.account.ContactTO;
import net.madz.module.contract.entity.UnitOfProject;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class UnitOfProjectTO extends StandardTO<UnitOfProject> {

	private static final long serialVersionUID = 1L;

	private String name;
	@Binding(name = "project.id")
	private String projectId;
	@Binding(name = "project.name")
	private String projectName;
	private Date startDate;
	private Date endDate;
	private Date nextBillDate;

	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO billToContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO shipToContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO payerContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO soldToContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ConstructionPartTO.class, accessType = AccessTypeEnum.Property)
	private Set<ConstructionPartTO> parts;

	@Binding(name = "contract.id")
	private String contractId;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ContactTO getBillToContact() {
		return billToContact;
	}

	public void setBillToContact(ContactTO billToContact) {
		this.billToContact = billToContact;
	}

	public ContactTO getShipToContact() {
		return shipToContact;
	}

	public void setShipToContact(ContactTO shipToContact) {
		this.shipToContact = shipToContact;
	}

	public ContactTO getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(ContactTO payerContact) {
		this.payerContact = payerContact;
	}

	public ContactTO getSoldToContact() {
		return soldToContact;
	}

	public void setSoldToContact(ContactTO soldToContact) {
		this.soldToContact = soldToContact;
	}

	public Set<ConstructionPartTO> getParts() {
		return parts;
	}

	public void setParts(Set<ConstructionPartTO> parts) {
		this.parts = parts;
	}

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

	public Date getNextBillDate() {
		return nextBillDate;
	}

	public void setNextBillDate(Date nextBillDate) {
		this.nextBillDate = nextBillDate;
	}

	@Override
	public String toString() {
		return "UnitOfProjectTO [name=" + name + ", projectId=" + projectId + ", projectName=" + projectName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", nextBillDate=" + nextBillDate + ", billToContact=" + billToContact + ", shipToContact="
				+ shipToContact + ", payerContact=" + payerContact + ", soldToContact=" + soldToContact + ", parts=" + parts
				+ ", contractId=" + contractId + "]";
	}


}
