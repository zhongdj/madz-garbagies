package net.madz.module.production.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.contract.entity.ConstructionPart;
import net.madz.module.contract.entity.UnitOfProject;
import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.module.production.ProductionRecordStatixTO;
import net.madz.module.production.ProductionRecordTO;
import net.madz.module.production.activeobject.IProductionRecordBO;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.module.production.entity.ProductionRecordBO;
import net.madz.service.etl.to.RawProductionRecordTO;
import net.vicp.madz.infra.binding.TransferObjectFactory;

@Stateless
@RolesAllowed({ "ADMIN" })
@Interceptors({ AuditInterceptor.class, ValidationInterceptor.class })
public class ProductionFacade implements ProductionFacadeRemote, ProductionFacadeLocal {

	private static final String UNSPECIFIED = "UNSPECIFIED";

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext context;

	@Override
	public String createConcreteMixingPlant(ConcreteMixingPlantTO plant) {

		ConcreteMixingPlant result = TenantResources.newEntity(ConcreteMixingPlant.class);
		try {
			result.setName(plant.getName());
			if (null != plant.getOperatorId()) {
				result.setOperator(em.find(User.class, plant.getOperatorId()));
			}
			result.setDeleted(false);
			em.persist(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.getId();
	}

	@Override
	public String createProductionRecord(ProductionRecordTO record) {
		if (null == record) {
			throw new NullPointerException();
		}
		ProductionRecordBO result = TenantResources.newEntity(ProductionRecordBO.class);
		result.setReferenceNumber(record.getReferenceNumber());
		result.setQuantity(record.getQuantity());
		result.setProductionTime(record.getProductionTime());
		result.setDescription(record.getDescription());
		result.setTruckNumber(record.getTruckNumber());
		result.setUnitOfProject(em.find(UnitOfProject.class, record.getUnitOfProjectId()));
		result.setMixingPlant(em.find(ConcreteMixingPlant.class, record.getMixingPlantId()));

		result.setPart(em.find(ConstructionPart.class, record.getPartId()));
		for (MerchandiseQTO qto : record.getMerchandise()) {
			result.addMerchandise(em.find(Merchandise.class, qto.getId()));
		}
		result.setUom(em.find(UnitOfMeasure.class, record.getUomId()));
		result.setDeleted(false);
		em.persist(result);
		return result.getId();
	}

	@Override
	public void createProductionRecord(ProductionRecordTO[] records) {
		if (null == records) {
			return;
		}
		for (ProductionRecordTO productionRecordTO : records) {
			createProductionRecord(productionRecordTO);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConcreteMixingPlantTO> findConcreteMixingPlants() {
		List<ConcreteMixingPlant> list = null;
		try {
			list = em.createQuery("select Object(o) from ConcreteMixingPlant as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<ConcreteMixingPlantTO> result = TransferObjectFactory.assembleTransferObjectList(list, ConcreteMixingPlantTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductionRecordTO> findProductionRecords() {
		List<ProductionRecordBO> list = null;
		try {
			list = em.createQuery("select Object(o) from ProductionRecordBO as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<ProductionRecordTO> result = TransferObjectFactory.assembleTransferObjectList(list, ProductionRecordTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public ConcreteMixingPlant getUnspecifiedConcreteMixingPlant() {
		try {
			return (ConcreteMixingPlant) em
					.createQuery("SELECT o FROM ConcreteMixingPlant o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			final ConcreteMixingPlant plant = TenantResources.newEntity(ConcreteMixingPlant.class);
			plant.setName(UNSPECIFIED);
			em.persist(plant);
			return plant;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllConcreteMixingPlants() {
		List<ConcreteMixingPlant> list = null;
		try {
			list = em.createQuery("select Object(o) from ConcreteMixingPlant as o WHERE o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		for (ConcreteMixingPlant record : list) {
			em.remove(record);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllProductionRecords() {
		List<ProductionRecordBO> list = null;
		try {
			list = em.createQuery("select Object(o) from ProductionRecordBO as o WHERE o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		for (ProductionRecordBO record : list) {
			em.remove(record);
		}
	}

	@Override
	public void hardDeleteConcreteMixingPlants(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			em.remove(em.find(ConcreteMixingPlant.class, id));
		}

	}

	@Override
	public void hardDeleteProductionRecords(String[] ids) {
		if (null == ids || 0 >= ids.length) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			em.remove(em.find(ProductionRecordBO.class, id));
		}

	}

	/**
	 * 
	 */
	@Override
	public void importProductionRecords(RawProductionRecordTO[] records) {

		for (RawProductionRecordTO productionRecordImportTO : records) {
			System.out.println(productionRecordImportTO);
		}
	}

	private void softDeleteConcreteMixingPlant(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			ConcreteMixingPlant result = em.find(ConcreteMixingPlant.class, id);
			result.setDeleted(true);
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}

	}

	@Override
	public void softDeleteConcreteMixingPlants(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteConcreteMixingPlant(id);
		}
	}

	private void softDeleteProductionRecord(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			ProductionRecordBO result = em.find(ProductionRecordBO.class, id);
			result.setDeleted(true);
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void softDeleteProductionRecords(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteProductionRecord(id);
		}

	}

	private void updateConcreteMixingPlant(ConcreteMixingPlantTO plant) {
		if (null == plant) {
			throw new NullPointerException();
		}
		try {
			ConcreteMixingPlant result = em.find(ConcreteMixingPlant.class, plant.getId());
			result.setName(plant.getName());
			if (null != plant.getOperatorId()) {
				result.setOperator(em.find(User.class, plant.getOperatorId()));
			}
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void updateConcreteMixingPlant(List<ConcreteMixingPlantTO> plants) {
		if (null == plants) {
			throw new NullPointerException();
		}
		for (ConcreteMixingPlantTO plant : plants) {
			updateConcreteMixingPlant(plant);
		}

	}

	private void updateProductionRecord(ProductionRecordTO record) {
		if (null == record) {
			throw new NullPointerException();
		}
		try {
			ProductionRecordBO result = em.find(ProductionRecordBO.class, record.getId());
			final String unitOfProjectId = record.getUnitOfProjectId();
			if (null != unitOfProjectId) {
				final UnitOfProject unitOfProject = em.find(UnitOfProject.class, unitOfProjectId);
				if (null != unitOfProject) {
					result.setUnitOfProject(unitOfProject);
				}
			}
			result.setQuantity(record.getQuantity());
			result.setReferenceNumber(record.getReferenceNumber());
			result.setDescription(record.getDescription());
			result.setUom(em.find(UnitOfMeasure.class, record.getUomId()));
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}

	}

	@Override
	public void updateProductionRecords(List<ProductionRecordTO> records) {
		if (null == records) {
			throw new NullPointerException();
		}
		for (ProductionRecordTO to : records) {
			updateProductionRecord(to);
		}

	}

	@Override
	public ProductionRecordTO findProductionRecordById(String id) {
		if (null == id || 0 >= id.length()) {
			throw new NullPointerException();
		}
		ProductionRecordTO result = null;
		try {
			result = TransferObjectFactory.createTransferObject(ProductionRecordTO.class, em.find(ProductionRecordBO.class, id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ConcreteMixingPlantTO findConcreteMixingPlantById(String id) {
		if (null == id || 0 >= id.length()) {
			throw new NullPointerException();
		}
		ConcreteMixingPlantTO result = null;
		try {
			result = TransferObjectFactory.createTransferObject(ConcreteMixingPlantTO.class, em.find(ConcreteMixingPlant.class, id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// We find Production Records with State "Confirmed" or "Lost"
	public List<ProductionRecordBO> findProductionRecords(String plantId, String projectId, Date start, Date end) {
		if (null == start || null == end || null == plantId || null == projectId) {
			throw new NullPointerException();
		}
		return em
				.createQuery(
						"SELECT object(o) from ProductionRecordBO as o where o.tenant.id = :tenantId and "
								+ "o.deleted = false and o.mixingPlant.id = :plantId and o.unitOfProject.id = :projectId and "
								+ "o.unitOfProject.contract.state='CONFIRMED' and (o.state = 'Confirmed' or o.state = 'Lost') "
								+ "order by o.productionTime").setParameter("tenantId", TenantResources.getTenant().getId())
				.setParameter("plantId", plantId).setParameter("projectId", projectId).getResultList();
	}

	@Override
	public List<ProductionRecordBO> findProductionRecords(String plantId, String accountId, String projectId, Date start, Date end,
			String state) {
		if (null == start || null == end || null == plantId) {
			throw new NullPointerException();
		}
		if (null == accountId || 0 >= accountId.trim().length()) {
			accountId = "*";
		}
		if (null == projectId || 0 >= projectId.trim().length()) {
			projectId = "*";
		}
		// return em
		// .createQuery(
		// "SELECT object(o) from ProductionRecordBO as o where o.tenant.id = :tenantId and o.deleted = false and o.mixingPlant.id = :plantId and o.unitOfProject.contract.billToAccount.id like :accountId  and o.unitOfProject.contract.state = 'CONFIRMED' and o.unitOfProject.id like :projectId and o.productionTime >= :start and o.productionTime <= :end and o.state = :state order by o.productionTime")
		// .setParameter("tenantId", TenantResources.getTenant().getId())
		// .setParameter("plantId", plantId)
		// .setParameter("accountId", "%" + accountId + "%")
		// .setParameter("projectId", "%" + projectId + "%")
		// .setParameter("start", start).setParameter("end", end)
		// .setParameter("state", state).getResultList();
		return em
				.createQuery(
						"SELECT object(o) from ProductionRecordBO as o where o.tenant.id = :tenantId and o.deleted = false and o.mixingPlant.id = :plantId and o.unitOfProject.contract.state='CONFIRMED' order by o.productionTime")
				.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("plantId", plantId).getResultList();
	}

	@Override
	public void doConfirmProductionRecord(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		final IProductionRecordBO productionRecordBO = BizObjectUtil.find(ProductionRecordBO.class, id);
		productionRecordBO.doConfirm();
	}

	@Override
	public void doLoseProductionRecord(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		final IProductionRecordBO productionRecordBO = BizObjectUtil.find(ProductionRecordBO.class, id);
		productionRecordBO.doLose();
	}

	@Override
	public void doBillProductionRecord(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		final IProductionRecordBO productionRecordBO = BizObjectUtil.find(ProductionRecordBO.class, id);
		productionRecordBO.doBill();
	}

	@Override
	public List<ConcreteMixingPlant> findConcreteMixingPlantsEntity() {
		List<ConcreteMixingPlant> list = null;
		try {
			list = em.createQuery("select Object(o) from ConcreteMixingPlant as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			return list;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime, String unitOfProjectId) {
		final Query query = em
				.createQuery(
						"SELECT o.productCode, SUM(o.quantity), count(o.id) " + "FROM ProductionRecordBO o "
								+ "WHERE o.productionTime >= :startTime " + "AND o.productionTime <= :endTime "
								+ "AND o.unitOfProject.id = :unitOfProjectId " + "AND o.tenant.id = :tenantId GROUP BY o.productCode")
				.setParameter("startTime", startTime).setParameter("endTime", endTime).setParameter("unitOfProjectId", unitOfProjectId)
				.setParameter("tenantId", TenantResources.getTenant().getId());

		final List<?> resultList = query.getResultList();
		if (null == resultList || 0 >= resultList.size()) {
			return new ArrayList<ProductionRecordStatixTO>();
		}

		final ArrayList<ProductionRecordStatixTO> result = new ArrayList<ProductionRecordStatixTO>();

		final UnitOfProject unitOfProject = em.find(UnitOfProject.class, unitOfProjectId);
		Object[] columns = null;
		String productCode = null;
		Double quantity = null;
		Long count = null;
		ProductionRecordStatixTO row = null;
		String unitOfProjectName = unitOfProject.getName();
		for (Object object : resultList) {
			columns = (Object[]) object;
			productCode = (String) columns[0];
			quantity = (Double) columns[1];
			count = (Long) columns[2];

			row = new ProductionRecordStatixTO();
			row.productCode = productCode;
			row.count = count;
			row.quantity = quantity;
			row.unitOfProjectName = unitOfProjectName;

			result.add(row);

		}
		return result;
	}

	@Override
	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime) {
		final Query query = em
				.createQuery(
						"SELECT o.productCode, SUM(o.quantity), count(o.id), o.unitOfProject.name " + "FROM ProductionRecordBO o "
								+ "WHERE o.productionTime >= :startTime " + "AND o.productionTime <= :endTime "
								+ "AND o.tenant.id = :tenantId GROUP BY o.unitOfProject.name, o.productCode ORDER BY o.productionTime ASC")
				.setParameter("startTime", startTime).setParameter("endTime", endTime)
				.setParameter("tenantId", TenantResources.getTenant().getId());

		final List<?> resultList = query.getResultList();
		if (null == resultList || 0 >= resultList.size()) {
			return new ArrayList<ProductionRecordStatixTO>();
		}

		final ArrayList<ProductionRecordStatixTO> result = new ArrayList<ProductionRecordStatixTO>();
		Object[] columns = null;
		String productCode = null;
		Double quantity = null;
		Long count = null;
		ProductionRecordStatixTO row = null;
		String unitOfProjectName = null;
		for (Object object : resultList) {
			columns = (Object[]) object;
			productCode = (String) columns[0];
			quantity = (Double) columns[1];
			count = (Long) columns[2];
			unitOfProjectName = (String) columns[3];

			row = new ProductionRecordStatixTO();
			row.productCode = productCode;
			row.count = count;
			row.quantity = quantity;
			row.unitOfProjectName = unitOfProjectName;

			result.add(row);

		}
		return result;
	}
}
