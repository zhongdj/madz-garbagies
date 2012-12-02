package samples.state;

import net.madz.core.state.FinalState;
import samples.domain.LadingRequest;

public class PickedState extends FinalState<LadingRequest> {

	public PickedState(LadingRequest bizObject) {
		super(LadingRequestState.PickedState.toString(), bizObject);
	}

}
