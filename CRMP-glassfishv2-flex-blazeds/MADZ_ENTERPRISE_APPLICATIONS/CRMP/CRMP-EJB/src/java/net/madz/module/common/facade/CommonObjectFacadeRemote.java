/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.common.to.update.MerchandiseUTO;
import net.madz.module.common.to.update.UnitOfMeasureUTO;

/**
 * 
 * @author CleaNEr
 */
@Remote
@RolesAllowed({ "ADMIN" })
public interface CommonObjectFacadeRemote {

	void updateMerchandise(List<MerchandiseUTO> merchandiseSet) throws ApplicationServiceException;

	// Merchandise Operations
	String createMerchandise(MerchandiseCTO merchandise) throws ApplicationServiceException;

	void removeMerchandise(List<String> ids) throws ApplicationServiceException;

	// Measure Unit Operations
	String createUnitOfMeasure(UnitOfMeasureCTO unit) throws ApplicationServiceException;

	void removeUnitOfMeasure(List<String> ids) throws ApplicationServiceException;

	void updateUnitOfMeasure(List<UnitOfMeasureUTO> units) throws ApplicationServiceException;
}
