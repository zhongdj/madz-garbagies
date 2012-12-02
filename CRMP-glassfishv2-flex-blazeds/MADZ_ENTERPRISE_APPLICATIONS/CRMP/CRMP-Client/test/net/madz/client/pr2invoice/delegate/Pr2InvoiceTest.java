package net.madz.client.pr2invoice.delegate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.madz.client.base.BaseFixture;
import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.module.production.ProductionRecordTO;
import net.madz.service.pr2invoice.AccountRunTaskTO;
import net.madz.service.pr2invoice.AllAccountRunTaskTO;
import net.madz.service.pr2invoice.SpecifiedUnitOfProjectAccountRunTaskTO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class Pr2InvoiceTest extends BaseFixture {

	protected static ProgrammaticLogin pl = new ProgrammaticLogin();
	Pr2InvoiceDelegate instance = null;
	ProductionDelegate productionInstance = null;
	String accountRunTaskId = null;

	@Before
	public void setUp() throws Exception {
		pl.login("administrator", "Barryzdjwin5631");
		super.setUp();
		instance = Pr2InvoiceDelegate.getInstance();
		productionInstance = ProductionDelegate.getInstance();
		configureProductionRecords();
	}

	private void configureProductionRecords() {
		// ProductionRecordTO record1 = configureFirstProductionRecord();
		// productionId1 = instance.createProductionRecord(record1);
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		populateProductionRecords(super.concreteMixingPlantIdA);
		populateProductionRecords(super.concreteMixingPlantIdB);
		populateProductionRecords(super.concreteMixingPlantIdC);
	}

	private void populateProductionRecords(String concreteMixingPlantId) {
		int i = 1;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse("2011-10-10");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		while (i < 10) {
			switch (i) {
			case 1:
			case 2:
			case 3:
				createProductionRecord(i, date, concreteMixingPlantId, super.unitOfProjectIdA, 4, "123", "1000");
				break;
			case 4:
			case 5:
			case 6:
				createProductionRecord(i, date, concreteMixingPlantId, super.unitOfProjectIdB, 3, "345", "1000");
				break;
			case 7:
			case 8:
			case 9:
				createProductionRecord(i, date, concreteMixingPlantId, super.unitOfProjectIdC, 4, "567", "1000");
				break;
			}
			try {
				Thread.sleep(5L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}

	}

	private void createProductionRecord(int i, Date productionTime, String concreteMixingPlantId, String unitOfProjectId, double quantity,
			String truckNumber, String referenceNumberBase) {
		ProductionRecordTO record = new ProductionRecordTO();
		record.setMixingPlantId(concreteMixingPlantId);
		record.setQuantity(quantity);
		record.setReferenceNumber(referenceNumberBase + i);
		record.setUnitOfProjectId(unitOfProjectId);
		record.setDescription("production record");
		record.setPartId(super.constructionPartId);
		record.setProductionTime(productionTime);
		record.setTruckNumber(truckNumber);
		record.setUomId(super.unitOfMeasureId);
		record.addMerchandise(commonQueryInstance.findMerchandise(super.merchandiseIdA));
		record.addMerchandise(commonQueryInstance.findMerchandise(super.merchandiseIdB));
		record.addMerchandise(commonQueryInstance.findMerchandise(super.merchandiseIdC));
		String resultId = productionInstance.createProductionRecord(record);
		// productionInstance.doConfirmProductionRecord(resultId);
		productionInstance.doLoseProductionRecord(resultId);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		pl.logout();
	}

	/**
	 * Before test, we must prepare below data: 1. Confirmed contract 2. Account
	 * 3. Contact 4. UnitOfProject 5. ConstructionPart 6. Confirmed
	 * ProductionRecord 7. UnitOfMeasure 8. Merchandise 9. Category 10.
	 * MixingConcretePlant
	 */
	// @Test
	public final void testConvertProductionRecordsToInvoice() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null;
		Date end = null;
		try {
			start = dateFormat.parse("2011-09-10");
			end = dateFormat.parse("2011-09-12");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// String accountId = "5ce50466-1a82-429e-803f-2745bb183d31";
		String unitOfProjectId = super.unitOfProjectIdA;
		// String unitOfProjectId = "aa737b5a-9e24-4dae-8374-80665faff7a3";
		System.out.println("id for unit of project A:" + unitOfProjectId);

		instance.convertProductionRecordsToInvoice(start, end, super.accountIdA, unitOfProjectId);
	}

	// @Test
	public final void testCreateSpecifiedAccountRunTask() {
		SpecifiedUnitOfProjectAccountRunTaskTO specifiedTaskTO = new SpecifiedUnitOfProjectAccountRunTaskTO();
		specifiedTaskTO.setSpecifiedUnitOfProjectId(super.unitOfProjectIdA);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			specifiedTaskTO.setStartDate(dateFormat.parse("2011-09-10"));
			specifiedTaskTO.setEndDate(dateFormat.parse("2011-09-12"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		instance.createAccountRunTask(specifiedTaskTO);
	}

	@SuppressWarnings("deprecation")
	@Test
	public final void testCreateAccountRunTastsForAllUnitOfProjects() {
		AllAccountRunTaskTO allTasksTO = new AllAccountRunTaskTO();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			allTasksTO.setStartDate(dateFormat.parse("2011-09-10"));
			allTasksTO.setEndDate(dateFormat.parse("2011-09-12"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		accountRunTaskId = instance.createAccountRunTask(allTasksTO);
		AccountRunTaskTO result = instance.findAccountRunTaskById(accountRunTaskId);
		AllAccountRunTaskTO actualResult = null;
		if (result instanceof AllAccountRunTaskTO) {
			actualResult = (AllAccountRunTaskTO) result;
		}
		Assert.assertEquals(27, actualResult.getScannedProductionRecordQuantity());
		Assert.assertTrue("ScannedProductivity is incorrect.",
				new Double(actualResult.getScannedProductivity()).compareTo(new Double(99)) == 0);
		Assert.assertTrue("GeneratedInvoiceAmount is incorrect.",
				new Double(actualResult.getGeneratedInvoiceAmount()).compareTo(new Double(594000)) == 0);
		Assert.assertEquals("GeneratedInvoiceQuantity is incorrect.", 9, actualResult.getGeneratedInvoiceQuantity());
		Assert.assertEquals("ScannedUnitOfProjectQuantity is incorrect.", 4, actualResult.getScannedUnitOfProjectQuantity());
		Assert.assertEquals("The state is incorrect.", "FINISHED", actualResult.getState());
	}
}
