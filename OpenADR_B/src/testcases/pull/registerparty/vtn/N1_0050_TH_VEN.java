package testcases.pull.registerparty.vtn;


import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N1_0050_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0050_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisableOadrCreatePartyRegistration_isRegIDAndVENIDMatch_Check(true);
		
		prompt(resources.prompt_006());
		prompt(resources.prompt_008());

		pollRequestReregistration();

		sendResponse();

		sendCreatePartyRegistration(CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest());
	}
}
