package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CancelReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8045_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8045_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_012();
		sendCreateReport(createReport);
		
		waitForCompletion();

		// checkUpdateReport(1);
		
		OadrCancelReportType cancelReport = CancelReportEventHelper.loadOadrCancelReportType(createReport);
		cancelReport.setReportToFollow(true);
		OadrCanceledReportType canceledReport = sendCancelReport(cancelReport);
		checkCanceledReportPendingReports(canceledReport, 1);
		
		waitForUpdateReport(2);
	}
}