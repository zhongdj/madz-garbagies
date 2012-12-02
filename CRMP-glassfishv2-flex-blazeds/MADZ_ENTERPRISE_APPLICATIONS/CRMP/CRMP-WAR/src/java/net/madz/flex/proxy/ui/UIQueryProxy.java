/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.flex.proxy.ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.security.delegate.UIQueryDelegate;
import net.madz.infra.security.facade.UIQueryFacadeRemote;
import net.madz.infra.security.persistence.MenuItem;

/**
 * 
 * @author CleaNEr
 */
public class UIQueryProxy implements UIQueryFacadeRemote {
	private UIQueryDelegate delegate;

	public UIQueryProxy() {
		try {
			delegate = UIQueryDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(UIQueryProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	public List<MenuItem> findMenuItems() throws NamingException {
		return delegate.findMenuItems();
	}

	public String getMenuXMLDescription() throws NamingException {
		return delegate.getMenuXMLDescription();
	}
}
