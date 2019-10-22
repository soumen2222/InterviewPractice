package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3010_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3010_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

				
		prompt(resources.prompt_019());
		
		OadrCreateReportType createReport = pollCreateReport();
		// TestSession.getOadrCreateReportTypeReceivedList().add(oadrCreateReportType);
		
		sendCreatedReport();
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport));
	}
}