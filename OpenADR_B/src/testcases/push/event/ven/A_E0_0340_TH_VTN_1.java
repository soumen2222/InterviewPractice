package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0340_DistributeEventAction_1;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class A_E0_0340_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0340_TH_VTN_1 testClass = new A_E0_0340_TH_VTN_1();
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

			IDistributeEventAction distributeEventAction1 = new E0_0340_DistributeEventAction_1();
			DistributeEventActionList
					.addDistributeEventAction(distributeEventAction1);
			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedTotalCount(2);
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

			String strOadrDistributeEvent = SchemaHelper.getDistributeEventAsString(
					distributeEventAction1.getDistributeEvent());
			distributeEventAction1.setEventCompleted(true);
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

			if (trace != null) {

				if (createdEventResult1.isExpectedCreatedEventReceived()) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);
					LogHelper.addTrace("Test Case has Passed");
				} else {
					createdEventResult1.setExpectedOptInCount(0);
					createdEventResult1.setExpectedTotalCount(0); 
					if (!createdEventResult1.isExpectedCreatedEventReceived()) {
						LogHelper.setResult(LogResult.FAIL);
						LogHelper
								.addTrace("Received oadrCreatedEvent response for completed event");
						LogHelper.addTrace("Test Case has Failed");
					} else {
						LogHelper.setResult(LogResult.FAIL);
						LogHelper
								.addTrace("Did not receive expected matching OadrCreatedEventType with "
										+ OptTypeType.OPT_IN);
						LogHelper.addTrace("Test Case has Failed");
					}
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Did not receive expected matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);
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
