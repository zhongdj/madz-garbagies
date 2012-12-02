/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.security.login.management;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.vicp.madz.security.login.factory.EncryptorFactory;
import net.vicp.madz.security.login.impl.Principal;
import net.vicp.madz.security.login.impl.RealmProperties;

/**
 * 
 * @author Administrator
 */
public class RealmPropertiesReloader implements RealmPropertiesReloaderMBean {

	public RealmPropertiesReloader() {
	}

	public void reload() {
		RealmProperties.reload();
		try {
			Properties prop = RealmProperties.getProperties();
			EncryptorFactory.getInstance().resetProperties(prop);
			prop = RealmProperties.getProperties();
			Principal.resetProperties(prop);
		} catch (IOException ex) {
			Logger.getLogger(RealmPropertiesReloader.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
