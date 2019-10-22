package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R1_3070_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3070_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_025());
		
		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_004();
		sendCreateReport(createReport);
		
		pollRegisterReport();
		
		sendRegisteredReport();
	}
}
