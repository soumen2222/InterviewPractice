package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.helper.CancelPartyRegistrationHelper;

public class N0_5030_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5030_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_006());

		sendCancelPartyRegistration(CancelPartyRegistrationHelper.loadOadrCancelPartyRegistrationRequest());
		
		alert(resources.prompt_005());
	}
}
