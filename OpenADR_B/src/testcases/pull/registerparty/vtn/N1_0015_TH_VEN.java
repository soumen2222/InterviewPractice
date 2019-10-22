package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;

public class N1_0015_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0015_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_006());	
		
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendQueryRegistration();
		
		checkIDs_Nx015(createdPartyRegistration);
	}
}
