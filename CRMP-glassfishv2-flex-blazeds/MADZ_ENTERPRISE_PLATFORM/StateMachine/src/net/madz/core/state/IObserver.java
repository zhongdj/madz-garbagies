package net.madz.core.state;

public interface IObserver<T> {

	void update(T bizObject, State<T> currentState, StateContext<T> context);
}
