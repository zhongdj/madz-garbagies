package net.madz.service.etl.to;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.madz.service.etl.entity.TableDescriptor;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class TableDescriptorTO extends StandardTO<TableDescriptor> {

	private static final long serialVersionUID = -300563547708854012L;
	private String name;
	@Binding(name = "columns", accessType = AccessTypeEnum.Property, bindingType = BindingTypeEnum.Entity, embeddedType = ColumnDescriptorTO.class)
	private final List<ColumnDescriptorTO> columns = new ArrayList<ColumnDescriptorTO>();

	public String getName() {
		return name;
	}

	public String getAccessName() {
		return this.name.replaceAll("__", " ");
	}

	public void setName(String name) {
		name = name.replaceAll(" ", "__");
		this.name = name;
	}

	public List<ColumnDescriptorTO> getColumns() {
		return Collections.unmodifiableList(columns);
	}

	public void addColumn(ColumnDescriptorTO column) {
		columns.add(column);
	}

	public void setColumns(List<ColumnDescriptorTO> columns) {
		this.columns.addAll(columns);
	}
}
