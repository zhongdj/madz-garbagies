/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core.model;

import java.util.Properties;

/**
 * 
 * @author Barry Zhong
 */
public class ServiceEndPoint {

	private static final int PRIME_SEED = 97;
	private static final int HASH_SEED = 3;
	private String name;
	private Properties contextParameter = new Properties();

	public ServiceEndPoint(String serviceName) {
		this.name = serviceName;
	}

	public Properties getContextParameter() {
		return contextParameter;
	}

	public void setContextParameter(Properties contextParameter) {
		this.contextParameter = contextParameter;
	}

	public void setProperty(String key, String value) {
		contextParameter.setProperty(key, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ServiceEndPoint other = (ServiceEndPoint) obj;
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
