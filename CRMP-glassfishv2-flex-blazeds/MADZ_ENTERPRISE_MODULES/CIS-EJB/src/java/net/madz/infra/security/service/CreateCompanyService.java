/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.service;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.messaging.facade.MessageBusProxy;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.messaging.event.outbound.CreateAccountEvent;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.persistence.UserGroup;
import net.madz.infra.security.to.create.CompanyCTO;
import net.vicp.madz.security.login.factory.EncryptorFactory;

/**
 * 
 * @author Administrator
 */
public class CreateCompanyService extends TenantIdentifiedService {

	private static final long serialVersionUID = 2445669673755633615L;
	private CompanyCTO companyCTO;

	public CreateCompanyService(CompanyCTO companyCTO) {
		super(companyCTO);
		this.companyCTO = companyCTO;
	}

	@Override
	protected void execute() throws ApplicationServiceException {

		// 1. find mandatory relationships
		// 2. construct with mandatory fields and relationships
		tenant = new Tenant(companyCTO.getName());
		// 3. set optional fields
		tenant.setAddress(companyCTO.getAddress());
		tenant.setArtificialPersonName(companyCTO.getArtificialPersonName());

		// 4. set optional relationships
		// 5. maintain bidirection relationships
		// 5.1.maintain ManyToOne
		// 5.2 maintain ManyToMany
		// 5.3 maintain OneToOne
		// 6. persist
		em.persist(tenant);
		// 7. set result
		setResult(tenant.getId());

		List<UserGroup> groups = new LinkedList<UserGroup>();
		groups.add(em.find(UserGroup.class, UserGroup.ADMINGROUP));
		User account = new User(companyCTO.getAdminName(), EncryptorFactory.getInstance().getPasswordEncryptor()
				.encrypt(companyCTO.getAdminPass()), groups, tenant);
		em.persist(account);
	}

	@Override
	protected void postExec() throws ApplicationServiceException {
		super.postExec();
		String[] groups = { "ADMINGroup" };
		MessageBusProxy.getInstance().sendMessage(new CreateAccountEvent(companyCTO.getAdminName(), groups));
	}

	@Override
	protected void validate() throws ValidationException {
		UserGroup group = em.find(UserGroup.class, UserGroup.ADMINGROUP);
		if (group == null) {
			throw new ValidationException(UserGroup.ADMINGROUP + " cannot be found!");
		}

		try {
			em.createNamedQuery("User.findByUsername").setParameter("username", companyCTO.getAdminName()).getSingleResult();
			throw new ValidationException("Account Name:" + companyCTO.getAdminName() + "already exist!");
		} catch (NoResultException ex) {
		}
	}
}
