package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8027_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8027_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addUpdatedReportResponse();
		addUpdatedReportResponse().setlastEvent(true);

		listenForRequests();

		sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_014());

		prompt(resources.prompt_060());
		
		pause(3 * 60, "Pausing for 3 minutes...");
		
		checkUpdateReport(2);
	}
}