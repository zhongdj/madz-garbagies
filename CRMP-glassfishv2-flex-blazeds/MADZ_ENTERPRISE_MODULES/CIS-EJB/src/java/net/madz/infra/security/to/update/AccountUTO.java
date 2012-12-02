/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.to.update;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author CleaNEr
 */
public class AccountUTO implements Serializable {

	private static final long serialVersionUID = -3756132472286990759L;
	private java.lang.String accountName;
	private java.lang.String password;
	private List<String> groups;
	private Long id;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
