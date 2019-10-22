package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_0267_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_267_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.RandomizedCancellationTimeoutThread;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0267_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0267_TH_VTN_1 testClass = new A_E1_0267_TH_VTN_1();
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
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0267_UIUserPromptText()+ "\n\n" + resourceFileReader.TestCase_RandomizedCancellationFailWarningPrompt(),
				UIUserPrompt.LARGE);

		if (TestSession.isUserClickedContinuePlay()) {

			// Create DistributeEventAction Action
			IDistributeEventAction distributeEventAction1 = new E1_267_Pull_CPP_DistributeEventAction();

			// Add DistributeEventAction to DistributeEventActionList
			OadrPollQueue.addToQueue(distributeEventAction1); 

			// Create CreatedEventResult
			ICreatedEventResult createdEventResult = new Default_CreatedEventResultOptINOptOut();
			createdEventResult.addDistributeEvent(distributeEventAction1);
			createdEventResult.setExpectedOptInCount(1);
			createdEventResult.setExpectedOptOutCount(0);
			createdEventResult.setLastCreatedEventResult(true);
			// Set the CreatedEventResult to
			// distributeEventAction.setCreatedEventSuccessCriteria
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult);

			// Create ResponseEventAction
			IResponseEventAction responseEventAction = new Default_ResponseEventAction();

			// Add ResponseEventAction to ResponseEventActionList
			ResponseEventActionList.addResponseEventAction(responseEventAction);

			// Start VTN Server
			VTNService.startVTNService();

			// Pause for Timeout before proceeding with TestCase validation.
			pauseForTestCaseTimeout();

			// If validation errors are found stop before prompt.
			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			// If Condition if Expected Created Event has been Received.
			if (createdEventResult.isExpectedCreatedEventReceived()) {
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN);

				UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
				uiUserPrompt2.Prompt(resourceFileReader
						.TestCase_0267_SecondUIUserPromptText(),
						UIUserPrompt.MEDIUM);

				if (!TestSession.isUserClickedContinuePlay()) {
					LogHelper.setResult(LogResult.NA);
					LogHelper
							.addTrace("TestCase execution was cancelled by the user");
					return;
				}

				RandomizedCancellationTimeoutThread randomizedTimeout=new RandomizedCancellationTimeoutThread();
				randomizedTimeout.start2andHalfMinTimer();
				
				IDistributeEventAction distributeEventAction2 = new E1_0267_Pull_CPP_DistributeEventAction();
				OadrPollQueue.addToQueue(distributeEventAction2); 
				// Create CreatedEventResult
				ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
				createdEventResult1.addDistributeEvent(distributeEventAction2);
				createdEventResult1.setExpectedOptInCount(1);
				createdEventResult1.setExpectedOptOutCount(0);
				createdEventResult1.setLastCreatedEventResult(true);
				distributeEventAction2
						.setCreatedEventSuccessCriteria(createdEventResult1);

				pauseForTestCaseTimeout();

				if (!createdEventResult1.isExpectedCreatedEventReceived()) {
					LogHelper
							.addTrace("Did not receive matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN
									+ " for the cancelled event.");
					LogHelper.addTrace("Test Case has Failed");
					LogHelper.setResult(LogResult.FAIL);
					return;
				} else {
					LogHelper
							.addTrace("Received matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN
									+ " for the cancelled event.");

				}

				// See of there were any validation errors during the pinging of
				// the VEN during the prompt wait time
				atleastOneValidationErrorPresent = TestSession
						.isAtleastOneValidationErrorPresent();
				if (atleastOneValidationErrorPresent) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

				// DualAction Dialog
				UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
				uiUserPromptDualAction.Prompt(resourceFileReader
						.TestCase_0267_UIUserPromptDualActionQuestion(),
						uiUserPromptDualAction.SMALL);

				
				
				// If user did not close the dual action dialog
				if (!TestSession.isUserCancelledCompleteUIDualAction()) {

					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
					
					atleastOneValidationErrorPresent = TestSession
							.isAtleastOneValidationErrorPresent();
					if (atleastOneValidationErrorPresent) {
						LogHelper.setResult(LogResult.FAIL);
						LogHelper.addTrace("Validation Error(s) are present");
						LogHelper.addTrace("Test Case has Failed");
						return;
					}

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

					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
					
					LogHelper.setResult(LogResult.NA);
					LogHelper
							.addTrace("TestCase execution was cancelled by the user");
				}

			} else { // No matching Created event received
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Did not receive matching OadrCreatedEventType with "
								+ OptTypeType.OPT_IN);
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
