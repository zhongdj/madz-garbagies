/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.NoResultException;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author Tracy
 */
public class FreezeAccountService extends ApplicationService {

	private AccountCTO accountCTO;

	public FreezeAccountService(AccountCTO accountCTO) {
		this.accountCTO = accountCTO;
	}

	@Override
	protected void execute() throws ApplicationServiceException {
		User account = null;
		try {
			account = em.find(User.class, accountCTO.getAccountName());
			account.freeze();
			em.persist(account);
		} catch (NoResultException ex) {
			Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
			throw new ApplicationServiceException("Account:" + accountCTO.getAccountName() + "not exists");
		}
	}

	@Override
	protected void validate() throws ValidationException {
		if (null == accountCTO) {
			throw new ValidationException("The Account must not be empty!");
		}
	}
}
