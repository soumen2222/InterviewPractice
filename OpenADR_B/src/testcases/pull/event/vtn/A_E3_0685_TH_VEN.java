package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.exception.FailedException;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.ResponseRequiredType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;
import com.qualitylogic.openadr.core.signal.helper.LogHelper;
import com.qualitylogic.openadr.core.util.ConformanceRuleValidator;
import com.qualitylogic.openadr.core.util.LogResult;
import com.qualitylogic.openadr.core.util.OadrUtil;
import com.qualitylogic.openadr.core.ven.VenToVtnClient;

public class A_E3_0685_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new A_E3_0685_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		ConformanceRuleValidator.setDisableDistributeEvent_EventStatusValid_ValidCheck(true);
		ConformanceRuleValidator.setDisable_CreatedDateTimeWithinExpectedWindow_Check(true);
		
		String message = resources.Prereq_NoEvents_Message() + "\n\n" + resources.TestCase_0685_UIUserPromptText();
		prompt(message);

		String distributeEventText1 = VenToVtnClient.poll(OadrDistributeEventType.class);
		if(OadrUtil.isValidationErrorPresent()){
			return;
		}

		boolean isExpectedReceived = OadrUtil.isExpected(distributeEventText1,OadrDistributeEventType.class);
	
		if(!isExpectedReceived){
			LogHelper.setResult(LogResult.FAIL);
			LogHelper
					.addTrace("Expected OadrDistributeEvent has not been received");
			LogHelper.addTrace("Test Case has Failed");
		    return;
		}
		
		OadrDistributeEventType distributeEvent1 = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText1);
		checkOadrEvent(distributeEvent1);
		sendCreatedEvent(distributeEventText1, OptTypeType.OPT_IN);		
		
		LogHelper.addTrace("---DistributeEvent 1--");
		if (!DistributeEventSignalHelper.isExpectedReceived("P", ResponseRequiredType.ALWAYS, 0, distributeEvent1.getOadrEvent().get(0))) {
			throw new FailedException("Did not receive a Pending Event");
		}

		pause(210);
		
		String distributeEventText2 = sendRequestEvent();
		OadrDistributeEventType distributeEvent2 = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText2);
		checkOadrEvent(distributeEvent2);

		//
		String eventID1 = distributeEvent1.getOadrEvent().get(0).getEiEvent().getEventDescriptor().getEventID();
		String eventID2 = distributeEvent2.getOadrEvent().get(0).getEiEvent().getEventDescriptor().getEventID();
		if (!eventID1.equals(eventID2)) {
			throw new FailedException("Event in the first and the second OadrDistributeEventType did not match");
		}
		
		LogHelper.addTrace("---DistributeEvent 2--");
		if (!DistributeEventSignalHelper.isExpectedReceived("CO", null, 0, distributeEvent2.getOadrEvent().get(0))) {
			prompt("Please rerun this test case as the random event start selected by the VEN was longer than the test case expected.");
			throw new FailedException("Rerun test case as random time selected by VEN was too long");
		}
	}
}
