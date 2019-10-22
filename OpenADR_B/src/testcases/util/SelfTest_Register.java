package testcases.util;

import testcases.pull.registerparty.ven.N1_0020_TH_VTN_1;
import testcases.push.registerparty.ven.N0_5020_TH_VTN_1;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;

public class SelfTest_Register {
	public static void main(String[] args) {
		System.setProperty("th.test", "selftest");
		TestSession.setTestRunner(true);
		
		new UIUserPromptDualAction().Prompt("This utility will run the Self Test Registration Bootstrap sequence. Press yes if you are in push mode, no if in pull mode.");
		if(TestSession.isUserClickedToCancelUIDualAction()){
			return;
		}
		
		boolean isPush = TestSession.isUiDualActionYesOptionClicked();
		if (isPush) {
			new N0_5020_TH_VTN_1().execute();
		} else {
			new N1_0020_TH_VTN_1().execute();
		}
	}
}
