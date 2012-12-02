/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.appserv.security;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import net.vicp.madz.security.login.exception.UserLockedException;
import net.vicp.madz.security.login.exception.UserNotExistException;
import net.vicp.madz.security.login.factory.EncryptorFactory;
import net.vicp.madz.security.login.impl.AuditPolicyAdvisor;
import net.vicp.madz.security.login.impl.Principal;
import net.vicp.madz.security.login.impl.PrincipalAuditor;
import net.vicp.madz.security.login.interfaces.IPasswordEncryptor;

import com.iplanet.ias.security.auth.realm.IASRealm;
import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import com.sun.org.apache.commons.collections.ArrayEnumeration;

/**
 * 
 * @author CleaNEr
 */
public class MadzRealm extends AppservRealm {

	public static final String AUTH_TYPE = "Madz JDBC Realm";

	@Override
	protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
		super.init(props);
		String jaasCtx = props.getProperty(IASRealm.JAAS_CONTEXT_PARAM);

		// TODO:implements init properties of MadzRealm
		{
			if (jaasCtx == null) {
				String msg = "JAAS_CONTEXT_PARAM is null";
				throw new BadRealmException(msg);
			} else {
				this.setProperty(IASRealm.JAAS_CONTEXT_PARAM, jaasCtx);
				if (_logger.isLoggable(Level.FINE)) {
					_logger.fine("MadzRealm : " + IASRealm.JAAS_CONTEXT_PARAM + "=" + jaasCtx);
				}
			}
		}
	}

	public String[] authenticate(String username, String password) throws LoginException {
		if (_logger.isLoggable(Level.FINE)) {
			_logger.fine("MadzRealm is authenticating " + username + "...");
		}

		EncryptorFactory encryptorFactory = EncryptorFactory.getInstance();
		IPasswordEncryptor encryptor = encryptorFactory.getPasswordEncryptor();
		AuditPolicyAdvisor auditPolicy = AuditPolicyAdvisor.getInstance();
		PrincipalAuditor instance = PrincipalAuditor.getInstance(encryptor, auditPolicy);
		try {
			String[] groups = instance.authenticateUser(username, password);
			if (_logger.isLoggable(Level.FINE)) {
				_logger.fine(username + " is authenticated Successfully");
			}
			return groups;
		} catch (UserNotExistException ex) {
			if (_logger.isLoggable(Level.WARNING)) {
				_logger.fine(username + " is authenticated Failed");
			}
			Logger.getLogger(MadzRealm.class.getName()).log(Level.SEVERE, null, ex);
			LoginException e = new LoginException();
			e.initCause(ex);
			throw e;
		} catch (UserLockedException ex) {
			if (_logger.isLoggable(Level.WARNING)) {
				_logger.fine(username + " is authenticated Failed");
			}
			Logger.getLogger(MadzRealm.class.getName()).log(Level.SEVERE, null, ex);
			LoginException e = new LoginException();
			e.initCause(ex);
			throw e;
		}

	}

	@Override
	public String getAuthType() {
		return AUTH_TYPE;
	}

	@Override
	public Enumeration getGroupNames(String username) throws InvalidOperationException, NoSuchUserException {
		try {

			String[] groups = Principal.findPrincipal(username).findGroup();
			LinkedList list = new LinkedList();
			for (int i = 0; i < groups.length; i++) {
				list.add(groups[i]);
			}
			return new ArrayEnumeration(list);
		} catch (UserNotExistException ex) {
			throw new NoSuchUserException(username + " does not exist");
		}
	}
}
