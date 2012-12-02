package net.madz.module.contract.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.account.entity.Account;
import net.madz.module.account.entity.Contact;
import net.madz.module.account.facade.AccountFacadeLocal;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.facade.CommonObjectQueryFacadeLocal;
import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractRatePlanComponentTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.contract.activeobject.IContractBO;
import net.madz.module.contract.entity.ConstructionPart;
import net.madz.module.contract.entity.Contract.BillPeriodBaselineUnit;
import net.madz.module.contract.entity.Contract.PaymentTerm;
import net.madz.module.contract.entity.ContractBO;
import net.madz.module.contract.entity.ContractRatePlanComponent;
import net.madz.module.contract.entity.Project;
import net.madz.module.contract.entity.UnitOfProject;
import net.madz.standard.StandardObjectFactory;
import net.vicp.madz.infra.binding.TransferObjectFactory;

@Stateless
@RolesAllowed({ "ADMIN" })
@Interceptors({ AuditInterceptor.class, ValidationInterceptor.class })
public class ContractFacade implements ContractFacadeRemote, ContractFacadeLocal {

	private static final String UNSPECIFIED = "UNSPECIFIED";
	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;

	@SuppressWarnings("unused")
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	@EJB
	private AccountFacadeLocal accountFacade;

	@EJB
	private CommonObjectQueryFacadeLocal commonObjectQueryFacade;

	@Override
	public String createConstructionPart(ConstructionPartTO part) {
		if (null == part) {
			throw new NullPointerException();
		}
		ConstructionPart result = TenantResources.newEntity(ConstructionPart.class);
		try {
			result.setName(part.getName());
			result.setDescription(part.getDescription());
			result.setDeleted(false);
			em.persist(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
		return result.getId();
	}

	@Override
	public String createContract(ContractTO contract) {
		try {
			ContractBO result = StandardObjectFactory.convertTO2Entity(ContractBO.class, contract, em);
			em.persist(result);
			return result.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public String createContractRatePlanComponent(ContractRatePlanComponentTO component) {
		ContractRatePlanComponent result;
		try {
			result = StandardObjectFactory.convertTO2Entity(ContractRatePlanComponent.class, component, em);
			em.persist(result);
			return result.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public String createProject(ProjectTO project) {
		if (null == project) {
			throw new NullPointerException();
		}
		try {
			Project result = TenantResources.newEntity(Project.class);
			result.setName(project.getName());
			// TODO Tracy Lu Add Address
			result.setBillToContact(em.find(Contact.class, project.getBillToContact().getId()));
			result.setPayerContact(em.find(Contact.class, project.getPayerContact().getId()));
			result.setShipToContact(em.find(Contact.class, project.getShipToContact().getId()));
			result.setSoldToContact(em.find(Contact.class, project.getSoldToContact().getId()));

			em.persist(result);
			return result.getId();
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public String createUnitOfProject(UnitOfProjectTO unit) {
		if (null == unit) {
			throw new NullPointerException();
		}
		try {
			UnitOfProject result = TenantResources.newEntity(UnitOfProject.class);
			result.setName(unit.getName());
			result.setStartDate(unit.getStartDate());
			result.setEndDate(unit.getEndDate());
			result.setDeleted(false);
			result.setNextBillDate(unit.getNextBillDate());
			result.setBillToContact(em.find(Contact.class, unit.getBillToContact().getId()));
			result.setPayerContact(em.find(Contact.class, unit.getPayerContact().getId()));
			result.setShipToContact(em.find(Contact.class, unit.getShipToContact().getId()));
			result.setSoldToContact(em.find(Contact.class, unit.getSoldToContact().getId()));
			if (null != unit.getProjectId()) {
				result.setProject(em.find(Project.class, unit.getProjectId()));
			}
			//
			em.persist(result);
			return result.getId();
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConstructionPartTO> findConstructionParts() {
		List<ConstructionPart> list = null;
		try {
			list = em.createQuery("select Object(o) from ConstructionPart as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<ConstructionPartTO> result = TransferObjectFactory.assembleTransferObjectList(list, ConstructionPartTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public ContractTO findContractById(String id) {
		final ContractBO contract = em.find(ContractBO.class, id);
		try {
			return TransferObjectFactory.createTransferObject(ContractTO.class, contract);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractTO> findContracts() {
		List<ContractBO> list = null;
		try {
			list = em.createQuery("SELECT Object(o) FROM ContractBO as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();
			List<ContractTO> result;
			result = TransferObjectFactory.assembleTransferObjectList(list, ContractTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public ProjectTO findProjectById(String id) {
		final Project project = em.find(Project.class, id);

		try {
			return TransferObjectFactory.createTransferObject(ProjectTO.class, project);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectTO> findProjects() {
		List<Project> list = null;
		try {
			list = em.createQuery("select Object(o) from Project as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<ProjectTO> result = TransferObjectFactory.assembleTransferObjectList(list, ProjectTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public UnitOfProjectTO findUnitOfProjectById(String id) {
		final UnitOfProject project = em.find(UnitOfProject.class, id);

		try {
			return TransferObjectFactory.createTransferObject(UnitOfProjectTO.class, project);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitOfProjectTO> findUnitOfProjects() {
		List<UnitOfProject> list = null;
		try {
			list = em.createQuery("select Object(o) from UnitOfProject as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<UnitOfProjectTO> result = TransferObjectFactory.assembleTransferObjectList(list, UnitOfProjectTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public ConstructionPart getUnspecifiedConstructionPart() {
		try {
			return (ConstructionPart) em.createQuery("SELECT o FROM ConstructionPart o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			ConstructionPart part = TenantResources.newEntity(ConstructionPart.class);// new
																						// ConstructionPart();
			part.setName(UNSPECIFIED);
			em.persist(part);
			return part;
		}
	}

	@Override
	public ContractBO getUnspecifiedContract() {

		try {
			return (ContractBO) em.createQuery("SELECT o FROM ContractBO o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			final ContractBO unspecifiedContract = TenantResources.newEntity(ContractBO.class);// new
																								// Contract();
			unspecifiedContract.setName(UNSPECIFIED);
			unspecifiedContract.setPaymentTerm(PaymentTerm.UNSPECIFIED);
			unspecifiedContract.setVersion(0);

			final Account unspecifiedAccount = accountFacade.getUnspecifiedAccount();
			unspecifiedContract.setBillToAccount(unspecifiedAccount);
			unspecifiedContract.setPayerAccount(unspecifiedAccount);
			unspecifiedContract.setShipToAccount(unspecifiedAccount);
			unspecifiedContract.setSoldToAccount(unspecifiedAccount);

			unspecifiedContract.setEffectiveStartDate(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			try {
				unspecifiedContract.setEffectiveEndDate(format.parse("2099-12-12"));
			} catch (ParseException e) {
				throw new BusinessException(e);
			}

			em.persist(unspecifiedContract);
			return unspecifiedContract;
		}

	}

	@Override
	public Project getUnspecifiedProject() {
		try {
			return (Project) em.createQuery("SELECT o FROM Project o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			Project project = TenantResources.newEntity(Project.class);// new
																		// Project();
			project.setName(UNSPECIFIED);

			project.setOwner(accountFacade.getUnspecifiedAccount());
			em.persist(project);
			return project;
		}
	}

	@Override
	public UnitOfProject getUnspecifiedUnitOfProject() {
		try {
			return (UnitOfProject) em.createQuery("SELECT o FROM UnitOfProject o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			final UnitOfProject unitOfProject = TenantResources.newEntity(UnitOfProject.class);// new
																								// UnitOfProject();
			unitOfProject.setName(UNSPECIFIED);

			final Contact unspecifiedContact = accountFacade.getUnspecifiedContact();

			unitOfProject.setBillToContact(unspecifiedContact);
			unitOfProject.setPayerContact(unspecifiedContact);
			unitOfProject.setShipToContact(unspecifiedContact);
			unitOfProject.setSoldToContact(unspecifiedContact);

			final Project unspecifiedProject = getUnspecifiedProject();
			unitOfProject.setProject(unspecifiedProject);

			em.persist(unitOfProject);
			return unitOfProject;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllConstructionParts() {
		List<ConstructionPart> list = null;
		try {
			list = em.createQuery("select Object(o) from ConstructionPart as o WHERE o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		for (ConstructionPart part : list) {
			em.remove(part);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllContracts() {
		List<ContractBO> result = null;
		try {
			result = em.createQuery("Select Object(o) from Contract o where o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();
			for (ContractBO contract : result) {
				em.remove(contract);
			}
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllProjects() {
		List<Project> list = null;
		try {
			list = em.createQuery("SELECT OBJECT(o) from Project o where o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();
			for (Project project : list) {
				em.remove(project);
			}
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllUnitOfProjects() {
		List<UnitOfProject> list = null;
		try {
			list = em.createQuery("select Object(o) from UnitOfProject as o WHERE o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		for (UnitOfProject unit : list) {
			em.remove(unit);
		}
	}

	@Override
	public void hardDeleteConstructionParts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			em.remove(em.find(ConstructionPart.class, id));
		}
	}

	@Override
	public void hardDeleteContracts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			em.remove(em.find(ContractBO.class, id));
		}
	}

	@Override
	public void hardDeleteProjects(String[] ids) {
		if (null == ids || 0 >= ids.length) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			em.remove(em.find(Project.class, id));
		}

	}

	@Override
	public void hardDeleteRatePlanComponent(String[] ids) {
		for (String id : ids) {
			ContractRatePlanComponent component = em.find(ContractRatePlanComponent.class, id);
			if (null != component) {
				component.setContract(null);
				em.remove(component);
			}
		}

	}

	@Override
	public void hardDeleteUnitOfProjects(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			final UnitOfProject unitOfProject = em.find(UnitOfProject.class, id);
			unitOfProject.setContract(null);
			em.remove(unitOfProject);
		}
	}

	private void softDeleteConstructionPart(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			ConstructionPart result = em.find(ConstructionPart.class, id);
			result.setDeleted(true);
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void softDeleteConstructionParts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteConstructionPart(id);
		}
	}

	private void softDeleteContract(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		ContractBO result = em.find(ContractBO.class, id);
		result.setDeleted(true);
		try {
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}

	}

	@Override
	public void softDeleteContracts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteContract(id);
		}
	}

	private void softDeleteProject(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		Project result = em.find(Project.class, id);
		result.setDeleted(true);
		try {
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
		em.merge(result);
	}

	@Override
	public void softDeleteProjects(String[] ids) {
		if (null == ids || 0 >= ids.length) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteProject(id);
		}

	}

	@Override
	public void softDeleteRatePlanComponent(String[] ids) {
		final ArrayList<ContractRatePlanComponent> result = new ArrayList<ContractRatePlanComponent>();
		for (String id : ids) {
			result.add(em.find(ContractRatePlanComponent.class, id));
		}

		for (ContractRatePlanComponent component : result) {
			component.setDeleted(true);
			em.merge(component);
		}

	}

	private void softDeleteUnitOfProject(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			UnitOfProject result = em.find(UnitOfProject.class, id);
			result.setDeleted(true);
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void softDeleteUnitOfProjects(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteUnitOfProject(id);
		}
	}

	public void testCascadeCreate() {

		ContractBO contract = TenantResources.newEntity(ContractBO.class);
		contract.setActive(true);
		contract.setName("test");
		contract.setPaymentTerm(PaymentTerm.NET15);
		contract.setBillPeriodBaseline(1);
		contract.setBillPeriodBaselineUnit(BillPeriodBaselineUnit.DAY);
		contract.setEffectiveStartDate(new Date());
		contract.setEffectiveEndDate(new Date());
		contract.setBillToAccount(accountFacade.getUnspecifiedAccount());
		contract.setSoldToAccount(accountFacade.getUnspecifiedAccount());
		contract.setShipToAccount(accountFacade.getUnspecifiedAccount());
		contract.setPayerAccount(accountFacade.getUnspecifiedAccount());
		contract.setBillVolumnBaseline(1000);
		contract.setBillVolumeBaselineUnit(commonObjectQueryFacade.getUnspecifiedUnitOfMeasure());

		UnitOfProject project = TenantResources.newEntity(UnitOfProject.class);
		project.setName("Tracy's Project");
		project.setBillToContact(accountFacade.getUnspecifiedContact());
		project.setPayerContact(accountFacade.getUnspecifiedContact());
		project.setShipToContact(accountFacade.getUnspecifiedContact());
		project.setSoldToContact(accountFacade.getUnspecifiedContact());
		project.setEndDate(new Date());
		project.setStartDate(new Date());
		project.setContract(contract);

		project = TenantResources.newEntity(UnitOfProject.class);
		project.setName("Barry's Project");
		project.setBillToContact(accountFacade.getUnspecifiedContact());
		project.setPayerContact(accountFacade.getUnspecifiedContact());
		project.setShipToContact(accountFacade.getUnspecifiedContact());
		project.setSoldToContact(accountFacade.getUnspecifiedContact());
		project.setEndDate(new Date());
		project.setStartDate(new Date());
		project.setContract(contract);

		ContractRatePlanComponent component = TenantResources.newEntity(ContractRatePlanComponent.class);
		component.setActive(true);
		component.setChargeRate(50.0D);
		component.setChargeModel("PerUnit");
		component.setChargeType("Usage");
		component.setContract(contract);
		component.setMerchandise(commonObjectQueryFacade.getUnspecifiedMerchandise());
		component.setUnitOfMeasure(commonObjectQueryFacade.getUnspecifiedUnitOfMeasure());

		em.persist(contract);
	}

	private void updateConstructionPart(ConstructionPartTO part) {
		if (null == part) {
			throw new NullPointerException();
		}
		ConstructionPart result = em.find(ConstructionPart.class, part.getId());
		try {
			result.setName(part.getName());
			result.setDescription(part.getDescription());
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void updateConstructionParts(List<ConstructionPartTO> parts) {
		if (null == parts) {
			throw new NullPointerException();
		}
		for (ConstructionPartTO part : parts) {
			updateConstructionPart(part);
		}
	}

	@Override
	public void updateContracts(List<ContractTO> contracts) {
		final String tenantId = TenantResources.getTenant().getId();
		ContractBO result = null;
		for (ContractTO contract : contracts) {
			result = em.find(ContractBO.class, contract.getId());
			if (!tenantId.equals(result.getTenant().getId())) {
				throw new BusinessException("Illegal Access to Other Tenants Resources.");
			}

			if (!ContractBO.ContractStateEnum.DRAFT.name().equals(result.getState())) {
				continue;
			}

			result.setName(contract.getName());
			result.setEffectiveStartDate(contract.getEffectiveStartDate());
			result.setEffectiveEndDate(contract.getEffectiveEndDate());
			result.setPaymentTerm(PaymentTerm.valueOf(contract.getPaymentTerm()));
			result.setBillVolumnBaseline(contract.getBillVolumnBaseline());
			if (null != contract.getBillVolumnBaselineUnitId()) {
				result.setBillVolumeBaselineUnit(em.find(UnitOfMeasure.class, contract.getBillVolumnBaselineUnitId()));
			}
			result.setBillPeriodBaseline(contract.getBillPeriodBaseline());
			if (null != contract.getBillPeriodBaselineUnit()) {
				result.setBillPeriodBaselineUnit(BillPeriodBaselineUnit.valueOf(contract.getBillPeriodBaselineUnit()));
			}

			result.setBillToAccount(em.find(Account.class, contract.getBillToAccount().getId()));
			result.setShipToAccount(em.find(Account.class, contract.getShipToAccount().getId()));
			result.setSoldToAccount(em.find(Account.class, contract.getSoldToAccount().getId()));
			result.setPayerAccount(em.find(Account.class, contract.getPayerAccount().getId()));

			em.merge(result);
		}

	}

	@Override
	public void updateProjects(List<ProjectTO> projects) {
		for (ProjectTO project : projects) {
			Project result = em.find(Project.class, project.getId());
			result.setName(project.getName());
			// TODO Tracy Lu Add Address
			result.setBillToContact(em.find(Contact.class, project.getBillToContact().getId()));
			result.setPayerContact(em.find(Contact.class, project.getPayerContact().getId()));
			result.setShipToContact(em.find(Contact.class, project.getShipToContact().getId()));
			result.setSoldToContact(em.find(Contact.class, project.getSoldToContact().getId()));
			em.merge(result);
		}

	}

	@Override
	public void updateRatePlanComponents(List<ContractRatePlanComponentTO> ratePlanComponents) {
		ContractRatePlanComponent ratePlanComponent = null;
		for (ContractRatePlanComponentTO component : ratePlanComponents) {
			ratePlanComponent = em.find(ContractRatePlanComponent.class, component.getId());
			ratePlanComponent.setChargeModel(component.getChargeModel());
			ratePlanComponent.setChargeType(component.getChargeType());
			ratePlanComponent.setChargeRate(component.getChargeRate());
			final ContractBO contract = em.find(ContractBO.class, component.getContract().getId());
			ratePlanComponent.setContract(contract);
			final Merchandise merchandise = em.find(Merchandise.class, component.getMerchandise().getId());
			ratePlanComponent.setMerchandise(merchandise);
			em.merge(ratePlanComponent);
		}

	}

	private void updateUnitOfProject(UnitOfProjectTO unit) {
		if (null == unit || null == unit.getId()) {
			throw new NullPointerException();
		}
		UnitOfProject result = em.find(UnitOfProject.class, unit.getId());
		result.setName(unit.getName());
		result.setStartDate(unit.getStartDate());
		result.setEndDate(unit.getEndDate());

		Set<ConstructionPart> parts = new HashSet<ConstructionPart>();
		if (null != unit.getParts()) {
			for (ConstructionPartTO part : unit.getParts()) {
				parts.add(em.find(ConstructionPart.class, part));
			}
		}
		result.setParts(parts);
		if (null != unit.getProjectId() && 0 <= unit.getProjectId().trim().length()) {
			result.setProject(em.find(Project.class, unit.getProjectId()));
		}

		if (null != unit.getContractId()) {
			result.setContract(em.find(ContractBO.class, unit.getContractId()));
		}
		result.setBillToContact(em.find(Contact.class, unit.getBillToContact().getId()));
		result.setPayerContact(em.find(Contact.class, unit.getPayerContact().getId()));
		result.setShipToContact(em.find(Contact.class, unit.getShipToContact().getId()));
		result.setSoldToContact(em.find(Contact.class, unit.getSoldToContact().getId()));
		em.merge(result);
	}

	@Override
	public void updateUnitOfProjects(List<UnitOfProjectTO> unitOfProjects) {
		if (null == unitOfProjects) {
			throw new NullPointerException();
		}
		for (UnitOfProjectTO unit : unitOfProjects) {
			updateUnitOfProject(unit);
		}

	}

	@Override
	public void confirmContract(String contractId) {
		if (null == contractId || 0 >= contractId.trim().length()) {
			throw new NullPointerException();
		}

		final IContractBO contractBO = BizObjectUtil.find(ContractBO.class, contractId);
		contractBO.doConfirm();
	}
}
