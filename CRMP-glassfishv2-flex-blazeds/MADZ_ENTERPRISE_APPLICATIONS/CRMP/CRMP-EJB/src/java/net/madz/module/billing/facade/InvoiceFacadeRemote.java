package net.madz.module.billing.facade;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.module.billing.BillRunTaskTO;
import net.madz.module.billing.InvoiceItemTO;
import net.madz.module.billing.InvoiceStatixTO;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.entity.BillRunTask;

@Remote
@RolesAllowed({ "ADMIN" })
public interface InvoiceFacadeRemote {

	// Invoice
	public String createInvoice(InvoiceTO invoice);

	public List<InvoiceTO> findInvoices();

	public InvoiceTO findInvoice(String id);

	public void updateInvoices(List<InvoiceTO> invoices);

	public void softDeleteInvoice(String[] ids);

	public void hardDeleteInvoice(String[] ids);

	public void doConfirmInvoice(String id);

	public void doBillInvoice(String id);

	public void doPostInvoice(String id);

	public void doPayInvoice(String id, double payAmount);

	// InvoiceItem
	public void addInvoiceItems(String invoiceId, Set<InvoiceItemTO> invoiceItems);

	// Find all invoice items belong to a invoice
	public Set<InvoiceItemTO> findInvoiceItems(String invoiceId);

	// Find invoice item by invoice item id
	public InvoiceItemTO findInvoiceItem(String id);

	public void updateInvoiceItems(Set<InvoiceItemTO> invoiceItems);

	public void softDeleteInvoiceItems(String[] ids);

	public void hardDeleteInvoiceItems(String[] ids);

	List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime, String unitOfProjectId);

	List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime);

	String createBillRunTask(BillRunTaskTO<? extends BillRunTask> billRunTaskTO);

	public BillRunTaskTO findBillRunTaskById(String billRunTaskId);

}
