package net.madz.module.billing.state;

import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import net.madz.core.util.Log;
import net.madz.module.billing.activeobject.IInvoiceBO;
import net.madz.module.billing.entity.InvoiceBO;

public class PostedState extends State<IInvoiceBO> {

	public PostedState(String name, IInvoiceBO liveObject) {
		super(name, liveObject);
	}

	@Override
	public State<IInvoiceBO> doStateChange(StateContext<IInvoiceBO> context) throws StateChangeException {
		String actionName = context.getTransition().getName();
		Log.info("", "Invoice's State: " + stateName + " is invoked by " + actionName);
		final InvoiceBO.ActionEnum action = InvoiceBO.ActionEnum.valueOf(actionName);
		Object[] args = context.getArgs();
		assert args.length == 1;
		assert args[0] != null;

		final double payAmount = (Double) args[0];
		switch (action) {
		case Pay:
			if (liveObject.getUnpaidAmount() > payAmount) {
				return InvoiceBO.newState(context.getLiveObject(), InvoiceBO.InvoiceStateEnum.PARTIALPAIED.name().toString());
			} else {
				return InvoiceBO.newState(context.getLiveObject(), InvoiceBO.InvoiceStateEnum.PAIEDOFF.name().toString());
			}
		}

		return invalidStateChange(context);
	}
}
