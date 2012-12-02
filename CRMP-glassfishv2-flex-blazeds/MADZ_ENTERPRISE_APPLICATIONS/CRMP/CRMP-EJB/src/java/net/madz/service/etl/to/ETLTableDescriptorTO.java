package net.madz.service.etl.to;

import java.util.List;

import net.madz.service.etl.entity.ETLTableDescriptor;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class ETLTableDescriptorTO extends StandardTO<ETLTableDescriptor> {

	private static final long serialVersionUID = 8456223089851069308L;

	private String databaseName;
	private String rawTableName;
	private String description;
	private String targetClassName;
	@Binding(name = "columnDescriptors", accessType = AccessTypeEnum.Property, bindingType = BindingTypeEnum.Entity, embeddedType = ETLColumnDescriptorTO.class)
	private List<ETLColumnDescriptorTO> columnDescriptors;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getRawTableName() {
		return rawTableName;
	}

	public void setRawTableName(String rawTableName) {
		this.rawTableName = rawTableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}

	public List<ETLColumnDescriptorTO> getColumnDescriptors() {
		return columnDescriptors;
	}

	public void setColumnDescriptors(List<ETLColumnDescriptorTO> columnDescriptors) {
		this.columnDescriptors = columnDescriptors;
	}

}
