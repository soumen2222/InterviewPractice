package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.exception.ValidationException;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N1_0025_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0025_TH_VEN());
	}

	@Override
	public void test() throws Exception {
				
		prompt(resources.prompt_002());
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		createPartyRegistration.setRegistrationID(null);
		createPartyRegistration.setVenID("PREALLOCATED_VEN_ID");
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendCreatePartyRegistration(createPartyRegistration);
		
		if (!"PREALLOCATED_VEN_ID".equals(createdPartyRegistration.getVenID())) {
			throw new ValidationException("Expecting preallocated venID"); 
		}
		
		sendPoll(OadrRegisterReportType.class);
		
		sendRegisteredReport();
		
		sendRegisterReport(RegisterReportEventHelper.loadMetadata_001());
	}
}