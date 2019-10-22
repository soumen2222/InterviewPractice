package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3190_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3190_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check(true);
		ConformanceRuleValidator.setDisableCreatedReport_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableUpdatedReport_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableCreateReport_isRIDValid_Check_Check(true);
		ConformanceRuleValidator.setDisableHistoryUsageGreenbuttonGranularityReportBackDurationZero_Check(true);
		ConformanceRuleValidator.setDisableCreateReport_PeriodicTelemetryReportContainsRequiredElements_Check(true);
		ConformanceRuleValidator.setDisableCreateReportReportSpecifierNotPresent_Check(true);
		ConformanceRuleValidator.setDisableCreateReport_DoesNotContainExcludedReportIntervalProperties_Check(true);
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		ConformanceRuleValidator.setDisableCreateReport_reportRequestIDUnique_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			prompt(resources.prompt_019());

			OadrCreateReportType createReport = pollCreateReport();
			sendCreatedReport();
			
			OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport);
			updateReport.getOadrReport().get(0).setReportRequestID("INVALID_REPORT_REQUEST_ID");
			sendUpdateReport(updateReport, ErrorConst.INVALID_ID_452);
		} else {
			pollCreateReport();
			sendCreatedReport(ErrorConst.INVALID_ID_452);
			
			pollCreateReport();
			sendCreatedReport(ErrorConst.INVALID_ID_452);
		}
	}
}