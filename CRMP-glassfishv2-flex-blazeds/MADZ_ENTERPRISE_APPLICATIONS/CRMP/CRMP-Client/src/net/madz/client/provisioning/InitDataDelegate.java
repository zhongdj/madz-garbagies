package net.madz.client.provisioning;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.infra.util.CachingServiceLocator;
import net.madz.service.provisioning.ProvisioningFacadeRemote;

public class InitDataDelegate implements ProvisioningFacadeRemote {

	private static InitDataDelegate instance;
	private ProvisioningFacadeRemote server;

	private InitDataDelegate() throws Exception {
		try {
			server = (ProvisioningFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(ProvisioningFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(ProvisioningFacadeRemote.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public static synchronized InitDataDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new InitDataDelegate();
		}
		return instance;
	}

	@Override
	public void initBasicData(String tenantId) {
		server.initBasicData(tenantId);
	}

}
