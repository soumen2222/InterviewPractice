package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_001AndEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class E0_6040_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6040_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		listenForRequests();

		addResponse().setlastEvent(true);
		addResponse();
		
		ICreatedEventResult createdEvent = sendDistributeEvent(new DefaultDistributeEvent_001AndEvent_002Action());
		createdEvent.setExpectedOptInCount(2);

		waitForCompletion();
		
		waitForOptionalCreatedEvent();

		checkCreatedEventReceived(createdEvent);
		checkCreatedEvent_Ex040();
	}
}
