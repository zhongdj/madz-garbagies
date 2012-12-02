package net.madz.service.pr2invoice.entity;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.TransitionMethod;

@ReactiveObject
public interface IAccountRun {

	@TransitionMethod(name = "Process")
	void doProcess();

	@TransitionMethod(name = "Finish")
	void doFinish();
}