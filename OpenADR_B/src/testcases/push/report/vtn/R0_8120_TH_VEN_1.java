package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.action.impl.DefaultResponseRegisteredReportWithMetaDataRequestAckAction;
import com.qualitylogic.openadr.core.base.VenPushTestCase;

public class R0_8120_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8120_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse(new DefaultResponseRegisteredReportWithMetaDataRequestAckAction());
		addResponse();
		addRegisteredReportResponse().setlastEvent(true);
		
		listenForRequests();

		prompt(resources.prompt_052());

		waitForRegisterReport(1);

		waitForCompletion();

		checkRegisterReportRequest(2);
	}
}
