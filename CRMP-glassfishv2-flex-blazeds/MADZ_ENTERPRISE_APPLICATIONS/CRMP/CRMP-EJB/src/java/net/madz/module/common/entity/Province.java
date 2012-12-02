package net.madz.module.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_province", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "Province.findAll", query = "SELECT c FROM Province c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "Province.findById", query = "SELECT c FROM Province c WHERE c.id = :id"),
		@NamedQuery(name = "Province.findByName", query = "SELECT c FROM Province c WHERE c.name = :name AND c.tenant.id = :tenantId") })
public class Province extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
