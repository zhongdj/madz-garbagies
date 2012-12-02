/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.madz.infra.biz.core.BizBean;
import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.persistence.MenuItem;
import net.madz.infra.security.persistence.Role;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.persistence.UserGroup;
import net.madz.infra.security.to.MenuItemTO;
import net.madz.infra.security.to.RoleTO;
import net.madz.infra.security.to.UserGroupTO;
import net.madz.infra.security.to.UserTO;
import net.madz.infra.security.util.TenantResources;
import net.madz.standard.StandardObjectFactory;
import net.vicp.madz.infra.binding.TransferObjectFactory;
import net.vicp.madz.security.login.exception.PasswordException;
import net.vicp.madz.security.login.factory.EncryptorFactory;
import net.vicp.madz.security.login.impl.AuditPolicyAdvisor;
import net.vicp.madz.security.login.impl.PrincipalAuditor;
import net.vicp.madz.security.login.interfaces.IAuditPolicy;
import net.vicp.madz.security.login.interfaces.IPasswordEncryptor;
import net.vicp.madz.security.login.interfaces.IPrincipalAuditor;

/**
 * 
 * @author CleaNEr
 */
@Stateless
public class UIFacadeBean extends BizBean implements UIFacadeRemote {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	@RolesAllowed({ "SA" })
	public void createUser(User user) {
		if (user != null && user.getUsername() != null && user.getUsername().trim().length() > 0) {
			em.persist(user);
		}
	}

	@RolesAllowed({ "SA" })
	public String createMenuItem(MenuItem menuItem) {
		if (menuItem != null && menuItem.getName() != null) {
			final MenuItem result = TenantResources.newEntity(MenuItem.class);
			result.setIcon(menuItem.getIcon());
			result.setName(menuItem.getName());
			if (null != menuItem.getParent() && null != menuItem.getParent().getId()) {
			    result.setParent(em.find(MenuItem.class, menuItem.getParent().getId()));
			}
			result.setViewId(menuItem.getViewId());
			em.persist(result);
			return result.getId();
		} else {
			throw new IllegalStateException();
		}

	}

	@RolesAllowed({ "SA" })
	public void createUserGroup(UserGroup group) {
		if (group != null) {
			em.persist(group);
		}
	}

	@RolesAllowed({ "SA" })
	public String createRole(Role role) {
		if (role != null && role.getName() != null && role.getName().trim().length() > 0) {
			final Role result = TenantResources.newEntity(Role.class);
			result.setName(role.getName());
			em.persist(result);
			return result.getId();
		} else {
			throw new IllegalStateException();
		}
	}

	@RolesAllowed({ "SA" })
	public void removeUser(User user) {
		if (user != null) {
			user = em.find(User.class, user.getUsername());
			if (user == null) {
				return;
			}
			user.removeAllRoles();
			user.removeAllUserGroup();
			em.remove(em.merge(user));
		}
	}

	@RolesAllowed({ "SA" })
	public void removeMenuItem(MenuItem menuItem) throws UIException {
		if (menuItem != null && menuItem.getId() != null && menuItem.getId().length() > 0) {
			menuItem = em.find(MenuItem.class, menuItem.getId());
			if (menuItem == null) {
				return;
			}
			if (menuItem.getChildren() != null && menuItem.getChildren().size() > 0) {
				throw new UIException("Cannot remove menuItem:" + menuItem.getName() + ", it still has sub menu items");
			}
			if (menuItem.getRoles() != null && menuItem.getRoles().size() > 0) {
				throw new UIException("Cannot remove menuItem:" + menuItem.getName()
						+ ", this menu items is still being used by some roles");
			}
			menuItem.getParent().removeSubMenuItem(menuItem);
			em.remove(em.merge(menuItem));
		}
	}

	@RolesAllowed({ "SA" })
	public void removeUserGroup(UserGroup group) throws UIException {
		if (group != null && group.getName() != null && group.getName().trim().length() > 0) {
			group = em.find(UserGroup.class, group.getName());
			if (group == null) {
				return;
			}
			if (group.getUsers() != null && group.getUsers().size() > 0) {
				throw new UIException("Cannot remove group:" + group.getName() + ", this group is still being used by some users");
			}

			group.removeAllRoles();
			em.remove(em.merge(group));
		}
	}

	@RolesAllowed({ "SA" })
	public void removeRole(Role role) throws UIException {
		if (role != null && role.getId() != null && role.getId().length() > 0) {
			role = em.find(Role.class, role.getId());
			if (role.getGroups() != null && role.getGroups().size() > 0) {
				throw new UIException("Cannot remove role:" + role.getName() + ", this role is still being used by some groups");
			}

			if (role.getUsers() != null && role.getUsers().size() > 0) {
				throw new UIException("Cannot remove role:" + role.getName() + ", this role is still being used by some users");
			}
			if (role.getMenuItems() != null && role.getMenuItems().size() > 0) {
				role.removeAllMenuItems();
			}
			em.remove(em.merge(role));
		}
	}

	@RolesAllowed({ "SA" })
	public void updateMenuItem(MenuItem menuItem) {
		MenuItem menuItemSrc = em.find(MenuItem.class, menuItem.getId());
		if (menuItemSrc != null) {
			menuItemSrc.setIcon(menuItem.getIcon());
			menuItemSrc.setName(menuItem.getName());
		}
		em.merge(menuItemSrc);
	}

	@RolesAllowed({ "SA" })
	public void assignUserGroupToUser(String userId, List<String> groupIds) {
		if (null == userId) {
			throw new IllegalArgumentException("userId cannot be NULL.");
		}
		if (null == groupIds || 0 >= groupIds.size()) {
			throw new IllegalArgumentException("groupIds cannot be NULL or empty.");
		}

		final User user = em.find(User.class, userId);
		if (null == user) {
			throw new IllegalArgumentException("Cannot find any corresponding user with userId = " + userId);
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(user.getTenant().getId())) {
			throw new IllegalStateException("groupId is not in the same tenant context.");
		}

		final List<UserGroup> groups = findUserGroups(groupIds);

		for (UserGroup group : groups) {
			user.addUserGroup(group);
		}
		em.merge(user);
	}

	@RolesAllowed({ "SA" })
	public void removeUserGroupToUser(String userId, List<String> groupIds) {
		if (null == userId) {
			throw new IllegalArgumentException("userId cannot be NULL.");
		}
		if (null == groupIds || 0 >= groupIds.size()) {
			throw new IllegalArgumentException("groupIds cannot be NULL or empty.");
		}

		final User user = em.find(User.class, userId);
		if (null == user) {
			throw new IllegalArgumentException("Cannot find any corresponding user with userId = " + userId);
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(user.getTenant().getId())) {
			throw new IllegalStateException("groupId is not in the same tenant context.");
		}

		final List<UserGroup> groups = findUserGroups(groupIds);

		for (UserGroup group : groups) {
			user.removeUserGroup(group);
		}
		em.merge(user);
	}

	@RolesAllowed({ "SA" })
	public void assignRoleToUser(String userId, List<String> roleIds) {
		if (null == userId) {
			throw new IllegalArgumentException("groupId cannot be NULL.");
		}

		if (null == roleIds || 0 >= roleIds.size()) {
			throw new IllegalArgumentException("roleIds cannot be NULL or empty.");
		}

		final User user = em.find(User.class, userId);
		if (null == user) {
			throw new IllegalArgumentException("Cannot find any corresponding user with userId = " + userId);
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(user.getTenant().getId())) {
			throw new IllegalStateException("groupId is not in the same tenant context.");
		}

		final List<Role> roles = findRoles(roleIds);
		for (Role role : roles) {
			user.addRole(role);
		}
		em.merge(user);
	}

	@RolesAllowed({ "SA" })
	public void removeRoleToUser(String userId, List<String> roleIds) {
		if (null == userId) {
			throw new IllegalArgumentException("groupId cannot be NULL.");
		}

		if (null == roleIds || 0 >= roleIds.size()) {
			throw new IllegalArgumentException("roleIds cannot be NULL or empty.");
		}

		final User user = em.find(User.class, userId);
		if (null == user) {
			throw new IllegalArgumentException("Cannot find any corresponding user with userId = " + userId);
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(user.getTenant().getId())) {
			throw new IllegalStateException("groupId is not in the same tenant context.");
		}

		final List<Role> roles = findRoles(roleIds);
		for (Role role : roles) {
			user.removeRole(role);
		}
		em.merge(user);
	}

	@RolesAllowed({ "SA" })
	public void assignRoleToUserGroup(String groupId, List<String> roleIds) {
		if (null == groupId) {
			throw new IllegalArgumentException("groupId cannot be NULL.");
		}

		if (null == roleIds || 0 >= roleIds.size()) {
			throw new IllegalArgumentException("roleIds cannot be NULL or empty.");
		}

		final UserGroup group = em.find(UserGroup.class, groupId);
		if (null == group) {
			throw new IllegalArgumentException("Cannot find any corresponding UserGroup with Argument groupId");
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(group.getTenant().getId())) {
			throw new IllegalStateException("groupId is not in the same tenant context.");
		}

		final List<Role> roles = findRoles(roleIds);

		for (Role role : roles) {
			group.addRole(role);
			em.merge(role);
		}
		em.merge(group);
	}

	@RolesAllowed({ "SA" })
	public void removeRoleToUserGroup(String groupId, List<String> roleIds) {

		if (null == groupId) {
			throw new IllegalArgumentException("groupId cannot be NULL.");
		}

		if (null == roleIds || 0 >= roleIds.size()) {
			throw new IllegalArgumentException("roleIds cannot be NULL or empty.");
		}

		final UserGroup group = em.find(UserGroup.class, groupId);
		if (null == group) {
			throw new IllegalArgumentException("Cannot find any corresponding UserGroup with Argument groupId");
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(group.getTenant().getId())) {
			throw new IllegalStateException("groupId is not in the same tenant context.");
		}

		final List<Role> roles = findRoles(roleIds);

		for (Role role : roles) {
			group.removeRole(role);
			em.merge(role);
		}
		em.merge(group);

	}

	public void assignMenuItemToRole(String roleId, List<String> menuItemIds) {
		if (null == roleId) {
			throw new IllegalArgumentException("roleId cannot be NULL.");
		}

		if (null == menuItemIds || 0 >= menuItemIds.size()) {
			throw new IllegalArgumentException("roleIds cannot be NULL or empty.");
		}

		final Role role = em.find(Role.class, roleId);
		if (null == role) {
			throw new IllegalArgumentException("Cannot find any corresponding Role with Argument roleId");
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(role.getTenant().getId())) {
			throw new IllegalStateException("roleId is not in the same tenant context.");
		}

		final List<MenuItem> menuItems = findMenuItems(menuItemIds);

		for (MenuItem menuItem : menuItems) {
			role.addMenuItem(menuItem);
		}
		em.merge(role);
	}

	public void removeMenuItemToRole(String roleId, List<String> menuItemIds) {

		if (null == roleId) {
			throw new IllegalArgumentException("roleId cannot be NULL.");
		}

		if (null == menuItemIds || 0 >= menuItemIds.size()) {
			throw new IllegalArgumentException("roleIds cannot be NULL or empty.");
		}

		final Role role = em.find(Role.class, roleId);
		if (null == role) {
			throw new IllegalArgumentException("Cannot find any corresponding Role with Argument roleId");
		}

		final String tenantId = TenantResources.getTenant().getId();

		if (!tenantId.equals(role.getTenant().getId())) {
			throw new IllegalStateException("roleId is not in the same tenant context.");
		}

		final List<MenuItem> menuItems = findMenuItems(menuItemIds);

		for (MenuItem menuItem : menuItems) {
			role.removeMenuItem(menuItem);
		}
		em.merge(role);

	}

	@SuppressWarnings("unchecked")
	private List<UserGroup> findUserGroups(List<String> groupIds) {
		final StringBuilder idSb = new StringBuilder();
		idSb.append("(");
		for (int i = 0; i < groupIds.size(); i++) {
			idSb.append("o.id = '").append(groupIds.get(i)).append("'");
			if (i < groupIds.size() - 1) {
				idSb.append(" OR ");
			}
		}

		idSb.append(")");

		final String tenantId = TenantResources.getTenant().getId();

		final Query userQuery = em.createQuery("SELECT o FROM UserGroup o WHERE o.tenant.id = :tenantId  AND " + idSb.toString())
				.setParameter("tenantId", tenantId);
		final List<UserGroup> groups = userQuery.getResultList();

		if (null == groups || 0 >= groups.size()) {
			throw new IllegalArgumentException("Cannot find any corresponding UserGroup with Argument groupIds");
		}
		return groups;
	}

	@SuppressWarnings("unchecked")
	private List<Role> findRoles(List<String> roleIds) {
		final StringBuilder idSb = new StringBuilder();
		idSb.append("(");
		for (int i = 0; i < roleIds.size(); i++) {
			idSb.append("o.id = '").append(roleIds.get(i)).append("'");
			if (i < roleIds.size() - 1) {
				idSb.append(" OR ");
			}
		}

		idSb.append(")");

		final String tenantId = TenantResources.getTenant().getId();
		final Query roleQuery = em.createQuery("SELECT o FROM Role o WHERE o.tenant.id = :tenantId AND " + idSb.toString()).setParameter(
				"tenantId", tenantId);
		final List<Role> roles = roleQuery.getResultList();

		if (null == roles || 0 >= roles.size()) {
			throw new IllegalArgumentException("Cannot find any corresponding Role with Argument roleIds");
		}
		return roles;
	}

	@SuppressWarnings("unchecked")
	private List<MenuItem> findMenuItems(List<String> menuItemIds) {
		final StringBuilder idSb = new StringBuilder();
		idSb.append("(");
		for (int i = 0; i < menuItemIds.size(); i++) {
			idSb.append("o.id = '").append(menuItemIds.get(i)).append("'");
			if (i < menuItemIds.size() - 1) {
				idSb.append(" OR ");
			}
		}

		idSb.append(")");
		final String tenantId = TenantResources.getTenant().getId();
		final Query menuItemQuery = em.createQuery("SELECT o FROM MenuItem o WHERE o.tenant.id = :tenantId AND " + idSb.toString())
				.setParameter("tenantId", tenantId);
		final List<MenuItem> menuItems = menuItemQuery.getResultList();

		if (null == menuItems || 0 >= menuItems.size()) {
			throw new IllegalArgumentException("Cannot find any corresponding MenuItem with Argument menuItemIds");
		}
		return menuItems;
	}

	@Override
	public String createMenuItem(MenuItemTO menuItem) {
		try {
			final MenuItem item = StandardObjectFactory.convertTO2Entity(MenuItem.class, menuItem, em);
			em.persist(item);
			return item.getId();
		} catch (Exception e) {
            throw new BusinessException(e);
		}
	}

	@Override
	public String createRole(RoleTO roleTO) {
		final Role role = new Role();
		role.setName(roleTO.getName());
		em.persist(role);
		return role.getId();
	}

	@Override
	public String createUserGroup(UserGroupTO groupTO) {
		final UserGroup group = new UserGroup();
		group.setName(groupTO.getName());
		em.persist(group);
		return group.getId();
	}

	@Override
	public String createUser(UserTO userTO) {
		final User user = new User();
		if (null == userTO.getEmail() || 0 >= userTO.getEmail().trim().length()) {
			throw new IllegalArgumentException("email cannot be empty");
		}
		if (null == userTO.getFullName() || 0 >= userTO.getFullName().trim().length()) {
			throw new IllegalArgumentException("Full name cannot be empty");
		}
		if (null == userTO.getUsername() || 0 >= userTO.getUsername().trim().length()) {
			throw new IllegalArgumentException("User name cannot be empty");
		}
		if (null == userTO.getPassword() || 0 >= userTO.getPassword().trim().length()) {
			throw new IllegalArgumentException("Full name cannot be empty");
		}

		user.setEmail(userTO.getEmail());
		user.setFullName(userTO.getFullName());
		user.setNeedResetPwd(true);
		user.setUsername(userTO.getUsername());
		final IPasswordEncryptor passwordEncryptor = EncryptorFactory.getInstance().getPasswordEncryptor();
		// FIXME Using abstract instead.
		final IAuditPolicy auditPolicy = AuditPolicyAdvisor.getInstance();
		final IPrincipalAuditor principalAuditor = PrincipalAuditor.getInstance(passwordEncryptor, auditPolicy);

		try {
			principalAuditor.auditPassword(passwordEncryptor.encrypt(userTO.getPassword()));
		} catch (PasswordException e) {
			throw new BusinessException(e);
		}

		user.setPassword(passwordEncryptor.encrypt(userTO.getPassword()));
		return user.getId();
	}

	@Override
	public void simpleUpdateMenuItems(List<MenuItemTO> menuItems) {
		if (null == menuItems || 0 >= menuItems.size()) {
			throw new IllegalArgumentException("menuItems cannot be NULL or empty.");
		}

		final String tenantId = TenantResources.getTenant().getId();
		MenuItem menuItem = null;
		for (MenuItemTO menuItemTO : menuItems) {
			menuItem = em.find(MenuItem.class, menuItemTO.getId());
			if (!tenantId.equals(menuItem.getTenant().getId())) {
				throw new IllegalArgumentException("menuItem is not in the tenant context.");
			}
			menuItem.setName(menuItemTO.getName());
			menuItem.setViewId(menuItemTO.getViewId());
			menuItem.setIcon(menuItemTO.getIcon());
			if (null != menuItemTO.getParentMenuItemId()) {
				final MenuItem parent = em.find(MenuItem.class, menuItemTO.getParentMenuItemId());

				if (!tenantId.equals(parent.getTenant().getId())) {
					throw new IllegalArgumentException("parentMenuItemId is not in the same tenant context.");
				}
				menuItem.setParent(parent);
			}
			em.merge(menuItem);
		}
	}

	@Override
	public void simpleUpdateRoles(List<RoleTO> roles) {
		if (null == roles || 0 >= roles.size()) {
			throw new IllegalArgumentException("roles cannot be NULL or empty.");
		}

		final String tenantId = TenantResources.getTenant().getId();
		Role role = null;
		for (RoleTO roleTO : roles) {
			role = em.find(Role.class, roleTO.getId());
			if (!tenantId.equals(role.getTenant().getId())) {
				throw new IllegalArgumentException("role is not in the tenant context.");
			}
			role.setName(roleTO.getName());
			em.merge(role);
		}
	}

	@Override
	public void simpleUpdateUserGroups(List<UserGroupTO> userGroups) {
		if (null == userGroups || 0 >= userGroups.size()) {
			throw new IllegalArgumentException("userGroups cannot be NULL or empty.");
		}

		final String tenantId = TenantResources.getTenant().getId();
		UserGroup group = null;
		for (UserGroupTO groupTO : userGroups) {
			group = em.find(UserGroup.class, groupTO.getId());
			if (!tenantId.equals(group.getTenant().getId())) {
				throw new IllegalArgumentException("userGroup is not in the tenant context.");
			}
			group.setName(groupTO.getName());
			em.merge(group);
		}
	}

	@Override
	public void simpleUpdateUser(UserTO userTO) {
		if (null == userTO.getEmail() || 0 >= userTO.getEmail().trim().length()) {
			throw new IllegalArgumentException("email cannot be empty");
		}
		if (null == userTO.getFullName() || 0 >= userTO.getFullName().trim().length()) {
			throw new IllegalArgumentException("Full name cannot be empty");
		}
		if (null == userTO.getUsername() || 0 >= userTO.getUsername().trim().length()) {
			throw new IllegalArgumentException("User name cannot be empty");
		}
		if (null == userTO.getPassword() || 0 >= userTO.getPassword().trim().length()) {
			throw new IllegalArgumentException("Full name cannot be empty");
		}

		final User user = em.find(User.class, userTO.getId());
		if (null == user) {
			throw new IllegalArgumentException("Cannot find any corresponding user with userId = " + userTO.getId());
		}
		// TODO validate email format
		user.setEmail(userTO.getEmail());
		user.setFullName(userTO.getFullName());
		user.setNeedResetPwd(true);
		// TODO validate username format
		user.setUsername(userTO.getUsername());
		final IPasswordEncryptor passwordEncryptor = EncryptorFactory.getInstance().getPasswordEncryptor();
		// FIXME Using abstract instead.
		final IAuditPolicy auditPolicy = AuditPolicyAdvisor.getInstance();
		final IPrincipalAuditor principalAuditor = PrincipalAuditor.getInstance(passwordEncryptor, auditPolicy);

		try {
			principalAuditor.auditPassword(passwordEncryptor.encrypt(userTO.getPassword()));
		} catch (PasswordException e) {
			throw new BusinessException(e);
		}

		user.setPassword(passwordEncryptor.encrypt(userTO.getPassword()));
		em.merge(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItemTO> findAllMenuItem() {

		final String tenantId = TenantResources.getTenant().getId();
		final Query menuItemQuery = em.createQuery("SELECT o FROM MenuItem o WHERE o.tenant.id = :tenantId").setParameter("tenantId",
				tenantId);
		final List<MenuItem> menuItems = menuItemQuery.getResultList();
		try {
			return TransferObjectFactory.assembleTransferObjectList(menuItems, MenuItemTO.class);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleTO> findAllRole() {
		final String tenantId = TenantResources.getTenant().getId();
		final Query roleQuery = em.createQuery("SELECT o FROM Role o WHERE o.tenant.id = :tenantId").setParameter("tenantId", tenantId);
		final List<Role> roles = roleQuery.getResultList();
		try {
			return TransferObjectFactory.assembleTransferObjectList(roles, RoleTO.class);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserGroupTO> findAllUserGroup() {
		final String tenantId = TenantResources.getTenant().getId();
		final Query groupQuery = em.createQuery("SELECT o FROM UserGroup o WHERE o.tenant.id = :tenantId").setParameter("tenantId",
				tenantId);
		final List<UserGroup> groups = groupQuery.getResultList();
		try {
			return TransferObjectFactory.assembleTransferObjectList(groups, UserGroupTO.class);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTO> findAllUser() {
		final String tenantId = TenantResources.getTenant().getId();
		final Query userQuery = em.createQuery("SELECT o FROM User o WHERE o.tenant.id = :tenantId").setParameter("tenantId", tenantId);
		final List<User> users = userQuery.getResultList();
		try {
			return TransferObjectFactory.assembleTransferObjectList(users, UserTO.class);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			throw new BusinessException(e);
		}
	}

	public MenuItemTO findMenuItemById(String id) {
		final MenuItem result = em.find(MenuItem.class, id);
		final String tenantId = TenantResources.getTenant().getId();
		if (null == result) {
			return null;
		}
		if (!tenantId.equals(result.getTenant().getId())) {
			throw new BusinessException("MenuItem Id : " + id + " is not in the tenant context.");
		}
		try {
			return TransferObjectFactory.createTransferObject(MenuItemTO.class, result);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			throw new BusinessException(e);
		}
	}

	@Override
	public void removeMenuItems(List<String> menuItems) {
		final String tenantId = TenantResources.getTenant().getId();
		MenuItem item = null;
		for (String id : menuItems) {
			item = em.find(MenuItem.class, id);
			if (null == item) {
				continue;
			}
			if (!tenantId.equals(item.getTenant().getId())) {
				throw new BusinessException("MenuItem Id : " + id + " is not in the tenant context.");
			}
			em.remove(item);
		}
	}
}
