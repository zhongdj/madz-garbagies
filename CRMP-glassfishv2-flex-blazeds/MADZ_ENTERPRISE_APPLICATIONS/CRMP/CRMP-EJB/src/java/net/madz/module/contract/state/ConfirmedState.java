package net.madz.module.contract.state;

import net.madz.core.state.FinalState;
import net.madz.module.contract.activeobject.IContractBO;

public class ConfirmedState extends FinalState<IContractBO> {

	public ConfirmedState(String name, IContractBO liveObject) {
		super(name, liveObject);
	}
}
