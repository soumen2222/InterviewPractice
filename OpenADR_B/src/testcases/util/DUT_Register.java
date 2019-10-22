package testcases.util;

import testcases.pull.registerparty.ven.N1_0020_TH_VTN_1;
import testcases.pull.registerparty.vtn.N1_0020_TH_VEN;
import testcases.push.registerparty.ven.N0_5020_TH_VTN_1;
import testcases.push.registerparty.vtn.N0_5020_TH_VEN;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;

public class DUT_Register {
	public static void main(String[] args) {
		new UIUserPromptDualAction().Prompt("This utility will run the Registration Bootstrap sequence. Press yes if you are registering a VTN DUT, no if registering a VEN DUT.");
		if(TestSession.isUserClickedToCancelUIDualAction()){
			return;
		}
		
		boolean isVen = TestSession.isUiDualActionYesOptionClicked();
		
		
		if (isVen) {
			new UIUserPromptDualAction().Prompt("Press yes if you are in push mode, no if in pull mode.");
			if(TestSession.isUserClickedToCancelUIDualAction()){
				return;
			}
			
			boolean isPush = TestSession.isUiDualActionYesOptionClicked();
			if (isPush) {
				new N0_5020_TH_VEN().execute();
			} else {
				new N1_0020_TH_VEN().execute();
			}
		} else {
			new UIUserPromptDualAction().Prompt("Press yes if you are in push mode, no if in pull mode.");
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
}
