package testcases.push.event.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0527_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0527_TH_VEN_1 testClass = new A_E2_0527_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		// Create a list of CreatedEventAction
		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true, "Response Description");
		createdAction1.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0527_Push_UIUserPromptText(), 1);

		VENService.startVENService();

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}
		pauseForTestCaseTimeout();

		String responseCode = "";

		ArrayList<OadrResponseType> oadrResponseList = TestSession
				.getOadrResponse();

		ArrayList<OadrDistributeEventType> distributeEventList = VENServerResource
				.getOadrDistributeEventReceivedsList();

		if (distributeEventList.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected one DistributeEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (!distributeEventList.get(0).getOadrEvent().get(0).getEiEvent()
				.getEventDescriptor().getEventStatus()
				.equals(EventStatusEnumeratedType.NEAR)) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected Event with EventStatus as NEAR");
			LogHelper.addTrace("Test Case has Failed");
			return;
		} else {
			LogHelper
					.addTrace("Expected Event with EventStatus as NEAR was received");
		}

		List<OadrEvent> eiEventList = distributeEventList.get(0).getOadrEvent();
		if (eiEventList.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Expected only one EiEvent");
			LogHelper.addTrace("Test Case has Failed");
			return;

		}

		long modificationNumber = eiEventList.get(0).getEiEvent()
				.getEventDescriptor().getModificationNumber();

		if (oadrResponseList.size() == 1) {
			OadrResponseType oadrResponse = oadrResponseList.get(0);

			String expectedResponseCode = "200";
			boolean isExpectedResponse = ResponseHelper
					.isExpectedResponseReceived(oadrResponse,
							expectedResponseCode);

			if (TestSession.isValidationErrorFound()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			LogHelper
					.addTrace("Received OadrDistributeEventType with modificationNumber "
							+ modificationNumber);

			if (isExpectedResponse && modificationNumber == 0) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ expectedResponseCode);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ responseCode);
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
