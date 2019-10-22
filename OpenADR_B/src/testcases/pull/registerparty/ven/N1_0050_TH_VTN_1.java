package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.action.IRequestReregistrationAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_RequestReregistrationAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class N1_0050_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0050_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		IRequestReregistrationAction requestReregistration = new Default_Pull_RequestReregistrationAction();
		queueResponse(requestReregistration);
		
		IResponseCreatedPartyRegistrationAckAction createdPartyRegistration = addCreatedPartyRegistrationResponse();
		createdPartyRegistration.setlastEvent(true);
		
		listenForRequests();

		prompt(resources.prompt_006());

		waitForCompletion();
		
		checkRequestRegistration(requestReregistration);
		checkCreatePartyRegistrationRequest(1);
	}
}
