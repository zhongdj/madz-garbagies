/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.security.login.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Administrator
 */
public class RealmProperties {

	private static volatile Properties prop;

	public static Properties getProperties() throws IOException {
		if (prop == null) {
			synchronized (RealmProperties.class) {
				if (prop == null) {
					init();
				}
			}
		}
		return (Properties) prop.clone();
	}

	private RealmProperties() {
	}

	public static void reload() {
		init();
	}

	private static void init() {
		InputStream input = null;
		try {
			input = new FileInputStream("Realm.properties");
			if (prop != null) {
				prop.clear();
			} else {
				prop = new Properties();
			}

			if (input == null) {
				throw new IllegalStateException("Can not load file: Realm.properties");
			}
			prop.load(input);
		} catch (IOException ex) {
			Logger.getLogger(RealmProperties.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException();
		} finally {
			try {
				input.close();
			} catch (IOException ex) {
				Logger.getLogger(RealmProperties.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
