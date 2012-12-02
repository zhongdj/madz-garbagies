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
@Table(name = "crmp_city", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "City.findById", query = "SELECT c FROM City c WHERE c.id = :id"),
		@NamedQuery(name = "City.findByName", query = "SELECT c FROM City c WHERE c.name = :name AND c.tenant.id = :tenantId") })
public class City extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "province_id", referencedColumnName = "ID")
	private Province province;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
