package net.madz.infra.security.messaging.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.infra.security.messaging.event.outbound.TenantLogEvent;
import net.madz.infra.security.util.TenantResources;
import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.event.handler.AbstractEventHandler;

public class TenantLogEventHandler extends AbstractEventHandler {

	@Override
	public IEvent process(IEvent event) {
		if (event instanceof TenantLogEvent) {
			TenantLogEvent logEvent = (TenantLogEvent) event;
			try {
				TenantResources.getEntityManager().persist(logEvent.getTenantOperationLog());
			} catch (NamingException e) {
				Logger.getLogger(TenantLogEventHandler.class.getSimpleName()).log(Level.SEVERE, e.getMessage());
			}
		}
		return event;
	}

	@Override
	public void setAcceptedEvents() {
		this.acceptedEvents.add(TenantLogEvent.class);
	}

}
