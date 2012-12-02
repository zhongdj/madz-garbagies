package net.madz.service.etl.to;

import net.madz.service.etl.entity.ETLColumnDescriptor;
import net.madz.standard.StandardTO;

public class ETLColumnDescriptorTO extends StandardTO<ETLColumnDescriptor> {

	private static final long serialVersionUID = -8669410473584693483L;

	private String rawColumnName;
	private String description;
	private String targetFieldName;

	public String getRawColumnName() {
		return rawColumnName;
	}

	public void setRawColumnName(String rawColumnName) {
		this.rawColumnName = rawColumnName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetFieldName() {
		return targetFieldName;
	}

	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}

}
