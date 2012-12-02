package net.madz.module.billing.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.madz.core.state.State;
import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.StateBuilder;
import net.madz.core.state.annotation.TransitionMethod;
import net.madz.module.billing.activeobject.IInvoiceBO;
import net.madz.module.billing.state.BilledState;
import net.madz.module.billing.state.ConfirmedState;
import net.madz.module.billing.state.DraftState;
import net.madz.module.billing.state.PaidOffState;
import net.madz.module.billing.state.PartialPaiedState;
import net.madz.module.billing.state.PostedState;

@Entity
@Table(name = "crmp_invoice", catalog = "crmp", schema = "")
@ReactiveObject
public class InvoiceBO extends Invoice implements IInvoiceBO {

	private static final long serialVersionUID = 4615307327639574737L;

	public InvoiceBO() {
		// Initiate a new invoice with draft state.
		this.state = "DRAFT";
	}

	@StateBuilder
	public static State<IInvoiceBO> newState(IInvoiceBO invoice, String stateName) {
		InvoiceStateEnum state = InvoiceStateEnum.valueOf(stateName);
		switch (state) {
		case DRAFT:
			return new DraftState(stateName, invoice);
		case CONFIRMED:
			return new ConfirmedState(stateName, invoice);
		case BILLED:
			return new BilledState(stateName, invoice);
		case POSTED:
			return new PostedState(stateName, invoice);
		case PARTIALPAIED:
			return new PartialPaiedState(stateName, invoice);
		case PAIEDOFF:
			return new PaidOffState(stateName, invoice);
		default:
			throw new IllegalStateException("State: " + state + " is not supported yet.");
		}
	}

	@TransitionMethod(name = "Confirm")
	public void doConfirm() {
	}

	// For bill run
	@TransitionMethod(name = "Bill")
	public void doBill() {
	}

	@TransitionMethod(name = "Post")
	public void doPost() {
	}

	@TransitionMethod(name = "Pay")
	public void doPay(double payAmount) {
		this.unpaidAmount -= payAmount;
	}

	public static enum InvoiceStateEnum {
		DRAFT, CONFIRMED, BILLED, POSTED, PARTIALPAIED, PAIEDOFF
	}

	public static enum ActionEnum {
		Confirm, Bill, Post, Pay
	}
}
