/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.infra.security.facade;

import javax.ejb.Local;

import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author CleaNEr
 */
@Local
public interface SecurityLocal {

	void createAccount(AccountCTO accountCTO) throws ApplicationServiceException;

	void freezeAccount(AccountCTO accountCTO) throws ApplicationServiceException;
}
