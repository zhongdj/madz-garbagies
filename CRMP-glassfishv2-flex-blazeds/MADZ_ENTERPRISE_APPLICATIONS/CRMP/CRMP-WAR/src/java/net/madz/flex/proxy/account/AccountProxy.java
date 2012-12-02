package net.madz.flex.proxy.account;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.account.biz.delegate.AccountDelegate;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.facade.AccountFacadeRemote;

public class AccountProxy implements AccountFacadeRemote {

	private AccountDelegate delegate;

	public AccountProxy() {
		try {
			delegate = AccountDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(AccountProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public String createAccount(AccountTO account) {
		return delegate.createAccount(account);
	}

	@Override
	public String createContact(ContactTO contact) {
		return delegate.createContact(contact);
	}

	@Override
	public AccountTO findAccount(String id) {
		return delegate.findAccount(id);
	}

	@Override
	public List<AccountTO> findAccounts() {
		return delegate.findAccounts();
	}

	@Override
	public List<AccountTO> findAccountsByName(String name) {
		return delegate.findAccountsByName(name);
	}

	@Override
	public List<AccountTO> findAccountsByShortName(String shortName) {
		return delegate.findAccountsByShortName(shortName);
	}

	@Override
	public List<ContactTO> findContactByName(String name) {
		return delegate.findContactByName(name);
	}

	@Override
	public List<ContactTO> findContacts() {
		return delegate.findContacts();
	}

	@Override
	public List<ContactTO> findContactsWithDeleted() {
		return delegate.findContactsWithDeleted();
	}

	@Override
	public void hardDeleteAllAccounts() {
		delegate.hardDeleteAllAccounts();
	}

	@Override
	public void hardDeleteAllContacts() {
		delegate.hardDeleteAllContacts();
	}

	@Override
	public void hardDeleteMultiAccounts(String[] ids) {
		delegate.hardDeleteMultiAccounts(ids);
	}

	@Override
	public void hardDeleteMultiContacts(String[] ids) {
		delegate.hardDeleteMultiContacts(ids);
	}

	@Override
	public boolean isAccountDeleted(String id) {
		return delegate.isAccountDeleted(id);
	}

	@Override
	public boolean isContactDeleted(String id) {
		return delegate.isContactDeleted(id);
	}

	@Override
	public void softDeleteMultiAccounts(String[] ids) {
		delegate.softDeleteMultiAccounts(ids);
	}

	@Override
	public void softDeleteMultiContacts(String[] ids) {
		delegate.softDeleteMultiContacts(ids);
	}

	@Override
	public void updateAccount(AccountTO account) {
		delegate.updateAccount(account);
	}

	@Override
	public void updateContacts(ContactTO[] contacts) {
		delegate.updateContacts(contacts);
	}

	@Override
	public void updateMultiAccounts(AccountTO[] accounts) {
		delegate.updateMultiAccounts(accounts);
	}

	@Override
	public List<ContactTO> findContacts(String accountId, String contactType) {
		return delegate.findContacts();
	}

}
