package net.madz.remote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.etl.db.AccessDBDataTypes;
import net.madz.etl.db.MysqlDBDataTypes;
import net.madz.module.production.ConcreteMixingPlantTO;
import net.madz.service.etl.to.ColumnDescriptorTO;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;
import net.madz.service.etl.to.TableDescriptorTO;
import net.madz.swing.wizard.displayable.DefaultMixingPlantDisplayable;
import net.madz.swing.wizard.displayable.IMixingPlantDisplayable;

public class ETLClientProxy {

	private static final String CRMP_1_DB = "crmp_1_db";

	private static final ETLClientProxy INSTANCE = new ETLClientProxy();

	private boolean simulator = false;
	private ETLDelegate delegate;

	public static ETLClientProxy getInstance() {
		return INSTANCE;
	}

	private ETLClientProxy() {
		try {
			delegate = ETLDelegate.getInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public void doSyncRawProductionRecords(String mixingPlantId,
			RawProductionRecordTO[] records) {
		if (!simulator) {
			delegate.doSyncRawProductionRecords(mixingPlantId, records);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public DatabaseDescriptorTO findDatabaseDescriptor(String plantId) {
		if (!simulator) {
			return delegate.findDatabaseDescriptorByPlantId(plantId);
		}

		return createSimulateDatabaseDescriptor();
	}

	public Collection<IMixingPlantDisplayable> findPlants() {

		if (!simulator) {
			try {
				final ArrayList<IMixingPlantDisplayable> result = new ArrayList<IMixingPlantDisplayable>();
				final List<ConcreteMixingPlantTO> mixingPlants = ProductionDelegate.getInstance().findConcreteMixingPlants();
                for (ConcreteMixingPlantTO plant : mixingPlants) {
//					DatabaseDescriptorTO db = delegate.findDatabaseDescriptorByPlantId(plant.getId());
//					ETLTableDescriptorTO etlTable = delegate.getProductionRecordETLTableDescriptor(db.getName());
//					DatabaseImportIndicatorTO importIndicator = delegate.getDatabaseImportIndicator(db.getName(), etlTable.getRawTableName());
//					List<DatabaseSyncIndicatorTO> syncIndicators = delegate.getDatabaseSyncIndicator(db.getName());
//					DatabaseSyncIndicatorTO tableSyncIndicator = null;
//					for (DatabaseSyncIndicatorTO syncIndicator : syncIndicators) {
//						if (etlTable.getRawTableName().equalsIgnoreCase(syncIndicator.getDatabaseName())) {
//							tableSyncIndicator = syncIndicator;
//							break;
//						}
//					}
					result.add(new DefaultMixingPlantDisplayable(plant));
				}
                return result;
			} catch (Exception e) {
			}
			
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collection<IMixingPlantDisplayable> plantsCollection = new ArrayList<IMixingPlantDisplayable>();

		plantsCollection.add(new DefaultMixingPlantDisplayable("1�Ž���վ"));
		plantsCollection.add(new DefaultMixingPlantDisplayable("2�Ž���վ"));
		plantsCollection.add(new DefaultMixingPlantDisplayable("3�Ž���վ"));
		plantsCollection.add(new DefaultMixingPlantDisplayable("4�Ž���վ"));
		return plantsCollection;
	}

	public DatabaseDescriptorTO getDatabaseDescriptor(String mysqlDatabaseName) {
		if (!simulator) {
			return delegate.getDatabaseDescriptor(mysqlDatabaseName);
		}
		return null;
	}

	public List<DatabaseImportIndicatorTO> getDatabaseImportIndicator(
			String mysqlDatabaseName) {
		if (!simulator) {
			return delegate.getDatabaseImportIndicator(mysqlDatabaseName);
		}
		return createSimulateDatabaseImportIndiator();
	}

	public List<DatabaseSyncIndicatorTO> getDatabaseSyncIndicator(
			String mysqlDatabaseName) {
		if (!simulator) {
			return delegate.getDatabaseSyncIndicator(mysqlDatabaseName);
		}
		return createSimulateDatabaseSyncIndicator();
	}

	public ETLTableDescriptorTO getProductionRecordETLTableDescriptor(
			String mysqlDatabaseName) {
		if (!simulator) {
			return delegate
					.getProductionRecordETLTableDescriptor(mysqlDatabaseName);
		}

		return createSimulateETLTableDescriptor();
	}

	public void updateDatabaseImportIndicator(
			DatabaseImportIndicatorTO indicator) {
		if (!simulator) {
			delegate.updateDatabaseImportIndicator(indicator);
		}
	}

	public void updateDatabaseSyncIndicator(
			DatabaseSyncIndicatorTO syncIndicator) {
		if (!simulator) {
			delegate.updateDatabaseSyncIndicator(syncIndicator);
		}
	}

	private ETLTableDescriptorTO createSimulateETLTableDescriptor() {
		ETLTableDescriptorTO t = new ETLTableDescriptorTO();
		t.setDatabaseName(CRMP_1_DB);
		t.setId("1");
		t.setRawTableName("srecord");
		t.setTargetClassName(RawProductionRecordTO.class.getName());

		ArrayList<ETLColumnDescriptorTO> columns = new ArrayList<ETLColumnDescriptorTO>();

		ETLColumnDescriptorTO column = new ETLColumnDescriptorTO();
		String rawColumnName = "ScTime";
		String targetFieldName = "productionTime";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = "Tech_req";
		targetFieldName = "recipeName";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = "Output_Vol";
		targetFieldName = "outputVolumn";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = "Truck_No";
		targetFieldName = "truckNumber";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = "Cust_Nm";
		targetFieldName = "projectOwnerName";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = "Proj_Nm";
		targetFieldName = "unitOfProjectName";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = null;
		targetFieldName = "unitOfProjectOwnerName";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = null;
		targetFieldName = "constructionPartName";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = null;
		targetFieldName = "mixingPlantName";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		column = new ETLColumnDescriptorTO();
		rawColumnName = "ID";
		targetFieldName = "referenceNumber";
		column.setRawColumnName(rawColumnName);
		column.setTargetFieldName(targetFieldName);
		columns.add(column);

		t.setColumnDescriptors(columns);
		return t;
	}

	private int getBatch() {
		return batch++;
	}

	private int batch = 0;

	private List<DatabaseSyncIndicatorTO> createSimulateDatabaseSyncIndicator() {
		ArrayList<DatabaseSyncIndicatorTO> result = new ArrayList<DatabaseSyncIndicatorTO>();
		DatabaseSyncIndicatorTO e = new DatabaseSyncIndicatorTO();
		e.setDatabaseName(CRMP_1_DB);
		e.setId("1");
		e.setIndicatorColumnName("ScTime");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.set(2010, 8, 8, 8, 8);
		c.add(Calendar.MINUTE, getBatch() * 29);
		Date date = c.getTime();
		e.setIndicatorValue(format.format(date));
		result.add(e);
		return result;
	}

	private List<DatabaseImportIndicatorTO> createSimulateDatabaseImportIndiator() {
		final ArrayList<DatabaseImportIndicatorTO> result = new ArrayList<DatabaseImportIndicatorTO>();
		DatabaseImportIndicatorTO e = new DatabaseImportIndicatorTO();
		e.setDatabaseName(CRMP_1_DB);
		e.setIndicatorColumnName("ScTime");
		e.setIndicatorColumnType(AccessDBDataTypes.DATETIME);
		e.setTableName("sRecord");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.set(2010, 8, 8, 8, 8);
		c.add(Calendar.MINUTE, getBatch() * 29);
		Date date = c.getTime();
		e.setIndicatorValue(format.format(date));
		result.add(e);
		return result;
	}

	private DatabaseDescriptorTO createSimulateDatabaseDescriptor() {
		DatabaseDescriptorTO result = new DatabaseDescriptorTO();
		result.setAccessPassword("");
		result.setAccessUsername("");
		result.setCreatedByName("administrator");
		result.setId("1");
		result.setName(CRMP_1_DB);
		result.setOdbcDatasourceName("crmp_1_dsn");
		result.setPlantId("1");
		result.setPlantName("3#Plant");
		List<TableDescriptorTO> tables = new ArrayList<TableDescriptorTO>();
		TableDescriptorTO e = new TableDescriptorTO();
		e.setId("1");
		e.setName("sRecord");
		List<ColumnDescriptorTO> columns = new ArrayList();
		ColumnDescriptorTO col = new ColumnDescriptorTO();
		col.setColumnLabel("ScTime");
		col.setColumnName("ScTime");
		col.setColumnPrecision(10);
		col.setColumnType(MysqlDBDataTypes.DATETIME);
		col.setColumnTypeName(MysqlDBDataTypes.DATETIME);
		col.setId("1");
		col.setPrimaryKey(true);
		columns.add(col);
		
		
		
		e.setColumns(columns);
		tables.add(e);
		result.setTables(tables);
		return result;
	}

}
