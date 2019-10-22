package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.OadrCreateReportType;
import com.qualitylogic.openadr.core.signal.OadrUpdateReportType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8027_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8027_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		checkActiveRegistration();
		
		addCreatedReportResponse();
		addUpdatedReportResponse();

		listenForRequests();

		prompt(resources.prompt_064());

		OadrCreateReportType createReport = waitForCreateReport(1);

		OadrUpdateReportType updateReport = UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, createReport);
		sendUpdateReport(updateReport);
		
		waitForReportBackDuration(createReport);
		
		sendUpdateReport(updateReport);
	}
}
