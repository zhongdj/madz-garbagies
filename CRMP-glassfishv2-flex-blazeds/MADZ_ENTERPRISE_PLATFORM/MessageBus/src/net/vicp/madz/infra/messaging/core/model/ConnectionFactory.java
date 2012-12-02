/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core.model;

/**
 * 
 * @author Barry Zhong
 */
public class ConnectionFactory {

	private static final int PRIME_SEED = 43;
	private static final int HASH_SEED = 3;
	private String name;
	private FactoryType type;
	private String userName;
	private String password;
	private ServiceEndPoint serviceProvider;

	public ConnectionFactory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ServiceEndPoint getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceEndPoint serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public FactoryType getType() {
		return type;
	}

	public void setType(FactoryType type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConnectionFactory other = (ConnectionFactory) obj;
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
