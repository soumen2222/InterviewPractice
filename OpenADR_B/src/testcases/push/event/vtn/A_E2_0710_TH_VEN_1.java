package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventErrorAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0710_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0710_TH_VEN_1 testClass = new A_E2_0710_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator
				.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		String message = resourceFileReader.TestCase_0710_UIUserPromptText();

		// Create a list of CreatedEventAction
		ICreatedEventAction createdAction1 = new CreatedEventErrorAction();
		createdAction1.setLastCreateEvent(true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2.Prompt(message, UIUserPrompt.MEDIUM);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}
		pauseForTestCaseTimeout();

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

		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.ALWAYS,0, VENServerResource
				.getOadrDistributeEventReceivedsList().get(0).getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		if (oadrResponseList.size() == 1) {
			OadrResponseType oadrResponse = oadrResponseList.get(0);

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
