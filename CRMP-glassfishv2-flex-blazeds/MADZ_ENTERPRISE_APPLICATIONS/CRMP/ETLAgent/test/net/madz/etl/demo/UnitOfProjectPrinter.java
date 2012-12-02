package net.madz.etl.demo;

import java.util.Calendar;
import java.util.Date;

import net.madz.client.pr2invoice.delegate.Pr2InvoiceDelegate;
import net.madz.service.pr2invoice.AllAccountRunTaskTO;

import com.sun.appserv.security.ProgrammaticLogin;

public class UnitOfProjectPrinter {

	public static void main(String[] args) throws Exception {
		final String webUsername = "administrator";
		final String webPassword = "Barryzdjwin5631";
		final String mysqlUsername = "root";
		final String mysqlPassword = "1q2w3e4r5t";
		final String dataSourceName = "crmp_1_dsn";
		final String mysqlDatabaseName = "crmp_1_db";
		final String mysqlServerIpAddress = "localhost";

		final ProgrammaticLogin pl = new ProgrammaticLogin();
		pl.login(webUsername, webPassword);

		// ContractDelegate d = ContractDelegate.getInstance();
		// List<UnitOfProjectTO> findUnitOfProjects = d.findUnitOfProjects();
		// for (UnitOfProjectTO unitOfProjectTO : findUnitOfProjects) {
		// System.out.println(unitOfProjectTO);
		// }

		// AccountDelegate a = AccountDelegate.getInstance();
		// List<AccountTO> accounts = a.findAccounts();
		// for (AccountTO accountTO : accounts) {
		// System.out.println(accountTO);
		// }

		Pr2InvoiceDelegate p = Pr2InvoiceDelegate.getInstance();
		Calendar c = Calendar.getInstance();
		c.set(2011, 1, 1);
		Date start = new Date(c.getTimeInMillis());
		c.set(2012, 1, 1);
		Date end = new Date(c.getTimeInMillis());

		AllAccountRunTaskTO accountRunTaskTO = new AllAccountRunTaskTO();
		accountRunTaskTO.setStartDate(start);
		accountRunTaskTO.setEndDate(end);

		p.createAccountRunTask(accountRunTaskTO);

		// mysql> UPDATE crmp_unit_of_project SET
		// CONTRACT_ID='14e70a5d-1f99-44c4-a7e6-ee7523c51bdc' WHERE id =
		// 'cb0d60d2-746a-40eb-8d89-269697aa1ab7';
		// Query OK, 1 row affected (0.00 sec)
		// Rows matched: 1 Changed: 1 Warnings: 0
		//
		// mysql> UPDATE crmp_unit_of_project SET
		// CONTRACT_ID='14e70a5d-1f99-44c4-a7e6-ee7523c51bdc' WHERE id =
		// '8ef73513-1f0c-4004-8364-e71ea984e34d';
		// Query OK, 1 row affected (0.00 sec)
		// Rows matched: 1 Changed: 1 Warnings: 0
		//
		// mysql> UPDATE crmp_unit_of_project SET
		// CONTRACT_ID='14e70a5d-1f99-44c4-a7e6-ee7523c51bdc' WHERE id =
		// '3f0aa609-0810-41c8-bfbb-217486ff9011';
		// Query OK, 1 row affected (0.00 sec)

//		p.convertProductionRecordsToInvoice(start, end,
//				"af4a153f-4b3b-400e-ac3a-6c9199725270",
//				"cb0d60d2-746a-40eb-8d89-269697aa1ab7");
//		p.convertProductionRecordsToInvoice(start, end,
//				"af4a153f-4b3b-400e-ac3a-6c9199725270",
//				"8ef73513-1f0c-4004-8364-e71ea984e34d");
//		p.convertProductionRecordsToInvoice(start, end,
//				"af4a153f-4b3b-400e-ac3a-6c9199725270",
//				"3f0aa609-0810-41c8-bfbb-217486ff9011");

		pl.logout();
	}
}
