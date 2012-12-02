package net.madz.client.provisioning;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.account.biz.delegate.AccountDelegate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class ProvisioningTesting {

	ProgrammaticLogin pl = new ProgrammaticLogin();
	InitDataDelegate instance = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
		pl.login("administrator", "Barryzdjwin5631");
		try {
			instance = InitDataDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(AccountDelegate.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
		pl.logout();
	}

	@Test
	public void testInitBasicData() {
		String tenantId = "1";
		instance.initBasicData(tenantId);
	}

}
