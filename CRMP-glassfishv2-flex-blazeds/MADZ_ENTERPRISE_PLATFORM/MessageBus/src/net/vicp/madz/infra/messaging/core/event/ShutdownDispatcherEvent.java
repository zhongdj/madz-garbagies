/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core.event;

import java.util.Properties;

import net.vicp.madz.infra.messaging.core.IEvent;

/**
 * 
 * @author dezhong
 */
public class ShutdownDispatcherEvent implements IEvent {

	public Properties getMessageProperties() {
		return new Properties();
	}

	public void setMessageProperties(Properties properties) {
		return;
	}
}
