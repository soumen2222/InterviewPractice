package testcases.pull.event.ven.selftest_a_ported;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

@SuppressWarnings("restriction")
public class A_E1_0210_DUT_VEN extends PULLBaseTestCase {

	public static void main(String[] args) throws Exception {
		A_E1_0210_DUT_VEN testClass = new A_E1_0210_DUT_VEN();
		testClass.executeTestCase();
	}

	@Override
	public void test() throws Exception {

		// Put OADRRequest Event
		OadrPollType oadrPollType = PollEventSignalHelper.loadOadrPollType(); 
		String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType); 
		String distributeEventResponse = VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 
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
