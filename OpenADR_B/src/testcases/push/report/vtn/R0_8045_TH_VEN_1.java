package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.action.IResponseCanceledReportTypeAckAction;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8045_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8045_TH_VEN_1());
	}
	
	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		addUpdatedReportResponse();

		listenForRequests();

		prompt(resources.prompt_056());
		
		OadrCreateReportType createReport = waitForCreateReport(1);
		String reportRequestID = createReport.getOadrReportRequest().get(0).getReportRequestID();
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, null));

		IResponseCanceledReportTypeAckAction canceledReport = addCanceledReportResponse();
		canceledReport.addReportRequestID(reportRequestID);
		
		prompt(resources.prompt_057());
		
		waitForCancelReport(1);

		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, null));
	}
}
