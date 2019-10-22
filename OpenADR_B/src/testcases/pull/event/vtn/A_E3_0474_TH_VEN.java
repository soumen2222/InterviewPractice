package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
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

public class A_E3_0474_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0474_TH_VEN testClass = new A_E3_0474_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0474_UIUserPromptText(),
				1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		// Create OadrRequestEvent
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
		
		OadrDistributeEventType oadrDistributeEvent = DistributeEventSignalHelper
		.createOadrDistributeEventFromString(strOadrDistributeEventResponse);
			if (oadrDistributeEvent.getOadrEvent().size() != 1) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Expected OadrEvent were not received in OadrDistributeEvent");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}


		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(strOadrDistributeEventResponse);

		// Create CreatedEvent and Post to VTN.
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, true, null);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}

		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		String message = resourceFileReader
				.TestCase_0474_Second_UIUserPromptText();
		uiUserPrompt2.Prompt(message, 1);

		String responseCode2 = null;

		// User clicked Play
		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.addTrace("Test Case was cancelled by the user");
			LogHelper.setResult(LogResult.NA);
			return;
		}


		pause(5);
		String response2 = VenToVtnClient.poll(OadrDistributeEventType.class);; 
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		 isExpectedReceived = OadrUtil.isExpected(response2,OadrDistributeEventType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		// Create CreatedEvent and Post.
		OadrDistributeEventType oadrDistributeEvent2 = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(response2);
		if (oadrDistributeEvent2.getOadrEvent().size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrEvent were not received in OadrDistributeEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		ArrayList<String> distributeEventList2 = new ArrayList<String>();
		distributeEventList2.add(response2);


		String oadrCreatedEventStr2 = CreatedEventHelper.createCreatedEvent(
				distributeEventList2, true, false, null);
		String oadrResponse2 = "";
		if (oadrCreatedEventStr2 != null) {
			oadrResponse2 = VenToVtnClient.post(oadrCreatedEventStr2);
		}

		OadrResponseType oadrResponseObj2 = ResponseHelper
				.createOadrResponseFromString(oadrResponse2);
		responseCode2 = ResponseHelper.getOadrResponseCode(oadrResponseObj2);

		pause(5);

		// Fail TestCase if validation errors are present.
		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		LogHelper.addTrace("---DistributeEvent 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---DistributeEvent 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,1, oadrDistributeEvent2.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (responseCode2 != null && responseCode2.length() > 0) {
			LogHelper.addTrace("Received OadrResponseType 2 with responseCode "
					+ responseCode2);
		}
		if (responseCode2 != null
				&& responseCode2.equals("200")) {
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
