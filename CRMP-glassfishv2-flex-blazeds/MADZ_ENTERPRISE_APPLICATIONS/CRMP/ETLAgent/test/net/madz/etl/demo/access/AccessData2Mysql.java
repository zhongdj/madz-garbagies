package net.madz.etl.demo.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.etl.db.AccessDBDataTypes;
import net.madz.etl.db.MysqlDBDataTypes;
import net.madz.service.etl.to.ColumnDescriptorTO;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.TableDescriptorTO;
import net.madz.utils.DbOperator;

import com.sun.appserv.security.ProgrammaticLogin;

public class AccessData2Mysql {

	// private static DbOperator dbo = null;
	// private static MetadataDelegate delegate = null;

	public static void main(String[] args) {
		final String accessUsername = "";
		final String accessPassword = "";
		final String webUsername = "administrator";
		final String webPassword = "Barryzdjwin5631";
		final String mysqlUsername = "root";
		final String mysqlPassword = "1q2w3e4r5t";
		final String dataSourceName = "crmp_1_dsn";
		final String mysqlDatabaseName = "crmp_1_db";
		final String mysqlServerIpAddress = "localhost";

		importDataFromAccessDatabase(accessUsername, accessPassword, 
				webUsername, webPassword, mysqlUsername, mysqlPassword,
				dataSourceName, mysqlDatabaseName, mysqlServerIpAddress);
	}

	public static void importDataFromAccessDatabase(final String accessUsername, final String accessPassword, final String webUsername,
			final String webPassword, final String mysqlUsername, final String mysqlPassword, final String dataSourceName,
			final String mysqlDatabaseName, final String mysqlServerIpAddress) {
		final String accessUrl = "jdbc:odbc:" + dataSourceName;
		final String mysqlUrl = "jdbc:mysql://" + mysqlServerIpAddress + ":3306/" + mysqlDatabaseName
				+ "?useUnicode=true&amp;CharacterEncoding=GBK";

		final DbOperator mysqlDbo = new DbOperator("com.mysql.jdbc.Driver", mysqlUrl, mysqlUsername, mysqlPassword);
		final DbOperator accessDbo = new DbOperator("sun.jdbc.odbc.JdbcOdbcDriver", accessUrl, accessUsername, accessPassword);

		final ProgrammaticLogin pl = new ProgrammaticLogin();
		try {
			pl.login(webUsername, webPassword);

			ETLDelegate delegate = ETLDelegate.getInstance();

			final List<DatabaseImportIndicatorTO> importIndicators = delegate.getDatabaseImportIndicator(mysqlDatabaseName);
			final Map<String, DatabaseImportIndicatorTO> tableIndicatorMap = new HashMap<String, DatabaseImportIndicatorTO>();
			for (DatabaseImportIndicatorTO indicator : importIndicators) {
				tableIndicatorMap.put(indicator.getTableName(), indicator);
			}

			final DatabaseDescriptorTO db = delegate.getDatabaseDescriptor(mysqlDatabaseName);

			final List<TableDescriptorTO> tables = db.getTables();
			for (TableDescriptorTO table : tables) {
				if (tableIndicatorMap.containsKey(table.getName())) {
					importDataForTable(delegate, mysqlDbo, accessDbo, mysqlDatabaseName, table, tableIndicatorMap.get(table.getName()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mysqlDbo.closeAll();
			accessDbo.closeAll();
			pl.logout();
		}
	}

	private static void importDataForTable(final ETLDelegate delegate, final DbOperator mysqlDbo, final DbOperator accessDbo,
			final String mysqlDatabaseName, final TableDescriptorTO tableTO, final DatabaseImportIndicatorTO indicator) {
		PreparedStatement ps = null;
		try {
			while (0 < countData(accessDbo, tableTO, indicator)) {

				StringBuilder sb = new StringBuilder();
				sb.append("SELECT TOP 5000 ");
				for (ColumnDescriptorTO column : tableTO.getColumns()) {
					sb.append(column.getAccessColumnName());
					sb.append(",");
				}
				sb.deleteCharAt(sb.length() - 1);

				generateFromAndWhereClauses(tableTO, indicator, sb);

				StringBuilder insertSb = null;

				ps = accessDbo.getPreparedStatement(sb.toString());
				ResultSet rs = ps.executeQuery();
				StringBuilder columnSb = null;
				StringBuilder valueSb = null;
				String indicatorValue = null;

				while (rs.next()) {
					insertSb = new StringBuilder();
					columnSb = new StringBuilder();
					valueSb = new StringBuilder();
					insertSb.append("INSERT INTO ").append(tableTO.getName()).append(" ");
					insertSb.append("(");
					for (ColumnDescriptorTO column : tableTO.getColumns()) {
						columnSb.append(column.getColumnName());
						columnSb.append(",");

						if (hasSingleQuote(column)) {
							valueSb.append("'");
						}

						if (indicator.getIndicatorColumnName().equalsIgnoreCase(column.getColumnName())) {
							indicatorValue = rs.getString(indicator.getIndicatorColumnName());
							valueSb.append(indicatorValue);
							indicator.setIndicatorValue(indicatorValue);
						} else {
							String value = rs.getString(column.getColumnName());
							if (null == value || 0 >= value.length()) {
								if (requireDefaultNumber(column)) {
									value = "0";
								} else {
									value = "";
								}
							}
							valueSb.append(value);
						}
						if (hasSingleQuote(column)) {
							valueSb.append("'");
						}
						valueSb.append(",");

					}
					columnSb.deleteCharAt(columnSb.length() - 1);
					valueSb.deleteCharAt(valueSb.length() - 1);

					insertSb.append(columnSb).append(" ");
					insertSb.append(") VALUES (");
					insertSb.append(valueSb);
					insertSb.append(")");

					mysqlDbo.addBatchSql(insertSb.toString());
				}

				mysqlDbo.batchUpdate();

				delegate.updateDatabaseImportIndicator(indicator);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException ignored) {
				}
			}
		}
	}

	private static boolean requireDefaultNumber(ColumnDescriptorTO column) {
		return MysqlDBDataTypes.DECIMAL.equalsIgnoreCase(column.getColumnTypeName())
				|| MysqlDBDataTypes.DOUBLE.equalsIgnoreCase(column.getColumnTypeName());
	}

	private static boolean hasSingleQuote(ColumnDescriptorTO column) {
		return !MysqlDBDataTypes.BIT.equalsIgnoreCase(column.getColumnTypeName());
	}

	private static int countData(DbOperator acessDbo, TableDescriptorTO tableTO, DatabaseImportIndicatorTO indicator) throws SQLException {
		StringBuilder countSb = new StringBuilder();
		countSb.append("SELECT COUNT(" + indicator.getIndicatorColumnName() + ")");
		countSb.append(" FROM ").append(tableTO.getAccessName()).append(" ");
		if (null != indicator.getIndicatorValue() && 0 < indicator.getIndicatorValue().trim().length()) {
			countSb.append("WHERE ").append(indicator.getIndicatorColumnName()).append(" ");
			countSb.append(" > ");
			appendIndicatorLiteral(indicator, countSb);
		}
		PreparedStatement countPs = acessDbo.getPreparedStatement(countSb.toString());
		try {
			System.out.println(countSb.toString());
			ResultSet countRs = countPs.executeQuery();
			countRs.next();
			String counterString = countRs.getString(1);
			return Integer.valueOf(counterString);
		} finally {
			if (null != countPs) {
				countPs.close();
			}
		}
	}

	private static void appendIndicatorLiteral(
			DatabaseImportIndicatorTO indicator, StringBuilder countSb) {
		final String indicatorColumnType = indicator.getIndicatorColumnType();
		if (AccessDBDataTypes.DATETIME.equals(indicatorColumnType)) {
			countSb.append(" #");
		} else if (!indicatorColumnType.equalsIgnoreCase(AccessDBDataTypes.COUNTER)) {
			countSb.append(" '");
		}
		countSb.append(indicator.getIndicatorValue());
		if (AccessDBDataTypes.DATETIME.equals(indicatorColumnType)) {
			countSb.append("#");
		} else if (!indicatorColumnType.equalsIgnoreCase(AccessDBDataTypes.COUNTER)) {
			countSb.append("'");
		}
	}

	private static void generateFromAndWhereClauses(TableDescriptorTO tableTO, DatabaseImportIndicatorTO indicator, StringBuilder sb) {
		sb.append(" FROM ").append(tableTO.getAccessName()).append(" ");

		if (null != indicator.getIndicatorValue() && 0 < indicator.getIndicatorValue().trim().length()) {
			sb.append("WHERE ").append(indicator.getIndicatorColumnName()).append(" ");
			sb.append(" > ");
			appendIndicatorLiteral(indicator, sb);
		}
		sb.append(" ").append("ORDER BY ").append(indicator.getIndicatorColumnName()).append(" ASC");
	}

}