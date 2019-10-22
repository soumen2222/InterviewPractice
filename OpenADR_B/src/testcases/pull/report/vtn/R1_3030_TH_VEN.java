package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class R1_3030_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			R1_3030_TH_VEN testClass = new R1_3030_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableCreateReport_reportRequestIDUnique_Check(true);
		
		TestSession.setMode(ModeType.PULL);
		boolean isExpectedReceived;
		
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2.Prompt(resourceFileReader.prompt_020());
		
		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		String strCreateReportType = VenToVtnClient.poll(OadrCreateReportType.class);
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(strCreateReportType,OadrCreateReportType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrCreateReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}

		OadrCreateReportType  oadrCreateReportType  = CreateReportEventHelper.createOadrCreateReportFromString(strCreateReportType);
		TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType);
		OadrCreatedReportType oadrRegisteredReportType = CreatedReportEventHelper.loadOadrCreatedReport();
		
		String strOadrCreatededReportType = SchemaHelper.getCreatedReportTypeAsString(oadrRegisteredReportType);
		
		String oadrResponse = VenToVtnClient
				.post(strOadrCreatededReportType,VTNServiceType.EiReport);
	
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(oadrResponse,OadrResponseType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrResponse has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}


		pause(2);
	

		OadrUpdateReportType oadrUpdateReportType = UpdateReportEventHelper
				.loadOadrUpdateReport_Update001(ServiceType.VEN,true,oadrCreateReportType);
		String strOadrUpdateReportType = SchemaHelper.getUpdateReportTypeAsString(oadrUpdateReportType);
		
		String strOadrUpdatedReportType = VenToVtnClient
				.post(strOadrUpdateReportType,VTNServiceType.EiReport);

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(strOadrUpdatedReportType,OadrUpdatedReportType.class);

		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrUpdatedReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    
		}/*else{
			LogHelper.setResult(LogResult.PASS);
			LogHelper
					.addTrace("Expected sequence has been completed");
			LogHelper.addTrace("Test Case has Passed");
			
		}*/
		
		UIUserPrompt uiUserPrompt3 = new UIUserPrompt();
		uiUserPrompt3.Prompt(resourceFileReader.prompt_021(),UIUserPrompt.MEDIUM);
		
		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
					LogHelper.setResult(LogResult.NA);
					LogHelper.addTrace("TestCase execution was cancelled by the user");
					return;
		}

		
	
		String strCreateReportType2 =  VenToVtnClient.poll(OadrCreateReportType.class);
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(strCreateReportType2,OadrCreateReportType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrCreateReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}

		OadrCreateReportType  oadrCreateReportType2  = CreateReportEventHelper.createOadrCreateReportFromString(strCreateReportType2);
		TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType2);
		OadrCreatedReportType oadrRegisteredReportType2 = CreatedReportEventHelper.loadOadrCreatedReport();
		
		String strOadrCreatededReportType2 = SchemaHelper.getCreatedReportTypeAsString(oadrRegisteredReportType2);
		
		String oadrResponse2 = VenToVtnClient
				.post(strOadrCreatededReportType2,VTNServiceType.EiReport);
	
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(oadrResponse2,OadrResponseType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrResponse has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}


		pause(2);
	

		OadrUpdateReportType oadrUpdateReportType2 = UpdateReportEventHelper
				.loadOadrUpdateReport_Update001(ServiceType.VEN,true,oadrCreateReportType2);
		String strOadrUpdateReportType2 = SchemaHelper.getUpdateReportTypeAsString(oadrUpdateReportType2);
		
		String strOadrUpdatedReportType2 = VenToVtnClient
				.post(strOadrUpdateReportType2,VTNServiceType.EiReport);

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(strOadrUpdatedReportType2,OadrUpdatedReportType.class);

		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrUpdatedReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    
		}/*else{
			LogHelper.setResult(LogResult.PASS);
			LogHelper
					.addTrace("Expected sequence has been completed");
			LogHelper.addTrace("Test Case has Passed");
			
		}*/
		
		
		
		///////////////////////////////////////////////////////////////////
		long plusOneMinuteTime = System.currentTimeMillis()+60000;
		
		while(System.currentTimeMillis()<plusOneMinuteTime){
			pause(1);
			break;
		}

		
		String strOadrUpdatedReportType1B = VenToVtnClient
				.post(strOadrUpdateReportType,VTNServiceType.EiReport);

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}
		
		isExpectedReceived = OadrUtil.isExpected(strOadrUpdatedReportType1B,OadrUpdatedReportType.class);

		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrUpdatedReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    
		}
		
		pause(5);
		String strOadrUpdatedReportType2B = VenToVtnClient
				.post(strOadrUpdateReportType2,VTNServiceType.EiReport);

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}
		
		isExpectedReceived = OadrUtil.isExpected(strOadrUpdatedReportType2B,OadrUpdatedReportType.class);

		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrUpdatedReport has not been received");
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