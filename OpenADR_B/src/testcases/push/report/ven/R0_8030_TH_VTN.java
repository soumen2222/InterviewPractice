package testcases.push.report.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ResponseUpdatedReportTypeAckActionList;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseUpdatedReportTypeAckAction;
import com.qualitylogic.openadr.core.base.PUSHBaseTestCase;
import com.qualitylogic.openadr.core.bean.ModeType;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.bean.VENServiceType;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.vtn.VTNService;
import com.qualitylogic.openadr.core.vtn.VtnToVenClient;

public class R0_8030_TH_VTN extends PUSHBaseTestCase {

	public static void main(String[] args) {
		try {
			R0_8030_TH_VTN testClass = new R0_8030_TH_VTN();
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
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType = new DefaultResponseUpdatedReportTypeAckAction();
		//oadrUpdatedReportType.setlastEvent(true);
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType2 = new DefaultResponseUpdatedReportTypeAckAction();
		//oadrUpdatedReportType.setlastEvent(true);
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType2);
		
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType3 = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType3);
		
		DefaultResponseUpdatedReportTypeAckAction oadrUpdatedReportType4 = new DefaultResponseUpdatedReportTypeAckAction();
		ResponseUpdatedReportTypeAckActionList.addResponseUpdatedReportAckAction(oadrUpdatedReportType4);
		
		VTNService.startVTNService();

		pause(5);
	
		OadrCreateReportType oadrCreateReportType = CreateReportEventHelper
				.loadOadrCreateReport_Request_002(ServiceType.VTN);
		
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

			//pauseForTestCaseTimeout();
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		
		ArrayList<OadrUpdateReportType> oadrUpdateListReceived = TestSession.getOadrUpdateReportTypeReceivedList();
		
		while (System.currentTimeMillis() < testCaseTimeout) {
			if(oadrUpdateListReceived.size()>0) break;
			pause(2);
		}
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}


		if (oadrUpdateListReceived.size()!=1) {

			LogHelper.addTrace("Expected 1 OadrUpdateReport. Received "+oadrUpdateListReceived.size() +" OadrUpdateReport");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);
			return;
			
		}
		
/////////////////////////////////////////////////////
		
		pause(5);
		
		OadrCreateReportType oadrCreateReportType2 = CreateReportEventHelper
				.loadOadrCreateReport_Request_003(ServiceType.VTN);
		
		TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType2);
		
		String strOadrCreateReportType2 = SchemaHelper.getCreateReportTypeAsString(oadrCreateReportType2);
		
		String strOadrCreatedReportType2 = VtnToVenClient
				.post(strOadrCreateReportType2,VENServiceType.EiReport);
	
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		isExpectedReceived = OadrUtil.isExpected(strOadrCreatedReportType2,OadrCreatedReportType.class);
		
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrCreatedReport has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}

		testCaseTimeout = OadrUtil.getTestCaseTimeout();
		
		oadrUpdateListReceived = TestSession.getOadrUpdateReportTypeReceivedList();
		
		while (System.currentTimeMillis() < testCaseTimeout) {
			if(oadrUpdateListReceived.size()>1) break;
			pause(2);
		}
		
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		if (oadrUpdateListReceived.size()!=2) {

			LogHelper.addTrace("Expected 2 OadrUpdateReport. Received "+oadrUpdateListReceived.size() +" OadrUpdateReport");
			LogHelper.addTrace("Test Case has Failed");
			LogHelper.setResult(LogResult.FAIL);
			return;		
		}

		
		///////////////////////////////////////////////
		
		String reportRequestID1 = oadrCreateReportType.getOadrReportRequest().get(0).getReportRequestID();
		String reportRequestID2 = oadrCreateReportType2.getOadrReportRequest().get(0).getReportRequestID();
		
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

		pause(10);
	
	}

	@Override
	public void cleanUp() throws Exception {
		VTNService.stopVTNService();
	}
}