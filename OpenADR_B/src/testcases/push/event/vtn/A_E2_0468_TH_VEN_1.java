package testcases.push.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0468_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0468_TH_VEN_1 testClass = new A_E2_0468_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		VENService.startVENService();

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(
				resourceFileReader.TestCase_0468_UIUserPromptText(), 1);

		if (!TestSession.isUserClickedContinuePlay()) {
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}
		
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		
		ArrayList<OadrDistributeEventType> distributeEventReceived = null;
		while (System.currentTimeMillis() < testCaseTimeout) {
			distributeEventReceived = VENServerResource
					.getOadrDistributeEventReceivedsList();
			pause(2);
			if(distributeEventReceived.size()>0) break;
			
		}
		distributeEventReceived = VENServerResource
				.getOadrDistributeEventReceivedsList();

		if (TestSession.isAtleastOneValidationErrorPresent()) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Validation Error(s) are present");

			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		if (distributeEventReceived.size() != 1) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("One DistributeEvent was expected");
			LogHelper.addTrace("Test Case has Failed");
			return;

		}
		if(!DistributeEventSignalHelper.isExpectedReceived("P",ResponseRequiredType.NEVER,0, distributeEventReceived.get(0).getOadrEvent().get(0))){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}
		
		if (distributeEventReceived.get(0).getOadrEvent().get(0)
				.getOadrResponseRequired() != ResponseRequiredType.NEVER) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("The received DistributeEvent did not have 'never' as oadrResponseRequired");
			LogHelper.addTrace("Test Case has Failed");
			return;
		} else {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Test Case has Passed");
			return;
		}
	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
