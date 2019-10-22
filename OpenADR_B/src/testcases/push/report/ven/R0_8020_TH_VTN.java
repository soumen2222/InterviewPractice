package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8020_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8020_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addUpdatedReportResponse();
		addUpdatedReportResponse().setlastEvent(true);
		
		addRegisteredReportResponse();
		
		listenForRequests();

		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_002(ServiceType.VTN);
		// TestSession.getOadrCreateReportTypeReceivedList().add(createReport);
		sendCreateReport(createReport);
		
		waitForCompletion();

		checkUpdateReport(2);
	}
}