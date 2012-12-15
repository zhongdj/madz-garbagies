package net.madz.module.billing.facade;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.util.TenantResources;
import net.madz.infra.util.BizObjectUtil;
import net.madz.interceptor.AuditInterceptor;
import net.madz.interceptor.TenantCacheInterceptor;
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.account.entity.Account;
import net.madz.module.billing.AllBillRunTaskTO;
import net.madz.module.billing.BillRunTaskTO;
import net.madz.module.billing.InvoiceItemTO;
import net.madz.module.billing.InvoiceNumberHelp;
import net.madz.module.billing.InvoiceStatixTO;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.SpecifiedUnitOfProjectBillRunTaskTO;
import net.madz.module.billing.activeobject.IInvoiceBO;
import net.madz.module.billing.entity.AllBillRunTask;
import net.madz.module.billing.entity.BillRunTask;
import net.madz.module.billing.entity.IBillRun;
import net.madz.module.billing.entity.InvoiceBO;
import net.madz.module.billing.entity.InvoiceBO.InvoiceStateEnum;
import net.madz.module.billing.entity.InvoiceItem;
import net.madz.module.billing.entity.SpecifiedUnitOfProjectBillRunTask;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.contract.entity.Contract;
import net.madz.module.contract.entity.ContractBO;
import net.madz.module.contract.entity.UnitOfProject;
import net.madz.module.production.ProductionRecordStatixTO;
import net.madz.service.pr2invoice.AllAccountRunTaskTO;
import net.madz.service.pr2invoice.SpecifiedUnitOfProjectAccountRunTaskTO;
import net.madz.service.pr2invoice.entity.AccountRunTask;
import net.madz.service.pr2invoice.entity.AllAccountRunTask;
import net.madz.service.pr2invoice.entity.IAccountRun;
import net.madz.service.pr2invoice.entity.SpecifiedUnitOfProjectAccountRunTask;
import net.madz.standard.StandardObjectFactory;
import net.vicp.madz.infra.binding.TransferObjectFactory;

@Stateless
@RolesAllowed({ "ADMIN" })
@Interceptors({ TenantCacheInterceptor.class, AuditInterceptor.class, ValidationInterceptor.class })
public class InvoiceFacade implements InvoiceFacadeRemote {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext sc;

	/**
	 * Add invoiceItems to specified invoice
	 */
	@Override
	public void addInvoiceItems(String invoiceId, Set<InvoiceItemTO> invoiceItems) {

		if (null == invoiceId || 0 >= invoiceId.trim().length() || null == invoiceItems || 0 >= invoiceItems.size()) {
			throw new NullPointerException();
		}

		InvoiceBO result = em.find(InvoiceBO.class, invoiceId.trim());
		if (result.getState().trim().toUpperCase() != InvoiceStateEnum.DRAFT.name()) {
			throw new BusinessException("Only invoice in DRAFT can be added new invoice items, but the state of this invoice is "
					+ result.getState());
		}

		Iterator<InvoiceItemTO> it = invoiceItems.iterator();

		while (it.hasNext()) {
			InvoiceItemTO currentItem = it.next();
			InvoiceItem item = TenantResources.newEntity(InvoiceItem.class);
			item.setQuantity(currentItem.getQuantity());
			item.setAmount(currentItem.getAmount());
			item.setMerchandise(em.find(Merchandise.class, currentItem.getMerchandiseId()));
			item.setInvoice(result);
			item.setUnitOfMeasure(em.find(UnitOfMeasure.class, currentItem.getUnitOfMeasureId()));

			em.persist(item);
		}
	}

	private void checkInvoicesStateIsDraft(String[] ids) {
		for (String id : ids) {
			InvoiceBO result = em.find(InvoiceBO.class, id);
			if (result.getState().trim().toUpperCase() != InvoiceStateEnum.DRAFT.name()) {
				throw new BusinessException("Only DRAFT invoice can be deleted! Please Check!" + result.getId() + result.getInvoiceNumber()
						+ result.getState());
			}
		}
	}

	/**
	 * Create a raw invoice without invoiceItems
	 */
	@Override
	public String createInvoice(InvoiceTO invoice) {
		if (null == invoice) {
			throw new NullPointerException("invoice is null!");
		}
		try {
			InvoiceBO result = TenantResources.newEntity(InvoiceBO.class);

			result.setInvoiceNumber(InvoiceNumberHelp.getInvoiceNumber(TenantResources.getTenant()));

			result.setReferenceNumber(invoice.getReferenceNumber());

			Account billToAccount = em.find(Account.class, invoice.getBillToAccountId());
			result.setBillToAccount(billToAccount);

			Account shipToAccount = em.find(Account.class, invoice.getShipToAccountId());
			result.setShipToAccount(shipToAccount);

			Account soldToAccount = em.find(Account.class, invoice.getSoldToAccountId());
			result.setSoldToAccount(soldToAccount);

			Account payerAccount = em.find(Account.class, invoice.getPayerAccountId());
			result.setPayerAccount(payerAccount);

			User currentUser = TenantResources.getCurrentUser();
			result.setCreatedBy(currentUser);
			Date createdOn = new Date();
			result.setCreatedOn(createdOn);
			result.setDeleted(false);
			result.setDueDate(invoice.getDueDate());
			if (null != invoice.getPostDate()) {
				result.setPostDate(invoice.getPostDate());
			}
			result.setTotalQuantity(0);
			result.setTotalAmount(0);
			result.setUnpaidAmount(0);
			em.persist(result);
			return result.getId();
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public void doConfirmInvoice(String id) {
		final IInvoiceBO invoiceBO = BizObjectUtil.find(InvoiceBO.class, id);
		invoiceBO.doConfirm();
	}

	@Override
	public void doBillInvoice(String id) {
		final IInvoiceBO invoiceBO = BizObjectUtil.find(InvoiceBO.class, id);
		invoiceBO.doBill();
	}

	@Override
	public void doPayInvoice(String id, double payAmount) {
		final IInvoiceBO invoiceBO = BizObjectUtil.find(InvoiceBO.class, id);
		invoiceBO.doPay(payAmount);
	}

	@Override
	public void doPostInvoice(String id) {
		final IInvoiceBO invoiceBO = BizObjectUtil.find(InvoiceBO.class, id);
		invoiceBO.doPost();
	}

	@Override
	public InvoiceTO findInvoice(String id) {
		if (null == id) {
			throw new NullPointerException();
		}
		InvoiceBO invoice = em.find(InvoiceBO.class, id);
		InvoiceTO result = null;
		try {
			result = TransferObjectFactory.createTransferObject(InvoiceTO.class, invoice);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		return result;
	}

	@Override
	public InvoiceItemTO findInvoiceItem(String id) {
		if (null == id) {
			throw new NullPointerException();
		}
		InvoiceItem invoiceItem = em.find(InvoiceItem.class, id);
		InvoiceItemTO result = null;
		try {
			result = TransferObjectFactory.createTransferObject(InvoiceItemTO.class, invoiceItem);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		return result;
	}

	@Override
	public Set<InvoiceItemTO> findInvoiceItems(String invoiceId) {
		if (null == invoiceId || 0 >= invoiceId.trim().length()) {
			throw new NullPointerException();
		}
		InvoiceBO invoice = em.find(InvoiceBO.class, invoiceId);
		if (null == invoice) {
			throw new NullPointerException();
		}
		Set<InvoiceItem> invoiceItems = invoice.getInvoiceItemSet();
		Set<InvoiceItemTO> result = new HashSet<InvoiceItemTO>();
		try {
			result = TransferObjectFactory.assembleTransferObjectSet(invoiceItems, InvoiceItemTO.class);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	@Override
	public List<InvoiceTO> findInvoices() {
		List<InvoiceBO> list = null;
		try {
			list = em.createQuery("select Object(o) from InvoiceBO as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<InvoiceTO> result = TransferObjectFactory.assembleTransferObjectList(list, InvoiceTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	private Set<InvoiceItemTO> getInvoiceItemTOsByIds(String[] ids) {
		Set<InvoiceItemTO> invoiceTOItems = new HashSet<InvoiceItemTO>();
		for (String id : ids) {
			InvoiceItem item = em.find(InvoiceItem.class, id);
			try {
				InvoiceItemTO result = TransferObjectFactory.createTransferObject(InvoiceItemTO.class, item);
				invoiceTOItems.add(result);
			} catch (Exception ex) {
				throw new BusinessException(ex);
			}
		}
		return invoiceTOItems;
	}

	@Override
	public void hardDeleteInvoice(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		checkInvoicesStateIsDraft(ids);
		for (String id : ids) {
			em.remove(em.find(InvoiceBO.class, id));
		}

	}

	@Override
	public void hardDeleteInvoiceItems(String[] ids) {
		if (null == ids || 0 >= ids.length) {
			throw new NullPointerException();
		}
		Set<InvoiceItemTO> invoiceTOItems = getInvoiceItemTOsByIds(ids);
		verifyItemsInSameDraftInvoice(invoiceTOItems);
		for (String id : ids) {
			if (null == id || 0 >= id.trim().length()) {
				throw new NullPointerException();
			}
			InvoiceItem result = em.find(InvoiceItem.class, id);
			if (!result.getState()) {
				em.remove(result);
				result.getInvoice().subtractFromTotalAmount(result.getAmount());
				result.getInvoice().subtractFromTotalQuantity(result.getQuantity());
			}
		}
	}

	private void softDeleteInvoice(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			InvoiceBO result = em.find(InvoiceBO.class, id);
			if (result.getState().toUpperCase() == InvoiceStateEnum.DRAFT.name()) {
				result.setDeleted(true);
				em.merge(result);
				if (null != result.getInvoiceItemSet()) {
					for (InvoiceItem item : result.getInvoiceItemSet()) {
						softDeleteInvoiceItem(item.getId());
					}
				}
			} else {
				throw new BusinessException("Only invoice in Draft state can be deleted!");
			}
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public void softDeleteInvoice(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		checkInvoicesStateIsDraft(ids);
		for (String id : ids) {
			softDeleteInvoice(id);
		}

	}

	private void softDeleteInvoiceItem(String id) {
		if (null == id || 0 >= id.length()) {
			throw new NullPointerException();
		}
		InvoiceItem result = em.find(InvoiceItem.class, id);
		if (!result.getState()) {
			result.setDeleted(true);
			em.merge(result);
			result.getInvoice().subtractFromTotalAmount(result.getAmount());
			result.getInvoice().subtractFromTotalQuantity(result.getQuantity());
			result.getInvoice().setUnpaidAmount(result.getInvoice().getTotalAmount());
		}
	}

	@Override
	public void softDeleteInvoiceItems(String[] ids) {
		if (null == ids || 0 >= ids.length) {
			throw new NullPointerException();
		}
		Set<InvoiceItemTO> invoiceTOItems = getInvoiceItemTOsByIds(ids);
		verifyItemsInSameDraftInvoice(invoiceTOItems);
		for (String id : ids) {
			softDeleteInvoiceItem(id);
		}
	}

	private void updateInvoice(InvoiceTO invoice) {
		if (null == invoice) {
			throw new NullPointerException();
		}
		try {
			InvoiceBO result = em.find(InvoiceBO.class, invoice.getId());
			result.setBillToAccount(em.find(Account.class, invoice.getBillToAccountId()));
			result.setDueDate(invoice.getDueDate());
			result.setInvoiceNumber(invoice.getInvoiceNumber());
			result.setPayerAccount(em.find(Account.class, invoice.getPayerAccountId()));
			result.setShipToAccount(em.find(Account.class, invoice.getShipToAccountId()));
			result.setSoldToAccount(em.find(Account.class, invoice.getSoldToAccountId()));
			em.merge(result);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}

	}

	@Override
	public void updateInvoiceItems(Set<InvoiceItemTO> invoiceItems) {
		if (null == invoiceItems || 0 >= invoiceItems.size()) {
			throw new NullPointerException();
		}
		verifyItemsInSameDraftInvoice(invoiceItems);
		for (InvoiceItemTO invoiceItemTO : invoiceItems) {
			if (null == invoiceItemTO.getInvoiceId()) {
				throw new NullPointerException();
			}
			InvoiceItem result = em.find(InvoiceItem.class, invoiceItemTO.getInvoiceId());
			if (result.getQuantity() != invoiceItemTO.getQuantity()) {
				double originalQuantity = result.getQuantity();
				double updatedQuantity = invoiceItemTO.getQuantity();
				result.getInvoice().subtractFromTotalQuantity(originalQuantity);
				result.getInvoice().addToTotalQuantity(updatedQuantity);
			}
			if (result.getAmount() != invoiceItemTO.getAmount()) {
				double originalAmount = result.getAmount();
				double updatedAmount = invoiceItemTO.getAmount();
				result.getInvoice().subtractFromTotalAmount(-originalAmount);
				result.getInvoice().addToTotalAmount(updatedAmount);
				result.getInvoice().setUnpaidAmount(result.getInvoice().getTotalAmount());
			}
			if (null != invoiceItemTO.getMerchandiseId()) {
				result.setMerchandise(em.find(Merchandise.class, invoiceItemTO.getMerchandiseId()));
			}
			if (null != invoiceItemTO.getUnitOfMeasureId()) {
				result.setUnitOfMeasure(em.find(UnitOfMeasure.class, invoiceItemTO.getUnitOfMeasureId()));
			}
			em.merge(result);
		}
	}

	@Override
	public void updateInvoices(List<InvoiceTO> invoices) {
		if (null == invoices) {
			throw new NullPointerException();
		}
		for (InvoiceTO to : invoices) {
			if (to.getState().trim().toUpperCase() != InvoiceStateEnum.DRAFT.name()) {
				throw new BusinessException("The state of invoice should be DRAFT when updated! Please Check! " + to.getId()
						+ to.getInvoiceNumber() + to.getState());
			}
		}
		for (InvoiceTO to : invoices) {
			updateInvoice(to);
		}

	}

	private void verifyItemsInSameDraftInvoice(Set<InvoiceItemTO> invoiceItems) {
		String expectedId = null;
		for (InvoiceItemTO invoiceItemTO : invoiceItems) {
			InvoiceItem result = em.find(InvoiceItem.class, invoiceItemTO.getInvoiceId());
			if (null == expectedId) {
				expectedId = result.getInvoice().getId();
			} else {
				if (expectedId != result.getInvoice().getId()) {
					throw new BusinessException("The updated invoice items should belong to the same invoice! Please check!");
				}
			}

		}

		InvoiceBO invoice = em.find(InvoiceBO.class, expectedId);
		if (invoice.getState() != InvoiceStateEnum.DRAFT.name()) {
			throw new BusinessException("Only invoice in DRAFT can be updated new invoice items, but the state of this invoice is "
					+ invoice.getState());
		}
	}

	@Override
	public List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime, String unitOfProjectId) {
		final Query query = em
				.createQuery(
						"SELECT o.productCode, SUM(o.totalQuantity), count(o.id), SUM(o.totalAmount) " + "FROM InvoiceBO o "
								+ "WHERE o.productionTime >= :startTime " + "AND o.productionTime <= :endTime "
								+ "AND o.unitOfProject.id = :unitOfProjectId "
								+ "AND o.tenant.id = :tenantId GROUP BY o.productCode ORDER BY o.productionTime ASC")
				.setParameter("startTime", startTime).setParameter("endTime", endTime).setParameter("unitOfProjectId", unitOfProjectId)
				.setParameter("tenantId", TenantResources.getTenant().getId());

		final List<?> resultList = query.getResultList();
		if (null == resultList || 0 >= resultList.size()) {
			return new ArrayList<InvoiceStatixTO>();
		}

		final ArrayList<InvoiceStatixTO> result = new ArrayList<InvoiceStatixTO>();

		final UnitOfProject unitOfProject = em.find(UnitOfProject.class, unitOfProjectId);
		Object[] columns = null;
		String productCode = null;
		Double quantity = null;
		Long count = null;
		InvoiceStatixTO row = null;
		String unitOfProjectName = unitOfProject.getName();
		Double amount = null;
		for (Object object : resultList) {
			columns = (Object[]) object;
			productCode = (String) columns[0];
			quantity = (Double) columns[1];
			count = (Long) columns[2];
			amount = (Double) columns[3];

			row = new InvoiceStatixTO();
			row.productCode = productCode;
			row.count = count;
			row.quantity = quantity;
			row.unitOfProjectName = unitOfProjectName;
			row.amount = amount;

			result.add(row);

		}
		return result;
	}

	@Override
	public List<InvoiceStatixTO> summarizeInvoice(Date startTime, Date endTime) {
		final Query query = em
				.createQuery(
						"SELECT o.productCode, SUM(o.totalQuantity), count(o.id), o.unitOfProject.name, SUM(o.totalAmount) "
								+ "FROM InvoiceBO o " + "WHERE o.productionTime >= :startTime " + "AND o.productionTime <= :endTime "
								+ "AND o.tenant.id = :tenantId GROUP BY o.unitOfProject.name, o.productCode ORDER BY o.productionTime ASC")
				.setParameter("startTime", startTime).setParameter("endTime", endTime)
				.setParameter("tenantId", TenantResources.getTenant().getId());

		final List<?> resultList = query.getResultList();
		if (null == resultList || 0 >= resultList.size()) {
			return new ArrayList<InvoiceStatixTO>();
		}

		final ArrayList<InvoiceStatixTO> result = new ArrayList<InvoiceStatixTO>();
		Object[] columns = null;
		String productCode = null;
		Double quantity = null;
		Long count = null;
		InvoiceStatixTO row = null;
		String unitOfProjectName = null;
		Double amount = null;
		for (Object object : resultList) {
			columns = (Object[]) object;
			productCode = (String) columns[0];
			quantity = (Double) columns[1];
			count = (Long) columns[2];
			unitOfProjectName = (String) columns[3];
			amount = (Double) columns[4];

			row = new InvoiceStatixTO();
			row.productCode = productCode;
			row.count = count;
			row.quantity = quantity;
			row.unitOfProjectName = unitOfProjectName;
			row.amount = amount;

			result.add(row);

		}
		return result;
	}

	@Override
	public String createBillRunTask(BillRunTaskTO<? extends BillRunTask> billRunTaskTO) {

		try {
			BillRunTask task = StandardObjectFactory.convertTO2Entity(billRunTaskTO, em);
			task.setGeneratedARAmount(0.0d);
			task.setGeneratedARQuantity(0.0d);
			task.setScannedInvoiceCount(0);
			task.setScannedProductivity(0.0d);
			em.persist(task);
			if (task instanceof SpecifiedUnitOfProjectBillRunTask) {
				SpecifiedUnitOfProjectBillRunTask singleBillRunTask = (SpecifiedUnitOfProjectBillRunTask) task;

				final UnitOfProject unitOfProject = singleBillRunTask.getSpecifiedUnitOfProject();
				if (null == unitOfProject) {
					throw new NullPointerException("The specified unitOfProject cann't be found when creating bill run task, please check!");
				}
				singleBillRunTask.setExecuteStartTime(new Timestamp(System.currentTimeMillis()));
				IBillRun newProxy = BizObjectUtil.newProxy(task);
				newProxy.doProcess();
				billRun(task, unitOfProject);
				newProxy.doFinish();
				singleBillRunTask.setSpecifiedUnitOfProject(unitOfProject);
				singleBillRunTask.setExecuteEndTime(new Timestamp(System.currentTimeMillis()));

			} else if (task instanceof AllBillRunTask) {
				AllBillRunTask allTask = (AllBillRunTask) task;

				final List<UnitOfProject> allProjects = em
						.createQuery("SELECT o FROM UnitOfProject o WHERE o.tenant.id = :tenantId and o.deleted = false")
						.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();
				if (null == allProjects) {
					throw new NullPointerException("There are no Unit of Projects need to be accounted");
				}
				allTask.setExecuteStartTime(new Timestamp(System.currentTimeMillis()));
				IBillRun newProxy = BizObjectUtil.newProxy(task);
				newProxy.doProcess();
				for (UnitOfProject unitOfProject : allProjects) {
					billRun(allTask, unitOfProject);
					allTask.setScannedUnitOfProjectQuantity(1);
				}
				newProxy.doFinish();
				allTask.setExecuteEndTime(new Timestamp(System.currentTimeMillis()));
			}
			return task.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	private void billRun(BillRunTask task, UnitOfProject unitOfProject) {
		if (null == task || null == unitOfProject) {
			throw new IllegalArgumentException("task and unitOfProject should not be null. Please check.");
		}
		// Verify the unitOfProject start to service
		//
		if (unitOfProject.getStartDate().after(new Date())) {
			throw new IllegalStateException("The unitOfProject has not yet started to service.");
		}
		// Verify Contract is active
		//
		ContractBO contract = unitOfProject.getContract();
		if (!ContractBO.ContractStateEnum.CONFIRMED.name().equals(contract.getState())) {
			task.setScannedInvoiceCount(0);
			task.setScannedProductivity(0.0);
			task.setGeneratedARAmount(0.0);
			task.setGeneratedARQuantity(0.0);
			return;
		}
		String billBaselineCombination = null;
		if (null != task.getBillBaselineCombination() && 0 <= task.getBillBaselineCombination().length()) {
			billBaselineCombination = task.getBillBaselineCombination();
		} else {
			billBaselineCombination = unitOfProject.getContract().getBillBaselineCombination();
		}

		if (Contract.BillBaselineCombination.PERIOD_ONLY.name().equals(billBaselineCombination)) {

			Date originalBillDate = unitOfProject.getNextBillDate();
			Date targetBillDate = null;

			// We are tending to generate nextBillDate according to Contract
			// and UnitOfProject
			if (null == originalBillDate) {
				originalBillDate = setNextBillDate(unitOfProject, contract, unitOfProject.getStartDate());
				targetBillDate = originalBillDate;
			}
			if (null != task.getTargetBillDate()) {
				targetBillDate = task.getTargetBillDate();
			}
			if (!targetBillDate.after(new Date())) {
				// Start to bill run
				//
				List<InvoiceBO> results = em
						.createQuery(
								"SELECT Object(o) FROM InvoiceBO AS o "
										+ "WHERE o.state = 'CONFIRMED' AND o.unitOfProject.id = :unitOfProjectId "
										+ "AND o.productionTime <= :nextBillDate " + "AND o.deleted = false "
										+ "AND o.tenant.id = :tenantId").setParameter("unitOfProjectId", unitOfProject.getId())
						.setParameter("nextBillDate", targetBillDate).setParameter("tenantId", TenantResources.getTenant().getId())
						.getResultList();
				if (null == results || 0 >= results.size()) {
					task.setScannedInvoiceCount(0);
					task.setScannedProductivity(0.0);
					task.setGeneratedARAmount(0.0);
					task.setGeneratedARQuantity(0.0);
					return;
				} else {
					for (InvoiceBO invoice : results) {
						doBillInvoice(invoice.getId());
						task.setScannedInvoiceCount(1);
						task.setScannedProductivity(invoice.getTotalQuantity());
						task.setGeneratedARQuantity(invoice.getTotalQuantity());
						task.setGeneratedARAmount(invoice.getTotalAmount());
					}
				}
				// Set nextBillDate
				//
				if (task.isNextBillDateUpdated()) {
					unitOfProject.setNextBillDate(setNextBillDate(unitOfProject, contract, targetBillDate));
				} else {
					unitOfProject.setNextBillDate(setNextBillDate(unitOfProject, contract, originalBillDate));
				}
			}

		} else if (Contract.BillBaselineCombination.VOLUME_ONLY.name().equals(billBaselineCombination)) {
			// Get billVolumeBaseline from Contract
			//
			int billVolumeBaseline = contract.getBillVolumeBaseline();
			int actualVolumn = 0;
			// Get the confirmed invoices to compute volume
			//
			List<InvoiceBO> results = em
					.createQuery(
							"SELECT Object(o) from InvoiceBO as o where o.state = 'CONFIRMED' and o.unitOfProject.id = :unitOfProjectId and o.deleted = false AND o.tenant.id = :tenantId")
					.setParameter("unitOfProjectId", unitOfProject.getId()).setParameter("tenantId", TenantResources.getTenant().getId())
					.getResultList();
			if (null == results || 0 >= results.size()) {
				task.setScannedInvoiceCount(0);
				task.setScannedProductivity(0.0);
				task.setGeneratedARAmount(0.0);
				task.setGeneratedARQuantity(0.0);
				return;
			} else {
				for (InvoiceBO invoice : results) {
					actualVolumn += invoice.getTotalQuantity();
				}

				if (actualVolumn >= billVolumeBaseline) {
					// Start to bill run
					//
					for (InvoiceBO invoice : results) {
						doBillInvoice(invoice.getId());
						task.setScannedInvoiceCount(1);
						task.setScannedProductivity(invoice.getTotalQuantity());
						task.setGeneratedARQuantity(invoice.getTotalQuantity());
						task.setGeneratedARAmount(invoice.getTotalAmount());
					}
				}
			}
			// Set nextBillDate
			//
			Date nextBillDate = unitOfProject.getNextBillDate();

			// We are tending to generate nextBillDate according to Contract
			// and UnitOfProject
			if (null == nextBillDate) {
				nextBillDate = setNextBillDate(unitOfProject, contract, unitOfProject.getStartDate());
			}
			if (null != nextBillDate) {
				unitOfProject.setNextBillDate(setNextBillDate(unitOfProject, contract, nextBillDate));
			}

		} else if (Contract.BillBaselineCombination.PERIOD_OR_VOLUME.name().equals(billBaselineCombination)) {
			boolean satisfiedVolumeRequirement = false;
			boolean satisfiedPeriodRequirement = false;

			satisfiedVolumeRequirement = checkVolumeRequirementSatisfied(unitOfProject);
			satisfiedPeriodRequirement = checkPeriodRequirementSatisfied(unitOfProject);
			if (satisfiedVolumeRequirement) {
				// Start to bill run on Volume
				// Get the confirmed invoices to compute volume
				//
				List<InvoiceBO> results = em
						.createQuery(
								"SELECT Object(o) from InvoiceBO as o where o.state = 'CONFIRMED' and o.unitOfProject.id = :unitOfProjectId and o.deleted = false AND o.tenant.id = :tenantId")
						.setParameter("unitOfProjectId", unitOfProject.getId())
						.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

				for (InvoiceBO invoice : results) {
					doBillInvoice(invoice.getId());
					task.setScannedInvoiceCount(1);
					task.setScannedProductivity(invoice.getTotalQuantity());
					task.setGeneratedARQuantity(invoice.getTotalQuantity());
					task.setGeneratedARAmount(invoice.getTotalAmount());
				}

			} else if (satisfiedPeriodRequirement) {
				Date nextBillDate = unitOfProject.getNextBillDate();

				// We are tending to generate nextBillDate according to Contract
				// and UnitOfProject
				if (null == nextBillDate) {
					nextBillDate = setNextBillDate(unitOfProject, contract, unitOfProject.getStartDate());
				}

				if (!nextBillDate.after(new Date())) {
					// Start to bill run
					//
					List<InvoiceBO> results = em
							.createQuery(
									"SELECT Object(o) from InvoiceBO as o where o.state = 'CONFIRMED' and o.unitOfProject.id = :unitOfProjectId and o.productionTime <= :nextBillDate and o.deleted = false AND o.tenant.id = :tenantId")
							.setParameter("unitOfProjectId", unitOfProject.getId()).setParameter("nextBillDate", nextBillDate)
							.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();
					if (null == results || 0 >= results.size()) {
						task.setScannedInvoiceCount(0);
						task.setScannedProductivity(0.0);
						task.setGeneratedARAmount(0.0);
						task.setGeneratedARQuantity(0.0);
						return;
					} else {
						for (InvoiceBO invoice : results) {
							doBillInvoice(invoice.getId());
							task.setScannedInvoiceCount(1);
							task.setScannedProductivity(invoice.getTotalQuantity());
							task.setGeneratedARQuantity(invoice.getTotalQuantity());
							task.setGeneratedARAmount(invoice.getTotalAmount());
							// Set nextBillDate
							//
							unitOfProject.setNextBillDate(setNextBillDate(unitOfProject, contract, nextBillDate));
						}
					}
				}
			}
			Date nextBillDate = unitOfProject.getNextBillDate();

			// We are tending to generate nextBillDate according to Contract
			// and UnitOfProject
			if (null == nextBillDate) {
				nextBillDate = setNextBillDate(unitOfProject, contract, unitOfProject.getStartDate());
			}
		} else {
			// For PERIOD_AND_VOLUME
			//
			boolean satisfiedVolumeRequirement = false;
			boolean satisfiedPeriodRequirement = false;
			if (satisfiedVolumeRequirement && satisfiedPeriodRequirement) {
				// Start to bill run on Volume
				// Get the confirmed invoices to compute volume
				//
				List<InvoiceBO> results = em
						.createQuery(
								"SELECT o from InvoiceBO o where o.state = 'CONFIRMED' and o.unitOfProject.id = :unitOfProjectId and o.deleted = false AND o.tenant.id = :tenantId")
						.setParameter("unitOfProjectId", unitOfProject.getId())
						.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

				for (InvoiceBO invoice : results) {
					doBillInvoice(invoice.getId());
					task.setScannedInvoiceCount(1);
					task.setScannedProductivity(invoice.getTotalQuantity());
					task.setGeneratedARQuantity(invoice.getTotalQuantity());
					task.setGeneratedARAmount(invoice.getTotalAmount());
				}
			} else {
				// Skip the bill run this time as requirement not matched.
				//
			}
			Date nextBillDate = unitOfProject.getNextBillDate();

			// We are tending to generate nextBillDate according to Contract
			// and UnitOfProject
			if (null == nextBillDate) {
				nextBillDate = setNextBillDate(unitOfProject, contract, unitOfProject.getStartDate());
			}
		}
	}

	private boolean checkPeriodRequirementSatisfied(UnitOfProject unitOfProject) {
		Date nextBillDate = unitOfProject.getNextBillDate();

		// We are tending to generate nextBillDate according to Contract
		// and UnitOfProject
		if (null == nextBillDate) {
			nextBillDate = setNextBillDate(unitOfProject, unitOfProject.getContract(), unitOfProject.getStartDate());
		}

		if (nextBillDate.after(new Date())) {
			return false;
		}
		return true;
	}

	private boolean checkVolumeRequirementSatisfied(UnitOfProject unitOfProject) {
		int billVolumeBaseline = unitOfProject.getContract().getBillVolumeBaseline();
		int actualVolumn = 0;
		// Get the confirmed invoices to compute volume
		//
		List<InvoiceBO> results = em
				.createQuery(
						"SELECT o from InvoiceBO o where o.state = 'CONFIRMED' and o.unitOfProject.id = :unitOfProjectId and o.deleted = false AND o.tenant.id = :tenantId")
				.setParameter("unitOfProjectId", unitOfProject.getId()).setParameter("tenantId", TenantResources.getTenant().getId())
				.getResultList();
		if (null == results || 0 >= results.size()) {
			return false;
		} else {
			for (InvoiceBO invoice : results) {
				actualVolumn += invoice.getTotalQuantity();
			}

			if (actualVolumn < billVolumeBaseline) {
				return false;
			}
		}
		return true;
	}

	private Date setNextBillDate(UnitOfProject unitOfProject, ContractBO contract, Date startDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		//
		if (Contract.BillPeriodBaselineUnit.YEAR.equals(contract.getBillPeriodBaselineUnit())) {
			// Ignore this scenario for the moment
			//
		} else if (Contract.BillPeriodBaselineUnit.MONTH.equals(contract.getBillPeriodBaselineUnit())) {
			calendar.add(Calendar.MONTH, contract.getBillPeriodBaseline());
		} else if (Contract.BillPeriodBaselineUnit.DAY.equals(contract.getBillPeriodBaselineUnit())) {
			calendar.add(Calendar.DAY_OF_YEAR, unitOfProject.getContract().getBillPeriodBaseline());
		} else if (Contract.BillPeriodBaselineUnit.UNSPECIFIED.equals(contract.getBillPeriodBaselineUnit())) {
			throw new IllegalStateException("The BillPeriodBaselineUnit should be specified!");
		}
		return calendar.getTime();
	}

	@Override
	public BillRunTaskTO<?> findBillRunTaskById(String billRunTaskId) {
		try {
			BillRunTask task = em.find(BillRunTask.class, billRunTaskId);
			if (task instanceof AllBillRunTask) {
				return TransferObjectFactory.createTransferObject(AllBillRunTaskTO.class, task);
			} else {
				return TransferObjectFactory.createTransferObject(SpecifiedUnitOfProjectBillRunTaskTO.class, task);
			}
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}
}
