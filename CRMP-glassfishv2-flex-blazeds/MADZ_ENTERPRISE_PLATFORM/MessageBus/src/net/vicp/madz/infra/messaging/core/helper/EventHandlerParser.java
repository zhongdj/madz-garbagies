/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core.helper;

import java.io.InputStream;
import java.util.List;

import net.vicp.madz.infra.messaging.core.model.Message;
import net.vicp.madz.infra.messaging.core.model.MessageBusConfig;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author Barry Zhong
 */
public class EventHandlerParser {

	private MessageBusConfig config;
	private Document configDoc;

	public EventHandlerParser(MessageBusConfig busConfig) throws DocumentException {
		this.config = busConfig;
		init();
	}

	private void init() throws DocumentException {
		InputStream in = getClass().getResourceAsStream("/EventHandlerMapping.xml");
		configDoc = read(in);
	}

	private Document read(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		return doc;
	}

	public MessageBusConfig parse() {
		Element eventsElement = configDoc.getRootElement();
		List<Element> eventList = eventsElement.elements(XMLModelConstant.EVENT);
		for (Element eventElement : eventList) {

			String type = eventElement.attributeValue(XMLModelConstant.TYPE);
			if (!config.getMessageConfigMap().containsKey(type)) {
				config.getMessageConfigMap().put(type, new Message(type));
			}
			Message msg = config.getMessageConfigMap().get(type);

			Element handlersElement = eventElement.element(XMLModelConstant.HANDLERS);
			List<Element> handlerList = handlersElement.elements(XMLModelConstant.HANDLER);
			for (Element handlerElement : handlerList) {
				String handlerType = handlerElement.attributeValue(XMLModelConstant.TYPE);
				msg.addHandler(handlerType);
			}
		}
		return config;
	}
}
