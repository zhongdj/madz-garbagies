package net.madz.service.etl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_meta_database_sync_indicator", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "findDatabaseSyncIndicatorByDatabaseName", query = "SELECT o FROM DatabaseSyncIndicator o WHERE o.database.name = :databaseName AND o.tenant.id = :tenantId") })
public class DatabaseSyncIndicator extends StandardObject {

	private static final long serialVersionUID = 8182538032126551272L;

	@ManyToOne(fetch = FetchType.EAGER)
	private DatabaseDescriptor database;

	@OneToOne(fetch = FetchType.EAGER)
	private TableDescriptor table;

	@OneToOne(fetch = FetchType.EAGER)
	private ColumnDescriptor indicatorColumn;

	@Column(name = "INDICATOR_VALUE")
	private String indicatorValue;

	public DatabaseDescriptor getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseDescriptor database) {
		this.database = database;
	}

	public TableDescriptor getTable() {
		return table;
	}

	public void setTable(TableDescriptor table) {
		this.table = table;
		if (null != table) {
			table.setSyncIndicator(this);
		}
	}

	public ColumnDescriptor getIndicatorColumn() {
		return indicatorColumn;
	}

	public void setIndicatorColumn(ColumnDescriptor indicatorColumn) {
		this.indicatorColumn = indicatorColumn;
	}

	public String getIndicatorValue() {
		return indicatorValue;
	}

	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	@Override
	public String toString() {
		return "DatabaseSyncIndicator [database=" + database + ", table=" + table + ", indicatorColumn=" + indicatorColumn
				+ ", indicatorValue=" + indicatorValue + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", deleted=" + deleted
				+ ", tenant=" + tenant + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", id=" + id + "]";
	}

}
