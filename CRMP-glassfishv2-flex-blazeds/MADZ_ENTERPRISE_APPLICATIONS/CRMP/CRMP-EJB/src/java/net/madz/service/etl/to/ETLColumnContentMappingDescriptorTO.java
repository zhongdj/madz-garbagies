package net.madz.service.etl.to;

import net.madz.service.etl.entity.ETLColumnContentMappingDescriptor;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class ETLColumnContentMappingDescriptorTO extends StandardTO<ETLColumnContentMappingDescriptor> {

	private static final long serialVersionUID = 1L;
	private String rawData;
	private String mappingKey;
	private String mappedData;
	@Binding(name = "columnDescriptor.id")
	private String columnDescriptorId;

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getMappedData() {
		return mappedData;
	}

	public void setMappedData(String mappedData) {
		this.mappedData = mappedData;
	}

	public String getColumnDescriptorId() {
		return columnDescriptorId;
	}

	public void setColumnDescriptorId(String columnDescriptorId) {
		this.columnDescriptorId = columnDescriptorId;
	}

	public String getMappingKey() {
		return mappingKey;
	}

	public void setMappingKey(String mappingKey) {
		this.mappingKey = mappingKey;
	}

	@Override
	public String toString() {
		return "ETLColumnContentMappingDescriptorTO [rawData=" + rawData + ", mappedData=" + mappedData + ", columnDescriptor="
				+ columnDescriptorId + "]";
	}

}
