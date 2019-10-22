package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class E0_6020_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6020_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		addResponse();
		
		listenForRequests();

		ICreatedEventResult createdEvent = sendDistributeEvent(new DefaultDistributeEvent_002Action());
		createdEvent.setExpectedCreateOpt_OptOutCount(0);
		createdEvent.setLastCreatedEventResult(true);

		waitForCompletion();
		
		checkCreatedEventReceived(createdEvent);
	}
}
