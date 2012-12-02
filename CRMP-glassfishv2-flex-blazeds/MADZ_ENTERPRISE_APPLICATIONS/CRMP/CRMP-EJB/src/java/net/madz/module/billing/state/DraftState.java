package net.madz.module.billing.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import net.madz.core.util.Log;
import net.madz.module.billing.activeobject.IInvoiceBO;
import net.madz.module.billing.entity.InvoiceBO;

public class DraftState extends State<IInvoiceBO> {

	public DraftState(String name, IInvoiceBO liveObject) {
		super(name, liveObject);
	}

	@Override
	public State<IInvoiceBO> doStateChange(StateContext<IInvoiceBO> context) throws StateChangeException {
		String actionName = context.getTransition().getName();
		Log.info("", "Invoice's State: " + stateName + " is invoked by " + actionName);
		InvoiceBO.ActionEnum action = InvoiceBO.ActionEnum.valueOf(actionName);
		switch (action) {
		case Confirm:
			return InvoiceBO.newState(context.getLiveObject(), InvoiceBO.InvoiceStateEnum.CONFIRMED.name().toString());
		}

		return invalidStateChange(context);
	}

}
