/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.persistence;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author CleaNEr
 */
@Entity
@Table(name = "CRMP_Role")
public class Role extends StandardObject {

	private static final long serialVersionUID = -8677837722180931934L;
	private String name;
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "CRMP_ROLE_MENU_ITEM", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "MENU_ITEM_ID", referencedColumnName = "ID"))
	private List<MenuItem> menuItems;
	@ManyToMany(mappedBy = "roles")
	private List<User> users = new LinkedList<User>();
	@ManyToMany(mappedBy = "roles")
	private List<UserGroup> groups = new LinkedList<UserGroup>();

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void addMenuItem(MenuItem menuItem) {
		if (menuItem != null && menuItem.getId() != null && menuItem.getId().length() > 0) {
			if (menuItems.contains(menuItem)) {
				return;
			} else {
				menuItems.add(menuItem);
				menuItem.addRole(this);
			}
		}
	}

	public void removeMenuItem(MenuItem menuItem) {
		if (menuItem != null && menuItem.getId() != null && menuItem.getId().length() > 0) {
			if (menuItems.contains(menuItem)) {
				menuItems.remove(menuItem);
				menuItem.removeRole(this);
			} else {
				return;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroup> groups) {
		this.groups = groups;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	void addUserGroup(UserGroup group) {
		if (group == null || group.getName() == null || group.getName().trim().length() <= 0) {
			return;
		}
		if (!groups.contains(group)) {
			groups.add(group);
		}
	}

	void removeUserGroup(UserGroup group) {
		if (group == null || group.getName() == null || group.getName().trim().length() <= 0) {
			return;
		}
		if (groups.contains(group)) {
			groups.remove(group);
		}
	}

	void addUser(User user) {
		if (user == null || user.getUsername() == null || user.getUsername().trim().length() <= 0) {
			return;
		}
		if (!users.contains(user)) {
			users.add(user);
		}
	}

	void removeUser(User user) {
		if (user == null || user.getUsername() == null || user.getUsername().trim().length() <= 0) {
			return;
		}
		if (users.contains(user)) {
			users.remove(user);
		}
	}

	public void removeAllMenuItems() {
		if (menuItems != null && menuItems.size() > 0) {
			for (MenuItem item : menuItems) {
				removeMenuItem(item);
			}
		}
	}

	@Override
	public String toString() {
		StringBuffer menuStr = new StringBuffer();
		if (menuItems != null && menuItems.size() > 0) {
			int count = 0;
			for (MenuItem menuItem : menuItems) {
				menuStr.append(menuItem.toString());
				if (count < menuItems.size() - 1) {
					menuStr.append(", ");
				}
				count++;
			}
		}
		return getClass().getName() + ":[id=" + getId() + ", name = " + name + ", menuItems = " + menuStr + " ]";
	}
}
