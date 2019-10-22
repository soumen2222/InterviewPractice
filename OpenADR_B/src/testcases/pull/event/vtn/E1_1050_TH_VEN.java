package testcases.pull.event.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OadrRequestEventType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.helper.CreatedEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.RequestEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class E1_1050_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			E1_1050_TH_VEN testClass = new E1_1050_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {

		
		ResourceFileReader resource = new ResourceFileReader();
		
		UIUserPrompt uiUserPrompt1 = new UIUserPrompt();
		uiUserPrompt1.Prompt(resource.prompt_010());
		
		OadrRequestEventType oadrRequestEvent = RequestEventSignalHelper.getOadrRequestEvent();
		String strOadrRequestEvent = SchemaHelper.getRequestEventAsString(oadrRequestEvent);
		String distributeEventResponse = VenToVtnClient.post(strOadrRequestEvent,VTNServiceType.EiEvent);
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}
		
		boolean isExpectedReceived = OadrUtil.isExpected(distributeEventResponse,OadrDistributeEventType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		ArrayList<String> distributeEventList = new ArrayList<String>();
		distributeEventList.add(distributeEventResponse);

		pause(5);
		
		String oadrCreatedEventStr = CreatedEventHelper.createCreatedEvent(
				distributeEventList, false, true, null);

		String strOadrResponse = "";
		if (oadrCreatedEventStr != null) {
			strOadrResponse = VenToVtnClient.post(oadrCreatedEventStr);
		}
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}
		
		pause(5);
		
		isExpectedReceived = OadrUtil.isExpected(strOadrResponse,OadrResponseType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrResponse has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    
		}else{
			LogHelper.setResult(LogResult.PASS);
			LogHelper
					.addTrace("Expected sequence has been completed");
			LogHelper.addTrace("Test Case has Passed");
		    
		}
		
	}

	@Override
	public void cleanUp() throws Exception {

	}

}
