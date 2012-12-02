/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BizBean;
import net.madz.infra.biz.service.core.ApplicationServiceBus;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.to.create.CompanyCTO;

/**
 * 
 * @author Administrator
 */
@Stateless
public class RegisterBean extends BizBean implements RegisterRemote, RegisterLocal {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	public void registerCompany(CompanyCTO companyCTO) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "create-company", companyCTO);
	}
}
