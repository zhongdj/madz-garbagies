package net.madz.module.billing.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import net.madz.core.util.Log;
import net.madz.module.billing.activeobject.IInvoiceBO;
import net.madz.module.billing.entity.InvoiceBO;

public class BilledState extends State<IInvoiceBO> {

	public BilledState(String name, IInvoiceBO liveObject) {
		super(name, liveObject);
	}

	@Override
	public State<IInvoiceBO> doStateChange(StateContext<IInvoiceBO> context) throws StateChangeException {
		String actionName = context.getTransition().getName();
		Log.info("", "Invoice's State: " + stateName + " is invoked by " + actionName);
		final InvoiceBO.ActionEnum action = InvoiceBO.ActionEnum.valueOf(actionName);

		switch (action) {
		case Post:
			return InvoiceBO.newState(context.getLiveObject(), InvoiceBO.InvoiceStateEnum.POSTED.name().toString());
		}

		return invalidStateChange(context);
	}

}
