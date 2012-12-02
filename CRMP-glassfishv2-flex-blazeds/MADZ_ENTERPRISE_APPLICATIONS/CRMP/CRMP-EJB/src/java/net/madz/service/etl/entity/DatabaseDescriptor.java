package net.madz.service.etl.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.production.entity.ConcreteMixingPlant;

@Entity
@Table(name = "crmp_meta_db_descriptor", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "findDatabaseDescriptorByName", query = "SELECT o FROM DatabaseDescriptor o WHERE o.name=:name AND o.tenant.id=:tenantId"),
		@NamedQuery(name = "findDatabaseDescriptorByPlantId", query = "SELECT o FROM DatabaseDescriptor o WHERE o.plant.id=:plantId AND o.tenant.id=:tenantId")

}

)
public class DatabaseDescriptor extends StandardObject {

	private static final long serialVersionUID = -8558676279089853741L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ODBC_DATASOURCE_NAME")
	private String odbcDatasourceName;

	@Column(name = "ACCESS_USERNAME")
	private String accessUsername;

	@Column(name = "ACCESS_PASSWORD")
	private String accessPassword;

	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	private ConcreteMixingPlant plant;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "database", fetch = FetchType.EAGER)
	private final List<TableDescriptor> tables = new ArrayList<TableDescriptor>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TableDescriptor> getTables() {
		return Collections.unmodifiableList(tables);
	}

	public void setTables(List<TableDescriptor> tables) {
		this.tables.clear();
		this.tables.addAll(tables);
	}

	/* package */void addTable(TableDescriptor table) {
		if (this.tables.contains(table)) {
			return;
		}
		this.tables.add(table);
	}

	/* pacakge */void remoteTable(TableDescriptor table) {
		if (this.tables.contains(table)) {
			this.tables.remove(table);
		}
	}

	public ConcreteMixingPlant getPlant() {
		return plant;
	}

	public void setPlant(ConcreteMixingPlant plant) {
		this.plant = plant;
	}

	public String getOdbcDatasourceName() {
		return odbcDatasourceName;
	}

	public void setOdbcDatasourceName(String odbcDatasourceName) {
		this.odbcDatasourceName = odbcDatasourceName;
	}

	public String getAccessUsername() {
		return accessUsername;
	}

	public void setAccessUsername(String accessUsername) {
		this.accessUsername = accessUsername;
	}

	public String getAccessPassword() {
		return accessPassword;
	}

	public void setAccessPassword(String accessPassword) {
		this.accessPassword = accessPassword;
	}

}
