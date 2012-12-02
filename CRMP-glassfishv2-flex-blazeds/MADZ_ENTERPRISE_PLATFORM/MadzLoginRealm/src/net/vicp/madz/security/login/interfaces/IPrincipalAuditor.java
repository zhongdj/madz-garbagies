/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.security.login.interfaces;

import javax.security.auth.login.LoginException;

import net.vicp.madz.security.login.exception.NeedResetPasswordException;
import net.vicp.madz.security.login.exception.PasswordException;
import net.vicp.madz.security.login.exception.UserLockedException;
import net.vicp.madz.security.login.exception.UserNotExistException;

/**
 * 
 * @author Administrator
 */
public interface IPrincipalAuditor {

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws net.vicp.tracy.login.interfaces.UserNotExistException
	 * @exception IllegalStateException
	 *                if IPasswordProvider field is null
	 */
	String[] authenticateUser(String userName, String inputPassword) throws UserNotExistException, UserLockedException, LoginException;

	boolean auditPassword(String password) throws PasswordException;

	/**
	 * 
	 * @param principal
	 * @return
	 * @throws NeedResetPasswordException
	 *             if principal's password reached the password life
	 * @throws
	 */
	boolean auditPrincipal(IPrincipal principal) throws NeedResetPasswordException, PasswordException, UserLockedException;
}
