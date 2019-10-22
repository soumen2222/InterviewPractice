package testcases.push.report.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.ResponseRegisteredReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportWithRequestTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.util.ResourceFileReader;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class R0_8060_TH_VTN_1 extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			R0_8060_TH_VTN_1 testClass = new R0_8060_TH_VTN_1();
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
		
		ResourceFileReader resourceFileReader = new ResourceFileReader();
		UIUserPrompt uiUserPrompt = new UIUserPrompt();
		uiUserPrompt.Prompt(resourceFileReader.prompt_051());

		if (!TestSession.isUserClickedContinuePlay()) {	
			// User closed the action dialog
			LogHelper.setResult(LogResult.NA);
			LogHelper.addTrace("TestCase execution was cancelled by the user");			
			return;
		}
		
		IResponseEventAction responseEvent=new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEvent);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType = new DefaultResponseUpdatedReportTypeAckAction();
		oadrUpdatedReportType.setlastEvent(true);
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType);

		IResponseRegisteredReportTypeAckAction oadrRegisteredReportType = new DefaultResponseRegisteredReportWithRequestTypeAckAction(ServiceType.VTN);
		ResponseRegisteredReportTypeAckActionList.addResponseRegisteredReportAckAction(oadrRegisteredReportType);
		VTNService.startVTNService();

		pauseForTestCaseTimeout();

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		ArrayList<OadrCreatedReportType>  oadrCreatedReportTypeReceivedList=TestSession.getOadrCreatedReportTypeReceivedList();
		if (oadrCreatedReportTypeReceivedList.size() != 1) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Expected one OadrCreatedReport. Received "+oadrCreatedReportTypeReceivedList.size()+" OadrCreatedReport");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);
			return;
		} 
		
		
		ArrayList<OadrUpdateReportType>  oadrUpdateReportTypeReceivedList=TestSession.getOadrUpdateReportTypeReceivedList();
		
		if (oadrUpdateReportTypeReceivedList.size() == 1) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Received one OadrUpdateReport as expected");
			LogHelper.addTrace("Test Case has Passed");
		} else {

			LogHelper
						.addTrace("One OadrUpdateReport was expected. Received "
								+ oadrUpdateReportTypeReceivedList.size()
								+ " OadrRegisterReport");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);

		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
