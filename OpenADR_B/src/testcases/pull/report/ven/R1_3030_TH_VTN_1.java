package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.ResponseEventActionList;
import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_002Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_003Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.PULLBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.oadrpoll.OadrPollQueue;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;

public class R1_3030_TH_VTN_1 extends PULLBaseTestCase{

	public static void main(String[] args) {
		try {
			R1_3030_TH_VTN_1 testClass = new R1_3030_TH_VTN_1();
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

		IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction = new DefaultResponseCreateReportRequest_002Action(ServiceType.VTN);
		OadrPollQueue.addToQueue(responseCreateReportTypeAckAction);
	
		
		IResponseEventAction responseEvent=new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEvent);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType1 = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType1);

		IResponseCreateReportTypeAckAction responseCreateReportTypeAckAction2 = new DefaultResponseCreateReportRequest_003Action(ServiceType.VTN);
		OadrPollQueue.addToQueue(responseCreateReportTypeAckAction2);

		IResponseEventAction responseEvent2=new Default_ResponseEventAction();
		ResponseEventActionList.addResponseEventAction(responseEvent2);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType2 = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType2);
		//oadrUpdatedReportType2.setlastEvent(true);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType3 = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType3);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType4 = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType4);
		
		VTNService.startVTNService();

		
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		
		
		
		while (System.currentTimeMillis() < testCaseTimeout) {
			if(oadrUpdatedReportType2.isEventCompleted()) break;
			pause(2);
		}

		String reportRequestID1 = responseCreateReportTypeAckAction.getOadrCreateReportResponse().getOadrReportRequest().get(0).getReportRequestID();
		String reportRequestID2 = responseCreateReportTypeAckAction2.getOadrCreateReportResponse().getOadrReportRequest().get(0).getReportRequestID();
		
		int firstReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID1);
		int secondReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID2);
		
		
		UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID2);
		

		
		long plusLessThanAMinuteTime = System.currentTimeMillis()+40000;
		
		while(System.currentTimeMillis()<plusLessThanAMinuteTime){
			pause(1);


			firstReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID1);
			secondReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID2);
			
			if(firstReportUpdateReceivedCount ==2  || secondReportUpdateReceivedCount==2){
				LogHelper.addTrace("The reports were received before a minute expired.");
				LogHelper.addTrace("Test Case has Failed");
				LogHelper.setResult(LogResult.FAIL);
				break;
			}
			
		}
		
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}
			
		
		long twentySeconds = System.currentTimeMillis()+20000;
		
		while(System.currentTimeMillis()<twentySeconds){
			pause(1);
			firstReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID1);
			secondReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID2);
		
			if(firstReportUpdateReceivedCount ==2  && secondReportUpdateReceivedCount==2){
				break;
			}
			
		}
		
		pause(10);
		firstReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID1);
		secondReportUpdateReceivedCount = UpdateReportEventHelper.numberOfReportsReceivedForReportRequestID(reportRequestID2);
	
		if(firstReportUpdateReceivedCount ==2  && secondReportUpdateReceivedCount==2){
			LogHelper.setResult(LogResult.PASS);
			LogHelper.addTrace("The expected sequence has been completed");
			LogHelper.addTrace("Test Case has Passed");
		}else{
			LogHelper.setResult(LogResult.FAIL);
			LogHelper.addTrace("The expected sequence has not been completed");
			LogHelper.addTrace("Test Case has Faies");			
		}
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}
