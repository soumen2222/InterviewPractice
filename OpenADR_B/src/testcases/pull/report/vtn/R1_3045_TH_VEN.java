package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3045_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3045_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		prompt(resources.prompt_056());

		OadrCreateReportType createReport = pollCreateReport();
		sendCreatedReport();
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport));

		prompt(resources.prompt_057());

		OadrCancelReportType cancelReport = pollCancelReport();
		sendCanceledReport(cancelReport, reportRequestID);
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport));
	}
}
