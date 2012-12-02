/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.core.state;

import java.sql.Timestamp;

/**
 * 
 * @author Barry
 */
public class Transition<T> {

	private String name;
	private Timestamp startTime;
	private Timestamp endTime;
	private long cost;
	private String fromState;
	private String toState;
	private boolean success;
	private transient T bizObject;

	public Transition(String name, T bizObject) {
		this.name = name;
		this.bizObject = bizObject;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getFromState() {
		return fromState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState;
	}

	public String getName() {
		return name;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getToState() {
		return toState;
	}

	public void setToState(String toState) {
		this.toState = toState;
	}

	public T getBizObject() {
		return bizObject;
	}

	public void setBizObject(T bizObject) {
		this.bizObject = bizObject;
	}

}
