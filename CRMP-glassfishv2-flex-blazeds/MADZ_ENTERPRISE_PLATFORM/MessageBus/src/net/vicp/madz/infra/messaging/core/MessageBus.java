package net.vicp.madz.infra.messaging.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.vicp.madz.infra.messaging.core.event.ShutdownDispatcherEvent;
import net.vicp.madz.infra.messaging.core.helper.EventHandlerParser;
import net.vicp.madz.infra.messaging.core.helper.MessageBusConfigParser;
import net.vicp.madz.infra.messaging.core.model.Message;
import net.vicp.madz.infra.messaging.core.model.MessageBusConfig;

import org.dom4j.DocumentException;

public class MessageBus implements LifeCycle {

	private volatile static MessageBus instance;
	private BlockingQueue<IEvent> messageQueue;
	private volatile MessageDispatcher dispatcher;
	private MessageBusConfigParser busConfigParser;
	private EventHandlerParser eventParser;
	private MessageBusConfig config;
	private volatile static boolean start;
	private Thread dispatcherThread;

	public static boolean isStart() {
		return start;
	}

	private static void setStart(boolean start) {
		MessageBus.start = start;
	}

	public void start() {
		if (start) {
			return;
		}
		setStart(true);
		if (dispatcher == null) {
			synchronized (MessageBus.class) {
				if (dispatcher == null) {
					dispatcher = new MessageDispatcher();
				}
			}
		}
		dispatcherThread = new Thread(dispatcher);
		dispatcherThread.start();
		try {
			loadConfig();
		} catch (DocumentException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println(getClass().getName() + " service started.");
	}

	public void restart() {
		stop();
		start();
	}

	public void stop() {
		if (!start) {
			return;
		}
		setStart(false);
		try {
			this.sendMessage(new ShutdownDispatcherEvent());
			dispatcherThread.interrupt();
		} finally {
			dispatcherThread = null;
			dispatcher = null;
		}
		System.out.println(getClass().getName() + " service stopped.");
	}

	public void reload() {
		try {
			reloadConfig();
		} catch (DocumentException ex) {
			Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public MessageBus() {
		messageQueue = new LinkedBlockingQueue();
		dispatcher = new MessageDispatcher();
	}

	public static MessageBus getInstance() {
		if (instance == null) {
			synchronized (MessageBus.class) {
				if (instance == null) {
					// InitialContext ic = null;
					// try {
					// ic = new InitialContext();
					// instance = (MessageBus)
					// ic.lookup("net.vicp.madz.messaging.core.MessageBus");
					// } catch (NamingException ex) {
					// Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE,
					// null, ex);
					instance = new MessageBus();
					// if (ic != null) {
					// try {
					// ic.bind("net.vicp.madz.messaging.core.MessageBus",
					// instance);
					// } catch (NamingException ex1) {
					// Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE,
					// null, ex1);
					// }
					// }
					// }
				}
			}
		}
		return instance;
	}

	public IEvent getIEvent() {
		try {
			return messageQueue.take();
		} catch (InterruptedException ex) {
			Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public void sendMessage(IEvent event) {
		try {
			messageQueue.put(event);
		} catch (InterruptedException ex) {
			Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void handleMessage(IEvent event) {
		MessageHandlerManager.processHandler(event);
	}

	private void loadConfig() throws DocumentException {
		busConfigParser = new MessageBusConfigParser();
		config = busConfigParser.parse();
		eventParser = new EventHandlerParser(config);
		config = eventParser.parse();
	}

	public void reloadConfig() throws DocumentException {
		loadConfig();
	}

	public MessageBusConfig getMessageBusConfig() {
		return this.config;
	}

	public List<Class> getAllEvents() {
		Collection<Message> coll = config.getMessageConfigMap().values();
		List<Class> list = new LinkedList<Class>();
		for (Message msg : coll) {
			try {
				Class clz = Class.forName(msg.getName());
				list.add(clz);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return list;
	}
}
