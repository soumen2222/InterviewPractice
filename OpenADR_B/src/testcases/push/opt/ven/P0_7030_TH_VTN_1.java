package testcases.push.opt.ven;

import java.util.List;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class P0_7030_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7030_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		
		prompt(resources.prompt_039());
		
		UIUserPrompt prompt = new UIUserPrompt(resources.prompt_016());
		addCreatedOptResponse().setPrompt(prompt);

		addCreatedOptResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCompletion();
		
		checkCreateOptRequest();
	}

	private void checkCreateOptRequest() {
		List<OadrCreateOptType> createOpts = TestSession.getCreateOptEventReceivedList();
		if (createOpts.size() != 2) {
			throw new FailedException("Expected 2 OadrCreateOpt(s), received " + createOpts.size());
		}
		
		OadrCreateOptType createOpt1 = createOpts.get(0);
		if (!createOpt1.getOptType().equals(OptTypeType.OPT_IN)) {
			throw new FailedException("Expected the first CreateOpt to be optIn");
		}
		
		OadrCreateOptType createOpt2 = createOpts.get(1);
		if (!createOpt2.getOptType().equals(OptTypeType.OPT_OUT)) {
			throw new FailedException("Expected the second CreateOpt to be optOut");
		}
	}
}
