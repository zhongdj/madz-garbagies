package samples;

import net.vicp.madz.infra.state.FinalState;

public class PickedState extends FinalState<LadingRequest> {

	public PickedState(String name, LadingRequest bizObject) {
		super(name, bizObject);
	}

}
