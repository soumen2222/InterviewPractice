package testcases.pull.event.ven.selftest_a_ported;

import java.util.ArrayList;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.util.Clone;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E1_0360_DUT_VEN extends PULLBaseTestCase  {

	public static void main(String[] args) {
		try {
			A_E1_0360_DUT_VEN testClass = new A_E1_0360_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		OadrRequestEventType oadrRequestEvent = RequestEventSignalHelper
				.loadOadrRequestEvent("oadrRequestEvent_Default.xml");
		oadrRequestEvent.getEiRequestEvent().setVenID(
				propertiesFileReader.get("VEN_ID"));

		OadrPollType oadrPollType = PollEventSignalHelper.loadOadrPollType(); 
		String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType); 
		String distributeEvent1 = VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(distributeEvent1);
		
		
/*		String strOadrRequestEvent = SchemaHelper
				.getRequestEventAsString(oadrRequestEvent);
		String distributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(distributeEventResponse);
*/
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, false, true, null);

		// Convert to object

		OadrCreatedEventType oadrCreatedEventObj = ResponseHelper
				.createOadrCreatedEventFromString(oadrCreatedEventStr);

		// Set Error
		oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
				.getEventResponse().get(0).setResponseCode("409");
		oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
				.getEventResponse().get(0).setOptType(OptTypeType.OPT_OUT);

		OadrCreatedEventType oadrCreatedEventObj2 = Clone
				.clone(oadrCreatedEventObj);
		oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
				.getEventResponse().remove(0);
		oadrCreatedEventObj2.getEiCreatedEvent().getEventResponses()
				.getEventResponse().remove(1);

		String oadrCreatedEventStr2 = SchemaHelper.getCreatedEventAsString(
				oadrCreatedEventObj2);
		 VenToVtnClient.post(oadrCreatedEventStr2);
		
		 // Convert back to string
		oadrCreatedEventStr = SchemaHelper.getCreatedEventAsString(oadrCreatedEventObj);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
