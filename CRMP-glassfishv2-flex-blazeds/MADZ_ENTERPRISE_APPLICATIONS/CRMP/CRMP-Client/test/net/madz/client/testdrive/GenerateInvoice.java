package net.madz.client.testdrive;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.madz.client.billing.biz.delegate.InvoiceDelegate;
import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.module.billing.InvoiceStatixTO;
import net.madz.module.production.ProductionRecordStatixTO;
import net.madz.module.production.facade.ProductionFacadeRemote;

import com.sun.appserv.security.ProgrammaticLogin;

public class GenerateInvoice {

	public static void main(String[] args) throws Exception {
		ProgrammaticLogin l = new ProgrammaticLogin();
		l.login("administrator", "Barryzdjwin5631");
		ProductionFacadeRemote d = ProductionDelegate.getInstance();
		String unitOfProjectId = "05d61646-5744-43e3-a51d-162dc97169fb";
		Calendar c = Calendar.getInstance();
		c.set(1970, 0, 0);
		Date startTime = c.getTime();
		c.set(2011, 11, 30);
		Date endTime = c.getTime();
		List<ProductionRecordStatixTO> summarizeProductionRecords = d.summarizeProductionRecord(startTime, endTime, unitOfProjectId);
		for (ProductionRecordStatixTO productionRecordStatixTO : summarizeProductionRecords) {
			System.out.println(productionRecordStatixTO);
		}

		System.out.println("----------------------------------");
		summarizeProductionRecords = d.summarizeProductionRecord(startTime, endTime);
		for (ProductionRecordStatixTO productionRecordStatixTO : summarizeProductionRecords) {
			System.out.println(productionRecordStatixTO);
		}

		InvoiceDelegate i = InvoiceDelegate.getInstance();
		List<InvoiceStatixTO> summarizeInvoice = i.summarizeInvoice(startTime, endTime);
		for (InvoiceStatixTO invoiceStatixTO : summarizeInvoice) {
			System.out.println(invoiceStatixTO);
		}
	}
}
