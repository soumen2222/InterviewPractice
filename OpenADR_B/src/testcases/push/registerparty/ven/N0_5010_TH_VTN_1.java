package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class N0_5010_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5010_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkNoActiveRegistration();
		
		prompt(resources.prompt_002());
		
		addCreatedPartyRegistrationResponseToQuery().setlastEvent(true);
	
		listenForRequests();

		prompt(resources.prompt_001());
		
		waitForCompletion();

		checkQueryRegistrationRequest(1);
	}
}
