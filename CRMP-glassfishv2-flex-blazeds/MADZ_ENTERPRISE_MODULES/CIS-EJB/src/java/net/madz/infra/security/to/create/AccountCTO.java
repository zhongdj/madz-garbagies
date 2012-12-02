/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.to.create;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;

/**
 * 
 * @author CleaNEr
 */
public class AccountCTO implements Serializable, IServiceArgument {

	private static final long serialVersionUID = 1757221406020060209L;
	private String accountName;
	private String password;
	private List<String> groups = new LinkedList<String>();

	public void addGroup(String name) {
		if (!groups.contains(name)) {
			groups.add(name);
		}
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * private String accountName; private String password; private List<String>
	 * groups;
	 * 
	 * @throws net.madz.infra.biz.core.ValidationException
	 */
	public void validate() throws ValidationException {
		if (null == accountName || accountName.trim().length() <= 0) {
			throw new ValidationException("Account Name must not be empty!");
		}
		if (null == password || password.trim().length() <= 0) {
			throw new ValidationException("Password must not be empty!");
		}
		if (null == groups || groups.size() <= 0) {
			throw new ValidationException("User's Group must not be empty!");
		}
	}

	public String getArgumentName() {
		return accountName;
	}
}
