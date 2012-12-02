package net.madz.core.state;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.core.state.management.StateChangeNotifier;
import net.madz.core.state.util.StateFactory;
import net.madz.core.state.util.StateUtil;
import net.madz.core.util.Log;

public class StateChangeEngine<T> {

	private final class NotifyTask<T> implements Callable<Boolean> {
		private final StateContext<T> context;
		private Thread runningThread;

		private NotifyTask(StateContext<T> context) {
			this.context = context;
		}

		public Boolean call() {
			runningThread = Thread.currentThread();
			// notify state change
			String threadName = Thread.currentThread().getName();
			if (null == threadName || !threadName.startsWith(STATE_CHANGE_NOTIFY_WORKER_THREAD_PREFIX)) {
				Thread.currentThread().setName(STATE_CHANGE_NOTIFY_WORKER_THREAD_PREFIX + "-" + (workerCounter++));
			}
			try {
				StateChangeNotifier.getInstance().notifyObservers(context);
			} catch (Exception ex) {
				Logger.getLogger(StateChangeEngine.class.getName()).log(Level.SEVERE, null, ex);
			}
			return true;
		}

		public void interrupt() {
			runningThread.interrupt();
		}
	}

	private static final String STATE_CHANGE_NOTIFY_WORKER_THREAD_PREFIX = "StateChangeNotifyWorkerThread";
	private static final StateChangeEngine INSTANCE = new StateChangeEngine();
	private static volatile ExecutorService executor = Executors.newCachedThreadPool();
	private static volatile int workerCounter = 1;

	public static StateChangeEngine getInstance() {
		return INSTANCE;
	}

	private StateChangeEngine() {
	}

	public <T> void moveForward(String transitionName, StateContext<T> context) throws StateInitiatedException, StateChangeException {
		final T liveObject = context.getLiveObject();
		if (null == liveObject) {
			throw new IllegalArgumentException("Cannot find liveObject in context.");
		}
		State<T> currentState = StateFactory.getInstance().newState(liveObject);

		final Transition<T> transition = new Transition<T>(transitionName, liveObject);
		transition.setStartTime(new Timestamp(System.currentTimeMillis()));
		transition.setFromState(currentState.getName());
		transition.setSuccess(false);
		context.setTransition(transition);

		try {
			context.setLastState(currentState);
			currentState = currentState.doStateChange(context);
			if (null != context.getMethod()) {
				Object result = context.getMethod().invoke(context.getLiveObject(), context.getArgs());
				context.setResult(result);
			}
			transition.setSuccess(true);
		} catch (StateChangeException ex) {
			Logger.getLogger(StateChangeEngine.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} catch (Exception ex) {
			Logger.getLogger(StateChangeEngine.class.getName()).log(Level.SEVERE, null, ex);
			throw new StateChangeException(ex);
		} finally {
			if (transition.isSuccess()) {
				transition.setToState(currentState.getName());
				context.setCurrentState(currentState);
				updateLiveObjectState(context);
			}
			transition.setEndTime(new Timestamp(System.currentTimeMillis()));
			context.setTransition(transition);
			if (transition.isSuccess()) {
				notifyStateChange(context);
			}
		}

	}

	private <T> void notifyStateChange(final StateContext<T> context) {

		// TODO [Notice][Step 1] Using StateChangeEngine's thread pool to run
		// notification task.
		NotifyTask<T> notifyTask = new NotifyTask<T>(context);
		Future<Boolean> submit = executor.submit(notifyTask);

		try {
			submit.get(5L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Log.error("StateChangeEngine." + "notifyStateChange", e.getMessage(), e);
			notifyTask.interrupt();
		} catch (ExecutionException e) {
			Log.error("StateChangeEngine." + "notifyStateChange", e.getMessage(), e);
		} catch (TimeoutException e) {
			Log.error("StateChangeEngine." + "notifyStateChange", e.getMessage(), e);
			notifyTask.interrupt();
		}

	}

	private <T> void updateLiveObjectState(StateContext<T> context) {

		final T liveObject = context.getLiveObject();
		final State<T> currentState = context.getCurrentState();

		Method setter = StateUtil.findStateSetter(liveObject.getClass());
		if (null == setter) {
			Field stateField = StateUtil.findStateField(liveObject.getClass());
			if (null == stateField) {
				throw new NullPointerException();
			}
			try {
				if (stateField.isAccessible()) {
					stateField.set(liveObject, currentState.getName());
				} else {
					synchronized (stateField) {
						stateField.setAccessible(true);
						stateField.set(liveObject, currentState.getName());
						stateField.setAccessible(false);
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(StateChangeEngine.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			try {
				setter.invoke(liveObject, currentState.getName());
			} catch (Exception ex) {
				Logger.getLogger(StateChangeEngine.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
