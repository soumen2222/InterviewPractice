package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0240_DistributeEventAction_1;
import com.qualitylogic.openadr.core.action.impl.E0_0240_DistributeEventAction_2;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0240_TH_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0240_TH_VTN testClass = new A_E0_0240_TH_VTN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		VTNService.startVTNService();

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {
			IDistributeEventAction distributeEventAction1 = new E0_0240_DistributeEventAction_1();
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setLastCreatedEventResult(true);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			distributeEventAction1.setEventCompleted(true);

			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEventAction1.getDistributeEvent());

			VtnToVenClient.post(strOadrDistributeEvent);
			distributeEventAction1.startCreatedEventTimeoutActionThread();

			pauseForTestCaseTimeout();


			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {
				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN
						+ " has been received for the prerequisite");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
			} else {

				String message = resourceFileReader
						.TestCase_0240_UIUserPromptText();
				UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
				uiUserPrompt2.Prompt(message);

				// See if there were any validation errors
				atleastOneValidationErrorPresent = TestSession
						.isAtleastOneValidationErrorPresent();
				if (atleastOneValidationErrorPresent) {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper.addTrace("Validation Error(s) are present");
					LogHelper.addTrace("Test Case has Failed");
					return;
				}

				if (TestSession.isUserClickedContinuePlay()) {

					IDistributeEventAction distributeEventAction2 = new E0_0240_DistributeEventAction_2();
					DistributeEventActionList
							.addDistributeEventAction(distributeEventAction2);
					ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
					createdEventResult2.setExpectedTotalCount(1);
					createdEventResult2
							.addDistributeEvent(distributeEventAction2);
					distributeEventAction2
							.setCreatedEventSuccessCriteria(createdEventResult2);
					createdEventResult2.setLastCreatedEventResult(true);
					distributeEventAction2.setEventCompleted(true);
					IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
					ResponseEventActionList
							.addResponseEventAction(responseEventAction2);

					strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString( 
							distributeEventAction2.getDistributeEvent());

					VtnToVenClient.post(strOadrDistributeEvent);
					distributeEventAction2.startCreatedEventTimeoutActionThread();
					pauseForTestCaseTimeout();

					// See of there were any validation errors
					if (atleastOneValidationErrorPresent) {
						LogHelper.setResult(LogResult.FAIL);
						LogHelper.addTrace("Validation Error(s) are present");
						LogHelper.addTrace("Test Case has Failed");
						return;
					}

					if (createdEventResult2.isExpectedCreatedEventReceived()) {
						LogHelper
								.addTrace("Received matching OadrCreatedEventType with "
										+ OptTypeType.OPT_IN);


						String uiUserPromptDualActionQuestion = resourceFileReader
								.TestCase_0240_UIUserPromptDualActionQuestion();
						UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
						uiUserPromptDualAction
								.Prompt(uiUserPromptDualActionQuestion);

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
								LogHelper
										.addTrace(resourceFileReader
												.LogMessage_UnableTOVerifyActiveEvent());
								LogHelper.addTrace("Test Case has Failed");
							}
						} else {
							// User closed the action dialog
							LogHelper.setResult(LogResult.NA);
							LogHelper
									.addTrace("TestCase execution was cancelled by the user");
						}

					} else {
						LogHelper.addTrace("No matching OadrCreatedEventType with "
								+ OptTypeType.OPT_IN + " or "
								+ OptTypeType.OPT_OUT + " has been received");
						LogHelper.addTrace("Test Case has Failed");
						LogHelper.setResult(LogResult.FAIL);
					}
				} else {
					// User closed the action dialog
					LogHelper.setResult(LogResult.NA);
					LogHelper
							.addTrace("TestCase execution was cancelled by the user");
				}

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
