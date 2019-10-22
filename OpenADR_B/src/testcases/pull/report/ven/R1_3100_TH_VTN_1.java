package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultRespRegReportMetadata_003AckAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;

public class R1_3100_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3100_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();

		listenForRequests();

		OadrCreateReportType createReport = waitForCreateReport(1);
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		
		IResponseRegisterReportTypeAckAction registerReport = new DefaultRespRegReportMetadata_003AckAction();
		registerReport.setReportRequestID(reportRequestID);
		queueResponse(registerReport);

		addResponse();

		addCanceledReportResponse().setlastEvent(true);
		
		prompt(resources.prompt_026());
		
		waitForCompletion();
		
		checkCreateReportReceived(1);
		
		checkRegisteredReportRequest(1);
		
		prompt(resources.prompt_028());
		
		waitForCancelReport(1);
		
		// pause(10);
	}
}
