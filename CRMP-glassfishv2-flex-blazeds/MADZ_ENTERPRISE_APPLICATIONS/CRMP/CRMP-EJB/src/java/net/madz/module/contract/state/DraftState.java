package net.madz.module.contract.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import net.madz.core.util.Log;
import net.madz.module.contract.activeobject.IContractBO;
import net.madz.module.contract.entity.ContractBO;

public class DraftState extends State<IContractBO> {

	public DraftState(String name, IContractBO liveObject) {
		super(name, liveObject);
	}

	@Override
	public State<IContractBO> doStateChange(StateContext<IContractBO> context) throws StateChangeException {
		String actionName = context.getTransition().getName();
		Log.info("", "Contract's State: " + stateName + " is invoked by " + actionName);
		ContractBO.ActionEnum action = ContractBO.ActionEnum.valueOf(actionName);
		switch (action) {
		case Confirm:
			return ContractBO.newState(context.getLiveObject(), ContractBO.ContractStateEnum.CONFIRMED.name().toString());
		}

		return invalidStateChange(context);
	}

}
