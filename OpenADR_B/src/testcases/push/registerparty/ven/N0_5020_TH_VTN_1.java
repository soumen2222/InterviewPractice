package testcases.push.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.helper.RegisterReportEventHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N0_5020_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new N0_5020_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {

		
		prompt(resources.prompt_002());

		addCreatedPartyRegistrationResponse();
		addRegisteredReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_003());
		
		waitForCreatePartyRegistration(1);
		
		sendRegisterReport(RegisterReportEventHelper.loadMetadata_003());
		
		waitForCompletion();
		checkRegisterReportRequest(1);
	}
}
