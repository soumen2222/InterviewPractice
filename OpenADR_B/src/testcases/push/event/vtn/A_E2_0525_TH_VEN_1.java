package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventAction_One;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
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

public class A_E2_0525_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0525_TH_VEN_1 testClass = new A_E2_0525_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void test() throws Exception {
		
		long modificationNumber2 = -1;
		// long modificationNumber3 = -1;

		ResourceFileReader resourceFileReader = new ResourceFileReader();

		ICreatedEventAction createdAction = new CreatedEventAction_One(true,
				true);
		CreatedEventActionList.addCreatedEventAction(createdAction);

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction2);
	
		ICreatedEventAction createdAction3 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction3);
	
		
		VENService.startVENService();

		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.TestCase_0525_UIUserPromptTex(),
				2);

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

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		String message2 = resourceFileReader
				.TestCase_0525__Second_UIUserPromptText();

		new UIUserPrompt().Prompt(message2, 2);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("Test Case was cancelled by the user.");
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
			int foundCount = 0;
			for (OadrDistributeEventType eachOadrDistributeEvent : oadrDistributeEventList) {
				if (eachOadrDistributeEvent.getOadrEvent() != null) {
					int size = eachOadrDistributeEvent.getOadrEvent().size();
					if (size == 2) {
						foundCount++;

						if (foundCount == 2) {
							expectedDistributeEvent2 = eachOadrDistributeEvent;
							break;
						}

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
					.addTrace("Did not receive expected modified OadrDistributeEventType payload within the expected time");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		String strCreatedAction2 = createdAction2.getCreateEvent();
		String oadrResponseStr2 = VenToVtnClient.post(strCreatedAction2);

		OadrResponseType oadrResponse2 = ResponseHelper
				.createOadrResponseFromString(oadrResponseStr2);

		OadrDistributeEventType oadrDistributeEvent1 = expectedDistributeEvent;

		OadrDistributeEventType oadrDistributeEvent2 = expectedDistributeEvent2;
		modificationNumber2 = DistributeEventSignalHelper
				.getFirstEventModificationNumber(oadrDistributeEvent2);

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
		String expectedResponseCode = "200";
		boolean isExpectedResponse1 = ResponseHelper
				.isExpectedResponseReceived(oadrResponse2, expectedResponseCode);

		if (oadrResponse2 != null) {
			LogHelper.addTrace("Received OadrResponseType with responseCode "
					+ ResponseHelper.getOadrResponseCode(oadrResponse2));
		}

		if (modificationNumber2 != -1) {
			LogHelper
					.addTrace("Received Modification OadrDistributeEvent_1 with modificationNumber "
							+ modificationNumber2);
		}

		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (isExpectedResponse1 && modificationNumber2 == 1) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Test Case has Passed");
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
