package testcases.push.event.ven;

import javax.xml.bind.JAXBContext;

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
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0096_TH_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0096_TH_VTN testClass = new A_E0_0096_TH_VTN();
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
			ICreatedEventResult createdEventResult = new Default_CreatedEventResultOptINOptOut();
			createdEventResult.addDistributeEvent(distributeEventAction1);
			createdEventResult.setExpectedTotalCount(0);
			createdEventResult.setLastCreatedEventResult(true);

			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult);

			IResponseEventAction responseEventAction = new Default_ResponseEventAction();
			ResponseEventActionList.addResponseEventAction(responseEventAction);

			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			distributeEventAction1.setEventCompleted(true);

			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(distributeEvent, -1);
			distributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.ACTIVE);
			distributeEvent.getOadrEvent().get(0).getEiEvent()
					.getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue((float) 1.0);
			distributeEvent.getOadrEvent().get(0)
					.setOadrResponseRequired(ResponseRequiredType.NEVER);
			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEvent);

			VtnToVenClient.post(strOadrDistributeEvent);
			
			// Start Expected Created Event Timeout thread without pause.
			distributeEventAction1.startCreatedEventTimeoutActionThread();

			pauseForTestCaseTimeout();

			Trace trace = TestSession.getTraceObj();

			pause(20);
			if (TestSession.isAtleastOneValidationErrorPresent()
					&& trace != null) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Validation Error(s) are present");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}

			if (createdEventResult.isExpectedCreatedEventReceived()) {
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
