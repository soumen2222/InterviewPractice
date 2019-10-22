package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class N0_5020_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5020_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		addRegisteredReportResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_002());		
		
		// sendQueryRegistration();
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		createPartyRegistration.setRegistrationID(null);
		createPartyRegistration.setVenID(null);
		sendCreatePartyRegistration(createPartyRegistration);
		
		pause(1);
		
		sendRegisterReport(RegisterReportEventHelper.loadMetadata_001());

		waitForCompletion();
		checkRegisterReportRequest(1);
	}
}
