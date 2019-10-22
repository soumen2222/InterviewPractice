package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventAction_One;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.common.UIUserPrompt;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;

public class E0_6030_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6030_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		ICreatedEventAction createEvent1 = new CreatedEventAction_One(true, true);
		createEvent1.setPromptForResponseReceipt(new UIUserPrompt(resources.prompt_011()));
		addCreatedEventResponse(createEvent1);
		
		ICreatedEventAction createEvent2 = new CreatedEventAction_One(true, true);
		createEvent2.setLastCreateEvent(true);
		addCreatedEventResponse(createEvent2);
		
		listenForRequests();

		prompt(resources.prompt_010());

		OadrDistributeEventType distributeEvent1 = waitForDistributeEvent(1);
		checkSignalNames(distributeEvent1, "ELECTRICITY_PRICE");

		OadrDistributeEventType distributeEvent2 = waitForDistributeEvent(2);
		checkSignalNames(distributeEvent2, "ELECTRICITY_PRICE");
		
		waitForCompletion();

		checkCreatedEventCompleted(createEvent1);
		checkCreatedEventCompleted(createEvent2);

	}
}
