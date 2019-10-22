package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R1_3020_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3020_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		prompt(resources.prompt_020());
		
		OadrCreateReportType createReport = pollCreateReport();

		sendCreatedReport();

		pause(2);
	
		OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport);
		sendUpdateReport(updateReport);
	
		pause(2);
		
		sendUpdateReport(updateReport);
	}
}
