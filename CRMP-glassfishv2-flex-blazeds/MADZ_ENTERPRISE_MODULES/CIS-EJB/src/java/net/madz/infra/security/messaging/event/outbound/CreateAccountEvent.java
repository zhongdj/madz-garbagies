package net.madz.infra.security.messaging.event.outbound;

import net.vicp.madz.infra.messaging.core.AbstractEvent;

/**
 * 
 * @author CleaNEr
 */
public class CreateAccountEvent extends AbstractEvent {

	private static final long serialVersionUID = 2118091417889737791L;

	public CreateAccountEvent(String name, final String[] groupNames) {
		this.userName = name;
		this.groupNames = groupNames;
	}

	public CreateAccountEvent(String name, final String[] groupNames, final Long[] roles) {
		this.userName = name;
		this.groupNames = groupNames;
		this.roles = roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String[] getGroupNames() {
		return groupNames.clone();
	}

	public void setGroupNames(final String[] groupNames) {
		this.groupNames = groupNames;
	}

	public Long[] getRoles() {
		return roles.clone();
	}

	public void setRoles(final Long[] roles) {
		this.roles = roles;
	}

	private String userName;
	private String[] groupNames;
	private Long[] roles;
}
