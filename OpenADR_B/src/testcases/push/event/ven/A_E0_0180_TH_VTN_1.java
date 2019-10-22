package testcases.push.event.ven;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Push_CPP_DistributeEventAction_twoEvents;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.EiEventType;
import com.qualitylogic.openadr.core.signal.EventStatusEnumeratedType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType.OadrEvent;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0180_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0180_TH_VTN_1 testClass = new A_E0_0180_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			VTNService.startVTNService();

			IDistributeEventAction distributeEventAction1 = new Push_CPP_DistributeEventAction_twoEvents();
			OadrDistributeEventType oadrDistributeEvent = distributeEventAction1
					.getDistributeEvent();
			int firstIntervalStartTimeDelayMin = 5;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							oadrDistributeEvent, firstIntervalStartTimeDelayMin);
			XMLGregorianCalendar currentTime = oadrDistributeEvent
					.getOadrEvent().get(1).getEiEvent().getEventDescriptor()
					.getCreatedDateTime();
			XMLGregorianCalendar event2StartTime = (XMLGregorianCalendar) currentTime
					.clone();
			Duration durationToAdd = OadrUtil.createDuration(10, 0);
			event2StartTime.add(durationToAdd);
			oadrDistributeEvent.getOadrEvent().get(1).getEiEvent()
					.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(event2StartTime);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setExpectedOptInCount(2);
			createdEventResult1.setLastCreatedEventResult(true);

			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			String strOadrDistributeEvent1 = SchemaHelper
					.getDistributeEventAsString(oadrDistributeEvent);

			distributeEventAction1.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent1);


			// Start Expected Created Event Timeout thread without pause.
			distributeEventAction1.startCreatedEventTimeoutActionThread();
			pauseForTestCaseTimeout();
			if (TestSession.isAtleastOneValidationErrorPresent()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");

				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (!createdEventResult1.isExpectedCreatedEventReceived()) {

				LogHelper.addTrace("No prerequisit OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " has been received");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

				return;
			} else {
				LogHelper
						.addTrace("Received matching prerequisit OadrCreatedEventType with "
								+ OptTypeType.OPT_IN);
			}


			IDistributeEventAction distributeEventAction2 = Clone
					.clone(distributeEventAction1);
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction2);

			ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult2.addDistributeEvent(distributeEventAction2);
			createdEventResult2.setExpectedTotalCount(3);
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

			XMLGregorianCalendar event2StartTimeModifedEvent = distributeEvent2
					.getOadrEvent().get(0).getEiEvent().getEiActivePeriod()
					.getProperties().getDtstart().getDateTime();
			Duration durationToAddModify = OadrUtil.createDuration(1, 0);
			event2StartTimeModifedEvent.add(durationToAddModify);

			distributeEvent2.getOadrEvent().get(1).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);

			PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
			OadrDistributeEventType newOadrDistributeEvent = new DistributeEventSignalHelper()
					.loadOadrDistributeEvent("oadrDistributeEvent_Push_CPP_Default.xml");
			EiEventType newEiEvent = newOadrDistributeEvent.getOadrEvent().get(0)
					.getEiEvent();

			newEiEvent.getEventDescriptor().setEventID(
					OadrUtil.createDescriptorEventID());
			newEiEvent
					.getEventDescriptor()
					.getEiMarketContext()
					.setMarketContext(
							propertiesFileReader.get("DR_MarketContext_1_Name"));

			XMLGregorianCalendar createdTime = OadrUtil.getCurrentTime();
			newEiEvent.getEventDescriptor().setCreatedDateTime(createdTime);

			XMLGregorianCalendar nextStartDateTime = (XMLGregorianCalendar) createdTime
					.clone();
			nextStartDateTime.add(durationToAddModify);
			newEiEvent.getEiActivePeriod().getProperties().getDtstart()
					.setDateTime(nextStartDateTime);

			OadrEvent oadrEvent = new OadrEvent();
			oadrEvent.setOadrResponseRequired(ResponseRequiredType.ALWAYS);
			oadrEvent.setEiEvent(newEiEvent);

			distributeEvent2.getOadrEvent().add(0, oadrEvent);

			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

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

			if (!createdEventResult2.isExpectedCreatedEventReceived()) {

				LogHelper.addTrace("No OadrCreatedEventType with "
						+ OptTypeType.OPT_IN + " has been received");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);

				return;
			} else {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received matching OadrCreatedEventType with "
						+ OptTypeType.OPT_IN);
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
