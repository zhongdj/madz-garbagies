package net.madz.module.contract;

import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.contract.entity.Project;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class ProjectTO extends StandardTO<Project> {
	private static final long serialVersionUID = 1L;

	private String name;

	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = AccountTO.class)
	private AccountTO owner;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO billToContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO shipToContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO payerContact;
	@Binding(bindingType = BindingTypeEnum.Entity, embeddedType = ContactTO.class)
	private ContactTO soldToContact;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountTO getOwner() {
		return owner;
	}

	public void setOwner(AccountTO owner) {
		this.owner = owner;
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

	@Override
	public String toString() {
		return "ProjectTO [name=" + name + ", billToContact=" + billToContact + ", shipToContact=" + shipToContact + ", payerContact="
				+ payerContact + ", soldToContact=" + soldToContact + "]";
	}
}
