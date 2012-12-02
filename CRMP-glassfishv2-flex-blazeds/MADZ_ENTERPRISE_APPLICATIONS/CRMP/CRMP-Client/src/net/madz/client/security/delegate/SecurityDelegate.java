/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.client.security.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.facade.SecurityRemote;
import net.madz.infra.security.to.create.AccountCTO;

/**
 * 
 * @author CleaNEr
 */
public class SecurityDelegate implements SecurityRemote {

	private static SecurityDelegate instance;
	private SecurityRemote server;

	private SecurityDelegate() throws Exception {
		try {
			server = (SecurityRemote) CachingServiceLocator.getInstance().getEJBObject(SecurityRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(SecurityDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized SecurityDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new SecurityDelegate();
		}
		return instance;
	}

	public void createAccount(AccountCTO account) throws ApplicationServiceException {
		server.createAccount(account);
	}

	public void freezeAccount(AccountCTO arg0) throws ApplicationServiceException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
