package net.madz.module.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_zip_code", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "ZipCode.findAll", query = "SELECT c FROM ZipCode c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "ZipCode.findById", query = "SELECT c FROM ZipCode c WHERE c.id = :id"),
		@NamedQuery(name = "ZipCode.findByName", query = "SELECT c FROM ZipCode c WHERE c.name = :name AND c.tenant.id = :tenantId") })
public class ZipCode extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "district_id", referencedColumnName = "ID")
	private District district;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "ZipCode [name=" + name + ", district=" + district + "]";
	}

}
