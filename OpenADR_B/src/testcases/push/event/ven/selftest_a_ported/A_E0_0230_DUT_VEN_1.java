package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E0_0230_DUT_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0230_DUT_VEN_1 testClass = new A_E0_0230_DUT_VEN_1();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				false, true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				false, true);
		CreatedEventActionList.addCreatedEventAction(createdAction2);

		VENService.startVENService();



		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt("------------Special Instruction------------\n1)This DUT routine depends on the TH_VTN prompt action performed exactly as instructed in the TH_VTN prompt.");
	
		if (!TestSession.isUserClickedContinuePlay()) {
	
			return;
		}

		long totalDurationInput = 200000;
		long testCaseTimeout = System.currentTimeMillis() + totalDurationInput;
	
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(10);
			if (createdAction2.isEventCompleted()) {
				break;
			}
		}


	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}