package testcases.pull.report.vtn;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.signal.OadrCancelReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreatedReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3170_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3170_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableRegisterReport_RequestIDMatchPreviousCreateReport_Check(true);
		ConformanceRuleValidator.setDisableCanceledReport_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			OadrCreateReportType createReport1 = pollCreateReport();
			String reportRequestID1 = createReport1.getOadrReportRequest().get(0).getReportRequestID();
			
			sendCreatedReport();

			OadrUpdateReportType updateReport_001 = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport1);
			sendUpdateReport(updateReport_001);

			OadrCreateReportType createReport2 = pollCreateReport();
			checkCreateReport_x170(createReport2);
			String reportRequestID2 = createReport1.getOadrReportRequest().get(0).getReportRequestID();

			OadrCreatedReportType createdReport = CreatedReportEventHelper.loadOadrCreatedReport();
			createdReport.getOadrPendingReports().getReportRequestID().add(reportRequestID1);
			sendCreatedReport(createdReport);
			
			OadrRegisterReportType registerReport = RegisterReportEventHelper.loadMetadata_001();
			sendRegisterReport(registerReport);

			OadrCancelReportType cancelReport = pollCancelReport();

			sendCanceledReport(cancelReport, ErrorConst.INVALID_ID_452, reportRequestID2);
		}
	}
}
