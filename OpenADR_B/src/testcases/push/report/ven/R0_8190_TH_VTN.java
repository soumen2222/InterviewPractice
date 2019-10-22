package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8190_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8190_TH_VTN());
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
			OadrCreateReportType createReport1 = CreateReportEventHelper.loadOadrCreateReport_Request_001(ServiceType.VTN);
			createReport1.getOadrReportRequest().get(0).getReportSpecifier().setReportSpecifierID("INVALID_REPORT_SPECIFIER_ID");
			sendCreateReport(createReport1, ErrorConst.INVALID_ID_452);

			OadrCreateReportType createReport2 = CreateReportEventHelper.loadOadrCreateReport_Request_001(ServiceType.VTN);
			createReport2.getOadrReportRequest().get(0).getReportSpecifier().getSpecifierPayload().get(0).setRID("INVALID_RID");
			sendCreateReport(createReport2, ErrorConst.INVALID_ID_452);
		} else {
			addUpdatedReportResponse(ErrorConst.INVALID_ID_452).setlastEvent(true);
			
			listenForRequests();

			OadrCreateReportType createReport = CreateReportEventHelper.loadOadrCreateReport_Request_001(ServiceType.VTN);
			sendCreateReport(createReport);

			waitForCompletion();

			checkUpdateReport(1);
		}
	}
}
