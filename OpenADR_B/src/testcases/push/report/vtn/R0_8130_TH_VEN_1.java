package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8130_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8130_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		addUpdatedReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_043());
		
		OadrCreateReportType createReport = waitForCreateReport(1);
		checkCreateReport_x130(createReport);
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update003(createReport));
	}
}
