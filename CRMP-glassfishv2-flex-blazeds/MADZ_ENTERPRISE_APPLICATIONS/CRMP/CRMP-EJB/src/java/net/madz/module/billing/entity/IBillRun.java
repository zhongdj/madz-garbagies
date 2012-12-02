package net.madz.module.billing.entity;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.TransitionMethod;

@ReactiveObject
public interface IBillRun {

	@TransitionMethod(name = "Process")
	void doProcess();

	@TransitionMethod(name = "Finish")
	void doFinish();
}