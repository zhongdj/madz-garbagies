package net.madz.core.state;

public interface IObservable {

	<T> void registerListener(Class<T> bizClass, String stateName, IStateChangeListener<T> observer);

	<T> void removeListener(Class<T> bizClass, String stateName, IStateChangeListener<T> observer);

	<T> void notifyObservers(StateContext<T> context);
}
