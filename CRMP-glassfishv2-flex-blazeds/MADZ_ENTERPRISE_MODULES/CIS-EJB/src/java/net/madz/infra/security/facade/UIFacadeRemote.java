package net.madz.infra.security.facade;

import java.util.List;

import javax.ejb.Remote;

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
@Remote
public interface UIFacadeRemote {

	void createUser(User user);

	String createMenuItem(MenuItem menuItem);

	void createUserGroup(UserGroup group);

	String createRole(Role role);

	void removeUser(User user);

	void removeMenuItem(MenuItem menuItem) throws UIException;

	void removeUserGroup(UserGroup group) throws UIException;

	void removeRole(Role role) throws UIException;

	void updateMenuItem(MenuItem menuItem);

	// TO CRUD
	String createMenuItem(MenuItemTO menuItem);

	String createRole(RoleTO role);

	String createUserGroup(UserGroupTO group);

	String createUser(UserTO user);

	void simpleUpdateMenuItems(List<MenuItemTO> menuItems);

	void simpleUpdateRoles(List<RoleTO> roles);

	void simpleUpdateUserGroups(List<UserGroupTO> userGroups);

	void simpleUpdateUser(UserTO user);

	List<MenuItemTO> findAllMenuItem();

	List<RoleTO> findAllRole();

	List<UserGroupTO> findAllUserGroup();

	List<UserTO> findAllUser();

	// Should be modified

	void assignUserGroupToUser(String userId, List<String> groupIds);

	void removeUserGroupToUser(String userId, List<String> groupIds);

	void assignRoleToUser(String userId, List<String> roleIds);

	void removeRoleToUser(String userId, List<String> roleIds);

	void assignRoleToUserGroup(String groupId, List<String> roleIds);

	void removeRoleToUserGroup(String groupId, List<String> roleIds);

	void assignMenuItemToRole(String roleId, List<String> menuItemIds);

	void removeMenuItemToRole(String roleId, List<String> menuItemIds);

	MenuItemTO findMenuItemById(String id);

	void removeMenuItems(List<String> menuItems);
}
