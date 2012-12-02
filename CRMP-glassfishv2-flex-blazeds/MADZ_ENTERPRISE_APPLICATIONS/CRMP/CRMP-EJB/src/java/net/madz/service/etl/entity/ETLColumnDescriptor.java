package net.madz.service.etl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_meta_etl_column_descriptor", catalog = "crmp", schema = "")
public class ETLColumnDescriptor extends StandardObject {

	private static final long serialVersionUID = 501838352085413724L;
	@Column(name = "RAW_COLUMN_NAME")
	private String rawColumnName;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "TARGET_FIELD_NAME")
	private String targetFieldName;

	@ManyToOne(fetch = FetchType.EAGER)
	private ETLTableDescriptor tableDescriptor;

	public String getRawColumnName() {
		return rawColumnName;
	}

	public void setRawColumnName(String columnName) {
		this.rawColumnName = columnName;
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

	public ETLTableDescriptor getTableDescriptor() {
		return tableDescriptor;
	}

	public void setTableDescriptor(ETLTableDescriptor tableDescriptor) {
		if (null == tableDescriptor) {
			if (null != this.tableDescriptor) {
				this.tableDescriptor.removeColumnDescriptor(this);
				this.tableDescriptor = null;
				return;
			} else {
				return;
			}
		} else if (null == this.tableDescriptor) {
			this.tableDescriptor = tableDescriptor;
			this.tableDescriptor.addColumnDescriptor(this);
		} else if (this.tableDescriptor != tableDescriptor) {
			this.tableDescriptor.removeColumnDescriptor(this);
			this.tableDescriptor = tableDescriptor;
			this.tableDescriptor.addColumnDescriptor(this);
		}
	}

}
