package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R1_3090_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3090_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		OadrCreateReportType createReport1 = CreateReportEventHelper.loadOadrCreateReport_Request_005();
		OadrCreatedReportType createdReport1 = sendCreateReport(createReport1);
		checkCreatedReportPendingReports(createdReport1, 1);
		
		pollRegisterReport();
		sendRegisteredReport();
		
		OadrCreateReportType createReport2 = CreateReportEventHelper.loadOadrCreateReport_Request_006();
		OadrCreatedReportType createdReport2 = sendCreateReport(createReport2);
		checkCreatedReportPendingReports(createdReport2, 2);
		
		pollRegisterReport();
		sendRegisteredReport();
		
		pollRegisterReport();
		sendRegisteredReport();

		pollRegisterReport();
		sendRegisteredReport();
	}
}
