package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_RequestReregistrationAction;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class N0_5050_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5050_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		listenForRequests();
		
		IResponseCreatedPartyRegistrationAckAction createdPartyRegistration = addCreatedPartyRegistrationResponse();
		createdPartyRegistration.setlastEvent(true);
		
		prompt(resources.prompt_006());

		sendRequestReregistration(new Default_Pull_RequestReregistrationAction().getOadrRequestReregistration());

		waitForCompletion();

		checkCreatePartyRegistrationRequest(1);
	}
}
