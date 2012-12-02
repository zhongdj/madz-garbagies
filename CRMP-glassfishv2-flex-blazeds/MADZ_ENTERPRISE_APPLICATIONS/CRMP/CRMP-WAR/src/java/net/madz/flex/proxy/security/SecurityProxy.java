/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.flex.proxy.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.security.delegate.SecurityDelegate;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.facade.SecurityRemote;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author CleaNEr
 */
public class SecurityProxy implements SecurityRemote {

	private SecurityDelegate instance;

	public SecurityProxy() {
		try {
			instance = SecurityDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(RegisterProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	public void createAccount(AccountCTO accountCTO) throws ApplicationServiceException {
		instance.createAccount(accountCTO);
	}

	public void freezeAccount(AccountCTO accountCTO) throws ApplicationServiceException {
		instance.freezeAccount(accountCTO);
	}
}
