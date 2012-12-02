/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.client.common.biz.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.common.facade.CommonObjectFacadeRemote;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.common.to.update.MerchandiseUTO;
import net.madz.module.common.to.update.UnitOfMeasureUTO;

/**
 * 
 * @author CleaNEr
 */
public class CommonObjectDelegate implements CommonObjectFacadeRemote {

	private static CommonObjectDelegate instance;
	private CommonObjectFacadeRemote server;

	private CommonObjectDelegate() throws Exception {
		try {
			server = (CommonObjectFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(CommonObjectFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(CommonObjectDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized CommonObjectDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new CommonObjectDelegate();
		}
		return instance;
	}

	public String createMerchandise(MerchandiseCTO merchandise) throws ApplicationServiceException {
		return server.createMerchandise(merchandise);
	}


	public void removeMerchandise(List<String> ids) throws ApplicationServiceException {
		server.removeMerchandise(ids);
	}

	public void updateMerchandise(List<MerchandiseUTO> merchandiseSet) throws ApplicationServiceException {
		server.updateMerchandise(merchandiseSet);
	}

	@Override
	public String createUnitOfMeasure(UnitOfMeasureCTO unit) throws ApplicationServiceException {
		return server.createUnitOfMeasure(unit);
	}

	@Override
	public void removeUnitOfMeasure(List<String> ids) throws ApplicationServiceException {
		server.removeUnitOfMeasure(ids);
	}

	@Override
	public void updateUnitOfMeasure(List<UnitOfMeasureUTO> units) throws ApplicationServiceException {
		server.updateUnitOfMeasure(units);
	}
}
