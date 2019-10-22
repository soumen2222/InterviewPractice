package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class N0_5065_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5065_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_006());

		IResponseCreatedPartyRegistrationAckAction createdPartyRegistration = addCreatedPartyRegistrationResponse();
		createdPartyRegistration.setlastEvent(true);
		createdPartyRegistration.setReRegistration(true);
		
		addRegisteredReportResponse();

		listenForRequests();
		
		prompt(resources.prompt_049());
		
		waitForCompletion();
		
		// sendRegisterReport(RegisterReportEventHelper.loadMetaTelemetary_003());

		pause(10);
	}
}
