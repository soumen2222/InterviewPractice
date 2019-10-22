package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultRespRegReportMetadata_003AckAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;

public class R1_3080_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3080_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		addCreatedReportResponse();
		
		addResponse();
		addResponse();
		
		listenForRequests();
		
		prompt(resources.prompt_026());
		
		OadrCreateReportType createReport = waitForCreateReport(1);
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();

		IResponseRegisterReportTypeAckAction registerReport1 = new DefaultRespRegReportMetadata_003AckAction();
		registerReport1.setReportRequestID(reportRequestID);
		queueResponse(registerReport1);

		// waitForCompletion();

		// List<OadrCreateReportType> createReports = TestSession.getOadrCreateReportTypeReceivedList();
		// if (createReports.size() > 0) {
		//	waitForReportBackDuration(createReport1);

		IResponseRegisterReportTypeAckAction registerReport2 = new DefaultRespRegReportMetadata_003AckAction();
		registerReport2.setReportRequestID(reportRequestID);
		queueResponse(registerReport2);
		
		waitForRegisteredReportRequest(2);
			
		//	System.out.println("Pausing for 20 seconds....");
		//	pause(20);
		// }
		
		// checkCreateReportReceived(1);
		// checkRegisteredReportRequest(2);
	}
}
