package net.madz.etl.demo;

import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.etl.demo.access.AccessData2Mysql;
import net.madz.etl.demo.access.AccessDbSchemaResolver;
import net.madz.etl.demo.access.AccessDbSchemaResolver.Mode;
import net.madz.module.production.ConcreteMixingPlantTO;

import com.sun.appserv.security.ProgrammaticLogin;

public class ResolveAndImportDataTestDrive {

	public static void main(String[] args) {

//		String dataSourceName = "crmp_1_dsn";
//		String catalogName = "Search";
//		String mysqlDatabaseName = "crmp_1_db";
//		Mode mode = Mode.Create;
//		String accessUsername = "";
//		String accessPassword = "";
//		String webUsername = "administrator";
//		String webPassword = "Barryzdjwin5631";
//		String mysqlUsername = "root";
//		String mysqlPassword = "1q2w3e4r5t";
//		String mysqlServerIpAddress = "localhost";

		 String dataSourceName = "crmp_2_dsn";
		 String catalogName = "CB3200Report";
		 String mysqlDatabaseName = "crmp_CB3200Report_db";
		 Mode mode = Mode.Create;
		 String accessUsername = "����Ա";
		 String accessPassword = "1";
		 String webUsername = "administrator";
		 String webPassword = "Barryzdjwin5631";
		 String mysqlUsername = "root";
		 String mysqlPassword = "1q2w3e4r5t";
		 String mysqlServerIpAddress = "localhost";

		// String dataSourceName = "crmp_3_dsn";
		// String catalogName = "data";
		// String mysqlDatabaseName = "crmp_data_db";
		// Mode mode = Mode.Create;
		// String accessUsername = "";
		// String accessPassword = "";
		// String webUsername = "administrator";
		// String webPassword = "Barryzdjwin5631";
		// String mysqlUsername = "root";
		// String mysqlPassword = "1q2w3e4r5t";
		// String mysqlServerIpAddress = "localhost";

		ConcreteMixingPlantTO plant = new ConcreteMixingPlantTO();
		plant.setName("2#");
		String mixingPlantId = "";
		ProgrammaticLogin pl = new ProgrammaticLogin();
		try {
			pl.login(webUsername, webPassword);
			try {
				mixingPlantId = ProductionDelegate.getInstance().createConcreteMixingPlant(plant);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		} finally {
			pl.logout();
		}

		AccessDbSchemaResolver.resolveAccessDatabase(mixingPlantId, dataSourceName, catalogName, mysqlDatabaseName, mode, accessUsername,
				accessPassword, webUsername, webPassword);

		MysqlDatabaseGenerator.doGenerateMysqlDb(webUsername, webPassword, mysqlDatabaseName, mysqlUsername, mysqlPassword,
				mysqlServerIpAddress);

//		AccessData2Mysql.importDataFromAccessDatabase(accessUsername, accessPassword, webUsername, webPassword, mysqlUsername,
//				mysqlPassword, dataSourceName, mysqlDatabaseName, mysqlServerIpAddress);
	}
}
