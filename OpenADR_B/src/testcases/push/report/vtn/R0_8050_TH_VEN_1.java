package testcases.push.report.vtn;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ResponseCreatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VTNServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdatedReportType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdatedReportEventHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.ven.VENService;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class R0_8050_TH_VEN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			R0_8050_TH_VEN_1 testClass = new R0_8050_TH_VEN_1();
			testClass.setEnableLogging(true);
			testClass.executeTestCase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();


		TestSession.setMode(ModeType.PUSH);
		DefaultResponseCreatedReportTypeAckAction defaultResponseCreatedReportTypeAckAction=new DefaultResponseCreatedReportTypeAckAction();
		ResponseCreatedReportTypeAckActionList.addResponseCreatedReportAckAction(defaultResponseCreatedReportTypeAckAction);
	
		VENService.startVENService();

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

		ArrayList<OadrCreateReportType>  oadrCreateReportTypeReceivedList=null;
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			oadrCreateReportTypeReceivedList=TestSession.getOadrCreateReportTypeReceivedList();
			if(oadrCreateReportTypeReceivedList.size()>0){
				break;
			}
			pause(5);
		}
		

		if(oadrCreateReportTypeReceivedList.size()!=1){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected one OadrCreateReport. Received "+oadrCreateReportTypeReceivedList.size()+" OadrCreateReport");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		UIUserPrompt uiUserPrompt3 = new UIUserPrompt();
		uiUserPrompt3.Prompt(resourceFileReader.prompt_023());
		
		if (!TestSession.isUserClickedContinuePlay()) {
			// User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");
			return;
		}

		
		OadrUpdateReportType oadrUpdateReportType = UpdateReportEventHelper
				.loadOadrUpdateReport_Update001(ServiceType.VEN,true,null);
		
		
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
		    return;
		}

		OadrUpdatedReportType  oadrUpdatedReportType  = UpdatedReportEventHelper.loadReportFromString(strOadrUpdatedReportType);
		
		
		if (oadrUpdatedReportType.getOadrCancelReport()!=null) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Received CancelReport with UpdatedReport");
			LogHelper.addTrace("Test Case has Passed");
		} else {
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("Did not receive the CancelReport with UpdatedReport");
			LogHelper.addTrace("Test Case has Failed");

		}
	}

	@Override
	public void cleanUp() throws Exception {
		VENService.stopVENService();
	}

}
