/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.client.security.delegate;

import java.util.List;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.infra.security.facade.UIQueryFacadeRemote;
import net.madz.infra.security.persistence.MenuItem;

/**
 * 
 * @author CleaNEr
 */
public class UIQueryDelegate implements UIQueryFacadeRemote {

	private static UIQueryDelegate instance;
	private UIQueryFacadeRemote server;

	public static synchronized UIQueryDelegate getInstance() throws Exception {
		if (instance == null) {
			synchronized (UIDelegate.class) {
				if (instance == null) {
					instance = new UIQueryDelegate();
				}
			}
		}
		return instance;
	}

	private UIQueryDelegate() throws Exception {
		server = (UIQueryFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(UIQueryFacadeRemote.class.getName());
	}

	public List<MenuItem> findMenuItems() throws NamingException {
		return server.findMenuItems();
	}

	public String getMenuXMLDescription() throws NamingException {
		return server.getMenuXMLDescription();
	}
}
