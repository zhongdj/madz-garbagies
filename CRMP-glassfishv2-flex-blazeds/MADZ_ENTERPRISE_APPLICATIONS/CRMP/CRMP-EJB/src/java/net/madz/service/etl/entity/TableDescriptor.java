package net.madz.service.etl.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_meta_table_descriptor", catalog = "crmp", schema = "")
public class TableDescriptor extends StandardObject {

	private static final long serialVersionUID = 3411304319763842178L;
	@Column(name = "NAME")
	private String name;
	@ManyToOne
	private DatabaseDescriptor database;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "table", fetch = FetchType.EAGER)
	private final List<ColumnDescriptor> columns = new ArrayList<ColumnDescriptor>();

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "table", fetch = FetchType.EAGER)
	private DatabaseImportIndicator importIndicator;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "table", fetch = FetchType.EAGER)
	private DatabaseSyncIndicator syncIndicator;

	/* package */void addColumn(ColumnDescriptor column) {
		if (!this.columns.contains(column)) {
			this.columns.add(column);
		}
	}

	public List<ColumnDescriptor> getColumns() {
		return Collections.unmodifiableList(columns);
	}

	public DatabaseDescriptor getDatabase() {
		return database;
	}

	public DatabaseImportIndicator getImportIndicator() {
		return importIndicator;
	}

	public String getName() {
		return name;
	}

	public DatabaseSyncIndicator getSyncIndicator() {
		return syncIndicator;
	}

	/* package */void removeColumn(ColumnDescriptor column) {
		if (this.columns.contains(column)) {
			this.columns.remove(column);
		}
	}

	public void setColumns(List<ColumnDescriptor> columns) {
		this.columns.clear();
		this.columns.addAll(columns);
	}

	public void setDatabase(DatabaseDescriptor database) {
		if (null == database) {
			return;
		}
		if (null == this.database) {
			this.database = database;
			this.database.addTable(this);
		} else if (!this.database.equals(database)) {
			this.database.remoteTable(this);
			this.database = database;
			this.database.addTable(this);
		}
	}

	/* package */void setImportIndicator(DatabaseImportIndicator importIndicator) {
		this.importIndicator = importIndicator;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* package */void setSyncIndicator(DatabaseSyncIndicator syncIndicator) {
		this.syncIndicator = syncIndicator;
	}
}
