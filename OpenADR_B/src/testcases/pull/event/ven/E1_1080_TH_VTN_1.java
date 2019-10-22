package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_001Action;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E1_1080_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1080_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisable_VtnIDValid_Check(true);
		ConformanceRuleValidator.setDisableResponseEvent_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);

		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			TestSession.setSwapCaseVtnID(true);
			queueDistributeEvent(new DefaultDistributeEvent_001Action());
			
			addResponse();

			listenForRequests();

			OadrCreatedEventType createdEvent = waitForCreatedEvent(1);
			
			checkCreatedEvent(1, ErrorConst.INVALID_ID_452);
		} else {
			IDistributeEventAction distributeEvent = new DefaultDistributeEvent_001Action();
			ICreatedEventResult createdEvent = queueDistributeEvent(distributeEvent);
			createdEvent.setLastCreatedEventResult(true);
			
			addResponse(ErrorConst.INVALID_ID_452);

			listenForRequests();

			waitForCompletion();

			checkCreatedEventReceived(createdEvent);
		}
	}
}
