package testcases.push.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class N0_5080_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5080_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_ActiveRegistration_Check(true);
		
		prompt(resources.prompt_002());
		
		checkNoActiveRegistration();
		
		boolean yesClicked = promptYes(resources.prompt_061());
		if (yesClicked) {
			TestSession.setResponseExpected(false);
			
			sendCreateOptWithNoResponse(getCreateOptSchedule_001());
		} else {
			// listenForRequests();
			
			waitForCompletion(20 * 1000);
			
			throw new NotApplicableException("Selftest placeholder");
		}
	}

	private void sendCreateOptWithNoResponse(OadrCreateOptType createOpt) throws Exception {
		String text = SchemaHelper.getOadrCreateOptAsString(createOpt);
		String response = VenToVtnClient.post(text, VTNServiceType.EiOpt);
		if (response != null) {
			Class objectType = null;
			try {
				objectType = SchemaHelper.getObjectType(response);
			} catch (Exception e) {
				// ignore 
			}
			
			if (objectType != null) {
				throw new FailedException("Expected no response from the DUT.");
			}
		}
	}
}
