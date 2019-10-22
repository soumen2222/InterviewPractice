package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R1_3140_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3140_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_044());
		
		OadrCreateReportType createReport = pollCreateReport();
		checkCreateReport_x140(createReport);
		
		sendCreatedReport();
		
		pause(2);
		
		OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update004(createReport);
		sendUpdateReport(updateReport);
	}
}
