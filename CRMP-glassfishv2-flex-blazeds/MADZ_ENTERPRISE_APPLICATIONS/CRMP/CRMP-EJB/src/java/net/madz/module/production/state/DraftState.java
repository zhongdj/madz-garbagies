package net.madz.module.production.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import net.madz.core.util.Log;
import net.madz.module.production.activeobject.IProductionRecordBO;
import net.madz.module.production.entity.ProductionRecordBO;

public class DraftState extends State<IProductionRecordBO> {

	public DraftState(String name, IProductionRecordBO liveObject) {
		super(name, liveObject);
	}

	@Override
	public State<IProductionRecordBO> doStateChange(StateContext<IProductionRecordBO> context) throws StateChangeException {
		final String actionName = context.getTransition().getName();
		Log.info("", "ProductionRecord's State: " + stateName + " is invoked by " + actionName);
		final ProductionRecordBO.ActionEnum action = ProductionRecordBO.ActionEnum.valueOf(actionName);
		switch (action) {
		case Confirm:
			return ProductionRecordBO.newState(context.getLiveObject(), ProductionRecordBO.ProductionRecordStateEnum.CONFIRMED.name()
					.toString());
		case Lose:
			return ProductionRecordBO
					.newState(context.getLiveObject(), ProductionRecordBO.ProductionRecordStateEnum.LOST.name().toString());
		}

		return invalidStateChange(context);
	}

}
