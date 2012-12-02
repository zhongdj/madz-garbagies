package net.madz.core.state;

public interface IStateChangeListener<T> {

	void update(StateContext<T> context);
}
