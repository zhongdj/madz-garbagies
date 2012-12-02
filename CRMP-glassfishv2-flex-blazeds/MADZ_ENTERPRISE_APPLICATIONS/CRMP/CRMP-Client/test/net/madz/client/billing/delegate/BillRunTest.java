package net.madz.client.billing.delegate;

import java.util.Calendar;
import java.util.List;

import net.madz.client.billing.biz.delegate.InvoiceDelegate;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.SpecifiedUnitOfProjectBillRunTaskTO;
import net.madz.module.contract.entity.Contract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class BillRunTest {

	protected static ProgrammaticLogin pl = new ProgrammaticLogin();
	InvoiceDelegate instance = null;
	String accountRunTaskId = null;

	@Before
	public void setUp() throws Exception {
		pl.login("administrator", "Barryzdjwin5631");
		instance = InvoiceDelegate.getInstance();
	}

	@Test
	public final void testBillRun() {
		// Run this test after run Pr2InvoiceTest to produce invoices
		//
		// Confirm all Draft invoices
		//
		List<InvoiceTO> invoices = instance.findInvoices();
		for (InvoiceTO to : invoices) {
			instance.doConfirmInvoice(to.getId());
		}

		SpecifiedUnitOfProjectBillRunTaskTO billRunTaskTO = new SpecifiedUnitOfProjectBillRunTaskTO();
		billRunTaskTO.setSpecifiedUnitOfProjectId("a2ef6f36-40a8-43db-9727-3f58aeb7ad55");
		billRunTaskTO.setTargetBillDate(Calendar.getInstance().getTime());
		billRunTaskTO.setBillBaselineCombination(Contract.BillBaselineCombination.PERIOD_ONLY.name());
		billRunTaskTO.setNextBillDateUpdated(true);
		

		String billRunTaskId = instance.createBillRunTask(billRunTaskTO);
//		BillRunTaskTO result = instance.findBillRunTaskById(billRunTaskId);
//		AllBillRunTaskTO actualResult = null;
//		if (result instanceof SpecifiedUnitOfProjectBillRunTaskTO) {
//			actualResult = (SpecifiedUnitOfProjectBillRunTaskTO) result;
//		}
		// Assert.assertEquals(27,
		// actualResult.getScannedProductionRecordQuantity());
		// Assert.assertTrue("ScannedProductivity is incorrect.",
		// new Double(actualResult.getScannedProductivity()).compareTo(new
		// Double(99)) == 0);
		// Assert.assertTrue("GeneratedInvoiceAmount is incorrect.",
		// new Double(actualResult.getGeneratedInvoiceAmount()).compareTo(new
		// Double(594000)) == 0);
		// Assert.assertEquals("GeneratedInvoiceQuantity is incorrect.", 9,
		// actualResult.getGeneratedInvoiceQuantity());
		// Assert.assertEquals("ScannedUnitOfProjectQuantity is incorrect.", 4,
		// actualResult.getScannedUnitOfProjectQuantity());
		// Assert.assertEquals("The state is incorrect.", "FINISHED",
		// actualResult.getState());
	}

	@After
	public void tearDown() throws Exception {
		pl.logout();
	}
}
