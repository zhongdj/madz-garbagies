/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.management;

import net.vicp.madz.infra.messaging.core.MessageBus;

import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.ServerLifecycleException;

/**
 * 
 * @author CleaNEr
 */
public class MessageBusBootstrap implements LifecycleListener {

	public MessageBusBootstrap() {
	}

	private volatile static MessageBus instance;

	public void handleEvent(LifecycleEvent event) throws ServerLifecycleException {
		switch (event.getEventType()) {
		case LifecycleEvent.INIT_EVENT:
			onInitEvent();
			break;
		case LifecycleEvent.READY_EVENT:
			onReadyEvent();
			break;
		case LifecycleEvent.SHUTDOWN_EVENT:
			onShutdownEvent();
			break;
		case LifecycleEvent.STARTUP_EVENT:
			onStartupEvent();
			break;
		case LifecycleEvent.TERMINATION_EVENT:
			onTerminationEvent();
			break;
		default:
			break;
		}
	}

	private void onInitEvent() {
		System.out.println(getClass().getName() + " is initing ....");
		System.out.println(getClass().getName() + " is inited complete.");
	}

	private void onStartupEvent() {
		System.out.println(getClass().getName() + " is starting up ....");
		System.out.println(getClass().getName() + " is started.");
	}

	private void onReadyEvent() {
		System.out.println(getClass().getName() + " is preparing ....");
		instance = MessageBus.getInstance();
		instance.start();
		System.out.println(getClass().getName() + " is ready.");
	}

	private void onShutdownEvent() {
		System.out.println(getClass().getName() + " is shuting down ....");
		if (instance != null) {
			instance.stop();
		}
		System.out.println(getClass().getName() + " is shut down.");
	}

	private void onTerminationEvent() {
		System.out.println(getClass().getName() + " is terminating ....");
		System.out.println(getClass().getName() + " is terminated.");
	}
}
