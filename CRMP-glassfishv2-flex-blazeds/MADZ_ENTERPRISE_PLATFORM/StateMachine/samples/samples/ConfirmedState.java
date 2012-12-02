package samples;

import net.vicp.madz.infra.state.State;
import net.vicp.madz.infra.state.StateChangeException;
import net.vicp.madz.infra.state.StateContext;

public class ConfirmedState extends State<LadingRequest> {

	public ConfirmedState(String name, LadingRequest bizObject) {
		super(name, bizObject);
	}

	@Override
	public State<LadingRequest> stateChange(String transitionName, StateContext<LadingRequest> context)
			throws StateChangeException {
		if (null != transitionName && transitionName.equals("pick")) {
			return new PickedState("picked", bizObject);
		} else {
			throw new StateChangeException("");
		}
	}

}
