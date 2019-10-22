package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseCreatedOptAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseCreatedOptAckActionList;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultOadrCreatedOptEvent;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Push_DistributeEventAction_OneEvent_TwoInterval;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0280_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0280_TH_VTN_1 testClass = new A_E0_0280_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		// Disable validation Check
		ConformanceRuleValidator
				.setDisable_MaketContext_Check(true);
		ConformanceRuleValidator
				.setDisable_VtnIDValid_Check(true);

		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message(),
				UIUserPrompt.SMALL);

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new Push_DistributeEventAction_OneEvent_TwoInterval();
			OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
					.getDistributeEvent();
			EiEventType eiEvent = oadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();
			DistributeEventSignalHelper
					.modificationRuleStartDtCurrentPlusMinutes(eiEvent, eiEvent
							.getEventDescriptor().getCreatedDateTime(), 1);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);
			distributeEventAction1.setEventCompleted(true);
			// Up to 4 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			VTNService.startVTNService();

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();

			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);
			long asynchTimeout = OadrUtil.getCreatedEventAsynchTimeout();

			while (System.currentTimeMillis() < asynchTimeout) {
				pause(5);
				if (createdEventResult1.isExpectedCreatedEventReceived()) {
					break;
				}

			}

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();

			Trace trace = TestSession.getTraceObj();
			if (atleastOneValidationErrorPresent && trace != null) {
				trace.setResult("Fail");
				trace.getLogFileContentTrace().append(
						"\nValidation Error(s) are present \n");
				trace.getLogFileContentTrace().append(
						"\nTest Case has Failed \n");
				return;
			}

			if (createdEventResult1.isExpectedCreatedEventReceived()) {

				IResponseCreatedOptAckAction createdOpt = new DefaultOadrCreatedOptEvent();
				ResponseCreatedOptAckActionList.addResponseCreatedOptAckAction(createdOpt);
				
				
				UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
				uiUserPrompt2.Prompt(
						resourceFileReader.TestCase_0280_UIUserPromptText(),
						UIUserPrompt.MEDIUM);

				if (TestSession.isUserClickedContinuePlay()) {

					// Set for OptOut for created event as 1
					createdEventResult1.setExpectedOptOutCount(1);

					createdEventResult1.setLastCreatedEventResult(true);

/*					distributeEventAction1
							.startCreatedEventTimeoutActionThread();
*/
					ICreatedEventResult createdEventResultOpt = new Default_CreatedEventResultOptINOptOut();
					createdEventResultOpt.addDistributeEvent(distributeEventAction1);
					createdEventResultOpt.setExpectedOptInCount(1);
					createdEventResultOpt.setExpectedCreateOpt_OptOutCount(1);
					createdEventResultOpt.setLastCreatedEventResult(true);

					
					asynchTimeout = OadrUtil.getCreatedEventAsynchTimeout();

					while (System.currentTimeMillis() < asynchTimeout) {
						pause(5);
						if(TestSession.isAtleastOneValidationErrorPresent()) break;
						if (createdEventResult1.isExpectedCreatedEventReceived())break;						
						if (createdEventResultOpt.isExpectedCreatedEventReceived())break;
					}

					UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
					uiUserPromptDualAction.Prompt(resourceFileReader
							.TestCase_0280_UIUserPromptDualActionQuestion(),
							uiUserPromptDualAction.SMALL);

					// Fail if there were any validation errors.
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

						// Check if Async optOut was received
						if (createdEventResult1
								.isExpectedCreatedEventReceived() || createdEventResultOpt.isExpectedCreatedEventReceived()) {
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
						// If user closed the action dialog
						LogHelper.setResult(LogResult.NA);
						LogHelper
								.addTrace("TestCase execution was cancelled by the user");
					}

				} else { // User closed the action dialog
					LogHelper.setResult(LogResult.NA);
					LogHelper
							.addTrace("TestCase execution was cancelled by the user");
				}

			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Did not receive expected matching OadrCreatedEventType with "
								+ OptTypeType.OPT_IN);
				LogHelper.addTrace("Test Case has Failed");

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