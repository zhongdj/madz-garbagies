package net.madz.core.state.management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.madz.core.state.IObservable;
import net.madz.core.state.IStateChangeListener;
import net.madz.core.state.StateContext;
import net.madz.core.state.annotation.ReactiveObject;

public class StateChangeNotifier implements StateChangeManagerMBean, IObservable {

	private static volatile StateChangeNotifier instance;

	private final Map<Class, Map<String, List<IStateChangeListener>>> observerMap;

	public static StateChangeNotifier getInstance() {
		if (null == instance) {
			synchronized (StateChangeNotifier.class) {
				if (null == instance) {
					instance = new StateChangeNotifier();
				}
			}
		}
		return instance;
	}

	private StateChangeNotifier() {
		observerMap = Collections.synchronizedMap(new HashMap<Class, Map<String, List<IStateChangeListener>>>());
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void restart() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public <T> void notifyObservers(StateContext<T> context) {
		Class<? extends Object> bizClass = context.getLiveObject().getClass();

		Class<?>[] interfaces = null;
		ArrayList<Class<?>> interfaceList = new ArrayList<Class<?>>();
		for (Class<?> clz = bizClass; null != clz; clz = clz.getSuperclass()) {
			interfaces = bizClass.getInterfaces();
			for (Class<?> anInterface : interfaceList) {
				interfaceList.add(anInterface);
			}
		}
		interfaces = interfaceList.toArray(new Class[0]);
		if (null == interfaces || 0 >= interfaces.length) {
			throw new IllegalStateException("Class: " + bizClass.getName() + " does not implement any interface");
		}

		for (Class<?> interfaze : interfaces) {
			if (null != interfaze.getAnnotation(ReactiveObject.class)) {
				bizClass = interfaze;
				break;
			}
		}

		Map<String, List<IStateChangeListener>> stateMap = null;
		if (!observerMap.containsKey(bizClass)) {
			return;
		} else {
			stateMap = observerMap.get(bizClass);
		}

		List<IStateChangeListener> listenerList = null;
		if (!stateMap.containsKey(context.getCurrentState().getName())) {
			return;
		}

		listenerList = stateMap.get(context.getCurrentState().getName());

		// Make a copy to avoid ConcurrentModificationException
		// while another thread is detaching a listener at the same time;
		listenerList = new ArrayList<IStateChangeListener>(listenerList);
		if (null != listenerList && listenerList.size() > 0) {
			for (IStateChangeListener observer : listenerList) {

				// TODO [Question] How to ensure the following code will return
				// quickly?
				observer.update(context);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public <T> void registerListener(Class<T> bizClass, String stateName, IStateChangeListener<T> observer) {
		synchronized (bizClass) {
			Map<String, List<IStateChangeListener>> stateMap = null;
			if (!observerMap.containsKey(bizClass)) {
				stateMap = Collections.synchronizedMap(new HashMap<String, List<IStateChangeListener>>());
				observerMap.put(bizClass, stateMap);
			} else {
				stateMap = observerMap.get(bizClass);
			}

			List<IStateChangeListener> observerList = null;
			if (!stateMap.containsKey(stateName)) {
				observerList = new LinkedList<IStateChangeListener>();
				stateMap.put(stateName, observerList);
			} else {
				observerList = stateMap.get(stateName);
			}

			if (!observerList.contains(observer)) {
				observerList.add(observer);
			}
		}

	}

	public <T> void registerListener(Class<T> bizClass, String[] stateNames, IStateChangeListener<T> observer) {
		if (null == stateNames || 0 >= stateNames.length) {
			throw new IllegalArgumentException("State names array cannot be empty or null");
		}
		if (null == bizClass || null == observer) {
			throw new IllegalArgumentException("bizClass or observer is null");
		}

		synchronized (bizClass) {
			for (String stateName : stateNames) {
				registerListener(bizClass, stateName, observer);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void removeListener(Class<T> bizClass, String stateName, IStateChangeListener<T> observer) {
		Map<String, List<IStateChangeListener>> stateMap = null;
		if (!observerMap.containsKey(bizClass)) {
			return;
		}

		synchronized (bizClass) {
			stateMap = observerMap.get(bizClass);

			List<IStateChangeListener> observerList = null;
			if (!stateMap.containsKey(stateName)) {
				return;
			} else {
				observerList = stateMap.get(stateName);
			}

			if (observerList.contains(observer)) {
				observerList.remove(observer);
			}
		}
	}

	public <T> void removeListener(Class<T> bizClass, String[] stateNames, IStateChangeListener<T> observer) {
		if (null == stateNames || 0 >= stateNames.length) {
			throw new IllegalArgumentException("State names array cannot be empty or null");
		}
		if (null == bizClass || null == observer) {
			synchronized (bizClass) {
				for (String stateName : stateNames) {
					removeListener(bizClass, stateName, observer);
				}
			}
		}
	}

}
