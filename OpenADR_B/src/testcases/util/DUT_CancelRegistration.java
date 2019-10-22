package testcases.util;

import testcases.pull.registerparty.vtn.N1_0030_TH_VEN;
import testcases.pull.registerparty.ven.N1_0040_TH_VTN_1;
import testcases.push.registerparty.vtn.N0_5030_TH_VEN;
import testcases.push.registerparty.ven.N0_5040_TH_VTN;

import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPromptDualAction;

public class DUT_CancelRegistration {
	public static void main(String[] args) {
		new UIUserPromptDualAction().Prompt("This utility will run the Cancel Registration sequence. Press yes if you are cancelling a VTN DUT registration, no if cancelling a VEN DUT registration.");
		if(TestSession.isUserClickedToCancelUIDualAction()){
			return;
		}
		
		boolean isVen = TestSession.isUiDualActionYesOptionClicked();
	
		
		if (isVen) {
			new UIUserPromptDualAction().Prompt("Press yes if you are in push mode, no if in pull mode.");
			boolean isPush = TestSession.isUiDualActionYesOptionClicked();
			if (isPush) {
				new N0_5030_TH_VEN().execute();
			} else {
				new N1_0030_TH_VEN().execute();
			}
		} else {
			new UIUserPromptDualAction().Prompt("Press yes if you are in push mode, no if in pull mode.");
			if(TestSession.isUserClickedToCancelUIDualAction()){
				return;
			}
		
			boolean isPush = TestSession.isUiDualActionYesOptionClicked();
			
			
			if (isPush) {
				new N0_5040_TH_VTN().execute();
			} else {
				new N1_0040_TH_VTN_1().execute();
			}
		}
	}
}
