package net.madz.client.account.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.account.biz.delegate.AccountDelegate;
import net.madz.module.account.AccountTO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class AccountTesting {

	ProgrammaticLogin pl = new ProgrammaticLogin();
	AccountDelegate instance = null;
	private static ThreadLocal<String> accountId = new ThreadLocal<String>();
	private static ThreadLocal<String> secondAccountId = new ThreadLocal<String>();

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("setUpClass");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		System.out.println("tearDownclass");
	}

	@Before
	public void setUp() {
		System.out.println("setUp");
		try {
			pl.login("administrator", "Barryzdjwin5631", "MadzRealm", false);
			instance = AccountDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(AccountDelegate.class.getName()).log(Level.SEVERE, null, ex);
		}
		accountId.remove();
		secondAccountId.remove();
		instance.hardDeleteAllAccounts();
	}

	@After
	public void tearDown() {
		System.out.println("tearDown");
		instance.hardDeleteAllAccounts();
		accountId.remove();
		secondAccountId.remove();
		pl.logout();
	}

	@Test
	public void testCreateAccount() throws Exception {
		System.out.println("createAccount");
		// set up
		AccountTO to = new AccountTO();
		to.setName("Barry Madz");
		to.setShortName("Barry");

		// testing
		accountId.set(instance.createAccount(to));
		assertNotNull(accountId.get());
	}

	@Test
	public void testUpdateAccount() throws Exception {
		System.out.println("update Account");
		// set up
		AccountTO to = new AccountTO();
		to.setName("Barry Madz Corp.");
		to.setShortName("Barry");
		accountId.set(instance.createAccount(to));

		AccountTO updated = new AccountTO();
		updated.setId(accountId.get());
		updated.setName("Barry Madz Corp. 2011");
		updated.setShortName("Barry Madz");

		// Testing
		instance.updateAccount(updated);
		ArrayList<AccountTO> list = null;
		list = (ArrayList<AccountTO>) instance.findAccountsByName("Barry Madz Corp. 2011");
		assertEquals(accountId.get(), list.get(0).getId());
	}

	@Test
	public void testUpdateMultiAccounts() throws Exception {
		System.out.println("Update Mutil Accounts");
		// set up
		AccountTO first = new AccountTO();
		first.setName("first company");
		first.setShortName("first");
		AccountTO second = new AccountTO();
		second.setName("second company");
		second.setShortName("second");
		accountId.set(instance.createAccount(first));
		secondAccountId.set(instance.createAccount(second));
		// testing
		AccountTO firstUpdated = new AccountTO();
		firstUpdated.setId(accountId.get());
		firstUpdated.setName("first company updated");
		firstUpdated.setShortName("first updated");

		AccountTO secondUpdated = new AccountTO();
		secondUpdated.setId(secondAccountId.get());
		secondUpdated.setName("second company updated");
		secondUpdated.setShortName("second updated");

		instance.updateMultiAccounts(new AccountTO[] { firstUpdated, secondUpdated });
		ArrayList<AccountTO> list = null;
		list = (ArrayList<AccountTO>) instance.findAccountsByName("first company updated");
		assertEquals(accountId.get(), list.get(0).getId());

		ArrayList<AccountTO> secondList = null;
		secondList = (ArrayList<AccountTO>) instance.findAccountsByName("second company updated");
		assertEquals(secondAccountId.get(), secondList.get(0).getId());
	}

	@Test
	public void testFindAccounts() {
		// set up
		AccountTO account = new AccountTO();
		account.setName("Test Find Accounts");
		account.setShortName("Test Find");
		accountId.set(instance.createAccount(account));

		AccountTO second = new AccountTO();
		second.setName("Test Find Accounts Second");
		second.setShortName("Test Find Second");
		secondAccountId.set(instance.createAccount(second));
		// testing
		List<AccountTO> list = instance.findAccounts();
		assertEquals(2, list.size());
	}

	@Test
	public void testFindAccountByName() {
		// setup
		AccountTO account = new AccountTO();
		account.setName("Test Find Account By Name");
		account.setShortName("Test Find By Name");
		accountId.set(instance.createAccount(account));
		// testing
		assertEquals(accountId.get(), instance.findAccountsByName(account.getName()).get(0).getId());
	}

	@Test
	public void testFindAccountByShortName() {
		// setup
		AccountTO account = new AccountTO();
		account.setName("Test Find Account By Short Name");
		account.setShortName("Find By Short");
		accountId.set(instance.createAccount(account));
		// testing
		assertEquals(accountId.get(), instance.findAccountsByShortName(account.getShortName()).get(0).getId());
	}

	@Test
	public void testSoftDeleteMultiAccounts() throws Exception {
		// set up
		AccountTO account = new AccountTO();
		account.setName("Test Delete Account");
		account.setShortName("Test Delete");
		accountId.set(instance.createAccount(account));
		// testing
		instance.softDeleteMultiAccounts(new String[] { accountId.get() });
		assertTrue(instance.isAccountDeleted(accountId.get()));
	}

	@Test
	public void testHardDeleteAllAccounts() {
		// setup
		AccountTO account = new AccountTO();
		account.setName("Test Delete Account");
		account.setShortName("Test Delete");
		accountId.set(instance.createAccount(account));
		// testing
		instance.hardDeleteAllAccounts();
		assertEquals(0, instance.findAccounts().size());
	}

	@Test
	public void testHardDeleteMultiAccounts() {
		// setup
		AccountTO first = new AccountTO();
		first.setName("Firt to be deleted");
		first.setShortName("First Deleted");
		accountId.set(instance.createAccount(first));

		AccountTO second = new AccountTO();
		second.setName("Second to be deleted");
		second.setShortName("Second Deleted");
		secondAccountId.set(instance.createAccount(second));

		// testing
		instance.hardDeleteMultiAccounts(new String[] { accountId.get(), secondAccountId.get() });
		assertEquals(0, instance.findAccounts().size());
	}

	@Test
	public void testIsAccountDeleted() {
		// setup
		AccountTO first = new AccountTO();
		first.setName("Firt to be deleted");
		first.setShortName("First Deleted");
		accountId.set(instance.createAccount(first));
		instance.softDeleteMultiAccounts(new String[] { accountId.get() });

		// testing
		assertTrue(instance.isAccountDeleted(accountId.get()));
	}

	public static void main(String[] args) {
		ProgrammaticLogin pl = new ProgrammaticLogin();
		AccountDelegate instance = null;
		pl.login("administrator", "Barryzdjwin5631");
		try {
			instance = AccountDelegate.getInstance();
		} catch (Exception ex) {
			// Logger.getLogger(AccountTesting.class.getName()).log(Level.SEVERE,
			// null, ex);
		}

		AccountTO account = new AccountTO();
		account.setName("��");
		account.setShortName("��");
		String id = instance.createAccount(account);
		//
		// // instance.deleteAccount(new
		// String[]{"1f2c98ae-9aee-43ec-871b-200fc0918695","4e2a5409-c822-49a1-9aaf-bafac98e932a"});
		//
		// List<AccountTO> accounts = instance.findAccounts();
		// System.out.println(accounts);

		// Testing

		String name = "������";

		List<AccountTO> accounts = instance.findAccountsByName(name);
		System.out.println("findByName" + accounts);

		accounts = instance.findAccountsByShortName("��");
		System.out.println("findByShortName" + accounts);

		pl.logout();
	}

}
