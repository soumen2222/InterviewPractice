package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_004Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class E1_1025_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1025_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		IDistributeEventAction distributeEvent = new DefaultDistributeEvent_004Action();
		ICreatedEventResult createdEvent = queueDistributeEvent(distributeEvent);
		createdEvent.setLastCreatedEventResult(true);
		
		addResponse();

		listenForRequests();

		waitForCompletion();

		checkCreatedEventReceived(createdEvent);
	}
}
