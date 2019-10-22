package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;

public class E0_6020_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6020_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		ICreatedEventAction createdEvent = new Default_CreatedEventActionOnLastDistributeEventReceived(false, true);
		addCreatedEventResponse(createdEvent).setLastCreateEvent(true);
		
		listenForRequests();

		prompt(resources.prompt_010());
		
		waitForCompletion();
		
		checkCreatedEventCompleted(createdEvent);
		
		OadrDistributeEventType distributeEvent = checkDistributeEventRequest(1);
		checkSignalNames(distributeEvent, "ELECTRICITY_PRICE");
	}
}
