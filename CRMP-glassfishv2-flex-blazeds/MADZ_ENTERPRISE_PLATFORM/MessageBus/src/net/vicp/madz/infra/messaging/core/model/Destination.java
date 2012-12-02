/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core.model;

/**
 * 
 * @author Barry Zhong
 */
public class Destination {
	private static final int HASH_SEED = 5;
	private static final int PRIME_SEED = 29;
	private String name;
	private DestinationType type;
	private ConnectionFactory connectionFactory;

	public Destination(String name) {
		this.name = name;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DestinationType getType() {
		return type;
	}

	public void setType(DestinationType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Destination other = (Destination) obj;
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
