package testcases.pull.event.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EiTargetType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventConformanceValidationHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E3_0435_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0435_TH_VEN testClass = new A_E3_0435_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ConformanceRuleValidator
				.setDisable_AtLeastOneEiTargetMatch_ValidCheck(true);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0435_UIUserPromptText(),
				2);

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
			LogHelper
					.addTrace("Expected OadrEvent were not received in OadrDistributeEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		
		// Create a ArrayList list of distribute event responses for which you
		// need to create created event.
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(strOadrDistributeEventResponse);

		// Create OadrCreatedEventType giving distributeEventList.
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, true, "Response Description");

		// Post the CreatedEvent to VTN.
		String oadrResponse = "";
		if (oadrCreatedEventStr != null) {
			oadrResponse = VenToVtnClient.post(oadrCreatedEventStr);
		}

		// Convert the oadrResponse received to OadrResponse.
		OadrResponseType oadrResponseObj = ResponseHelper
				.createOadrResponseFromString(oadrResponse);

		// Get the response code
		String responseCode = ResponseHelper
				.getOadrResponseCode(oadrResponseObj);

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

		LogHelper.addTrace("Received OadrResponseType with responseCode "
				+ responseCode);

		EiTargetType eiTarget = oadrDistributeEvent.getOadrEvent().get(0)
				.getEiEvent().getEiTarget();
		List<String> resourceIDList = eiTarget.getResourceID();
		List<String> venIDList = eiTarget.getVenID();

		boolean atLeastOneTargetReceived = false;

		if (resourceIDList != null && resourceIDList.size() > 0) {
			atLeastOneTargetReceived = true;
		}
		if (venIDList != null && venIDList.size() > 0) {
			atLeastOneTargetReceived = true;
		}
		if (!atLeastOneTargetReceived) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Did not receive even one EiTarget element.");
			LogHelper.addTrace("Test Case has Failed");
			return;
		} else {
			LogHelper.addTrace("Receive atleast one EiTarget element.");
		}

		boolean isAtLeastOneEiTargetMatch = DistributeEventConformanceValidationHelper
				.isAtLeastOneEiTargetMatch(oadrDistributeEvent);

		if (!isAtLeastOneEiTargetMatch) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("None of the EiTarget elements match the test system properties configuration.");
			LogHelper.addTrace("Test Case has Failed");
			return;
		} else {
			LogHelper
					.addTrace("Atleast one EiTarget elements match the test system properties configuration.");

		}

		// Test Case condition
		if (responseCode.equals("200") && DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, oadrDistributeEvent.getOadrEvent().get(0))) {

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
