package net.madz.infra.security.logging.util;

import net.madz.infra.logging.util.ServiceLogger;
import net.madz.infra.messaging.facade.MessageBusProxy;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.logging.service.TenantIdentifiedLoggingService;

public class TenantIdentifiedSerivceLogger extends ServiceLogger {

	public static void log(TenantIdentifiedService service) {
		TenantIdentifiedLoggingService loggingService = new TenantIdentifiedLoggingService(service);
		MessageBusProxy.submitService(loggingService);
	}
}
