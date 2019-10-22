package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.bean.ServiceType;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class R0_8010_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8010_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		ConformanceRuleValidator.setDisableCreateReport_PeriodicReportReportDurationNotLessThanMinimumSamplingPeriod_Check(true);
		//ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		
		addUpdatedReportResponse().setlastEvent(true);
		
		listenForRequests();

		sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_001(ServiceType.VTN));

		waitForCompletion();

		checkUpdateReport(1);
	}
}
