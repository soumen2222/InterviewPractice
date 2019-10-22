package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_001AndEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class E1_1040_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1040_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		ICreatedEventResult createdEvent = queueDistributeEvent(new DefaultDistributeEvent_001AndEvent_002Action());
		createdEvent.setExpectedOptInCount(2);

		addResponse().setlastEvent(true);
		addResponse();

		listenForRequests();

		waitForCompletion();
		
		waitForOptionalCreatedEvent();

		checkCreatedEventReceived(createdEvent);
		checkCreatedEvent_Ex040();
	}
}
