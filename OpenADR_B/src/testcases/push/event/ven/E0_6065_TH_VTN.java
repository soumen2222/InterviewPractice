package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.exception.NotApplicableException;

public class E0_6065_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6065_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			addResponse();
			addCreatedOptResponse();
			addResponse().setlastEvent(true);
			
			listenForRequests();

			sendDistributeEvent(new DefaultDistributeEvent_002Action());

			waitForCompletion();
		}
	}
}
