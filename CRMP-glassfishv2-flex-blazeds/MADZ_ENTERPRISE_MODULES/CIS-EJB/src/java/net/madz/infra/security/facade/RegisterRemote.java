/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import javax.ejb.Remote;

import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.to.create.CompanyCTO;

/**
 * 
 * @author Administrator
 */
@Remote
public interface RegisterRemote {

	public void registerCompany(CompanyCTO companyCTO) throws ApplicationServiceException;
}
