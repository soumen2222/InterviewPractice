package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class N0_5030_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5030_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_006());
		
		addCanceledPartyRegistrationResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_004());

		waitForCompletion();

		checkCancelPartyRegistrationRequest(1);

		alert(resources.prompt_005());
	}
}
