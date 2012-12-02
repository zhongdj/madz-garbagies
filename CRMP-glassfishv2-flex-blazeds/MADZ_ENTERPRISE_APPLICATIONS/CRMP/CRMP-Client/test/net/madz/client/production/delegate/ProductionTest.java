package net.madz.client.production.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.madz.client.account.biz.delegate.AccountDelegate;
import net.madz.client.common.biz.delegate.CommonObjectDelegate;
import net.madz.client.common.biz.delegate.CommonObjectQueryDelegate;
import net.madz.client.contract.biz.delegate.ContractDelegate;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.entity.Contact.ContactType;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.module.production.ProductionRecordTO;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class ProductionTest {

	ProgrammaticLogin pl = new ProgrammaticLogin();
	ProductionDelegate instance = null;
	ContractDelegate contractInstance = null;
	AccountDelegate accountInstance = null;
	CommonObjectDelegate commonInstance = null;
	CommonObjectQueryDelegate commonQueryInstance = null;

	private static ThreadLocal<String> billToAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> payerAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> shipToAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> soldToAccountId = new ThreadLocal<String>();
	private static ThreadLocal<String> billToContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> payerContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> shipToContactId = new ThreadLocal<String>();
	private static ThreadLocal<String> soldToContactId = new ThreadLocal<String>();

	private static String categoryId;
	private static String merchandiseId;
	private static String productionId1;
	private static String productionId2;
	private static ThreadLocal<String> unitOfMeasureId = new ThreadLocal<String>();
	private static String concreteMixingPlantId = "";
	private static String unitOfProjectId = "";
	private static String constructionPartId = "";
	private static ThreadLocal<String> projectId = new ThreadLocal<String>();

	UnitOfProjectTO unitOfProjectTO = null;

	AccountTO billToAccount = null;
	AccountTO payerAccount = null;
	AccountTO soldToAccount = null;
	AccountTO shipToAccount = null;

	ContactTO billToContact = null;
	ContactTO shipToContact = null;
	ContactTO soldToContact = null;
	ContactTO payerContact = null;

	@Before
	public void setUp() throws Exception {
		pl.login("administrator", "Barryzdjwin5631");
		instance = ProductionDelegate.getInstance();
		contractInstance = ContractDelegate.getInstance();
		accountInstance = AccountDelegate.getInstance();
		commonInstance = CommonObjectDelegate.getInstance();
		commonQueryInstance = CommonObjectQueryDelegate.getInstance();

		configureAccounts();
		configureContacts();
		configureUnitOfProject();
		configureConstructionPart();
		configureConcreteMixingPlant();
		configureUnitOfMeasure();
		configureMerchandises();
	}

	@After
	public void tearDown() throws Exception {
		pl.logout();
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

	private void configureUnitOfMeasure() {
		UnitOfMeasureCTO unitOfMeasure = new UnitOfMeasureCTO();
		unitOfMeasure.setName("������");
		unitOfMeasure.setDescription("������");
		try {
			unitOfMeasureId.set(commonInstance.createUnitOfMeasure(unitOfMeasure));
		} catch (ApplicationServiceException e) {
			e.printStackTrace();
		}
	}

	private void configureMerchandises() {
		MerchandiseCTO merchandiseCTO = new MerchandiseCTO();
		merchandiseCTO.setCode("A1010");
		merchandiseCTO.setName("A101019");
		merchandiseCTO.setDescription("test");
		merchandiseCTO.setCategory(categoryId);
		merchandiseCTO.setSuggestPrice(12.3);
		merchandiseCTO.setUnitOfMeasureId(unitOfMeasureId.get());
		try {
			merchandiseId = commonInstance.createMerchandise(merchandiseCTO);
		} catch (ApplicationServiceException e) {
			e.printStackTrace();
		}

	}

	private void configureConstructionPart() {
		ConstructionPartTO part = new ConstructionPartTO();
		part.setName("c32");
		part.setDescription("C32");
		constructionPartId = contractInstance.createConstructionPart(part);
	}

	private void configureConcreteMixingPlant() {
		ConcreteMixingPlantTO plant = new ConcreteMixingPlantTO();
		plant.setName("#3");
		plant.setOperatorId("2");
		plant.setOperatorName("tracy");
		concreteMixingPlantId = instance.createConcreteMixingPlant(plant);
	}

	private void configureUnitOfProject() {
		unitOfProjectTO = new UnitOfProjectTO();
		unitOfProjectTO.setName("���ǽ����");
		unitOfProjectTO.setBillToContact(billToContact);
		unitOfProjectTO.setPayerContact(payerContact);
		unitOfProjectTO.setSoldToContact(soldToContact);
		unitOfProjectTO.setShipToContact(shipToContact);
		unitOfProjectTO.setProjectId(projectId.get());
		unitOfProjectId = contractInstance.createUnitOfProject(unitOfProjectTO);
	}

	@Test
	public void testCreateProductionRecord() {
		ProductionRecordTO record1 = configureFirstProductionRecord();
		productionId1 = instance.createProductionRecord(record1);
		ProductionRecordTO record2 = configureSecondProductionRecord();
		productionId2 = instance.createProductionRecord(record2);
		assertNotNull(productionId1);
		assertNotNull(productionId2);
	}

	private ProductionRecordTO configureFirstProductionRecord() {
		ProductionRecordTO record = new ProductionRecordTO();
		record.setMixingPlantId(concreteMixingPlantId);
		record.setPartId(concreteMixingPlantId);
		record.setQuantity(20);
		record.setReferenceNumber("012993");
		record.setUnitOfProjectId(unitOfProjectId);
		record.setUomId(unitOfMeasureId.get());
		record.setProductionTime(new Date());
		record.setDescription("20111");
		record.addMerchandise(commonQueryInstance.findMerchandise(merchandiseId));
		return record;
	}

	private ProductionRecordTO configureSecondProductionRecord() {
		ProductionRecordTO record = new ProductionRecordTO();
		record.setMixingPlantId(concreteMixingPlantId);
		record.setPartId(concreteMixingPlantId);
		record.setQuantity(60);
		record.setReferenceNumber("012953");
		record.setUnitOfProjectId(unitOfProjectId);
		record.setUomId(unitOfMeasureId.get());
		record.setProductionTime(new Date());
		record.setDescription("20134");
		record.addMerchandise(commonQueryInstance.findMerchandise(merchandiseId));
		return record;
	}

	@Test
	public void testFindProductionRecords() {
		assertEquals(2, instance.findProductionRecords().size());
	}

	@Test
	public void testUpdateProductionRecords() {
		ProductionRecordTO result = instance.findProductionRecordById(productionId1);
		result.setDescription(result.getDescription() + "update");

		List<ProductionRecordTO> needToBeUpdated = new ArrayList<ProductionRecordTO>();
		needToBeUpdated.add(result);

		instance.updateProductionRecords(needToBeUpdated);
		ProductionRecordTO actual = instance.findProductionRecordById(productionId1);
		assertEquals(result.getDescription(), actual.getDescription());
	}

	@Test
	public void testSoftDeleteProductionRecords() {
		ProductionRecordTO findProductionRecord = instance.findProductionRecordById(productionId1);
		assertNotNull(findProductionRecord);
		instance.softDeleteProductionRecords(new String[] { productionId1 });
		assertNotNull(findProductionRecord);
	}

	@Ignore
	public void testHardDeleteAllProductionRecords() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testHardDeleteProductionRecords() {
		instance.hardDeleteProductionRecords(new String[] { productionId1, productionId2 });
	}

	@Ignore
	public void testCreateConcreteMixingPlant() {
		ConcreteMixingPlantTO to = new ConcreteMixingPlantTO();
		to.setName("3#");
		to.setOperatorId("001");
		to.setOperatorName("haihai");
		concreteMixingPlantId = instance.createConcreteMixingPlant(to);
		assertNotNull(concreteMixingPlantId);
	}


	@Test
	public void testUpdateConcreteMixingPlant() {
		ConcreteMixingPlantTO cmpt = new ConcreteMixingPlantTO();
		cmpt.setName("3#");
		cmpt.setOperatorId("001");
		cmpt.setOperatorName("haihai updated");
		cmpt.setId(concreteMixingPlantId);
		List<ConcreteMixingPlantTO> updatedList = new ArrayList<ConcreteMixingPlantTO>();
		updatedList.add(cmpt);
		instance.updateConcreteMixingPlant(updatedList);
		cmpt = instance.findConcreteMixingPlantById(concreteMixingPlantId);
		assertEquals("haihai updated", cmpt.getOperatorName());
	}

	@Test
	public void testSoftDeleteConcreteMixingPlants() {
		instance.softDeleteConcreteMixingPlants(new String[] { concreteMixingPlantId });
		assertNotNull(instance.findConcreteMixingPlantById(concreteMixingPlantId));
	}

	@Ignore
	public void testHardDeleteConcreteMixingPlants() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testHardDeleteAllConcreteMixingPlants() {
		fail("Not yet implemented");
	}

}
