package net.madz.core.state.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import net.madz.core.state.annotation.StateIndicator;

/**
 * 
 * @author Barry
 */
public class StateUtil {

	public static Method findStateSetter(Class<?> clz) {
		Method[] methods = clz.getMethods();

		String setterName = null;

		while (!Object.class.equals(clz)) {
			if (null == methods) {
				continue;
			}

			for (Method method : methods) {
				if (null == method.getAnnotation(StateIndicator.class)) {
					continue;
				}
				String name = method.getName();
				if (isGetter(name)) {
					if (name.startsWith("get")) {
						setterName = "set" + name.substring(3);
					} else {
						setterName = "set" + name.substring(2);
					}
					break;
				}
			}
			clz = clz.getSuperclass();
		}

		try {
			if (null != setterName) {
				return clz.getMethod(setterName, String.class);
			}
		} catch (Exception ex) {
			Logger.getLogger(StateUtil.class.getName()).severe(ex.getMessage());
		}
		return null;
	}

	public static Field findStateField(Class<?> clz) {

		while (!Object.class.equals(clz)) {
			Field[] fields = clz.getDeclaredFields();
			if (null == fields) {
				continue;
			}

			for (Field field : fields) {
				if (null != field.getAnnotation(StateIndicator.class)) {
					return field;
				}
			}
			clz = clz.getSuperclass();
		}

		return null;
	}

	private static boolean isGetter(String name) {
		if (name.startsWith("get") || name.startsWith("is")) {
			return true;
		} else {
			return false;
		}
	}

	private StateUtil() {
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

	private static Method findMethodThroughClassHierarchy(Class cl, String name) {
		try {
			return cl.getDeclaredMethod(name);
		} catch (SecurityException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchMethodException e) {
			if (null != cl.getSuperclass()) {
				return findMethodThroughClassHierarchy(cl.getSuperclass(), name);
			} else {
				throw new IllegalStateException(e);
			}
		}
	}
}
