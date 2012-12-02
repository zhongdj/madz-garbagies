/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BizBean;
import net.madz.infra.biz.service.core.ApplicationServiceBus;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author CleaNEr
 */
@Stateless
public class SecurityBean extends BizBean implements SecurityRemote, SecurityLocal {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	@RolesAllowed({ "ADMIN", "OP" })
	public void createAccount(AccountCTO accountCTO) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "CreateAccount", accountCTO);
	}

	@RolesAllowed({ "ADMIN" })
	public void freezeAccount(AccountCTO accountCTO) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "FreezeAccount", accountCTO);
	}
}
