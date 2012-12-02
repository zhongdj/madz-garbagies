/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author CleaNEr
 */
@Remote
public interface SecurityRemote {

	@RolesAllowed({ "ADMIN", "OP" })
	void createAccount(AccountCTO accountCTO) throws ApplicationServiceException;

	@RolesAllowed({ "ADMIN" })
	void freezeAccount(AccountCTO accountCTO) throws ApplicationServiceException;
}
