package net.madz.module.common.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.common.to.query.UnitOfMeasureQTO;

/**
 * 
 * @author CleaNEr
 */
@Local
@RolesAllowed(value = { "ADMIN", "OP" })
public interface CommonObjectQueryFacadeLocal {

	MerchandiseQTO findMerchandise(String id);

	List<MerchandiseQTO> findMerchandise(MerchandiseQTO mqto);

	List<MerchandiseQTO> findMerchandise();

	List<UnitOfMeasureQTO> findUnitOfMeasure(boolean deleted);

	UnitOfMeasureQTO findUnitOfMeasureById(String id);

	Merchandise getUnspecifiedMerchandise();

	UnitOfMeasure getUnspecifiedUnitOfMeasure();
}
