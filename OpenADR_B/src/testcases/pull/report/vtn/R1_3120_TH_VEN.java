package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.action.IResponseRegisteredReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportWithMetaDataRequestAckAction;
import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisteredReportType;

public class R1_3120_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3120_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_052());
		
		OadrRegisterReportType registerReport = pollRegisterReport();
		
		// TestSession.getOadrRegisterReportTypeReceivedList().add(registerReport);

		IResponseRegisteredReportTypeAckAction registeredReportAction = new DefaultResponseRegisteredReportWithMetaDataRequestAckAction();
		OadrRegisteredReportType registeredReport = registeredReportAction.getOadrRegisteredReportResponse();
		sendRegisteredReport(registeredReport, registerReport);

		pollRegisterReport();

		sendRegisteredReport();
	}
}
