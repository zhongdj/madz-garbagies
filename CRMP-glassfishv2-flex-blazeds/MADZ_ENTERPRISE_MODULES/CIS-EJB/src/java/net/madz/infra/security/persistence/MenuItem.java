/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.persistence;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author CleaNEr
 */
@Entity
@Table(name = "CRMP_Menu_Item")
public class MenuItem extends StandardObject {

	private static final long serialVersionUID = -8602363199452540590L;
	private String name;
	private String icon;
	private String viewId;
	@ManyToOne
	private MenuItem parent;
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<MenuItem> children = new LinkedList<MenuItem>();
	@ManyToMany(mappedBy = "menuItems")
	private List<Role> roles = new LinkedList<Role>();

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public MenuItem getParent() {
		return parent;
	}

	public String getViewId() {
		return viewId;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(MenuItem parent) {
		this.parent = parent;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public void addSubMenuItem(MenuItem item) {
		if (item == null || item.getId() == null || item.getId().length() <= 0) {
			return;
		}
		children.add(item);
		if (item.parent != null && item.parent.getId() != null && item.parent.getId().length() <= 0) {
			item.parent.children.remove(item);
		}
		item.setParent(parent);
		parent.children.add(item);
	}

	public void removeSubMenuItem(MenuItem item) {
		if (item == null || item.getId() == null || item.getId().length() <= 0) {
			return;
		}
		if (children.contains(item)) {
			item.setParent(null);
			children.remove(item);
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	void addRole(Role role) {
		if (role == null || role.getId() == null || role.getId().length() <= 0) {
			return;
		}
		if (!roles.contains(role)) {
			roles.add(role);
		}
	}

	void removeRole(Role role) {
		if (role == null || role.getId() == null || role.getId().length() <= 0) {
			return;
		}
		if (roles.contains(role)) {
			roles.remove(role);
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + ":[ id = " + getId() + ", name = " + name + ", icon = " + icon + ", viewId = " + viewId + ", parent "
				+ parent + " ]";
	}
}
