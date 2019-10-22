package testcases.pull.event.ven.selftest_a_ported;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.ResponseHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

@SuppressWarnings("restriction")
public class A_E1_0240_DUT_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0240_DUT_VEN testClass = new A_E1_0240_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		JAXBContext context = null;
		// Put OADRRequest Event
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		OadrPollType oadrPollType = PollEventSignalHelper.loadOadrPollType(); 
		String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType); 
		String distributeEvent1 = VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 


		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(distributeEvent1);

		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, false, true, null);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}

		pause(30);
		String distributeEvent2 = VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 
		
		ArrayList<String> distributeEventList2 = new ArrayList<String>();
		distributeEventList2.add(distributeEvent2);

		String oadrCreatedEventStr2 = CreatedEventHelper.createCreatedEvent(
				distributeEventList2, false, true, null);

		// Convert to object
		OadrCreatedEventType oadrCreatedEventObj = ResponseHelper
				.createOadrCreatedEventFromString(oadrCreatedEventStr2);

		oadrCreatedEventObj.getEiCreatedEvent().getEventResponses()
				.getEventResponse().get(0).getQualifiedEventID()
				.setModificationNumber(1);

		// Convert back to string
		context = JAXBContext.newInstance(OadrCreatedEventType.class);
		oadrCreatedEventStr2 = SchemaHelper.getCreatedEventAsString(
				oadrCreatedEventObj);

		if (oadrCreatedEventStr2 != null) {
			VenToVtnClient.post(oadrCreatedEventStr2);
		}

		// PING
		/*
		 * VenToVtnClient.post(strOadrRequestEvent); pause(5);
		 * VenToVtnClient.post(strOadrRequestEvent); pause(5);
		 * VenToVtnClient.post(strOadrRequestEvent);
		 */
		// PING
	}

	@Override
	public void cleanUp() throws Exception {

	}

}
