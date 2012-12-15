package net.madz.service.provisioning;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.interceptor.AuditInterceptor;
import net.madz.interceptor.TenantCacheInterceptor;
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.entity.Contact.ContactType;
import net.madz.module.account.facade.AccountFacadeLocal;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.common.facade.CommonObjectFacadeLocal;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.contract.entity.Contract.PaymentTerm;
import net.madz.module.contract.facade.ContractFacadeLocal;

@Stateless
@RolesAllowed({ "SA" })
@Interceptors({ TenantCacheInterceptor.class, AuditInterceptor.class, ValidationInterceptor.class })
public class ProvisioningFacade implements ProvisioningFacadeRemote {

	private static final String UNSPECIFIED = "UNSPECIFIED";
	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	@EJB
	private CommonObjectFacadeLocal commonFacade;
	@EJB
	private ContractFacadeLocal contractFacade;
	@EJB
	private AccountFacadeLocal accountFacade;

	@Override
	public void initBasicData(String tenantId) {
		if (null == tenantId || 0 >= tenantId.trim().length()) {
			throw new NullPointerException();
		}
		// create Unknown unitOfMeasure
		UnitOfMeasureCTO unitOfMeasure = new UnitOfMeasureCTO();
		unitOfMeasure.setName(UNSPECIFIED);
		unitOfMeasure.setDescription(UNSPECIFIED);
		String unitOfMeasureId = "";

		// create Unknown merchandise
		MerchandiseCTO merchandise = new MerchandiseCTO();
		merchandise.setCode("Unkown");
		merchandise.setDescription(UNSPECIFIED);
		merchandise.setName(UNSPECIFIED);
		merchandise.setSuggestPrice(-1.0d);
		String merchandiseId = "";

		try {
			unitOfMeasureId = commonFacade.createUnitOfMeasure(unitOfMeasure);
			merchandise.setCategory(CategoryEnum.UNSPECIFIED.name());
			merchandise.setUnitOfMeasureId(unitOfMeasureId);
			merchandiseId = commonFacade.createMerchandise(merchandise);
		} catch (ApplicationServiceException e) {
			e.printStackTrace();
		}
		// create Unknown constructionPart
		ConstructionPartTO constructionPart = new ConstructionPartTO();
		constructionPart.setName(UNSPECIFIED);
		constructionPart.setDescription(UNSPECIFIED);
		String constructionPartId = contractFacade.createConstructionPart(constructionPart);
		constructionPart.setId(constructionPartId);

		// create Unknown account
		AccountTO account = new AccountTO();
		account.setName(UNSPECIFIED);
		account.setShortName(UNSPECIFIED);
		String accountId = accountFacade.createAccount(account);
		account.setId(accountId);

		// create Unknown contact
		ContactTO contact = new ContactTO();
		contact.setAccountId(accountId);
		contact.setContactType(ContactType.BillTo.name());
		contact.setName(UNSPECIFIED);
		String contactId = accountFacade.createContact(contact);
		contact.setId(contactId);

		// create Unknown project
		ProjectTO project = new ProjectTO();
		project.setName(UNSPECIFIED);
		project.setBillToContact(contact);
		project.setPayerContact(contact);
		project.setShipToContact(contact);
		project.setSoldToContact(contact);
		String projectId = contractFacade.createProject(project);

		// create Unknown unitOfProject
		UnitOfProjectTO unitOfProject = new UnitOfProjectTO();
		unitOfProject.setName(UNSPECIFIED);
		unitOfProject.setBillToContact(contact);
		unitOfProject.setPayerContact(contact);
		unitOfProject.setShipToContact(contact);
		unitOfProject.setSoldToContact(contact);
		// unitOfProject.setProjectId(projectId);

		unitOfProject.setStartDate(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			unitOfProject.setEndDate(format.parse("2999-12-12"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Set<ConstructionPartTO> parts = new HashSet<ConstructionPartTO>();
		parts.add(constructionPart);
		unitOfProject.setParts(parts);

		String unitOfProjectId = contractFacade.createUnitOfProject(unitOfProject);
		unitOfProject.setId(unitOfProjectId);

		// create Unknown contract

		ContractTO contract = new ContractTO();
		contract.setName("Unkown");
		// contract.setBillMethod(UNSPECIFIED);
		contract.setPaymentTerm(PaymentTerm.UNSPECIFIED.name());
		contract.setBillToAccount(account);
		contract.setPayerAccount(account);
		contract.setShipToAccount(account);
		contract.setSoldToAccount(account);
		contract.setEffectiveStartDate(new Date());
		try {
			contract.setEffectiveEndDate(format.parse("2099-12-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<UnitOfProjectTO> unitOfProjects = new ArrayList<UnitOfProjectTO>();
		unitOfProjects.add(unitOfProject);
		contract.setUnitOfProjects(unitOfProjects);
		String contractId = contractFacade.createContract(contract);
	}

}
