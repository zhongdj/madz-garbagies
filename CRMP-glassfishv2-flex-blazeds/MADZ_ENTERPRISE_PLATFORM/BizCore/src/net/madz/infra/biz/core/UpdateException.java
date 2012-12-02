/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.biz.core;

import java.util.List;

/**
 * 
 * @author CleaNEr
 */
public class UpdateException extends Exception {

	private static final long serialVersionUID = 519933910108548253L;
	private List faultList;

	public UpdateException(List faultList) {
		super();
		this.faultList = faultList;
	}

	public List getFaultList() {
		return faultList;
	}

	public void setFaultList(List faultList) {
		this.faultList = faultList;
	}
}
