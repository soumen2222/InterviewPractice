package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3080_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3080_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

				
		OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_005();
		sendCreateReport(createReport);
		
		pollRegisterReport();
		
		sendRegisteredReport();
		
		waitForReportBackDuration(createReport);

		// String registerReport = VenToVtnClient.poll(OadrRegisterReportType.class);
		// if (!OadrUtil.isExpected(registerReport, OadrRegisterReportType.class)) {
		//	System.out.println("Pausing for 20 seconds...");
		//	pause(20);
		//	pollRegisterReport();
		// }
		pollRegisterReport();

		sendRegisteredReport();
	}
}