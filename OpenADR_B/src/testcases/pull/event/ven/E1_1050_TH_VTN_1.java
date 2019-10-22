package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventResultOptINOptOut;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class E1_1050_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			E1_1050_TH_VTN_1 testClass = new E1_1050_TH_VTN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

			IDistributeEventAction distributeEventAction1 = new DefaultDistributeEvent_002Action();
			OadrPollQueue.addToQueue(distributeEventAction1);

			ICreatedEventResult createdEventResult1 = new Default_CreatedEventResultOptINOptOut();
			createdEventResult1.setExpectedOptInCount(1);
			createdEventResult1.addDistributeEvent(distributeEventAction1);
			distributeEventAction1
					.setCreatedEventSuccessCriteria(createdEventResult1);
			createdEventResult1.setLastCreatedEventResult(true);

			IResponseEventAction responseEventAction = new Default_ResponseEventAction();
			ResponseEventActionList.addResponseEventAction(responseEventAction);
			responseEventAction.setlastEvent(true);

			VTNService.startVTNService();
			
			if (!TestSession.isUserClickedContinuePlay()) {
				 // User closed the single action dialog
				LogHelper.setResult(LogResult.NA);
				LogHelper.addTrace("TestCase execution was cancelled by the user");
			}
			
			pauseForTestCaseTimeout();

			if(OadrUtil.isValidationErrorPresent()){
				return;
			}
			
			if (createdEventResult1.isExpectedCreatedEventReceived()) {
					LogHelper.setResult(LogResult.PASS);
					LogHelper
							.addTrace("Received matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);
					LogHelper.addTrace("Test Case has Passed");
			} else {
					LogHelper.setResult(LogResult.FAIL);
					LogHelper
							.addTrace("Did not receive expected matching OadrCreatedEventType with "
									+ OptTypeType.OPT_IN);
					LogHelper.addTrace("Test Case has Failed");

			}
			
		}

	

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
