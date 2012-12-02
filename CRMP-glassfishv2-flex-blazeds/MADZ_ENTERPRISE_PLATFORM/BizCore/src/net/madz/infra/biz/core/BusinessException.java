/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.biz.core;

/**
 * 
 * @author CleaNEr
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -829445478088522146L;

	public BusinessException(Exception ex) {
		super(ex);
	}

	public BusinessException(String message, Exception ex) {
		super(message, ex);
	}

	public BusinessException(String message) {
		super(message);
	}
}
