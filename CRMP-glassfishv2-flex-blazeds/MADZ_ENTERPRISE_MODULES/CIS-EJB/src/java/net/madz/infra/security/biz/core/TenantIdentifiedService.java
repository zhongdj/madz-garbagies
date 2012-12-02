package net.madz.infra.security.biz.core;

import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.util.TenantResources;

public abstract class TenantIdentifiedService extends ApplicationService {
	private static final long serialVersionUID = 1L;
	protected Tenant tenant;

	public TenantIdentifiedService(Object... args) {
		super(args);
		try {
			tenant = TenantResources.getTenant();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tenant getTenant() {
		return tenant;
	}
}
