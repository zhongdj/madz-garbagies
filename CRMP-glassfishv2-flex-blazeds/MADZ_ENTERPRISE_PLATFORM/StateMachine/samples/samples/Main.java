package samples;

import java.util.Date;

import net.vicp.madz.infra.state.IObserver;
import net.vicp.madz.infra.state.State;
import net.vicp.madz.infra.state.StateContext;
import net.vicp.madz.infra.state.management.StateChangeManager;
import net.vicp.madz.infra.util.BizObjectUtil;

public class Main {

	private static final class SimpleObserver implements IObserver<LadingRequest> {
		@Override
		public void update(LadingRequest bizObject,
				State<LadingRequest> currentState, StateContext<LadingRequest> context) {
			System.out.println(bizObject + " has been changed to "
					+ currentState.getName());
		}
	}

	public static void main(String[] args) {
		LadingRequest bill = new LadingRequest();
		bill.setId(1L);
		bill.setStartDate(new Date());
		bill.setStateName("requested");
		
		
		ILadingRequest bizProxy = BizObjectUtil.getBizProxy(bill);
		
		SimpleObserver observer = new SimpleObserver();
		StateChangeManager.getInstance().attach(LadingRequest.class, "confirmed",
				observer);
		StateChangeManager.getInstance().attach(LadingRequest.class, "picked",
				observer);

		bizProxy.doConfirm();
		bizProxy.doPick();
	}
}
