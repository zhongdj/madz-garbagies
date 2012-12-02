/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.flex.proxy.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.security.delegate.RegisterDelegate;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.to.create.CompanyCTO;

/**
 * 
 * @author CleaNEr
 */
public class RegisterProxy {
	private RegisterDelegate instance;

	public RegisterProxy() {
		try {
			instance = RegisterDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(RegisterProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	public void registerCompany(CompanyCTO companyCTO) throws ApplicationServiceException {

		instance.registerCompany(companyCTO);
	}
}
