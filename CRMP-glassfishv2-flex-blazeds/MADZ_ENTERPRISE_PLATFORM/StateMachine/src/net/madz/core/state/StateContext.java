/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.core.state;

import java.lang.reflect.Method;

/**
 * 
 * @author Barry
 */
public class StateContext<T> {

	private State<T> lastState;
	private State<T> currentState;
	private Transition<T> lastTransition;

	private Method method;
	private Object[] args;
	private Object result;

	private T liveObject;
	private T proxy;

	public State<T> getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State<T> currentState) {
		this.currentState = currentState;
	}

	public State<T> getLastState() {
		return lastState;
	}

	public void setLastState(State<T> lastState) {
		this.lastState = lastState;
	}

	public Transition<T> getTransition() {
		return lastTransition;
	}

	public void setTransition(Transition<T> lastTransition) {
		this.lastTransition = lastTransition;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public T getLiveObject() {
		return liveObject;
	}

	public void setLiveObject(T bizObject) {
		this.liveObject = bizObject;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void destroy() {
		this.method = null;
		this.args = null;
		this.currentState = null;
		this.lastState = null;
		this.liveObject = null;
		this.result = null;
	}

	public T getProxy() {
		return proxy;
	}

	public void setProxy(T proxy) {
		this.proxy = proxy;
	}
}
