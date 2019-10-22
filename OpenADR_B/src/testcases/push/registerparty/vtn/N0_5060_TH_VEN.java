package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class N0_5060_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5060_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		addRegisteredReportResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_006());		
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		createPartyRegistration.setRegistrationID(null);
		createPartyRegistration.setVenID(null);
		
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendCreatePartyRegistration(createPartyRegistration);
		checkRegistrationIdAndVenId(createdPartyRegistration);

		sendRegisterReport(RegisterReportEventHelper.loadMetadata_001());

		waitForCompletion();
		checkRegisterReportRequest(1);
	}
}
