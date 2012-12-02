/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.flex.proxy.common;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.common.biz.delegate.CommonObjectDelegate;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.common.to.update.MerchandiseUTO;
import net.madz.module.common.to.update.UnitOfMeasureUTO;

/**
 * 
 * @author CleaNEr
 */
public class CommonObjectProxy {

	private CommonObjectDelegate delegate;

	public CommonObjectProxy() {
		try {
			delegate = CommonObjectDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(CommonObjectProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	public String createMerchandise(MerchandiseCTO merchandise) throws ApplicationServiceException {
		return delegate.createMerchandise(merchandise);
	}

	public void removeMerchandise(List<String> ids) throws ApplicationServiceException {
		delegate.removeMerchandise(ids);
	}

	public void updateMerchandise(List<MerchandiseUTO> merchandiseSet) throws ApplicationServiceException {
		delegate.updateMerchandise(merchandiseSet);
	}

	public String createUnitOfMeasure(UnitOfMeasureCTO unit) throws ApplicationServiceException {
		return delegate.createUnitOfMeasure(unit);
	}

	public void removeUnitOfMeasure(List<String> ids) throws ApplicationServiceException {
		delegate.removeUnitOfMeasure(ids);
	}

	public void updateUnitOfMeasure(List<UnitOfMeasureUTO> units) throws ApplicationServiceException {
		delegate.updateUnitOfMeasure(units);
	}
}
