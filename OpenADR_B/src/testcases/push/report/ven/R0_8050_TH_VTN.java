package testcases.push.report.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.ResponseRegisteredReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_002Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedWithCancellationReportTypeAckAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VENServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class R0_8050_TH_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			R0_8050_TH_VTN testClass = new R0_8050_TH_VTN();
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
		IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction = new DefaultResponseCreateReportRequest_002Action(ServiceType.VTN);

		DefaultResponseRegisteredReportTypeAckAction oadrRegisteredReportType = new DefaultResponseRegisteredReportTypeAckAction();
		ResponseRegisteredReportTypeAckActionList.addResponseRegisteredReportAckAction(oadrRegisteredReportType);

		IResponseUpdatedReportTypeAckAction oadrUpdatedReportType = new DefaultResponseUpdatedWithCancellationReportTypeAckAction(ServiceType.VTN,responseCreateReportTypeAckAction);
		oadrUpdatedReportType.setlastEvent(true);
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType);
		VTNService.startVTNService();

		
		UIUserPrompt uiUserPrompt2 = new UIUserPrompt();
		uiUserPrompt2.Prompt("Start VEN then click Resume");

		if(!TestSession.isUserClickedContinuePlay()) {	
			// User closed the action dialog
						LogHelper.setResult(LogResult.NA);
						LogHelper.addTrace("TestCase execution was cancelled by the user");			
						return;
		}
		
		OadrCreateReportType oadrCreateReportType =responseCreateReportTypeAckAction.getOadrCreateReportResponse();
		
		TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType);
		
		String strOadrCreateReportType = SchemaHelper.getCreateReportTypeAsString(oadrCreateReportType);
		
		String strOadrCreatedReportType = VtnToVenClient
				.post(strOadrCreateReportType,VENServiceType.EiReport);

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		boolean isExpectedReceived = OadrUtil.isExpected(strOadrCreatedReportType,OadrCreatedReportType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrCreatedReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}

		
		pauseForTestCaseTimeout();

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		ArrayList<OadrUpdateReportType> oadrUpdateListReceived = TestSession.getOadrUpdateReportTypeReceivedList();

		if (oadrUpdateListReceived!=null && oadrUpdateListReceived.size()==1) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Received OadrUpdateReport as expected");
			LogHelper.addTrace("Test Case has Passed");
		} else {

			LogHelper.addTrace("Test Case has Failed");
			LogHelper.addTrace("Expected 1 OadrUpdateReport. Received "+oadrUpdateListReceived.size() +" OadrUpdateReport");

			LogHelper.setResult(LogResult.FAIL);

		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}