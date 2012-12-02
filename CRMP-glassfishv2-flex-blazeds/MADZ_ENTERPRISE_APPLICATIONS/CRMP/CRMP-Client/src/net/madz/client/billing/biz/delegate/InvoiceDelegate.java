package net.madz.client.billing.biz.delegate;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.module.billing.BillRunTaskTO;
import net.madz.module.billing.InvoiceItemTO;
import net.madz.module.billing.InvoiceStatixTO;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.entity.BillRunTask;
import net.madz.module.billing.facade.InvoiceFacadeRemote;

public class InvoiceDelegate implements InvoiceFacadeRemote {

	private static InvoiceDelegate instance;
	private InvoiceFacadeRemote server;

	private InvoiceDelegate() throws Exception {
		try {
			server = (InvoiceFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(InvoiceFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(InvoiceDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized InvoiceDelegate getInstance() throws Exception {
		if (null == instance) {
			return new InvoiceDelegate();
		} else {
			return instance;
		}
	}

	@Override
	public String createInvoice(InvoiceTO invoice) {
		return server.createInvoice(invoice);
	}

	@Override
	public List<InvoiceTO> findInvoices() {
		return server.findInvoices();
	}

	@Override
	public InvoiceTO findInvoice(String id) {
		return server.findInvoice(id);
	}

	@Override
	public void updateInvoices(List<InvoiceTO> invoices) {
		server.updateInvoices(invoices);
	}

	@Override
	public void softDeleteInvoice(String[] ids) {
		server.softDeleteInvoice(ids);
	}

	@Override
	public void hardDeleteInvoice(String[] ids) {
		server.hardDeleteInvoice(ids);
	}

	@Override
	public void addInvoiceItems(String invoiceId, Set<InvoiceItemTO> invoiceItems) {
		server.addInvoiceItems(invoiceId, invoiceItems);
	}

	@Override
	public void updateInvoiceItems(Set<InvoiceItemTO> invoiceItems) {
		server.updateInvoiceItems(invoiceItems);
	}

	@Override
	public Set<InvoiceItemTO> findInvoiceItems(String invoiceId) {
		return server.findInvoiceItems(invoiceId);
	}

	@Override
	public InvoiceItemTO findInvoiceItem(String id) {
		return server.findInvoiceItem(id);
	}

	@Override
	public void softDeleteInvoiceItems(String[] ids) {
		server.softDeleteInvoiceItems(ids);
	}

	@Override
	public void hardDeleteInvoiceItems(String[] ids) {
		server.hardDeleteInvoiceItems(ids);
	}

	@Override
	public void doConfirmInvoice(String id) {
		server.doConfirmInvoice(id);
	}

	@Override
	public void doPostInvoice(String id) {
		server.doPostInvoice(id);
	}

	@Override
	public void doPayInvoice(String id, double payAmount) {
		server.doPayInvoice(id, payAmount);
	}

	@Override
	public List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime, String unitOfProjectId) {
		return server.summarizeInvoice(startTime, endTime, unitOfProjectId);
	}

	@Override
	public List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime) {
		return server.summarizeInvoice(startTime, endTime);
	}

	@Override
	public void doBillInvoice(String id) {
		server.doBillInvoice(id);
	}

	@Override
	public String createBillRunTask(BillRunTaskTO<? extends BillRunTask> billRunTaskTO) {
		return server.createBillRunTask(billRunTaskTO);
	}

	public BillRunTaskTO<?> findBillRunTaskById(String billRunTaskId) {
		return server.findBillRunTaskById(billRunTaskId);
	}

}
