package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E3_0655_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0655_TH_VEN testClass = new A_E3_0655_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ConformanceRuleValidator
				.setDisableDistributeEvent_AllPreviousEvntPresent_Check(true);
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0655_UIUserPromptText(),
				1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		// Create OadrRequestEvent
		OadrRequestEventType oadrRequestEvent = RequestEventSignalHelper
				.loadOadrRequestEvent("oadrRequestEvent_Default.xml");
		oadrRequestEvent.getEiRequestEvent().setVenID(
				propertiesFileReader.getVenID());

		// 0 event ---------------------------------
		oadrRequestEvent.getEiRequestEvent().setReplyLimit(new Long(0));

		// Convert OadrRequestEventType Object to String and post it to VTN
		String strOadrRequestEvent = SchemaHelper
				.getRequestEventAsString(oadrRequestEvent);
		String strOadrDistributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);

		// Load OadrDistributeEventResponse received to OadrDistributeEvent
		// Object
		OadrDistributeEventType oadrDistributeEvent = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(strOadrDistributeEventResponse);
		ArrayList<String> distributeEventList=new ArrayList<String>();
		
		String createdEvent1 = CreatedEventHelper.createCreatedEvent(distributeEventList, true, true, "");
		VenToVtnClient.post(createdEvent1);
		 
		 
		int zeroEventExpected = oadrDistributeEvent.getOadrEvent().size();

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (zeroEventExpected == 0) {
			LogHelper
					.addTrace("Received No Event as expected for reply limit 0 in oadrRequestEvent");
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected No Event as reply limit was 0 in oadrRequestEvent. Received "
							+ zeroEventExpected + " Events");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// 1 event ---------------------------------
		oadrRequestEvent.getEiRequestEvent().setReplyLimit(new Long(1));

		// Convert OadrRequestEventType Object to String and post it to VTN
		strOadrRequestEvent = SchemaHelper
				.getRequestEventAsString(oadrRequestEvent);
		strOadrDistributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);

		distributeEventList =new ArrayList<String>();
		
		distributeEventList.add(strOadrDistributeEventResponse);
				
		String createdEvent2 = CreatedEventHelper.createCreatedEvent(distributeEventList, true, true, "");
		VenToVtnClient.post(createdEvent2);
		 
		// Load OadrDistributeEventResponse received to OadrDistributeEvent
		// Object
		oadrDistributeEvent = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(strOadrDistributeEventResponse);


		OadrDistributeEventType oadrDistributeEvent1 = Clone.clone(oadrDistributeEvent);
			
		int oneEventExpected = oadrDistributeEvent.getOadrEvent().size();

		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (oneEventExpected == 1) {
			LogHelper
					.addTrace("Received 1 Event as expected for reply limit 1 in oadrRequestEvent");
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected 1 Event as reply limit was 1 in oadrRequestEvent. Received "
							+ oneEventExpected + " Events");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// 2 events Expected ---------------------------------
		oadrRequestEvent.getEiRequestEvent().setReplyLimit(new Long(2));

		// Convert OadrRequestEventType Object to String and post it to VTN
		strOadrRequestEvent = SchemaHelper
				.getRequestEventAsString(oadrRequestEvent);
		strOadrDistributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);

		distributeEventList =new ArrayList<String>();
		
		distributeEventList.add(strOadrDistributeEventResponse);
				
		String createdEvent3 = CreatedEventHelper.createCreatedEvent(distributeEventList, true, true, "");
		VenToVtnClient.post(createdEvent3);
		
		// Load OadrDistributeEventResponse received to OadrDistributeEvent
		// Object
		oadrDistributeEvent = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(strOadrDistributeEventResponse);
		OadrDistributeEventType oadrDistributeEvent2 = Clone.clone(oadrDistributeEvent);
		
		int twoEventsExpected = oadrDistributeEvent.getOadrEvent().size();
		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (twoEventsExpected == 2) {
			LogHelper
					.addTrace("Received 2 Event as expected for reply limit 2 in oadrRequestEvent");
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected 2 Events as reply limit was 2 in oadrRequestEvent. Received "
							+ twoEventsExpected + " Events");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// Three events ---------------------------------
		oadrRequestEvent.getEiRequestEvent().setReplyLimit(new Long(3));

		// Convert OadrRequestEventType Object to String and post it to VTN
		strOadrRequestEvent = SchemaHelper
				.getRequestEventAsString(oadrRequestEvent);
		strOadrDistributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);

		distributeEventList =new ArrayList<String>();
		
		distributeEventList.add(strOadrDistributeEventResponse);
				
		String createdEvent4 = CreatedEventHelper.createCreatedEvent(distributeEventList, true, true, "");
		VenToVtnClient.post(createdEvent4);
		
		
		// Load OadrDistributeEventResponse received to OadrDistributeEvent
		// Object
		oadrDistributeEvent = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(strOadrDistributeEventResponse);
		OadrDistributeEventType oadrDistributeEvent3 = Clone.clone(oadrDistributeEvent);
		

		int threeEventsExpected = oadrDistributeEvent.getOadrEvent().size();

		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (threeEventsExpected == 3) {
			LogHelper
					.addTrace("Received 3 Events as expected for reply limit 3 in oadrRequestEvent");
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected 3 Events as reply limit was 3 in oadrRequestEvent. Received "
							+ twoEventsExpected + " Events");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// Four events ---------------------------------
		oadrRequestEvent.getEiRequestEvent().setReplyLimit(new Long(4));

		// Convert OadrRequestEventType Object to String and post it to VTN
		strOadrRequestEvent = SchemaHelper
				.getRequestEventAsString(oadrRequestEvent);
		strOadrDistributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);

		distributeEventList =new ArrayList<String>();
		
		distributeEventList.add(strOadrDistributeEventResponse);
				
		String createdEvent5 = CreatedEventHelper.createCreatedEvent(distributeEventList, true, true, "");
		VenToVtnClient.post(createdEvent5);
		
		
		
		// Load OadrDistributeEventResponse received to OadrDistributeEvent
		// Object
		oadrDistributeEvent = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(strOadrDistributeEventResponse);
		OadrDistributeEventType oadrDistributeEvent4 = Clone.clone(oadrDistributeEvent);
		

		int fourEventsExpected = oadrDistributeEvent.getOadrEvent().size();

		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (fourEventsExpected == 3) {
			LogHelper
					.addTrace("Received 3 Events as expected for reply limit 4 in oadrRequestEvent");
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected 3 Events as reply limit was 4 in oadrRequestEvent. Received "
							+ twoEventsExpected + " Events");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// Create a ArrayList list of distribute event responses for which you
		// need to create created event.
		distributeEventList = new ArrayList<String>();
		distributeEventList.add(strOadrDistributeEventResponse);

		// Create OadrCreatedEventType giving distributeEventList.
		/*
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, false, null);
		 
		OadrCreatedEventType oadrCreatedEventAll = CreatedEventHelper
				.createCreatedEventFromString(oadrCreatedEventStr);
		OadrCreatedEventType oadrCreatedEventOptOut = Clone
				.clone(oadrCreatedEventAll);

		oadrCreatedEventOptOut.getEiCreatedEvent().getEventResponses()
				.getEventResponse().remove(0);

		oadrCreatedEventAll.getEiCreatedEvent().getEventResponses()
				.getEventResponse().remove(1);
		oadrCreatedEventAll.getEiCreatedEvent().getEventResponses()
				.getEventResponse().remove(1);
		oadrCreatedEventAll.getEiCreatedEvent().getEventResponses()
				.getEventResponse().get(0).setOptType(OptTypeType.OPT_IN);

		String oadrCreatedEventOptInStr = SchemaHelper
				.getCreatedEventAsString(oadrCreatedEventAll);
		String oadrCreatedEventOptOutStr = SchemaHelper
				.getCreatedEventAsString(oadrCreatedEventOptOut);

		// Post the CreatedEvent to VTN.
		String oadrResponse1 = "";
		if (oadrCreatedEventOptInStr != null) {
			oadrResponse1 = VenToVtnClient.post(oadrCreatedEventOptInStr);
		}

		String oadrResponse2 = "";
		if (oadrCreatedEventOptInStr != null) {
			oadrResponse2 = VenToVtnClient.post(oadrCreatedEventOptOutStr);
		}
		// Convert the oadrResponse received to OadrResponse.
		OadrResponseType oadrResponseObj1 = ResponseHelper
				.createOadrResponseFromString(oadrResponse1);

		OadrResponseType oadrResponseObj2 = ResponseHelper
				.createOadrResponseFromString(oadrResponse2);

		// Get the response code
		String responseCode1 = ResponseHelper
				.getOadrResponseCode(oadrResponseObj1);
		String responseCode2 = ResponseHelper
				.getOadrResponseCode(oadrResponseObj2);
		 */
		
		// Check if there were any errors in the payload and fail test case if
		// found.
		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		
		LogHelper.addTrace("------DistributeEvent 1 - Event 1------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent1.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 2 - Event 1------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent2.getOadrEvent().get(0))){
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
		
		
		
		LogHelper.addTrace("------DistributeEvent 3 - Event 1------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent3.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 3 - Event 2------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent3.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 3 - Event 3------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent3.getOadrEvent().get(2))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		
		LogHelper.addTrace("------DistributeEvent 4 - Event 1------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent4.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 4 - Event 2------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent4.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("------DistributeEvent 4 - Event 3------");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent4.getOadrEvent().get(2))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
	
		// Test Case condition
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Test Case has Passed");

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
