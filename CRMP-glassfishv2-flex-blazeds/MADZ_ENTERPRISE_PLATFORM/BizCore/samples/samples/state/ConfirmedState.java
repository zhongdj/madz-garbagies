package samples.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import samples.domain.LadingRequest;

public class ConfirmedState extends State<LadingRequest> {

	public ConfirmedState(LadingRequest bizObject) {
		super(LadingRequestState.ConfirmedState.toString(), bizObject);
	}

	@Override
	public State<LadingRequest> doStateChange(StateContext<LadingRequest> context) throws StateChangeException {
		if (null != context.getTransition().getName() && context.getTransition().getName().equals("pick")) {
			return new PickedState(liveObject);
		} else {
			throw new StateChangeException("");
		}
	}

}
