package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.SchemaHelper;

public class E0_6040_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6040_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		listenForRequests();
		
		prompt(resources.prompt_012());

		OadrDistributeEventType distributeEvent = waitForDistributeEvent(1);
		checkDistributeEvent_Ex040(distributeEvent);
		
		String distributeEventText = SchemaHelper.getDistributeEventAsString(distributeEvent);

		// Testing 1 CreatedEvent
		sendCreatedEvent_Ex040(distributeEventText, 1);
		pause(2);
		sendCreatedEvent_Ex040(distributeEventText, 0);
		// sendCreatedEvent(distributeEventText, OptTypeType.OPT_IN);
	}
}
