package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;

public class N0_5065_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5065_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse();

		listenForRequests();

		prompt(resources.prompt_006());		
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendCreatePartyRegistration(createPartyRegistration);
		checkRegistrationIdAndVenId(createPartyRegistration, createdPartyRegistration);

		// sendRegisterReport(RegisterReportEventHelper.loadMetaTelemetary_001());
		
		pause(10);
	}
}
