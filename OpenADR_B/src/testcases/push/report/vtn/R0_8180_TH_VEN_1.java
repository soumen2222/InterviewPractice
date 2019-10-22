package testcases.push.report.vtn;

import java.util.ArrayList;
import java.util.List;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.OadrUtil;

public class R0_8180_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8180_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		addCreatedReportResponse();
		
		addUpdatedReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_058());	

		List<OadrCreateReportType> createReports = waitForCreateReport_Rx180();
		if (createReports.size() == 1) {
			OadrCreateReportType createReport = createReports.get(0);
			checkReportRequest(createReport, 2);
			
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

			OadrReportRequestType reportRequest1 = createReport1.getOadrReportRequest().get(0);
			OadrReportRequestType reportRequest2 = createReport2.getOadrReportRequest().get(0);
			sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update009(reportRequest1, reportRequest2));
		} else {
			throw new FailedException("Expected 1 or 2 CreateReport requests.");
		}
	}

	private List<OadrCreateReportType> waitForCreateReport_Rx180() {
		long testCaseTimeout = OadrUtil.getTestCaseTimeout();
		while (System.currentTimeMillis() < testCaseTimeout) {
			pause(1);
			
			if (TestSession.getOadrCreateReportTypeReceivedList().size() >= 1) {
				break;
			} else if (TestSession.isAtleastOneValidationErrorPresent()) {
				break;
			}
		}
		
		pause(15, "Waiting 15 seconds for additional CreateReport requests...");
		
		int listSize = TestSession.getOadrCreateReportTypeReceivedList().size();
		if ((listSize != 1) && (listSize != 2)) {
			throw new FailedException("Expected 1 or 2 CreateReport(s), received " + listSize);
		}

		checkForValidationErrors();
		
		List<OadrCreateReportType> createReports = new ArrayList<>();
		createReports.add(TestSession.getOadrCreateReportTypeReceivedList().get(0));
		if (listSize == 2) {
			createReports.add(TestSession.getOadrCreateReportTypeReceivedList().get(1));
		}
		
		return createReports;
	}
}
