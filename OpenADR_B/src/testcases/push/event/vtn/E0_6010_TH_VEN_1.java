package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;

public class E0_6010_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6010_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		listenForRequests();
		
		prompt(resources.prompt_009());
		
		waitForDistributeEvent(1);
		
		checkDistributeEventRequest(1);
	}
}
