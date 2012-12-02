package net.vicp.madz.infra.messaging.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceCachedFactory {
	private static volatile Map<String, ExecutorService> executors;

	static {
		if (executors == null) {
			synchronized (ExecutorServiceCachedFactory.class) {
				if (executors == null) {
					executors = Collections.synchronizedMap(new HashMap<String, ExecutorService>());
				}
			}
		}
	}

	public static ExecutorService getExecutorService(String destinationJNDIName) {
		if (executors.containsKey(destinationJNDIName)) {
			return executors.get(destinationJNDIName);
		} else {
			synchronized (executors) {
				if (executors.containsKey(destinationJNDIName)) {
					return executors.get(destinationJNDIName);
				} else {
					ExecutorService executorService = Executors.newSingleThreadExecutor();
					executors.put(destinationJNDIName, executorService);
					return executorService;
				}
			}
		}
	}

	private ExecutorServiceCachedFactory() {
	}

}
