package net.vicp.madz.infra.messaging.core;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import net.vicp.madz.infra.messaging.core.event.ShutdownDispatcherEvent;
import net.vicp.madz.infra.messaging.core.model.ConnectionFactory;
import net.vicp.madz.infra.messaging.core.model.Destination;
import net.vicp.madz.infra.messaging.core.model.Message;
import net.vicp.madz.infra.messaging.core.model.MessageType;
import net.vicp.madz.infra.messaging.core.model.ServiceEndPoint;

public class MessageDispatcher implements Runnable {

	private boolean stopped;

	public void run() {
		try {
			while (MessageBus.isStart() && (!Thread.currentThread().isInterrupted())) {
				IEvent event = MessageBus.getInstance().getIEvent();
				if (event instanceof ShutdownDispatcherEvent) {
					closeConnections();
					return;
				} else {
					UserTransaction ut = null;
					if (event != null) {
						try {
							InitialContext ic = new InitialContext();
							ut = (UserTransaction) ic.lookup("UserTransaction");

							String eventType = event.getClass().getName();
							Message message = MessageBus.getInstance().getMessageBusConfig().getMessageConfigMap().get(eventType);
							if (message == null) {
								Logger.getLogger(getClass().getName()).log(Level.SEVERE,
										"EventType:[" + eventType + "] corresponding Message is null.");
								continue;
							} else {
								ut.begin();
								List<Destination> dstlist = message.getDestinationList();
								for (Destination dst : dstlist) {
									MessageConnectivityConfig messageConnectivityConfig = createMessageConnectivityConfig(dst);
									MessageConnectivity messageConnectivity = MessageConnectivityCachedFactory
											.getMessageConnectivity(messageConnectivityConfig);
									MessageSender sender = new MessageSender(messageConnectivity, event);
									ExecutorService executorService = ExecutorServiceCachedFactory.getExecutorService(dst.getName());
									executorService.submit(sender);
								}
								ut.commit();
							}
						} catch (RollbackException ex) {
							try {
								Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
								ut.rollback();
							} catch (IllegalStateException ex1) {
								Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex1);
							} catch (SecurityException ex1) {
								Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex1);
							} catch (SystemException ex1) {
								Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex1);
							}
						} catch (HeuristicMixedException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (HeuristicRollbackException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (SecurityException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (IllegalStateException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (NotSupportedException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (SystemException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						} catch (NamingException ex) {
							Logger.getLogger(MessageDispatcher.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			}
		} finally {
			if (!stopped) {
				closeConnections();
			}
		}
	}

	private MessageConnectivityConfig createMessageConnectivityConfig(Destination dst) {
		String destinationJNDIName = dst.getName();
		ConnectionFactory cf = dst.getConnectionFactory();
		String connectionFactoryJNDIName = cf.getName();
		String username = cf.getUserName();
		String password = cf.getPassword();
		ServiceEndPoint endpoint = cf.getServiceProvider();
		Properties envProperties = endpoint.getContextParameter();
		MessageConnectivityConfig config = new MessageConnectivityConfig();

		config.setConnectionFactoryJNDIName(connectionFactoryJNDIName);
		config.setDestinationJNDIName(destinationJNDIName);
		config.setEnvProperties(envProperties);
		config.setMessageType(MessageType.Object);
		config.setPassword(password);
		config.setUsername(username);
		return config;
	}

	private void closeConnections() {
		stopped = true;
		MessageConnectivityCachedFactory.closeAll();
	}
}
