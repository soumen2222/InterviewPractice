package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E3_0680_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0680_TH_VEN testClass = new A_E3_0680_TH_VEN();
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
				+ "\n\n" + resourceFileReader.TestCase_0680_UIUserPromptText(),
				2);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		// Create OadrRequestEvent
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
		
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(response);
		// Create CreatedEvent and Post.
		OadrDistributeEventType oadrDistributeEvent1 = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(response);

		if (oadrDistributeEvent1.getOadrEvent().size() != 2) {
		
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace(oadrDistributeEvent1.getOadrEvent().size()
					+ " Event(s) were received. Expected 1 Event");
		
			return;
		}
		
		// Create CreatedEvent and Post to VTN.
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, true, null);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}


		// Prompt
		String message2 = resourceFileReader
				.TestCase_0680_Second_Pull_UIUserPromptText();
		UIUserPrompt uiUserPrompt1 = new UIUserPrompt();
		uiUserPrompt1.Prompt(message2, 1);


		// User clicked Play
		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.addTrace("Test Case was cancelled by the user");
			LogHelper.setResult(LogResult.NA);
			return;
		}


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

		if (oadrDistributeEvent2.getOadrEvent().size() != 2) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace(oadrDistributeEvent2.getOadrEvent().size()
					+ " Event(s) were received. Expected 1 Event");

			return;
		}
		distributeEventList.clear();
		distributeEventList.add(response2);
		// Create CreatedEvent and Post to VTN.
		String oadrCreatedEventStr2 = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, true, null);

		if (oadrCreatedEventStr2 != null) {
			VenToVtnClient.post(oadrCreatedEventStr2);
		}

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		LogHelper.addTrace("---DistributeEvent 1 - Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent1.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---DistributeEvent 1 - Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent1.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		

		LogHelper.addTrace("---DistributeEvent 2 - Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent2.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---DistributeEvent 2 - Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent2.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		if (oadrDistributeEvent1.getOadrEvent().size() == 2
				&& oadrDistributeEvent2.getOadrEvent().size() == 2) {
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
