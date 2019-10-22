package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E1_0345_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0345_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0345_TH_VTN_1 testClass = new A_E1_0345_TH_VTN_1();
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

			IDistributeEventAction distributeEventAction1 = new E1_0345_Pull_CPP_DistributeEventAction();
			OadrPollQueue.addToQueue(distributeEventAction1); 

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedTotalCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			// Upto 1 CreatedEvents can be received.
			IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();

			ResponseEventActionList
					.addResponseEventAction(responseEventAction1);

			VTNService.startVTNService();

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
				LogHelper.addTrace("Received matching OadrCreatedEvent");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace("Did not receive matching OadrCreatedEvent");
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
