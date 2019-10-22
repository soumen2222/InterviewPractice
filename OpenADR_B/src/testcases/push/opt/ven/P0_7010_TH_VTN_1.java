package testcases.push.opt.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class P0_7010_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7010_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_014());
		
		addCreatedOptResponse().setlastEvent(true);
		
		listenForRequests();

		waitForCompletion();
		
		checkCreateOptRequest(1);
	}
}
