package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.action.impl.Default_Pull_CancelPartyRegistrationAction;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N1_0070_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0070_TH_VTN_1());
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
			Default_Pull_CancelPartyRegistrationAction cancelPartyRegistration = new Default_Pull_CancelPartyRegistrationAction();
			cancelPartyRegistration.setRegistrationID("INVALID_REGISTRATION_ID");
			queueResponse(cancelPartyRegistration);
			addResponse();

			queueResponse(new Default_Pull_CancelPartyRegistrationAction());
			addResponse();

			queueResponse(new Default_Pull_CancelPartyRegistrationAction());
			addResponse();

			listenForRequests();

			prompt(resources.prompt_006());

			waitForCanceledPartyRegistration(1, ErrorConst.INVALID_ID_452);
			waitForCanceledPartyRegistration(2, ErrorConst.OK_200);
		} else {
			addCanceledPartyRegistrationResponse(ErrorConst.INVALID_ID_452);
			addCanceledPartyRegistrationResponse(ErrorConst.INVALID_ID_452);
			addCanceledPartyRegistrationResponse().setlastEvent(true);
			
			// IResponseEventAction response = new Default_ResponseEventAction(ErrorConst.NOT_AUTHORIZED_463);
			// response.setlastEvent(true);
			// queueResponse(response);
			
			listenForRequests();
			
			waitForCompletion();
		}
		
		alert(resources.prompt_005());
	}
}
