package net.madz.service.etl;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.service.etl.entity.ETLColumnContentMappingDescriptor;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnContentMappingDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;

@Remote
@RolesAllowed({ "ADMIN" })
public interface ETLFacadeRemote {
	String createDatabase(DatabaseDescriptorTO db);

	void createETLColumnContentMappingDescriptorList(
			List<ETLColumnContentMappingDescriptorTO> contentMappingDescriptorList);

	String createETLTableDescriptor(ETLTableDescriptorTO table);

	void deleteUpdateContentMappingDescriptors(
			String[] contentMappingDescriptorIds);

	void doSyncRawProductionRecords(String mixingPlantId,
			RawProductionRecordTO[] records);

	List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors();

	List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors(
			String plantId, String databaseName, String tableName);

	DatabaseDescriptorTO getDatabaseDescriptor(String databaseName);

	DatabaseDescriptorTO findDatabaseDescriptorByPlantId(String plantId);

	List<DatabaseImportIndicatorTO> getDatabaseImportIndicator(
			String databaseName);

	DatabaseImportIndicatorTO getDatabaseImportIndicator(
			String mysqlDatabaseName, String rawTableName);

	List<DatabaseSyncIndicatorTO> getDatabaseSyncIndicator(String databaseName);

	ETLTableDescriptorTO getProductionRecordETLTableDescriptor(
			String databaseName);

	void initialImportRawUnitOfProject(String[] names, String plantId);

	void updateContentMappingDescriptors(
			ETLColumnContentMappingDescriptorTO[] contentMappingDescriptors);

	void updateDatabaseImportIndicator(DatabaseImportIndicatorTO indicator);

	void updateDatabaseSyncIndicator(DatabaseSyncIndicatorTO indicator);

}
