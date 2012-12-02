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
import net.madz.module.common.facade.CommonObjectQueryFacadeRemote;
import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.common.to.query.UnitOfMeasureQTO;

/**
 * 
 * @author CleaNEr
 */
public class CommonObjectQueryDelegate implements CommonObjectQueryFacadeRemote {

	private static CommonObjectQueryDelegate instance;
	private CommonObjectQueryFacadeRemote server;

	private CommonObjectQueryDelegate() throws Exception {
		try {
			server = (CommonObjectQueryFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(
					CommonObjectQueryFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(CommonObjectDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized CommonObjectQueryDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new CommonObjectQueryDelegate();
		}
		return instance;
	}


	@Override
	public MerchandiseQTO findMerchandise(String id) {
		return server.findMerchandise(id);
	}

	@Override
	public List<MerchandiseQTO> findMerchandise() {
		return server.findMerchandise();
	}

	@Override
	public List<MerchandiseQTO> findMerchandise(MerchandiseQTO mqto) {
		return server.findMerchandise(mqto);
	}

	@Override
	public List<UnitOfMeasureQTO> findUnitOfMeasure(boolean deleted) {
		return server.findUnitOfMeasure(deleted);
	}

	@Override
	public UnitOfMeasureQTO findUnitOfMeasureById(String id) {
		return server.findUnitOfMeasureById(id);
	}
}
