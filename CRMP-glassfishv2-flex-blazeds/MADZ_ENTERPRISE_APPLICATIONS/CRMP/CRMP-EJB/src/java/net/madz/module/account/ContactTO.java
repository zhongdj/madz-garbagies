package net.madz.module.account;

import net.madz.module.account.entity.Contact;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class ContactTO extends StandardTO<Contact> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String mobile;
	private String telephone;
	private String fax;
	private Boolean sex;
	private String contactType;
	@Binding(name = "account.id")
	private String accountId;
	@Binding(name = "account.name")
	private String accountName;

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
