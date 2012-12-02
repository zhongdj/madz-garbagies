/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vicp.madz.infra.messaging.core;

/**
 * 
 * @author Barry Zhong
 */
public interface IHandlerContext {
	public boolean isFinished();

	public void setFinished(boolean flag);
}
