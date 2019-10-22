package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_008Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;

public class R1_3140_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3140_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_008Action();
		queueResponse(createReport);

		addResponse();
		
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		prompt(resources.prompt_053());
		
		waitForCompletion();

		checkCreatedReport(1);

		OadrUpdateReportType updateReport = checkUpdateReport(1);
		checkUpdateReport(updateReport, 30, 2);
	}
}
