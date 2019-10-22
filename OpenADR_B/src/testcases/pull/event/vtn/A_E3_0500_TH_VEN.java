package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E3_0500_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0500_TH_VEN testClass = new A_E3_0500_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		new UIUserPrompt().Prompt(
				new ResourceFileReader().TestCase_0500_UIUserPromptText(), 1);
		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}


		String strOadrDistributeEventResponse = VenToVtnClient.poll(OadrDistributeEventType.class);; 
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		boolean isExpectedReceived = OadrUtil.isExpected(strOadrDistributeEventResponse,OadrDistributeEventType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		// Load OadrDistributeEventResponse received to OadrDistributeEvent
		// Object
		OadrDistributeEventType oadrDistributeEvent = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(strOadrDistributeEventResponse);

		if (oadrDistributeEvent.getOadrEvent().size() != 1) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace(oadrDistributeEvent.getOadrEvent().size()
					+ " Event(s) were received. Expected 1 Event");

			return;
		}

		// Create a ArrayList list of distribute event responses for which you
		// need to create created event.
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(strOadrDistributeEventResponse);

		// Create OadrCreatedEventType giving distributeEventList.
		String oadrCreatedEventStr1 = CreatedEventHelper.createCreatedEvent(
				distributeEventList, false, true, null);

		OadrCreatedEventType oadrCreatedEvent2 = CreatedEventHelper
				.createCreatedEventFromString(oadrCreatedEventStr1);
		oadrCreatedEvent2.getEiCreatedEvent().getEventResponses()
				.getEventResponse().get(0).setOptType(OptTypeType.OPT_OUT);

		String oadrCreatedEventStr2 = SchemaHelper
				.getCreatedEventAsString(oadrCreatedEvent2);

		// Post the CreatedEvent to VTN.
		String oadrResponse1 = "";
		if (oadrCreatedEventStr1 != null) {
			oadrResponse1 = VenToVtnClient.post(oadrCreatedEventStr1);
		}

		// Convert the oadrResponse received to OadrResponse.
		OadrResponseType oadrResponseObj1 = ResponseHelper
				.createOadrResponseFromString(oadrResponse1);

		// Get the response code
		String responseCode1 = ResponseHelper
				.getOadrResponseCode(oadrResponseObj1);

		pause(3);

		String oadrResponse2 = "";
		if (oadrCreatedEventStr2 != null) {
			oadrResponse2 = VenToVtnClient.post(oadrCreatedEventStr2);
		}
		// Get the response code
		OadrResponseType oadrResponseObj2 = ResponseHelper
				.createOadrResponseFromString(oadrResponse2);

		String responseCode2 = ResponseHelper
				.getOadrResponseCode(oadrResponseObj2);

		// Check if there were any errors in the payload and fail test case if
		// found.
		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent.getOadrEvent().get(0))){

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper
				.addTrace("Received OadrResponseType for first CreatedEvent with responseCode "
						+ responseCode1);

		LogHelper
				.addTrace("Received OadrResponseType for second CreatedEvent with responseCode "
						+ responseCode2);

		// Test Case condition
		if (responseCode1.equals("200") && responseCode2.equals("200")) {

			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Test Case has Passed");

		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
		}

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
