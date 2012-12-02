package net.madz.infra.security.to;

import java.util.List;

import net.madz.infra.security.persistence.UserGroup;
import net.madz.standard.StandardTO;
import net.vicp.madz.infra.binding.annotation.AccessTypeEnum;
import net.vicp.madz.infra.binding.annotation.Binding;
import net.vicp.madz.infra.binding.annotation.BindingTypeEnum;

public class UserGroupTO extends StandardTO<UserGroup> {

	private static final long serialVersionUID = 4334626951175575103L;
	private String name;
	@Binding(name = "roles", accessType = AccessTypeEnum.Field, bindingType = BindingTypeEnum.Entity, embeddedType = RoleTO.class)
	private List<RoleTO> roleList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RoleTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleTO> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return "UserGroupTO [name=" + name + ", roleList=" + roleList + "]";
	}

}
