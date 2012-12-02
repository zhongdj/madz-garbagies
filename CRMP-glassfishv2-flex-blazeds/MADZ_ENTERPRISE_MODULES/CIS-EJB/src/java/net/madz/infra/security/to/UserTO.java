package net.madz.infra.security.to;

import java.sql.Timestamp;
import java.util.List;

import net.madz.infra.security.persistence.User;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class UserTO extends StandardTO<User> {

	private static final long serialVersionUID = -2694300473269589088L;

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

	@Binding(name = "groups", accessType = AccessTypeEnum.Field, bindingType = BindingTypeEnum.Entity, embeddedType = UserGroupTO.class)
	private List<UserGroupTO> userGroupList;
	@Binding(name = "roles", accessType = AccessTypeEnum.Field, bindingType = BindingTypeEnum.Entity, embeddedType = RoleTO.class)
	private List<RoleTO> roleList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public boolean isLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public int getLoginFailedTimes() {
		return loginFailedTimes;
	}

	public void setLoginFailedTimes(int loginFailedTimes) {
		this.loginFailedTimes = loginFailedTimes;
	}

	public int getAccessDeniedTimes() {
		return accessDeniedTimes;
	}

	public void setAccessDeniedTimes(int accessDeniedTimes) {
		this.accessDeniedTimes = accessDeniedTimes;
	}

	public boolean isFreezenFlag() {
		return freezenFlag;
	}

	public void setFreezenFlag(boolean freezenFlag) {
		this.freezenFlag = freezenFlag;
	}

	public boolean isNeedResetPwd() {
		return needResetPwd;
	}

	public void setNeedResetPwd(boolean needResetPwd) {
		this.needResetPwd = needResetPwd;
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

	public List<UserGroupTO> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<UserGroupTO> userGroupList) {
		this.userGroupList = userGroupList;
	}

	public List<RoleTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleTO> roleList) {
		this.roleList = roleList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserTO [username=" + username + ", password=" + password + ", email=" + email + ", fullName=" + fullName + ", lockFlag="
				+ lockFlag + ", loginTimes=" + loginTimes + ", loginFailedTimes=" + loginFailedTimes + ", accessDeniedTimes="
				+ accessDeniedTimes + ", freezenFlag=" + freezenFlag + ", needResetPwd=" + needResetPwd + ", loginDate=" + loginDate
				+ ", lastLoginDate=" + lastLoginDate + ", lastFailedTime=" + lastFailedTime + ", lastChangePwdTime=" + lastChangePwdTime
				+ ", userGroupList=" + userGroupList + ", roleList=" + roleList + "]";
	}

}
