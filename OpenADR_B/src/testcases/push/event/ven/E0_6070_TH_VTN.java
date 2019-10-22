package testcases.push.event.ven;

import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_006Action;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E0_6070_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6070_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		
		listenForRequests();

		sendDistributeEvent(new DefaultDistributeEvent_006Action(), ErrorConst.SIGNAL_NOT_SUPPORTED_460);

		OadrCreatedEventType createdEvent = waitForCreatedEvent(1);
		
		checkCreatedEvent(createdEvent, ErrorConst.SIGNAL_NOT_SUPPORTED_460);
	}
}
