package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8160_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8160_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();

		listenForRequests();
		
		prompt(resources.prompt_046());
		
		OadrCreateReportType createReport = waitForCreateReport(1);
		checkCreateReport_x160(createReport);
		
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		// System.out.println("reportRequestID=" + reportRequestID);
		
		pause(60, "Pausing for 1 minute before sending UpdateReport...");
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update006(createReport));

		pause(120, "Pausing for 2 minutes before sending UpdateReport...");
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update006(createReport));
	}
}
