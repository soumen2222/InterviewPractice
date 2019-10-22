package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_002Action;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_005Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrCreatedReportType;
import com.qualitylogic.openadr.core.signal.OadrRegisterReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R1_3170_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3170_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableRegisterReport_RequestIDMatchPreviousCreateReport_Check(true);
		ConformanceRuleValidator.setDisableCanceledReport_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);

		listenForRequests();

		// first CreateReport
		IResponseCreateReportTypeAckAction createReportAction1 = new DefaultResponseCreateReportRequest_002Action(ServiceType.VTN);
		OadrCreateReportType createReport1 = createReportAction1.getOadrCreateReportResponse();
		queueResponse(createReportAction1);
		addResponse();
		
		OadrCreatedReportType createdReport1 = waitForCreatedReport(1);
		checkCreateReport1(createReport1, createdReport1);
		
		addUpdatedReportResponse();
		
		OadrUpdateReportType updateReport1 = waitForUpdateReport(1);
		checkSameReportRequestID(createReport1, updateReport1);
		
		// second CreateReport
		IResponseCreateReportTypeAckAction createReportAction2 = new DefaultResponseCreateReportRequest_005Action(ServiceType.VTN);
		OadrCreateReportType createReport2 = createReportAction2.getOadrCreateReportResponse();
		queueResponse(createReportAction2);
		addResponse();
		
		OadrCreatedReportType createdReport2 = waitForCreatedReport(2);
		checkCreateReport2(createReport1, createReport2, createdReport2);
		
		addRegisteredReportResponse();
		
		OadrRegisterReportType registerReport1 = waitForRegisterReport(1);
		// checkUpdateReport_x170(createReport2, updateReport1);

		// CancelReport
		IResponseCancelReportTypeAckAction cancelReportAction = new DefaultResponseCancelReportTypeAckAction(ServiceType.VTN, createReportAction1);
		queueResponse(cancelReportAction);
		
		OadrCanceledReportType canceledReport = waitForCanceledReport(1);
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
