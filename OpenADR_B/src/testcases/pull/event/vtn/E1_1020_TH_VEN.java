package testcases.pull.event.vtn;

import com.qualitylogic.openadr.core.base.VenPullTestCase;
import com.qualitylogic.openadr.core.signal.OadrDistributeEventType;
import com.qualitylogic.openadr.core.signal.OptTypeType;
import com.qualitylogic.openadr.core.signal.helper.DistributeEventSignalHelper;

public class E1_1020_TH_VEN extends VenPullTestCase {

	public static void main(String[] args) {
		execute(new E1_1020_TH_VEN());
	}

	@Override
	public void test() throws Exception {
		prompt(resources.prompt_010());

		String distributeEventText = pollDistributeEventString();
		sendCreatedEvent(distributeEventText, OptTypeType.OPT_IN);
		
		OadrDistributeEventType distributeEvent = DistributeEventSignalHelper.createOadrDistributeEventFromString(distributeEventText);
		checkSignalNames(distributeEvent, "ELECTRICITY_PRICE");
	}
}
