package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E0_0130_DUT_VEN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0130_DUT_VEN testClass = new A_E0_0130_DUT_VEN();
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
		createdAction1.setLastCreateEvent(true); 

		VENService.startVENService();

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		pauseForTestCaseTimeout(); 


	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
