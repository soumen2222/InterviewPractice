package testcases.push.event.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0360_DistributeEventAction_1;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0360_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0360_TH_VTN_1 testClass = new A_E0_0360_TH_VTN_1();
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
				.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		//ConformanceRuleValidator
			//	.setDisableDistributeEvent_SignalNameSimple_Check(true);
		ConformanceRuleValidator.setDisable_MaketContext_Check(true);
		// Prompt to make sure events are cleared from DUT
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new E0_0360_DistributeEventAction_1();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);
			distributeEventAction1.setEventCompleted(true);
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.setExpectedOptOutCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			// Up to 2 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();

			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			VTNService.startVTNService();

			String strOadrDistributeEvent = SchemaHelper
					.getDistributeEventAsString(distributeEventAction1.getDistributeEvent());

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
				trace.getLogFileContentTrace().append(
						"\nTest Case has Failed \n");
				return;
			}

			// See if expected error is returned from DUT
			boolean isExpectedError = false;
			if (!TestSession.getCreatedEventReceivedList().isEmpty()) {
				OadrDistributeEventType distributeEvent = distributeEventAction1
						.getDistributeEvent();

				OadrEvent event1 = distributeEvent.getOadrEvent().get(0);
				OadrEvent event2 = distributeEvent.getOadrEvent().get(1);
				String eventID1 = event1.getEiEvent().getEventDescriptor()
						.getEventID();
				String eventID2 = event2.getEiEvent().getEventDescriptor()
						.getEventID();

				ArrayList<OadrCreatedEventType> oadrCreatedEventList = TestSession
						.getCreatedEventReceivedList();

				EventResponse eventResponse1 = CreatedEventHelper
						.getCreatedEventEventResponse(oadrCreatedEventList,
								eventID1);
				EventResponse eventResponse2 = CreatedEventHelper
						.getCreatedEventEventResponse(oadrCreatedEventList,
								eventID2);

				if (eventResponse1 != null
						&& eventResponse1.getResponseCode().startsWith("4")
						&& eventResponse2 != null
						&& eventResponse2.getResponseCode().startsWith("2")) {
					isExpectedError = true;
				}

			}

			if (trace != null) {

				if (createdEventResult1.isExpectedCreatedEventReceived()
						&& isExpectedError) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received eventResponse 4xx error with optOut in oadrCreatedEvent");
					LogHelper.addTrace("Test Case has Passed");
				} else {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Did not receive the expected eventResponse");
					LogHelper.addTrace("Test Case has Failed");

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
