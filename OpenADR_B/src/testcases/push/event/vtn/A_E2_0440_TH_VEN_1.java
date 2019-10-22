package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEvent_TwoEvent_Action;
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

public class A_E2_0440_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0440_TH_VEN_1 testClass = new A_E2_0440_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void test() throws Exception {
		
		// Create a list of CreatedEventAction
		ICreatedEventAction createdAction1 = new CreatedEvent_TwoEvent_Action(
				true, true, false);

		ICreatedEventAction createdAction2 = new CreatedEvent_TwoEvent_Action(
				true, true, false);

		CreatedEventActionList.addCreatedEventAction(createdAction1);
		CreatedEventActionList.addCreatedEventAction(createdAction2);

		VENService.startVENService();

		new UIUserPrompt().Prompt(
				new ResourceFileReader().TestCase_0440_Push_UIUserPromptText(),
				1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;

		ArrayList<OadrDistributeEventType> oadrDistributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();

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
			if (expectedDistributeEvent != null)
				break;
		}

		pause(5);
		String responseCode = "";

		ArrayList<OadrResponseType> oadrResponseList = TestSession
				.getOadrResponse();

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {

			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (expectedDistributeEvent == null) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive two OadrEvent in a single OadrDistributeEventType payload within the expected time");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		LogHelper.addTrace("---Event 1--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		LogHelper.addTrace("-----------------------------");

		LogHelper.addTrace("---Event 2--");
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, expectedDistributeEvent.getOadrEvent().get(1))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		LogHelper.addTrace("-----------------------------");

		int oadrResponseListSize = oadrResponseList.size();

		if (oadrResponseListSize > 0) {
			OadrResponseType oadrResponse = oadrResponseList
					.get(oadrResponseListSize - 1);

			String expectedResponseCode = "200";
			boolean isExpectedResponse = ResponseHelper
					.isExpectedResponseReceived(oadrResponse,
							expectedResponseCode);

			if (isExpectedResponse) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ expectedResponseCode);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ responseCode);
				LogHelper.addTrace("Test Case has Failed");
			}
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
