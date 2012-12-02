package net.madz.flex.proxy.etl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.flex.proxy.contract.ContractProxy;
import net.madz.service.etl.ETLFacadeRemote;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnContentMappingDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;

public class ETLWebProxy implements ETLFacadeRemote {

	private final ETLDelegate delegate;

	public ETLWebProxy() {
		try {
			delegate = ETLDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(ContractProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public String createDatabase(DatabaseDescriptorTO db) {
		return delegate.createDatabase(db);
	}

	@Override
	public void createETLColumnContentMappingDescriptorList(List<ETLColumnContentMappingDescriptorTO> contentMappingDescriptorList) {
		delegate.createETLColumnContentMappingDescriptorList(contentMappingDescriptorList);
	}

	@Override
	public String createETLTableDescriptor(ETLTableDescriptorTO table) {
		return delegate.createETLTableDescriptor(table);
	}

	@Override
	public void deleteUpdateContentMappingDescriptors(String[] contentMappingDescriptorIds) {
		delegate.deleteUpdateContentMappingDescriptors(contentMappingDescriptorIds);
	}

	@Override
	public void doSyncRawProductionRecords(String mixingPlantId, RawProductionRecordTO[] records) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors() {
		return delegate.findContentMappingDescriptors();
	}

	@Override
	public List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors(String plantId, String databaseName, String tableName) {
		return delegate.findContentMappingDescriptors(plantId, databaseName, tableName);
	}

	@Override
	public DatabaseDescriptorTO getDatabaseDescriptor(String databaseName) {
		return delegate.getDatabaseDescriptor(databaseName);
	}

	@Override
	public List<DatabaseImportIndicatorTO> getDatabaseImportIndicator(String databaseName) {
		return delegate.getDatabaseImportIndicator(databaseName);
	}

	@Override
	public DatabaseImportIndicatorTO getDatabaseImportIndicator(String mysqlDatabaseName, String rawTableName) {
		return delegate.getDatabaseImportIndicator(mysqlDatabaseName, rawTableName);
	}

	@Override
	public List<DatabaseSyncIndicatorTO> getDatabaseSyncIndicator(String databaseName) {
		return delegate.getDatabaseSyncIndicator(databaseName);
	}

	@Override
	public ETLTableDescriptorTO getProductionRecordETLTableDescriptor(String databaseName) {
		return delegate.getProductionRecordETLTableDescriptor(databaseName);
	}

	@Override
	public void initialImportRawUnitOfProject(String[] names, String plantId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateContentMappingDescriptors(ETLColumnContentMappingDescriptorTO[] contentMappingDescriptors) {
		delegate.updateContentMappingDescriptors(contentMappingDescriptors);
	}

	@Override
	public void updateDatabaseImportIndicator(DatabaseImportIndicatorTO indicator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDatabaseSyncIndicator(DatabaseSyncIndicatorTO indicator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseDescriptorTO findDatabaseDescriptorByPlantId(String plantId) {
		return delegate.findDatabaseDescriptorByPlantId(plantId);
	}

}
