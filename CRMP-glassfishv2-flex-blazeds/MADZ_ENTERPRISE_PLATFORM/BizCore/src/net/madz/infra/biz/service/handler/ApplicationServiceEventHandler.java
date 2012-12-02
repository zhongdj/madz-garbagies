/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.biz.service.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.biz.service.event.ApplicationServiceEvent;
import net.madz.infra.logging.service.LoggingService;
import net.madz.infra.logging.util.ServiceLogger;
import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.event.handler.AbstractEventHandler;

/**
 * 
 * @author CleaNEr
 */
public class ApplicationServiceEventHandler extends AbstractEventHandler {

	@Override
	public IEvent process(IEvent event) {
		if (event instanceof ApplicationServiceEvent) {
			ApplicationService service = ((ApplicationServiceEvent) event).getService();
			try {
				service.process();
			} catch (ApplicationServiceException ex) {
				Logger.getLogger(ApplicationServiceEventHandler.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				if (service instanceof LoggingService) {
				} else {
					ServiceLogger.log(service);
				}
			}
		}
		return event;
	}

	@Override
	public void setAcceptedEvents() {
		this.acceptedEvents.add(ApplicationServiceEvent.class);
	}
}
