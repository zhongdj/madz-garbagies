package net.madz.module.contract.activeobject;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.TransitionMethod;

@ReactiveObject
public interface IContractBO {
	@TransitionMethod(name = "Confirm")
	public void doConfirm();
}
