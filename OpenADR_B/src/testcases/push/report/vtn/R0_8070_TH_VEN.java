package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_004Action;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;

public class R0_8070_TH_VEN extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8070_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addRegisteredReportResponse().setlastEvent(true);
		
		listenForRequests();
		
		IResponseCreateReportTypeAckAction createReportAction = new DefaultResponseCreateReportRequest_004Action(ServiceType.VEN);
		OadrCreateReportType createReport = createReportAction.getOadrCreateReportResponse();
		sendCreateReport(createReport);
	
		waitForCompletion();
		
		checkRegisterReportRequest(1);
	}
}
