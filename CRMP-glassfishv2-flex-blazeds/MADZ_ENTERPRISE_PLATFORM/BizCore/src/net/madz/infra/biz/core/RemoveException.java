/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.biz.core;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author CleaNEr
 */
public class RemoveException extends Exception {

	private static final long serialVersionUID = 3553742181966423644L;
	private List faultList;

	public RemoveException() {
		super();
	}

	public RemoveException(String string) {
		super(string);
	}

	public List getFaultList() {
		return faultList;
	}

	public void setFaultList(List faultList) {
		this.faultList = faultList;
	}

	public void addFaultItem(Object item) {
		if (faultList == null) {
			faultList = new LinkedList();
		}
		faultList.add(item);
	}
}
