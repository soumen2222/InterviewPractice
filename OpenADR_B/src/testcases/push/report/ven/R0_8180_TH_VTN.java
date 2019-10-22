package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8180_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8180_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addUpdatedReportResponse().setlastEvent(true);
		addUpdatedReportResponse();
		
		listenForRequests();

		// Two CreateReports testing
		/*
		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_001(ServiceType.VTN);
		checkReportRequest(createReport, 1);
		sendCreateReport(createReport);

		OadrCreateReportType createReport2 = CreateReportEventHelper.loadOadrCreateReport_Request_009();
		checkReportRequest(createReport2, 1);
		sendCreateReport(createReport2);
		*/
		
		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_013();
		checkReportRequest(createReport, 2);
		sendCreateReport(createReport);
		
		waitForCompletion();
		
		pause(15, "Waiting 15 seconds for additional UpdateReport request...");

		checkUpdateReport_Rx180();
	}
}
