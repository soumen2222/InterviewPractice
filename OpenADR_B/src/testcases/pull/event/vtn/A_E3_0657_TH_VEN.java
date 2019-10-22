package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
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

public class A_E3_0657_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0657_TH_VEN testClass = new A_E3_0657_TH_VEN();
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
				+ "\n\n" + resourceFileReader.TestCase_0657_UIUserPromptText(),
				1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

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
		
		OadrDistributeEventType oadrDistributeEvent = DistributeEventSignalHelper
		.createOadrDistributeEventFromString(response);
		if (oadrDistributeEvent.getOadrEvent().size() != 1) {
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
				.TestCase_0657_Second_UIUserPromptText();
		uiUserPrompt2.Prompt(message2, 1);

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

		if (!oadrDistributeEvent2.getOadrEvent().get(0).getEiEvent()
				.getEventDescriptor().getEventStatus()
				.equals(EventStatusEnumeratedType.CANCELLED)) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected a cancelled DistributeEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		String response3 = VenToVtnClient.poll(OadrDistributeEventType.class);; 
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		 isExpectedReceived = OadrUtil.isExpected(response3,OadrDistributeEventType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		OadrDistributeEventType oadrDistributeEvent3 = DistributeEventSignalHelper
				.createOadrDistributeEventFromString(response3);

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
		if(!DistributeEventSignalHelper.isExpectedReceived("C",ResponseRequiredType.ALWAYS,1, oadrDistributeEvent2.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---DistributeEvent 3--");
		if(!DistributeEventSignalHelper.isExpectedReceived("C",ResponseRequiredType.ALWAYS,1, oadrDistributeEvent3.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		if (oadrDistributeEvent3.getOadrEvent().get(0).getEiEvent()
				.getEventDescriptor().getEventStatus()
				.equals(EventStatusEnumeratedType.CANCELLED)) {
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
