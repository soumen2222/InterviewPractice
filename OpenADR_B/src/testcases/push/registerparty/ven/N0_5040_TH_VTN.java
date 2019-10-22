package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCancelPartyRegistrationType;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N0_5040_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5040_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		prompt(resources.prompt_006());
		
		checkActiveRegistration();
		
		OadrCancelPartyRegistrationType cancelPartyRegistration = CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest();
		// cancelPartyRegistration.setVenID(null);
		sendCancelPartyRegistration(cancelPartyRegistration);
		
		alert(resources.prompt_005());	
	}
}
