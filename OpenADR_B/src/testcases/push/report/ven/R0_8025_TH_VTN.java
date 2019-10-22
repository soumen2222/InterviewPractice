package testcases.push.report.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.helper.CreateReportEventHelper;

public class R0_8025_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new R0_8025_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkActiveRegistration();

		
		addUpdatedReportResponse().setlastEvent(true);

		listenForRequests();

		sendCreateReport(CreateReportEventHelper.loadOadrCreateReport_Request_011());

		boolean yesClicked = promptYes(resources.prompt_054());
		if (!yesClicked) {
			throw new FailedException("Expected a delay in transmission of the oadrUpdateReport payload.");
		}
		
		waitForCompletion();
		
		checkUpdateReport(1);
	}
}