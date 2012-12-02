package net.madz.client.billing.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.account.biz.delegate.AccountDelegate;
import net.madz.client.billing.biz.delegate.InvoiceDelegate;
import net.madz.client.common.biz.delegate.CommonObjectDelegate;
import net.madz.client.contract.biz.delegate.ContractDelegate;
import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.entity.Contact.ContactType;
import net.madz.module.billing.InvoiceItemTO;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.entity.InvoiceBO;
import net.madz.module.billing.entity.InvoiceBO.InvoiceStateEnum;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.module.production.ProductionRecordTO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class InvoiceTesting {

	ProgrammaticLogin pl = new ProgrammaticLogin();
	InvoiceDelegate instance = null;

	AccountDelegate accountInstance = null;
	ProductionDelegate productionInstance = null;
	ContractDelegate contractInstance = null;
	CommonObjectDelegate commonInstance = null;

	AccountTO billToAccount = null;
	AccountTO payerAccount = null;
	AccountTO soldToAccount = null;
	AccountTO shipToAccount = null;

	ContactTO billToContact = null;
	ContactTO shipToContact = null;
	ContactTO soldToContact = null;
	ContactTO payerContact = null;

	ProductionRecordTO record = null;
	ConcreteMixingPlantTO plant = null;
	UnitOfProjectTO unitOfProjectTO = null;
	UnitOfMeasureCTO unitOfMeasureTO = null;

	private static ThreadLocal<String> invoiceId = new ThreadLocal<String>();
	private static ThreadLocal<String> billToAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> payerAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> shipToAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> soldToAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> billToContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> payerContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> shipToContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> soldToContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> recordId = new ThreadLocal<String>();
	private static ThreadLocal<String> plantId = new ThreadLocal<String>();
	private static ThreadLocal<String> unitOfProjectId = new ThreadLocal<String>();
	private static ThreadLocal<String> unitOfMeasureId = new ThreadLocal<String>();
	private static ThreadLocal<String> projectId = new ThreadLocal<String>();
	private static String categoryId;
	private static String merchandiseId;

	@Before
	public void setUp() throws Exception {
		pl.login("administrator", "Barryzdjwin5631");

		try {
			instance = InvoiceDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(InvoiceDelegate.class.getName()).log(Level.SEVERE, null, ex);
		}
		commonInstance = CommonObjectDelegate.getInstance();
		try {
			accountInstance = AccountDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(AccountDelegate.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			productionInstance = ProductionDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(ProductionDelegate.class.getName()).log(Level.SEVERE, null, ex);
		}

		contractInstance = ContractDelegate.getInstance();

		configureAccounts();
		configureContacts();
		configureProject();
		configureUnitOfProject();
		configurePlant();
		configureUnitOfMeasue();
		configureMerchandise();
		// configureProductionRecord();
	}


	private void configureMerchandise() {
		MerchandiseCTO merchandise = new MerchandiseCTO();
		merchandise.setName("C100");

	}

	private void configureProject() {
		ProjectTO project = new ProjectTO();
		project.setBillToContact(billToContact);
		project.setShipToContact(shipToContact);
		project.setSoldToContact(soldToContact);
		project.setPayerContact(payerContact);
		project.setName("����");
		projectId.set(contractInstance.createProject(project));
		project.setId(projectId.get());
	}

	private void configureContacts() {
		billToContact = new ContactTO();
		billToContact.setAccountId(billToAccountId.get());
		billToContact.setContactType(ContactType.valueOf("BillTo").name());
		billToContact.setMobile("15810281470");
		billToContactId.set(accountInstance.createContact(billToContact));
		billToContact.setId(billToContactId.get());

		shipToContact = new ContactTO();
		shipToContact.setAccountId(shipToAccountId.get());
		shipToContact.setContactType(ContactType.valueOf("ShipTo").name());
		shipToContact.setMobile("15810281460");
		shipToContactId.set(accountInstance.createContact(shipToContact));
		shipToContact.setId(shipToContactId.get());

		soldToContact = new ContactTO();
		soldToContact.setAccountId(soldToAccountId.get());
		soldToContact.setContactType(ContactType.valueOf("SoldTo").name());
		soldToContact.setMobile("15810281490");
		soldToContactId.set(accountInstance.createContact(soldToContact));
		soldToContact.setId(soldToContactId.get());

		payerContact = new ContactTO();
		payerContact.setAccountId(payerAccountId.get());
		payerContact.setContactType(ContactType.valueOf("Payer").name());
		payerContact.setMobile("15810281420");
		payerContactId.set(accountInstance.createContact(payerContact));
		payerContact.setId(payerContactId.get());
	}

	private void configurePlant() {
		plant = new ConcreteMixingPlantTO();
		plant.setName("1��ջ");
		plant.setOperatorId("1");
		plant.setOperatorName("administrator");
		plantId.set(productionInstance.createConcreteMixingPlant(plant));
	}

	private void configureUnitOfProject() {
		unitOfProjectTO = new UnitOfProjectTO();
		unitOfProjectTO.setName("���ǽ����");
		unitOfProjectTO.setBillToContact(billToContact);
		unitOfProjectTO.setPayerContact(payerContact);
		unitOfProjectTO.setSoldToContact(soldToContact);
		unitOfProjectTO.setShipToContact(shipToContact);
		unitOfProjectTO.setProjectId(projectId.get());
		unitOfProjectId.set(contractInstance.createUnitOfProject(unitOfProjectTO));
		// unitOfProjectTO.setParts(parts)
	}

	private void configureUnitOfMeasue() {
		UnitOfMeasureCTO unitOfMeasure = new UnitOfMeasureCTO();
		unitOfMeasure.setName("������");
		unitOfMeasure.setDescription("������");
		try {
			unitOfMeasureId.set(commonInstance.createUnitOfMeasure(unitOfMeasure));
		} catch (ApplicationServiceException e) {
			e.printStackTrace();
		}
	}

	private void configureProductionRecord() {
		record = new ProductionRecordTO();
		// record.setBilled(false);
		record.setReferenceNumber("230-193-12");
		record.setQuantity(120);
		record.setProductionTime(new Date());
		record.setDescription("E0234");
		record.setUnitOfProjectId(unitOfProjectId.get());
		record.setMixingPlantId(plantId.get());
		record.setUomId(unitOfMeasureId.get());
		// record.setMerchandiseSet(merchandiseSet);
		recordId.set(productionInstance.createProductionRecord(record));
	}

	private void configureAccounts() {
		billToAccount = new AccountTO();
		billToAccount.setName("Bill To Account");
		billToAccount.setShortName("billto");
		billToAccountId.set(accountInstance.createAccount(billToAccount));

		payerAccount = new AccountTO();
		payerAccount.setName("Payer To Account");
		payerAccount.setShortName("payerto");
		payerAccountId.set(accountInstance.createAccount(payerAccount));

		shipToAccount = new AccountTO();
		shipToAccount.setName("Ship To Account");
		shipToAccount.setShortName("shipto");
		shipToAccountId.set(accountInstance.createAccount(shipToAccount));

		soldToAccount = new AccountTO();
		soldToAccount.setName("Sold To Account");
		soldToAccount.setShortName("soldto");
		soldToAccountId.set(accountInstance.createAccount(soldToAccount));
	}

	@After
	public void tearDown() throws Exception {
		// removeInvoices();
		// removeProductionRecords();
		// removeMixingPlants();
		// removeUnitOfMeasure();
		// removeUnitOfProjects();
		// removeProjects();
		// removeContacts();
		// removeAccounts();
		pl.logout();
	}

	private void removeContacts() {
		accountInstance.hardDeleteAllContacts();
	}

	private void removeUnitOfProjects() {
		contractInstance.hardDeleteAllUnitOfProjects();

	}

	private void removeProjects() {
		contractInstance.hardDeleteAllProjects();
	}

	private void removeUnitOfMeasure() {
	}

	private void removeMixingPlants() {
		productionInstance.hardDeleteAllConcreteMixingPlants();
	}

	private void removeProductionRecords() {
		productionInstance.hardDeleteAllProductionRecords();
	}

	private void removeInvoices() {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add(invoiceId.get());
		// instance.hardDeleteInvoice(ids.toArray(new String[0]));
	}

	private void removeAccounts() {
		accountInstance.hardDeleteAllAccounts();
	}

	// @Test
	public void testCreateInvoice() {
		InvoiceTO first = new InvoiceTO();
		first.setBillToAccountId(billToAccountId.get());
		first.setPayerAccountId(payerAccountId.get());
		first.setShipToAccountId(shipToAccountId.get());
		first.setSoldToAccountId(soldToAccountId.get());
		first.setDueDate(new Date());

		invoiceId.set(instance.createInvoice(first));
	}

	@Test
	public void testConfirmInvoice() {
		InvoiceTO first = new InvoiceTO();
		first.setBillToAccountId(billToAccountId.get());
		first.setPayerAccountId(payerAccountId.get());
		first.setShipToAccountId(shipToAccountId.get());
		first.setSoldToAccountId(soldToAccountId.get());
		first.setDueDate(new Date());
		invoiceId.set(instance.createInvoice(first));
		instance.doConfirmInvoice(invoiceId.get());

		InvoiceTO result = instance.findInvoice(invoiceId.get());
		assertEquals(InvoiceBO.InvoiceStateEnum.valueOf(result.getState()), InvoiceStateEnum.CONFIRMED);
	}

	// @Test
	public void testAddInvoiceItems() {
		// Prepare 2 invoice items
		InvoiceItemTO first = new InvoiceItemTO();
		first.setAmount(123.2);
		first.setQuantity(12.4);

		// first.setMerchandiseId(merchandiseId);

		// Add the 2 invoice items into invoiceId
		// verify whether added
	}

	// @Test
	public void testFindInvoices() {
		fail("Not yet implemented");
	}

	// @Test
	public void testUpdateInvoices() {
		fail("Not yet implemented");
	}

	// @Test
	public void testSoftDeleteInvoice() {
		fail("Not yet implemented");
	}

	// @Test
	public void testHardDeleteInvoiceStringArray() {
		fail("Not yet implemented");
	}

	// @Test
	public void testHardDeleteInvoice() {
		fail("Not yet implemented");
	}

}
