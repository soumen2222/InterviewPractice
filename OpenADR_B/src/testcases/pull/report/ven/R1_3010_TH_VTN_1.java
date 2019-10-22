package testcases.pull.report.ven;

import com.qualitylogic.openadr.core.action.IResponseCreateReportTypeAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultResponseCreateReportRequest_001Action;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;

public class R1_3010_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new R1_3010_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		IResponseCreateReportTypeAckAction createReport = new DefaultResponseCreateReportRequest_001Action(ServiceType.VTN);
		queueResponse(createReport);

		addResponse();
		
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();
		
		waitForCompletion();
		
		checkCreatedReport(1);
		checkUpdateReport(1);
	}
}
