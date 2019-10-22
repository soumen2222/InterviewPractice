package testcases.pull.general.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.action.impl.DistEvent_002Modify10Min_SecondAction;
import com.qualitylogic.openadr.core.base.VtnPullTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class G1_4010_TH_VTN_1 extends VtnPullTestCase {

	public static void main(String[] args) {
		execute(new G1_4010_TH_VTN_1());
	}

	@Override
	public void test() throws Exception {
		// ConformanceRuleValidator.setDisableDistributeEvent_PayloadFloatLimitValid_Check(true);
		
		checkSecurity();
		
		TestSession.setSecurityDebug("true");
		
		IDistributeEventAction distributeEvent1 = new DefaultDistributeEvent_002Action();
		ICreatedEventResult createdEvent1 = queueDistributeEvent(distributeEvent1);
		
		IDistributeEventAction distributeEvent2 = new DistEvent_002Modify10Min_SecondAction();
		ICreatedEventResult createdEvent2 = queueDistributeEvent(distributeEvent2);
		
		addResponse();
		addResponse().setlastEvent(true);
		
		listenForRequests();
		
		prompt(String.format(resources.prompt_042(), properties.getSecurityMode()));
		
		waitForCompletion();

		checkCreatedEventReceived(createdEvent1);
		checkCreatedEventReceived(createdEvent2);
	}
}
