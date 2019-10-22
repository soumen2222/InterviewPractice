package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_268_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Empty_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.RandomizedCancellationTimeoutThread;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0268_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0268_TH_VTN_1 testClass = new A_E1_0268_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ConformanceRuleValidator
				.setDisableDistributeEvent_AllPreviousEvntPresent_Check(true);
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message()
				+ "\n\n" + resourceFileReader.TestCase_0268_UIUserPromptText()+ "\n\n" + resourceFileReader.TestCase_RandomizedCancellationFailWarningPrompt(),
				uiUserPrompt.LARGE);

		if (TestSession.isUserClickedContinuePlay()) {
			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

			// Create DistributeEventAction Action
			IDistributeEventAction distributeEventAction1 = new E1_268_Pull_CPP_DistributeEventAction();

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
			responseEventAction.setlastEvent(true);
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
						.TestCase_0268_SecondUIUserPromptText(),
						uiUserPrompt.MEDIUM);

				if (!TestSession.isUserClickedContinuePlay()) {
					LogHelper.setResult(LogResult.NA);
					LogHelper
							.addTrace("TestCase execution was cancelled by the user");
					return;
				}

				RandomizedCancellationTimeoutThread randomizedTimeout=new RandomizedCancellationTimeoutThread();
				randomizedTimeout.start2andHalfMinTimer();

				IDistributeEventAction distributeEventAction2 = new Empty_Pull_CPP_DistributeEventAction();
				OadrPollQueue.addToQueue(distributeEventAction2); 

				long totalDurationInput = Long.valueOf(propertiesFileReader
						.get("asyncResponseTimeout"));
				long testCaseTimeout = System.currentTimeMillis()
						+ totalDurationInput;

				while (System.currentTimeMillis() < testCaseTimeout) {
					pause(10);
					if (distributeEventAction2.isEventCompleted())
						break;
				}

				if (!distributeEventAction2.isEventCompleted()) {
					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Expected OadrRequestEventType has not been received");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

				pause(15);

				// See of there were any validation errors during the pinging of
				// the VEN during the prompt wait time
				atleastOneValidationErrorPresent = TestSession
						.isAtleastOneValidationErrorPresent();
				if (atleastOneValidationErrorPresent) {
					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

				// DualAction Dialog
				UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
				uiUserPromptDualAction.Prompt(resourceFileReader
						.TestCase_0268_UIUserPromptDualActionQuestion(),
						uiUserPromptDualAction.SMALL);

				// See of there were any validation errors during the pinging of
				// the VEN during the prompt wait time
				atleastOneValidationErrorPresent = TestSession
						.isAtleastOneValidationErrorPresent();
				if (atleastOneValidationErrorPresent) {
					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

				// If user did not close the dual action dialog
				if (!TestSession.isUserCancelledCompleteUIDualAction()) {
					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
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
					randomizedTimeout.pauseIfRandomizedCancellationNotComplete();
					// If user chose dual action dialog
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
