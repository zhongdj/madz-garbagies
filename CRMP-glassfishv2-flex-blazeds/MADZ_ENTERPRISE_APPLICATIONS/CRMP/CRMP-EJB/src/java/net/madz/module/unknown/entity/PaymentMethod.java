/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.module.unknown.entity;

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
@Table(name = "crmp_payment_method", catalog = "crmp", schema = "")
@NamedQueries({ @NamedQuery(name = "PaymentMethod.findAll", query = "SELECT c FROM PaymentMethod c"),
		@NamedQuery(name = "PaymentMethod.findById", query = "SELECT c FROM PaymentMethod c WHERE c.id = :id"),
		@NamedQuery(name = "PaymentMethod.findByName", query = "SELECT c FROM PaymentMethod c WHERE c.name = :name"),
		@NamedQuery(name = "PaymentMethod.findByCreatedOn", query = "SELECT c FROM PaymentMethod c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "PaymentMethod.findByUpdatedOn", query = "SELECT c FROM PaymentMethod c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "PaymentMethod.findByDeleted", query = "SELECT c FROM PaymentMethod c WHERE c.deleted = :deleted") })
public class PaymentMethod extends StandardObject {
	private static final long serialVersionUID = 1L;
	@Column(name = "name", length = 50)
	private String name;

	public PaymentMethod() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.PaymentMethod[id=" + getId() + "]";
	}

}
