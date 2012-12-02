package net.madz.service.etl.to;

import net.madz.service.etl.entity.DatabaseImportIndicator;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class DatabaseSyncIndicatorTO extends StandardTO<DatabaseImportIndicator> {

	private static final long serialVersionUID = -7625839356074017774L;
	@Binding(name = "database.name")
	private String databaseName;
	@Binding(name = "table.name")
	private String tableName;
	@Binding(name = "indicatorColumn.columnName")
	private String indicatorColumnName;
	@Binding(name = "indicatorColumn.columnTypeName")
	private String indicatorColumnType;
	private String indicatorValue;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIndicatorColumnName() {
		return indicatorColumnName;
	}

	public void setIndicatorColumnName(String indicatorColumnName) {
		this.indicatorColumnName = indicatorColumnName;
	}

	public String getIndicatorColumnType() {
		return indicatorColumnType;
	}

	public void setIndicatorColumnType(String indicatorColumnType) {
		this.indicatorColumnType = indicatorColumnType;
	}

	public String getIndicatorValue() {
		return indicatorValue;
	}

	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

}