package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.action.impl.DistEvent_002Modify10Min_SecondAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class E1_1030_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1030_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		ICreatedEventResult createdEvent1 = queueDistributeEvent(new DefaultDistributeEvent_002Action());
		ICreatedEventResult createdEvent2 = queueDistributeEvent(new DistEvent_002Modify10Min_SecondAction());

		addResponse();
		addResponse().setlastEvent(true);

		listenForRequests();
		
		waitForCompletion();
		
		checkCreatedEventReceived(createdEvent1);
		checkCreatedEventReceived(createdEvent2);
	}
}
