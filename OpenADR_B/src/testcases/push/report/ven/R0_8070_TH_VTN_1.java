package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class R0_8070_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8070_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();

		listenForRequests();

		prompt(resources.prompt_025());
		
		OadrCreateReportType createReport = waitForCreateReport(1);
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();

		OadrRegisterReportType registerReport = RegisterReportEventHelper.loadMetadata_003();
		registerReport.setReportRequestID(reportRequestID);
		sendRegisterReport(registerReport);
	}
}
