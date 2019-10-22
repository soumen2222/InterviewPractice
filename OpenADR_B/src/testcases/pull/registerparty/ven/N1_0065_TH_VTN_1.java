package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.action.IResponseCreatedPartyRegistrationAckAction;
import com.qualitylogic.openadr.core.action.impl.DefaultRespRegReportMetadata_003AckAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class N1_0065_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0065_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		IResponseCreatedPartyRegistrationAckAction createdPartyRegistration = addCreatedPartyRegistrationResponse();
		createdPartyRegistration.setlastEvent(true);
		createdPartyRegistration.setReRegistration(true);
		
		queueResponse(new DefaultRespRegReportMetadata_003AckAction());
		addResponse();
		addRegisteredReportResponse();

		listenForRequests();
		
		prompt(resources.prompt_006());
		prompt(resources.prompt_049());

		waitForCompletion();

		checkCreatePartyRegistrationRequest(1);
		
		pause(10);
	}
}
