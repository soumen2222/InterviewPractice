package testcases.pull.report.vtn;

import java.util.List;

import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.OadrResponseType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisteredReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class R1_3060_TH_VEN extends PULLBaseTestCase {

	public static void main(String[] args) {
		try {
			R1_3060_TH_VEN testClass = new R1_3060_TH_VEN();
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

		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.prompt_024(),1);
		
		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
					LogHelper.setResult(LogResult.NA);
					LogHelper.addTrace("TestCase execution was cancelled by the user");
					return;
		}

		OadrRegisterReportType oadrRegisterReportType = RegisterReportEventHelper.loadMetadata_001();
		String strRegisterReportType = SchemaHelper.getRegisterReportTypeAsString(oadrRegisterReportType);
		
		String strRegisteredReportType = VenToVtnClient
				.post(strRegisterReportType,VTNServiceType.EiReport);

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		boolean isExpectedReceived = OadrUtil.isExpected(strRegisteredReportType,OadrRegisteredReportType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrRegisteredReportType has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}

		
		OadrRegisteredReportType  oadrRegisteredReportType  = RegisteredReportEventHelper.createOadrRegisteredReportFromString(strRegisteredReportType);

		List<OadrReportRequestType>  oadrReportRequestList = oadrRegisteredReportType.getOadrReportRequest();
		if(oadrReportRequestList==null || oadrReportRequestList.size()<1){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Did not receive OadrReportRequest with the OadrRegisteredReport");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		OadrCreateReportType  oadrCreateReportType  = RegisteredReportEventHelper.createCreateReport(oadrRegisteredReportType);
		oadrCreateReportType.setRequestID(oadrRegisterReportType.getRequestID());
		TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType);
		OadrCreatedReportType createdReport = CreatedReportEventHelper.loadOadrCreatedReport();
		
		String strOadrCreatededReportType = SchemaHelper.getCreatedReportTypeAsString(createdReport);
		
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
					.addTrace("Expected OadrUpdatedReportType has not been received");
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