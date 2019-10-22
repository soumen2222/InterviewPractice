package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventAction_One;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E2_0680_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0680_TH_VEN_1 testClass = new A_E2_0680_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void test() throws Exception {

		ResourceFileReader resourceFileReader = new ResourceFileReader();

		ICreatedEventAction createdAction = new CreatedEventAction_One(true,
				true);
		CreatedEventActionList.addCreatedEventAction(createdAction);

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		
		String message2 = resourceFileReader
				.TestCase_0680_Second_Push_UIUserPromptText();
		UIUserPrompt uiUserPrompt1 = new UIUserPrompt(message2, 1);
		createdAction1.setPromptForResponseReceipt(uiUserPrompt1);

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);

		VENService.startVENService();

		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0680_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;

		ArrayList<OadrDistributeEventType> oadrDistributeEventList = null;

		OadrDistributeEventType expectedDistributeEvent = null;

		while (System.currentTimeMillis() < testCaseTimeout) {
			oadrDistributeEventList = (ArrayList<OadrDistributeEventType>) VENServerResource
					.getOadrDistributeEventReceivedsList().clone();
			pause(5);
			for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
				if (eachOadrDistributeEvent.getOadrEvent() != null) {
					int size = eachOadrDistributeEvent.getOadrEvent().size();
					if (size == 2) {
						expectedDistributeEvent = eachOadrDistributeEvent;
						break;
					}
				}

			}
			if (expectedDistributeEvent != null) {
				break;
			}
		}

		pause(5);

		if (expectedDistributeEvent == null) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive two OadrEvent in a single OadrDistributeEventType payload within the expected time");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		
		String strCreatedAction = createdAction1.getCreateEvent();
		VenToVtnClient.post(strCreatedAction);

		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2
				.Prompt(resourceFileReader
						.TestCase_0680_Second_Push_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		testCaseTimeout = System.currentTimeMillis() + totalDurationInput;

		oadrDistributeEventList = null;

		OadrDistributeEventType expectedDistributeEvent2 = null;

		while (System.currentTimeMillis() < testCaseTimeout) {
			oadrDistributeEventList = (ArrayList<OadrDistributeEventType>) VENServerResource
					.getOadrDistributeEventReceivedsList().clone();
			pause(5);
			for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
				if (eachOadrDistributeEvent.getOadrEvent() != null) {
					int size = eachOadrDistributeEvent.getOadrEvent().size();
					if (size == 3) {
						expectedDistributeEvent2 = eachOadrDistributeEvent;
						break;
					}
				}

			}
			if (expectedDistributeEvent2 != null) {
				break;
			}
		}

		pause(5);

		if (expectedDistributeEvent2 == null) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive expected OadrDistributeEventType payload within the expected time");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		
		String strCreatedAction2 = createdAction2.getCreateEvent();
		String oadrResponseStr2 = VenToVtnClient.post(strCreatedAction2);

		ResponseHelper.createOadrResponseFromString(oadrResponseStr2);

		oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();

		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		
		
		LogHelper.addTrace("---DistributeEvent 1: Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		LogHelper.addTrace("---DistributeEvent 1: Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		
		LogHelper.addTrace("---DistributeEvent 2: Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent2.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		LogHelper.addTrace("---DistributeEvent 2: Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent2.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---DistributeEvent 3: Event 3--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent2.getOadrEvent().get(2))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.setResult(LogResult.PASS);
		LogHelper.addTrace("Test Case has Passed");

	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
