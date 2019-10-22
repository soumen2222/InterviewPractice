package testcases.pull.registerparty.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class N1_0080_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0080_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_ActiveRegistration_Check(true);
		
		prompt(resources.prompt_002());
		
		checkNoActiveRegistration();
		
		boolean yesClicked = promptYes(resources.prompt_061());
		if (yesClicked) {
			TestSession.setResponseExpected(false);
			
			pollResponseWithNoResponse();
		} else {
			throw new NotApplicableException("A pull VTN cannot initiate payloads, so this self test in not relevant.");
		}
	}

	private void pollResponseWithNoResponse() throws Exception {
		String poll = SchemaHelper.getOadrPollTypeAsString(PollEventSignalHelper.loadOadrPollType());
		String response = VenToVtnClient.post(poll, VTNServiceType.OadrPoll);
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
