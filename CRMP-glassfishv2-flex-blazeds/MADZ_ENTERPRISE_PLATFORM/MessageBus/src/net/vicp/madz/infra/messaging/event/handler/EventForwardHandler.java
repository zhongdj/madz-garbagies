/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.event.handler;

import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.core.MessageBus;

/**
 * 
 * @author Barry Zhong
 */
public class EventForwardHandler extends AbstractEventHandler {

	public EventForwardHandler() {
	}

	public IEvent process(IEvent event) {
		System.out.println(EventForwardHandler.class.getName() + " is processing " + event.getClass().getName());
		MessageBus.getInstance().sendMessage(event);
		return event;
	}

	@Override
	public void setAcceptedEvents() {
		acceptedEvents = MessageBus.getInstance().getAllEvents();
	}
}
