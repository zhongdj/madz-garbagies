package net.madz.service.etl.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.madz.service.etl.entity.DatabaseDescriptor;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class DatabaseDescriptorTO extends StandardTO<DatabaseDescriptor>
		implements Serializable {

	private static final long serialVersionUID = -4225636075406443780L;

	private String name;

	@Binding(name = "plant.id")
	private String plantId;

	@Binding(name = "plant.name")
	private String plantName;

	private String odbcDatasourceName;

	private final List<TableDescriptorTO> tables = new ArrayList<TableDescriptorTO>();

	private String accessUsername;

	private String accessPassword;

	public String getOdbcDatasourceName() {
		return odbcDatasourceName;
	}

	public void setOdbcDatasourceName(String odbcDatasourceName) {
		this.odbcDatasourceName = odbcDatasourceName;
	}

	public void setAccessUsername(String accessUsername) {
		this.accessUsername = accessUsername;
	}

	public void setAccessPassword(String accessPassword) {
		this.accessPassword = accessPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlantId() {
		return plantId;
	}

	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public List<TableDescriptorTO> getTables() {
		return Collections.unmodifiableList(tables);
	}

	public void addTable(TableDescriptorTO table) {
		if (!tables.contains(table)) {
			tables.add(table);
		}
	}

	public void setTables(List<TableDescriptorTO> tables) {
		this.tables.addAll(tables);
	}

	public String getAccessUsername() {
		return null == accessUsername ? "" : accessUsername;
	}

	public String getAccessPassword() {
		return null == accessPassword ? "" : accessPassword;
	}

}
