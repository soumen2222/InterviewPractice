package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8150_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8150_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

			
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_009());

		waitForCompletion();

		OadrUpdateReportType updateReport = checkUpdateReport(1);
		checkUpdateReport_x150(updateReport);
	}
}
