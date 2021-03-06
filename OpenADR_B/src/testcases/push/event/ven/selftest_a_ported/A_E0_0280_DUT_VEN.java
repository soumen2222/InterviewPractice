package testcases.push.event.ven.selftest_a_ported;

import com.qualitylogic.openadr.core.action.CreatedEventActionList;
import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreateOptEventActionOnLastDistEvntRecd;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E0_0280_DUT_VEN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E0_0280_DUT_VEN testClass = new A_E0_0280_DUT_VEN();
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
			pause(40);
			
			String createdEvent = createdAction1.getCreateEvent();
			OadrCreatedEventType oadrCreatedEventObj = CreatedEventHelper
					.createCreatedEventFromString(createdEvent);
			oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
					.getEventResponse().get(0).setOptType(OptTypeType.OPT_OUT);
			createdEvent = SchemaHelper
					.getCreatedEventAsString(oadrCreatedEventObj);

			//VenToVtnClient.post(createdEvent);

			
			Default_CreateOptEventActionOnLastDistEvntRecd defaultCreateOpt = new Default_CreateOptEventActionOnLastDistEvntRecd(OptTypeType.OPT_OUT);
			OadrCreateOptType  oadrCreateOpt  = defaultCreateOpt.getOadrCreateOpt();
			String strOadrCreateOptType = SchemaHelper.getOadrCreateOptAsString(oadrCreateOpt);
			
			if (strOadrCreateOptType != null) {
				VenToVtnClient.post(strOadrCreateOptType,VTNServiceType.EiOpt);
			}
			
			pause(10);

			// System.out.println(VTNServerResource.getTest());
			// System.out.println(System.currentTimeMillis());
		}

	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
