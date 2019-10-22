package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;

public class N0_5040_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5040_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		addCanceledPartyRegistrationResponse().setlastEvent(true);
		
		listenForRequests();
	
		prompt(resources.prompt_006());
		prompt(resources.prompt_007());
		
		pauseForTestCaseTimeout();
		
		checkCancelPartyRegistrationRequest(1);

		alert(resources.prompt_005());
	}
}
