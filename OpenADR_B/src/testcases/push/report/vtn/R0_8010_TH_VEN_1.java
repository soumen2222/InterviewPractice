package testcases.push.report.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.helper.UpdateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8010_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8010_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check(true);

		addCreatedReportResponse();
		
		addUpdatedReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_019());	

		waitForCreateReport(1);
		
		sendUpdateReport(UpdateReportEventHelper.loadOadrUpdateReport_Update001(ServiceType.VEN, true, null));
	}
}
