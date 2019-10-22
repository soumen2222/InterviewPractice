package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
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
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0474_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0474_TH_VEN_1 testClass = new A_E2_0474_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		long modificationNumber2 = -1;

		ResourceFileReader resourceFileReader = new ResourceFileReader();

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		String message2 = resourceFileReader
				.TestCase_0474_Second_UIUserPromptText();
		UIUserPrompt uiUserPrompt1 = new UIUserPrompt(message2, 1);
		createdAction1.setPromptForResponseReceipt(uiUserPrompt1);

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, false);
		createdAction2.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction2);

		VENService.startVENService();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0474_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		pauseForTestCaseTimeout();


		ArrayList<OadrResponseType> oadrResponseList = TestSession
				.getOadrResponse();

		if (oadrResponseList.size() == 2) {
			OadrDistributeEventType oadrDistributeEvent2 = VENServerResource
					.getOadrDistributeEventReceivedsList().get(1);
			modificationNumber2 = DistributeEventSignalHelper
					.getFirstEventModificationNumber(oadrDistributeEvent2);

			OadrResponseType oadrResponse1 = null;

			if (oadrResponseList.size() > 0) {
				oadrResponse1 = oadrResponseList.get(1);
			}

			String expectedResponseCode = "200";
			boolean isExpectedResponse1 = ResponseHelper
					.isExpectedResponseReceived(oadrResponse1,
							expectedResponseCode);

			if (oadrResponse1 != null) {
				LogHelper.addTrace("Received OadrResponseType with responseCode "
						+ ResponseHelper.getOadrResponseCode(oadrResponse1));
			}

			if (modificationNumber2 != -1) {
				LogHelper
						.addTrace("Received Modification OadrDistributeEvent_1 with modificationNumber "
								+ modificationNumber2);
			}

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {

				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			LogHelper.addTrace("---DistributeEvent 1--");
			if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, VENServerResource
					.getOadrDistributeEventReceivedsList().get(0).getOadrEvent().get(0))){
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			}
			
			LogHelper.addTrace("---DistributeEvent 2--");
			if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,1, VENServerResource
					.getOadrDistributeEventReceivedsList().get(1).getOadrEvent().get(0))){
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Test Case has Failed");
				return;
			}
			LogHelper.addTrace("-----------------------------");
			if (isExpectedResponse1 && modificationNumber2 == 1) {
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
