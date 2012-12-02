package net.madz.flex.proxy.billing;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.billing.biz.delegate.InvoiceDelegate;
import net.madz.module.billing.BillRunTaskTO;
import net.madz.module.billing.InvoiceItemTO;
import net.madz.module.billing.InvoiceStatixTO;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.entity.BillRunTask;
import net.madz.module.billing.facade.InvoiceFacadeRemote;

public class BillingProxy implements InvoiceFacadeRemote {

	private InvoiceDelegate delegate;

	public BillingProxy() {
		try {
			delegate = InvoiceDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(BillingProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public String createInvoice(InvoiceTO invoice) {
		return delegate.createInvoice(invoice);
	}

	@Override
	public List<InvoiceTO> findInvoices() {
		return delegate.findInvoices();
	}

	@Override
	public InvoiceTO findInvoice(String id) {
		return delegate.findInvoice(id);
	}

	@Override
	public void updateInvoices(List<InvoiceTO> invoices) {
		delegate.updateInvoices(invoices);
	}

	@Override
	public void softDeleteInvoice(String[] ids) {
		delegate.softDeleteInvoice(ids);
	}

	@Override
	public void hardDeleteInvoice(String[] ids) {
		delegate.hardDeleteInvoice(ids);
	}

	@Override
	public void doConfirmInvoice(String id) {
		delegate.doConfirmInvoice(id);
	}

	@Override
	public void doPostInvoice(String id) {
		delegate.doPostInvoice(id);
	}

	@Override
	public void doPayInvoice(String id, double payAmount) {
		delegate.doPayInvoice(id, payAmount);
	}

	@Override
	public void addInvoiceItems(String invoiceId, Set<InvoiceItemTO> invoiceItems) {
		delegate.addInvoiceItems(invoiceId, invoiceItems);
	}

	@Override
	public Set<InvoiceItemTO> findInvoiceItems(String invoiceId) {
		return delegate.findInvoiceItems(invoiceId);
	}

	@Override
	public InvoiceItemTO findInvoiceItem(String id) {
		return delegate.findInvoiceItem(id);
	}

	@Override
	public void updateInvoiceItems(Set<InvoiceItemTO> invoiceItems) {
		delegate.updateInvoiceItems(invoiceItems);
	}

	@Override
	public void softDeleteInvoiceItems(String[] ids) {
		delegate.softDeleteInvoiceItems(ids);
	}

	@Override
	public void hardDeleteInvoiceItems(String[] ids) {
		delegate.hardDeleteInvoice(ids);
	}

	@Override
	public List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime, String unitOfProjectId) {
		return delegate.summarizeInvoice(startTime, endTime, unitOfProjectId);
	}

	@Override
	public List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime) {
		return delegate.summarizeInvoice(startTime, endTime);
	}

	@Override
	public void doBillInvoice(String id) {
		delegate.doBillInvoice(id);
	}

	@Override
	public String createBillRunTask(BillRunTaskTO<? extends BillRunTask> billRunTaskTO) {
		return delegate.createBillRunTask(billRunTaskTO);
	}

	@Override
	public BillRunTaskTO findBillRunTaskById(String billRunTaskId) {
		return delegate.findBillRunTaskById(billRunTaskId);
	}

}
