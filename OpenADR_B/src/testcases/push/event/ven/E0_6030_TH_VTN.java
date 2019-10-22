package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.action.impl.DistEvent_002Modify10Min_SecondAction;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E0_6030_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6030_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
				
		addResponse();
		addResponse().setlastEvent(true);
		
		listenForRequests();

		ICreatedEventResult createdEvent1 = sendDistributeEvent(new DefaultDistributeEvent_002Action());
		waitForCreatedEvent(createdEvent1);

		ICreatedEventResult createdEvent2 = sendDistributeEvent(new DistEvent_002Modify10Min_SecondAction());
		createdEvent2.setLastCreatedEventResult(true);
		
		waitForCompletion();

		checkCreatedEventReceived(createdEvent1);
		checkCreatedEventReceived(createdEvent2);
	}
}
