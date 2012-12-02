package net.madz.core.state;

public class FinalState<T> extends State<T> {

	public FinalState(String name, T bizObject) {
		super(name, bizObject);
	}

	@Override
	public final State<T> doStateChange(StateContext<T> context) throws StateChangeException {

		throw new StateChangeException("State " + stateName + " is a final state, state won't be transited any more.");
	}

}
