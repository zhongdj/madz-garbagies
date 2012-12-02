package net.madz.module.account.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;

@Remote
@RolesAllowed({ "ADMIN" })
public interface AccountFacadeRemote {

	String createAccount(AccountTO account);

	String createContact(ContactTO contact);

	AccountTO findAccount(String id);

	List<AccountTO> findAccounts();

	List<AccountTO> findAccountsByName(String name);

	List<AccountTO> findAccountsByShortName(String shortName);

	List<ContactTO> findContactByName(String name);

	List<ContactTO> findContacts();

	List<ContactTO> findContacts(String accountId, String contactType);

	List<ContactTO> findContactsWithDeleted();

	void hardDeleteAllAccounts();

	void hardDeleteAllContacts();

	void hardDeleteMultiAccounts(String[] ids);

	void hardDeleteMultiContacts(String[] ids);

	boolean isAccountDeleted(String id);

	boolean isContactDeleted(String id);

	void softDeleteMultiAccounts(String[] ids);

	void softDeleteMultiContacts(String[] ids);

	void updateAccount(AccountTO account);

	void updateContacts(ContactTO[] contacts);

	void updateMultiAccounts(AccountTO[] accounts);

}
