package testcases.push.event.ven;

import java.util.ArrayList;

import com.qualitylogic.openadr.core.action.ICreatedEventResult;
import com.qualitylogic.openadr.core.action.impl.DefaultDistributeEvent_002Action;
import com.qualitylogic.openadr.core.base.VtnPushTestCase;
import com.qualitylogic.openadr.core.common.TestSession;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrCreateOptType;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E0_6060_TH_VTN extends VtnPushTestCase {

	public static void main(String[] args) {
		execute(new E0_6060_TH_VTN());
	}
	
	@Override
	public void test() throws Exception {
		addResponse();
		
		addCreatedOptResponse().setlastEvent(true);
		
		listenForRequests();

		DefaultDistributeEvent_002Action distributeEventAction = new DefaultDistributeEvent_002Action();
		ICreatedEventResult createdEvent = sendDistributeEvent(distributeEventAction);
		createdEvent.setExpectedCreateOpt_OptOutCount(1);

		waitForCreatedEvent(1);
		
		prompt(resources.prompt_013());

		waitForCompletion();
	
		checkCreatedEventReceived(distributeEventAction.getDistributeEvent(), createdEvent);
	}

	private void checkCreatedEventReceived(OadrDistributeEventType distributeEvent, ICreatedEventResult createdEvent) {
		if (!createdEvent.isExpectedCreatedEventReceived()) {
            int expectedOptInCount = createdEvent.getExpectedCreateOpt_OptInCount();
            int expectedOptOutCount = createdEvent.getExpectedCreateOpt_OptOutCount();

            ArrayList<OadrCreateOptType> createOpts = TestSession.getCreateOptEventReceivedList();
            int receivedOptInCount = DistributeEventSignalHelper.numberOfOptInCreateOptReceived(distributeEvent, createOpts);
            int receivedOptOutCount = DistributeEventSignalHelper.numberOfOptOutCreateOptReceived(distributeEvent, createOpts);
            
            if (expectedOptOutCount != receivedOptOutCount) {
            	throw new FailedException("Expected " + expectedOptOutCount + " CreatOpt of type optOut, got " + receivedOptOutCount);
            } else {
            	throw new FailedException("OadrCreatedEvent has not been received.");
            }
		}
	}
}
