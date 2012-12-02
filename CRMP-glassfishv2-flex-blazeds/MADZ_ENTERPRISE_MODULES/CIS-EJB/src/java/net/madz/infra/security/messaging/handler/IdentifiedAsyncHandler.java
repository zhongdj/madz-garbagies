/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.messaging.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.infra.messaging.event.IdentifiedEvent;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.util.TenantResources;
import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.event.handler.AbstractEventHandler;

/**
 * 
 * @author Barry
 */
public abstract class IdentifiedAsyncHandler extends AbstractEventHandler {

	protected Tenant tenant;

	@Override
	public IEvent process(IEvent event) {
		if (event instanceof IdentifiedEvent) {
			try {
				tenant = TenantResources.getOwnedCompany();
				if (null == tenant) {
					Logger.getLogger(IdentifiedAsyncHandler.class.getName()).log(Level.WARNING,
							"Cannot find an company which corresponds this IdentifiedEvent: " + event);
					return event;
				}
				return processEvent((IdentifiedEvent) event);
			} catch (Exception ex) {
				Logger.getLogger(IdentifiedAsyncHandler.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return event;
	}

	@Override
	public void setAcceptedEvents() {
		this.acceptedEvents.add(IdentifiedEvent.class);
	}

	protected abstract IdentifiedEvent processEvent(IdentifiedEvent event);
}
