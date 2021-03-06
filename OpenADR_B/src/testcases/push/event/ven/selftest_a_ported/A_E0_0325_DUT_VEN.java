package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventErrorActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENService;

public class A_E0_0325_DUT_VEN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0325_DUT_VEN testClass = new A_E0_0325_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ICreatedEventAction createdAction1 = new CreatedEventErrorActionOnLastDistributeEventReceived(
				false);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long totalDurationToRun = System.currentTimeMillis()
				+ totalDurationInput;

		while (totalDurationToRun > System.currentTimeMillis()) {
			if (createdAction1.isEventCompleted()) {
				break;
			}
			Thread.sleep(500);
		}
		pause(20);
	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
