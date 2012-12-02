package net.madz.flex.proxy.production;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.flex.proxy.account.AccountProxy;
import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.module.production.ProductionRecordStatixTO;
import net.madz.module.production.ProductionRecordTO;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.module.production.facade.ProductionFacadeRemote;
import net.madz.service.etl.to.RawProductionRecordTO;

public class ProductionProxy implements ProductionFacadeRemote {

	private final ProductionDelegate delegate;

	public ProductionProxy() {
		super();
		try {
			delegate = ProductionDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(AccountProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public String createConcreteMixingPlant(ConcreteMixingPlantTO plant) {
		return delegate.createConcreteMixingPlant(plant);
	}

	@Override
	public String createProductionRecord(ProductionRecordTO record) {
		return delegate.createProductionRecord(record);
	}

	// @Override
	// public List<ConcreteMixingPlantTO> findConcreteMixingPlants() {
	// return delegate.findConcreteMixingPlants();
	// }

	@Override
	public List<ProductionRecordTO> findProductionRecords() {
		return delegate.findProductionRecords();
	}

	@Override
	public ConcreteMixingPlant getUnspecifiedConcreteMixingPlant() {
		return delegate.getUnspecifiedConcreteMixingPlant();
	}

	@Override
	public void hardDeleteAllConcreteMixingPlants() {
		delegate.hardDeleteAllConcreteMixingPlants();
	}

	@Override
	public void hardDeleteAllProductionRecords() {
		delegate.hardDeleteAllProductionRecords();
	}

	@Override
	public void hardDeleteConcreteMixingPlants(String[] ids) {
		delegate.hardDeleteAllConcreteMixingPlants();
	}

	@Override
	public void hardDeleteProductionRecords(String[] ids) {
		delegate.hardDeleteConcreteMixingPlants(ids);
	}

	@Override
	public void importProductionRecords(RawProductionRecordTO[] records) {
		delegate.importProductionRecords(records);
	}

	@Override
	public void softDeleteConcreteMixingPlants(String[] ids) {
		delegate.softDeleteConcreteMixingPlants(ids);
	}

	@Override
	public void softDeleteProductionRecords(String[] ids) {
		delegate.softDeleteProductionRecords(ids);
	}

	@Override
	public void updateConcreteMixingPlant(List<ConcreteMixingPlantTO> plants) {
		delegate.updateConcreteMixingPlant(plants);
	}

	@Override
	public void updateProductionRecords(List<ProductionRecordTO> records) {
		delegate.updateProductionRecords(records);
	}

	@Override
	public ProductionRecordTO findProductionRecordById(String id) {
		return delegate.findProductionRecordById(id);
	}

	@Override
	public ConcreteMixingPlantTO findConcreteMixingPlantById(String id) {
		return delegate.findConcreteMixingPlantById(id);
	}

	@Override
	public void doConfirmProductionRecord(String id) {
		delegate.doConfirmProductionRecord(id);
	}

	@Override
	public void doLoseProductionRecord(String id) {
		delegate.doLoseProductionRecord(id);
	}

	@Override
	public void doBillProductionRecord(String id) {
		delegate.doBillProductionRecord(id);
	}

	@Override
	public List<ConcreteMixingPlantTO> findConcreteMixingPlants() {
		return delegate.findConcreteMixingPlants();
	}

	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime, String unitOfProjectId) {
		return delegate.summarizeProductionRecord(startTime, endTime, unitOfProjectId);
	}

	@Override
	public List<ProductionRecordStatixTO> summarizeProductionRecord(Date startTime, Date endTime) {
		return delegate.summarizeProductionRecord(startTime, endTime);
	}

}
