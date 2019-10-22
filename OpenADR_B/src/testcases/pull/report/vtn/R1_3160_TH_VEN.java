package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R1_3160_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3160_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_046());
		
		OadrCreateReportType createReport = pollCreateReport();
		checkCreateReport_x160(createReport);
		
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		
		sendCreatedReport();
		
		pause(60, "Pausing for 1 minute before sending UpdateReport...");
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update006(createReport));

		pause(120, "Pausing for 2 minutes before sending UpdateReport...");
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update006(createReport));
	}
}
