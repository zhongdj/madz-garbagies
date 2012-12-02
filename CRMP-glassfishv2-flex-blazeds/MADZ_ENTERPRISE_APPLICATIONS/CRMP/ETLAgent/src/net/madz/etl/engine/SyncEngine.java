package net.madz.etl.engine;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.madz.etl.demo.MysqlDBDataTypes;
import net.madz.etl.demo.access.AccessDBDataTypes;
import net.madz.etl.engine.ISyncListener.SyncContext;
import net.madz.remote.ETLClientProxy;
import net.madz.service.etl.to.ColumnDescriptorTO;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;
import net.madz.service.etl.to.TableDescriptorTO;
import net.madz.utils.DbOperator;

public class SyncEngine {

	public static final SyncEngine INSTANCE = new SyncEngine();

	private static final int ACCESS_DB_BATCH_SIZE = 5000;

	private static void appendIndicatorLiteral(
			DatabaseImportIndicatorTO indicator, StringBuilder countSb) {
		final String indicatorColumnType = indicator.getIndicatorColumnType();
		if (AccessDBDataTypes.DATETIME.equals(indicatorColumnType)) {
			countSb.append(" #");
		} else if (!indicatorColumnType
				.equalsIgnoreCase(AccessDBDataTypes.COUNTER)) {
			countSb.append(" '");
		}
		countSb.append(indicator.getIndicatorValue());
		if (AccessDBDataTypes.DATETIME.equals(indicatorColumnType)) {
			countSb.append("#");
		} else if (!indicatorColumnType
				.equalsIgnoreCase(AccessDBDataTypes.COUNTER)) {
			countSb.append("'");
		}
	}

	private static int countData(DbOperator acessDbo,
			TableDescriptorTO tableTO, DatabaseImportIndicatorTO indicator)
			throws SQLException {

		if (null == indicator.getIndicatorColumnName()) {
			return 0;
		}

		StringBuilder countSb = new StringBuilder();
		countSb.append("SELECT COUNT(" + indicator.getIndicatorColumnName()
				+ ")");
		countSb.append(" FROM ").append(tableTO.getAccessName()).append(" ");
		if (null != indicator.getIndicatorValue()
				&& 0 < indicator.getIndicatorValue().trim().length()) {
			countSb.append("WHERE ").append(indicator.getIndicatorColumnName())
					.append(" ");
			countSb.append(" > ");
			appendIndicatorLiteral(indicator, countSb);
		}
		PreparedStatement countPs = acessDbo.getPreparedStatement(countSb
				.toString());
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

	private static void generateFromAndWhereClauses(TableDescriptorTO tableTO,
			DatabaseImportIndicatorTO indicator, StringBuilder sb) {
		sb.append(" FROM ").append(tableTO.getAccessName()).append(" ");

		if (null != indicator.getIndicatorValue()
				&& 0 < indicator.getIndicatorValue().trim().length()) {
			sb.append("WHERE ").append(indicator.getIndicatorColumnName())
					.append(" ");
			sb.append(" > ");
			appendIndicatorLiteral(indicator, sb);
		}
		sb.append(" ").append("ORDER BY ")
				.append(indicator.getIndicatorColumnName()).append(" ASC");
	}

	public static SyncEngine getInstance() {
		return INSTANCE;
	}

	// private String dataSourceName = "";
	// private String mysqlDatabaseName = "";

	private static boolean hasSingleQuote(ColumnDescriptorTO column) {
		return !MysqlDBDataTypes.BIT.equalsIgnoreCase(column
				.getColumnTypeName());
	}

	private static boolean requireDefaultNumber(ColumnDescriptorTO column) {
		return MysqlDBDataTypes.DECIMAL.equalsIgnoreCase(column
				.getColumnTypeName())
				|| MysqlDBDataTypes.DOUBLE.equalsIgnoreCase(column
						.getColumnTypeName());
	}

	private volatile ISyncListener listener = new ISyncListener() {

		@Override
		public void onImportBatchEnded(SyncContext context) {
		}

		@Override
		public void onImportBatchFailed(SyncContext context, RuntimeException ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onImportBatchStarted(SyncContext context) {
		}

		@Override
		public void onImportEnded(SyncContext context) {
		}

		@Override
		public void onImportFailed(SyncContext context, RuntimeException ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onImportStarted(SyncContext context) {
		}

		@Override
		public void onSyncBatchEnded(SyncContext context) {
		}

		@Override
		public void onSyncBatchFailed(SyncContext context, RuntimeException ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSyncBatchStarted(SyncContext context) {
		}

		@Override
		public void onSyncEnded(SyncContext context) {
		}

		@Override
		public void onSyncFailed(SyncContext context, RuntimeException ex) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSyncStarted(SyncContext context) {
		}

		@Override
		public void onTaskCompleted() {
		}

		@Override
		public void onTaskFailed(SyncException ex) {
		}

		@Override
		public void onTaskStarted() {
		}
	};

	private String mysqlUsername = "root";

	private String mysqlPassword = "1q2w3e4r5t";

	private String mysqlServerIpAddress = "localhost";

	private SyncEngine() {
		mysqlUsername = System.getProperty("mysql.username");
		mysqlPassword = System.getProperty("mysql.password");
		mysqlServerIpAddress = System.getProperty("mysql.host");
	}

	private void clearContext(String plantId, SyncContext context) {
		context.plantId = plantId;
		context.batchNumber = 0;
		context.completedQuantity = 0;
		context.indicatorText = null;
		context.rawTableName = null;
		context.timeCost = -1L;
	}

	public void doSync(String[] plantIds) {

		if (null == plantIds || 0 >= plantIds.length) {
			return;
		}
		try {
			notifyStarted();
			SyncContext context = new SyncContext();
			DatabaseDescriptorTO db = null;
			for (String plantId : plantIds) {
				clearContext(plantId, context);
				db = ETLClientProxy.getInstance().findDatabaseDescriptor(
						plantId);
				if (null == db) {
					continue;
				}
				long start = notifyImportStarted(context, plantId);
				try {
					// Start to Import
					importDataFromAccessDatabase(context, db);
					notifyImportEnded(context, plantId, start);
				} catch (RuntimeException ex) {
					notifyImportFailed(context, start, ex);
					throw ex;
				}
				clearContext(plantId, context);
				start = notifySyncStarted(context, plantId);

				try { // Start to Sync
					syncRawProductionData2Server(context, db.getName());
					notifySyncEnded(context, plantId, start);
				} catch (RuntimeException ex) {
					notifySyncFailed(context, start, ex);
					throw ex;
				}
			}

			notifyCompleted();
		} catch (Exception ex) {
			notifyFailed(ex);
		} finally {

		}
	}

	private void importDataForTable(final SyncContext context,
			final ETLClientProxy delegate, final DbOperator mysqlDbo,
			final DbOperator accessDbo, final String mysqlDatabaseName,
			final TableDescriptorTO tableTO,
			final DatabaseImportIndicatorTO indicator) {
		PreparedStatement ps = null;

		int batchNumber = 1;

		try {
			int countData = countData(accessDbo, tableTO, indicator);
			while (0 < countData) {

				context.batchNumber = batchNumber++;
				final long start = notifyImportBatchStarted(context);
				try {
					StringBuilder sb = new StringBuilder();
					sb.append("SELECT TOP " + ACCESS_DB_BATCH_SIZE + " ");
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

						context.completedQuantity++;

						insertSb = new StringBuilder();
						columnSb = new StringBuilder();
						valueSb = new StringBuilder();
						insertSb.append("INSERT INTO ")
								.append(tableTO.getName()).append(" ");
						insertSb.append("(");
						for (ColumnDescriptorTO column : tableTO.getColumns()) {
							columnSb.append(column.getColumnName());
							columnSb.append(",");

							if (hasSingleQuote(column)) {
								valueSb.append("'");
							}

							if (indicator.getIndicatorColumnName()
									.equalsIgnoreCase(column.getColumnName())) {
								indicatorValue = rs.getString(indicator
										.getIndicatorColumnName());
								valueSb.append(indicatorValue);
								indicator.setIndicatorValue(indicatorValue);
								context.indicatorText = indicatorValue;
							} else {
								String value = rs.getString(column
										.getColumnName());
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
					} // record level while

					mysqlDbo.batchUpdate();

					delegate.updateDatabaseImportIndicator(indicator);

					notifyImportBatchEnded(context, start);
				} catch (RuntimeException ex) {
					notifyImportBatchFailed(context, start, ex);
					throw ex;
				}
				countData = countData(accessDbo, tableTO, indicator);
			} // batch level while

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

	public void importDataFromAccessDatabase(SyncContext context,
			DatabaseDescriptorTO partialDb) {
		final String accessUsername = partialDb.getAccessUsername();
		final String accessPassword = partialDb.getAccessPassword();
		final String dataSourceName = partialDb.getOdbcDatasourceName();
		final String mysqlDatabaseName = partialDb.getName();
		if (null == dataSourceName) {
			throw new NullPointerException("Please configure Data Source Name at Server side.");
		}
		final String accessUrl = "jdbc:odbc:" + dataSourceName;
		final String mysqlUrl = "jdbc:mysql://" + mysqlServerIpAddress
				+ ":3306/" + mysqlDatabaseName
				+ "?useUnicode=true&amp;CharacterEncoding=GBK";

		final DbOperator mysqlDbo = new DbOperator("com.mysql.jdbc.Driver",
				mysqlUrl, mysqlUsername, mysqlPassword);
		final DbOperator accessDbo = new DbOperator(
				"sun.jdbc.odbc.JdbcOdbcDriver", accessUrl, accessUsername,
				accessPassword);

		try {
			ETLClientProxy delegate = ETLClientProxy.getInstance();

			final List<DatabaseImportIndicatorTO> importIndicators = delegate
					.getDatabaseImportIndicator(mysqlDatabaseName);
			final Map<String, DatabaseImportIndicatorTO> tableIndicatorMap = new HashMap<String, DatabaseImportIndicatorTO>();
			for (DatabaseImportIndicatorTO indicator : importIndicators) {
				tableIndicatorMap.put(indicator.getTableName(), indicator);
			}

			final DatabaseDescriptorTO db = delegate
					.getDatabaseDescriptor(mysqlDatabaseName);

			final List<TableDescriptorTO> tables = db.getTables();
			for (TableDescriptorTO table : tables) {
				if (tableIndicatorMap.containsKey(table.getName())) {

					context.rawTableName = table.getAccessName();
					context.completedQuantity = 0;
					importDataForTable(context, delegate, mysqlDbo, accessDbo,
							mysqlDatabaseName, table,
							tableIndicatorMap.get(table.getName()));

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SyncException(e);
		} finally {
			mysqlDbo.closeAll();
			accessDbo.closeAll();
		}
	}

	private void notifyCompleted() {
		listener.onTaskCompleted();
	}

	private void notifyFailed(Exception ex) {
		listener.onTaskFailed(new SyncException(ex));
	}

	private void notifyImportBatchEnded(SyncContext context, long start) {
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onImportBatchEnded(context);
	}

	private void notifyImportBatchFailed(SyncContext context, long start,
			RuntimeException ex) {
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onImportBatchFailed(context, ex);

	}

	private long notifyImportBatchStarted(SyncContext context) {
		long start = System.currentTimeMillis();
		context = context.clone();
		listener.onImportBatchStarted(context);
		return start;
	}

	private void notifyImportEnded(SyncContext context, String plantId,
			long start) {
		context.plantId = plantId;
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onImportEnded(context);
	}

	private void notifyImportFailed(SyncContext context, long start,
			RuntimeException ex) {
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onImportFailed(context, ex);
	}

	private long notifyImportStarted(SyncContext context, String plantId) {
		long start = System.currentTimeMillis();
		context.plantId = plantId;
		context = context.clone();
		listener.onImportStarted(context);
		return start;
	}

	private void notifyStarted() {
		listener.onTaskStarted();
	}

	private void notifySyncBatchEnded(SyncContext context, long start) {
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onSyncBatchEnded(context);
	}

	private void notifySyncBatchFailed(SyncContext context, long start,
			RuntimeException ex) {
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onSyncBatchFailed(context, ex);
	}

	private long notifySyncBatchStarted(SyncContext context) {
		long start = System.currentTimeMillis();
		context = context.clone();
		listener.onSyncBatchStarted(context);
		return start;
	}

	private void notifySyncEnded(SyncContext context, String plantId, long start) {
		context.plantId = plantId;
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onSyncEnded(context);
	}

	private void notifySyncFailed(SyncContext context, long start,
			RuntimeException ex) {
		context.timeCost = System.currentTimeMillis() - start;
		context = context.clone();
		listener.onSyncFailed(context, ex);
	}

	private long notifySyncStarted(SyncContext context, String plantId) {
		long start;
		start = System.currentTimeMillis();
		context.plantId = plantId;
		context = context.clone();
		listener.onSyncStarted(context);
		return start;
	}

	public void setSyncListener(ISyncListener listener) {
		if (null == listener) {
			throw new NullPointerException("listener cannot be null.");
		}
		this.listener = listener;
	}

	public void syncRawProductionData2Server(SyncContext context,
			final String mysqlDatabaseName) {
		DbOperator dbo = null;
		PreparedStatement countPs = null;
		PreparedStatement ps = null;
		PreparedStatement updatePs = null;
		int batchNumber = 1;
		try {
			// TODO Step 1 get production record metadata from server

			final List<DatabaseSyncIndicatorTO> syncIndicators = ETLClientProxy
					.getInstance().getDatabaseSyncIndicator(mysqlDatabaseName);
			if (null == syncIndicators || 0 >= syncIndicators.size()) {
				throw new IllegalStateException(
						"Cannot find syncIndicators from Server.");
			}

			final ETLTableDescriptorTO tableDescriptor = ETLClientProxy
					.getInstance().getProductionRecordETLTableDescriptor(
							mysqlDatabaseName);

			context.rawTableName = tableDescriptor.getRawTableName();

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
			// ETLClientProxy.getInstance().getDatabaseImportIndicator(
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

			context.completedQuantity = 0;
			context.indicatorText = null;
			long start = -1L;

			while (0 < unSyncedCount) {
				context.batchNumber = batchNumber++;
				start = notifySyncBatchStarted(context);
				try {
					resultList = new ArrayList<RawProductionRecordTO>();
					ResultSet rs = ps.executeQuery();
					RawProductionRecordTO importRecord = null;
					String targetFieldName = null;
					Field targetField = null;

					String indicatorValue = null;
					while (rs.next()) {

						context.completedQuantity++;

						// Step 3 Assemble ResultTO
						importRecord = new RawProductionRecordTO();
						for (ETLColumnDescriptorTO etlColumn : columnDescriptors) {

							targetFieldName = etlColumn.getTargetFieldName();
							// FIXME No inheritence supported here.
							targetField = importRecord.getClass()
									.getDeclaredField(targetFieldName);
							String value = "";

							if (null == etlColumn.getRawColumnName()) {
								continue;
							} else {
								value = rs.getString(etlColumn
										.getRawColumnName());
							}

							if (etlColumn.getRawColumnName().equalsIgnoreCase(
									syncIndicator.getIndicatorColumnName())) {
								updatePs.setObject(1, value);
								updatePs.addBatch();
								indicatorValue = value;
								context.indicatorText = indicatorValue;
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

					DatabaseDescriptorTO databaseDescriptor = ETLClientProxy
							.getInstance().getDatabaseDescriptor(
									mysqlDatabaseName);

					assert (null != databaseDescriptor.getPlantId());

					ETLClientProxy.getInstance().doSyncRawProductionRecords(
							databaseDescriptor.getPlantId(),
							resultList.toArray(new RawProductionRecordTO[0]));

					syncIndicator.setIndicatorValue(indicatorValue);
					ETLClientProxy.getInstance().updateDatabaseSyncIndicator(
							syncIndicator);

					System.out.println("inserted " + resultList.size()
							+ " records to server.");

					updatePs.executeBatch();
					notifySyncBatchEnded(context, start);
				} catch (RuntimeException ex) {
					notifySyncBatchFailed(context, start, ex);
					throw ex;
				}
				// context.subPhase = SyncPhaseEnum.SyncEnd;

				// Reset counter;
				countSet = countPs.executeQuery();
				countSet.next();
				unSyncedCount = countSet.getInt(1);
				System.out.println(unSyncedCount + " Left. ");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (null != updatePs) {
				try {
					updatePs.close();
				} catch (SQLException e) {
				}
			}

			if (null != countPs) {
				try {
					countPs.close();
				} catch (SQLException e) {
				}
			}

			if (null != ps) {
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
