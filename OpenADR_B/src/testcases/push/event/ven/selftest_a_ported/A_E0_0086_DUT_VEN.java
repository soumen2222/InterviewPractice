package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E0_0086_DUT_VEN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0086_DUT_VEN testClass = new A_E0_0086_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		VENService.startVENService();

		// pauseForTestCaseTimeout(); 
		pause(20);

	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
