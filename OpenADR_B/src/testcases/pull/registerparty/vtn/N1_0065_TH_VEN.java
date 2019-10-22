package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;

public class N1_0065_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0065_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_006());
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendCreatePartyRegistration(createPartyRegistration);
		checkRegistrationIdAndVenId(createPartyRegistration, createdPartyRegistration);
		
		// sendPoll(OadrRegisterReportType.class);
		// sendRegisteredReport();
		
		// sendRegisterReport(RegisterReportEventHelper.loadMetaTelemetary_001());
	}
}
