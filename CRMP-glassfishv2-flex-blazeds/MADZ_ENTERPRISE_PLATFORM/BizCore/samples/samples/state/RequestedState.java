package samples.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import samples.domain.LadingRequest;

public class RequestedState extends State<LadingRequest> {

	public RequestedState(LadingRequest bizObject) {
		super(LadingRequestState.RequestedState.toString(), bizObject);
	}

	@Override
	public State<LadingRequest> doStateChange(StateContext<LadingRequest> context) throws StateChangeException {
		if (null != context.getTransition().getName() && context.getTransition().getName().equals("confirm")) {
			return new ConfirmedState(liveObject);
		} else {
			throw new StateChangeException("");
		}
	}

}
