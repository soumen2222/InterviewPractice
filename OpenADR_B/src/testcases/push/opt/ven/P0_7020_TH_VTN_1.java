package testcases.push.opt.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.UIUserPrompt;

public class P0_7020_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7020_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_014());
		
		UIUserPrompt prompt = new UIUserPrompt(resources.prompt_015(), UIUserPrompt.SMALL);
		addCreatedOptResponse().setPrompt(prompt);
		addCanceledOptResponse().setlastEvent(true);
		
		listenForRequests();
		
		waitForCompletion();

		checkCreateOptRequest(1);
		checkCancelOptRequest(1);
	}
}
