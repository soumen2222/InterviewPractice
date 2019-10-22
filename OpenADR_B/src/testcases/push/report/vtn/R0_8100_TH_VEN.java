package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8100_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8100_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse().setlastEvent(true);
		
		listenForRequests();

		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_005();
		sendCreateReport(createReport);
		
		waitForCompletion();

		checkRegisterReportRequest(1);
		
		sendCancelReport(createReport);
	}
}
