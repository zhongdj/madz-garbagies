/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.common.to.query.UnitOfMeasureQTO;
import net.vicp.madz.infra.binding.TransferObjectFactory;

/**
 * 
 * @author CleaNEr
 */
@Stateless
@RolesAllowed({ "SA", "ADMIN", "OP" })
// @TransactionManagement(TransactionManagementType.BEAN)
public class CommonObjectQueryFacadeBean implements CommonObjectQueryFacadeRemote, CommonObjectQueryFacadeLocal {

	private static final String UNSPECIFIED = "UNSPECIFIED";
	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	private List<Merchandise> find(MerchandiseQTO mqto) {
		try {
			if (mqto == null) {
				return null;
			}

			String code = "%";
			String name = "%";
			String categoryId = "%";
			if (mqto.getCode() != null && mqto.getCode().trim().length() > 0) {
				code = mqto.getCode().trim() + "%";
			}
			if (mqto.getName() != null && mqto.getName().trim().length() > 0) {
				name = mqto.getName().trim() + "%";
			}

			List<Merchandise> list = em
					.createQuery(
							"select Object(o) from Merchandise as o WHERE o.tenant.id = :tenantId and o.code like :code and o.name like :name and o.category =:category")
					.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("code", code).setParameter("name", name)
					.setParameter("category", CategoryEnum.valueOf(mqto.getCategoryName())).getResultList();
			return list;

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<MerchandiseQTO> findMerchandise() {
		List<Merchandise> list = null;
		try {
			list = em.createQuery("select Object(o) from Merchandise as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			return TransferObjectFactory.assembleTransferObjectList(list, MerchandiseQTO.class);

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}

	}

	public List<MerchandiseQTO> findMerchandise(MerchandiseQTO mqto) {
		List<MerchandiseQTO> result = new ArrayList<MerchandiseQTO>();
		List<Merchandise> list = find(mqto);
		try {
			return TransferObjectFactory.assembleTransferObjectList(list, MerchandiseQTO.class);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	public MerchandiseQTO findMerchandise(String id) {
		Merchandise merchandise = em.find(Merchandise.class, id);
		MerchandiseQTO result = null;
		try {
			result = TransferObjectFactory.createTransferObject(MerchandiseQTO.class, merchandise);
			return result;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<UnitOfMeasureQTO> findUnitOfMeasure(boolean deleted) {
		List<UnitOfMeasureQTO> result = new ArrayList<UnitOfMeasureQTO>();
		List<UnitOfMeasure> list = null;
		try {
			list = em.createQuery("select Object(o) from UnitOfMeasure as o WHERE o.tenant.id = :tenantId and o.deleted = :deleted")
					.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("deleted", deleted).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		try {
			return TransferObjectFactory.assembleTransferObjectList(list, UnitOfMeasureQTO.class);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	public UnitOfMeasureQTO findUnitOfMeasureById(String id) {
		UnitOfMeasure uom = em.find(UnitOfMeasure.class, id);
		try {
			return TransferObjectFactory.createTransferObject(UnitOfMeasureQTO.class, uom);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	// @Override
	// public Category getUnspecifiedCategory() {
	// final String unspecifiedName = UNSPECIFIED;
	//
	// try {
	// return (Category)
	// em.createQuery("SELECT o FROM Category o WHERE o.tenant.id = :tenantId AND o.name = :name")
	// .setParameter("name", unspecifiedName).setParameter("tenantId",
	// TenantResources.getTenant().getId()).getSingleResult();
	// } catch (Exception ex) {
	// Category category = TenantResources.newEntity(Category.class);// new
	// // Category();
	// category.setName(unspecifiedName);
	// em.persist(category);
	// return category;
	// }
	// }

	@Override
	public Merchandise getUnspecifiedMerchandise() {

		try {
			return (Merchandise) em.createQuery("SELECT o FROM Merchandise o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			Merchandise merchandise = TenantResources.newEntity(Merchandise.class);// new
																					// Merchandise();
			merchandise.setName(UNSPECIFIED);
			merchandise.setCode(UNSPECIFIED);
			merchandise.setDescription(UNSPECIFIED);
			merchandise.setSuggestPrice(-1.0d);
			merchandise.setCategory(CategoryEnum.CONCRETE);
			merchandise.setUom(getUnspecifiedUnitOfMeasure());
			em.persist(merchandise);
			return merchandise;
		}

	}

	@Override
	public synchronized UnitOfMeasure getUnspecifiedUnitOfMeasure() {
		try {
			return (UnitOfMeasure) em.createQuery("SELECT o FROM UnitOfMeasure o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			UnitOfMeasure unitOfMeasure = TenantResources.newEntity(UnitOfMeasure.class);// new
																							// UnitOfMeasure();
			unitOfMeasure.setName(UNSPECIFIED);
			unitOfMeasure.setDescription(UNSPECIFIED);
			em.persist(unitOfMeasure);
			return unitOfMeasure;
		}
	}
}
