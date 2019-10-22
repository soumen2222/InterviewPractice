package testcases.push.opt.ven;

import java.util.List;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;

public class P0_7040_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7040_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_014());
		
		UIUserPrompt prompt18 = new UIUserPrompt(resources.prompt_018());
		addCreatedOptResponse().setPrompt(prompt18);

		UIUserPrompt prompt15 = new UIUserPrompt(resources.prompt_015());
		addCreatedOptResponse().setPrompt(prompt15);
		
		addCanceledOptResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCompletion();
		
		checkCreateOptRequest();
		checkCancelOptRequest(1);
	}

	private void checkCreateOptRequest() {
		List<OadrCreateOptType> createOpts = TestSession.getCreateOptEventReceivedList();
		if (createOpts.size() != 2) {
			throw new FailedException("Expected 2 OadrCreateOpt(s), received " + createOpts.size());
		}
		
		String optID1 = createOpts.get(0).getOptID();
		String optID2 = createOpts.get(1).getOptID();
		if (!optID1.equals(optID2)) {
			throw new FailedException("Expected " + optID1 + " and " + optID2 + " to match");
		}
	}
}
