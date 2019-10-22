package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3150_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3150_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		
		prompt(resources.prompt_045());
		
		OadrCreateReportType createReport = pollCreateReport();
		checkCreateReport_x150(createReport);
		
		sendCreatedReport();
		
		pause(2);
		
		OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update005(createReport);
		sendUpdateReport(updateReport);
	}
}
