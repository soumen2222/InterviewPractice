package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class N0_5060_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5060_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_006());

		addCreatedPartyRegistrationResponse().setNewRegistration(true);
		
		addRegisteredReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_035());
		
		waitForCreatePartyRegistration(1);
		
		sendRegisterReport(RegisterReportEventHelper.loadMetadata_003());
		
		waitForCompletion();
		checkRegisterReportRequest(1);
	}
}
