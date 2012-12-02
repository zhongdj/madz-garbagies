package net.madz.module.account.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.entity.Account;
import net.madz.module.account.entity.Contact;

@Local
@RolesAllowed({ "ADMIN" })
public interface AccountFacadeLocal {

	// Account
	String createAccount(AccountTO account);

	// Contact
	public String createContact(ContactTO contact);

	AccountTO findAccount(String id);

	List<AccountTO> findAccounts();

	List<AccountTO> findAccountsByName(String name);

	List<AccountTO> findAccountsByShortName(String shortName);

	public List<ContactTO> findContactByName(String name);

	public List<ContactTO> findContacts();

	public List<ContactTO> findContactsWithDeleted();

	Account getUnspecifiedAccount();

	Contact getUnspecifiedContact();

	void hardDeleteAllAccounts();

	public void hardDeleteAllContacts();

	void hardDeleteMultiAccounts(String[] ids);

	public void hardDeleteMultiContacts(String[] ids);

	boolean isAccountDeleted(String id);

	public boolean isContactDeleted(String id);

	void softDeleteMultiAccounts(String[] ids);

	public void softDeleteMultiContacts(String[] ids);

	void updateAccount(AccountTO account);

	public void updateContacts(ContactTO[] contacts);

	void updateMultiAccounts(AccountTO[] accounts);

}
