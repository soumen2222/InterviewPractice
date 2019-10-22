package testcases.push.event.vtn;

import com.qualitylogic.openadr.core.action.ICreatedEventAction;
import com.qualitylogic.openadr.core.action.impl.CreatedEventErrorAction;
import com.qualitylogic.openadr.core.action.impl.Default_CreatedEventActionOnLastDistributeEventReceived;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VenPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E0_6080_TH_VEN_1 extends VenPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6080_TH_VEN_1());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisable_VtnIDValid_Check(true);
		ConformanceRuleValidator.setDisableResponseEvent_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			TestSession.setSwapCaseVenID(true);
			ICreatedEventAction createdEvent = new Default_CreatedEventActionOnLastDistributeEventReceived(true, true);
			addCreatedEventResponse(createdEvent).setLastCreateEvent(true);
			
			listenForRequests();
			
			prompt(resources.prompt_065());
			
			waitForCompletion();
			
			checkDistributeEventRequest(1);
			checkCreatedEventCompleted(createdEvent);
			checkResponse(1, ErrorConst.INVALID_ID_452);
		} else {
			ICreatedEventAction createdEvent = new CreatedEventErrorAction(ErrorConst.INVALID_ID_452);
			addCreatedEventResponse(createdEvent).setLastCreateEvent(true);
			
			listenForRequests();

			waitForCompletion();
		}
	}
}
