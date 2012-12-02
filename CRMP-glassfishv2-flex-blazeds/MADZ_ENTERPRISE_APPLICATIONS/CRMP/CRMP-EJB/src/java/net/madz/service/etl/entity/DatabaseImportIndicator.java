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
@Table(name = "crmp_meta_database_import_indicator", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "findDatabaseImportIndicatorByDatabaseName", query = "SELECT o FROM DatabaseImportIndicator o WHERE o.database.name = :databaseName AND o.tenant.id = :tenantId"),
		@NamedQuery(name = "findDatabaseImportIndicatorByDatabaseNameAndTableName", query = "SELECT o FROM DatabaseImportIndicator o WHERE o.database.name = :databaseName AND o.table.name = :tableName AND o.tenant.id = :tenantId") })
public class DatabaseImportIndicator extends StandardObject {

	private static final long serialVersionUID = -270942457605422085L;

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
			table.setImportIndicator(this);
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

}
