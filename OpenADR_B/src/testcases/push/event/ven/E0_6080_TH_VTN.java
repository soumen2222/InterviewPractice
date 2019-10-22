package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_001Action;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E0_6080_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6080_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisable_VenIDValid_Check(true);
		ConformanceRuleValidator.setDisable_VtnIDValid_Check(true);
		ConformanceRuleValidator.setDisableResponseEvent_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		ConformanceRuleValidator.setDisableRequestIDValid_Check(true);
		ConformanceRuleValidator.setDisable_EventIDValid_Check(true);
		
		boolean yesClicked = promptYes(resources.prompt_050());
		if (yesClicked) {
			addResponse().setlastEvent(true);
			
			listenForRequests();

			TestSession.setSwapCaseVtnID(true);
			sendDistributeEvent(new DefaultDistributeEvent_001Action(), ErrorConst.INVALID_ID_452);

			OadrCreatedEventType createdEvent = waitForCreatedEvent(1);
			
			checkCreatedEvent(1, ErrorConst.INVALID_ID_452);
		} else {
			addResponse(ErrorConst.INVALID_ID_452);

			listenForRequests();

			ICreatedEventResult createdEvent = sendDistributeEvent(new DefaultDistributeEvent_001Action());
			createdEvent.setLastCreatedEventResult(true);

			waitForCompletion();
			
			checkCreatedEventReceived(createdEvent);
		}
	}
}
