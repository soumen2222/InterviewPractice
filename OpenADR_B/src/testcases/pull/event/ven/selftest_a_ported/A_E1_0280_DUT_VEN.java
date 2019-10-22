package testcases.pull.event.ven.selftest_a_ported;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.impl.Default_CreateOptEventActionOnLastDistEvntRecd;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.ven.VENServerResource;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E1_0280_DUT_VEN extends PULLBaseTestCase {

	public static void main(String[] args) throws Exception {
		A_E1_0280_DUT_VEN testClass = new A_E1_0280_DUT_VEN();
		testClass.executeTestCase();
	}

	@Override
	public void test() throws Exception {

		OadrPollType oadrPollType = PollEventSignalHelper.loadOadrPollType(); 
		String stroadrPollType = SchemaHelper.getOadrPollTypeAsString(oadrPollType); 
		String distributeEventResponse = VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(distributeEventResponse);

		OadrDistributeEventType distributeEvent = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventResponse);
		VENServerResource.getOadrDistributeEventReceivedsList().add(distributeEvent);
		
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, true, null);

		if (oadrCreatedEventStr != null) {
			VenToVtnClient.post(oadrCreatedEventStr);
		}


		VenToVtnClient.post(stroadrPollType,VTNServiceType.OadrPoll); 

		Default_CreateOptEventActionOnLastDistEvntRecd defaultCreateOpt = new Default_CreateOptEventActionOnLastDistEvntRecd(OptTypeType.OPT_OUT);
		OadrCreateOptType  oadrCreateOpt  = defaultCreateOpt.getOadrCreateOpt();
		String strOadrCreateOptType = SchemaHelper.getOadrCreateOptAsString(oadrCreateOpt);
		
		
		String oadrCreatedEventStr2 = CreatedEventHelper.createCreatedEvent(
				distributeEventList, true, false, null);
		
		
		//CreateOptEventHelper.createCreateOptTypeEvent(distributeEventResponse, OptTypeType.OPT_OUT, eventToSelect, resourcePositionInPayloadToInclude)
		pause(20);
		

/*		if (oadrCreatedEventStr2 != null) {
			VenToVtnClient.post(oadrCreatedEventStr2);
		}
*/

		if (strOadrCreateOptType != null) {
			VenToVtnClient.post(strOadrCreateOptType,VTNServiceType.EiOpt);
		}

	}

	@Override
	public void cleanUp() throws Exception {

	}

}
