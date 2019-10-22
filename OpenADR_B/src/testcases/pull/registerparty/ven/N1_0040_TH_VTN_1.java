package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.action.impl.Default_Pull_CancelPartyRegistrationAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCanceledPartyRegistrationType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N1_0040_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0040_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		checkActiveRegistration();
		
		Default_Pull_CancelPartyRegistrationAction cancelPartyRegistration = new Default_Pull_CancelPartyRegistrationAction();
		// cancelPartyRegistration.setHasVenID(false);
		queueResponse(cancelPartyRegistration);

		addResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_006());

		waitForCompletion();

		checkCanceledPartyRegistration(1);
		
		OadrCanceledPartyRegistrationType canceledPartyRegistration = TestSession.getCanceledPartyRegistrationReceivedToList().get(0);
		checkRegistrationIdAndVenId(cancelPartyRegistration, canceledPartyRegistration);
		
		alert(resources.prompt_005());
	}
}
