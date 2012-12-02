package net.madz.module.account;

import java.util.Date;

import net.madz.infra.security.persistence.User;
import net.madz.module.account.entity.Account;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.TransferObjectFactory;

public class AccountTO extends StandardTO {

	private static final long serialVersionUID = 1L;

	private String name;
	private String shortName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		return "AccountTO [name=" + name + ",shortName=" + shortName + ", getId()=" + getId() + ", getCreatedByName()="
				+ getCreatedByName() + ", getCreatedOn()=" + getCreatedOn() + ", getUpdatedByName()=" + getUpdatedByName()
				+ ", getUpdatedOn()=" + getUpdatedOn() + "]";
	}

	public static void main(String[] args) {
		User createdBy = new User();
		createdBy.setUsername("zhongdj@gmail.com");
		Account a = new Account();
		a.setName("Barry");
		a.setShortName("By");
		a.setId("Id-247650-3sc3d");
		a.setCreatedBy(createdBy);
		a.setCreatedOn(new Date());

		try {
			AccountTO result = TransferObjectFactory.createTransferObject(AccountTO.class, a);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
