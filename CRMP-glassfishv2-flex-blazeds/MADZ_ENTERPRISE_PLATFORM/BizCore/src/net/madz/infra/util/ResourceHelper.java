/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.util;

import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * 
 * @author CleaNEr
 */
public class ResourceHelper {

	private static final String ENTITY_MANAGER = "java:comp/env/persistence/EntityManager";
	private static final String SESSION_CONTEXT = "java:comp/env/SessionContext";
	private static final String USER_TRANSACTION = "java:comp/env/UserTransaction";

	public static UserTransaction getUserTransaction() throws NamingException {
		Context ctx = new InitialContext();
		return (UserTransaction) ctx.lookup(USER_TRANSACTION);
	}

	public static String getPrincipalName() throws NamingException {
		SessionContext stx = getSessionContext();
		String result = stx.getCallerPrincipal().getName();
		if (null == result) {
			return "UNKNOWN USER";
		} else {
			return result;
		}
	}

	private static SessionContext getSessionContext() throws NamingException {
		Context ctx = new InitialContext();
		SessionContext stx = (SessionContext) ctx.lookup(SESSION_CONTEXT);
		return stx;
	}

	public static EntityManager getEntityManager() throws NamingException {
		EntityManager em = null;
		Context ctx = new InitialContext();
		em = (EntityManager) ctx.lookup(ENTITY_MANAGER);
		return em;
	}

	protected ResourceHelper() {
	}

}
