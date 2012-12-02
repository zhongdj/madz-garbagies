/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.core.state;

/**
 * 
 * @author Barry
 */
public class StateChangeException extends Exception {

	private static final long serialVersionUID = 1L;
	private StateContext<?> stateContext;

	public StateChangeException(Throwable cause) {
		super(cause);
	}

	public StateChangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public StateChangeException(String message) {
		super(message);
	}

	public StateChangeException(StateContext<?> ctx) {
		super();
		this.stateContext = ctx;
	}

	public StateChangeException() {
	}

	public StateContext<?> getContext() {
		return stateContext;
	}

	public void setContext(StateContext<?> context) {
		this.stateContext = context;
	}
}
