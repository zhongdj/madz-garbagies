package net.madz.etl.demo.access;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.etl.db.AccessDBDataTypes;
import net.madz.service.etl.to.ColumnDescriptorTO;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.TableDescriptorTO;

import com.sun.appserv.security.ProgrammaticLogin;

public class AccessDbSchemaResolver {

	public static enum Mode {
		DropAndCreate, Create, Upgrade
	}

	/**
	 * @param args
	 *            java net.madz.etl.demo.AccessDbSchemaResolver sourceDSN
	 *            catalogName targetDatabaseName mode username password
	 * 
	 *            mode: 1. drop-and-create 2. create 3. upgrade
	 */
	public static void main(String[] args) {
		if (null == args || 0 >= args.length) {
			System.out
					.println("Use java net.madz.etl.demo.AccessDbSchemaResolver sourceDSN catalogName targetDatabaseName mode username password");
			System.out.println("mode: 1. drop-and-create 2. create 3. upgrade");
			throw new IllegalArgumentException();
		}

		final String sourceDSN = args[0];
		final String catalogName = args[1];
		final String targetDatabaseName = args[2];
		final String modeStr = args[3];
		final Mode mode;
		final String accessUsername;
		final String accessPassword;
		final String webPassword;
		final String webUsername;

		webUsername = args[4];
		webPassword = args[5];

		if (args.length < 7) {
			accessUsername = "";
			accessPassword = "";
		} else {
			accessUsername = args[6];
			if (args.length < 8) {
				accessPassword = "";
			} else {
				accessPassword = args[7];
			}
		}

		if ("drop-and-create".equalsIgnoreCase(modeStr)) {
			mode = Mode.DropAndCreate;
		} else if ("create".equalsIgnoreCase(modeStr)) {
			mode = Mode.Create;
		} else {
			mode = Mode.Upgrade;
		}
		resolveAccessDatabase(null, sourceDSN, catalogName, targetDatabaseName, mode, accessUsername, accessPassword, webUsername,
				webPassword);
	}

	public static void resolveAccessDatabase(final String mixingPlantId, final String dataSourceName, final String accessCatalogName,
			final String mysqlDatabaseName, final Mode mode, final String accessUsername, final String accessPassword,
			final String webUsername, final String webPassword) {
		switch (mode) {
		case Create:
			String databaseId = createDatabaseMetadata(mixingPlantId, dataSourceName, accessCatalogName, mysqlDatabaseName, accessUsername,
					accessPassword, webUsername, webPassword);

			System.out.println("Database Meta Data created, Id = " + databaseId);
			break;
		case Upgrade:
			break;
		case DropAndCreate:
			break;
		}
	}

	private static String createDatabaseMetadata(final String mixingPlantId, final String sourceDSN, final String catalogName,
			final String targetDatabaseName, final String accessUsername, final String accessPassword, final String webUsername,
			final String webPassword) {

		DatabaseDescriptorTO database = new DatabaseDescriptorTO();
		database.setName(targetDatabaseName);
		database.setPlantId(mixingPlantId);
		database.setOdbcDatasourceName(sourceDSN);

		List<TableDescriptorTO> tables = doResolve(sourceDSN, catalogName, targetDatabaseName, accessUsername, accessPassword);
		for (TableDescriptorTO tableDescriptorTO : tables) {
			database.addTable(tableDescriptorTO);
		}

		ProgrammaticLogin pl = new ProgrammaticLogin();
		try {
			pl.login(webUsername, webPassword);
			return ETLDelegate.getInstance().createDatabase(database);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		} finally {
			pl.logout();
		}
	}

	private static List<TableDescriptorTO> doResolve(final String sourceDSN, final String catalogName, final String targetDatabaseName,
			final String username, final String password) {
		final String url = "jdbc:odbc:" + sourceDSN;
		final List<TableDescriptorTO> result = new ArrayList<TableDescriptorTO>();
		Connection conn = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			conn = DriverManager.getConnection(url, username, password);
			DatabaseMetaData db = conn.getMetaData();

			String[] types = new String[1];
			types[0] = "TABLE";
			ResultSet tableRs = db.getTables(catalogName, null, null, types);
			TableDescriptorTO table = null;
			while (tableRs.next()) {
				table = new TableDescriptorTO();
				String tableName = tableRs.getString("TABLE_NAME");
				table.setName(tableName);
				resolveColumns(conn, table, catalogName);
				final List<ColumnDescriptorTO> columns = table.getColumns();
				final Map<String, ColumnDescriptorTO> columnsMap = new HashMap<String, ColumnDescriptorTO>();
				for (ColumnDescriptorTO columnDescriptorTO : columns) {
					columnsMap.put(columnDescriptorTO.getColumnName(), columnDescriptorTO);
				}

				// ResultSet primaryKeysRS = db.getPrimaryKeys(null, null,
				// tableName);
				// while (primaryKeysRS.next()) {
				// String primaryKeyColumn = primaryKeysRS
				// .getString("COLUMN_NAME");
				// ColumnDescriptorTO columnDescriptorTO = columnsMap
				// .get(primaryKeyColumn);
				// columnDescriptorTO.setPrimaryKey(true);
				// if (columnDescriptorTO.isKey()) {
				// columnDescriptorTO.setKey(false);
				// }
				// }

				result.add(table);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static void resolveColumns(Connection conn, TableDescriptorTO table, String catalogName) throws SQLException {
		Statement statement = conn.createStatement();
		final String sql = "select * from " // + catalogName + "."
				+ table.getAccessName();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		ColumnDescriptorTO column = null;
		for (int i = 1; i <= columnCount; i++) {
			String columnName = metaData.getColumnName(i);
			String columnTypeName = metaData.getColumnTypeName(i);
			int columnDisplaySize = metaData.getColumnDisplaySize(i);
			boolean autoIncrement = metaData.isAutoIncrement(i);
			int precision = metaData.getPrecision(i);
			int scale = metaData.getScale(i);

			column = new ColumnDescriptorTO();
			column.setColumnName(columnName);
			column.setColumnTypeName(columnTypeName);
			column.setAutoIncremental(autoIncrement);
			if (AccessDBDataTypes.DECIMAL.equalsIgnoreCase(columnTypeName)) {
				column.setColumnDisplaySize(precision);
				column.setColumnPrecision(scale);
			} else {
				column.setColumnDisplaySize(columnDisplaySize);
				column.setColumnPrecision(precision);
			}
			if (autoIncrement) {
				column.setKey(true);
			}
			table.addColumn(column);

			System.out.print("Column Name: \t" + columnName);
			System.out.print(" \t" + columnTypeName);
			System.out.print("(" + columnDisplaySize + ")");

			if (autoIncrement) {
				System.out.print(" PRIMARY KEY AUTOINCREMENTAL");
			}
			// System.out.print("\tColumn Precision: \t" + precision + "\t");
			System.out.println();
		}
	}
}
