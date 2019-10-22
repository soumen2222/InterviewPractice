package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8160_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8160_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse();
		addRegisteredReportResponse();
		
		listenForRequests();

		sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_010());

		prompt(resources.prompt_047());
		
		OadrUpdateReportType updateReport1 = waitForUpdateReport(1);
		OadrUpdateReportType updateReport2 = waitForUpdateReport(2);
		checkUpdateReports_x160(updateReport1, updateReport2);
	}
}
