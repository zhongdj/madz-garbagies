package net.madz.client.etl.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.service.etl.ETLFacadeRemote;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnContentMappingDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;

public class ETLDelegate implements ETLFacadeRemote {
	private static ETLDelegate instance;
	private ETLFacadeRemote server;

	private ETLDelegate() throws Exception {
		try {
			server = (ETLFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(ETLFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(ETLDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized ETLDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new ETLDelegate();
		}
		return instance;
	}

	@Override
	public String createDatabase(DatabaseDescriptorTO db) {
		return server.createDatabase(db);
	}

	@Override
	public DatabaseDescriptorTO getDatabaseDescriptor(String databaseName) {
		return server.getDatabaseDescriptor(databaseName);
	}

	public List<DatabaseImportIndicatorTO> getDatabaseImportIndicator(String databaseName) {
		return server.getDatabaseImportIndicator(databaseName);
	}

	public void updateDatabaseImportIndicator(DatabaseImportIndicatorTO indicator) {
		server.updateDatabaseImportIndicator(indicator);
	}

	@Override
	public List<DatabaseSyncIndicatorTO> getDatabaseSyncIndicator(String databaseName) {
		return server.getDatabaseSyncIndicator(databaseName);
	}

	@Override
	public void updateDatabaseSyncIndicator(DatabaseSyncIndicatorTO indicator) {
		server.updateDatabaseSyncIndicator(indicator);
	}

	@Override
	public String createETLTableDescriptor(ETLTableDescriptorTO table) {
		return server.createETLTableDescriptor(table);
	}

	@Override
	public ETLTableDescriptorTO getProductionRecordETLTableDescriptor(String databaseName) {
		return server.getProductionRecordETLTableDescriptor(databaseName);
	}

	@Override
	public void createETLColumnContentMappingDescriptorList(List<ETLColumnContentMappingDescriptorTO> contentMappingDescriptorList) {
		server.createETLColumnContentMappingDescriptorList(contentMappingDescriptorList);
	}

	public DatabaseImportIndicatorTO getDatabaseImportIndicator(String mysqlDatabaseName, String rawTableName) {
		return server.getDatabaseImportIndicator(mysqlDatabaseName, rawTableName);
	}

	@Override
	public void doSyncRawProductionRecords(String mixingPlantId, RawProductionRecordTO[] records) {
		server.doSyncRawProductionRecords(mixingPlantId, records);

	}

	@Override
	public void initialImportRawUnitOfProject(String[] names, String plantId) {
		server.initialImportRawUnitOfProject(names, plantId);
	}

	@Override
	public void deleteUpdateContentMappingDescriptors(String[] contentMappingDescriptorIds) {
		server.deleteUpdateContentMappingDescriptors(contentMappingDescriptorIds);
	}

	@Override
	public List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors() {
		return server.findContentMappingDescriptors();
	}

	@Override
	public List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors(String plantId, String databaseName, String tableName) {
		return server.findContentMappingDescriptors(plantId, databaseName, tableName);
	}

	@Override
	public void updateContentMappingDescriptors(ETLColumnContentMappingDescriptorTO[] contentMappingDescriptors) {
		server.updateContentMappingDescriptors(contentMappingDescriptors);
	}

	@Override
	public DatabaseDescriptorTO findDatabaseDescriptorByPlantId(String plantId) {
		return server.findDatabaseDescriptorByPlantId(plantId);
	}

}
