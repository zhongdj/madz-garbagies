package net.madz.service.messaging.facade;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.BizObjectManager;
import net.vicp.madz.infra.messaging.core.IEvent;
import net.vicp.madz.infra.messaging.core.MessageBus;

/**
 * 
 * @author Barry Zhong
 */
@MessageDriven(mappedName = "jms/SystemEventQueue", activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@RunAs("ADMIN")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MessageBusListener implements MessageListener {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;

	public MessageBusListener() {
	}

	@PostConstruct
	public void injectBizObjectManager() {
		try {
			BizObjectManager.bind(this);
		} catch (NamingException ex) {
			Logger.getLogger(MessageBusListener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			try {
				Object object = ((ObjectMessage) message).getObject();
				if (object instanceof IEvent) {
					MessageBus instance = MessageBus.getInstance();
					IEvent event = (IEvent) object;
					instance.handleMessage(event);
				}
			} catch (JMSException ex) {
				Logger.getLogger(MessageBusListener.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(MessageBusListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
