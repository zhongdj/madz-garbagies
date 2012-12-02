package net.vicp.madz.infra.messaging.core;

import java.util.Properties;

import net.vicp.madz.infra.messaging.core.model.MessageType;

public class MessageConnectivityConfig {

	private Properties envProperties;
	private String username;
	private String password;
	private String connectionFactoryJNDIName;
	private String destinationJNDIName;
	private MessageType messageType;

	public void setConnectionFactoryJNDIName(String connectionFactoryJNDIName) {
		this.connectionFactoryJNDIName = connectionFactoryJNDIName;
	}

	public void setDestinationJNDIName(String destinationJNDIName) {
		this.destinationJNDIName = destinationJNDIName;
	}

	public void setEnvProperties(Properties envProperties) {
		this.envProperties = envProperties;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConnectionFactoryJNDIName() {
		return connectionFactoryJNDIName;
	}

	public String getDestinationJNDIName() {
		return destinationJNDIName;
	}

	public Properties getEnvProperties() {
		return envProperties;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

}
