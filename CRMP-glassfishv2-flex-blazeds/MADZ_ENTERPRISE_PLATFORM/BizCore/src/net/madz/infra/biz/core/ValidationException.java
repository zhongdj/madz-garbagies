/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.infra.biz.core;

/**
 * 
 * @author CleaNEr
 */
public class ValidationException extends Exception {

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException() {
		super();
	}

}
