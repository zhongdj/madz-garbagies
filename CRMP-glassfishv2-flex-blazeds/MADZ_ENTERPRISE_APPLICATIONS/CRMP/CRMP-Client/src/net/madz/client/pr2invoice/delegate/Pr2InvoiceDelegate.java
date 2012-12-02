package net.madz.client.pr2invoice.delegate;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.module.billing.InvoiceTO;
import net.madz.service.pr2invoice.AccountRunTaskTO;
import net.madz.service.pr2invoice.entity.AccountRunTask;
import net.madz.service.pr2invoice.facade.ProductionRecordsToInvoiceRemote;

public class Pr2InvoiceDelegate implements ProductionRecordsToInvoiceRemote {

	private static Pr2InvoiceDelegate instance = null;
	private ProductionRecordsToInvoiceRemote server = null;

	public Pr2InvoiceDelegate() {
		try {
			server = (ProductionRecordsToInvoiceRemote) CachingServiceLocator.getInstance().getEJBObject(
					ProductionRecordsToInvoiceRemote.class.getName());
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static synchronized Pr2InvoiceDelegate getInstance() {
		if (null == instance) {
			instance = new Pr2InvoiceDelegate();
		}
		return instance;
	}

	@Override
	public List<InvoiceTO> convertProductionRecordsToInvoice(Date start, Date end, String accountId, String unitOfProjectId) {
		return server.convertProductionRecordsToInvoice(start, end, accountId, unitOfProjectId);
	}

	@Override
	public String createAccountRunTask(AccountRunTaskTO<? extends AccountRunTask> accountRunTaskTO) {
		return server.createAccountRunTask(accountRunTaskTO);
	}

	@Override
	public AccountRunTaskTO<?> findAccountRunTaskById(String accountRunTaskId) {
		return server.findAccountRunTaskById(accountRunTaskId);
	}

	@Override
	public List<AccountRunTaskTO<?>> findAccountRunTasks() {
		return server.findAccountRunTasks();
	}

}
