package net.madz.module.production.facade;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.module.production.ProductionRecordStatixTO;
import net.madz.module.production.ProductionRecordTO;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.service.etl.to.RawProductionRecordTO;

@Remote
@RolesAllowed({ "ADMIN" })
public interface ProductionFacadeRemote {

	public String createConcreteMixingPlant(ConcreteMixingPlantTO plant);

	public String createProductionRecord(ProductionRecordTO record);

	public List<ConcreteMixingPlantTO> findConcreteMixingPlants();

	public List<ProductionRecordTO> findProductionRecords();

	public ProductionRecordTO findProductionRecordById(String id);

	public void doConfirmProductionRecord(String id);

	public void doLoseProductionRecord(String id);

	public void doBillProductionRecord(String id);

	public ConcreteMixingPlant getUnspecifiedConcreteMixingPlant();

	public ConcreteMixingPlantTO findConcreteMixingPlantById(String id);

	public void hardDeleteAllConcreteMixingPlants();

	public void hardDeleteAllProductionRecords();

	public void hardDeleteConcreteMixingPlants(String[] ids);

	public void hardDeleteProductionRecords(String[] ids);

	public void importProductionRecords(RawProductionRecordTO[] records);

	public void softDeleteConcreteMixingPlants(String[] ids);

	public void softDeleteProductionRecords(String[] ids);

	public void updateConcreteMixingPlant(List<ConcreteMixingPlantTO> plants);

	public void updateProductionRecords(List<ProductionRecordTO> records);

	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime, String unitOfProjectId);

	List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime);
	

}
