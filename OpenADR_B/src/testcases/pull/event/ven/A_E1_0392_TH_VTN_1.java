package testcases.pull.event.ven;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_0392_DistributeEventAction_1;
import com.qualitylogic.openadr.core.action.impl.E1_0392_DistributeEventAction_2;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0392_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0392_TH_VTN_1 testClass = new A_E1_0392_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		// Prompt to make sure events are cleared from DUT
		ConformanceRuleValidator
				.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new E1_0392_DistributeEventAction_1();
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			OadrPollQueue.addToQueue(distributeEventAction1); 

			IDistributeEventAction distributeEventAction2 = new E1_0392_DistributeEventAction_2();
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

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			long totalDurationInput = Long.valueOf(propertiesFileReader
					.get("asyncResponseTimeout"));
			long totalDurationToRun = System.currentTimeMillis()
					+ totalDurationInput;

			boolean isExpectedEventReceived = false;
			while (totalDurationToRun > System.currentTimeMillis()) {
				pause(10);
				ArrayList<OadrCreatedEventType> createdEventList = TestSession
						.getCreatedEventReceivedList();

				if (createdEventList.size() > 1) {
					isExpectedEventReceived = true;
				}
			}

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();
			if (atleastOneValidationErrorPresent) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (!isExpectedEventReceived) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Did not receive the expected number of CreatedEvents");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {
				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN
						+ " has been received for the prerequisite");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				return;
			} else {
				LogHelper.addTrace("Expected OadrCreatedEventType with "
						+ OptTypeType.OPT_IN
						+ " has been received for the prerequisite");

				ArrayList<OadrCreatedEventType> createdEventList = TestSession
						.getCreatedEventReceivedList();
				OadrCreatedEventType oadrCreatedEvent = createdEventList.get(1);
				EiCreatedEvent createdEvent = oadrCreatedEvent
						.getEiCreatedEvent();

				List<EventResponse> eventResponses = createdEvent
						.getEventResponses().getEventResponse();

				EventResponse eventResponse = eventResponses.get(0);

				String reqID = eventResponse.getRequestID();
				String eventID = eventResponse.getQualifiedEventID()
						.getEventID();
				long modificationNumber = eventResponse.getQualifiedEventID()
						.getModificationNumber();
				String responseCode = eventResponse.getResponseCode();

				OadrDistributeEventType distributeEvent = distributeEventAction2
						.getDistributeEvent();
				String requestIDToMatch = distributeEvent.getRequestID();
				String eventIDToMatch = distributeEvent.getOadrEvent().get(0)
						.getEiEvent().getEventDescriptor().getEventID();

				if (requestIDToMatch.equals(reqID)
						&& eventIDToMatch.equals(eventID)
						&& modificationNumber == 3
						&& responseCode.startsWith("4")) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received the Error OadrCreatedEventType as expected");
					LogHelper.addTrace("Test Case has Passed");
				} else {
					LogHelper
							.addTrace("Did not received the Error OadrCreatedEventType as expected");
					LogHelper.addTrace("Test Case has Failed");
					LogHelper.setResult(LogResult.FAIL);

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
