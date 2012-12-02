/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.account.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_account", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "Account.findAll", query = "SELECT c FROM Account c WHERE c.tenant.id = :tenantId"),
		@NamedQuery(name = "Account.findById", query = "SELECT c FROM Account c WHERE c.id = :id"),
		@NamedQuery(name = "Account.findByName", query = "SELECT c FROM Account c WHERE c.name = :name AND c.tenant.id = :tenantId"),
		@NamedQuery(name = "Account.findByCreatedOn", query = "SELECT c FROM Account c WHERE c.createdOn = :createdOn AND c.tenant.id = :tenantId"),
		@NamedQuery(name = "Account.findByUpdatedOn", query = "SELECT c FROM Account c WHERE c.updatedOn = :updatedOn AND c.tenant.id = :tenantId"),
		@NamedQuery(name = "Account.findByDeleted", query = "SELECT c FROM Account c WHERE c.deleted = :deleted AND c.tenant.id = :tenantId") })
public class Account extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Basic(optional = false)
	@Column(name = "short_name", nullable = false, length = 100)
	private String shortName;

	public Account() {
	}

	public Account(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		return "Account [name=" + name + ", shortName=" + shortName + "]";
	}

}
