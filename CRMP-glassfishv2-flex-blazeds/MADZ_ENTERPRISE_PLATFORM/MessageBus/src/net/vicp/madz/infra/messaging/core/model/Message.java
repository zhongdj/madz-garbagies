/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Barry Zhong
 */
public class Message {

	private static final int PRIME_SEED = 97;
	private static final int HASH_SEED = 7;
	private String name;
	private MessageType messageType;
	private List<Destination> destinationList = new LinkedList<Destination>();
	private List<String> handlerList = new LinkedList<String>();

	public Message(String name) {
		this.name = name;
	}

	public List<String> getHandlerList() {
		return handlerList;
	}

	public void setHandlerList(List<String> handlerList) {
		this.handlerList = handlerList;
	}

	public void addHandler(String handler) {
		this.handlerList.add(handler);
	}

	public void addDestination(Destination destination) {
		destinationList.add(destination);
	}

	public List<Destination> getDestinationList() {
		return destinationList;
	}

	public void setDestinationList(List<Destination> destinationList) {
		this.destinationList = destinationList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Message other = (Message) obj;
		if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = HASH_SEED;
		hash = PRIME_SEED * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}
}
