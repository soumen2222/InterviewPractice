package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.signal.OadrReportRequestType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class R0_8120_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8120_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		listenForRequests();

		prompt(resources.prompt_030());

		OadrRegisterReportType registerReport1 = RegisterReportEventHelper.loadMetadata_003();
		OadrRegisteredReportType registeredReport = sendRegisterReport(registerReport1);
		String reportRequestID = registeredReport.getOadrReportRequest().get(0).getReportRequestID();
		
		checkReportRequest(registeredReport);

		// populate CreateReportReceivedList to be read by sendCreatedReport
		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport();
		createReport.setRequestID(registerReport1.getRequestID());
		for (OadrReportRequestType reportRequest : createReport.getOadrReportRequest()) {
			reportRequest.setReportRequestID(reportRequestID);
		}
		TestSession.getOadrCreateReportTypeReceivedList().add(createReport);

		sendCreatedReport();

		OadrRegisterReportType registerReport2 = RegisterReportEventHelper.loadMetadata_003();
		registerReport2.setReportRequestID(reportRequestID);
		sendRegisterReport(registerReport2);
	}
}
