package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8090_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8090_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse().setlastEvent(true);
		addRegisteredReportResponse();
		addRegisteredReportResponse();
		addRegisteredReportResponse();
		
		listenForRequests();

		OadrCreatedReportType createdReport1 = sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_005());
		checkCreatedReportPendingReports(createdReport1, 1);
		
		waitForCompletion();
		
		checkRegisterReportRequestLess(1);
		
		OadrCreatedReportType createdReport2 = sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_006());
		checkCreatedReportPendingReports(createdReport2, 2);

		int registerReportCount = TestSession.getOadrRegisterReportTypeReceivedList().size();
		waitForRegisterReport(registerReportCount + 3);
		
		pause(2);
	}
}
