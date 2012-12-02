/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.util;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.core.state.StateChangeInterceptor;
import net.madz.core.state.annotation.ReactiveObject;

/**
 * 
 * @author Barry
 */
public class BizObjectUtil {

	private BizObjectUtil() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T newProxy(T bizObject) {
		if (null == bizObject) {
			return null;
		}

		if (isBizObject(bizObject)) {
			Class<T> bizClass = (Class<T>) bizObject.getClass();

			ArrayList<Class<?>> interfacesList = new ArrayList<Class<?>>();
			for (Class<?> clz = bizClass; null != clz; clz = clz.getSuperclass()) {
				final Class<?>[] interfaces = clz.getInterfaces();
				for (Class<?> anInterface : interfaces) {
					interfacesList.add(anInterface);
				}
			}

			return (T) Proxy.newProxyInstance(bizClass.getClassLoader(), interfacesList.toArray(new Class[0]),
					new StateChangeInterceptor<T>(bizObject));
		} else {
			return bizObject;
		}
	}

	@SuppressWarnings("unchecked")
	public static <I, T extends I> I newProxy(Class<I> tInterface, T bizObject) {
		if (null == bizObject) {
			return null;
		}

		if (isBizObject(bizObject)) {
			Class<T> bizClass = (Class<T>) bizObject.getClass();

			Class<I>[] interfaceArray = new Class[1];
			interfaceArray[0] = tInterface;

			return (T) Proxy.newProxyInstance(bizClass.getClassLoader(), interfaceArray, new StateChangeInterceptor<T>(bizObject));
		} else {
			return bizObject;
		}
	}

	public static <T> T find(Class<T> cl, Object id) {
		try {
			T entity = ResourceHelper.getEntityManager().find(cl, id);
			return newProxy(entity);
		} catch (NamingException ex) {
			Logger.getLogger(BizObjectUtil.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static boolean isBizObject(Object bizObject) {
		if (bizObject.getClass().getAnnotation(ReactiveObject.class) == null) {
			return false;
		}
		return true;
	}
}
