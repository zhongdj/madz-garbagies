/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vicp.madz.infra.messaging.core;

import java.util.List;

/**
 * 
 * @author Barry Zhong
 */
public interface IEventHandler {
	public IEvent process(IEvent event);

	public IHandlerContext getHandlerContext();

	public void setHandlerContext(IHandlerContext context);

	public List<Class> getAcceptedEvent();
}
