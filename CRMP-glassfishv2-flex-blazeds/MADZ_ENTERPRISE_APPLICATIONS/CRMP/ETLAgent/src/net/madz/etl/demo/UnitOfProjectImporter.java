package net.madz.etl.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.etl.demo.access.AccessDbSchemaResolver.Mode;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.ETLColumnDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;
import net.madz.utils.DbOperator;

import com.sun.appserv.security.ProgrammaticLogin;

public class UnitOfProjectImporter {

	public static void main(String[] args) {
		String dataSourceName = "crmp_1_dsn";
		String catalogName = "Search";
		String mysqlDatabaseName = "crmp_1_db";
		Mode mode = Mode.Create;
		String accessUsername = "";
		String accessPassword = "";
		String webUsername = "administrator";
		String webPassword = "Barryzdjwin5631";
		String mysqlUsername = "root";
		String mysqlPassword = "1q2w3e4r5t";
		String mysqlServerIpAddress = "localhost";

		syncRawUnitOfProjectData2Server(webUsername, webPassword, mysqlUsername, mysqlPassword, mysqlDatabaseName, mysqlServerIpAddress);
	}

	public static void syncRawUnitOfProjectData2Server(final String webUsername, final String webPassword, final String mysqlUsername,
			final String mysqlPassword, final String mysqlDatabaseName, final String mysqlServerIpAddress) {
		final ProgrammaticLogin pl = new ProgrammaticLogin();
		DbOperator dbo = null;
		PreparedStatement ps = null;
		try {
			pl.login(webUsername, webPassword);

			generateSampleETLInformation(mysqlDatabaseName);

			// TODO Step 1 get production record metadata from server

			ETLTableDescriptorTO tableDescriptor = ETLDelegate.getInstance().getProductionRecordETLTableDescriptor(mysqlDatabaseName);

			// TODO Step 2 Assemble SQL
			final List<ETLColumnDescriptorTO> columnDescriptors = tableDescriptor.getColumnDescriptors();

			String unitOfProjectColumnName = null;
			for (ETLColumnDescriptorTO etlColumn : columnDescriptors) {
				if ("unitOfProjectName".equals(etlColumn.getTargetFieldName())) {
					unitOfProjectColumnName = etlColumn.getRawColumnName();
					break;
				}
			}

			if (null == unitOfProjectColumnName) {
				System.out.println("Cannot find unitOfProjectColumnName");
			}

			final StringBuilder sql = new StringBuilder();
			sql.append("SELECT").append(" DISTINCT (").append(unitOfProjectColumnName).append(")");
			sql.append(" FROM ").append(tableDescriptor.getRawTableName());

			final String mysqlUrl = "jdbc:mysql://" + mysqlServerIpAddress + ":3306/" + mysqlDatabaseName
					+ "?useUnicode=true&amp;CharacterEncoding=GBK";
			dbo = new DbOperator(mysqlUrl, mysqlUsername, mysqlPassword);
			ps = dbo.getPreparedStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			ArrayList<String> result = new ArrayList<String>();
			while (rs.next()) {
				result.add(rs.getString(1));
			}

			for (String name : result) {
				System.out.println(name);
			}
			DatabaseDescriptorTO databaseDescriptor = ETLDelegate.getInstance().getDatabaseDescriptor(mysqlDatabaseName);

			assert (null != databaseDescriptor.getPlantId());

			ETLDelegate.getInstance().initialImportRawUnitOfProject(result.toArray(new String[0]), databaseDescriptor.getPlantId());

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			dbo.closeAll();
		}
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
				tableDescriptor.setRawTableName("srecords");
				tableDescriptor.setTargetClassName(RawProductionRecordTO.class.getName());

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
				tableDescriptor.setColumnDescriptors(columns);
				ETLDelegate.getInstance().createETLTableDescriptor(tableDescriptor);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
