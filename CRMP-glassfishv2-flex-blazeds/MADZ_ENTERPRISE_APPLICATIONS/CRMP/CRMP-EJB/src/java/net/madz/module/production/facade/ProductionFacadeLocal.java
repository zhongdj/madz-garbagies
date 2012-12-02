package net.madz.module.production.facade;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

import net.madz.module.production.ProductionRecordTO;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.module.production.entity.ProductionRecordBO;

@Local
@RolesAllowed({ "ADMIN" })
public interface ProductionFacadeLocal {

	void createProductionRecord(ProductionRecordTO[] records);

	ConcreteMixingPlant getUnspecifiedConcreteMixingPlant();

	public List<ConcreteMixingPlant> findConcreteMixingPlantsEntity();

	public List<ProductionRecordBO> findProductionRecords(String plantId, String projectId, Date start, Date end);

	public List<ProductionRecordBO> findProductionRecords(String plantId, String accountId, String projectId, Date start, Date end,
			String state);
}
