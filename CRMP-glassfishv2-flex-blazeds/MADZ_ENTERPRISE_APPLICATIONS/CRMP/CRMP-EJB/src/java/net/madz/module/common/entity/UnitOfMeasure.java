/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.persistence.User;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_unit_of_measure", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "UnitOfMeasure.findAll", query = "SELECT c FROM UnitOfMeasure c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfMeasure.findById", query = "SELECT c FROM UnitOfMeasure c WHERE c.id = :id AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfMeasure.findByName", query = "SELECT c FROM UnitOfMeasure c WHERE c.name = :name AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfMeasure.findByDescription", query = "SELECT c FROM UnitOfMeasure c WHERE c.description = :description AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfMeasure.findByCreatedOn", query = "SELECT c FROM UnitOfMeasure c WHERE c.createdOn = :createdOn AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfMeasure.findByUpdatedOn", query = "SELECT c FROM UnitOfMeasure c WHERE c.updatedOn = :updatedOn AND  c.tenant.id = :tenantId"),
		@NamedQuery(name = "UnitOfMeasure.findByDeleted", query = "SELECT c FROM UnitOfMeasure c WHERE c.deleted = :deleted AND  c.tenant.id = :tenantId") })
public class UnitOfMeasure extends StandardObject {
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 40)
	private String name;
	@Column(name = "DESCRIPTION", length = 80)
	private String description;

	public UnitOfMeasure(String name, User createdBy, Tenant tenant) {
		super();
		setName(name);
	}

	public UnitOfMeasure(String name, String description, User createdBy, Tenant tenant) {
		super();
		setDescription(description);
		setName(name);
		setTenant(tenant);
		setCreatedBy(createdBy);
		setCreatedOn(new Date(System.currentTimeMillis()));
	}

	public UnitOfMeasure() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.UnitOfMeasure[id=" + getId() + "]";
	}

}
