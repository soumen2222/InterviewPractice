package testcases.pull.event.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_NoEvent_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.util.Trace;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E1_0020_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0020_TH_VTN_1 testClass = new A_E1_0020_TH_VTN_1();
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
			IDistributeEventAction distributeEventAction1 = new Default_NoEvent_DistributeEventAction();
			DistributeEventActionList.addDistributeEventAction(distributeEventAction1);
			
			// Create CreatedEventResult
			/*
			ICreatedEventResult createdEventResult = new Default_CreatedEventResultOptINOptOut();
			createdEventResult.addDistributeEvent(distributeEventAction1);
			createdEventResult.setExpectedOptInCount(1);
			createdEventResult.setExpectedOptOutCount(0);
			createdEventResult.setLastCreatedEventResult(true);
			*/
			
/*			IResponseEventAction responseEventAction = new Default_ResponseEventAction();
			ResponseEventActionList.addResponseEventAction(responseEventAction);
			responseEventAction.setlastEvent(true);
*/
			VTNService.startVTNService();


			boolean atleastOneValidationErrorPresent = TestSession
					.isAtleastOneValidationErrorPresent();

			long testCaseTimeout = OadrUtil.getTestCaseTimeout();
			

			while (System.currentTimeMillis() < testCaseTimeout) {
				pause(2);
				if(distributeEventAction1.isEventCompleted()){
					break;
				}
			}
			
			testCaseTimeout = OadrUtil.getCreatedEventAsynchTimeout();
			long totalDurationInput = Long.valueOf(new PropertiesFileReader()
					.get("createdEventAsynchTimeout"));
			System.out.println("\n***Test case will pause for "+totalDurationInput+" milliseconds to see if any unexpected OadrCreatedEvent in received. This value is configured in createdEventAsynchTimeout property in the properties file");
			while (System.currentTimeMillis() < testCaseTimeout) {
				pause(5);
			}
			
			
			
			Trace trace = TestSession.getTraceObj();
			if (atleastOneValidationErrorPresent && trace != null) {
				trace.setResult("Fail");
				trace.getLogFileContentTrace().append(
						"\nValidation Error(s) are present \n");
				trace.getLogFileContentTrace().append(
						"\nTest Case has Failed \n");
				return;
			}

			ArrayList<OadrCreatedEventType> createdEventReceivedList = TestSession
					.getCreatedEventReceivedList();

			if (distributeEventAction1.isEventCompleted()
					&& createdEventReceivedList.size() == 0) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace("Received no OadrCreatedEventType as expected");
				LogHelper.addTrace("Test Case has Passed");
			} else {
				if (distributeEventAction1.isEventCompleted()) {
					LogHelper
							.addTrace("No OadrCreatedEventType was expected. Received "
									+ createdEventReceivedList.size()
									+ " OadrCreatedEvent");
				} else {
					LogHelper
							.addTrace("Testcase Timeout occured waiting for oadrRequestEvent");
				}
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
