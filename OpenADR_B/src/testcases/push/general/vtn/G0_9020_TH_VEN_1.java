package testcases.push.general.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;

public class G0_9020_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new G0_9020_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		boolean yesClicked = promptYes(resources.prompt_062());
		if (!yesClicked) {
			throw new FailedException("The alternately run test cases should pass without needing to configure the DUT VTN between executions.");
		}
	}
}
