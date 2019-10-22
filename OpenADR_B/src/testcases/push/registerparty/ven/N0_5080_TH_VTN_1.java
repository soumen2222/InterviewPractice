package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class N0_5080_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5080_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_ActiveRegistration_Check(true);
		
		prompt(resources.prompt_002());
		
		checkNoActiveRegistration();
		
		boolean yesClicked = promptYes(resources.prompt_061());
		if (yesClicked) {
			TestSession.setResponseExpected(false);
		
			sendRegisterReportWithNoResponse(RegisterReportEventHelper.loadMetadata_003());
		} else {
			// listenForRequests();
			
			waitForCompletion(15 * 1000);
			
			throw new NotApplicableException("Selftest placeholder");
		}
	}

	private void sendRegisterReportWithNoResponse(OadrRegisterReportType registerReport) throws Exception {
		String text = SchemaHelper.getRegisterReportTypeAsString(registerReport);
		String response = VtnToVenClient.post(text);
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
