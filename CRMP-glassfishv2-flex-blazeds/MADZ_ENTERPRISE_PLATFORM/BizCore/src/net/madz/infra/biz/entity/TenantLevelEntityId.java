package net.madz.infra.biz.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TenantLevelEntityId {

	@Id
	private int tenantId;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long dataSeq;

	public TenantLevelEntityId() {
		super();
	}

	public TenantLevelEntityId(int tenantId, long dataSeq) {
		super();
		this.tenantId = tenantId;
		this.dataSeq = dataSeq;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public long getDataSeq() {
		return dataSeq;
	}

	public void setDataSeq(long dataSeq) {
		this.dataSeq = dataSeq;
	}

}
