package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatePartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CreatePartyRegistrationReqSignalHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N0_5050_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5050_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisableOadrCreatePartyRegistration_isRegIDAndVENIDMatch_Check(true);
		
		addResponse();

		listenForRequests();

		prompt(resources.prompt_006());
		prompt(resources.prompt_008());

		waitForRequestReregistration(1);
		
		OadrCreatePartyRegistrationType createPartyRegistration = CreatePartyRegistrationReqSignalHelper.loadCreatePartyRegistrationRequest();
		toggleTrailingSlash(createPartyRegistration);
		sendCreatePartyRegistration(createPartyRegistration);
	}

	private void toggleTrailingSlash(OadrCreatePartyRegistrationType createPartyRegistration) {
		String transportAddress = createPartyRegistration.getOadrTransportAddress().trim();
		if (transportAddress.endsWith("/")) {
			transportAddress = transportAddress.substring(0, transportAddress.length() - 1);
		} else {
			transportAddress += "/";
		}
		createPartyRegistration.setOadrTransportAddress(transportAddress);
	}
}
