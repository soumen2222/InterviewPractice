package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.action.ResponseCreatedPartyRegistrationToQueryAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultCreatedPartyRegistration;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrQueryRegistrationType;

public class N1_0015_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0015_TH_VTN_1());
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
		
		boolean yesClicked = promptYes(resources.prompt_034());
		if (!yesClicked) {
			throw new FailedException("The DUT VEN is not polling at the interval requested as part of registration");
		}
		
		yesClicked = promptYes(resources.prompt_033());
		if (!yesClicked) {
			throw new FailedException("Each of the registration characteristic in the VEN's oadrCreatePartyRegistration payload does not match the actual configuration of the device");
		}
	}
}
