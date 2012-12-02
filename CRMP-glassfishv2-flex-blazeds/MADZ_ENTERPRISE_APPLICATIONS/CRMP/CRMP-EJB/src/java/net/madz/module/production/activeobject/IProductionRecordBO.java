package net.madz.module.production.activeobject;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.TransitionMethod;

@ReactiveObject
public interface IProductionRecordBO {
	@TransitionMethod(name = "Confirm")
	public void doConfirm();

	@TransitionMethod(name = "Lose")
	public void doLose();
	
	@TransitionMethod(name = "Bill")
	public void doBill();
}
