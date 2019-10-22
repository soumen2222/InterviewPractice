package testcases.push.event.ven;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

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
import com.qualitylogic.openadr.core.signal.EiCreatedEvent;
import com.qualitylogic.openadr.core.signal.EventResponses.EventResponse;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0392_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0392_TH_VTN_1 testClass = new A_E0_0392_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ConformanceRuleValidator
				.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {
			VTNService.startVTNService();

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			OadrDistributeEventType distributeEvent1 = distributeEventAction1
					.getDistributeEvent();

			int firstIntervalStartTimeDelayMin = 2;

			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent1,
							firstIntervalStartTimeDelayMin);
			distributeEvent1.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(5);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.setLastCreatedEventResult(true);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			distributeEvent1.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());

			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			String strOadrDistributeEvent1 = SchemaHelper
					.getDistributeEventAsString(distributeEvent1);
			
			distributeEventAction1.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent1);

			// Start timeout monitor thread and pause for Created Event Timeout
			distributeEventAction1
					.startCreatedEventTimeoutActionThreadWithPause();

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {

				LogHelper.addTrace("No prerequisit OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " has been received");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

				return;
			}

			// Pause for 5 seconds
			pause(5);
			
			// Prerequisite Section Done - Proceed to Modify event

			IDistributeEventAction distributeEventAction2 = Clone
					.clone(distributeEventAction1);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction2);

			distributeEventAction1.getDistributeEvent().getOadrEvent().get(0)
					.getEiEvent().getEventDescriptor().getEventID();

			OadrDistributeEventType distributeEvent2 = distributeEventAction2
					.getDistributeEvent();
			distributeEvent2.setRequestID(OadrUtil
					.createoadrDistributeRequestID());

			String evntID = distributeEvent1.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().getEventID();

			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());
			distributeEvent2.setRequestID(OadrUtil
					.createoadrDistributeRequestID());
			XMLGregorianCalendar startTime = distributeEvent2.getOadrEvent()
					.get(0).getEiEvent().getEiActivePeriod().getProperties()
					.getDtstart().getDateTime();
			Duration duration = OadrUtil.createDuration("PT1M");
			startTime.add(duration);

			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setModificationNumber(3);
			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor().setEventID(evntID);

			ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult2.addDistributeEvent(distributeEventAction2);
			createdEventResult2.setExpectedTotalCount(0);
			createdEventResult2.setLastCreatedEventResult(true);
			distributeEventAction2.setEventCompleted(false);

			distributeEventAction2
					.setCreatedEventSuccessCriteria(createdEventResult2);

			distributeEventAction2.setEventCompleted(false);

			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);
			distributeEvent2.getOadrEvent().get(0)
					.setOadrResponseRequired(ResponseRequiredType.ALWAYS);
			String strOadrDistributeEvent2 = SchemaHelper
					.getDistributeEventAsString(distributeEvent2);

			distributeEventAction2.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent2);

			// Start Expected Created Event Timeout thread without pause.
			distributeEventAction2.startCreatedEventTimeoutActionThread();

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
					break;
				}
			}

			if (!isExpectedEventReceived) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Did not receive the expected number of CreatedEvents");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

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

		} else { // User closed the dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
