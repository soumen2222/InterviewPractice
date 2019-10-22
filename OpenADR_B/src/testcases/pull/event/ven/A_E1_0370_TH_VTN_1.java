package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_NoEvent_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_0370_DistributeEventAction_1;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0370_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0370_TH_VTN_1 testClass = new A_E1_0370_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			ConformanceRuleValidator.setDisableDistributeEvent_AllPreviousEvntPresent_Check(true);
			
			IDistributeEventAction distributeEventAction1 = new E1_0370_DistributeEventAction_1();
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			OadrPollQueue.addToQueue(distributeEventAction1); 

			IDistributeEventAction distributeEventAction2 = new Default_NoEvent_DistributeEventAction();
			OadrPollQueue.addToQueue(distributeEventAction2); 
			ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult2.setExpectedTotalCount(0);
			createdEventResult2.addDistributeEvent(distributeEventAction2);
			distributeEventAction2
					.setCreatedEventSuccessCriteria(createdEventResult2);
			createdEventResult2.setLastCreatedEventResult(true);

			// Could receive upto 2 CreatedEvent(1 for new + 1 Modified).
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			VTNService.startVTNService();

			pauseForTestCaseTimeout();

			// If validation errors are found stop before prompt.
			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			} else {

				// If Condition if Expected Created Event has been Received.
				if (createdEventResult2.isExpectedCreatedEventReceived()) {
					LogHelper
							.addTrace("No oardCreatedEvent received, as expected");
					// Single option UIPrompt
					String message = resourceFileReader
							.TestCase_0370_UIUserPromptText();
					UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
					uiUserPrompt2.Prompt(message, UIUserPrompt.MEDIUM);

					// See of there were any validation errors during the
					// pinging of the VEN during the prompt wait time
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

						// DualAction Dialog
						String uiUserPromptDualActionQuestion = resourceFileReader
								.TestCase_0370_UIUserPromptDualActionQuestion();
						UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
						uiUserPromptDualAction
								.Prompt(uiUserPromptDualActionQuestion);

						// If user did not close the dual action dialog
						if (!TestSession.isUserCancelledCompleteUIDualAction()) {

							// If user choose Yes option
							if (TestSession.isUiDualActionYesOptionClicked()) {
								LogHelper.setResult(LogResult.PASS);
								LogHelper.addTrace(resourceFileReader
										.LogMessage_VerifiedActiveEvent());
								LogHelper.addTrace("Test Case has Passed");
								return;
							} else {
								// If user choose No option
								LogHelper.setResult(LogResult.FAIL);
								LogHelper
										.addTrace(resourceFileReader
												.LogMessage_UnableTOVerifyActiveEvent());
								LogHelper.addTrace("Test Case has Failed");
							}
						} else {
							// If user chose dual action dialog
							LogHelper.setResult(LogResult.NA);
							LogHelper
									.addTrace("TestCase execution was cancelled by the user");
						}

					} else { // User closed the single action dialog
						LogHelper.setResult(LogResult.NA);
						LogHelper
								.addTrace("TestCase execution was cancelled by the user");
					}

				} else { // No matching Created event received
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Received unexpected oadrCreatedEvent\n\nThe test case has failed.");
				}
			}

		} else { // User closed the single action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
