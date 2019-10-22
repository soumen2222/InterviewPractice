package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E1_1040_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1040_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_012());
		
		String distributeEventText = pollDistributeEventString();
		// Testing 1 CreatedEvent
		sendCreatedEvent_Ex040(distributeEventText, 1);
		// sendCreatedEvent(distributeEventText, OptTypeType.OPT_IN);

		OadrDistributeEventType distributeEvent = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText);
		checkDistributeEvent_Ex040(distributeEvent);
		
		pause(2);
		
		// Testing 1 CreatedEvent
		sendCreatedEvent_Ex040(distributeEventText, 0);
	}
}
