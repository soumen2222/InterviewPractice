package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.action.impl.DefaultRespRegReportMetadata_003AckAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class N1_0060_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0060_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();
		
		addCreatedPartyRegistrationResponse().setNewRegistration(true);
		
		queueResponse(new DefaultRespRegReportMetadata_003AckAction());
		addResponse();
		addRegisteredReportResponse().setlastEvent(true);

		listenForRequests();
		
		prompt(resources.prompt_006());
		prompt(resources.prompt_035());

		waitForCompletion();
		waitForRegisteredReportRequest(1);

		checkCreatePartyRegistrationRequest(1);
		checkRegisteredReportRequest(1);
		checkRegisterReportRequest(1);
	}
}
