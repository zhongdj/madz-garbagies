package net.madz.module.production.state;

import net.madz.core.state.FinalState;
import net.madz.module.production.activeobject.IProductionRecordBO;

public class BilledState extends FinalState<IProductionRecordBO> {

	public BilledState(String name, IProductionRecordBO bizObject) {
		super(name, bizObject);
	}

}
