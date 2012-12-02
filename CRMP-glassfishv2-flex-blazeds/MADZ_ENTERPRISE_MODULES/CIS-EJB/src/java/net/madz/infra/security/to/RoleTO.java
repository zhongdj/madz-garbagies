package net.madz.infra.security.to;

import java.util.List;

import net.madz.infra.security.persistence.Role;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class RoleTO extends StandardTO<Role> {

	private static final long serialVersionUID = -4831745203476424308L;
	private String name;
	@Binding(name = "menuItems", accessType = AccessTypeEnum.Field, bindingType = BindingTypeEnum.Entity, embeddedType = MenuItemTO.class)
	private List<MenuItemTO> menuItems;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuItemTO> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItemTO> menuItems) {
		this.menuItems = menuItems;
	}

	@Override
	public String toString() {
		return "RoleTO [name=" + name + ", menuItems=" + menuItems + "]";
	}

}
