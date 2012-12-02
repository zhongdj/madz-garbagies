/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.client.security.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
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
public class UIDelegate implements UIFacadeRemote {

	private static UIDelegate instance;
	private UIFacadeRemote server;

	public static synchronized UIDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new UIDelegate();
		}
		return instance;
	}

	private UIDelegate() throws Exception {
		try {
			server = (UIFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(UIFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(UIDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	public void createUser(User user) {
		server.createUser(user);
	}

	public String createMenuItem(MenuItem menuItem) {
		return server.createMenuItem(menuItem);
	}

	public void createUserGroup(UserGroup group) {
		server.createUserGroup(group);
	}

	public String createRole(Role role) {
		return server.createRole(role);
	}

	public void removeUser(User user) {
		server.removeUser(user);
	}

	public void removeMenuItem(MenuItem menuItem) throws UIException {
		server.removeMenuItem(menuItem);
	}

	public void removeUserGroup(UserGroup group) throws UIException {
		server.removeUserGroup(group);
	}

	public void removeRole(Role role) throws UIException {
		server.removeRole(role);
	}

	public void updateMenuItem(MenuItem menuItem) {
		server.updateMenuItem(menuItem);
	}

	@Override
	public String createMenuItem(MenuItemTO menuItem) {
		return server.createMenuItem(menuItem);
	}

	@Override
	public String createRole(RoleTO role) {
		return server.createRole(role);
	}

	@Override
	public String createUserGroup(UserGroupTO group) {
		return server.createUserGroup(group);
	}

	@Override
	public String createUser(UserTO user) {
		return server.createUser(user);
	}

	@Override
	public void simpleUpdateMenuItems(List<MenuItemTO> menuItems) {
		server.simpleUpdateMenuItems(menuItems);
	}

	@Override
	public void simpleUpdateRoles(List<RoleTO> roles) {
		server.simpleUpdateRoles(roles);
	}

	@Override
	public void simpleUpdateUserGroups(List<UserGroupTO> userGroups) {
		server.simpleUpdateUserGroups(userGroups);
	}

	@Override
	public void simpleUpdateUser(UserTO user) {
		server.simpleUpdateUser(user);
	}

	@Override
	public List<MenuItemTO> findAllMenuItem() {
		return server.findAllMenuItem();
	}

	@Override
	public List<RoleTO> findAllRole() {
		return server.findAllRole();
	}

	@Override
	public List<UserGroupTO> findAllUserGroup() {
		return server.findAllUserGroup();
	}

	@Override
	public List<UserTO> findAllUser() {
		return server.findAllUser();
	}

	@Override
	public void assignUserGroupToUser(String userId, List<String> groupIds) {
		server.assignUserGroupToUser(userId, groupIds);
	}

	@Override
	public void removeUserGroupToUser(String userId, List<String> groupIds) {
		server.removeUserGroupToUser(userId, groupIds);
	}

	@Override
	public void assignRoleToUser(String userId, List<String> roleIds) {
		server.assignRoleToUser(userId, roleIds);
	}

	@Override
	public void removeRoleToUser(String userId, List<String> roleIds) {
		server.removeRoleToUser(userId, roleIds);
	}

	@Override
	public void assignRoleToUserGroup(String groupId, List<String> roleIds) {
		server.assignRoleToUserGroup(groupId, roleIds);

	}

	@Override
	public void removeRoleToUserGroup(String groupId, List<String> roleIds) {
		server.removeRoleToUserGroup(groupId, roleIds);
	}

	@Override
	public void assignMenuItemToRole(String roleId, List<String> menuItemIds) {
		server.assignMenuItemToRole(roleId, menuItemIds);
	}

	@Override
	public void removeMenuItemToRole(String roleId, List<String> menuItemIds) {
		server.removeMenuItemToRole(roleId, menuItemIds);

	}

	@Override
	public MenuItemTO findMenuItemById(String id) {
		return server.findMenuItemById(id);
	}

	@Override
	public void removeMenuItems(List<String> menuItems) {
		server.removeMenuItems(menuItems);
	}

}
