package testcases.pull.report.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class R1_3180_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3180_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_058());
		
		List<OadrCreateReportType> createReports = pollCreateReport_Rx180();
		if (createReports.size() == 1) {
			OadrCreateReportType createReport = createReports.get(0);
			checkReportRequest(createReport, 2);

			sendCreatedReport();
			
			OadrReportRequestType reportRequest1 = createReport.getOadrReportRequest().get(0);
			OadrReportRequestType reportRequest2 = createReport.getOadrReportRequest().get(1);
			sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update009(reportRequest1, reportRequest2));
			
			// Two UpdateReports testing
			// sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update009(reportRequest1, reportRequest2));
		} else if (createReports.size() == 2) {
			OadrCreateReportType createReport1 = createReports.get(0);
			OadrCreateReportType createReport2 = createReports.get(1);
			checkReportRequest(createReport1, 1);
			checkReportRequest(createReport2, 1);

			sendCreatedReport();
			sendCreatedReport();
			
			OadrReportRequestType reportRequest1 = createReport1.getOadrReportRequest().get(0);
			OadrReportRequestType reportRequest2 = createReport2.getOadrReportRequest().get(0);
			
			sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update009(reportRequest1, reportRequest2));
		} else {
			throw new FailedException("Expected 1 or 2 CreateReport requests.");
		}
	}

	private List<OadrCreateReportType> pollCreateReport_Rx180() {
		List<OadrCreateReportType> createReports = new ArrayList<>();
		
		String createReportText1 = VenToVtnClient.poll(OadrCreateReportType.class);
		
		if (!OadrUtil.isExpected(createReportText1, OadrCreateReportType.class)) {
			throw new FailedException("Expected OadrCreateReport has not been received");
		}
		
		OadrCreateReportType createReport1 = CreateReportEventHelper.createOadrCreateReportFromString(createReportText1);
		createReports.add(createReport1);

		String createReportText2 = VenToVtnClient.poll(OadrCreateReportType.class);
		
		if (OadrUtil.isExpected(createReportText2, OadrCreateReportType.class)) {
			OadrCreateReportType createReport2 = CreateReportEventHelper.createOadrCreateReportFromString(createReportText2);
			createReports.add(createReport2);
		}
		
		return createReports;
	}

}