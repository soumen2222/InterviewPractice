package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_Push_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0370_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0370_TH_VTN_1 testClass = new A_E0_0370_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		// Disable Conformance Rules
		ConformanceRuleValidator
				.setDisableDistributeEvent_AllPreviousEvntPresent_Check(true);
		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}
		IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();

		int firstIntervalStartTimeDelayMin = 1;
		DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(
				distributeEventAction1.getDistributeEvent(),
				firstIntervalStartTimeDelayMin);

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);
		distributeEventAction1.setEventCompleted(true);
		ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
		createdEventResult1.setExpectedOptInCount(1);
		createdEventResult1.addDistributeEvent(distributeEventAction1);
		distributeEventAction1
				.setCreatedEventSuccessCriteria(createdEventResult1);
		createdEventResult1.setLastCreatedEventResult(true);

		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();

		ResponseEventActionList.addResponseEventAction(responseEventAction1);

		VTNService.startVTNService();

		String strOadrDistributeEvent = SchemaHelper
				.getDistributeEventAsString(distributeEventAction1
						.getDistributeEvent());

		VtnToVenClient.post(strOadrDistributeEvent);

		distributeEventAction1.startCreatedEventTimeoutActionThread();
		pauseForTestCaseTimeout();

		boolean atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();

		Trace trace = TestSession.getTraceObj();
		if (atleastOneValidationErrorPresent && trace != null) {
			trace.setResult("Fail");
			trace.getLogFileContentTrace().append(
					"\nValidation Error(s) are present \n");
			trace.getLogFileContentTrace().append("\nTest Case has Failed \n");
			return;
		}

		if (!createdEventResult1.isExpectedCreatedEventReceived()) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("CreatedEvent with optIn was not received as expected");
			LogHelper.addTrace("Test Case has Failed");

			return;
		} else {
			LogHelper
					.addTrace("CreatedEvent with optIn was received as expected");
		}

		OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
				.getDistributeEvent();
		DistributeEventSignalHelper.setNewReqIDEvntIDStartTimeAndMarketCtx1(
				oadrDistributeEvent, firstIntervalStartTimeDelayMin);

		oadrDistributeEvent.getOadrEvent().remove(0);
		strOadrDistributeEvent = SchemaHelper
				.getDistributeEventAsString(oadrDistributeEvent);
		VtnToVenClient.post(strOadrDistributeEvent);

		String message = resourceFileReader.TestCase_0370_UIUserPromptText();
		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2.Prompt(message, uiUserPrompt.MEDIUM);

		atleastOneValidationErrorPresent = TestSession
				.isAtleastOneValidationErrorPresent();
		if (atleastOneValidationErrorPresent) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// If user clicks continue play
		if (TestSession.isUserClickedContinuePlay()) {

			String uiUserPromptDualActionQuestion = resourceFileReader
					.TestCase_0370_UIUserPromptDualActionQuestion();
			UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
			uiUserPromptDualAction.Prompt(uiUserPromptDualActionQuestion);

			// If user did not close the action dialog
			if (!TestSession.isUserCancelledCompleteUIDualAction()) {

				// If user choose Yes option
				if (TestSession.isUiDualActionYesOptionClicked()) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper.addTrace(resourceFileReader
							.LogMessage_VerifiedActiveEvent());
					LogHelper.addTrace("Test Case has Passed");

				} else {
					// If user choose No option
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace(resourceFileReader
							.LogMessage_UnableTOVerifyActiveEvent());
					LogHelper.addTrace("Test Case has Failed");
				}
			} else {
				// If user chose dual action dialog
				LogHelper.setResult(LogResult.NA);
				LogHelper
						.addTrace("TestCase execution was cancelled by the user");
			}

		} else { // User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
