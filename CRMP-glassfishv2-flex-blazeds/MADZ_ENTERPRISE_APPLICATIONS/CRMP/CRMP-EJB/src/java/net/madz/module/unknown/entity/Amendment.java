/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.unknown.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.contract.entity.ContractBO;

/**
 * 
 * @author a
 */
@Entity
@Table(name = "crmp_amendment", catalog = "crmp", schema = "")
@NamedQueries({
		@NamedQuery(name = "Amendment.findAll", query = "SELECT c FROM Amendment c"),
		@NamedQuery(name = "Amendment.findById", query = "SELECT c FROM Amendment c WHERE c.id = :id"),
		@NamedQuery(name = "Amendment.findByAmendmentType", query = "SELECT c FROM Amendment c WHERE c.amendmentType = :amendmentType"),
		@NamedQuery(name = "Amendment.findByState", query = "SELECT c FROM Amendment c WHERE c.state = :state"),
		@NamedQuery(name = "Amendment.findByOldBillMethod", query = "SELECT c FROM Amendment c WHERE c.oldBillMethod = :oldBillMethod"),
		@NamedQuery(name = "Amendment.findByNewBillMethod", query = "SELECT c FROM Amendment c WHERE c.newBillMethod = :newBillMethod"),
		@NamedQuery(name = "Amendment.findByEffectiveStartDate", query = "SELECT c FROM Amendment c WHERE c.effectiveStartDate = :effectiveStartDate"),
		@NamedQuery(name = "Amendment.findByEffectiveEndDate", query = "SELECT c FROM Amendment c WHERE c.effectiveEndDate = :effectiveEndDate"),
		@NamedQuery(name = "Amendment.findByCreatedOn", query = "SELECT c FROM Amendment c WHERE c.createdOn = :createdOn"),
		@NamedQuery(name = "Amendment.findByUpdatedOn", query = "SELECT c FROM Amendment c WHERE c.updatedOn = :updatedOn"),
		@NamedQuery(name = "Amendment.findByDeleted", query = "SELECT c FROM Amendment c WHERE c.deleted = :deleted") })
public class Amendment extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "amendment_type", nullable = false, length = 20)
	private String amendmentType;
	@Basic(optional = false)
	@Column(name = "state", nullable = false, length = 20)
	private String state;
	@Basic(optional = false)
	@Column(name = "old_bill_method", nullable = false, length = 20)
	private String oldBillMethod;
	@Basic(optional = false)
	@Column(name = "new_bill_method", nullable = false, length = 20)
	private String newBillMethod;
	@Column(name = "effective_start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;
	@Column(name = "effective_end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;
	@JoinColumn(name = "contract_Id", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ContractBO contract;

	public Amendment() {
	}

	public Amendment(String amendmentType, String state, String oldBillMethod, String newBillMethod) {
		this.amendmentType = amendmentType;
		this.state = state;
		this.oldBillMethod = oldBillMethod;
		this.newBillMethod = newBillMethod;
	}

	public String getAmendmentType() {
		return amendmentType;
	}

	public void setAmendmentType(String amendmentType) {
		this.amendmentType = amendmentType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOldBillMethod() {
		return oldBillMethod;
	}

	public void setOldBillMethod(String oldBillMethod) {
		this.oldBillMethod = oldBillMethod;
	}

	public String getNewBillMethod() {
		return newBillMethod;
	}

	public void setNewBillMethod(String newBillMethod) {
		this.newBillMethod = newBillMethod;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public ContractBO getContractId() {
		return contract;
	}

	public void setContractId(ContractBO contractId) {
		this.contract = contractId;
	}

	@Override
	public String toString() {
		return "net.madz.module.unknown.entity.Amendment[id=" + getId() + "]";
	}
}
