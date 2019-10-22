package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8170_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8170_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableCanceledReport_ResponseCodeSuccess_Check(true);
		
		addUpdatedReportResponse();
		addRegisteredReportResponse();
		
		listenForRequests();

		OadrCreateReportType createReport1 = CreateReportEventHelper.loadOadrCreateReport_Request_002(ServiceType.VTN);
		OadrCreatedReportType createdReport1 = sendCreateReport(createReport1);
		checkCreateReport1(createReport1, createdReport1);
		
		OadrUpdateReportType updateReport1 = waitForUpdateReport(1);
		checkSameReportRequestID(createReport1, updateReport1);
		
		OadrCreateReportType createReport2 = CreateReportEventHelper.loadOadrCreateReport_Request_005();
		OadrCreatedReportType createdReport2 = sendCreateReport(createReport2);
		checkCreateReport2(createReport1, createReport2, createdReport2);
		
		OadrRegisterReportType registerReport1 = waitForRegisterReport(1);
		// checkUpdateReport_x170(createReport2, registerReport2);

		OadrCanceledReportType canceledReport = sendCancelReport(createReport1, ErrorConst.INVALID_ID_452);
		checkCanceledReportPendingReports(canceledReport, 1);
	}

	private void checkCreateReport1(OadrCreateReportType createReport1, OadrCreatedReportType createdReport1) {
		checkSameReportRequestID(createReport1, createdReport1);
		checkCreatedReportPendingReports(createdReport1, 1);
	}

	private void checkCreateReport2(OadrCreateReportType createReport1, OadrCreateReportType createReport2, OadrCreatedReportType createdReport2) {
		checkSameReportRequestID(createReport2, createdReport2);
		checkDifferentReportRequestID(createReport1, createdReport2);
		checkCreatedReportPendingReports(createdReport2, 2);
	}
}
