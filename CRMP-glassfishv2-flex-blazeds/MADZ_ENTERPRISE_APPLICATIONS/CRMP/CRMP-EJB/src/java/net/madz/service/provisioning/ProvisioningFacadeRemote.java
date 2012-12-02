package net.madz.service.provisioning;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

@Remote
@RolesAllowed({ "SA" })
public interface ProvisioningFacadeRemote {
	public void initBasicData(String tenantId);
}
