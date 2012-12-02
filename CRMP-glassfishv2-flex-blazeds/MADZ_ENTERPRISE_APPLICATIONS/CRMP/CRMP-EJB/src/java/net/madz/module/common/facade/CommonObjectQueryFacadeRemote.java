package net.madz.module.common.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.common.to.query.UnitOfMeasureQTO;

/**
 * 
 * @author CleaNEr
 */
@Remote
@RolesAllowed(value = { "ADMIN", "OP" })
public interface CommonObjectQueryFacadeRemote {

	MerchandiseQTO findMerchandise(String id);

	List<MerchandiseQTO> findMerchandise(MerchandiseQTO mqto);

	List<MerchandiseQTO> findMerchandise();

	List<UnitOfMeasureQTO> findUnitOfMeasure(boolean deleted);

	UnitOfMeasureQTO findUnitOfMeasureById(String id);
}
