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
import net.madz.infra.security.facade.RegisterRemote;
import net.madz.infra.security.to.create.CompanyCTO;

/**
 * 
 * @author CleaNEr
 */
public class RegisterDelegate implements RegisterRemote {

	private static RegisterDelegate instance;
	private RegisterRemote server;

	private RegisterDelegate() throws Exception {
		try {
			server = (RegisterRemote) CachingServiceLocator.getInstance().getEJBObject(RegisterRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(RegisterDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized RegisterDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new RegisterDelegate();
		}
		return instance;
	}

	public void registerCompany(CompanyCTO companyCTO) throws ApplicationServiceException {
		server.registerCompany(companyCTO);
	}
}
