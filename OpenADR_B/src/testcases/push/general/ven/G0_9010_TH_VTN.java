package testcases.push.general.ven;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.IDistributeEventAction;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.action.impl.DistEvent_002Modify10Min_SecondAction;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;

public class G0_9010_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new G0_9010_TH_VTN());
	}

	@Override
	public void test() throws Exception {
		checkSecurity();
		
		// ConformanceRuleValidator.setDisableDistributeEvent_PayloadFloatLimitValid_Check(true);

		TestSession.setSecurityDebug("true");
		
		prompt(String.format(resources.prompt_042(), properties.getSecurityMode()));
		
		addResponse();
		
		listenForRequests();

		IDistributeEventAction distributeEvent1 = new DefaultDistributeEvent_002Action();
		ICreatedEventResult createdEvent1 = sendDistributeEvent(distributeEvent1);
		
		waitForCreatedEvent(createdEvent1);
		
		// prompt(resources.prompt_032());

		IDistributeEventAction distributeEvent2 = new DistEvent_002Modify10Min_SecondAction();
		ICreatedEventResult createdEvent2 = sendDistributeEvent(distributeEvent2);

		addResponse().setlastEvent(true);
		
		waitForCompletion();

		checkCreatedEventReceived(createdEvent2);
	}
}
