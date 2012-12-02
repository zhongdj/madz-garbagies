/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.persistence;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author Barry Zhong
 */
@Entity
@Table(name = "CRMP_User")
@NamedQueries({
		@NamedQuery(name = "validate", query = "SELECT COUNT(a) FROM User AS a WHERE a.username = :accountName AND a.password= :password"),
		@NamedQuery(name = "checkExist", query = "SELECT COUNT(a) FROM User AS a WHERE a.username = :accountName"),
		@NamedQuery(name = "checkNotLocked", query = "SELECT COUNT(a) FROM User AS a WHERE a.lockFlag = false AND a.username=:accountName"),
		@NamedQuery(name = "User.findByUsername", query = "SELECT OBJECT(a) FROM User AS a WHERE a.username = :username"),
		@NamedQuery(name = "findByAccountNameFuzzy", query = "SELECT OBJECT(a) FROM User AS a WHERE a.username LIKE :accountName"),
		@NamedQuery(name = "findByAccountIdFuzzy", query = "SELECT OBJECT(a) FROM User AS a WHERE a.id LIKE :id") })
public class User extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Column(unique = true, updatable = false, length = 40)
	private String username;
	private String password;
	private String email;
	private String fullName;
	private boolean lockFlag;
	private int loginTimes;
	private int loginFailedTimes;
	private int accessDeniedTimes;
	private boolean freezenFlag;
	private boolean needResetPwd;
	private Timestamp loginDate;
	private Timestamp lastLoginDate;
	private Timestamp lastFailedTime;
	private Timestamp lastChangePwdTime;
	private String oldPasswords;
	@ManyToOne(fetch = FetchType.EAGER)
	private Tenant tenant;
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "CRMP_USER_USER_GROUP", joinColumns = @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "GROUP_NAME", referencedColumnName = "NAME"))
	private List<UserGroup> groups = new LinkedList<UserGroup>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<Role> roles = new LinkedList<Role>();

	public User() {
	}

	public User(String accountName, String password, List<UserGroup> groups, Tenant company) {
		this.setUsername(accountName);
		this.setPassword(password);
		this.setNeedResetPwd(true);
		this.setGroups(groups);
		this.setTenant(company);
	}

	public int getAccessDeniedTimes() {
		return accessDeniedTimes;
	}

	public void setAccessDeniedTimes(int accessDeniedTimes) {
		this.accessDeniedTimes = accessDeniedTimes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String accountName) {
		this.username = accountName;
	}

	public boolean isFreezenFlag() {
		return freezenFlag;
	}

	public void setFreezenFlag(boolean freezenFlag) {
		this.freezenFlag = freezenFlag;
	}

	public Timestamp getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Timestamp loginDate) {
		this.loginDate = loginDate;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	public int getLoginFailedTimes() {
		return loginFailedTimes;
	}

	public void setLoginFailedTimes(int loginFailedTimes) {
		this.loginFailedTimes = loginFailedTimes;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public boolean isNeedResetPwd() {
		return needResetPwd;
	}

	public void setNeedResetPwd(boolean needResetPwd) {
		this.needResetPwd = needResetPwd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getLastFailedTime() {
		return lastFailedTime;
	}

	public void setLastFailedTime(Timestamp lastFailedTime) {
		this.lastFailedTime = lastFailedTime;
	}

	public Timestamp getLastChangePwdTime() {
		return lastChangePwdTime;
	}

	public void setLastChangePwdTime(Timestamp lastChangePwdTime) {
		this.lastChangePwdTime = lastChangePwdTime;
	}

	public String getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(String oldPasswords) {
		this.oldPasswords = oldPasswords;
	}

	public List<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroup> groups) {
		if (null == groups) {
			if (this.groups != null) {
				this.groups.clear();
			}
			return;
		} else {
			if (this.groups != null && this.groups.size() > 0) {
				for (UserGroup userGroup : this.groups) {
					userGroup.removeUser(this);
				}
			}
			this.groups = groups;
			for (UserGroup userGroup : groups) {
				userGroup.addUser(this);
			}
		}
	}

	public void removeAllUserGroup() {
		if (getGroups() != null && getGroups().size() > 0) {
			for (UserGroup group : getGroups()) {
				removeUserGroup(group);
			}
		}
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant company) {
		this.tenant = company;
	}

	public void addUserGroup(UserGroup group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
	}

	public void removeUserGroup(UserGroup group) {
		if (groups.contains(group)) {
			groups.remove(group);
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (role == null || role.getId() == null || role.getId().length() <= 0) {
			return;
		}
		if (!roles.contains(role)) {
			roles.add(role);
			role.addUser(this);
		}
	}

	public void removeRole(Role role) {
		if (role == null || role.getId() == null || role.getId().length() <= 0) {
			return;
		}
		if (roles.contains(role)) {
			roles.remove(role);
			role.removeUser(this);
		}
	}

	public void removeAllRoles() {
		if (getRoles() != null && getRoles().size() > 0) {
			for (Role role : getRoles()) {
				removeRole(role);
			}
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	// Business Methods
	public void freeze() {
		this.setFreezenFlag(true);
	}

	public void unFreezeAccount() {
		this.setFreezenFlag(false);
	}

	public void lock() {
		this.setLockFlag(true);
	}

	public void unlock() {
		this.setLockFlag(false);
	}

	public void resetPassword(String oldPassword, String newPassword, String confirmPassword) {
		// TODO implements resetPassword
	}

	@Override
	public String toString() {
		String groupStr = "";
		if (groups != null) {
			for (UserGroup group : groups) {
				groupStr += group.getName();
			}
		}
		StringBuilder s = new StringBuilder();
		s.append("Account [ name=").append(username).append(", groups=").append(groupStr).append(", lockFlag=").append(lockFlag)
				.append(", loginTimes=").append(loginTimes).append(", loginFailedTimes=").append(loginFailedTimes)
				.append(", accessDeniedTimes=").append(accessDeniedTimes).append(", freezen=").append(freezenFlag).append(", loginDate =")
				.append(loginDate).append(", lastChangePwdTime=").append(lastChangePwdTime).append("]");
		return s.toString();
	}
}
