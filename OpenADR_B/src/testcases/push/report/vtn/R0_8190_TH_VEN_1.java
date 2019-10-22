package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8190_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8190_TH_VEN_1());
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
			addCreatedReportResponse();
			addUpdatedReportResponse().setlastEvent(true);
			
			listenForRequests();
			
			prompt(resources.prompt_019());

			OadrCreateReportType createReport = waitForCreateReport(1);
			
			OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport);
			updateReport.getOadrReport().get(0).setReportRequestID("INVALID_REPORT_REQUEST_ID");
			sendUpdateReport(updateReport, ErrorConst.INVALID_ID_452);
		} else {
			addCreatedReportResponse(ErrorConst.INVALID_ID_452);
			addCreatedReportResponse(ErrorConst.INVALID_ID_452).setlastEvent(true);

			listenForRequests();

			waitForCompletion();
		}
	}
}
