package testcases.push.opt.ven;

import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class P0_7050_TH_VTN_1 extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new P0_7050_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCanceledOpt_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableCancelOpt_isOptIDValid_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_036());
		if (yesClicked) {
			throw new NotApplicableException("Placeholder test case for self test, skipping execution for real device.");
		} else {
			addCreatedOptResponse();
		}
		
		listenForRequests();
		
		waitForCancelOpt(1);

		checkCreateOptRequest(1);
	}
}
