package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class N0_5025_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5025_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		addRegisteredReportResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_002());		
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		createPartyRegistration.setRegistrationID(null);
		createPartyRegistration.setVenID("PREALLOCATED_VEN_ID");
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendCreatePartyRegistration(createPartyRegistration);
		
		if (!"PREALLOCATED_VEN_ID".equals(createdPartyRegistration.getVenID())) {
			throw new ValidationException("Expecting preallocated venID"); 
		}
		
		sendRegisterReport(RegisterReportEventHelper.loadMetadata_001());

		waitForCompletion();
		checkRegisterReportRequest(1);
	}
}
