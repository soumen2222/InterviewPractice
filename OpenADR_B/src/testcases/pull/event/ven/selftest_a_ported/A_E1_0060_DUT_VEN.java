package testcases.pull.event.ven.selftest_a_ported;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrPollType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.PollEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.PropertiesFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

@SuppressWarnings("restriction")
public class A_E1_0060_DUT_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			A_E1_0060_DUT_VEN testClass = new A_E1_0060_DUT_VEN();
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		String strOadrDistributeEventResponse = VenToVtnClient.poll(OadrDistributeEventType.class);; 
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		boolean isExpectedReceived = OadrUtil.isExpected(strOadrDistributeEventResponse,OadrDistributeEventType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}


		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(strOadrDistributeEventResponse);

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
