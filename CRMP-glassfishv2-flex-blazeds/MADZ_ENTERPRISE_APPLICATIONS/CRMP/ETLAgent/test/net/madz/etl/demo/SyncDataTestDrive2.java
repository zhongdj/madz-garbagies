package net.madz.etl.demo;

import java.util.ArrayList;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.service.etl.to.ETLColumnDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;

import com.sun.appserv.security.ProgrammaticLogin;

public class SyncDataTestDrive2 {

	public static void main(String[] args) {

		final String webUsername = "administrator";
		final String webPassword = "Barryzdjwin5631";
		final String mysqlUsername = "root";
		final String mysqlPassword = "1q2w3e4r5t";
		final String dataSourceName = "crmp_2_dsn";
		final String mysqlDatabaseName = "crmp_CB3200Report_db";
		final String mysqlServerIpAddress = "localhost";

		final ProgrammaticLogin pl = new ProgrammaticLogin();

		 pl.login(webUsername, webPassword);
		 generateSampleETLInformation(mysqlDatabaseName);
		 pl.logout();

//		MysqlDataExtractor.syncRawProductionData2Server(webUsername, webPassword, mysqlUsername, mysqlPassword, mysqlDatabaseName,
//				mysqlServerIpAddress);
	}

	public static void generateSampleETLInformation(final String mysqlDatabaseName) {
		ETLTableDescriptorTO tableDescriptor = null;

		try {
			tableDescriptor = ETLDelegate.getInstance().getProductionRecordETLTableDescriptor(mysqlDatabaseName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			if (null == tableDescriptor) {
				tableDescriptor = new ETLTableDescriptorTO();
				tableDescriptor.setDatabaseName(mysqlDatabaseName);
				tableDescriptor.setRawTableName("ProdMasterData");
				tableDescriptor.setTargetClassName(RawProductionRecordTO.class.getName());

				ArrayList<ETLColumnDescriptorTO> columns = new ArrayList<ETLColumnDescriptorTO>();

				ETLColumnDescriptorTO column = new ETLColumnDescriptorTO();
				String rawColumnName = "PDate";
				String targetFieldName = "productionTime";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);

				column = new ETLColumnDescriptorTO();
				rawColumnName = "RecipeName";
				targetFieldName = "recipeName";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);

				column = new ETLColumnDescriptorTO();
				rawColumnName = "Volume";
				targetFieldName = "outputVolumn";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);

				column = new ETLColumnDescriptorTO();
				rawColumnName = "Truck";
				targetFieldName = "truckNumber";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);

				column = new ETLColumnDescriptorTO();
				rawColumnName = "Cname";
				targetFieldName = "projectOwnerName";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);

				column = new ETLColumnDescriptorTO();
				rawColumnName = "Sname";
				targetFieldName = "unitOfProjectName";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);

				column = new ETLColumnDescriptorTO();
				rawColumnName = "Cname";
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
				rawColumnName = "AutoID";
				targetFieldName = "referenceNumber";
				column.setRawColumnName(rawColumnName);
				column.setTargetFieldName(targetFieldName);
				columns.add(column);
				tableDescriptor.setColumnDescriptors(columns);
				ETLDelegate.getInstance().createETLTableDescriptor(tableDescriptor);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
