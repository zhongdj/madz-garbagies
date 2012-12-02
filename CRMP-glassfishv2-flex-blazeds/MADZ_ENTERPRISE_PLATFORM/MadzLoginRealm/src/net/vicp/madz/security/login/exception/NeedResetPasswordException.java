/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vicp.madz.security.login.exception;

/**
 * 
 * @author Administrator
 */
public class NeedResetPasswordException extends Exception {

	public NeedResetPasswordException(String string) {
		super(string);
	}

	public NeedResetPasswordException() {
		super();
	}
}
