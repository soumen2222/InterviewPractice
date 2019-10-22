package testcases.pull.event.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_0285_DistributeEventAction_1;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.CreatedEventBean;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0285_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0285_TH_VTN_1 testClass = new A_E1_0285_TH_VTN_1();
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

			IDistributeEventAction distributeEventAction1 = new E1_0285_DistributeEventAction_1();
			OadrPollQueue.addToQueue(distributeEventAction1); 

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptOutCount(1);
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			// Upto 2 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
			IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();

			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);
			ResponseEventActionList
					.addResponseEventAction(responseEventAction2);

			VTNService.startVTNService();

			pauseForTestCaseTimeout();

			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();

			// Run the testcase
			Trace trace = TestSession.getTraceObj();
			if (atleastOneValidationErrorPresent && trace != null) {
				trace.setResult("Fail");
				trace.getLogFileContentTrace().append(
						"\nValidation Error(s) are present \n");
				trace.getLogFileContentTrace().append(
						"\nTest Case has Failed \n");
				return;
			}

			if (!distributeEventAction1.isEventCompleted()) {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("Distribute event was not dispatched. Either oadrRequestEvent was not received or the replyLimit in it was less than the 2");
				LogHelper.addTrace("Test Case has Failed");
				return;
			}
			// Set flags for what combo of optIn and OptOut received
			boolean isOneEventOptInOptOut = createdEventResult1
					.isExpectedCreatedEventReceived();
			createdEventResult1.setExpectedOptOutCount(0);
			createdEventResult1.setExpectedOptInCount(2);
			boolean isTwoEventOptIn = createdEventResult1
					.isExpectedCreatedEventReceived();

			// Get the created event received by VTN.
			ArrayList<OadrCreatedEventType> createdEventReceivedList = TestSession
					.getCreatedEventReceivedList();
			ArrayList<CreatedEventBean> createdEventBeanList1 = OadrUtil
					.transformToCreatedEventBeanList(createdEventReceivedList);
			OadrDistributeEventType distributeEvent = distributeEventAction1
					.getDistributeEvent();
			OptTypeType optType = DistributeEventSignalHelper
					.getOptTypeInOptedCreatedEvent(distributeEvent
							.getOadrEvent().get(0).getEiEvent(),
							distributeEvent, createdEventBeanList1);

			if (isTwoEventOptIn) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("VEN did OptIn on both overlapping events in different market contexts.");
				LogHelper.addTrace("Test Case has Passed");
			} else if (isOneEventOptInOptOut && optType == OptTypeType.OPT_OUT) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("VEN opted in to the higher priority event only");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("VEN did not OptIn to both events or to just the higher priority event as expected");
				LogHelper.addTrace("Test Case has Failed");

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
