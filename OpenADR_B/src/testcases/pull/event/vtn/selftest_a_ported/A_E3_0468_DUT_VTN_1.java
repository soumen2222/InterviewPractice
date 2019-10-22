package testcases.pull.event.vtn.selftest_a_ported;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_Pull_CPP_DistributeEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class A_E3_0468_DUT_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E3_0468_DUT_VTN_1 testClass = new A_E3_0468_DUT_VTN_1();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		IDistributeEventAction distributeEventAction1 = new Default_Pull_CPP_DistributeEventAction();
		distributeEventAction1.getDistributeEvent().getOadrEvent().get(0)
				.setOadrResponseRequired(ResponseRequiredType.NEVER);
		OadrPollQueue.addToQueue(distributeEventAction1); 

		VTNService.startVTNService();

		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(2);
			if(distributeEventAction1.isEventCompleted()){
				break;
			}
		}

		pause(5);
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
