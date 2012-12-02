package net.madz.service.pr2invoice.facade;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.module.billing.InvoiceTO;
import net.madz.service.pr2invoice.AccountRunTaskTO;
import net.madz.service.pr2invoice.entity.AccountRunTask;

@Remote
@RolesAllowed({ "ADMIN" })
public interface ProductionRecordsToInvoiceRemote {

	String createAccountRunTask(AccountRunTaskTO<? extends AccountRunTask> accountRunTaskTO);

	AccountRunTaskTO<?> findAccountRunTaskById(String accountRunTaskId);
	
	List<AccountRunTaskTO<?>> findAccountRunTasks();
	
//	List<InvoiceTO> convertProductionRecordsToInvoice(AccountRunTask task, String unitOfProjectId);

	List<InvoiceTO> convertProductionRecordsToInvoice(Date start, Date end, String accountId, String unitOfProjectId);

}
