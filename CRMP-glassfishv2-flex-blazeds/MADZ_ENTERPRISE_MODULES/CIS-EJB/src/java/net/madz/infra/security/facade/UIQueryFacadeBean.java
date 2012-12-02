/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.madz.infra.security.persistence.MenuItem;
import net.madz.infra.security.persistence.Role;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.persistence.UserGroup;

/**
 * 
 * @author CleaNEr
 */
@Stateless
public class UIQueryFacadeBean implements UIQueryFacadeRemote {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;

	private String getPrincipalName() throws NamingException {
		return ctx.getCallerPrincipal().getName();
	}

	@RolesAllowed(value = { "ADMIN", "OP" })
	public List<MenuItem> findMenuItems() throws NamingException {
		try {
			List<MenuItem> result = new LinkedList<MenuItem>();
			List<Role> roles = new LinkedList<Role>();
			String userName = getPrincipalName();
			Query query = em.createNamedQuery("User.findByUsername");
			query.setParameter("username", userName);
			User user = (User) query.getSingleResult();
			if (user == null) {
				return result;
			}
			if (user.getGroups() != null && user.getGroups().size() > 0) {
				for (UserGroup group : user.getGroups()) {
					if (group.getRoles() != null && group.getRoles().size() > 0) {
						for (Role role : group.getRoles()) {
							if (!roles.contains(role)) {
								roles.add(role);
							}
						}
					}
				}
			}

			if (user.getRoles() != null && user.getRoles().size() > 0) {
				for (Role role : user.getRoles()) {
					if (!roles.contains(role)) {
						roles.add(role);
					}
				}
			}

			if (roles.size() > 0) {
				for (Role role : roles) {
					if (role.getMenuItems() != null && role.getMenuItems().size() > 0) {
						for (MenuItem item : role.getMenuItems()) {
							if (!result.contains(item)) {
								if (item.getParent() == null) {
									result.add(item);
								}
							}
						}
					}
				}
			}
			return result;
		} catch (NamingException ex) {
			Logger.getLogger(UIQueryFacadeBean.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	/**
	 * 
	 * <node id="1" label="Application" data="0" icon="application"/> <node
	 * id="2" label="Buttons" data="-1"> <node id="3" label="Button" data="3"
	 * icon="button"/> <node id="4" label="ButtonBar" data="4"
	 * icon="buttonBar"/> <node id="5" label="LinkBar" data="14"
	 * icon="linkBar"/> <node id="6" label="LinkButton" data="15"
	 * icon="linkButton"/> <node id="7" label="PopUpButton" data="20"
	 * icon="button"/> <node id="8" label="ToggleButtonBar" data="26"
	 * icon="toggleButtonBar"/> </node>
	 * 
	 * @return
	 * @throws javax.naming.NamingException
	 */
	@RolesAllowed(value = { "ADMIN", "OP" })
	public String getMenuXMLDescription() throws NamingException {
		List<MenuItem> menuItems = findMenuItems();
		StringBuffer result = new StringBuffer();
		if (menuItems != null && menuItems.size() > 0) {
			for (MenuItem item : menuItems) {
				result.append(transferMenuItem(item));
			}
		}
		return result.toString();
	}

	private StringBuffer transferMenuItem(MenuItem item) {
		StringBuffer result = new StringBuffer();
		if (item != null) {
			result.append("<node ");
			result.append("id=\"" + item.getId() + "\" ");
			result.append("label=\"" + item.getName() + "\" ");

			if (item.getViewId() != null && item.getViewId().trim().length() > 0) {
				result.append("data=\"" + item.getViewId().trim() + "\" ");
			}

			if (item.getIcon() != null && item.getIcon().trim().length() > 0) {
				result.append("icon=\"" + item.getIcon().trim() + "\" ");
			}

			if (item.getChildren().size() > 0) {
				result.append(">\n");
				for (MenuItem child : item.getChildren()) {
					result.append(transferMenuItem(child));
					result.append("\n");
				}
				result.append("</node>\n");
			} else {
				result.append("/>\n");
			}
		}
		System.out.println(result);
		return result;
	}
}
