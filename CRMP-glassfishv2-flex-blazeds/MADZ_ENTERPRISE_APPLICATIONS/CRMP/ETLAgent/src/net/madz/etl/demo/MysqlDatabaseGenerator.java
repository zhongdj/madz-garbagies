package net.madz.etl.demo;

import java.util.ArrayList;
import java.util.List;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.etl.demo.access.AccessDBDataTypes;
import net.madz.service.etl.to.ColumnDescriptorTO;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.TableDescriptorTO;
import net.madz.utils.DbOperator;

import com.sun.appserv.security.ProgrammaticLogin;

public class MysqlDatabaseGenerator {

	public static void main(String args[]) {
		final String webUsername = "administrator";
		final String webPassword = "Barryzdjwin5631";
		final String mysqlDatabaseName = "crmp_CB3200Report_db";// "crmp_1_db";

		// doGenerateMysqlDb(webUsername, webPassword, mysqlDatabaseName);
	}

	public static void doGenerateMysqlDb(final String webUsername, final String webPassword, final String mysqlDatabaseName,
			final String mysqlUsername, final String mysqlPassword, final String mysqlServerIpAddress) {
		final ProgrammaticLogin pl = new ProgrammaticLogin();
		final String mysqlUrl = "jdbc:mysql://" + mysqlServerIpAddress + ":3306/" + mysqlDatabaseName
				+ "?useUnicode=true&amp;CharacterEncoding=GBK";

		final DbOperator dbo = new DbOperator("com.mysql.jdbc.Driver", mysqlUrl, mysqlUsername, mysqlPassword);
		try {

			pl.login(webUsername, webPassword);
			final DatabaseDescriptorTO db = ETLDelegate.getInstance().getDatabaseDescriptor(mysqlDatabaseName);
			final List<String> dropDatabaseDDLs = generateDropDatabaseDDL(db);
			for (String dropDatabaseDDL : dropDatabaseDDLs) {
				final String sql = new String(dropDatabaseDDL.getBytes(), "GBK");
				System.out.println(sql);
				dbo.addBatchSql(sql);
			}
			final List<String> createDbDDLs = generateCreateDatabaseDDL(db);
			for (String createDbDDL : createDbDDLs) {
				final String sql = new String(createDbDDL.getBytes(), "GBK");
				System.out.println(sql);
				dbo.addBatchSql(sql);
			}
			if (dbo.batchUpdate()) {
				System.out.println("Successfully Recreated.");
			} else {
				System.out.println("Database Recreated Failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		} finally {
			pl.logout();
			dbo.closeAll();
		}
	}

	private static List<String> generateDropDatabaseDDL(DatabaseDescriptorTO db) throws Exception {
		final List<String> result = new ArrayList<String>();
		final List<TableDescriptorTO> tables = db.getTables();
		StringBuffer ddl = null;
		for (TableDescriptorTO table : tables) {
			ddl = new StringBuffer();
			generateDropTableDDL(ddl, table);
			result.add(ddl.toString());
		}
		return result;
	}

	private static void generateDropTableDDL(StringBuffer ddl, TableDescriptorTO table) {
		ddl.append("DROP TABLE IF EXISTS ").append(table.getName()).append(";\n");
	}

	private static List<String> generateCreateDatabaseDDL(DatabaseDescriptorTO db) throws Exception {
		List<TableDescriptorTO> tables = db.getTables();
		StringBuffer ddl = null;
		List<String> result = new ArrayList<String>();
		for (TableDescriptorTO table : tables) {
			ddl = new StringBuffer();
			generateCreateTableDDL(ddl, table);
			result.add(ddl.toString());
		}
		return result;
	}

	private static void generateCreateTableDDL(StringBuffer ddl, TableDescriptorTO table) {
		String tableName;
		String columnName;
		String columnTypeName;
		int columnDisplaySize;
		int columnPrecision;
		boolean autoIncremental;
		boolean key;
		boolean primaryKey;
		tableName = table.getName();
		List<ColumnDescriptorTO> columns = table.getColumns();

		ddl.append("CREATE TABLE `").append(tableName).append("` (");

		for (ColumnDescriptorTO column : columns) {
			columnName = column.getColumnName();
			columnTypeName = column.getColumnTypeName();
			columnDisplaySize = column.getColumnDisplaySize();
			columnPrecision = column.getColumnPrecision();
			autoIncremental = column.isAutoIncremental();
			key = column.isKey();
			primaryKey = column.isPrimaryKey();
			ddl.append("`").append(columnName).append("`");
			ddl.append(" ").append(getMappedColumnType(columnTypeName, columnDisplaySize, columnPrecision));
			if (primaryKey) {
				ddl.append(" ").append("PRIMARY KEY");
				if (autoIncremental) {
					ddl.append(" ").append("AUTO_INCREMENT");
				}
			} else if (key) {
				ddl.append(" ").append("UNIQUE");
				if (autoIncremental) {
					ddl.append(" ").append("AUTO_INCREMENT");
				}
			}

			ddl.append(",\n");
		}

		ddl.append("_synced TINYINT(1) DEFAULT '0'");

		ddl.append(") ENGINE=InnoDB DEFAULT CHARSET=gbk;");
		ddl.append("\n");
	}

	private static String getMappedColumnType(String columnTypeName, int columnDisplaySize, int columnPrecision) {
		final String mappedColumnType;
		final int mappedColumnSize;
		final int mappedColumnPrecision;

		if (AccessDBDataTypes.COUNTER.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.INTEGER;
			mappedColumnSize = 32;
			mappedColumnPrecision = -1;
		} else if (AccessDBDataTypes.BYTE.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.TINYINT;
			mappedColumnSize = 8;
			mappedColumnPrecision = -1;
		} else if (AccessDBDataTypes.DOUBLE.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.DOUBLE;
			if (0 > columnDisplaySize) {
				mappedColumnSize = 64;
			} else {
				mappedColumnSize = columnDisplaySize;
			}
			if (0 > columnPrecision) {
				mappedColumnPrecision = 0;
			} else {
				mappedColumnPrecision = columnPrecision;
			}
		} else if (AccessDBDataTypes.REAL.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.FLOAT;
			if (0 > columnDisplaySize) {
				mappedColumnSize = 64;
			} else {
				mappedColumnSize = columnDisplaySize;
			}
			if (0 > columnPrecision) {
				mappedColumnPrecision = 0;
			} else {
				mappedColumnPrecision = columnPrecision;
			}
		} else if (AccessDBDataTypes.DECIMAL.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.DECIMAL;
			if (0 > columnDisplaySize) {
				mappedColumnSize = 64;
			} else {
				mappedColumnSize = columnDisplaySize;
			}
			if (0 > columnPrecision) {
				mappedColumnPrecision = 0;
			} else {
				mappedColumnPrecision = columnPrecision;
			}
		} else if (AccessDBDataTypes.SMALLINT.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.SMALLINT;
			if (0 > columnDisplaySize) {
				mappedColumnSize = 64;
			} else {
				mappedColumnSize = columnDisplaySize;
			}
			mappedColumnPrecision = -1;
		}

		else if (AccessDBDataTypes.DATETIME.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.DATETIME;
			mappedColumnSize = -1;
			mappedColumnPrecision = -1;
		} else if (AccessDBDataTypes.INTEGER.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.INTEGER;
			if (0 > columnDisplaySize) {
				mappedColumnSize = 32;
			} else {
				mappedColumnSize = columnDisplaySize;
			}
			mappedColumnPrecision = -1;
		} else if (AccessDBDataTypes.VARCHAR.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.VARCHAR;
			if (0 >= columnDisplaySize) {
				mappedColumnSize = 255;
				mappedColumnPrecision = -1;
			} else {
				mappedColumnSize = columnDisplaySize;
				mappedColumnPrecision = -1;
			}
		} else if (AccessDBDataTypes.BIT.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.BIT;
			mappedColumnSize = 1;
			mappedColumnPrecision = -1;
		} else if (AccessDBDataTypes.LONGCHAR.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.TEXT;
			if (0 >= columnDisplaySize) {
				mappedColumnSize = 255;
				mappedColumnPrecision = -1;
			} else {
				mappedColumnSize = columnDisplaySize;
				mappedColumnPrecision = -1;
			}
		} else if (AccessDBDataTypes.VARBINARY.equalsIgnoreCase(columnTypeName)) {
			mappedColumnType = MysqlDBDataTypes.BLOB;
			if (0 > columnDisplaySize) {
				mappedColumnSize = 64;
			} else {
				mappedColumnSize = columnDisplaySize;
			}
			mappedColumnPrecision = -1;
		}

		else {
			throw new IllegalArgumentException("Not Supported Type: " + columnTypeName);
		}

		final StringBuffer result = new StringBuffer();
		result.append(mappedColumnType);
		if (0 < mappedColumnSize) {
			result.append("(").append(mappedColumnSize);
			if (0 <= mappedColumnPrecision) {
				result.append(", ").append(mappedColumnPrecision);
			}
			result.append(")");
		}
		return result.toString();
	}
}
