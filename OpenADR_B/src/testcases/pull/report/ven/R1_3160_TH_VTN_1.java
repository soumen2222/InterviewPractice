package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_010Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;

public class R1_3160_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3160_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_010Action();
		queueResponse(createReport);

		addResponse();
		
		addUpdatedReportResponse();
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCompletion(5 * 60 * 1000);

		checkCreatedReport(1);
		checkUpdateReport(2);
		
		OadrUpdateReportType updateReport1 = TestSession.getOadrUpdateReportTypeReceivedList().get(0);
		OadrUpdateReportType updateReport2 = TestSession.getOadrUpdateReportTypeReceivedList().get(1);
		checkUpdateReports_x160(updateReport1, updateReport2);
	}
}
