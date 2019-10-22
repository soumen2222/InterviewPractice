package testcases.push.event.ven;

import java.util.ArrayList;
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
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
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
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0092_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0092_TH_VTN_1 testClass = new A_E0_0092_TH_VTN_1();
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

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.setLastCreatedEventResult(true);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);

			OadrDistributeEventType distributeEvent1 = distributeEventAction1
					.getDistributeEvent();

			distributeEvent1.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());
			DistributeEventSignalHelper
					.modificationRuleStartDtCurrentPlusMinutes(distributeEvent1
							.getOadrEvent().get(0).getEiEvent(),
							distributeEvent1.getOadrEvent().get(0).getEiEvent()
									.getEventDescriptor().getCreatedDateTime(),
							5);

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

			IDistributeEventAction distributeEventAction2 = Clone
					.clone(distributeEventAction1);
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction2);

			ICreatedEventResult createdEventResult2 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult2.addDistributeEvent(distributeEventAction2);
			createdEventResult2.setExpectedTotalCount(0);
			createdEventResult2.setLastCreatedEventResult(true);
			distributeEventAction2.setEventCompleted(false);

			distributeEventAction2
					.setCreatedEventSuccessCriteria(createdEventResult2);

			OadrDistributeEventType distributeEvent2 = distributeEventAction2
					.getDistributeEvent();
			distributeEvent2.setRequestID(OadrUtil
					.createoadrDistributeRequestID());

			List<OadrEvent> oadrEventList = distributeEvent2.getOadrEvent();
			XMLGregorianCalendar currentTime = OadrUtil.getCurrentTime();

			for (OadrEvent eachOadrEvent : oadrEventList) {
				XMLGregorianCalendar createdDateTime = (XMLGregorianCalendar) currentTime
						.clone();
				eachOadrEvent.getEiEvent().getEventDescriptor()
						.setCreatedDateTime(createdDateTime);
			}

			distributeEventAction2.setEventCompleted(false);

			distributeEvent2.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setCreatedDateTime(OadrUtil.getCurrentTime());
			DistributeEventSignalHelper
					.modificationRuleStartDtCurrentPlusMinutes(distributeEvent2
							.getOadrEvent().get(0).getEiEvent(),
							distributeEvent2.getOadrEvent().get(0).getEiEvent()
									.getEventDescriptor().getCreatedDateTime(),
							1);
			DistributeEventSignalHelper
					.modificationRuleIncrementModificationNumber(distributeEvent2
							.getOadrEvent().get(0).getEiEvent());

			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);
			distributeEvent2.getOadrEvent().get(0)
					.setOadrResponseRequired(ResponseRequiredType.NEVER);
			String strOadrDistributeEvent2 = SchemaHelper
					.getDistributeEventAsString(distributeEvent2);

			distributeEventAction2.setEventCompleted(true);
			VtnToVenClient.post(strOadrDistributeEvent2);

			// Start Expected Created Event Timeout thread without pause.
			distributeEventAction2.startCreatedEventTimeoutActionThread();

			pause(20);
			if (TestSession.isAtleastOneValidationErrorPresent()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");

				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			ArrayList<OadrCreatedEventType> createdEventList = TestSession
					.getCreatedEventReceivedList();

			if (createdEventList.size() == 1) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("Did not receive OadrCreatedEventType as expected");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.addTrace("Received unexpected OadrCreatedEvent");
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
