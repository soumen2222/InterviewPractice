package testcases.push.event.ven;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_0260_Push_DistributeEventAction_OneEvent_TwoInterval;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0260_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0260_TH_VTN_1 testClass = new A_E0_0260_TH_VTN_1();
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
				+ "\n\n" + resourceFileReader.TestCase_0260_UIUserPromptText()+ "\n\n" + resourceFileReader.TestCase_RandomizedCancellationFailWarningPrompt(),
				uiUserPrompt.LARGE);

		if (TestSession.isUserClickedContinuePlay()) {
	
			// Create DistributeEventAction Action
			IDistributeEventAction distributeEventAction1 = new E1_0260_Push_DistributeEventAction_OneEvent_TwoInterval();
			distributeEventAction1.setEventCompleted(true);
			// Add DistributeEventAction to DistributeEventActionList
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

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

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);
			distributeEventAction1.startCreatedEventTimeoutActionThread();

			// Pause for Timeout before proceeding with TestCase validation.
			pauseForTestCaseTimeout();

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			// If Created Event has been Received.
			if (createdEventResult.isExpectedCreatedEventReceived()) {
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN);

				UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
				uiUserPromptDualAction.Prompt(resourceFileReader
						.TestCase_0260_UIUserPromptDualActionQuestion(),
						uiUserPromptDualAction.SMALL);

				atleastOneValidationErrorPresent = TestSession
						.isAtleastOneValidationErrorPresent();
				if (atleastOneValidationErrorPresent) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

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
					// If user closed action dialog
					LogHelper.setResult(LogResult.NA);
					LogHelper
							.addTrace("TestCase execution was cancelled by the user");
				}
				
			} else { // No matching Created event received
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Did not receive matching OadrCreatedEventType with "
								+ OptTypeType.OPT_IN);
				return;
			}

		} else { // User closed the single action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}
	}

	@Override
	public void cleanUp() throws Exception {
		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2.Prompt("If this test case exited before the full 3 minute event duration, you will need to wait until the event completes or reset the VEN to avoid overlapping event collisions due to randomized cancellation.",uiUserPrompt2.SMALL);
		
		VTNService.stopVTNService();
	}
}
