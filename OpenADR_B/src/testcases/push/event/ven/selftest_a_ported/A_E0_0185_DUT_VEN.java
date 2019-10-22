package testcases.push.event.ven.selftest_a_ported;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.E0_0185_CreatedEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E0_0185_DUT_VEN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0185_DUT_VEN testClass = new A_E0_0185_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ICreatedEventAction createdAction1 = new E0_0185_CreatedEventAction(
				true, true);
		CreatedEventActionList.addCreatedEventAction(createdAction1);

		VENService.startVENService();

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

		long totalDurationInput = Long.valueOf(propertiesFileReader
				.get("asyncResponseTimeout"));
		long totalDurationToRun = System.currentTimeMillis()
				+ totalDurationInput;

		while (totalDurationToRun > System.currentTimeMillis()) {
			pause(1);
			if (createdAction1.isEventCompleted()) {

				break;
			}

		}

		if (createdAction1.isEventCompleted()) {
			ArrayList<String> distributeEventList = new ArrayList<String>();
			ArrayList<OadrDistributeEventType> distributeEventReceived = VENServerResource
					.getOadrDistributeEventReceivedsList();
			distributeEventList
					.add(SchemaHelper
							.getDistributeEventAsString(distributeEventReceived
									.get(1)));
			String createdEvent = CreatedEventHelper.createCreatedEvent(
					distributeEventList, true, true, null);
			pause(3);
			VenToVtnClient.post(createdEvent);
			pause(20);
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
