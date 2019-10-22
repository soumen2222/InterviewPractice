package testcases.pull.registerparty.ven;

import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.exception.NotApplicableException;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class N1_0080_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new N1_0080_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_ActiveRegistration_Check(true);
		
		prompt(resources.prompt_002());
		
		checkNoActiveRegistration();
		
		boolean yesClicked = promptYes(resources.prompt_061());
		if (yesClicked) {
			throw new NotApplicableException("A pull VTN cannot initiate payloads, so this self test in not relevant.");
		} else {
			// listenForRequests();
			
			waitForCompletion(15 * 1000);
			
			throw new NotApplicableException("Selftest placeholder");
		}
	}
}
