package net.madz.module.billing.activeobject;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.TransitionMethod;

@ReactiveObject
public interface IInvoiceBO {
	@TransitionMethod(name = "Confirm")
	public void doConfirm();

	@TransitionMethod(name = "Bill")
	public void doBill();

	@TransitionMethod(name = "Post")
	public void doPost();

	@TransitionMethod(name = "Pay")
	public void doPay(double payAmount);

	public double getUnpaidAmount();
}
