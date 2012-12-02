/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.messaging.event;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.infra.util.ResourceHelper;
import net.vicp.madz.infra.messaging.core.AbstractEvent;

/**
 * 
 * @author Barry
 */
public class IdentifiedEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;
	protected String userName;

	public IdentifiedEvent() throws Exception {
		try {
			this.userName = ResourceHelper.getPrincipalName();
			if (null == userName || userName.trim().length() <= 0) {
				throw new Exception("Cannot obtain valid userName");
			}
		} catch (NamingException ex) {
			Logger.getLogger(IdentifiedEvent.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public IdentifiedEvent(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
}
