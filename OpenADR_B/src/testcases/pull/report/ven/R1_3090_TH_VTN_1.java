package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreatedReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultRespRegReportMetadata_003AckAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;

public class R1_3090_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3090_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_026());
		
		addCreatedReportResponse();
		
		listenForRequests();

		OadrCreateReportType createReport = waitForCreateReport(1);
		String reportRequestID1 = createReport.getOadrReportRequest().get(0).getReportRequestID();

		IResponseRegisterReportTypeAckAction registerReport1 = new DefaultRespRegReportMetadata_003AckAction();
		registerReport1.setReportRequestID(reportRequestID1);
		queueResponse(registerReport1);
		
		addResponse();

		IResponseCreatedReportTypeAckAction createdReportAction = addCreatedReportResponse();
		createdReportAction.addReportRequestID(reportRequestID1);
		
		OadrCreateReportType createReport2 = waitForCreateReport(2);
		String reportRequestID2 = createReport2.getOadrReportRequest().get(0).getReportRequestID();
		
		IResponseRegisterReportTypeAckAction registerReport2 = new DefaultRespRegReportMetadata_003AckAction();
		registerReport2.setReportRequestID(reportRequestID2);
		queueResponse(registerReport2);
		
		IResponseRegisterReportTypeAckAction registerReport3 = new DefaultRespRegReportMetadata_003AckAction();
		registerReport3.setReportRequestID(reportRequestID1);
		queueResponse(registerReport3);

		IResponseRegisterReportTypeAckAction registerReport4 = new DefaultRespRegReportMetadata_003AckAction();
		registerReport4.setReportRequestID(reportRequestID2);
		queueResponse(registerReport4);

		addResponse();
		addResponse();
		addResponse();
				
		prompt(resources.prompt_027());
		
		waitForRegisteredReportRequest(4);
	}
}
