package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class E1_1060_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1060_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		IDistributeEventAction distributeEvent = new DefaultDistributeEvent_002Action();
		ICreatedEventResult createdEvent = queueDistributeEvent(distributeEvent);
		createdEvent.setExpectedCreateOpt_OptOutCount(1);
					
		addResponse();
		addCreatedOptResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCreatedEvent(1);

		prompt(resources.prompt_013());
		
		waitForCompletion();

		checkCreatedEventReceived(createdEvent);
	}
}
