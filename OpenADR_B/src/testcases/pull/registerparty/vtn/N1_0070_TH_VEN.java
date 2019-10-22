package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationConfValHelper;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.Direction;

public class N1_0070_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0070_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCancelPartyRegistration_IsRegistrationValid_Check(true);
		ConformanceRuleValidator.setDisableCanceledPartyRegistration_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisableResponseEvent_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		checkActiveRegistration();
		
		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			prompt(resources.prompt_006() + " VTN MUST be running before clicking Resume." );

			OadrCancelPartyRegistrationType cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			cancelPartyRegistration.setRegistrationID("INVALID_REGISTRATION_ID");
			sendCancelPartyRegistration(cancelPartyRegistration, ErrorConst.INVALID_ID_452);

			cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			cancelPartyRegistration.setVenID("INVALID_VEN_ID");
			sendCancelPartyRegistration(cancelPartyRegistration, ErrorConst.INVALID_ID_452);

			cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
			sendCancelPartyRegistration(cancelPartyRegistration);

			// OadrResponseType response = pollResponse();
			// checkResponseCode(response, ErrorConst.NOT_AUTHORIZED_463);
			
		} else {
			OadrCancelPartyRegistrationType cancelPartyRegistration = pollCancelPartyRegistration();
			boolean isRegistrationValid = CancelPartyRegistrationConfValHelper.isRegistrationValid(cancelPartyRegistration,Direction.Receive);
			if (isRegistrationValid) {
				throw new FailedException("Expecting an invalid registrationID");
			}
			sendCanceledPartyRegistration(cancelPartyRegistration, ErrorConst.INVALID_ID_452);

			cancelPartyRegistration = pollCancelPartyRegistration();
			sendCanceledPartyRegistration(cancelPartyRegistration);
		}
		
		alert(resources.prompt_005());
	}
}
