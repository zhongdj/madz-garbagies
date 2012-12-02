package net.madz.module.production.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.madz.core.state.State;
import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.StateBuilder;
import net.madz.core.state.annotation.TransitionMethod;
import net.madz.module.production.activeobject.IProductionRecordBO;
import net.madz.module.production.state.BilledState;
import net.madz.module.production.state.ConfirmedState;
import net.madz.module.production.state.DraftState;
import net.madz.module.production.state.LostState;

@Entity
@Table(name = "crmp_production_record", catalog = "crmp", schema = "")
@ReactiveObject
public class ProductionRecordBO extends ProductionRecord implements IProductionRecordBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1155719800455357033L;

	public ProductionRecordBO() {
		this.state = ProductionRecordStateEnum.DRAFT.name();
	}

	@StateBuilder
	public static State<IProductionRecordBO> newState(IProductionRecordBO productionRecord, String stateName) {
		ProductionRecordStateEnum state = ProductionRecordStateEnum.valueOf(stateName);
		switch (state) {
		case DRAFT:
			return new DraftState(stateName, productionRecord);
		case LOST:
			return new LostState(stateName, productionRecord);
		case CONFIRMED:
			return new ConfirmedState(stateName, productionRecord);
		case BILLED:
			return new BilledState(stateName, productionRecord);
		default:
			throw new IllegalStateException("State: " + state + " is not supported yet.");
		}
	}

	@TransitionMethod(name = "Confirm")
	public void doConfirm() {
	}

	@TransitionMethod(name = "Bill")
	public void doBill() {
	}

	@TransitionMethod(name = "Lose")
	public void doLose() {
	}

	public static enum ProductionRecordStateEnum {
		DRAFT, LOST, CONFIRMED, BILLED
	}

	public static enum ActionEnum {
		Confirm, Bill, Lose
	}
}
