package testcases.pull.general.ven;

import com.qualitylogic.openadr.core.action.IResponseEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_ResponseEventAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class G1_4011_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4011_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		IResponseEventAction response = new Default_ResponseEventAction();
		response.setlastEvent(true);
		queueResponse(response);
		
		listenForRequests();

		prompt(resources.prompt_059());
		
		waitForCompletion();
		
		checkResponse(response);
	}
}
