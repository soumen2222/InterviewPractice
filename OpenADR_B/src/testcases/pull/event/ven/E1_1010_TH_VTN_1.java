package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_NoEvent_DistributeEventAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;

public class E1_1010_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1010_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		IDistributeEventAction distributeEvent = new Default_NoEvent_DistributeEventAction();
		queueResponse(distributeEvent);
		
		listenForRequests();
		
		waitForDistributeEvent(distributeEvent);
		
		checkDistributeEvent(distributeEvent);
		
		pause(10);
	}
}
