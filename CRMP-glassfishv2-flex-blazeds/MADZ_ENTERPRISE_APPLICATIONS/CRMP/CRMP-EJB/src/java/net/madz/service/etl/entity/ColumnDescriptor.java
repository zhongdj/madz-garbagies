package net.madz.service.etl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_meta_column_descriptor", catalog = "crmp", schema = "")
public class ColumnDescriptor extends StandardObject {

	private static final long serialVersionUID = -906919389171795101L;

	@Column(name = "COLUMN_NAME")
	private String columnName;
	@Column(name = "COLUMN_LABEL")
	private String columnLabel;
	@Column(name = "COLUMN_TYPE")
	private String columnType;
	@Column(name = "COLUMN_TYPE_NAME")
	private String columnTypeName;
	@Column(name = "COLUMN_DISPLAY_SIZE")
	private int columnDisplaySize;
	@Column(name = "COLUMN_PRECISION")
	private int columnPrecision;
	@Column(name = "MIN_VERSION")
	private int minVersion;
	@Column(name = "MAX_VERSION")
	private int maxVersion;
	@Column(name = "AUTO_INCREMENTAL")
	private boolean autoIncremental;
	@Column(name = "KEY_COLUMN")
	private boolean keyColumn;
	@Column(name = "PRIMARY_KEY_COLUMN")
	private boolean primaryKeyColumn;

	@ManyToOne
	private TableDescriptor table;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public boolean isAutoIncremental() {
		return autoIncremental;
	}

	public void setAutoIncremental(boolean autoIncremental) {
		this.autoIncremental = autoIncremental;
	}

	public boolean isKey() {
		return keyColumn;
	}

	public void setKey(boolean key) {
		this.keyColumn = key;
	}

	public boolean isPrimaryKey() {
		return primaryKeyColumn;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKeyColumn = primaryKey;
	}

	public TableDescriptor getTable() {
		return table;
	}

	public void setTable(TableDescriptor table) {
		if (null == table) {
			return;
		}
		if (null != this.table && !this.table.equals(table)) {
			this.table.removeColumn(this);
			this.table = table;
			this.table.addColumn(this);
		} else if (null == this.table) {
			this.table = table;
			this.table.addColumn(this);
		}
	}

}
