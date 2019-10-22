package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R1_3025_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3025_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		prompt(resources.prompt_055());
		
		OadrCreateReportType createReport = pollCreateReport();
		sendCreatedReport();

		waitForDtstart(createReport);

		sendUpdateReportWithSignedId(UpdateReportEventHelper.loadOadrUpdateReport_Update008(createReport));
	}
}
