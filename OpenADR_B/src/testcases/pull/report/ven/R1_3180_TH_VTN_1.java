package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_013Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class R1_3180_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3180_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		// Two CreateReport testing
		/*
		IResponseCreateReportTypeAckAction createReport1 = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN);
		checkReportRequest(createReport1.getOadrCreateReportResponse(), 1);
		queueResponse(createReport1);

		IResponseCreateReportTypeAckAction createReport2 = new DefaultResponseCreateReportRequest_009Action();
		checkReportRequest(createReport2.getOadrCreateReportResponse(), 1);
		queueResponse(createReport2);
		*/
		
		IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_013Action();
		checkReportRequest(createReport.getOadrCreateReportResponse(), 2);
		queueResponse(createReport);
		
		addResponse();
		
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();
		
		waitForCompletion();
		
		pause(15, "Waiting 15 seconds for additional UpdateReports...");
		
		// checkCreatedReport(1);
		checkUpdateReport_Rx180();
	}
}
