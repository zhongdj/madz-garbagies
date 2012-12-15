package net.madz.etl.demo;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;
import net.madz.utils.DbOperator;

import com.sun.appserv.security.ProgrammaticLogin;

public class MysqlDataExtractor {

	public static void main(String[] args) {
		final String webUsername = "administrator";
		final String webPassword = "Barryzdjwin5631";
		final String mysqlUsername = "root";
		final String mysqlPassword = "1q2w3e4r5t";
		final String dataSourceName = "crmp_1_dsn";
		final String mysqlDatabaseName = "crmp_1_db";
		final String mysqlServerIpAddress = "localhost";

		syncRawProductionData2Server(webUsername, webPassword, mysqlUsername,
				mysqlPassword, mysqlDatabaseName, mysqlServerIpAddress);
	}

	public static void syncRawProductionData2Server(final String webUsername,
			final String webPassword, final String mysqlUsername,
			final String mysqlPassword, final String mysqlDatabaseName,
			final String mysqlServerIpAddress) {
		final ProgrammaticLogin pl = new ProgrammaticLogin();
		DbOperator dbo = null;
		PreparedStatement countPs = null;
		PreparedStatement ps = null;
		PreparedStatement updatePs = null;
		try {
			pl.login(webUsername, webPassword);

			// TODO Step 1 get production record metadata from server

			final List<DatabaseSyncIndicatorTO> syncIndicators = ETLDelegate
					.getInstance().getDatabaseSyncIndicator(mysqlDatabaseName);
			if (null == syncIndicators || 0 >= syncIndicators.size()) {
				throw new IllegalStateException(
						"Cannot find syncIndicators from Server.");
			}

			final ETLTableDescriptorTO tableDescriptor = ETLDelegate
					.getInstance().getProductionRecordETLTableDescriptor(
							mysqlDatabaseName);
			DatabaseSyncIndicatorTO syncIndicator = null;
			for (DatabaseSyncIndicatorTO databaseSyncIndicatorTO : syncIndicators) {
				if (tableDescriptor.getRawTableName().equalsIgnoreCase(
						databaseSyncIndicatorTO.getTableName())) {
					syncIndicator = databaseSyncIndicatorTO;
					break;
				}
			}

			if (null == syncIndicator) {
				throw new IllegalStateException(
						"Cannot find SyncIndicator for table : "
								+ tableDescriptor.getRawTableName());
			}

			// TODO Step 2 Assemble SQL
			final StringBuilder sql = new StringBuilder();
			sql.append("SELECT").append(" ");
			final List<ETLColumnDescriptorTO> columnDescriptors = tableDescriptor
					.getColumnDescriptors();
			for (ETLColumnDescriptorTO etlColumn : columnDescriptors) {
				if (null == etlColumn.getRawColumnName()
						|| 0 >= etlColumn.getRawColumnName().length()) {
					continue;
				}
				sql.append(etlColumn.getRawColumnName()).append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" ");
			final String rawTableName = tableDescriptor.getRawTableName();
			// final DatabaseImportIndicatorTO databaseImportIndicator =
			// ETLDelegate.getInstance().getDatabaseImportIndicator(
			// mysqlDatabaseName, rawTableName);
			sql.append("FROM").append(" ").append(rawTableName).append(" ");

			sql.append("WHERE").append(" ");

			if (null != syncIndicator.getIndicatorValue()) {
				sql.append(syncIndicator.getIndicatorColumnName())
						.append(" > ").append("'")
						.append(syncIndicator.getIndicatorValue()).append("'")
						.append(" AND ");
			}
			sql.append("_synced = 0 ORDER BY "
					+ syncIndicator.getIndicatorColumnName()
					+ " ASC LIMIT 2000");

			System.out.println(sql);

			final String mysqlUrl = "jdbc:mysql://" + mysqlServerIpAddress
					+ ":3306/" + mysqlDatabaseName
					+ "?useUnicode=true&amp;CharacterEncoding=GBK";
			// FIXME Get Sync Indicator
			String countSql = "SELECT COUNT(*) FROM " + rawTableName
					+ " WHERE _synced = 0 ";
			if (null != syncIndicator.getIndicatorValue()) {
				countSql += "AND " + syncIndicator.getIndicatorColumnName()
						+ " > '" + syncIndicator.getIndicatorValue() + "'";
			}

			dbo = new DbOperator(mysqlUrl, mysqlUsername, mysqlPassword);
			countPs = dbo.getPreparedStatement(countSql);
			ps = dbo.getPreparedStatement(sql.toString());
			ResultSet countSet = countPs.executeQuery();
			countSet.next();
			int unSyncedCount = countSet.getInt(1);
			ArrayList<RawProductionRecordTO> resultList = null;

			final String updateSql = "UPDATE " + rawTableName
					+ " SET _synced = 1 WHERE "
					+ syncIndicator.getIndicatorColumnName() + " = ?";
			updatePs = dbo.getPreparedStatement(updateSql);

			while (0 < unSyncedCount) {

				resultList = new ArrayList<RawProductionRecordTO>();
				ResultSet rs = ps.executeQuery();
				RawProductionRecordTO importRecord = null;
				String targetFieldName = null;
				Field targetField = null;

				String indicatorValue = null;
				while (rs.next()) {

					// Step 3 Assemble ResultTO
					importRecord = new RawProductionRecordTO();
					for (ETLColumnDescriptorTO etlColumn : columnDescriptors) {

						targetFieldName = etlColumn.getTargetFieldName();
						// FIXME No inheritence supported here.
						targetField = importRecord.getClass().getDeclaredField(
								targetFieldName);
						String value = "";

						if (null == etlColumn.getRawColumnName()) {
							continue;
						} else {
							value = rs.getString(etlColumn.getRawColumnName());
						}

						if (etlColumn.getRawColumnName().equalsIgnoreCase(
								syncIndicator.getIndicatorColumnName())) {
							updatePs.setObject(1, value);
							updatePs.addBatch();
							indicatorValue = value;
						}
						if (targetField.isAccessible()) {
							targetField.set(importRecord, value);
						} else {
							targetField.setAccessible(true);
							targetField.set(importRecord, value);
							targetField.setAccessible(false);
						}
					}
					resultList.add(importRecord);
				}

				// Step 5 UPDATE state
				System.out.println("inserting " + resultList.size()
						+ " records to server.");

				// ProductionDelegate.getInstance().importProductionRecords(
				// resultList.toArray(new RawProductionRecordTO[0]));

				DatabaseDescriptorTO databaseDescriptor = ETLDelegate
						.getInstance().getDatabaseDescriptor(mysqlDatabaseName);

				assert (null != databaseDescriptor.getPlantId());

				ETLDelegate.getInstance().doSyncRawProductionRecords(
						databaseDescriptor.getPlantId(),
						resultList.toArray(new RawProductionRecordTO[0]));

				syncIndicator.setIndicatorValue(indicatorValue);
				ETLDelegate.getInstance().updateDatabaseSyncIndicator(
						syncIndicator);

				System.out.println("inserted " + resultList.size()
						+ " records to server.");

				updatePs.executeBatch();

				// Reset counter;
				countSet = countPs.executeQuery();
				countSet.next();
				unSyncedCount = countSet.getInt(1);
				System.out.println(unSyncedCount + " Left. ");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			pl.logout();
			if (null == updatePs) {
				try {
					updatePs.close();
				} catch (SQLException e) {
				}
			}

			if (null == countPs) {
				try {
					countPs.close();
				} catch (SQLException e) {
				}
			}

			if (null == ps) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

			if (null != dbo) {
				dbo.closeAll();
			}
		}
	}

}
