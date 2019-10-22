package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventAction_LessThanThree;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
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

public class A_E2_0520_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0520_TH_VEN_1 testClass = new A_E2_0520_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableDistributeEvent_EventStatusValid_ValidCheck(true);
		ConformanceRuleValidator.setDisableDistributeEvent_EventOrderValid_Check(true);
		
		ICreatedEventAction createdAction = new Default_CreatedEventActionOnLastDistributeEventReceived(
				false, true);

		ICreatedEventAction createdAction1 = new CreatedEventAction_LessThanThree(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		ICreatedEventAction createdAction2 = new CreatedEventAction_LessThanThree(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction2);

		ICreatedEventAction createdAction3 = new CreatedEventAction_LessThanThree(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction3);

		VENService.startVENService();
		
		new UIUserPrompt().Prompt(
				new ResourceFileReader().TestCase_0520_UIUserPromptText(), 2);

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
					if (size == 4) {
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
					.addTrace("Did not receive four OadrEvent in a single OadrDistributeEventType payload within the expected time");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		String createdEventToSend = createdAction.getCreateEvent();
		String oadrResponseStr = VenToVtnClient.post(createdEventToSend);

		OadrResponseType oadrResponse = ResponseHelper
				.createOadrResponseFromString(oadrResponseStr);

		boolean isExpectedResponse = ResponseHelper.isExpectedResponseReceived(
				oadrResponse, "200");

		String expectedResponseCode = "200";
		isExpectedResponse = ResponseHelper.isExpectedResponseReceived(
				oadrResponse, expectedResponseCode);

		if (TestSession.isValidationErrorFound()) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");

			return;
		}

		LogHelper.addTrace("---Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("A",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("A",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		LogHelper.addTrace("---Event 3--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(2))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
	
		LogHelper.addTrace("---Event 4--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(3))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		pause(5);

		long modificationNumber1 = expectedDistributeEvent.getOadrEvent()
				.get(0).getEiEvent().getEventDescriptor()
				.getModificationNumber();
		long modificationNumber2 = expectedDistributeEvent.getOadrEvent()
				.get(1).getEiEvent().getEventDescriptor()
				.getModificationNumber();
		long modificationNumber3 = expectedDistributeEvent.getOadrEvent()
				.get(2).getEiEvent().getEventDescriptor()
				.getModificationNumber();
		long modificationNumber4 = expectedDistributeEvent.getOadrEvent()
				.get(3).getEiEvent().getEventDescriptor()
				.getModificationNumber();

		LogHelper.addTrace("Received modificationNumber for OadrEvent1 "
				+ modificationNumber1 + " in OadrDistributeEvent");
		LogHelper.addTrace("Received modificationNumber for OadrEvent2 "
				+ modificationNumber2 + " in OadrDistributeEvent");
		LogHelper.addTrace("Received modificationNumber for OadrEvent3 "
				+ modificationNumber3 + " in OadrDistributeEvent");
		LogHelper.addTrace("Received modificationNumber for OadrEvent4 "
				+ modificationNumber4 + " in OadrDistributeEvent");

		LogHelper.addTrace("Received OadrResponseType with responseCode "
				+ expectedResponseCode);

		if (isExpectedResponse && modificationNumber1 == 0
				&& modificationNumber2 == 0 && modificationNumber3 == 0
				&& modificationNumber4 == 0) {
			LogHelper.setResult(LogResult.PASS);

			LogHelper.addTrace("Test Case has Passed");

			new UIUserPrompt().Prompt(new ResourceFileReader()
					.TestCase_0520_UIUserPromptText_End(), 0);

		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
		}


	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
