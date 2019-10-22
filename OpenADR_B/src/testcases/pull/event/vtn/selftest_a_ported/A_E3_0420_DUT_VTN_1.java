package testcases.pull.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E3_0420_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0420_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0420_DUT_VTN_1 testClass = new A_E3_0420_DUT_VTN_1();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new E3_0420_Pull_CPP_DistributeEventAction();
		DistributeEventActionList.addDistributeEventAction(distributeEventAction1);
		
		IResponseEventAction responseEventAction = new Default_ResponseEventAction();
		responseEventAction.setlastEvent(true);
		ResponseEventActionList.addResponseEventAction(responseEventAction);

		VTNService.startVTNService();

		pauseForTestCaseTimeout();
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
