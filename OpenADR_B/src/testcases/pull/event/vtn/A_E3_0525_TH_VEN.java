package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
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

public class A_E3_0525_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0525_TH_VEN testClass = new A_E3_0525_TH_VEN();
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
				+ "\n\n" + resourceFileReader.TestCase_0525_UIUserPromptTex(),
				2);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}
		long modificationNumber2 = -1;

		String response = VenToVtnClient.poll(OadrDistributeEventType.class);; 
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		boolean isExpectedReceived = OadrUtil.isExpected(response,OadrDistributeEventType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		OadrDistributeEventType oadrDistributeEvent1 = DistributeEventSignalHelper
		.createOadrDistributeEventFromString(response);

		if (oadrDistributeEvent1.getOadrEvent().size() != 2) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Expected OadrEvent were not received in OadrDistributeEvent");
					LogHelper.addTrace("Test Case has Failed");
					return;
		}

		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(response);

		// Create CreatedEvent and Post to VTN.
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, true, null);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}

		// Prompt
		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		String message2 = resourceFileReader
				.TestCase_0525__Second_UIUserPromptText();
		uiUserPrompt2.Prompt(message2, 1);

		String responseCode2 = null;

		// User clicked Play
		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.addTrace("Test Case was cancelled by the user");
			LogHelper.setResult(LogResult.NA);
			return;
		}


		// Convert OadrRequestEventType Object to String and post it to VTN
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
		
		OadrDistributeEventType oadrDistributeEvent2 = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(response2);
		if (oadrDistributeEvent2.getOadrEvent().size() != 2) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Expected OadrEvent were not received in OadrDistributeEvent");
					LogHelper.addTrace("Test Case has Failed");
					return;
		}
		
		ArrayList<String> distributeEventList2 = new ArrayList<String>();
		distributeEventList2.add(response2);

		if (oadrDistributeEvent2 != null) {
			modificationNumber2 = DistributeEventSignalHelper
					.getFirstEventModificationNumber(oadrDistributeEvent2);
			String activeEventIDOadrDist1 = oadrDistributeEvent1.getOadrEvent()
					.get(0).getEiEvent().getEventDescriptor().getEventID();
			EventStatusEnumeratedType activeEventStatus = oadrDistributeEvent1
					.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
					.getEventStatus();
			String cancelledEventIDOadrDist2 = oadrDistributeEvent2
					.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
					.getEventID();
			EventStatusEnumeratedType cancelledEventStatus = oadrDistributeEvent2
					.getOadrEvent().get(0).getEiEvent().getEventDescriptor()
					.getEventStatus();

			if (!activeEventIDOadrDist1.equals(cancelledEventIDOadrDist2)) {
				LogHelper
						.addTrace("Did not receive the same first event in the initial and modified OadrDistributeEvent");

				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			} else {
				LogHelper
						.addTrace("Receive the same first event in the initial and modified OadrDistributeEvent");

			}

			if (!activeEventStatus.equals(EventStatusEnumeratedType.ACTIVE)) {
				LogHelper
						.addTrace("The first event in the initial OadrDistributeEventType was expected to be ACTIVE "
								+ activeEventStatus);

				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			} else {
				LogHelper
						.addTrace("Received first ACTIVE event in initial OadrDistributeEventType as expected");

			}

			if (!cancelledEventStatus
					.equals(EventStatusEnumeratedType.CANCELLED)) {
				LogHelper
						.addTrace("The first event in the modified OadrDistributeEventType was expected to be CANCELLED "
								+ activeEventStatus);

				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			} else {
				LogHelper
						.addTrace("The first event in the modified OadrDistributeEventType is CANCELLED as expected");
			}

			if (modificationNumber2 != 1) {
				LogHelper
						.addTrace("Did not receive the expected  ModificationNumber 1 in the modified OadrDistributeEvent");

				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			} else {
				LogHelper
						.addTrace("Receive the expected  ModificationNumber 1 in the cancelled Event");

			}
		}
		
		
		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		String oadrCreatedEventStr2 = CreatedEventHelper.createCreatedEvent(
				distributeEventList2, true, true, null);
		String oadrResponse2 = "";
		if (oadrCreatedEventStr2 != null) {
			oadrResponse2 = VenToVtnClient.post(oadrCreatedEventStr2);
		}

		OadrResponseType oadrResponseObj2 = ResponseHelper
				.createOadrResponseFromString(oadrResponse2);
		responseCode2 = ResponseHelper.getOadrResponseCode(oadrResponseObj2);

		pause(5);

		// Fail TestCase if validation errors are present.
		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}


		LogHelper.addTrace("------DistributeEvent 1 - Event 1------");
		if(!DistributeEventSignalHelper.isExpectedReceived("A",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent1.getOadrEvent().get(0))){

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 1 - Event 2------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent1.getOadrEvent().get(1))){

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 2 - Event 1------");
		if(!DistributeEventSignalHelper.isExpectedReceived("C",ResponseRequiredType.ALWAYS,1, oadrDistributeEvent2.getOadrEvent().get(0))){

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 2 - Event 2------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent2.getOadrEvent().get(1))){

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		if (responseCode2 != null && responseCode2.length() > 0) {
			LogHelper.addTrace("Received OadrResponseType 2 with responseCode "
					+ responseCode2);
		}
		// Test Case condition
		if (modificationNumber2 == 1 && responseCode2 != null
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
