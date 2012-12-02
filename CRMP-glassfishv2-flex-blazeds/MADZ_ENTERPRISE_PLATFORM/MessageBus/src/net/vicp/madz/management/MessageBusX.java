package net.vicp.madz.management;

import net.vicp.madz.infra.messaging.core.MessageBus;

/**
 * Class MessageBusX
 * 
 * @author CleaNEr
 */
public class MessageBusX implements MessageBusXMBean {

	public MessageBusX() {
	}

	public void reload() {
		System.out.println(getClass().getName() + " is reloading ...");
		MessageBus.getInstance().reload();
		System.out.println(getClass().getName() + " is reloaded complete.");
	}

	public void restart() {
		System.out.println(getClass().getName() + " is restarting ...");
		MessageBus.getInstance().restart();
		System.out.println(getClass().getName() + " restart complete.");
	}

	public void start() {
		System.out.println(getClass().getName() + " is starting ...");
		MessageBus.getInstance().start();
		System.out.println(getClass().getName() + " start complete.");
	}

	public void stop() {
		System.out.println(getClass().getName() + " is stoping ...");
		MessageBus.getInstance().stop();
		System.out.println(getClass().getName() + " stop complete.");
	}
}
