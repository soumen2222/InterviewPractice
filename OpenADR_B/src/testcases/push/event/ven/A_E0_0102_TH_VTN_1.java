package testcases.push.event.ven;

import java.util.List;

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
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0102_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0102_TH_VTN_1 testClass = new A_E0_0102_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableDistributeEvent_EventStatusValid_ValidCheck(true);

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {
			VTNService.startVTNService();

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setExpectedOptInCount(1);

			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			OadrDistributeEventType distributeEvent1 = distributeEventAction1
					.getDistributeEvent();

			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent1,
							0);
			distributeEvent1.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.ACTIVE);
			distributeEvent1.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue((float) 1.0);
			distributeEvent1.getOadrEvent().get(0)
					.setOadrResponseRequired(ResponseRequiredType.ALWAYS);

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
			
			// Prerequisite Section Done- Proceed to Modify event

			IDistributeEventAction distributeEventAction2 = Clone
					.clone(distributeEventAction1);
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction2);

			ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult2.addDistributeEvent(distributeEventAction2);
			createdEventResult2.setExpectedOptInCount(1);
			createdEventResult2.setLastCreatedEventResult(true);
			distributeEventAction2.setEventCompleted(false);

			distributeEventAction2
					.setCreatedEventSuccessCriteria(createdEventResult2);

			OadrDistributeEventType distributeEvent2 = distributeEventAction2
					.getDistributeEvent();
			distributeEvent2.setRequestID(OadrUtil
					.createoadrDistributeRequestID());
			distributeEventAction2.setEventCompleted(false);

			DistributeEventSignalHelper
					.modificationRuleIncrementModificationNumber(distributeEvent2
							.getOadrEvent().get(0).getEiEvent());
			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);
			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue(0);
			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			List<OadrEvent> oadrEventList = distributeEvent2.getOadrEvent();
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				XMLGregorianCalendar createdDateTime = (XMLGregorianCalendar) currentTime
						.clone();
				eachOadrEvent.getEiEvent().getEventDescriptor()
						.setCreatedDateTime(createdDateTime);
			}

			String strOadrDistributeEvent2 = SchemaHelper
					.getDistributeEventAsString(distributeEvent2);

			distributeEventAction2.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent2);

			// Start Expected Created Event Timeout thread without pause.
			distributeEventAction2.startCreatedEventTimeoutActionThread();

			pauseForTestCaseTimeout();

			if (TestSession.isAtleastOneValidationErrorPresent()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");

				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (createdEventResult2.isExpectedCreatedEventReceived()) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received expected OadrCreatedEvent");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.addTrace("No OadrCreatedEventType has been received");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

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
