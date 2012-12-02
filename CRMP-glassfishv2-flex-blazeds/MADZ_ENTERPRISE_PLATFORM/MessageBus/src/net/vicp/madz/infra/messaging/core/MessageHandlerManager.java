package net.vicp.madz.infra.messaging.core;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.vicp.madz.infra.messaging.core.model.Message;

public class MessageHandlerManager {

	public static void processHandler(IEvent event) {
		String eventType = event.getClass().getName();

		System.out.println("MessageBus::Handling IEvent [" + eventType + "] ...");

		Message message = MessageBus.getInstance().getMessageBusConfig().getMessageConfigMap().get(eventType);
		List<String> handlerNameList = message.getHandlerList();
		IHandlerContext context = null;
		for (String handlerName : handlerNameList) {
			try {
				Class handlerClz = Thread.currentThread().getContextClassLoader().loadClass(handlerName);
				IEventHandler handler = (IEventHandler) handlerClz.newInstance();
				// Initial HandlerContext
				{
					if (context == null) {
						if (handler.getHandlerContext() == null) {
							context = new HandlerContext();
							handler.setHandlerContext(context);
						} else {
							context = handler.getHandlerContext();
						}
					} else {
						handler.setHandlerContext(context);
					}
				}

				// Process Handlers
				{
					List<Class> acceptList = handler.getAcceptedEvent();
					if (acceptList.contains(event.getClass())) {
						System.out.println("With handler: [" + handler.getClass().getName() + "]");
						event = handler.process(event);
					}
				}

				// Handler Chain continue ?
				{
					if (context.isFinished()) {
						break;
					}
				}
			} catch (InstantiationException ex) {
				Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(MessageBus.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	private MessageHandlerManager() {
	}
}
