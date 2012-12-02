package samples;

import java.util.Date;

import net.madz.core.state.IObserver;
import net.madz.core.state.State;
import net.madz.core.state.StateContext;
import net.madz.infra.util.BizObjectUtil;
import samples.domain.ILadingRequest;
import samples.domain.LadingRequest;

public class Main {

	private static final class SimpleObserver implements IObserver<LadingRequest> {
		@Override
		public void update(LadingRequest bizObject, State<LadingRequest> currentState, StateContext<LadingRequest> context) {
			System.out.println(bizObject + " has been changed to " + currentState.getName());
		}
	}

	public static void main(String[] args) {
		LadingRequest bill = new LadingRequest();
		bill.setId(1L);
		bill.setStartDate(new Date());
		bill.setStateName("RequestedState");

		ILadingRequest bizProxy = BizObjectUtil.newProxy(bill);

		SimpleObserver observer = new SimpleObserver();
		// ObserverControl.getInstance().attach(LadingRequest.class,
		// LadingRequestState.ConfirmedState.toString(), observer);
		// ObserverControl.getInstance().attach(LadingRequest.class,
		// LadingRequestState.PickedState.toString(), observer);

		bizProxy.doConfirm();
		bizProxy.doPick();

		// ObserverControl.getInstance().detach(LadingRequest.class,
		// LadingRequestState.ConfirmedState.toString(), observer);
		// ObserverControl.getInstance().detach(LadingRequest.class,
		// LadingRequestState.PickedState.toString(), observer);

		bill = new LadingRequest();
		bill.setId(1L);
		bill.setStartDate(new Date());
		bill.setStateName("RequestedState");

		bizProxy = BizObjectUtil.newProxy(bill);
		bizProxy.doConfirm();
		bizProxy.doPick();

	}

}
