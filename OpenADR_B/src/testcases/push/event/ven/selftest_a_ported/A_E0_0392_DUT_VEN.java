package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E0_0392_DUT_VEN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0392_DUT_VEN testClass = new A_E0_0392_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		ICreatedEventAction createdAction1 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);
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
		}

		pause(10);

		ICreatedEventAction createdAction2 = new Default_CreatedEventActionOnLastDistributeEventReceived(
				true, true);

		String oadrCreatedEventStr2 = createdAction2.getCreateEvent();
		OadrCreatedEventType oadrCreatedEvent = CreatedEventHelper
				.createCreatedEventFromString(oadrCreatedEventStr2);
		oadrCreatedEvent.getEiCreatedEvent().getEventResponses()
				.getEventResponse().get(0).setResponseCode("400");
		oadrCreatedEventStr2 = SchemaHelper
				.getCreatedEventAsString(oadrCreatedEvent);
		if (oadrCreatedEventStr2 != null) {
			VenToVtnClient.post(oadrCreatedEventStr2);
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
