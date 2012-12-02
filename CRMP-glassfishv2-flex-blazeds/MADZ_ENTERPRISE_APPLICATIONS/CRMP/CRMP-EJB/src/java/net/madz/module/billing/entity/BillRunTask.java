package net.madz.module.billing.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.madz.core.state.FinalState;
import net.madz.core.state.State;
import net.madz.core.state.StateChangeException;
import net.madz.core.state.StateContext;
import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.StateBuilder;
import net.madz.core.state.annotation.TransitionMethod;
import net.madz.core.util.Log;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISC", length = 20, discriminatorType = DiscriminatorType.STRING)
@ReactiveObject
public abstract class BillRunTask extends BillRunTaskBase implements IBillRun {

	private static final long serialVersionUID = 6022395408618397500L;

	public BillRunTask() {
		this.state = StateEnum.NEW.name();
	}

	@TransitionMethod(name = "Process")
	public void doProcess() {

	}

	@TransitionMethod(name = "Finish")
	public void doFinish() {

	}

	@StateBuilder
	public static State<IBillRun> newState(IBillRun invoice, String stateName) {
		StateEnum state = StateEnum.valueOf(stateName);
		switch (state) {
		case NEW:
			return new NewState(stateName, invoice);
		case PROCESSING:
			return new ProcessingState(stateName, invoice);
		case FINISHED:
			return new FinishedState(stateName, invoice);
		default:
			throw new IllegalStateException("State: " + state + " is not supported yet.");
		}
	}

	public static enum StateEnum {
		NEW, PROCESSING, FINISHED
	}

	public static enum ActionEnum {
		Process, Finish
	}

	public static class NewState extends State<IBillRun> {

		public NewState(String name, IBillRun liveObject) {
			super(name, liveObject);
		}

		@Override
		public State<IBillRun> doStateChange(StateContext<IBillRun> context) throws StateChangeException {
			String actionName = context.getTransition().getName();
			Log.info("", "BillRunTask's State: " + stateName + " is invoked by " + actionName);
			ActionEnum action = ActionEnum.valueOf(actionName);
			switch (action) {
			case Process:
				return BillRunTask.newState(context.getLiveObject(), StateEnum.PROCESSING.name().toString());
			}

			return invalidStateChange(context);
		}

	}

	public static class ProcessingState extends State<IBillRun> {

		public ProcessingState(String name, IBillRun liveObject) {
			super(name, liveObject);
		}

		@Override
		public State<IBillRun> doStateChange(StateContext<IBillRun> context) throws StateChangeException {
			String actionName = context.getTransition().getName();
			Log.info("", "BillRunTask's State: " + stateName + " is invoked by " + actionName);
			ActionEnum action = ActionEnum.valueOf(actionName);
			switch (action) {
			case Finish:
				return BillRunTask.newState(context.getLiveObject(), StateEnum.FINISHED.name().toString());
			}

			return invalidStateChange(context);
		}

	}

	public static class FinishedState extends FinalState<IBillRun> {

		public FinishedState(String name, IBillRun bizObject) {
			super(name, bizObject);
		}

	}
}
