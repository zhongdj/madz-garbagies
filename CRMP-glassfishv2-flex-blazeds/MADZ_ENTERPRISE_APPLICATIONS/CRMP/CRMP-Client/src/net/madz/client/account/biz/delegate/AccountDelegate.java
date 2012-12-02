package net.madz.client.account.biz.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.facade.AccountFacadeRemote;

public class AccountDelegate implements AccountFacadeRemote {

	private static AccountDelegate instance;
	private AccountFacadeRemote server;

	private AccountDelegate() throws Exception {
		try {
			server = (AccountFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(AccountFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(AccountDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized AccountDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new AccountDelegate();
		}
		return instance;
	}

	@Override
	public String createAccount(AccountTO account) {
		return server.createAccount(account);
	}

	@Override
	public void updateAccount(AccountTO account) {
		server.updateAccount(account);
	}

	@Override
	public void updateMultiAccounts(AccountTO[] accounts) {
		server.updateMultiAccounts(accounts);
	}

	@Override
	public AccountTO findAccount(String id) {
		return server.findAccount(id);
	}

	@Override
	public List<AccountTO> findAccounts() {
		return server.findAccounts();
	}

	@Override
	public List<AccountTO> findAccountsByName(String name) {
		return server.findAccountsByName(name);
	}

	@Override
	public List<AccountTO> findAccountsByShortName(String shortName) {
		return server.findAccountsByShortName(shortName);
	}

	@Override
	public void softDeleteMultiAccounts(String[] ids) {
		server.softDeleteMultiAccounts(ids);
	}

	@Override
	public void hardDeleteMultiAccounts(String[] ids) {
		server.hardDeleteMultiAccounts(ids);
	}

	@Override
	public void hardDeleteAllAccounts() {
		server.hardDeleteAllAccounts();
	}

	@Override
	public boolean isAccountDeleted(String id) {
		return server.isAccountDeleted(id);
	}

	// Contact
	@Override
	public String createContact(ContactTO account) {
		return server.createContact(account);
	}

	@Override
	public void updateContacts(ContactTO[] contacts) {
		server.updateContacts(contacts);
	}

	@Override
	public List<ContactTO> findContacts() {
		return server.findContacts();
	}

	@Override
	public List<ContactTO> findContactsWithDeleted() {
		return server.findContactsWithDeleted();
	}

	@Override
	public List<ContactTO> findContactByName(String name) {
		return server.findContactByName(name);
	}

	@Override
	public void hardDeleteAllContacts() {
		server.hardDeleteAllContacts();
	}

	@Override
	public void hardDeleteMultiContacts(String[] ids) {
		server.hardDeleteMultiContacts(ids);
	}

	@Override
	public void softDeleteMultiContacts(String[] ids) {
		server.softDeleteMultiContacts(ids);
	}

	@Override
	public boolean isContactDeleted(String id) {
		return server.isContactDeleted(id);
	}

	@Override
	public List<ContactTO> findContacts(String accountId, String contactType) {
		return server.findContacts(accountId, contactType);
	}

}
