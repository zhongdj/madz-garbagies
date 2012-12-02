/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.messaging.facade;

import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.biz.service.event.ApplicationServiceEvent;
import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.core.MessageBus;

/**
 * 
 * @author CleaNEr
 */
public class MessageBusProxy {

	public static MessageBus getInstance() {
		return MessageBus.getInstance();
	}

	public static void submitService(ApplicationService service) {
		getInstance().sendMessage(ApplicationServiceEvent.createApplicationServiceEvent(service));
	}

	public static void sendEvent(IEvent event) {
		getInstance().sendMessage(event);
	}

	private MessageBusProxy() {
	}

}
