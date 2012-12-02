package net.madz.infra.security.logging.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.madz.infra.logging.entity.OperationLog;
import net.madz.infra.security.persistence.Tenant;

@Entity
@Table(name = "crmp_tenant_operation_log")
public class TenantOperationLog extends OperationLog {

	private static final long serialVersionUID = 1L;
	@ManyToOne(optional = true)
	private Tenant tenant;

	public TenantOperationLog() {

	}

	public TenantOperationLog(String serviceName, String userName, String sourceType, String sourceName, String description,
			Timestamp start, Timestamp complete, long timeCost, boolean succeed, Tenant tenant) {
		super(serviceName, userName, sourceType, sourceName, description, start, complete, timeCost, succeed);
		this.tenant = tenant;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant company) {
		this.tenant = company;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (null == object) {
			return false;
		}
		if (object.getClass() != this.getClass()) {
			return false;
		}

		TenantOperationLog other = (TenantOperationLog) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

}
