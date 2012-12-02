package samples;

import net.vicp.madz.infra.state.State;
import net.vicp.madz.infra.state.StateChangeException;
import net.vicp.madz.infra.state.StateContext;

public class RequestedState extends State<LadingRequest> {

	public RequestedState(String name, LadingRequest bizObject) {
		super(name, bizObject);
	}

	@Override
	public State<LadingRequest> stateChange(String transitionName, StateContext<LadingRequest> context)
			throws StateChangeException {
		if (null != transitionName && transitionName.equals("confirm")) {
			return new ConfirmedState("confirmed", bizObject);
		} else {
			throw new StateChangeException("");
		}

	}

}
