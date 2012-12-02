package net.vicp.madz.infra.messaging.core.helper;

import net.vicp.madz.infra.messaging.core.model.MessageType;

/**
 * 
 * @author Barry Zhong
 */
public class TypeMappingHelper {

	static MessageType getMessageType(String type) {
		if (type == null) {
			return null;
		} else if (type.equals("Object")) {
			return MessageType.Object;
		} else if (type.equals("MapMessage")) {
			return MessageType.MapMessage;
		} else if (type.equals("StreamMessage")) {
			return MessageType.StreamMessage;
		} else if (type.equals("Text")) {
			return MessageType.Text;
		} else {
			return null;
		}
	}

	private TypeMappingHelper() {
	}

}
