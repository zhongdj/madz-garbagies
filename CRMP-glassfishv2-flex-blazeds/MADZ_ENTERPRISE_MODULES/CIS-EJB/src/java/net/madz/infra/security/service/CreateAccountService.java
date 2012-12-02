/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.service;

import java.util.LinkedList;
import java.util.List;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.messaging.facade.MessageBusProxy;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.messaging.event.outbound.CreateAccountEvent;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.persistence.UserGroup;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author CleaNEr
 */
public class CreateAccountService extends TenantIdentifiedService {

	private static final long serialVersionUID = -601018178146770645L;
	private AccountCTO accountCTO;

	public CreateAccountService(AccountCTO accountCTO) {
		super(accountCTO);
		this.accountCTO = accountCTO;
	}

	@Override
	protected void execute() throws ApplicationServiceException {
		// 1. find mandatory relationships
		// 2. construct with mandatory fields and relationships
		List<UserGroup> groups = new LinkedList<UserGroup>();
		for (String group : accountCTO.getGroups()) {
			groups.add(em.find(UserGroup.class, group.trim()));
		}
		User account = new User(accountCTO.getAccountName(), accountCTO.getPassword(), groups, tenant);
		// 3. set optional fields
		// 4. set optional relationships
		// 5. maintain bidirection relationships
		// 5.1.maintain ManyToOne
		// 5.2 maintain ManyToMany
		for (String group : accountCTO.getGroups()) {
			em.find(UserGroup.class, group.trim()).addUser(account);
		}
		// 5.3 maintain OneToOne
		// 6. persist
		em.persist(account);
		// 7. set result
		setResult(account.getId());
	}

	@Override
	protected void postExec() throws ApplicationServiceException {
		super.postExec();
		String[] groups = accountCTO.getGroups().toArray(new String[accountCTO.getGroups().size()]);

		MessageBusProxy.getInstance().sendMessage(new CreateAccountEvent(accountCTO.getAccountName(), groups));
	}

	@Override
	protected void validate() throws ValidationException {
		// 1. validation null argument
		if (null == accountCTO) {
			throw new ValidationException("The Account object can not be null!");
		}
		// 1.5 validate whether the object duplicated
		if (null != em.find(AccountCTO.class, accountCTO.getAccountName())) {
			throw new ValidationException("The Account already exists!");
		}
		List<String> groups = accountCTO.getGroups();
		// 2. validate mandatory relationship
		for (String group : groups) {
			if (null == group || group.trim().length() <= 0) {
				throw new ValidationException("Some group are empty!");
			}
			UserGroup userGroup = em.find(UserGroup.class, group.trim());
			if (null == userGroup) {
				throw new ValidationException("The UserGroup " + group + "can not be found!");
			}
		}
	}
}
