package net.madz.service.pr2invoice.facade;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.util.TenantResources;
import net.madz.infra.util.BizObjectUtil;
import net.madz.interceptor.AuditInterceptor;
import net.madz.interceptor.TenantCacheInterceptor;
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.account.entity.Account;
import net.madz.module.billing.InvoiceNumberHelp;
import net.madz.module.billing.InvoiceTO;
import net.madz.module.billing.entity.InvoiceBO;
import net.madz.module.billing.entity.InvoiceItem;
import net.madz.module.billing.facade.InvoiceFacadeRemote;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.contract.entity.ContractRatePlanComponent;
import net.madz.module.contract.entity.UnitOfProject;
import net.madz.module.production.activeobject.IProductionRecordBO;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.module.production.entity.ProductionRecordBO;
import net.madz.module.production.entity.ProductionRecordBO.ProductionRecordStateEnum;
import net.madz.module.production.facade.ProductionFacadeLocal;
import net.madz.service.pr2invoice.AccountRunTaskTO;
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
public class ProductionRecordsToInvoice implements ProductionRecordsToInvoiceRemote {

	@EJB
	ProductionFacadeLocal productionInstance;

	@EJB
	InvoiceFacadeRemote invoiceInstance;

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;

	@SuppressWarnings("unused")
	@Resource(name = "SessionContext")
	private SessionContext sc;

	@SuppressWarnings("unchecked")
	@Override
	public String createAccountRunTask(AccountRunTaskTO<? extends AccountRunTask> accountRunTaskTO) {
		try {
			AccountRunTask task = StandardObjectFactory.convertTO2Entity(accountRunTaskTO, em);
			task.setGeneratedInvoiceAmount(0);
			task.setGeneratedInvoiceQuantity(0);
			task.setScannedProductionRecordQuantity(0);
			task.setScannedProductivity(0);
			em.persist(task);
			if (task instanceof SpecifiedUnitOfProjectAccountRunTask) {
				SpecifiedUnitOfProjectAccountRunTask singleAccountTask = (SpecifiedUnitOfProjectAccountRunTask) task;

				final UnitOfProject unitOfProject = singleAccountTask.getSpecifiedUnitOfProject();
				if (null == unitOfProject) {
					throw new NullPointerException(
							"The specified unitOfProject cann't be found when creating account run task, please check!");
				}
				singleAccountTask.setExecuteStartTime(new Timestamp(System.currentTimeMillis()));
				IAccountRun newProxy = BizObjectUtil.newProxy(task);
				newProxy.doProcess();
				convertProductionRecordsToInvoice(task, unitOfProject.getId());
				newProxy.doFinish();
				singleAccountTask.setSpecifiedUnitOfProject(unitOfProject);
				singleAccountTask.setExecuteEndTime(new Timestamp(System.currentTimeMillis()));

			} else if (task instanceof AllAccountRunTask) {
				AllAccountRunTask allTask = (AllAccountRunTask) task;

				final List<UnitOfProject> allProjects = em
						.createQuery("SELECT o FROM UnitOfProject o WHERE o.tenant.id = :tenantId and o.deleted = false")
						.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();
				if (null == allProjects) {
					throw new NullPointerException("There are no Unit of Projects need to be accounted");
				}
				allTask.setExecuteStartTime(new Timestamp(System.currentTimeMillis()));
				IAccountRun newProxy = BizObjectUtil.newProxy(task);
				newProxy.doProcess();
				for (UnitOfProject unitOfProject : allProjects) {
					convertProductionRecordsToInvoice(allTask, unitOfProject.getId());
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

	private List<InvoiceTO> convertProductionRecordsToInvoice(AccountRunTask task, String unitOfProjectId) {
		if (null == task) {
			throw new NullPointerException("The task is null when converting production record to invoice. Please check!");
		}
		if (task.getStartDate() == null || task.getEndDate() == null) {
			throw new NullPointerException("The start date and end date should be specified when converting production record to invoices.");
		}
		Date start = task.getStartDate();
		Date end = task.getEndDate();
		// Find out all mixing plants of tenant
		final List<ConcreteMixingPlant> plants = productionInstance.findConcreteMixingPlantsEntity();
		if (null == plants) {
			throw new NullPointerException("There is no Concrete Mixing plant. Please add before converting production record to invoice.");
		}
		List<InvoiceBO> invoices = new ArrayList<InvoiceBO>();
		List<ProductionRecordBO> records = new ArrayList<ProductionRecordBO>();

		String truckNumber = "";
		Account billToAccount = null;
		Account payerAccount = null;
		Account shipToAccount = null;
		Account soldToAccount = null;

		List<ProductionRecordBO> recordsOfSameTruck = new ArrayList<ProductionRecordBO>();

		// collect how many production record scanned
		int scannedProductionRecordQuantity = 0;

		for (ConcreteMixingPlant plant : plants) {
			System.out.println("plant:" + plant.getName());
			// Generate query conditions
			records = productionInstance.findProductionRecords(plant.getId(), unitOfProjectId, start, end);
			if (null == records || 0 >= records.size()) {
				System.out.println("There is no production records returned from plant:" + plant.getName());
				continue;
			}
			System.out.println("There are " + records.size() + "production records" + "returned from plant: " + plant.getName());
			recordsOfSameTruck = new ArrayList<ProductionRecordBO>();
			for (ProductionRecordBO record : records) {
				// collect statistic information
				scannedProductionRecordQuantity++;
				if (!record.getTruckNumber().equals(truckNumber)) {
					// Convert production records to invoice items
					generateInvoiceOfSameTruck(invoices, billToAccount, payerAccount, shipToAccount, soldToAccount, recordsOfSameTruck);

					truckNumber = record.getTruckNumber();
					// referenceNumber = record.getReferenceNumber();
					billToAccount = record.getUnitOfProject().getContract().getBillToAccount();
					payerAccount = record.getUnitOfProject().getContract().getPayerAccount();
					shipToAccount = record.getUnitOfProject().getContract().getShipToAccount();
					soldToAccount = record.getUnitOfProject().getContract().getSoldToAccount();
					recordsOfSameTruck.add(record);
				} else {
					recordsOfSameTruck.add(record);
				}
			}
			// To handle the last 1 or several production records
			generateInvoiceOfSameTruck(invoices, billToAccount, payerAccount, shipToAccount, soldToAccount, recordsOfSameTruck);
		}
		if (null == invoices || 0 == invoices.size()) {
			System.out.println("There is no invoices generated at last.");
		} else {
			System.out.println("There are " + invoices.size() + "invoices generated");
		}

		// Collect statistic information
		// generatedInvoiceQuantity: the number of generated invoices
		// generatedInvoiceAmount: the total amount of generate invoices
		// scannedProductivity: the total quantity of generated invoices
		// scannedProductionRecordQuantity: the number of scanned production record
		int generatedInvoiceQuantity = 0;
		double generatedInvoiceAmount = 0.0d;
		double scannedProductivity = 0.0d;
		for (InvoiceBO invoice : invoices) {
			generatedInvoiceQuantity++;
			generatedInvoiceAmount += invoice.getTotalAmount();
			scannedProductivity += invoice.getTotalQuantity();
		}
		task.setGeneratedInvoiceQuantity(generatedInvoiceQuantity);
		task.setGeneratedInvoiceAmount(generatedInvoiceAmount);
		task.setScannedProductionRecordQuantity(scannedProductionRecordQuantity);
		task.setScannedProductivity(scannedProductivity);

		List<InvoiceTO> results = null;
		try {
			if (null != invoices) {
				results = TransferObjectFactory.assembleTransferObjectList(invoices, InvoiceTO.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<InvoiceTO> convertProductionRecordsToInvoice(Date start, Date end, String accountId, String unitOfProjectId) {
		if (start == null || end == null) {
			throw new NullPointerException("The start date and end date should be specified when converting production record to invoices.");
		}
		// Find out all mixing plants of tenant
		final List<ConcreteMixingPlant> plants = productionInstance.findConcreteMixingPlantsEntity();
		if (null == plants) {
			throw new NullPointerException("There is no Concrete Mixing plant. Please add before converting production record to invoice.");
		}

		List<InvoiceBO> invoices = new ArrayList<InvoiceBO>();
		List<ProductionRecordBO> records = new ArrayList<ProductionRecordBO>();

		String truckNumber = "";
		Account billToAccount = null;
		Account payerAccount = null;
		Account shipToAccount = null;
		Account soldToAccount = null;
		// String referenceNumber = ""; //we can ignore the referenceNumber when
		// converting

		List<ProductionRecordBO> recordsOfSameTruck = new ArrayList<ProductionRecordBO>();
		for (ConcreteMixingPlant plant : plants) {
			System.out.println("plant:" + plant.getName());
			// Generate query conditions
			records = productionInstance.findProductionRecords(plant.getId(), accountId, unitOfProjectId, start, end,
					ProductionRecordStateEnum.CONFIRMED.name());
			recordsOfSameTruck = new ArrayList<ProductionRecordBO>();
			if (null == records || 0 >= records.size()) {
				System.out.println("There is no production records returned from plant:" + plant.getName());
				continue;
			}
			System.out.println("There are " + records.size() + "production records" + "returned from plant: " + plant.getName());

			for (ProductionRecordBO record : records) {
				if (!record.getTruckNumber().equals(truckNumber)) {
					// Convert production records to invoice items
					generateInvoiceOfSameTruck(invoices, billToAccount, payerAccount, shipToAccount, soldToAccount, recordsOfSameTruck);

					truckNumber = record.getTruckNumber();
					// referenceNumber = record.getReferenceNumber();
					billToAccount = record.getUnitOfProject().getContract().getBillToAccount();
					payerAccount = record.getUnitOfProject().getContract().getPayerAccount();
					shipToAccount = record.getUnitOfProject().getContract().getShipToAccount();
					soldToAccount = record.getUnitOfProject().getContract().getSoldToAccount();
					recordsOfSameTruck.add(record);
				} else {
					recordsOfSameTruck.add(record);
				}
			}
			// To handle the last 1 or several production records
			generateInvoiceOfSameTruck(invoices, billToAccount, payerAccount, shipToAccount, soldToAccount, recordsOfSameTruck);
		}

		if (null == invoices || 0 == invoices.size()) {
			System.out.println("There is no invoices generated at last.");
		} else {
			System.out.println("There are " + invoices.size() + "invoices generated");
		}
		List<InvoiceTO> results = null;
		try {
			if (null != invoices) {
				results = TransferObjectFactory.assembleTransferObjectList(invoices, InvoiceTO.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	private void generateInvoiceOfSameTruck(List<InvoiceBO> invoices, Account billToAccount, Account payerAccount, Account shipToAccount,
			Account soldToAccount, List<ProductionRecordBO> recordsOfSameTruck) {
		if (0 < recordsOfSameTruck.size()) {
			System.out.println("production records to be in a same invoice:" + recordsOfSameTruck.toString());
			// Create an invoice
			InvoiceBO invoice = TenantResources.newEntity(InvoiceBO.class);

			// InvoiceTO invoice = new InvoiceTO();
			invoice.setInvoiceNumber(InvoiceNumberHelp.getInvoiceNumber(TenantResources.getTenant()));
			invoice.setBillToAccount(billToAccount);
			invoice.setPayerAccount(payerAccount);
			invoice.setShipToAccount(shipToAccount);
			invoice.setSoldToAccount(soldToAccount);

			// invoice.setReferenceNumber(referenceNumber);
			System.out.println("Invoice id is:" + invoice.getId());
			// Create invoice items and add them into invoice.
			Set<InvoiceItem> invoiceItems = new HashSet<InvoiceItem>();
			boolean isLost = false;
			for (ProductionRecordBO productionRecord : recordsOfSameTruck) {

				invoice.setProductCode(productionRecord.getProductCode());

				if (null == invoice.getProductionTime()) {
					invoice.setProductionTime(productionRecord.getProductionTime());
				}

				if (null == invoice.getUnitOfProject()) {
					invoice.setUnitOfProject(productionRecord.getUnitOfProject());
				}

				for (Merchandise merchandise : productionRecord.getMerchandiseSet()) {
					InvoiceItem item = TenantResources.newEntity(InvoiceItem.class);
					item.setMerchandise(merchandise);
					item.setQuantity(productionRecord.getQuantity());
					item.setUnitOfMeasure(productionRecord.getUom());
					double chargeRate = 0.0d;
					Set<ContractRatePlanComponent> contractRatePlanComponentSet = productionRecord.getUnitOfProject().getContract()
							.getContractRatePlanComponentSet();
					for (ContractRatePlanComponent rate : contractRatePlanComponentSet) {
						if (merchandise == rate.getMerchandise()) {
							chargeRate = rate.getChargeRate();
							break;
						}
					}
					item.setAmount(productionRecord.getQuantity() * chargeRate);
					item.setInvoice(invoice);
					invoiceItems.add(item);
				}

				// If the state of production record is LOST, need to add a
				// comment in invoice.
				// update the state of production record to BILLED
				if (productionRecord.getState() == ProductionRecordBO.ProductionRecordStateEnum.LOST.name()) {
					isLost = true;
				}
				IProductionRecordBO newProxy = BizObjectUtil.newProxy((IProductionRecordBO) productionRecord);
				newProxy.doBill();
			}

			// clear after converting
			recordsOfSameTruck.clear();
			invoice.setCommment("The invoice is for Lost Production Records.");
			em.persist(invoice);
			invoices.add(invoice);
			// if (0 >= invoices.size()) {
			// System.out.println("The invoices generated is null.");
			// }
		}
	}

	@Override
	public AccountRunTaskTO<?> findAccountRunTaskById(String accountRunTaskId) {
		try {
			AccountRunTask accountRunTask = em.find(AccountRunTask.class, accountRunTaskId);
			if (accountRunTask instanceof AllAccountRunTask) {
				return TransferObjectFactory.createTransferObject(AllAccountRunTaskTO.class, accountRunTask);
			} else {
				return TransferObjectFactory.createTransferObject(SpecifiedUnitOfProjectAccountRunTaskTO.class, accountRunTask);
			}
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public List<AccountRunTaskTO<?>> findAccountRunTasks() {
		final List<AccountRunTask> accountRunTaskList = em
				.createQuery("SELECT Object(o) FROM AccountRunTask o WHERE o.tenant.id = :tenantId ORDER BY o.createdOn")
				.setParameter("tenantId", TenantResources.getTenant().getId()).setMaxResults(2000).getResultList();

		if (null == accountRunTaskList || 0 >= accountRunTaskList.size()) {
			return new ArrayList<AccountRunTaskTO<?>>();
		}

		try {
			final List<AccountRunTaskTO<?>> result = new ArrayList<AccountRunTaskTO<?>>();
			for (AccountRunTask accountRunTask : accountRunTaskList) {
				if (accountRunTask instanceof AllAccountRunTask) {
					result.add(TransferObjectFactory.createTransferObject(AllAccountRunTaskTO.class, accountRunTask));
				} else {
					result.add(TransferObjectFactory.createTransferObject(SpecifiedUnitOfProjectAccountRunTaskTO.class, accountRunTask));
				}
			}

			return result;
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

}
