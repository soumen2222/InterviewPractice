package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;

public class R0_8040_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8040_TH_VEN_1());
	}
	
	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		addCreatedReportResponse();
		addUpdatedReportResponse();
		addCanceledReportResponse().setlastEvent(true);

		listenForRequests();

		prompt(resources.prompt_020());
		
		waitForCreateReport(1);
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, null));
		
		prompt(resources.prompt_022());
		
		waitForCancelReport(1);
	}
}
