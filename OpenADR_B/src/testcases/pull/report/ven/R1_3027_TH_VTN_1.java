package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_014Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class R1_3027_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3027_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		queueResponse(new DefaultResponseCreateReportRequest_014Action());

		addResponse();

		addUpdatedReportResponse();
		addUpdatedReportResponse();

		listenForRequests();
		
		waitForCreatedReport(1);
		
		prompt(resources.prompt_060());

		pause(3 * 60, "Pausing for 3 minutes...");

		checkCreatedReport(1);
		checkUpdateReport(2);
	}
}
