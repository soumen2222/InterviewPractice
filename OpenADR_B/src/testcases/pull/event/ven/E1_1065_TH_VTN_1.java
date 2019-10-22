package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.exception.NotApplicableException;

public class E1_1065_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1065_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			IDistributeEventAction distributeEvent = new DefaultDistributeEvent_002Action();
			ICreatedEventResult createdEvent = queueDistributeEvent(distributeEvent);
			createdEvent.setExpectedCreateOpt_OptOutCount(1);
						
			addResponse();
			addCreatedOptResponse();
			addResponse().setlastEvent(true);
			
			listenForRequests();
			
			waitForCompletion();
		}
	}
}
