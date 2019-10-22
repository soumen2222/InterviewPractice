package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8025_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8025_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		addUpdatedReportResponse();

		listenForRequests();

		prompt(resources.prompt_055());

		OadrCreateReportType createReport = waitForCreateReport(1);

		waitForDtstart(createReport);
		
		sendUpdateReportWithSignedId(UpdateReportEventHelper.loadOadrUpdateReport_Update008(createReport));
	}
}
