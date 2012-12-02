package net.madz.service.etl.to;

import java.io.Serializable;

import net.madz.service.etl.entity.ColumnDescriptor;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class ColumnDescriptorTO extends StandardTO<ColumnDescriptor> implements Serializable {

	private static final long serialVersionUID = -8122099834799743309L;
	private String columnName;
	private String columnLabel;
	private String columnType;
	private String columnTypeName;
	private int columnDisplaySize;
	private int columnPrecision;
	private int minVersion;
	private int maxVersion;
	private boolean autoIncremental;
	@Binding(name = "keyColumn")
	private boolean key;
	@Binding(name = "primaryKeyColumn")
	private boolean primaryKey;

	public boolean isAutoIncremental() {
		return autoIncremental;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		columnName = columnName.replaceAll(" ", "__");
		this.columnName = columnName;
	}

	public String getAccessColumnName() {
		return this.columnName.replaceAll("__", " ");
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public void setColumnDisplaySize(int columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}

	public int getColumnPrecision() {
		return columnPrecision;
	}

	public void setColumnPrecision(int columnPrecision) {
		this.columnPrecision = columnPrecision;
	}

	public int getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(int minVersion) {
		this.minVersion = minVersion;
	}

	public int getMaxVersion() {
		return maxVersion;
	}

	public void setMaxVersion(int maxVersion) {
		this.maxVersion = maxVersion;
	}

	public void setAutoIncremental(boolean autoIncremental) {
		this.autoIncremental = autoIncremental;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

}
