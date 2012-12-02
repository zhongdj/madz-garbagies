package net.vicp.madz.infra.messaging.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MessageConnectivityCachedFactory {

	private static volatile Map<String, MessageConnectivity> messageConnectivities;

	static {
		if (messageConnectivities == null) {
			synchronized (MessageConnectivityCachedFactory.class) {
				if (messageConnectivities == null) {
					messageConnectivities = Collections.synchronizedMap(new HashMap<String, MessageConnectivity>());
				}
			}
		}
	}

	public static MessageConnectivity getMessageConnectivity(String connFactoryJNDIName, String destinationJNDIName) {
		if (messageConnectivities.containsKey(destinationJNDIName)) {
			return messageConnectivities.get(destinationJNDIName);
		} else {
			synchronized (messageConnectivities) {
				if (messageConnectivities.containsKey(destinationJNDIName)) {
					return messageConnectivities.get(destinationJNDIName);
				} else {
					MessageConnectivity messageConnectivity = createMessageConnectivity(connFactoryJNDIName, destinationJNDIName);
					messageConnectivities.put(destinationJNDIName, messageConnectivity);
					return messageConnectivity;
				}
			}
		}
	}

	public static MessageConnectivity getMessageConnectivity(MessageConnectivityConfig config) {
		if (messageConnectivities.containsKey(config.getDestinationJNDIName())) {
			return messageConnectivities.get(config.getDestinationJNDIName());
		} else {
			synchronized (messageConnectivities) {
				if (messageConnectivities.containsKey(config.getDestinationJNDIName())) {
					return messageConnectivities.get(config.getDestinationJNDIName());
				} else {
					MessageConnectivity messageConnectivity = createMessageConnectivity(config);
					messageConnectivities.put(config.getDestinationJNDIName(), messageConnectivity);
					return messageConnectivity;
				}
			}
		}
	}

	static void closeAll() {
		try {
			Set<Entry<String, MessageConnectivity>> set = messageConnectivities.entrySet();
			for (Entry entry : set) {
				MessageConnectivity conn = (MessageConnectivity) entry.getValue();
				conn.close();
			}
		} finally {
			messageConnectivities.clear();
		}

	}

	private static MessageConnectivity createMessageConnectivity(MessageConnectivityConfig config) {
		return new MessageConnectivity(config);
	}

	private static MessageConnectivity createMessageConnectivity(String connFactoryJNDIName, String destinationJNDIName) {
		return new MessageConnectivity(connFactoryJNDIName, destinationJNDIName);
	}

	private MessageConnectivityCachedFactory() {
	}

}
