/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author Barry Zhong
 */
public class AbstractEvent implements IEvent {

	private static final long serialVersionUID = 9063972005459971134L;
	protected Properties messageProperties;

	public Properties getMessageProperties() {
		return this.messageProperties;
	}

	public void setMessageProperties(Properties messageProperties) {
		this.messageProperties = messageProperties;
	}

	public String getEventReceiverNames() {
		StringBuilder names = new StringBuilder();
		if (this.messageProperties != null) {
			Set<Entry<Object, Object>> sets = this.messageProperties.entrySet();
			for (Entry<Object, Object> entry : sets) {
				names.append("key:[" + entry.getKey() + "], value:[" + entry.getValue() + "]\n");
			}
		} else {
			names.append("messageProperties is null");
		}
		return names.toString();
	}
}
