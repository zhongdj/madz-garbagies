package net.madz.service.etl.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.production.entity.ConcreteMixingPlant;

@Entity
@Table(name = "crmp_meta_etl_relation_content_mapping", catalog = "crmp", schema = "")
@NamedQuery(name = "findETLColumnContentMappingDescriptor", query = "SELECT o FROM ETLColumnContentMappingDescriptor o WHERE o.tenant.id = :tenantId AND o.mappingKey = :mappingKey AND o.plant.id = :plantId AND o.rawData = :rawData")
public class ETLColumnContentMappingDescriptor extends StandardObject {

	private static final long serialVersionUID = -8628778252619407994L;

	@Column(name = "RAW_DATA")
	private String rawData;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private ConcreteMixingPlant plant;

	@Column(name = "MAPPING_KEY")
	private String mappingKey;

	@Column(name = "MAPPED_DATA")
	private String mappedData;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private ETLColumnDescriptor columnDescriptor;

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

	public ETLColumnDescriptor getColumnDescriptor() {
		return columnDescriptor;
	}

	public void setColumnDescriptor(ETLColumnDescriptor columnDescriptor) {
		this.columnDescriptor = columnDescriptor;
	}

	public ConcreteMixingPlant getPlant() {
		return plant;
	}

	public void setPlant(ConcreteMixingPlant plant) {
		this.plant = plant;
	}

	public String getMappingKey() {
		return mappingKey;
	}

	public void setMappingKey(String mappingKey) {
		this.mappingKey = mappingKey;
	}

	@Override
	public String toString() {
		return "ETLColumnContentMappingDescriptor [rawData=" + rawData + ", plant=" + plant + ", mappingKey=" + mappingKey
				+ ", mappedData=" + mappedData + ", columnDescriptor=" + columnDescriptor + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", deleted=" + deleted + ", tenant=" + tenant + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", id=" + id + "]";
	}

}
