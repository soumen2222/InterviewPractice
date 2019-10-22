package testcases.pull.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.DistributeEventActionList;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.E3_0655_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0655_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0655_DUT_VTN_1 testClass = new A_E3_0655_DUT_VTN_1();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		 }
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction4 = new E3_0655_CPP_DistributeEventAction();
		distributeEventAction4.getDistributeEvent();

		IDistributeEventAction distributeEventAction3 = Clone
				.clone(distributeEventAction4);

		IDistributeEventAction distributeEventAction2 = Clone
				.clone(distributeEventAction3);
		distributeEventAction2.getDistributeEvent().getOadrEvent().remove(2);

		IDistributeEventAction distributeEventAction1 = Clone
				.clone(distributeEventAction2);
		distributeEventAction1.getDistributeEvent().getOadrEvent().remove(1);

		IDistributeEventAction distributeEventAction0 = Clone
				.clone(distributeEventAction1);
		distributeEventAction0.getDistributeEvent().getOadrEvent().remove(0);

		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction0);
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction1);
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction2);
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction3);
		DistributeEventActionList
				.addDistributeEventAction(distributeEventAction4);

		IResponseEventAction responseEventAction1 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction1);
		IResponseEventAction responseEventAction2 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction2);
		IResponseEventAction responseEventAction3 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction3);
		IResponseEventAction responseEventAction4 = new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEventAction4);
		responseEventAction4.setlastEvent(true);
		
		VTNService.startVTNService();

		pauseForTestCaseTimeout(); 

	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}