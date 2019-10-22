package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
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
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class R1_3050_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			R1_3050_TH_VEN testClass = new R1_3050_TH_VEN();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		TestSession.setMode(ModeType.PULL);

		boolean isExpectedReceived;
		
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2.Prompt(resourceFileReader.prompt_020(),1);
		
		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		String oadrCreateReportType = VenToVtnClient.poll(OadrCreateReportType.class);
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(oadrCreateReportType,OadrCreateReportType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrCreateReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}

		OadrCreateReportType createReportType = CreateReportEventHelper.createOadrCreateReportFromString(oadrCreateReportType);
		TestSession.addCreateReportTypeReceivedToList(createReportType);
		
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
	
		UIUserPrompt uiUserPrompt3 = new UIUserPrompt();
		uiUserPrompt3.Prompt(resourceFileReader.prompt_023());
		
		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
					LogHelper.setResult(LogResult.NA);
					LogHelper.addTrace("TestCase execution was cancelled by the user");
					return;
		}

		OadrUpdateReportType oadrUpdateReportType = UpdateReportEventHelper
				.loadOadrUpdateReport_Update001(ServiceType.VEN,true,createReportType);
		String strOadrUpdateReportType = SchemaHelper.getUpdateReportTypeAsString(oadrUpdateReportType);
		
		String strOadrUpdatedReportType = VenToVtnClient
				.post(strOadrUpdateReportType,VTNServiceType.EiReport);


		isExpectedReceived = OadrUtil.isExpected(strOadrUpdatedReportType,OadrUpdatedReportType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrUpdatedReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		}else{
			
			OadrUpdatedReportType updateReport = UpdatedReportEventHelper.loadReportFromString(strOadrUpdatedReportType);
			OadrCancelReportType  oadrCancelReportType  = updateReport.getOadrCancelReport();
			if(oadrCancelReportType!=null && oadrCancelReportType.getReportRequestID().size()>0){
				LogHelper.setResult(LogResult.PASS);
				LogHelper
						.addTrace("Expected OadrUpdatedReport has been received with OadrCancelReport");
				LogHelper.addTrace("Test Case has Passed");
			}else{
				LogHelper.setResult(LogResult.FAIL);
				LogHelper
						.addTrace("OadrUpdatedReport did not have OadrCancelReport");
				LogHelper.addTrace("Test Case has Passed");
				
			}
				
		}
	}

	@Override
	public void cleanUp() throws Exception {

	}

}