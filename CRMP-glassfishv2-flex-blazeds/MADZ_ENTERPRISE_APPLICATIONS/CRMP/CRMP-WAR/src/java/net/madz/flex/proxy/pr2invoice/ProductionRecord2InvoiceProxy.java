package net.madz.flex.proxy.pr2invoice;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.pr2invoice.delegate.Pr2InvoiceDelegate;
import net.madz.flex.proxy.contract.ContractProxy;
import net.madz.module.billing.InvoiceTO;
import net.madz.service.pr2invoice.AccountRunTaskTO;
import net.madz.service.pr2invoice.entity.AccountRunTask;
import net.madz.service.pr2invoice.facade.ProductionRecordsToInvoiceRemote;

public class ProductionRecord2InvoiceProxy implements ProductionRecordsToInvoiceRemote {

	private final Pr2InvoiceDelegate delegate;

	public ProductionRecord2InvoiceProxy() {
		try {
			delegate = Pr2InvoiceDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(ContractProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public String createAccountRunTask(AccountRunTaskTO<? extends AccountRunTask> accountRunTaskTO) {
		return delegate.createAccountRunTask(accountRunTaskTO);
	}

	@Override
	public List<InvoiceTO> convertProductionRecordsToInvoice(Date start, Date end, String accountId, String unitOfProjectId) {
		return delegate.convertProductionRecordsToInvoice(start, end, accountId, unitOfProjectId);
	}

	@Override
	public AccountRunTaskTO<?> findAccountRunTaskById(String accountRunTaskId) {
		return delegate.findAccountRunTaskById(accountRunTaskId);
	}

	@Override
	public List<AccountRunTaskTO<?>> findAccountRunTasks() {
		return delegate.findAccountRunTasks();
	}

}
