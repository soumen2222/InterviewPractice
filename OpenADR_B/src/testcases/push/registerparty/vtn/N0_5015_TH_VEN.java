package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;

public class N0_5015_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5015_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_006());	
		
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendQueryRegistration();
		
		checkIDs_Nx015(createdPartyRegistration);
	}
}
