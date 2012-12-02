package net.madz.core.state;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.madz.core.state.annotation.TransitionMethod;

public class StateChangeInterceptor<T> implements InvocationHandler {

	private T liveObject;

	public StateChangeInterceptor(T t) {
		this.liveObject = t;
	}

	public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
		if (null == proxy || null == proxyMethod) {
			throw new NullPointerException();
		}

		Method method = liveObject.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());

		if (null != method.getAnnotation(TransitionMethod.class)) {
			// create state machine
			final StateChangeEngine stateChangeEngine = StateChangeEngine.getInstance();
			// state machine forward
			final StateContext<T> context = new StateContext<T>();

			context.setProxy((T) proxy);
			context.setArgs(args);
			context.setLiveObject(liveObject);
			context.setMethod(method);

			final TransitionMethod transition = method.getAnnotation(TransitionMethod.class);
			String transitionName = method.getName();
			if (transition != null && transition.name() != null) {
				transitionName = transition.name();
			}
			synchronized (liveObject) {
				// perform state change, and execute biz logic, and at last
				// notify state change
				stateChangeEngine.moveForward(transitionName, context);
			}
			// clean state context
			Object result = context.getResult();
			// context.destroy();
			return result;
		} else {
			return method.invoke(liveObject, args);
		}
	}
}
