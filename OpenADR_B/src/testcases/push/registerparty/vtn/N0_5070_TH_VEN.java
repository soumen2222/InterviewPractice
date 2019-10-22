package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N0_5070_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5070_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCancelPartyRegistration_IsRegistrationValid_Check(true);
		ConformanceRuleValidator.setDisableCanceledPartyRegistration_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);

		checkActiveRegistration();
		
		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			prompt(resources.prompt_006());
			
			OadrCancelPartyRegistrationType cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			cancelPartyRegistration.setRegistrationID("INVALID_REGISTRATION_ID");
			sendCancelPartyRegistration(cancelPartyRegistration, ErrorConst.INVALID_ID_452);

			cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			cancelPartyRegistration.setVenID("INVALID_VEN_ID");
			sendCancelPartyRegistration(cancelPartyRegistration, ErrorConst.INVALID_ID_452);

			cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			sendCancelPartyRegistration(cancelPartyRegistration);

			// cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			// sendCancelPartyRegistration(cancelPartyRegistration, ErrorConst.NOT_AUTHORIZED_463);
		} else {
			addCanceledPartyRegistrationResponse(ErrorConst.INVALID_ID_452);
			addCanceledPartyRegistrationResponse();
			// addCanceledPartyRegistrationResponse(ErrorConst.NOT_AUTHORIZED_463);

			listenForRequests();
			
			waitForCancelPartyRegistration(2);
		}
		
		alert(resources.prompt_005());
	}
}
