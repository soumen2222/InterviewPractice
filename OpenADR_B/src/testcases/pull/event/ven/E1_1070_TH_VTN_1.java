package testcases.pull.event.ven;

import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_006Action;
import com.qualitylogic.openadr.core.base.ErrorConst;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrCreatedEventType;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class E1_1070_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1070_TH_VTN_1());
	}
	
	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableCreatedEvent_ResponseCodeSuccess_Check(true);
		
		queueDistributeEvent(new DefaultDistributeEvent_006Action());
		
		addResponse();

		listenForRequests();

		OadrCreatedEventType createdEvent = waitForCreatedEvent(1);
		
		checkCreatedEvent(createdEvent, ErrorConst.SIGNAL_NOT_SUPPORTED_460);
	}
}
