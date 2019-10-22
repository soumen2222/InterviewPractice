package testcases.pull.report.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.IResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_002Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedWithCancellationReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class R1_3050_TH_VTN_1 extends PULLBaseTestCase {

	public static void main(String[] args){
		try {
			R1_3050_TH_VTN_1 testClass = new R1_3050_TH_VTN_1();
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

		IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction = new DefaultResponseCreateReportRequest_002Action(ServiceType.VTN);
		OadrPollQueue.addToQueue(responseCreateReportTypeAckAction);
	
		IResponseEventAction responseEvent=new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEvent);
		
		IResponseUpdatedReportTypeAckAction oadrUpdatedReportType = new DefaultResponseUpdatedWithCancellationReportTypeAckAction(ServiceType.VTN,responseCreateReportTypeAckAction);
		oadrUpdatedReportType.setlastEvent(true);
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType);
		
		VTNService.startVTNService();


		pauseForTestCaseTimeout();

		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		ArrayList<OadrUpdateReportType>  oadrUpdateReportTypeReceivedList=TestSession.getOadrUpdateReportTypeReceivedList();
		
		ArrayList<OadrCreatedReportType>  oadrCreatedReportTypeReceivedList=TestSession.getOadrCreatedReportTypeReceivedList();

		if(oadrCreatedReportTypeReceivedList.size()!=1){
			LogHelper
						.addTrace("One OadrCreatedReport was expected. Received "
								+ oadrCreatedReportTypeReceivedList.size()
								+ " OadrCreatedReport");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);
			return;
		}
		
		if (oadrUpdateReportTypeReceivedList.size() == 1) {
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("Received one OadrUpdateReport as expected");
			LogHelper.addTrace("Test Case has Passed");
		} else {

			LogHelper
						.addTrace("One OadrUpdateReport was expected. Received "
								+ oadrUpdateReportTypeReceivedList.size()
								+ " OadrUpdateReport");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);

		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
