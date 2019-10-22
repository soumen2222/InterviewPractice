package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E0_0096_DUT_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0096_DUT_VEN_1 testClass = new A_E0_0096_DUT_VEN_1();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		VENService.startVENService();

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		//pauseForTestCaseTimeout(); 
		pause(25);

	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
