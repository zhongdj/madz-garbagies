package net.madz.infra.security.to.query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.madz.infra.security.persistence.User;
import net.madz.infra.security.persistence.UserGroup;

public class UserLoginQTO {

	private String id;
	private java.lang.String userName;
	private java.lang.String password;
	private List<String> groups;
	private Timestamp loginDate;
	private Timestamp lastLoginDate;
	private Timestamp lastFailedTime;
	private Timestamp lastChangePwdTime;
	private Date userCreateTime;

	public UserLoginQTO() {
	}

	public UserLoginQTO(User account) {
		this.id = account.getId();
		this.userCreateTime = account.getCreatedOn();
		this.userName = account.getUsername();

		if (account.getGroups() != null) {
			for (UserGroup g : account.getGroups()) {
				groups.add(g.getName());
			}
		}

		this.lastChangePwdTime = account.getLastChangePwdTime();
		this.lastFailedTime = account.getLastFailedTime();
		this.lastLoginDate = account.getLastLoginDate();
		this.loginDate = account.getLoginDate();
		this.password = account.getPassword();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserName(java.lang.String accountName) {
		this.userName = accountName;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public void setUserCreateTime(Timestamp accountCreateTime) {
		this.userCreateTime = accountCreateTime;
	}

	public void setLastChangePwdTime(Timestamp lastChangePwdTime) {
		this.lastChangePwdTime = lastChangePwdTime;
	}

	public void setLastFailedTime(Timestamp lastFailedTime) {
		this.lastFailedTime = lastFailedTime;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public void setLoginDate(Timestamp loginDate) {
		this.loginDate = loginDate;
	}

	public String getId() {
		return this.id;
	}

	public java.lang.String getUserName() {
		return this.userName;
	}

	public java.lang.String getPassword() {
		return this.password;
	}

	public List<String> getGroups() {
		return this.groups;
	}

	public Date getUserCreateTime() {
		return userCreateTime;
	}

	public Timestamp getLastChangePwdTime() {
		return lastChangePwdTime;
	}

	public Timestamp getLastFailedTime() {
		return lastFailedTime;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public Timestamp getLoginDate() {
		return loginDate;
	}
}
