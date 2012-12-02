package net.madz.module.billing.state;

import net.madz.core.state.FinalState;
import net.madz.module.billing.activeobject.IInvoiceBO;

public class PaidOffState extends FinalState<IInvoiceBO> {

	public PaidOffState(String name, IInvoiceBO bizObject) {
		super(name, bizObject);
	}

}
