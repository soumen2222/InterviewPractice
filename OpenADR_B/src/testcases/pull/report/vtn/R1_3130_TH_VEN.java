package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R1_3130_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3130_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_043());
		
		OadrCreateReportType createReport = pollCreateReport();
		checkCreateReport_x130(createReport);

		sendCreatedReport();
		
		pause(2);
		
		OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update003(createReport);
		sendUpdateReport(updateReport);
	}
}
