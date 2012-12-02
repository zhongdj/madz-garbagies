package net.madz.infra.security.messaging.event.outbound;

import java.io.Serializable;

import net.madz.infra.security.logging.entity.TenantOperationLog;
import net.vicp.madz.infra.messaging.core.AbstractEvent;

public class TenantLogEvent extends AbstractEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private final TenantOperationLog tenantOperationLog;

	public TenantLogEvent(TenantOperationLog log) {
		this.tenantOperationLog = log;
	}

	public TenantOperationLog getTenantOperationLog() {
		return tenantOperationLog;
	}

}
