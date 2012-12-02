/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.core.state.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.core.state.State;
import net.madz.core.state.StateInitiatedException;
import net.madz.core.state.annotation.StateBuilder;

/**
 * 
 * @author Barry
 */
public class StateFactory {

	private static volatile StateFactory instance;
	private Map<Class, Map<String, Class<? extends State>>> stateClassMap = Collections
			.synchronizedMap(new HashMap<Class, Map<String, Class<? extends State>>>());

	private StateFactory() {
	}

	public static StateFactory getInstance() {
		if (null == instance) {
			synchronized (StateFactory.class) {
				if (null == instance) {
					instance = new StateFactory();
				}
			}
		}
		return instance;
	}

	public <T> State<T> newState(T liveObject) throws StateInitiatedException {
		Method[] methods = liveObject.getClass().getMethods();
		String stateName = null;
		if (null == methods || 0 >= methods.length) {
			stateName = findStateNameByField(liveObject);
		} else {
			stateName = findStateNameByMethod(liveObject);
			if (null == stateName) {
				stateName = findStateNameByField(liveObject);
			}
		}
		if (null == stateName) {
			throw new StateInitiatedException("StateIndicator field or getter cannot be found in class: " + liveObject.getClass().getName()
					+ "\n" + " or may not be initialized.");
		}

		return newState(liveObject, stateName);
	}

	private static Field findFieldThroughClassHierarchy(Class cl, String name) {
		try {
			return cl.getDeclaredField(name);
		} catch (SecurityException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchFieldException e) {
			if (null != cl.getSuperclass()) {
				return findFieldThroughClassHierarchy(cl.getSuperclass(), name);
			} else {
				throw new IllegalStateException(e);
			}
		}
	}

	private String findStateNameByMethod(Object bizObject) throws StateInitiatedException {
		String stateName = null;
		Class<? extends Object> clz = bizObject.getClass();
		while (!Object.class.equals(clz)) {
			Method[] methods = clz.getMethods();
			for (Method method : methods) {
				if (null == method.getAnnotation(net.madz.core.state.annotation.StateIndicator.class)) {
					continue;
				}
				if (!method.getReturnType().equals(String.class)) {
					throw new StateInitiatedException("StateIndicator Method should return String.");
				}
				try {
					return (String) method.invoke(bizObject);
				} catch (Exception ex) {
					Logger.getLogger(StateFactory.class.getName()).log(Level.SEVERE, null, ex);
					throw new StateInitiatedException(ex);
				}
			}
			clz = clz.getSuperclass();
		}
		return stateName;
	}

	private String findStateNameByField(Object bizObject) throws StateInitiatedException {
		String stateName = null;
		Class<? extends Object> clz = bizObject.getClass();
		while (!Object.class.equals(clz)) {
			Field[] fields = clz.getDeclaredFields();
			if (null == fields || 0 >= fields.length) {
				clz = clz.getSuperclass();
				continue;
			}
			for (Field field : fields) {
				if (null == field.getAnnotation(net.madz.core.state.annotation.StateIndicator.class)) {
					continue;
				}

				if (field.isAccessible()) {
					try {
						if (field.get(bizObject) instanceof String) {
							stateName = (String) field.get(bizObject);
						}
						return stateName;
					} catch (Exception ex) {
						Logger.getLogger(StateFactory.class.getName()).log(Level.SEVERE, null, ex);
						throw new StateInitiatedException(ex);
					}
				} else {
					try {
						synchronized (field) {
							field.setAccessible(true);
							stateName = (String) field.get(bizObject);
							field.setAccessible(false);
						}
						return stateName;
					} catch (Exception ex) {
						Logger.getLogger(StateFactory.class.getName()).log(Level.SEVERE, null, ex);
						throw new StateInitiatedException(ex);
					}
				}
			}
			clz = clz.getSuperclass();
		}
		throw new StateInitiatedException("State field cannot be found.");
	}

	public <T> State<T> newState(T liveObject, String stateName) throws StateInitiatedException {

		for (Class clazz = liveObject.getClass(); clazz != null; clazz = clazz.getSuperclass()) {

			Method[] methods = clazz.getDeclaredMethods();
			if (null == methods || 0 >= methods.length) {
				throw new IllegalStateException(liveObject.getClass().getName() + " does not contain any method.");
			}

			Method targetMethod = null;
			for (Method method : methods) {
				if (null != method.getAnnotation(StateBuilder.class)) {
					targetMethod = method;
					break;
				}
			}

			if (null == targetMethod) {
				continue;
			}

			try {
				return (State<T>) targetMethod.invoke(liveObject, liveObject, stateName);
			} catch (Exception e) {
				e.printStackTrace();
				throw new StateInitiatedException(e);
			}
		}
		throw new StateInitiatedException("Cannot find StateBuilder Method through class Hierachy");
	}

}
