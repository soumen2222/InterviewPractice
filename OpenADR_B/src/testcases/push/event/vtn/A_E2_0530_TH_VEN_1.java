package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E2_0530_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E2_0530_TH_VEN_1 testClass = new A_E2_0530_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		ResourceFileReader resourceFileReader = new ResourceFileReader();

		VENService.startVENService();

		String message = resourceFileReader.TestCase_0530_UIUserPromptText();
		uiUserPrompt.Prompt(message, uiUserPrompt.MEDIUM);
		if (TestSession.isUserClickedContinuePlay() == false) {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Test Case has Failed");
			return;
		}

		// DualAction Dialog
		String uiUserPromptDualActionQuestion = resourceFileReader
				.TestCase_0530_UIUserPromptDualActionQuestion();
		UIUserPromptDualAction uiUserPromptDualAction = new UIUserPromptDualAction();
		uiUserPromptDualAction.Prompt(uiUserPromptDualActionQuestion);

		// If user did not close the dual action dialog
		if (!TestSession.isUserCancelledCompleteUIDualAction()) {

			// If user choose Yes option
			if (TestSession.isUiDualActionYesOptionClicked()) {
				LogHelper.setResult(LogResult.PASS);
				LogHelper.addTrace(resourceFileReader
						.LogMessage_VerifiedActiveEvent());
				LogHelper.addTrace("Test Case has Passed");

			} else {
				// If user choose No option
				LogHelper.setResult(LogResult.FAIL);
				LogHelper.addTrace(resourceFileReader
						.LogMessage_UnableTOVerifyActiveEvent());
				LogHelper.addTrace("Test Case has Failed");
			}
		} else {
			// If user chose dual action dialog
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
		}
	}

	@Override
	public void cleanUp() throws Exception {
		OadrUtil.Push_VTN_Shutdown_Prompt();

		VENService.stopVENService();
	}

}
