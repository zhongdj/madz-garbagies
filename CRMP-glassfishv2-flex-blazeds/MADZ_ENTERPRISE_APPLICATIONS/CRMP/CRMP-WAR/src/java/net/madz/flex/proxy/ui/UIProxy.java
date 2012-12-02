/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.flex.proxy.ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.security.delegate.UIDelegate;
import net.madz.infra.security.facade.UIException;
import net.madz.infra.security.facade.UIFacadeRemote;
import net.madz.infra.security.persistence.MenuItem;
import net.madz.infra.security.persistence.Role;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.persistence.UserGroup;
import net.madz.infra.security.to.MenuItemTO;
import net.madz.infra.security.to.RoleTO;
import net.madz.infra.security.to.UserGroupTO;
import net.madz.infra.security.to.UserTO;

/**
 * 
 * @author CleaNEr
 */
public class UIProxy implements UIFacadeRemote {

	private UIDelegate delegate;

	public UIProxy() {
		try {
			delegate = UIDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(UIProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	public void createUser(User user) {
		delegate.createUser(user);
	}

	public String createMenuItem(MenuItem menuItem) {
		return delegate.createMenuItem(menuItem);
	}

	public void createUserGroup(UserGroup group) {
		delegate.createUserGroup(group);
	}

	public String createRole(Role role) {
		return delegate.createRole(role);
	}

	public void removeUser(User user) {
		delegate.removeUser(user);
	}

	public void removeMenuItem(MenuItem menuItem) throws UIException {
		delegate.removeMenuItem(menuItem);
	}

	public void removeUserGroup(UserGroup group) throws UIException {
		delegate.removeUserGroup(group);
	}

	public void removeRole(Role role) throws UIException {
		delegate.removeRole(role);
	}

	public void updateMenuItem(MenuItem menuItem) {

		delegate.updateMenuItem(menuItem);

	}

	@Override
	public String createMenuItem(MenuItemTO menuItem) {
		return delegate.createMenuItem(menuItem);
	}

	@Override
	public String createRole(RoleTO role) {
		return delegate.createRole(role);
	}

	@Override
	public String createUserGroup(UserGroupTO group) {
		return delegate.createUserGroup(group);
	}

	@Override
	public String createUser(UserTO user) {
		return delegate.createUser(user);
	}

	@Override
	public void simpleUpdateMenuItems(List<MenuItemTO> menuItems) {
		delegate.simpleUpdateMenuItems(menuItems);
	}

	@Override
	public void simpleUpdateRoles(List<RoleTO> roles) {
		delegate.simpleUpdateRoles(roles);
	}

	@Override
	public void simpleUpdateUserGroups(List<UserGroupTO> userGroups) {
		delegate.simpleUpdateUserGroups(userGroups);
	}

	@Override
	public void simpleUpdateUser(UserTO user) {
		delegate.simpleUpdateUser(user);
	}

	@Override
	public List<MenuItemTO> findAllMenuItem() {
		return delegate.findAllMenuItem();
	}

	@Override
	public List<RoleTO> findAllRole() {
		return delegate.findAllRole();
	}

	@Override
	public List<UserGroupTO> findAllUserGroup() {
		return delegate.findAllUserGroup();
	}

	@Override
	public List<UserTO> findAllUser() {
		return delegate.findAllUser();
	}

	@Override
	public void assignUserGroupToUser(String userId, List<String> groupIds) {
		delegate.assignUserGroupToUser(userId, groupIds);
	}

	@Override
	public void removeUserGroupToUser(String userId, List<String> groupIds) {
		delegate.removeUserGroupToUser(userId, groupIds);
	}

	@Override
	public void assignRoleToUser(String userId, List<String> roleIds) {
		delegate.assignRoleToUser(userId, roleIds);
	}

	@Override
	public void removeRoleToUser(String userId, List<String> roleIds) {
		delegate.removeRoleToUser(userId, roleIds);
	}

	@Override
	public void assignRoleToUserGroup(String groupId, List<String> roleIds) {
		delegate.assignRoleToUserGroup(groupId, roleIds);
	}

	@Override
	public void removeRoleToUserGroup(String groupId, List<String> roleIds) {
		delegate.removeRoleToUserGroup(groupId, roleIds);
	}

	@Override
	public void assignMenuItemToRole(String roleId, List<String> menuItemIds) {
		delegate.assignMenuItemToRole(roleId, menuItemIds);
	}

	@Override
	public void removeMenuItemToRole(String roleId, List<String> menuItemIds) {
		delegate.removeMenuItemToRole(roleId, menuItemIds);
	}

	@Override
	public MenuItemTO findMenuItemById(String id) {
		return delegate.findMenuItemById(id);
	}

	@Override
	public void removeMenuItems(List<String> menuItems) {
		delegate.removeMenuItems(menuItems);
	}

}
