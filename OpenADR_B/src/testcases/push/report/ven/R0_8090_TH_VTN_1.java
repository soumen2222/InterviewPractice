package testcases.push.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;

public class R0_8090_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8090_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		
		listenForRequests();

		prompt(resources.prompt_026());

		OadrCreateReportType createReport1 = waitForCreateReport(1);
		String reportRequestID1 = createReport1.getOadrReportRequest().get(0).getReportRequestID();
		
		OadrRegisterReportType registerReport1 = RegisterReportEventHelper.loadMetadata_003();
		registerReport1.setReportRequestID(reportRequestID1);
		sendRegisterReport(registerReport1);

		IResponseCreatedReportTypeAckAction createdReportAction = addCreatedReportResponse();
		createdReportAction.addReportRequestID(reportRequestID1);
		
		// extra RegisterReport
		// sendRegisterReport(RegisterReportEventHelper.loadMetadata_003());
		
		prompt(resources.prompt_027());

		OadrCreateReportType createReport2 = waitForCreateReport(2);
		String reportRequestID2 = createReport2.getOadrReportRequest().get(0).getReportRequestID();
		
		OadrRegisterReportType registerReport2 = RegisterReportEventHelper.loadMetadata_003();
		registerReport2.setReportRequestID(reportRequestID2);
		sendRegisterReport(registerReport2);

		OadrRegisterReportType registerReport3 = RegisterReportEventHelper.loadMetadata_003();
		registerReport3.setReportRequestID(reportRequestID1);
		sendRegisterReport(registerReport3);
		
		OadrRegisterReportType registerReport4 = RegisterReportEventHelper.loadMetadata_003();
		registerReport4.setReportRequestID(reportRequestID2);
		sendRegisterReport(registerReport4);
	}
}
