package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.channel.util.StringUtil;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreatedPartyRegistrationType;

public class N0_5010_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5010_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkNoActiveRegistration();
		
		prompt(resources.prompt_002());	
		
		OadrCreatedPartyRegistrationType createdPartyRegistration = sendQueryRegistration();
		
		String registrationID = createdPartyRegistration.getRegistrationID();
		if (!StringUtil.isBlank(registrationID)) {
			throw new FailedException("Expected no registrationID in OadrCreatedPartyRegistration, but received " + registrationID);
		}
		
		String venID = createdPartyRegistration.getVenID();
		if (!StringUtil.isBlank(venID)) {
			throw new FailedException("Expected no venID in OadrCreatedPartyRegistration, but received " + venID);
		}
	}
}
