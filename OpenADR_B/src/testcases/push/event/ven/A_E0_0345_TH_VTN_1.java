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
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

@SuppressWarnings("restriction")
public class A_E0_0345_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0345_TH_VTN_1 testClass = new A_E0_0345_TH_VTN_1();
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
		uiUserPrompt.Prompt(resourceFileReader.Prereq_NoEvents_Message());

		if (TestSession.isUserClickedContinuePlay()) {

			IDistributeEventAction distributeEventAction1 = new Default_Push_CPP_DistributeEventAction();
			int firstIntervalStartTimeDelayMin = 2;
			DistributeEventSignalHelper
					.setNewReqIDEvntIDStartTimeAndMarketCtx1(
							distributeEventAction1.getDistributeEvent(),
							firstIntervalStartTimeDelayMin);
			distributeEventAction1.getDistributeEvent().getOadrEvent().get(0)
					.getEiEvent().getEventDescriptor()
					.setEventStatus(EventStatusEnumeratedType.CANCELLED);
			distributeEventAction1.getDistributeEvent().getOadrEvent().get(0)
					.getEiEvent().getEiEventSignals().getEiEventSignal().get(0)
					.getCurrentValue().getPayloadFloat().setValue(0);
			distributeEventAction1.getDistributeEvent().getOadrEvent().get(0)
					.getEiEvent().getEventDescriptor().setModificationNumber(2);

			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);
			distributeEventAction1.setEventCompleted(true);
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedTotalCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			// Up to 2 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();

			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			VTNService.startVTNService();

			JAXBContext context = JAXBContext
					.newInstance(OadrDistributeEventType.class);
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

			if (createdEventResult1.isExpectedCreatedEventReceived()) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received the matching OadrCreatedEvent");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Did not receive the matching OadrCreatedEvent");
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
