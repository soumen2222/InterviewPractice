package testcases.push.event.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0454_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0454_TH_VEN_1 testClass = new A_E2_0454_TH_VEN_1();
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
				false, false);
		createdAction1.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0454_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}
		pauseForTestCaseTimeout();

		ArrayList<OadrResponseType> oadrResponseList = TestSession
				.getOadrResponse();

		if (oadrResponseList.size() == 1) {
			OadrResponseType oadrResponse = oadrResponseList.get(0);

			String expectedResponseCode = "200";
			boolean isExpectedResponse = ResponseHelper
					.isExpectedResponseReceived(oadrResponse,
							expectedResponseCode);

			if (TestSession.isValidationErrorFound()) {
				LogHelper.setResult(LogResult.FAIL);
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

			ArrayList<OadrDistributeEventType> distributeEventList = VENServerResource
					.getOadrDistributeEventReceivedsList();

			if (distributeEventList.size() != 1) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Expected only one DistributeEvent");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			List<OadrEvent> eiEventList = distributeEventList.get(0)
					.getOadrEvent();
			if (eiEventList.size() != 1) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Expected only one EiEvent");
				LogHelper.addTrace("Test Case has Failed");
				return;

			}

			long modificationNumber = eiEventList.get(0).getEiEvent()
					.getEventDescriptor().getModificationNumber();

			LogHelper
					.addTrace("Received OadrDistributeEventType with ModificationNumber "
							+ modificationNumber);
			LogHelper.addTrace("Received OadrResponseType with responseCode "
					+ expectedResponseCode);

			if (isExpectedResponse && modificationNumber == 0 && DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, eiEventList.get(0))) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
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
