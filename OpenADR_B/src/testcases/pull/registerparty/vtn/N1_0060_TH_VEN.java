package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class N1_0060_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0060_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_006());
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		createPartyRegistration.setRegistrationID(null);
		createPartyRegistration.setVenID(null);
		
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendCreatePartyRegistration(createPartyRegistration);
		checkRegistrationIdAndVenId(createdPartyRegistration);
		
		sendPoll(OadrRegisterReportType.class);
		
		sendRegisteredReport();
		
		sendRegisterReport(RegisterReportEventHelper.loadMetadata_001());
	}
}
