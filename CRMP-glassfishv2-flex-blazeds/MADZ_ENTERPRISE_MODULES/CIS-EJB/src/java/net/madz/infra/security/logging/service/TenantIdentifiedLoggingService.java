package net.madz.infra.security.logging.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.infra.logging.service.LoggingService;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.logging.entity.TenantOperationLog;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.util.TenantResources;

public class TenantIdentifiedLoggingService extends LoggingService {

	private static final long serialVersionUID = 1L;
	protected Tenant company;

	public TenantIdentifiedLoggingService(TenantIdentifiedService service) {
		super(service);
		company = service.getTenant();
	}

	@Override
	protected void execute() {
		if (null == company) {
			try {
				company = TenantResources.getTenant();
			} catch (Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "Company cannot be obtained.");
			}
		}
		String sourceName = service.getSourceName();
		if (sourceName != null && sourceName.length() > 100) {
			sourceName = sourceName.substring(0, 96) + "...";
		}
		TenantOperationLog log = new TenantOperationLog(service.getServiceName(), service.getUserName(), service.getSourceType(),
				sourceName, service.getDescription(), service.getStart(), service.getComplete(), service.getTimeCost(),
				service.isSucceed(), company);
		log.setFailedCause(service.getFailedCause());
		em.persist(log);
	}

}
