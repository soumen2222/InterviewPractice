package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_011Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class R1_3025_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3025_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		queueResponse(new DefaultResponseCreateReportRequest_011Action());

		addResponse();

		addUpdatedReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_054());

		waitForCompletion();

		checkCreatedReport(1);
		checkUpdateReport(1);
	}
}
