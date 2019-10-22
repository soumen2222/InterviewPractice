package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCancelReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_012Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCanceledReportType;

public class R1_3045_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3045_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		IResponseCreateReportTypeAckAction createReportAction = new DefaultResponseCreateReportRequest_012Action();
		queueResponse(createReportAction);
		
		IResponseCancelReportTypeAckAction cancelReportAction = new DefaultResponseCancelReportTypeAckAction(ServiceType.VTN, createReportAction);
		cancelReportAction.setReportToFollow(true);
		queueResponse(cancelReportAction);
		
		addResponse();
		addResponse();

		addUpdatedReportResponse();
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCancelReport(cancelReportAction);
		// waitForCancelReport(1);
		
		// checkUpdateReport(1);
		
		waitForCompletion();

		checkCreatedReport(1);
		
		OadrCanceledReportType canceledReport = checkCanceledReport(1);
		checkCanceledReportPendingReports(canceledReport, 1);		

		checkUpdateReport(2);
	}
}
