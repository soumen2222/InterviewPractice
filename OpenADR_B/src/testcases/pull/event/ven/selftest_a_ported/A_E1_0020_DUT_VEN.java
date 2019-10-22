package testcases.pull.event.ven.selftest_a_ported;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E1_0020_DUT_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0020_DUT_VEN testClass = new A_E1_0020_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		/*
		OadrPollType oadrPollType = PollEventSignalHelper.loadOadrPollType(); 
		String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType); 
		String distributeEventResponse = VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 
		*/
		// JAXBContext context = null;
		PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
		OadrRequestEventType oadrRequestEvent = RequestEventSignalHelper
				.loadOadrRequestEvent(propertiesFileReader.get("testDataPath")
						+ "oadrRequestEvent_Default.xml");
		oadrRequestEvent.getEiRequestEvent().setVenID(
				propertiesFileReader.getVenID());
		/*
		context = JAXBContext.newInstance(OadrRequestEventType.class);
		String strOadrRequestEvent = SchemaHelper.asString(context,
				oadrRequestEvent);
		String distributeEventResponse = VenToVtnClient
				.post(strOadrRequestEvent);
		*/
		String strOadrRequestEvent = SchemaHelper.getRequestEventAsString(oadrRequestEvent);
		String distributeEventResponse = VenToVtnClient.post(strOadrRequestEvent, VTNServiceType.EiEvent);
		
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(distributeEventResponse);

		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, false, true, null);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
