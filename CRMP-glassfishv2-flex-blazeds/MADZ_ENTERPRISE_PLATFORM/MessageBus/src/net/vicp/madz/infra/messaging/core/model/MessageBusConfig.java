/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vicp.madz.infra.messaging.core.model;

import java.util.HashMap;

/**
 * 
 * @author Barry Zhong
 */
public class MessageBusConfig {
	private HashMap<String, ServiceEndPoint> serviceEndPointMap = new HashMap<String, ServiceEndPoint>();
	private HashMap<String, ConnectionFactory> connectionFactoryMap = new HashMap<String, ConnectionFactory>();
	private HashMap<String, Destination> destinationMap = new HashMap<String, Destination>();
	private HashMap<String, Message> messageConfigMap = new HashMap<String, Message>();

	public HashMap<String, ConnectionFactory> getConnectionFactoryMap() {
		return connectionFactoryMap;
	}

	public void setConnectionFactoryMap(HashMap<String, ConnectionFactory> connectionFactoryMap) {
		this.connectionFactoryMap = connectionFactoryMap;
	}

	public HashMap<String, Destination> getDestinationMap() {
		return destinationMap;
	}

	public void setDestinationMap(HashMap<String, Destination> destinationMap) {
		this.destinationMap = destinationMap;
	}

	public HashMap<String, Message> getMessageConfigMap() {
		return messageConfigMap;
	}

	public void setMessageConfigMap(HashMap<String, Message> messageConfigMap) {
		this.messageConfigMap = messageConfigMap;
	}

	public HashMap<String, ServiceEndPoint> getServiceEndPointMap() {
		return serviceEndPointMap;
	}

	public void setServiceEndPointMap(HashMap<String, ServiceEndPoint> serviceEndPointMap) {
		this.serviceEndPointMap = serviceEndPointMap;
	}
}
