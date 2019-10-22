package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseCreatedOptAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreatedOptEvent;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Pull_DistributeEventAction_OneEvent_TwoInterval;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0280_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0280_TH_VTN_1 testClass = new A_E1_0280_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {

			e.printStackTrace();
			LogHelper.addTrace(e.getMessage());
		}
	}

	@Override
	public void test() throws Exception {

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message(),
				UIUserPrompt.SMALL);

		if (TestSession.isUserClickedContinuePlay()) {

			// Create DistributeEventAction Action
			IDistributeEventAction distributeEventAction1 = new Pull_DistributeEventAction_OneEvent_TwoInterval();
			OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
					.getDistributeEvent();
			EiEventType eiEvent = oadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();
			DistributeEventSignalHelper
					.modificationRuleStartDtCurrentPlusMinutes(eiEvent, eiEvent
							.getEventDescriptor().getCreatedDateTime(), 1);

			// Add DistributeEventAction to DistributeEventActionList
			OadrPollQueue.addToQueue(distributeEventAction1); 

			// Create CreatedEventResult
			ICreatedEventResult createdEventResult = new Default_CreatedEventResultOptINOptOut();
			createdEventResult.addDistributeEvent(distributeEventAction1);
			createdEventResult.setExpectedOptInCount(1);
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

				IResponseCreatedOptAckAction createdOpt = new DefaultOadrCreatedOptEvent();
				ResponseCreatedOptAckActionList.addResponseCreatedOptAckAction(createdOpt);
				
				// Single option UIPrompt
				UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
				uiUserPrompt2.Prompt(
						resourceFileReader.TestCase_0280_UIUserPromptText(),
						UIUserPrompt.MEDIUM);

				// If user clicks continue play
				if (TestSession.isUserClickedContinuePlay()) {

					createdEventResult.setExpectedOptOutCount(1);
					createdEventResult
							.setIncludingPingOptOut_MinimumExpected(1);
					createdEventResult.setLastCreatedEventResult(true);

					long asynchTimeout = OadrUtil.getCreatedEventAsynchTimeout();
					
					ICreatedEventResult createdEventResultOpt = new Default_CreatedEventResultOptINOptOut();
					createdEventResultOpt.addDistributeEvent(distributeEventAction1);
					createdEventResultOpt.setExpectedOptInCount(1);
					createdEventResultOpt.setExpectedCreateOpt_OptOutCount(1);
					createdEventResultOpt.setLastCreatedEventResult(true);

					
					while (System.currentTimeMillis() < asynchTimeout) {
						if(TestSession.isAtleastOneValidationErrorPresent()) break;
						if(createdEventResult.isExpectedCreatedEventReceived()) break;
						if(createdEventResultOpt.isExpectedCreatedEventReceived()) break;
						
						pause(5);
					}
					
					// DualAction Dialog
					UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
					uiUserPromptDualAction.Prompt(resourceFileReader
							.TestCase_0280_UIUserPromptDualActionQuestion(),
							uiUserPromptDualAction.SMALL);

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

					// If user did not close the dual action dialog
					if (!TestSession.isUserCancelledCompleteUIDualAction()) {

						// See if Async optOut was received
						if (createdEventResult.isExpectedCreatedEventReceived() || createdEventResultOpt.isExpectedCreatedEventReceived()) {
							LogHelper.addTrace("Received Asyc optOut");
						} else {
							LogHelper.setResult(LogResult.FAIL);
							LogHelper.addTrace("Did not receive asyn optOut");
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
