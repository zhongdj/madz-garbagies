package net.vicp.madz.infra.messaging.core.helper;

import java.io.InputStream;
import java.util.List;

import net.vicp.madz.infra.messaging.core.model.ConnectionFactory;
import net.vicp.madz.infra.messaging.core.model.Destination;
import net.vicp.madz.infra.messaging.core.model.DestinationType;
import net.vicp.madz.infra.messaging.core.model.FactoryType;
import net.vicp.madz.infra.messaging.core.model.Message;
import net.vicp.madz.infra.messaging.core.model.MessageBusConfig;
import net.vicp.madz.infra.messaging.core.model.MessageType;
import net.vicp.madz.infra.messaging.core.model.ServiceEndPoint;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author Barry Zhong
 */
public class MessageBusConfigParser {

	private MessageBusConfig config = new MessageBusConfig();
	private Document configDoc;

	public MessageBusConfigParser() throws DocumentException {
		init();
	}

	private void init() throws DocumentException {
		InputStream in = getClass().getResourceAsStream("/MessageBusConfig.xml");
		configDoc = read(in);
	}

	private void parseEndPoints() {
		// get "Endpoints" node element
		Element root = configDoc.getRootElement();
		// get "Endpoints" node element
		Element endPointsSection = root.element(XMLModelConstant.ENDPOINTS);
		// get "ServiceEndPoint" element list
		List<Element> endPointElements = endPointsSection.elements(XMLModelConstant.SERVICEENDPOINT);
		for (Element endPointElement : endPointElements) {
			String pointName = endPointElement.attributeValue(XMLModelConstant.NAME);
			ServiceEndPoint point = new ServiceEndPoint(pointName);
			List<Element> properties = endPointElement.elements(XMLModelConstant.PROPERTY);
			for (Element propertyElement : properties) {
				String name = propertyElement.attributeValue(XMLModelConstant.NAME);
				String value = propertyElement.attributeValue(XMLModelConstant.VALUE);
				point.setProperty(name, value);
			}
			config.getServiceEndPointMap().put(pointName, point);
		}
	}

	/**
     *
     */
	private void parseConnectionFactories() {
		// get "Endpoints" node element
		Element root = configDoc.getRootElement();
		// get "Factories" node element
		Element factoriesSection = root.element(XMLModelConstant.CONNECTION_FACTORIES);
		List<Element> factoriesList = factoriesSection.elements(XMLModelConstant.CONNECTION_FACTORY);

		for (Element factoryElement : factoriesList) {
			String name = factoryElement.attributeValue(XMLModelConstant.NAME);
			String type = factoryElement.attributeValue(XMLModelConstant.TYPE);
			String serviceProvider = factoryElement.attributeValue(XMLModelConstant.SERVICE_PROVIDER);
			String username = factoryElement.attributeValue(XMLModelConstant.USERNAME);
			String password = factoryElement.attributeValue(XMLModelConstant.PASSWORD);
			ConnectionFactory factory = new ConnectionFactory(name);
			if (type == null) {
				throw new ParseException("ConnectionFactory 'type' attribute is null");
			} else if (type.equals("Topic")) {
				factory.setType(FactoryType.Topic);
			} else if (type.equals("Queue")) {
				factory.setType(FactoryType.Queue);
			} else {
				throw new ParseException("Illeagal connectionFactory 'type' attribute");
			}

			ServiceEndPoint point = config.getServiceEndPointMap().get(serviceProvider);
			if (point == null) {
				throw new ParseException("ConnectionFactory 'serviceProvider' attribute is null");
			} else {
				factory.setServiceProvider(point);
			}

			factory.setUserName(username);
			factory.setPassword(password);
			config.getConnectionFactoryMap().put(name, factory);
		}
	}

	private void parseDestinations() {
		// get "Endpoints" node element
		Element root = configDoc.getRootElement();
		// get "Destination" node element
		Element destinationSection = root.element(XMLModelConstant.DESTINATIONS);
		List<Element> destinationsList = destinationSection.elements(XMLModelConstant.DESTINATION);
		for (Element destinationElement : destinationsList) {
			String name = destinationElement.attributeValue(XMLModelConstant.NAME);
			String type = destinationElement.attributeValue(XMLModelConstant.TYPE);
			String connectionFactory = destinationElement.attributeValue(XMLModelConstant.CONNECTION_FACTORY_ATTR);
			Destination destination = new Destination(name);
			if (type == null) {
				throw new ParseException("Destination " + name + "  'type' attribute is null");
			} else if (type.equals("Topic")) {
				destination.setType(DestinationType.Topic);
			} else if (type.equals("Queue")) {
				destination.setType(DestinationType.Queue);
			} else {
				throw new ParseException("Illeagal Destination " + name + " 'type' attribute");
			}
			ConnectionFactory factory = config.getConnectionFactoryMap().get(connectionFactory);
			if (factory == null) {
				throw new ParseException("Destination 'connectionFactory' attribute is null");
			}
			destination.setConnectionFactory(factory);
			config.getDestinationMap().put(name, destination);
		}
	}

	private void parseMessageConfig() {
		// get "Endpoints" node element
		Element root = configDoc.getRootElement();
		// get "MessageConfig" node element
		Element messageConfigSection = root.element(XMLModelConstant.MESSAGE_CONFIG);
		List<Element> messageList = messageConfigSection.elements(XMLModelConstant.MESSAGE);
		for (Element messageElement : messageList) {
			String type = messageElement.attributeValue(XMLModelConstant.TYPE);
			Message msg = new Message(type);

			Element messageTypeElement = messageElement.element(XMLModelConstant.MESSAGE_TYPE);
			String messageType = messageTypeElement.attributeValue(XMLModelConstant.TYPE);
			MessageType msgType = TypeMappingHelper.getMessageType(messageType);
			if (msgType == null) {
				throw new ParseException("Message: " + type + " has illegal <" + XMLModelConstant.MESSAGE_TYPE + "> element");
			}
			msg.setMessageType(msgType);

			Element destinationsElement = messageElement.element(XMLModelConstant.DESTINATIONS);
			if (destinationsElement == null) {
				throw new ParseException("Message: " + type + " has illegal <" + XMLModelConstant.DESTINATIONS + "> element");
			}

			List<Element> destinationList = destinationsElement.elements(XMLModelConstant.DESTINATION);
			for (Element destinationElement : destinationList) {
				String name = destinationElement.attributeValue(XMLModelConstant.NAME);
				Destination dest = config.getDestinationMap().get(name);
				if (dest == null) {
					throw new ParseException("Message: " + type + " has illegal <" + XMLModelConstant.DESTINATION + "> element");
				}
				msg.addDestination(dest);
			}
			config.getMessageConfigMap().put(type, msg);
		}
	}

	private Document read(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		return doc;
	}

	public MessageBusConfig parse() {
		try {
			parseEndPoints();
			parseConnectionFactories();
			parseDestinations();
			parseMessageConfig();
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, "exception caught", ex);
		}
		return config;
	}
}
