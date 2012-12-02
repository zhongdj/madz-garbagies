/**
 * 
 */
package net.madz.client.account.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.account.biz.delegate.AccountDelegate;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

/**
 * @author MacX
 * 
 */
public class ContactTesting {

	ProgrammaticLogin pl = new ProgrammaticLogin();
	AccountDelegate instance = null;
	AccountTO account = null;
	private static ThreadLocal<String> accountId = new ThreadLocal<String>();
	private static ThreadLocal<String> contactId = new ThreadLocal<String>();
	private static ThreadLocal<String> secondContactId = new ThreadLocal<String>();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
		pl.login("administrator", "Barryzdjwin5631");
		try {
			instance = AccountDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(AccountDelegate.class.getName()).log(Level.SEVERE, null, ex);
		}
		// Add 1 account for testing
		account = new AccountTO();
		account.setName("������Ԫ���޹�˾");
		account.setShortName("����");
		accountId.set(instance.createAccount(account));
		// Clean Contact Table
		instance.hardDeleteAllContacts();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
		// Clean Contact Table
		instance.hardDeleteAllContacts();
		// clear the test data
		instance.hardDeleteMultiAccounts(new String[] { accountId.get() });
		pl.logout();
	}

	@Test
	public void testCreateContact() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		// testing
		accountId.set(instance.createContact(first));
		assertNotNull(accountId.get());
	}

	@Test
	public void testFindContacts() throws Exception {
		// setup
		ContactTO first = configureFirstContact();

		ContactTO second = configureSecondContact();

		contactId.set(instance.createContact(first));
		secondContactId.set(instance.createContact(second));
		// testing
		assertEquals(2, instance.findContacts().size());
	}

	@Test
	public void testFindContactsWithDeleted() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));
		instance.softDeleteMultiContacts(new String[] { contactId.get() });

		ContactTO second = configureSecondContact();
		secondContactId.set(instance.createContact(second));
		// testing
		assertEquals(1, instance.findContactsWithDeleted().size());
	}

	@Test
	public void testFindContactByName() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));
		first.setId(contactId.get());

		// testing
		List<ContactTO> list = instance.findContactByName(first.getName());
		boolean find = false;
		for (ContactTO result : list) {
			if (first.getId().endsWith(result.getId())) {
				find = true;
			}
		}
		assertTrue(find);
	}

	@Test
	public void testUpdateContacts() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));
		first.setId(contactId.get());
		// testing
		first.setName(first.getName() + " Updated");
		first.setContactType(ContactType.ShipTo.toString());
		first.setMobile("15910783554");
		instance.updateContacts(new ContactTO[] { first });

		assertEquals(first.getId(), instance.findContacts().get(0).getId());
		assertEquals(first.getName(), instance.findContacts().get(0).getName());
		assertEquals(first.getContactType(), instance.findContacts().get(0).getContactType());
		assertEquals(first.getMobile(), instance.findContacts().get(0).getMobile());
	}

	@Test
	public void testSoftDeleteContact() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));
		instance.softDeleteMultiContacts(new String[] { contactId.get() });
		// testing
		assertTrue(instance.isContactDeleted(contactId.get()));
	}

	@Test
	public void testHardDeleteContact() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));
		// testing
		instance.hardDeleteMultiContacts(new String[] { contactId.get() });
		assertEquals(0, instance.findContacts().size());
	}

	@Test
	public void testHardDeleteContactAll() throws Exception {
		// setup
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));

		ContactTO second = configureSecondContact();
		secondContactId.set(instance.createContact(second));
		// testing
		instance.hardDeleteAllContacts();
		assertEquals(0, instance.findContacts().size());
	}

	@Test
	public void testIsDeleted() throws Exception {
		// setup
		instance.hardDeleteAllContacts();
		ContactTO first = configureFirstContact();
		contactId.set(instance.createContact(first));
		instance.softDeleteMultiContacts(new String[] { contactId.get() });
		// testing
		assertTrue(instance.isContactDeleted(contactId.get()));
	}

	private static enum ContactType {
		BillTo, SoldTo, Payer, ShipTo
	}

	private ContactTO configureFirstContact() {
		ContactTO first = new ContactTO();
		first.setContactType(ContactType.BillTo.toString());
		first.setEmail("fangdayong@126.com");
		first.setFax("010-58964744");
		first.setMobile("1389334870");
		first.setTelephone("010-456238");
		first.setName("������");
		first.setSex(true);
		first.setAccountId(accountId.get());
		first.setAccountName(account.getName());
		return first;
	}

	private ContactTO configureSecondContact() {
		ContactTO second = new ContactTO();
		second.setContactType(ContactType.SoldTo.toString());
		second.setEmail("fanghongjing@126.com");
		second.setFax("010-58934589");
		second.setMobile("1382345678");
		second.setTelephone("075-138839720");
		second.setName("���꾲");
		second.setSex(true);
		second.setAccountId(accountId.get());
		second.setAccountName(account.getName());
		return second;
	}

}
