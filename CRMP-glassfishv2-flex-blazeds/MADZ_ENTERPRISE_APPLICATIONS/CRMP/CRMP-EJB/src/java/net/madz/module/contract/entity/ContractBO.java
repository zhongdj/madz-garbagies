package net.madz.module.contract.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.madz.core.state.State;
import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.StateBuilder;
import net.madz.core.state.annotation.TransitionMethod;
import net.madz.module.contract.activeobject.IContractBO;
import net.madz.module.contract.state.ConfirmedState;
import net.madz.module.contract.state.DraftState;

@Entity
@Table(name = "crmp_contract", catalog = "crmp", schema = "")
@ReactiveObject
public class ContractBO extends Contract implements IContractBO {

	private static final long serialVersionUID = 1L;

	public ContractBO() {
		this.state = "DRAFT";
	}

	@StateBuilder
	public static State<IContractBO> newState(IContractBO contract, String stateName) {
		ContractStateEnum state = ContractStateEnum.valueOf(stateName);
		switch (state) {
		case DRAFT:
			return new DraftState(stateName, contract);
		case CONFIRMED:
			return new ConfirmedState(stateName, contract);
		default:
			throw new IllegalStateException("State: " + state + " is not supported yet.");
		}
	}

	@TransitionMethod(name = "Confirm")
	public void doConfirm() {
	}

	public static enum ContractStateEnum {
		DRAFT, CONFIRMED
	}

	public static enum ActionEnum {
		Confirm
	}
}
