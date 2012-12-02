package net.madz.client.production.delegate;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.infra.util.CachingServiceLocator;
import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.module.production.ProductionRecordStatixTO;
import net.madz.module.production.ProductionRecordTO;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.module.production.facade.ProductionFacadeRemote;
import net.madz.service.etl.to.RawProductionRecordTO;

public class ProductionDelegate implements ProductionFacadeRemote {

	private static ProductionDelegate instance;
	private static ProductionFacadeRemote server;

	private ProductionDelegate() throws Exception {
		try {
			server = (ProductionFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(ProductionFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(ProductionFacadeRemote.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized ProductionDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new ProductionDelegate();
		}
		return instance;
	}

	@Override
	public String createProductionRecord(ProductionRecordTO record) {
		return server.createProductionRecord(record);
	}

	@Override
	public List<ProductionRecordTO> findProductionRecords() {
		return server.findProductionRecords();
	}

	@Override
	public void updateProductionRecords(List<ProductionRecordTO> records) {
		server.updateProductionRecords(records);
	}

	@Override
	public void softDeleteProductionRecords(String[] ids) {
		server.softDeleteProductionRecords(ids);
	}

	@Override
	public void hardDeleteAllProductionRecords() {
		server.hardDeleteAllProductionRecords();
	}

	@Override
	public void hardDeleteProductionRecords(String[] ids) {
		server.hardDeleteProductionRecords(ids);
	}

	@Override
	public String createConcreteMixingPlant(ConcreteMixingPlantTO plant) {
		return server.createConcreteMixingPlant(plant);
	}

	// @Override
	// public List<ConcreteMixingPlantTO> findConcreteMixingPlants() {
	// return server.findConcreteMixingPlants();
	// }

	@Override
	public void updateConcreteMixingPlant(List<ConcreteMixingPlantTO> plants) {
		server.updateConcreteMixingPlant(plants);
	}

	@Override
	public void softDeleteConcreteMixingPlants(String[] ids) {
		server.softDeleteConcreteMixingPlants(ids);
	}

	@Override
	public void hardDeleteConcreteMixingPlants(String[] ids) {
		server.hardDeleteConcreteMixingPlants(ids);
	}

	@Override
	public void hardDeleteAllConcreteMixingPlants() {
		server.hardDeleteAllConcreteMixingPlants();
	}

	@Override
	public void importProductionRecords(RawProductionRecordTO[] records) {
		server.importProductionRecords(records);
	}

	@Override
	public ConcreteMixingPlant getUnspecifiedConcreteMixingPlant() {
		return server.getUnspecifiedConcreteMixingPlant();
	}

	@Override
	public ProductionRecordTO findProductionRecordById(String id) {
		return server.findProductionRecordById(id);
	}

	@Override
	public ConcreteMixingPlantTO findConcreteMixingPlantById(String id) {
		return server.findConcreteMixingPlantById(id);
	}

	@Override
	public void doConfirmProductionRecord(String id) {
		server.doConfirmProductionRecord(id);
	}

	@Override
	public void doLoseProductionRecord(String id) {
		server.doLoseProductionRecord(id);
	}

	@Override
	public void doBillProductionRecord(String id) {
		server.doBillProductionRecord(id);
	}

	@Override
	public List<ConcreteMixingPlantTO> findConcreteMixingPlants() {
		return server.findConcreteMixingPlants();
	}

	@Override
	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime, String unitOfProjectId) {
		return server.summarizeProductionRecord(startTime, endTime, unitOfProjectId);
	}

	@Override
	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime) {
		return server.summarizeProductionRecord(startTime, endTime);
	}

}
