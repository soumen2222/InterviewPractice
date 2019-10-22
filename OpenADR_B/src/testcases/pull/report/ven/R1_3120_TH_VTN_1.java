package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseRegisterReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultRespRegReportMetadata_003AckAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3120_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3120_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		queueResponse(new DefaultRespRegReportMetadata_003AckAction());
		addCreatedReportResponse();
		addResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_030());
		
		OadrRegisteredReportType registeredReport = waitForRegisteredReportRequest(1);
		checkReportRequest(registeredReport);
		String reportRequestID = registeredReport.getOadrReportRequest().get(0).getReportRequestID();

		IResponseRegisterReportTypeAckAction registerReport = new DefaultRespRegReportMetadata_003AckAction();
		registerReport.setReportRequestID(reportRequestID);
		queueResponse(registerReport);

		waitForCompletion();
		
		checkCreateReportReceived(1);
		checkRegisteredReportRequest(2);
	}
}
