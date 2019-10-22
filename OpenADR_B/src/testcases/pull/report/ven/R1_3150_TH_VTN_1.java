package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_009Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;

public class R1_3150_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3150_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_009Action();
		queueResponse(createReport);

		addResponse();
		
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCompletion();

		checkCreatedReport(1);

		OadrUpdateReportType updateReport = checkUpdateReport(1);
		checkUpdateReport_x150(updateReport);
	}
}
