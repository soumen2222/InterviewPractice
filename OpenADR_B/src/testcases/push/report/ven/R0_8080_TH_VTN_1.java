package testcases.push.report.ven;

import java.util.List;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8080_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8080_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

				
		addCreatedReportResponse();
		
		listenForRequests();

		prompt(resources.prompt_026());
		
		OadrCreateReportType createReport = waitForCreateReport(1);
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		
		OadrRegisterReportType registerReport = RegisterReportEventHelper.loadMetadata_003();
		registerReport.setReportRequestID(reportRequestID);
		sendRegisterReport(registerReport);
		
		List<OadrCreateReportType> createReports = TestSession.getOadrCreateReportTypeReceivedList();
		waitForReportBackDuration(createReports.get(0));
		
		sendRegisterReport(registerReport);
	}
}
