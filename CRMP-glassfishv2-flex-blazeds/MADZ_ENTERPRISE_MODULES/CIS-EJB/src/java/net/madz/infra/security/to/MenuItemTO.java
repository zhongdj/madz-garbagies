package net.madz.infra.security.to;

import net.madz.infra.security.persistence.MenuItem;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.Binding;

public class MenuItemTO extends StandardTO<MenuItem> {

	private static final long serialVersionUID = -8427658045183621972L;

	private String name;
	private String icon;
	private String viewId;
	@Binding(name = "parent.id")
	private String parentMenuItemId;
	@Binding(name = "parent.name")
	private String parentMenuItemName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getParentMenuItemId() {
		return parentMenuItemId;
	}

	public void setParentMenuItemId(String parentMenuItemId) {
		this.parentMenuItemId = parentMenuItemId;
	}

	public String getParentMenuItemName() {
		return parentMenuItemName;
	}

	public void setParentMenuItemName(String parentMenuItemName) {
		this.parentMenuItemName = parentMenuItemName;
	}

	@Override
	public String toString() {
		return "MenuItemTO [name=" + name + ", icon=" + icon + ", viewId=" + viewId + ", parentMenuItemId=" + parentMenuItemId
				+ ", parentMenuItemName=" + parentMenuItemName + "]";
	}

}
