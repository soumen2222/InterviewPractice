package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_001Action;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3190_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3190_TH_VTN_1());
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
			IResponseCreateReportTypeAckAction createReport1 = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN);
			createReport1.getOadrCreateReportResponse().getOadrReportRequest().get(0).getReportSpecifier().setReportSpecifierID("INVALID_REPORT_SPECIFIER_ID");
			queueResponse(createReport1);
			
			addResponse();

			IResponseCreateReportTypeAckAction createReport2 = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN);
			createReport2.getOadrCreateReportResponse().getOadrReportRequest().get(0).getReportSpecifier().getSpecifierPayload().get(0).setRID("INVALID_RID");
			queueResponse(createReport2);
			
			addResponse().setlastEvent(true);

			listenForRequests();

			waitForCreatedReport(1, ErrorConst.INVALID_ID_452);
			waitForCreatedReport(2, ErrorConst.INVALID_ID_452);
			
			waitForCompletion();
		} else {
			IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN);
			queueResponse(createReport);
			
			addResponse();
			
			addUpdatedReportResponse(ErrorConst.INVALID_ID_452).setlastEvent(true);

			listenForRequests();

			waitForCompletion();

			checkCreatedReport(1);
			checkUpdateReport(1);
		}
	}
}
