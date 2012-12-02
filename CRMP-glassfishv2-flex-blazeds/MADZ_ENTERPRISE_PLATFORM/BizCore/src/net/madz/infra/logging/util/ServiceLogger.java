/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.logging.util;

import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.logging.service.LoggingService;
import net.madz.infra.messaging.facade.MessageBusProxy;

/**
 * 
 * @author CleaNEr
 */
public class ServiceLogger {

	public static void log(ApplicationService service) {
		LoggingService loggingService = new LoggingService(service);
		MessageBusProxy.submitService(loggingService);
	}

	protected ServiceLogger() {
	}

}
