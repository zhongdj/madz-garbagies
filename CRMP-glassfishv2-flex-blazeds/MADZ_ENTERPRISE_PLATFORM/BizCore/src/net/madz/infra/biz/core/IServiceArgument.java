/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.infra.biz.core;

/**
 * 
 * @author CleaNEr
 */
public interface IServiceArgument {
	void validate() throws ValidationException;

	String getArgumentName();
}
