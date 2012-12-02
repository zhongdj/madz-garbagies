/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.event.handler;

import java.util.LinkedList;
import java.util.List;

import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.core.IEventHandler;
import net.vicp.madz.infra.messaging.core.IHandlerContext;

/**
 * 
 * @author Barry Zhong
 */
public abstract class AbstractEventHandler implements IEventHandler {

	protected List<Class> acceptedEvents = new LinkedList();
	protected IHandlerContext context;

	public AbstractEventHandler() {
		setAcceptedEvents();
	}

	public List<Class> getAcceptedEvent() {
		return acceptedEvents;
	}

	public IHandlerContext getHandlerContext() {
		return context;
	}

	public void setHandlerContext(IHandlerContext handlerContext) {
		this.context = handlerContext;
	}

	public abstract IEvent process(IEvent event);

	public abstract void setAcceptedEvents();
}
