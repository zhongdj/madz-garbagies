/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.core.state;

/**
 * 
 * @author Barry
 */
public class StateInitiatedException extends Exception {

	private static final long serialVersionUID = 1L;

	public StateInitiatedException(Throwable cause) {
		super(cause);
	}

	public StateInitiatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public StateInitiatedException(String message) {
		super(message);
	}

	public StateInitiatedException() {
	}

}
