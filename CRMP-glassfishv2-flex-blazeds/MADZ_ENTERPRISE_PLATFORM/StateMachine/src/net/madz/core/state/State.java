/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.core.state;

/**
 * 
 * @author Barry
 */
public abstract class State<T> {

	protected String stateName;
	protected T liveObject;

	public State(String name, T liveObject) {
		this.stateName = name;
		this.liveObject = liveObject;
	}

	public String getName() {
		return stateName;
	}

	public void setName(String name) {
		this.stateName = name;
	}

	public T getBizObject() {
		return liveObject;
	}

	public void setBizObject(T bizObject) {
		this.liveObject = bizObject;
	}

	public abstract State<T> doStateChange(StateContext<T> context) throws StateChangeException;

	protected State<T> invalidStateChange(StateContext<T> context) throws StateChangeException {
		StateChangeException ex = new StateChangeException("Illegal State Change: state = " + stateName + ", action = "
				+ context.getTransition() + ", task = " + context.getLiveObject());
		ex.setContext(context);
		throw ex;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		State other = (State) obj;
		if (stateName == null) {
			if (other.stateName != null) {
				return false;
			}
		} else if (!stateName.equals(other.stateName)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "State [stateName=" + stateName + "]";
	}

}
