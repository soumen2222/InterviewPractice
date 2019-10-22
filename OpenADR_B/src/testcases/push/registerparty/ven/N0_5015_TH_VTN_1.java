package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.action.ResponseCreatedPartyRegistrationToQueryAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultCreatedPartyRegistration;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;

public class N0_5015_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5015_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		DefaultCreatedPartyRegistration createdPartyRegistration = new DefaultCreatedPartyRegistration(OadrQueryRegistrationType.class);
		createdPartyRegistration.setRequiredOnly(true);
		createdPartyRegistration.setlastEvent(true);
		ResponseCreatedPartyRegistrationToQueryAckActionList.addResponsePartyRegistrationAckAction(createdPartyRegistration);
	
		listenForRequests();

		prompt(resources.prompt_006());
		
		prompt(resources.prompt_001());
		
		waitForCompletion();

		checkQueryRegistrationRequest(1);

		boolean yesClicked = promptYes(resources.prompt_033());
		if (!yesClicked) {
			throw new FailedException("Each of the registration characteristic in the VEN's oadrCreatePartyRegistration payload does not match the actual configuration of the device");
		}
	}
}
