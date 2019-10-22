package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.impl.Default_NoEvent_DistributeEventAction;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;

public class E0_6010_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6010_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		sendDistributeEventWithEmptyResponse(new Default_NoEvent_DistributeEventAction());
	}
}
