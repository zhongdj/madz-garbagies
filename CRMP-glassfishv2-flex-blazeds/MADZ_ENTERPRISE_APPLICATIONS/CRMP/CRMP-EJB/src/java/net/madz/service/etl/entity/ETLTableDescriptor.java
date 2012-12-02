package net.madz.service.etl.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_meta_etl_table_descriptor", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "findETLTableDescriptorByDatabaseName", query = "SELECT o FROM ETLTableDescriptor o WHERE o.targetClassName= :targetClassName AND o.database.name=:dbName AND o.tenant.id=:tenantId") }

)
public class ETLTableDescriptor extends StandardObject {

	private static final long serialVersionUID = 6258584620459939778L;

	@Column(name = "RAW_TABLE_NAME")
	private String rawTableName;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "TARGET_CLASS_NAME")
	private String targetClassName;

	@ManyToOne(fetch = FetchType.EAGER)
	private DatabaseDescriptor database;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tableDescriptor", fetch = FetchType.EAGER)
	private final List<ETLColumnDescriptor> columnDescriptors = new ArrayList<ETLColumnDescriptor>();

	@OneToOne(fetch = FetchType.EAGER)
	private TableDescriptor table;
	
	void addColumnDescriptor(ETLColumnDescriptor column) {
		if (columnDescriptors.contains(column)) {
			return;
		}
		columnDescriptors.add(column);
	}

	public List<ETLColumnDescriptor> getColumnDescriptors() {
		return columnDescriptors;
	}

	public DatabaseDescriptor getDatabase() {
		return database;
	}

	public String getDescription() {
		return description;
	}

	public String getRawTableName() {
		return rawTableName;
	}

	public TableDescriptor getTable() {
		return table;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	void removeColumnDescriptor(ETLColumnDescriptor column) {
		if (!columnDescriptors.contains(column)) {
			return;
		}
		columnDescriptors.remove(column);
	}

	public void setDatabase(DatabaseDescriptor database) {
		this.database = database;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRawTableName(String rawTableName) {
		this.rawTableName = rawTableName;
	}

	public void setTable(TableDescriptor table) {
		this.table = table;
	}

	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}

}
