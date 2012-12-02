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
@Table(name = "crmp_district", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "District.findAll", query = "SELECT c FROM District c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "District.findById", query = "SELECT c FROM District c WHERE c.id = :id"),
		@NamedQuery(name = "District.findByName", query = "SELECT c FROM District c WHERE c.name = :name AND c.tenant.id = :tenantId") })
public class District extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "ID")
	private City city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
