/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.facade;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BizBean;
import net.madz.infra.biz.service.core.ApplicationServiceBus;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.common.to.update.MerchandiseUTO;
import net.madz.module.common.to.update.UnitOfMeasureUTO;

/**
 * 
 * @author CleaNEr
 */
@Stateless
@RolesAllowed({ "ADMIN" })
public class CommonObjectFacadeBean extends BizBean implements CommonObjectFacadeRemote, CommonObjectFacadeLocal {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	public String createMerchandise(MerchandiseCTO merchandise) throws ApplicationServiceException {
		return (String) ApplicationServiceBus.invokeService(this, "create-merchandise", merchandise);
	}

	public void updateMerchandise(List<MerchandiseUTO> merchandiseSet) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "update-batch-merchandise", merchandiseSet);
	}

	public void removeMerchandise(List<String> ids) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "remove-batch-merchandise", ids);
	}

	public String createUnitOfMeasure(UnitOfMeasureCTO unit) throws ApplicationServiceException {
		return (String) ApplicationServiceBus.invokeService(this, "create-unit-of-measure", unit);
	}

	public void removeUnitOfMeasure(List<String> ids) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "remove-unit-of-measure", ids);
	}

	public void updateUnitOfMeasure(List<UnitOfMeasureUTO> units) throws ApplicationServiceException {
		ApplicationServiceBus.invokeService(this, "update-unit-of-measure", units);
	}

}
